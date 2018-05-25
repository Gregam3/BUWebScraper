package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class ArsenalManiaScraper extends AbstractScraper {
    public ArsenalManiaScraper() {
        setPostPattern("class=\"username\" dir=\"auto\" itemprop=\"name\">([\\S\\s])*?</a>" +
                "[\\S\\s]*?<blockquote class=\"messageText.*>([\\S\\s]*?)<div class=\"messageTextEndMarker\">" +
                "[\\S\\s]*?<span class=\"DateTime\" title=\"([A-Za-z]+ [0-9]+, [0-9]{4} at [0-9]+:[0-9]+ [AM|PM]+)");
        setPageUrlPrefix("page-");
        setForumSizePattern("<h3>Forum Statistics</h3>[\\S\\s]*?Messages:[\\S\\s]*?</dt>[\\S\\s]*?<dd>[\\S\\s]*?([0-9,]+)");

        setGroupIndexes(new int[]{2, 1, 3});
    }
}
