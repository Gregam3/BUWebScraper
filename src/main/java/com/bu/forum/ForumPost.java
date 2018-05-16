package com.bu.forum;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class ForumPosts {
    private Club club;
    private List<String> posts;

    public ForumPosts(Club club) {
        this.club = club;
        this.posts = new LinkedList<>();
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    public void addAll(List<String> post) {
        posts.addAll(post);
    }
}
