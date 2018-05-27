package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class ToontasticScraper extends AbstractScraper {
    public ToontasticScraper() {
        setPostPattern("title=\"Go to ([\\S\\s]*?)'s profile" +
                "[\\S\\s]*?>Posted <time datetime=\"([0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2})Z\"" +
                "[\\S\\s]*?<div data-role=\"commentContent\" class=\"ipsType_normal ipsType_richText ipsContained\" data-controller=\"core.front.core.lightboxedImages\">([\\S\\s]*?)<div class=\"ipsItemControls\">");
        setPageUrlPrefix("/?page=");
        setPostGroupIndexes(new int[]{3,1,2});

        setForumSizePattern("<dt class=\"ipsDataItem_stats_number\">[\\S\\s]*?([0-9,]+)[\\S\\s]*?</dt>");
    }
}
