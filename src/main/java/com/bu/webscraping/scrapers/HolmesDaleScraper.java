package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class HolmesDaleScraper extends AbstractScraper {
    public HolmesDaleScraper() {
        setPostPattern("</span> <b>([0-9]{2} [A-Z][a-z]{2} [0-9]+ [0-9]+\\.[0-9]{2}[am|pm]+)</b></th>" +
                "[\\S\\s]*?Add ([\\S\\s]*?) as a friend" +
                "[\\S\\s]*?<td colspan=\"2\"> <p>([\\S\\s]*?)</td>");
        setPageUrlPrefix("&page=");
        setGroupIndexes(new int[]{3,2,1});
    }
}
