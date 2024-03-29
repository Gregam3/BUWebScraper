package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class RedCafeScraper extends AbstractScraper {
    public RedCafeScraper() {
        setPostPattern("<span class=\"item\"><span class=\"DateTime\"[\\S\\s]*?([A-Z][a-z]{2} [0-9]+, [0-9]{4} at [0-9]+:[0-9]{2})\"" +
                "[\\S\\s]*?members/([\\S\\s]*?)\\.[0-9]+/\" class=\"username" +
                "[\\S\\s]*?<div class=\"messageContent\">([\\S\\s]*?)</div> </li>");
        setPageUrlPrefix("/page-");
        setPostGroupIndexes(new int[]{3,2,1});

        setQuotePattern("<blockquote class=\"quoteContainer\">([\\S\\s]*?)<div class=\"quoteExpand\">" +
                "[\\S\\s]*?</blockquote>([\\S\\s]*)");

        setForumType(ForumType.MANCHESTER_UNITED_RED_CAFE);
    }
}