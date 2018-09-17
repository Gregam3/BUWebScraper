package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class MumsNetScraper extends AbstractScraper {
    public MumsNetScraper() {
        setPostPattern("<span class=\"nickname\".*\"nick\">([\\S\\s]*?)</" +
                "[\\S\\s]*?<span class=\"post_time\">([\\S\\s]*?)</span>" +
                "[\\S\\s]*?<div class=\"talk-post  message\">([\\S\\s]*?)</div>");
        setPageUrlPrefix("?pg=");
        setPostGroupIndexes(new int[]{3,1,2});

        setForumType(ForumType.MUMS_NET);
    }
}
