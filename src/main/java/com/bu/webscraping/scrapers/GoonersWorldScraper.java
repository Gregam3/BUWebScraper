package com.bu.webscraping.scrapers;

import com.bu.webscraping.ScraperFactory;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class GoonersWorldScraper extends AbstractScraper {
    public GoonersWorldScraper() {
        setPostPattern("class=\"username-coloured\">([\\S\\s]*?)</a>[\\S\\s]*? » ([\\S\\s]*?)</p>[\\S\\s]*?<div class=\"content\">([\\S\\s]*?) <dl class=\"postprofile\"");
        setQuotePattern("<blockquote.*>([\\S\\s]*?)</blockquote>([^<blockquote].*)");
        setForumSizePattern("<dd class=\"posts\">[\\S\\s]*?([0-9]+)[\\S\\s]*?<dfn>Posts</dfn>");

        setPageUrlPrefix("&start=");

        setPagePathVariableIncrement(15);
        setPagePathVariableStart(0);
        setLastPagePatternLong("Page <strong>1</strong> of <strong>([0-9]+)</strong>");

        setPostGroupIndexes(new int[]{3,1,2});
    }
}
