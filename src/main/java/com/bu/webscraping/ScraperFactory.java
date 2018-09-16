package com.bu.webscraping;

import com.bu.forum.ForumType;
import com.bu.forum.ForumPost;
import com.bu.webscraping.scrapers.*;

import java.io.*;
import java.util.*;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class ScraperFactory {
    private static Map<Class, Scraper> scrapers = new LinkedHashMap<>();
    private static Map<ForumType, List<ForumPost>> postMap = new LinkedHashMap<>();
    private static Map<ForumType, Long> threadSizeMap = new LinkedHashMap<>();
    private static Map<String, Class> urlToClassMap = new HashMap<>();

    public static void populateUrlToClassMap() {
        urlToClassMap.put("www.redandwhitekop.com", RedAndWhiteScraper.class);
        urlToClassMap.put("forum.talkchelsea.net", TalkChelseaScraper.class);
        urlToClassMap.put("forums.bluemoon-mcfc.co.uk", BlueMoonMCFCScraper.class);
        urlToClassMap.put("arsenal-mania.com", ArsenalManiaScraper.class);
        urlToClassMap.put("www.cpfc.org", CPFCScraper.class);
        urlToClassMap.put("www.foxestalk.co.uk", FoxesTalkScraper.class);
        urlToClassMap.put("www.grandoldteam.com", GrandOldTeamScraper.class);
        urlToClassMap.put("bournemouth-forum.vitalfootball.co.uk", BournemouthForumScraper.class);
        urlToClassMap.put("boards.footymad.net", FootyMadScraper.class);
        urlToClassMap.put("lfcreds.com", LFCRedsScraper.class);
        urlToClassMap.put("www.redcafe.net", RedCafeScraper.class);
        urlToClassMap.put("www.spurscommunity.co.uk", SpursCommunityScraper.class);
        urlToClassMap.put("www.toontastic.net", ToontasticScraper.class);
        urlToClassMap.put("westbrom.com", WestBromScraper.class);
        urlToClassMap.put("oatcakefanzine.proboards.com", OatcakeFanzineScraper.class);
        urlToClassMap.put("www.holmesdale.net", HolmesDaleScraper.class);
        urlToClassMap.put("www.saintsweb.co.uk", SaintsWebScraper.class);
        urlToClassMap.put("wfcforums.com", WFCForumsScraper.class);
        urlToClassMap.put("www.fansnetwork.co.uk", FansNetworkScraper.class);
        urlToClassMap.put("www.goonersworld.co.uk", GoonersWorldScraper.class);
        urlToClassMap.put("forums.liverpoolfc.com", LiverpoolFCScraper.class);
        urlToClassMap.put("www.mumsnet.com", MumsNetScraper.class);
        urlToClassMap.put("www.netmums.com", NetMumsScraper.class);
        urlToClassMap.put("www.westhamonline.net", WestHamOnlineScraper.class);
    }

    public static Map<ForumType, Long> retrieveForumSizes() throws IOException, IllegalAccessException, InstantiationException {
        String[] forumUrls = getURLs("forums.txt");

        if (forumUrls == null)
            return null;

        String currentUrl = null;

        for (String forumUrl : forumUrls) {
            for (String url : urlToClassMap.keySet())
                if (forumUrl.contains(url) && !forumUrl.contains("redcafe.net") && !forumUrl.contains("westhamonline.net")) {
                    currentUrl = url;
                    addThreadSizeToMap(urlToClassMap.get(url), forumUrl);
                    break;
                }

            if (currentUrl == null)
                System.err.println("Site: \"" + forumUrl + "\" could not be found, if this is blank please remove any trailing spaces or semi-colons after the last thread link");

            currentUrl = null;
        }

        return threadSizeMap;
    }

    private static void addThreadSizeToMap(Class<?> scraperClass, String forumUrl) throws InstantiationException, IllegalAccessException, IOException {
        Scraper scraper = getScraper(scraperClass);

        threadSizeMap.put(scraper.getForumType(), scraper.retrieveForumSize(forumUrl));
    }

    public static Map<ForumType, List<ForumPost>> retrievePosts() throws IOException, InstantiationException, IllegalAccessException {
        String[] threadUrls = getURLs("threads.txt");

        if (threadUrls == null)
            return null;

        String currentUrl = null;

        for (String threadUrl : threadUrls) {
            if (threadUrl.charAt(threadUrl.length() - 1) == '/')
                threadUrl = threadUrl.substring(0, threadUrl.length() - 1);

            for (String url : urlToClassMap.keySet())
                if (threadUrl.contains(url)) {
                    currentUrl = url;
                    addPostsToMap(urlToClassMap.get(url), threadUrl);
                    break;
                }

            if (currentUrl == null)
                System.err.println("Site: \"" + threadUrl + "\" could not be found, if this is blank please remove any trailing spaces or semi-colons after the last thread link");

            currentUrl = null;
        }

        return postMap;
    }

    private static void addPostsToMap(Class<?> scraperClass, String threadUrl) throws IllegalAccessException, InstantiationException, IOException {
        Scraper scraper = getScraper(scraperClass);

        List<ForumPost> forumPosts = scraper.retrievePostsForThread(threadUrl);

        postMap.computeIfAbsent(scraper.getForumType(), k -> new LinkedList<>());

        postMap.get(scraper.getForumType()).addAll(forumPosts);
    }

    private static String[] getURLs(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));

        String urls = bufferedReader.readLine();

        if (urls == null)
            return null;

        return urls.split(";");
    }

    private static Scraper getScraper(Class<?> scraperClass) throws IllegalAccessException, InstantiationException {
        Scraper scraper = scrapers.get(scraperClass);

        if (scraper == null) {
            scrapers.put(scraperClass, (Scraper) scraperClass.newInstance());
            return scrapers.get(scraperClass);
        }

        return scraper;
    }
}
