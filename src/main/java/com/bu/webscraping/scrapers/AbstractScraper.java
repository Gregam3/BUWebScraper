package com.bu.webscraping.scrapers;

import com.bu.forum.ForumPost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public abstract class AbstractScraper implements Scraper{
    private static Pattern THREAD_PATTERN;
    private static Pattern CONTENT_PATTERN;

    private void setThreadPattern(String threadPatternString) {
        THREAD_PATTERN = Pattern.compile(threadPatternString);
    }

    private void setContentPattern(String contentPatternString) {
        THREAD_PATTERN = Pattern.compile(contentPatternString);
    }

    public Set<ForumPost> retrieveForumPosts(String forumUrl) throws IOException {
        Document document = Jsoup.connect(forumUrl).get();

        return null;
    }
}
