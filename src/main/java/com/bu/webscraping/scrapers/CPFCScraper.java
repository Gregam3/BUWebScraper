package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class CPFCScraper extends AbstractScraper {
    public CPFCScraper() {
        setPostPattern("></a> ([0-9]+-[0-9]{2}-[0-9]{4}, [0-9]{2}:[0-9]{2} [AM|PM])" +
                "[\\S\\s]*?<div id=\"post_message_[0-9]+\">([\\S\\s])*?</div>" +
                "[\\S\\s]*?Find all posts by ([\\S\\s])*?</a>");
        setPageUrlPrefix("&page=2");

        setForumSizePattern("<td class=\"alt2\">([0-9,]+)</td>");

        setForumType(ForumType.CRYSTAL_PALACE_CPFC);
    }
}
