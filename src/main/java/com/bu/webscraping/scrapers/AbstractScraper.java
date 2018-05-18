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
    private Pattern postPattern;
    private Pattern lastPagePattern = Pattern.compile("Page 1 of ([0-9]*)");
    private String pageUrlFormat = "";
    int pagePathVariableIncrement = 1;
    int pagePathVariableStart = 1;

    //0 represents post content, 1 represents user and 2 represents time
    private int[] groupIndexes = new int[]{1, 2, 3};

    void setPostPattern(String postRegex) {
        postPattern = Pattern.compile(postRegex);
    }

    void setLastPagePattern(String lastPageRegex) {
        this.lastPagePattern = Pattern.compile(lastPageRegex);
    }

    void setPageUrlFormat(String pageUrlFormat) {
        this.pageUrlFormat = pageUrlFormat;
    }

    void setGroupIndexes(int[] groupIndexes) {
        this.groupIndexes = groupIndexes;
    }

    public List<ForumPost> retrievePostsForForum(String threadUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();

        int threadLength = getLastPage(threadUrl);

        int currentPageCount = 1;

        for (int pagePathVariableIterator = pagePathVariableStart;
             //threadLength * by increment because some path variables use post number rather than page number
             pagePathVariableIterator < (threadLength * pagePathVariableIncrement) + pagePathVariableIncrement;
             pagePathVariableIterator += pagePathVariableIncrement) {
            System.out.println("Thread: " + threadUrl + " - Pages scraped for current site: "+ currentPageCount+" out of "+threadLength+". Total pages scraped: " + Main.cumulativePageCount);
            forumPosts.addAll(retrievePostsForPage(threadUrl + pageUrlFormat + pagePathVariableIterator));
            currentPageCount++;
            Main.cumulativePageCount++;
        }

        Main.siteNumber++;
        return forumPosts;
    }

    public List<ForumPost> retrievePostsForPage(String forumUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();

        String rawHtml = Jsoup.connect(forumUrl).get().toString();

        Matcher postMatcher = postPattern.matcher(rawHtml);

        while (postMatcher.find())
            forumPosts.add(
                    new ForumPost(
                            Jsoup.parse(postMatcher.group(groupIndexes[0])).text(),
                            postMatcher.group(groupIndexes[1]),
                            Jsoup.parse(postMatcher.group(groupIndexes[2])).text()
                    )
            );

        return forumPosts;
    }

    private int getLastPage(String threadUrl) throws IOException {
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
