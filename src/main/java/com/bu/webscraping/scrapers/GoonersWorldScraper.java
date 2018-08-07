package com.bu.webscraping.scrapers;

import com.bu.webscraping.ScraperFactory;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class GoonersWorldScraper extends AbstractScraper {
    public GoonersWorldScraper() {
        setPostPattern("class=\"username-coloured\">([\\S\\s]*?)</a></strong> » " +
                "([\\S\\s]*?)</p>[\\S\\s]*?<div class=\"content\">" +
                "([\\S\\s]*?)<div id=");

        setPageUrlPrefix("&start=");

        setPagePathVariableIncrement(15);
        setPagePathVariableStart(0);

        setPostGroupIndexes(new int[]{3,2,1});
    }
}
