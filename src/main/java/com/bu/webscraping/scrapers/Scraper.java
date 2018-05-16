package com.bu.webscraping.scrapers;

import com.bu.forum.Club;
import com.bu.forum.ForumPost;

import java.io.IOException;
import java.util.Set;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public interface Scraper {
    Set<ForumPost> retrievePosts(String forumUrl, Club club) throws IOException;
    void setCurrentForumUrl(String forumUrl);
}
