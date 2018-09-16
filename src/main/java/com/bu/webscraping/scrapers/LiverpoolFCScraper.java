package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class LiverpoolFCScraper extends AbstractScraper {
    public LiverpoolFCScraper() {
        setPostPattern("<li class=\"optionlabel\">([\\S\\s]*?)</li>" +
                "[\\S\\s]*?<span class=\"date\">([\\S\\s]*?)</span>" +
                "[\\S\\s]*?<div class=\"content\">([\\S\\s]*?)</blockquote>");
        setPageUrlPrefix("/page");
        setPostGroupIndexes(new int[]{3,1,2});
        setQuotePattern("<div class=\"message\">([\\S\\s]*?)</div>([\\S\\s]*)");
        setForumSizePattern("<td class=\"alt1 stats\">Threads: [0-9,]+<br> Replies: ([0-9,]+)</td>");

        setForumType(ForumType.LIVERPOOL_FC);
    }
}
