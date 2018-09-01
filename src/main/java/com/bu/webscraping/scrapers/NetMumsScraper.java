package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class NetMumsScraper extends AbstractScraper {
    public NetMumsScraper() {
        setPostPattern("<span class=\"postdate old\"> <span class=\"date\">([\\S\\s]*?)</span>" +
                "[\\S\\s]*?<div class=\"username.*\">([\\S\\s]*?)(?:</div>|</strong>)" +
                "[\\S\\s]*?<div class=\"content\">([\\S\\s]*?)<div class=\"postfoot\">");
        setQuotePattern("<div class=\"message\">([\\S\\s]*?)</div>([\\S\\s]*)");
        setPageUrlPrefix("-");
        setPageUrlSuffix(".html");
        setPostGroupIndexes(new int[]{3,2,1});
        setRequiresHTMLExtension(true);
    }
}
