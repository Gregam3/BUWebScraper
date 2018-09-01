package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class WestBromScraper extends AbstractScraper {
    public WestBromScraper() {
        this.pageIncrement = 25;
        this.pageStartIndex = 0;
        setPostPattern("title=\"View the profile of ([^\"]+)[\\S\\s]*?<strong>.*</strong> (.* [0-9]+:[0-9]+:[0-9]+ [AM|PM]{2})[\\S\\s]*?msg_[0-9]+\">([\\S\\s]*?)<div class=\"moderatorbar\"> ");
        setLastPagePatternLong("([0-9]+)</a> &nbsp;&nbsp;\n");
        setPageUrlPrefix(".");
        setPostGroupIndexes(new int[] {3,1,2});

        setLastPagePatternLong("topic=[0-9]+.[0-9]+\">([0-9]+)</a> \\|");
        setLastPagePatternShort(";topic=[0-9]+\\.[0-9]+\">([0-9]+)</a> ");

        setForumSizePattern("<td class=\"stats windowbg\"> <p>([0-9]+) Posts <br> [0-9]+ Topics </p> </td> ");

        setQuotePattern("<blockquote class=\"bbc_standard_quote\">([\\S\\s]*?)</blockquote>([\\S\\s]*)");
    }
}
