package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class FootyMadScraper extends AbstractScraper {
    public FootyMadScraper() {
        setPostPattern("<span class=\"date\">(13-05-2018.*?<span class=\"time\">[0-9]+:[0-9]+ [PM|AM]+)" +
                "[\\S\\s]*?line.png\" alt=\"([\\S\\s]*?) is offline" +
                "[\\S\\s]*?<blockquote class=\"postcontent.*?>([\\S\\s]*?)</blockquote>");
        setPageUrlFormat("&page=");
        setGroupIndexes(new int[]{3,2,1});
    }
}
