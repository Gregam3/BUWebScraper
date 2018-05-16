package com.bu.forum;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class ForumPost {
    private String postContent;
    private String username;
    private String time;

    public ForumPost(String postContent, String username, String time) {
        this.postContent = postContent;
        this.username = username;
        this.time = time;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
