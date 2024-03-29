package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class SpursCommunityScraper extends AbstractScraper {
    public SpursCommunityScraper() {
        setPostPattern("data-lb-caption-desc=\"([\\S\\s]*?) · ([A-Z][a-z]{2} [0-9]+, [0-9]{4} at [0-9]+:[0-9]{2} [PM|AM]+)\">" +
                "[\\S\\s]*?<div class=\"bbWrapper\">([\\S\\s]*?)<footer class=\"message-footer\"> ");
        setLastPagePatternLong("ria-expanded=\"false\" aria-haspopup=\"true\"> 1 of ([0-9]+)");
        setPageUrlPrefix("/page-");

        setPostGroupIndexes(new int[]{3,1,2});

        setForumSizePattern("<dt>[\\S\\s]*? Messages[\\S\\s]*?</dt>[\\S\\s]*?<dd>[\\S\\s]*?([0-9,]+)[\\S\\s]*?</dd> ");

        setQuotePattern("<div class=\"bbCodeBlock-expandContent\">([\\S\\s]*?)</div>" +
                "[\\S\\s]*?</a>([\\S\\s]*?) <div class=\"js-selectToQuoteEnd\">");

        setForumType(ForumType.TOTTENHAM_HOTSPURS_SPURS_COMMUNITY);
    }
}
