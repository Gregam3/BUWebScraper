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
        setLastPagePatternLong("Page ([0-9]+) -[\\S\\s]*?<a ");
        setPostGroupIndexes(new int[]{3, 1, 2});
    }

    @Override
    public List<ForumPost> retrievePostsForForum(String threadUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();


        double currentPageCount = 1;
        int pagePathVariableIterator = 0;

        while (true) {
            Matcher currentPageMatcher = lastPagePatternLong.matcher(Jsoup.connect(threadUrl + "|n|1|" + pagePathVariableIterator).get().toString());

            if (!currentPageMatcher.find())
                return forumPosts;

            System.out.println("Thread: " + threadUrl + " - Pages scraped for current thread: " + (int) currentPageCount +
                    ", unavailable total for WestHamOnline. This is normal don't worry. Total pages scraped: " + Main.cumulativePageCount);

            forumPosts.addAll(retrievePostsForPage(threadUrl + "|n|1|" + pagePathVariableIterator));

            pagePathVariableIterator++;
            currentPageCount++;
            Main.cumulativePageCount++;
        }
    }

}
