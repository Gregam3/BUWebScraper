package com.bu.webscraping.scrapers;

import com.bu.forum.ForumPost;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class BlueMoonMCFCScraper extends AbstractScraper {

    public BlueMoonMCFCScraper() {
        setPostPattern("<blockquote.*>([\\S\\s]*?)<div class=\\\"messageTextEndMarker\\\">[\\S\\s]*?dir=\"auto\">([^<]+)[\\S\\s]*?<span class=\"DateTime\" title=\"([^\"]+)");
        setLastPagePattern("Page 1 of ([0-9]*)");
        this.pageUrlFormat = "page-";
    }
}