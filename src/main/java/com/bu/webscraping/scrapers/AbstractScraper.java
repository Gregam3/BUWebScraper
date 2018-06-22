package com.bu.webscraping.scrapers;

import com.bu.Main;
import com.bu.forum.ForumPost;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
    Pattern lastPagePatternLong = Pattern.compile("Page 1 of ([0-9]*)");

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
    int pagePathVariableIncrement = 1;

    /**
     * Many of the afore mentioned PHP pages start at 0 rather than 1
     */
    int pagePathVariableStart = 1;

    /**
     * Used to direct the matcher to the correct group,
     * index 0 always refers to the position of the post content group
     * index 1 always refers to the position of the username group
     * index 2 always refers to the position of the date and time group
     */
    private int[] postGroupIndexes = new int[]{1, 2, 3};
    
    private static final String MULTIMEDIA_REPLACEMENT_TEXT = "[IMAGE/VIDEO/EMOJI] only";

    private List<String> wordsToRemoveForQuote = new LinkedList<>();

    /** Unmatchable Pattern */
    private Pattern quotePattern = Pattern.compile("\\^{4000}");

    private final Pattern notEmptyAfterQuotePattern = Pattern.compile("Quote: '[\\S\\s]*?' -.");

    void setQuotePattern(String quotePatternRegex) {
        this.quotePattern = Pattern.compile(quotePatternRegex);
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

    public void setPageUrlSuffix(String pageUrlSuffix) {
        this.pageUrlSuffix = pageUrlSuffix;
    }

    void setPostGroupIndexes(int[] postGroupIndexes) {
        this.postGroupIndexes = postGroupIndexes;
    }

    public long retrieveForumSize(String forumUrl) throws IOException {
        long totalPostCount = 0;

        String rawHtml = Jsoup.connect(forumUrl).get().toString();

        Matcher forumSizeMatcher = forumSizePattern.matcher(rawHtml);

        while (forumSizeMatcher.find())
            totalPostCount += Long.valueOf(forumSizeMatcher.group(1).replace(",", ""));

        System.out.println("Retrieved forum size for:" + forumUrl + " it had " + totalPostCount + " posts, written to forum-sizes.txt.");

        return totalPostCount;
    }

    public List<ForumPost> retrievePostsForThread(String threadUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();

        double threadLength = getPageCountForThread(threadUrl);

        double currentPageCount = 1;

        for (int pagePathVariableIterator = pagePathVariableStart;
            //threadLength * by increment because some path variables use post number rather than page number
             pagePathVariableIterator <= (threadLength * pagePathVariableIncrement);
             pagePathVariableIterator += pagePathVariableIncrement) {

            if (currentPageCount <= threadLength)
                //Casting purely for text formatting
                System.out.println("Thread: " + threadUrl + " - Pages scraped for current thread: " + (int) currentPageCount + "/" + (int) threadLength
                        + " (" + (int) (((currentPageCount / threadLength) * 100)) + "%)" + ". Total pages scraped: " + Main.cumulativePageCount);

            forumPosts.addAll(retrievePostsForPage(threadUrl + pageUrlPrefix + pagePathVariableIterator + pageUrlSuffix));
            currentPageCount++;
            Main.cumulativePageCount++;
        }

        Main.siteNumber++;
        return forumPosts;
    }

    public List<ForumPost> retrievePostsForPage(String threadPageUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();

        String rawHtml = Jsoup.connect(threadPageUrl).get().toString();

        Matcher postMatcher = postPattern.matcher(rawHtml);

        String content;

        while (postMatcher.find()) {
            content = formatContent(postMatcher.group(postGroupIndexes[0]));

            ForumPost forumPost = new ForumPost(
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

    private String formatContent(String rawContentHtml) {
        Matcher quoteMatcher = quotePattern.matcher(rawContentHtml);

        if (quoteMatcher.find())
            rawContentHtml = "Quote: '"
                    +((Jsoup.parse(quoteMatcher.group(1)).text().isEmpty()) ? MULTIMEDIA_REPLACEMENT_TEXT : quoteMatcher.group(1))
                    + "' -  " + quoteMatcher.group(2);

        String content = Jsoup.parse(rawContentHtml).text();

        Matcher notEmptyAfterQuoteMatcher = notEmptyAfterQuotePattern.matcher(content);

        if(content.contains("Quote: '") && !notEmptyAfterQuoteMatcher.find())
            content += MULTIMEDIA_REPLACEMENT_TEXT;

        return content.replace("â€™", "'").replace("\"\"", "\"");
    }

    private int getPageCountForThread(String threadUrl) throws IOException {
        try {
            String rawHtml = Jsoup.connect(threadUrl).get().toString();

            Matcher matcher = lastPagePatternLong.matcher(rawHtml);
            if (matcher.find())
                return Integer.valueOf(matcher.group(1));
            else
                return getShortPageCount(rawHtml);

        } catch (Exception e) {
            System.err.println("Could not retrieve page count, this may be due to the thread being fewer than 5 pages. If so, this is not an issue.");
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
