package com.bu.webscraping.scrapers;

import com.bu.Main;
import com.bu.forum.ForumPost;
import com.bu.forum.ForumType;
import com.bu.webscraping.Login;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public abstract class AbstractScraper implements Scraper {
    /**
     * Used to group each of the post counts for each category on the root forum url
     */
    private Pattern forumSizePattern;

    /**
     * Used to fetch each post on a thread page
     */
    private Pattern postPattern;

    /**
     * Finds the last page of the thread, used on first page only.
     */
    Pattern lastPagePatternLong = Pattern.compile("[Pp]age 1 of ([0-9]*)");

    private Pattern lastPagePatternShort;

    /**
     * Anything that occurs after the base threadUrl that allows for path variable page navigation e.g. "page-"
     */
    private String pageUrlPrefix = "";

    /**
     * Anything that occurs after the page number that allows for path variable page navigation e.g. "||" in the case of WestHamOnlineScraper
     */
    private String pageUrlSuffix = "";

    /**
     * Custom increment as many PHP pages use the path variable to declare how many posts are shown e.g. "40"
     */
    int pageIncrement = 1;

    /**
     * Many of the afore mentioned PHP pages start at 0 rather than 1
     */
    int pageStartIndex = 1;

    /**
     * Used to direct the matcher to the correct group,
     * index 0 always refers to the position of the post content group
     * index 1 always refers to the position of the username group
     * index 2 always refers to the position of the date and time group
     */
    private int[] postGroupIndexes = new int[]{1, 2, 3};

    /**
     *
     */
    private Login login = null;

    private static final String MULTIMEDIA_REPLACEMENT_TEXT = "[IMAGE/VIDEO/EMOJI only]";

    /**
     * Functionally Unmatchable Pattern.
     */
    private Pattern quotePattern = Pattern.compile("\\^{4000}");
    /**
     * Checks if there is any more text after the quote ends, one space is appended hence it checks for another character after.
     */
    private final Pattern notEmptyAfterQuotePattern = Pattern.compile("Quote: '[\\S\\s]*?'..");

    /**
     * Some sites are not quite compatible with the typical Prefix and Suffix used for navigating pages as they require .html at the end of the URL or they redirect you,
     * When this value is true it will append the entered url adding the .html after any suffix
     */
    private boolean requiresHTMLExtension = false;

    private ForumType forumType;

    public ForumType getForumType() {
        return forumType;
    }

    public void setForumType(ForumType forumType) {
        this.forumType = forumType;
    }

    void setRequiresHTMLExtension(boolean requiresHTMLExtension) {
        this.requiresHTMLExtension = requiresHTMLExtension;
    }

    void setQuotePattern(String quotePatternRegex) {
        this.quotePattern = Pattern.compile("([\\S\\s]*)" + quotePatternRegex);
    }

    public void setForumSizePattern(String forumSizeRegex) {
        this.forumSizePattern = Pattern.compile(forumSizeRegex);
    }

    void setPostPattern(String postRegex) {
        postPattern = Pattern.compile(postRegex);
    }

    void setLastPagePatternLong(String lastPageRegex) {
        this.lastPagePatternLong = Pattern.compile(lastPageRegex);
    }

    void setLastPagePatternShort(String lastPagePatternShortRegex) {
        this.lastPagePatternShort = Pattern.compile(lastPagePatternShortRegex);
    }

    void setPageUrlPrefix(String pageUrlPrefix) {
        this.pageUrlPrefix = pageUrlPrefix;
    }

    void setPageIncrement(int pageIncrement) {
        this.pageIncrement = pageIncrement;
    }

    void setPageStartIndex(int pageStartIndex) {
        this.pageStartIndex = pageStartIndex;
    }

    public void setPageUrlSuffix(String pageUrlSuffix) {
        this.pageUrlSuffix = pageUrlSuffix;
    }

    void setPostGroupIndexes(int[] postGroupIndexes) {
        this.postGroupIndexes = postGroupIndexes;
    }

    void setLogin(Login login) {
        this.login = login;
    }

    public long retrieveForumSize(String forumUrl) throws IOException {
        try {
            long totalPostCount = 0;

            String rawHtml = Jsoup.connect(forumUrl).get().toString();

            Matcher forumSizeMatcher = forumSizePattern.matcher(rawHtml);

            while (forumSizeMatcher.find())
                totalPostCount += Long.valueOf(forumSizeMatcher.group(1).replace(",", ""));

            System.out.println("Retrieved forum size for:" + forumUrl + " it had " + totalPostCount + " posts, written to forum-sizes.txt.");
            return totalPostCount;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public List<ForumPost> retrievePostsForThread(String threadUrl) throws IOException {
        if (login != null)
            login();

        int threadLength = getLongPageCount(threadUrl + ((requiresHTMLExtension) ? ".html" : ""));

        List<ForumPost> forumPosts = scrapePostsForThread(threadUrl, threadLength, 0);

        Main.siteNumber++;
        return forumPosts;
    }

    private List<ForumPost> scrapePostsForThread(String threadUrl, int threadLength, int startIndex) {
        List<ForumPost> forumPosts = new LinkedList<>();

        double currentPageCount = startIndex + 1;

        for (int pageNumber = pageStartIndex + (startIndex * pageIncrement);
            //threadLength * by increment because some path variables use post number rather than page number
             pageNumber <= (threadLength * pageIncrement);
             pageNumber += pageIncrement) {

            try {
                if (currentPageCount <= threadLength)
                    //Casting purely for text formatting
                    System.out.println("Thread: " + getFormedPageUrl(threadUrl, pageNumber) + " - Current thread: " + (int) currentPageCount + "/" + (int) threadLength
                            + " (" + (int) (((currentPageCount / threadLength) * 100)) + "%)" + " - Total: " + Main.cumulativePageCount);

                forumPosts.addAll(retrievePostsForPage(threadUrl + pageUrlPrefix + pageNumber + pageUrlSuffix));

                currentPageCount++;
                Main.cumulativePageCount++;

            } catch (Exception e) {
                System.err.println("Error: " + getFormedPageUrl(threadUrl, pageNumber) +
                        " produced the following error:" + e + ". Continuing scraping of remaining pages.");

                //Not pageNumber++ to indicate the next page is needed as page start index has not yet been 0 (may change later)
                forumPosts.addAll(scrapePostsForThread(threadUrl, threadLength, pageNumber));

                //To stop the current loop, so when the loop resumes at the next iterator it does not cascade back to finish this loop
                pageNumber = (threadLength * pageIncrement) + 1;
            }
        }


        return forumPosts;
    }

    private String getFormedPageUrl(String threadUrl, int iterator) {
        return threadUrl + pageUrlPrefix + iterator + pageUrlSuffix;
    }

    public List<ForumPost> retrievePostsForPage(String threadPageUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();

        String rawHtml;

        Connection pageConnection =
                Jsoup.connect(threadPageUrl).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");

        rawHtml = (login != null) ?
                pageConnection.cookies(
                        login.getCookies()
                ).get().toString() :
                pageConnection.get().toString();

        Matcher postMatcher = postPattern.matcher(rawHtml);

        String content;

        while (postMatcher.find()) {
            content = formatContent(postMatcher.group(postGroupIndexes[0]));

            ForumPost forumPost = new ForumPost(
                    threadPageUrl,
                    (content.isEmpty()) ? MULTIMEDIA_REPLACEMENT_TEXT : content,
                    Jsoup.parse(postMatcher.group(postGroupIndexes[1])).text(),
                    Jsoup.parse(postMatcher.group(postGroupIndexes[2])).text()
            );

            if (!forumPosts.contains(forumPost))
                forumPosts.add(
                        forumPost
                );
        }
        return forumPosts;
    }

    private void login() throws IOException {
        Connection.Response loginForm = Jsoup.connect(login.getLoginUrl())
                .userAgent("Mozilla")
                .method(Connection.Method.GET)
                .execute();

        Connection.Response response = Jsoup.connect(login.getLoginUrl())
                .data("login", login.getUsername())
                .data("password", login.getPassword())
                .cookies(loginForm.cookies())
                .method(Connection.Method.POST)
                .execute();

        for (String cookieName : login.getCookies().keySet())
            login.getCookies().put(cookieName, response.cookie(cookieName));

        System.out.println();
    }

    private final static int PRE_QUOTE_GROUP = 1;
    private final static int QUOTE_GROUP = 2;
    private final static int POST_QUOTE_GROUP = 3;
    private final static String QUOTE_START = "Quote: [";
    private final static String QUOTE_END = "]: Quote end|";

    private String formatContent(String rawContentHtml) {
        Matcher quoteMatcher = quotePattern.matcher(rawContentHtml);

        StringBuilder postWithQuotesBuilder = new StringBuilder();

        while (quoteMatcher.find()) {
            String quoteContent = (Jsoup.parse(quoteMatcher.group(QUOTE_GROUP)).text().isEmpty()) ? MULTIMEDIA_REPLACEMENT_TEXT : quoteMatcher.group(1);

            postWithQuotesBuilder
                    .append(quoteMatcher.group(PRE_QUOTE_GROUP)).append(" ")
                    .append(QUOTE_START).append(quoteContent).append(QUOTE_END)
                    .append(quoteMatcher.group(POST_QUOTE_GROUP)).append(" ");
        }

        String content = Jsoup.parse(
                (postWithQuotesBuilder.toString().isEmpty()) ?
                        rawContentHtml : postWithQuotesBuilder.toString()
        ).text();

        Matcher notEmptyAfterQuoteMatcher = notEmptyAfterQuotePattern.matcher(content);

        if (content.contains(QUOTE_START) && !notEmptyAfterQuoteMatcher.find())
            content += MULTIMEDIA_REPLACEMENT_TEXT;

        return content.replace("â€™", "'").replace("\"\"", "\"");
    }

    private int getLongPageCount(String threadUrl) throws IOException {
        try {
            String rawHtml = (login != null) ?
                    Jsoup.connect(threadUrl).cookies(login.getCookies()).get().toString() :
                    Jsoup.connect(threadUrl).get().toString();

            Matcher matcher = lastPagePatternLong.matcher(rawHtml);

            if (matcher.find())
                return Integer.valueOf(matcher.group(1));
            else
                return getShortPageCount(rawHtml);

        } catch (Exception e) {
            return 1;
        }
    }

    private int getShortPageCount(String rawHtml) {
        int lastPage = 0;

        Matcher matcher = lastPagePatternShort.matcher(rawHtml);

        while (matcher.find())
            lastPage = Integer.valueOf(matcher.group(1));

        return lastPage;
    }
}
