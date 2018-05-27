package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class FansNetworkScraper extends AbstractScraper {
    public FansNetworkScraper() {
        setPostPattern("</b> on ([0-9]+:[0-9]+ - [A-Z][a-z]{2} [0-9]+) with [0-9]+ views</td>[\\S\\s]*? <td valign=\"top\">([\\S\\s]*?)<center>[\\S\\s]*?<a href=\"/football/swanseacity/settings/ignore-member/([\\S\\s]*?)\" title");
        setPageUrlPrefix("/page:");
        setLastPagePatternLong("</li> \\.\\.\\.[\\S\\s]*?li><a href=\"/[\\S\\s]*?/[\\S\\s]*?/[\\S\\s]*?/[\\S\\s]*?/page:([0-9]+)\">");
        setLastPagePatternShort("<li><a href=\"/football/swanseacity/forum/[0-9]+/page:([0-9]+)\">[0-9]+</a></li>");

        setPostGroupIndexes(new int[]{2,3,1});

        setForumSizePattern("white;\">ALL posts</a>&nbsp;\\(([0-9]+)\\)");
    }
}
