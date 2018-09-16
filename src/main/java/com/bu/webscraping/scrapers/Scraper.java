package com.bu.webscraping.scrapers;

import com.bu.forum.ForumPost;
import com.bu.forum.ForumType;

import java.io.IOException;
import java.util.List;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public interface Scraper {
    List<ForumPost> retrievePostsForPage(String forumUrl) throws IOException;
    List<ForumPost> retrievePostsForThread(String forumUrl) throws IOException;
    long retrieveForumSize(String forumUrl) throws IOException;
    ForumType getForumType();
}
