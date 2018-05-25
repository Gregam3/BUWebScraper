package com.bu.webscraping.scrapers;

import com.bu.Main;
import com.bu.forum.ForumPost;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class WestHamOnlineScraper extends AbstractScraper {
    public WestHamOnlineScraper() {
        setPostPattern("<a href=\"profile.php\\?[0-9]+\"><b>([\\S\\s]*?)</b></a> ([0-9]+:[0-9]{2} [A-Z][a-z]+ [A-Z][a-z]{2} [0-9]+)" +
                "[\\S\\s]*?bgcolor=\"#FFFFFF\" valign=\"top\">[\\S\\s]*?<div class=\"mediumtext\">([\\S\\s]*?)</div> </td> ");
        setLastPagePattern("Page ([0-9]+) -[\\S\\s]*?<a ");
        setGroupIndexes(new int[]{3,1,2});
    }

    @Override
    public List<ForumPost> retrievePostsForForum(String threadUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();


        double currentPageCount = 1;
        int pagePathVariableIterator = 0;
        int previousScrapedPageNumber = 0;

        while (true) {
            Matcher currentPageMatcher = lastPagePattern.matcher(Jsoup.connect(threadUrl + "||" + pagePathVariableIterator + "||").get().toString());

            currentPageMatcher.find();
            int currentPageNumber = Integer.valueOf(currentPageMatcher.group(1));

            System.out.println("Thread: " + threadUrl + " - Pages scraped for current site: " + (int) currentPageCount +
                    ", unavailable total for WestHamOnline. This is normal don't worry. Total pages scraped: " + Main.cumulativePageCount);

            forumPosts.addAll(retrievePostsForPage(threadUrl + "||" + pagePathVariableIterator + "||"));
            currentPageCount++;
            Main.cumulativePageCount++;

            if(currentPageNumber == previousScrapedPageNumber) {
                Main.siteNumber++;
                return forumPosts;
            }

            previousScrapedPageNumber = currentPageNumber;
        }
    }

}
