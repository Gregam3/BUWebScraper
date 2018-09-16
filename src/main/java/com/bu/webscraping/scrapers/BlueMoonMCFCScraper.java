package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class BlueMoonMCFCScraper extends AbstractScraper {

    public BlueMoonMCFCScraper() {
        setPostPattern("<blockquote.*>([\\S\\s]*?)<div class=\\\"messageTextEndMarker\\\">" +
                "[\\S\\s]*?dir=\"auto\">([^<]+)[\\S\\s]*?class=\"DateTime\"[\\S\\s]*?([0-9]+ [A-Za-z]+ [0-9]{4} at [0-9]+:[0-9]+ [AM|PM]+)");
        setPageUrlPrefix("/page-");

        setForumSizePattern("Messages:[\\S\\s]*?</dt>[\\S\\s]*?<dd>[\\S\\s]*?([0-9,]+)");

        setQuotePattern("<blockquote class=\"quoteContainer\">([\\S\\s]*?)<div class=\"quoteExpand\">[\\S\\s]*?</blockquote>([\\S\\s]*)");

        setForumType(ForumType.MAN_CITY_BLUE_MOON_MCFC);
    }
}