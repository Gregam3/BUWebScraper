package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class BlueMoonMCFCScraper extends AbstractScraper {

    public BlueMoonMCFCScraper() {
        setPostPattern("<blockquote.*>([\\S\\s]*?)<div class=\\\"messageTextEndMarker\\\">" +
                "[\\S\\s]*?dir=\"auto\">([^<]+)[\\S\\s]*?" +
                "<span class=\"DateTime\" title=\"([^\"]+)");
        setPageUrlFormat("page-");
    }
}