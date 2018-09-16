package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class TalkChelseaScraper extends AbstractScraper {

    public TalkChelseaScraper() {
        setPostPattern("'s profile\" class=\"ipsType_break\">([^<]+)</a>[\\S\\s]*?title=\"([0-9]{2}\\/[0-9]{2}\\/[0-9]{4} [0-9]{2}:[0-9]{2}  [PM|AM]{2})\"" +
                "[\\S\\s]*?<div data-role=\"commentContent\".*>([\\S\\s]*?)<ul class=\"ipsComment_controls ipsClearfix\" data-role=\"commentControls\"> ");
        setLastPagePatternLong("Page 1 of ([0-9]*)");
        setPageUrlPrefix("/?page=");
        setPostGroupIndexes(new int[]{3, 1, 2});

        setForumSizePattern("3px;\">[\\S\\s]*?([0-9,]+)");

        setQuotePattern("<div class=\"ipsQuote_contents\">([\\S\\s]*?)</div>([\\S\\s]*)");

        setForumType(ForumType.CHELSEA_TALK_CHELSEA);
    }
}
