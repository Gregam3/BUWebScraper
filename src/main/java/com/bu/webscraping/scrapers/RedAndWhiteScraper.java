package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class RedAndWhiteScraper extends AbstractScraper {
    public RedAndWhiteScraper() {
        this.pageIncrement = 40;
        this.pageStartIndex = 0;
        setPostPattern("title=\"View the profile of ([^\"]+)[\\S\\s]*?<strong>.*</strong> ([A-Za-z]+ [0-9]+, [0-9]{4}, [0-9]+:[0-9]+:[0-9]+ [AM|PM]{2})[\\S\\s]*?msg_[0-9]+\">([\\S\\s]*?)<div class=\"moderatorbar\"> ");
        setLastPagePatternLong("([0-9]+)</a> &nbsp;&nbsp;\n");
        setPageUrlPrefix(".");
        setPostGroupIndexes(new int[] {3,1,2});

        setForumSizePattern("<td class=\"stats windowbg\"> <p>([0-9,]+) Posts <br>");
    }
}
