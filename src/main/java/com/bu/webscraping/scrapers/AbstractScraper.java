package com.bu.webscraping.scrapers;

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
public abstract class AbstractScraper {
    private static Pattern POST_PATTERN;
    String pageFormat = "";

    String forumUrl;

    void setPostPattern(String postRegex) {
        POST_PATTERN = Pattern.compile(postRegex);
    }

    List<ForumPost> retrievePosts(String forumUrl) throws IOException {
        List<ForumPost> forumPosts = new LinkedList<>();

        String rawHtml = Jsoup.connect(forumUrl).get().toString();

        Matcher postMatcher = POST_PATTERN.matcher(rawHtml);

        while (postMatcher.find())
            forumPosts.add(
                    new ForumPost(
                            //group one should always be post content
                            postMatcher.group(1),
                            //group two should always be username
                            postMatcher.group(2),
                            //group three should always be date
                            postMatcher.group(3)
                    )
            );

        return forumPosts;
    }
}
