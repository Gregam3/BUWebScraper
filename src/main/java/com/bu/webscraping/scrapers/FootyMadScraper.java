package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class FootyMadScraper extends AbstractScraper {
    public FootyMadScraper() {
        setPostPattern("<span class=\"date\">([0-9]{2}-[0-9]{2}-[0-9]{4}.*?<span class=\"time\">[0-9]+:[0-9]+ [PM|AM]+)[\\S\\s]*?line.png\" alt=\"([\\S\\s]*?) is [off|on][\\S\\s]*?<blockquote class=\"postcontent.*?>([\\S\\s]*?)</blockquote>");
        setPageUrlPrefix("&page=");
        setPostGroupIndexes(new int[]{3,2,1});

        setForumSizePattern("<li>Posts: ([0-9,]+)</li>");

        setQuotePattern("<div class=\"message\">([\\S\\s]*?)</div>([\\S\\s]*)");

        setForumType(ForumType.BURNLEY_FOOTY_MAD);
    }
}
