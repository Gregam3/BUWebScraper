package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class WFCForumsScraper extends AbstractScraper {
    public WFCForumsScraper() {
        setPostPattern("itemprop=\"name\">([\\S\\s]*?)</a>" +
                "[\\S\\s]*?<div class=\"messageContent\">([\\S\\s]*?)<div class=\"messageMeta" +
                "[\\S\\s]*?<span class=\"DateTime\" title=\"([A-Z][a-z]{2} [0-9]+, [0-9]{4} at [0-9]+:[0-9]+ [AM|PM]+)\"");
        setPageUrlPrefix("page-");
        setPostGroupIndexes(new int[]{2,1,3});

        setForumSizePattern("<dt>[\\S\\s]*?Messages:[\\S\\s]*?</dt>[\\S\\s]*?<dd>[\\S\\s]*?([0-9,]+)[\\S\\s]*?</dd>");
    }
}
