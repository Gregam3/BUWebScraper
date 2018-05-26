package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class WestBromScraper extends AbstractScraper {
    public WestBromScraper() {
        this.pagePathVariableIncrement = 25;
        this.pagePathVariableStart = 0;
        setPostPattern("title=\"View the profile of ([^\"]+)[\\S\\s]*?<strong>.*</strong> (.* [0-9]+:[0-9]+:[0-9]+ [AM|PM]{2})[\\S\\s]*?msg_[0-9]+\">([\\S\\s]*?)<div class=\"moderatorbar\"> ");
        setLastPagePattern("([0-9]+)</a> &nbsp;&nbsp;\n");
        setPageUrlPrefix(".");
        setPostGroupIndexes(new int[] {3,1,2});

        setLastPagePattern("topic=[0-9]+.[0-9]+\">([0-9]+)</a> \\|");
    }
}
