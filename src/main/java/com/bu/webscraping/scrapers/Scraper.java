package com.bu.webscraping.scrapers;

import com.bu.forum.ForumPost;

import java.io.IOException;
import java.util.Set;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public interface Scraper {
    Set<ForumPost> retrieveForumPosts(String forumUrl) throws IOException;
}
