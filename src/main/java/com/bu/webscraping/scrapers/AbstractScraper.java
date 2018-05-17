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
    Pattern lastPagePattern;
    String pageUrlFormat = "";
    int pagePathVariableIncrement = 1;
    int pagePathVariableStart = 1;

    //0 represents post content, 1 represents user and 2 represents time
    int[] groupIndexes = new int[]{1, 2, 3};

    void setPostPattern(String postRegex) {
        postPattern = Pattern.compile(postRegex);
    }

    public void setLastPagePattern(String lastPageRegex) {
        this.lastPagePattern = Pattern.compile(lastPageRegex);
    }

    public void setPageUrlFormat(String pageUrlFormat) {
        this.pageUrlFormat = pageUrlFormat;
    }


    public List<ForumPost> retrieveAllPosts(String threadUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();

        int threadLength = getLastPage(threadUrl);

        for (int pagePathVariableIterator = pagePathVariableStart;
             //threadLength * by increment because some path variables use post number rather than page number
             pagePathVariableIterator < (threadLength * pagePathVariableIncrement) + pagePathVariableIncrement;
             pagePathVariableIterator += pagePathVariableIncrement) {
            System.out.println("Site " + Main.siteNumber + " - Total pages scraped: " + Main.cumulativePageCount);
            forumPosts.addAll(retrievePosts(threadUrl + pageUrlFormat + pagePathVariableIterator));

            Main.cumulativePageCount++;
        }

        Main.siteNumber++;
        return forumPosts;
    }

    public List<ForumPost> retrievePosts(String forumUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();

        String rawHtml = Jsoup.connect(forumUrl).get().toString();

        Matcher postMatcher = postPattern.matcher(rawHtml);

        while (postMatcher.find())
            forumPosts.add(
                    new ForumPost(
                            //group one should always be post content
                            Jsoup.parse(postMatcher.group(groupIndexes[0])).text(),
                            //group two should always be username
                            postMatcher.group(groupIndexes[1]),
                            //group three should always be date
                            postMatcher.group(groupIndexes[2])
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
            System.err.println("Could not retrieve page count:" + e);
            return 1;
        }
    }
}
