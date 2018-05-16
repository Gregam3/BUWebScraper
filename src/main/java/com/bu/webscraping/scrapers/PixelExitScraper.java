package com.bu.webscraping.scrapers;

import com.bu.forum.Club;
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
public class PixelExitScraper extends AbstractScraper {
    private static final Pattern lastPagePattern = Pattern.compile("Page 1 of ([0-9]*)");

    public PixelExitScraper() {
        setPostPattern("<blockquote.*>([\\S\\s]*?)<div class=\\\"messageTextEndMarker\\\">[\\S\\s]*?dir=\"auto\">([^<]+)[\\S\\s]*?<span class=\"DateTime\" title=\"([^\"]+)");
    }

    public List<ForumPost> retrieveAllPosts(String threadUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();

        pageFormat = "page-";

        int threadLength = getLastPage(threadUrl);

        for (int pageIterator = 1; pageIterator < threadLength + 1; pageIterator++) {
            System.out.println("Read " + pageIterator + " pages");
            forumPosts.addAll(retrievePosts(threadUrl + pageFormat + pageIterator));
        }

        return forumPosts;
    }

    private int getLastPage(String threadUrl) throws IOException {
        try {
            Matcher matcher = lastPagePattern.matcher(Jsoup.connect(threadUrl).get().toString());
            matcher.find();
            return Integer.valueOf(matcher.group(1));
        } catch (Exception e) {
            System.err.print("Could not retrieve page count:" + e);
            return -1;
        }
    }
}