package com.bu.forum;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class ForumPost {
    private String threadName;
    private String content;

    public ForumPost(String threadName, String content) {
        this.threadName = threadName;
        this.content = content;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
