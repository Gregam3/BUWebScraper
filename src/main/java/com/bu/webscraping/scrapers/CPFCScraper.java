package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class CPFCScraper extends AbstractScraper {
    public CPFCScraper() {
        setPostPattern("></a> ([0-9]+-[0-9]{2}-[0-9]{4}, [0-9]{2}:[0-9]{2} [AM|PM])" +
                "[\\S\\s]*?<div id=\"post_message_[0-9]+\">([\\S\\s])*?</div>" +
                "[\\S\\s]*?Find all posts by ([\\S\\s])*?</a>");
        setPageUrlFormat("&page=2");
    }
}
