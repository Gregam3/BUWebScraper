package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
@Deprecated
public class ArsenalManiaScraper extends AbstractScraper {
    public ArsenalManiaScraper() {
        setPostPattern("class=\"username\" dir=\"auto\" itemprop=\"name\">([\\S\\s]*?)</a>[\\S\\s]*?" +
                "[\\S\\s]*?<blockquote class=\"messageText.*>([\\S\\s]*?)<div class=\"messageTextEndMarker\">" +
                "[\\S\\s]*?class=\"DateTime\".*([A-Z][a-z]{2} [0-9]+, [0-9]{4} at [0-9]+:[0-9]+ [AM|PM]+)");
        setPageUrlPrefix("/page-");
        setForumSizePattern("<h3>Forum Statistics</h3>[\\S\\s]*?Messages:[\\S\\s]*?</dt>[\\S\\s]*?<dd>[\\S\\s]*?([0-9,]+)");

        setPostGroupIndexes(new int[]{2, 1, 3});

        setQuotePattern("<blockquote class=\"quoteContainer\">([\\S\\s]*?)<div class=\"quoteExpand\">" +
                "[\\S\\s]*?</blockquote>[\\S\\s]*?</div>([\\S\\s]*)");

        setForumType(ForumType.ARSENAL_ARSENAL_MANIA);
    }
}