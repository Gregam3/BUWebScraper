package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class OatcakeFanzineScraper extends AbstractScraper {
    public OatcakeFanzineScraper() {
        setPostPattern("Post by ([\\S\\s]*?) on <abbr" +
                "[\\S\\s]*?title=\"([A-Z][a-z]{2} [0-9]+, [0-9]{4} [0-9]+:[0-9]+:[0-9]+ [GMT|BST]+)" +
                "[\\S\\s]*?<div class=\"message\">([\\S\\s]*?)</tr>");
        setLastPagePatternLong("<div onclick=\"var page=prompt[\\S\\s]*?\">([0-9]+)</a></li>");
        setLastPagePatternShort("<li.*><a.*>([0-9]+)</a></li>");
        setPageUrlPrefix("?page=");

        setPostGroupIndexes(new int[]{3,1,2});

        setForumSizePattern("<td class=\"posts\">([0-9,]+)</td> ");

        setQuotePattern("<div class=\"quote_header\">([\\S\\s]*?)<div class=\"quote_clear\">([\\S\\s]*)");

        setForumType(ForumType.STOKE_CITY_OAKCAKE_FANZINE);
    }
}