package com.bu.webscraping.scrapers;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class OatcakeFanzineScraper extends AbstractScraper {
    public OatcakeFanzineScraper() {
        setPostPattern("Post by ([\\S\\s]*?) on <abbr" +
                "[\\S\\s]*?title=\"([A-Z][a-z]{2} [0-9]+, [0-9]{4} [0-9]+:[0-9]+:[0-9]+ [GMT|BST]+)" +
                "[\\S\\s]*?<div class=\"message\">([\\S\\s]*?)</tr>");
        setLastPagePattern("<div onclick=\"var page=prompt[\\S\\s]*?\">([0-9]+)</a></li>");
        setPageUrlPrefix("?page=");

        setGroupIndexes(new int[]{3,1,2});
    }
}