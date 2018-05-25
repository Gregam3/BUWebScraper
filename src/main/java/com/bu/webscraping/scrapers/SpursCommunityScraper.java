package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class SpursCommunityScraper extends AbstractScraper {
    public SpursCommunityScraper() {
        setPostPattern("data-lb-caption-desc=\"([\\S\\s]*?) · ([A-Z][a-z]{2} [0-9]+, [0-9]{4} at [0-9]+:[0-9]{2} [PM|AM]+)\">" +
                "[\\S\\s]*?<div class=\"bbWrapper\">([\\S\\s]*?)<footer class=\"message-footer\"> ");
        setLastPagePattern("ria-expanded=\"false\" aria-haspopup=\"true\"> 1 of ([0-9]+)");
        setPageUrlPrefix("page-");

        setGroupIndexes(new int[]{3,1,2});
    }
}
