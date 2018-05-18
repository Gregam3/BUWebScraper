package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class GrandOldTeamScraper extends AbstractScraper {
    public GrandOldTeamScraper() {
        setPostPattern("class=\"username\" dir=\"auto\" itemprop=\"name\">([\\S\\s]*?)</a>" +
                "[\\S\\s]*?<blockquote class=\"messageText.*>([\\S\\s]*?)<div class=\"messageTextEndMarker\">" +
                "[\\S\\s]*?>([A-Za-z]{3} [0-9]+, [0-9]{4} at [0-9]+:[0-9]{2} [PM|AM]{2})</abbr>");
        setPageUrlFormat("page-");
        setGroupIndexes(new int[]{2, 1, 3});

        setForumSizePattern("Messages:[\\S\\s]*?</dt>[\\S\\s]*?<dd>[\\S\\s]*?([0-9,]+)");
    }
}
