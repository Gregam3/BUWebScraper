package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class SaintsWebScraper extends AbstractScraper {
    public SaintsWebScraper() {
        setPostPattern("<span class=\"date\">([0-9]+-[0-9]+-[0-9]{4},&nbsp;<span class=\"time\">[0-9]+:[0-9]+ [AM|PM]+)</span></span>" +
                "[\\S\\s]*?line\"><strong>([\\S\\s]*?)</strong>" +
                "[\\S\\s]*?<div id=\"post_message_[0-9]+\">([\\S\\s]*?)<div class=\"postfoot\">");
        setPageUrlPrefix("/page");

        setPostGroupIndexes(new int[]{3,2,1});

        setForumSizePattern("<li>Posts: ([0-9,]+)</li> ");

        setQuotePattern("<div class=\"message\">([\\S\\s]*?)</div>([\\S\\s]*)");
    }
}
