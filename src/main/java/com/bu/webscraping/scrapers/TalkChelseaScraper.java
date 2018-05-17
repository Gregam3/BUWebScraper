package com.bu.webscraping.scrapers;

import com.bu.forum.ForumPost;

import java.io.IOException;
import java.util.List;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class TalkChelseaScraper extends AbstractScraper{

    public TalkChelseaScraper() {
        setPostPattern("'s profile\" class=\"ipsType_break\">([^<]+)</a>" +
                "[\\S\\s]*?title=\"([0-9]{2}\\/[0-9]{2}\\/[0-9]{4} [0-9]{2}:[0-9]{2}  [PM|AM]{2})\" " +
                "data-short[\\S\\s]*?<div data-role=\"commentContent\".*>([\\S\\s]*?)</div>");
        setLastPagePattern("Page 1 of ([0-9]*)");
        this.pageUrlFormat = "?page=";

        groupIndexes = new int[]{3, 1, 2};
    }
}
