package com.bu.webscraping;

import com.bu.forum.ForumType;
import com.bu.webscraping.scrapers.Scraper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class ScraperFactory {
    public static Map<ForumType, Scraper> scrapers = new HashMap<ForumType, Scraper>();

    public void initiliaseScrapers() {
        //TODO initialise all scrapers here
    }
}
