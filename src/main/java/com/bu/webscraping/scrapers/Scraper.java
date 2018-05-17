package com.bu.webscraping.scrapers;

import com.bu.forum.Club;
import com.bu.forum.ForumPost;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public interface Scraper {
    List<ForumPost> retrievePosts(String forumUrl) throws IOException;
    List<ForumPost> retrieveAllPosts(String forumUrl) throws IOException;
}
