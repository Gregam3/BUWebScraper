package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class FoxesTalkScraper extends AbstractScraper {
    public FoxesTalkScraper() {
        setPostPattern("title=\"([0-9]+/[0-9]{2}/[0-9]{2} [0-9]+:[0-9]{2})[\\S\\s]*?data-ipsquote-username=\"([\\S\\s]*?)\">[\\S\\s]*?<div data-role=\"commentContent\".*?>([\\S\\s]*?)<div class=\"ipsItemControls\">");
        setPageUrlPrefix("?page=");
        setGroupIndexes(new int[]{3, 2, 1});

        setForumSizePattern("<dt class=\"ipsDataItem_stats_number\">[\\S\\s]*?([0-9,]+)[\\S\\s]*?</dt>");
    }
}
