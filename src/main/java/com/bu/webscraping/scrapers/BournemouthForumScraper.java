package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class BournemouthForumScraper extends AbstractScraper {
    public BournemouthForumScraper() {
        setPostPattern("data-lb-caption-desc=\"(.*?)· ([A-Z][a-z]{2} [0-9]+, [0-9]{4} at [0-9]+:[0-9]{2} [PM|AM]{2})" +
                "[\\S\\s]*?<article class=\"message-body.*([\\S\\s]*?)<footer class=\"message-footer\">");
        setLastPagePatternLong("aria-expanded=\"[true|false]+\" aria-haspopup=\"[true|false]+\"> 1 of ([0-9]+) </a>");
        setPageUrlPrefix("/page-");
        setForumSizePattern("<dl class=\"pairs pairs--inline\">[\\S\\s]*?<dt>[\\S\\s]*?Messages[\\S\\s]*?</dt>[\\S\\s]*?<dd>[\\S\\s]*?([0-9,]+)[\\S\\s]*?</dd>");

        setPostGroupIndexes(new int[]{3,1,2});

        setQuotePattern("<div class=\"bbCodeBlock-content\"> ([\\S\\s]*?)<div class=\"bbCodeBlock-expandLink\">" +
                "[\\S\\s]*</a>([\\S\\s]*)");

        setForumType(ForumType.BOURNEMOUTH_BOURNEMOUTH_FORUM);
    }
}
