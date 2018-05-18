package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class BournemouthForumScraper extends AbstractScraper {
    public BournemouthForumScraper() {
        setPostPattern("data-lb-caption-desc=\"(.*?)· ([A-Z][a-z]{2} [0-9]+, [0-9]{4} at [0-9]+:[0-9]{2} [PM|AM]{2})" +
                "[\\S\\s]*?<article class=\"message-body.*([\\S\\s]*?)<footer class=\"message-footer\">");
        setLastPagePattern("aria-expanded=\"[true|false]+\" aria-haspopup=\"[true|false]+\"> 1 of ([0-9]+) </a>");
        setPageUrlFormat("page-");

        setGroupIndexes(new int[]{3,1,2});
    }
}
