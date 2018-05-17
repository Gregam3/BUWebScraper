package com.bu.webscraping.scrapers;

import com.bu.forum.ForumPost;

import java.io.IOException;
import java.util.List;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public interface Scraper {
    List<ForumPost> retrievePostsForPage(String forumUrl) throws IOException;
    List<ForumPost> retrievePostsForForum(String forumUrl) throws IOException;
}
