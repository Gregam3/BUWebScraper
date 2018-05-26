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
    private Pattern forumSizePattern;
    private Pattern postPattern;
    Pattern lastPagePattern = Pattern.compile("Page 1 of ([0-9]*)");
    private String pageUrlPrefix = "";
    private String pageUrlSuffix = "";
    int pagePathVariableIncrement = 1;
    int pagePathVariableStart = 1;

    //0 represents post content, 1 represents user and 2 represents time
    private int[] groupIndexes = new int[]{1, 2, 3};

    public void setForumSizePattern(String forumSizeRegex) {
        this.forumSizePattern = Pattern.compile(forumSizeRegex);
    }

    void setPostPattern(String postRegex) {
        postPattern = Pattern.compile(postRegex);
    }

    void setLastPagePattern(String lastPageRegex) {
        this.lastPagePattern = Pattern.compile(lastPageRegex);
    }

    void setPageUrlPrefix(String pageUrlPrefix) {
        this.pageUrlPrefix = pageUrlPrefix;
    }

    public void setPageUrlSuffix(String pageUrlSuffix) {
        this.pageUrlSuffix = pageUrlSuffix;
    }

    void setGroupIndexes(int[] groupIndexes) {
        this.groupIndexes = groupIndexes;
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

    public List<ForumPost> retrievePostsForForum(String threadUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();

        double threadLength = getPageCountForThread(threadUrl);

        double currentPageCount = 1;

        for (int pagePathVariableIterator = pagePathVariableStart;
            //threadLength * by increment because some path variables use post number rather than page number
             pagePathVariableIterator <= (threadLength * pagePathVariableIncrement);
             pagePathVariableIterator += pagePathVariableIncrement) {
            //Clears console
            //Casting purely for text formatting
            System.out.println("Thread: " + threadUrl + " - Pages scraped for current site: " + (int) currentPageCount + "/" + (int) threadLength
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

        while (postMatcher.find()) {
            ForumPost forumPost = new ForumPost(
                    Jsoup.parse(postMatcher.group(groupIndexes[0])).text(),
                    Jsoup.parse(postMatcher.group(groupIndexes[1])).text(),
                    Jsoup.parse(postMatcher.group(groupIndexes[2])).text()
            );

            if (!forumPosts.contains(forumPost))
                forumPosts.add(
                        forumPost
                );
        }
        return forumPosts;
    }

    private int getPageCountForThread(String threadUrl) throws IOException {
        try {
            Matcher matcher = lastPagePattern.matcher(Jsoup.connect(threadUrl).get().toString());
            matcher.find();
            return Integer.valueOf(matcher.group(1));
        } catch (Exception e) {
            System.err.println("Could not retrieve page count, this may be due to the thread being fewer than 5 pages. If so this is not an issue.");
            return 1;
        }
    }
}
