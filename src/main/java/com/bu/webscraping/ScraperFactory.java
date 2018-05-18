package com.bu.webscraping;

import com.bu.forum.ForumType;
import com.bu.forum.ForumPost;
import com.bu.webscraping.scrapers.*;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class ScraperFactory {
    private static Map<Class, Scraper> scrapers = new LinkedHashMap<>();
    private static Map<ForumType, List<ForumPost>> postMap = new LinkedHashMap<>();
    private static Map<ForumType, Long> threadSizeMap = new LinkedHashMap<>();

    public static Map<ForumType, Long> retrieveTotalForumPostsForAllForums() throws IOException, IllegalAccessException, InstantiationException {
        String[] forumUrls = getURLs("forums.txt");

        if(forumUrls == null)
            return null;

        for (String forumUrl : forumUrls) {
            if (forumUrl.contains("www.redandwhitekop.com"))
                addThreadSizeToMap(ForumType.LIVERPOOL_RED_AND_WHITE, RedAndWhiteScraper.class, forumUrl);
            else if (forumUrl.contains("forum.talkchelsea.net"))
                addThreadSizeToMap(ForumType.CHELSEA_TALK_CHELSEA, TalkChelseaScraper.class, forumUrl);
            else if (forumUrl.contains("forums.bluemoon-mcfc.co.uk"))
                addThreadSizeToMap(ForumType.MAN_CITY_BLUE_MOON_MCFC, BlueMoonMCFCScraper.class, forumUrl);
            else if (forumUrl.contains("arsenal-mania.com"))
                addThreadSizeToMap(ForumType.ARSENAL_ARSENAL_MANIA, ArsenalManiaScraper.class, forumUrl);
            else if (forumUrl.contains("www.cpfc.org"))
                addThreadSizeToMap(ForumType.CRYSTAL_PALACE_CPFC, CPFCScraper.class, forumUrl);
            else if (forumUrl.contains("www.foxestalk.co.uk"))
                addThreadSizeToMap(ForumType.LEICESTER_CITY_FOXES_TALK, FoxesTalkScraper.class, forumUrl);
            else if (forumUrl.contains("www.grandoldteam.com"))
                addThreadSizeToMap(ForumType.EVERTON_GRAND_OLD_TEAM, GrandOldTeamScraper.class, forumUrl);
            else if (forumUrl.contains("bournemouth-forum.vitalfootball.co.uk"))
                addThreadSizeToMap(ForumType.BOURNEMOUTH_BOURNEMOUTH_FORUM, BournemouthForumScraper.class, forumUrl);
            else if (forumUrl.contains("boards.footymad.net"))
                addThreadSizeToMap(ForumType.BURNLEY_FOOTY_MAD, FootyMadScraper.class, forumUrl);
            else
                System.err.println("Site: \"" + forumUrl + "\" could not be found, if this is blank please remove any trailing spaces or commas after the last thread link");
        }

        return threadSizeMap;
    }

    private static void addThreadSizeToMap(ForumType forumType, Class<?> scraperClass, String forumUrl) throws InstantiationException, IllegalAccessException, IOException {
        Scraper scraper = getScraper(scraperClass);

        threadSizeMap.put(forumType, scraper.retrieveForumSize(forumUrl));
    }

    public static Map<ForumType, List<ForumPost>> retrievePostsForAllForums() throws IOException, InstantiationException, IllegalAccessException {
        String[] threadUrls = getURLs("threads.txt");

        if (threadUrls == null)
            return null;

        for (String threadUrl : threadUrls)
            if (threadUrl.contains("www.redandwhitekop.com"))
                addPostsToMap(ForumType.LIVERPOOL_RED_AND_WHITE, RedAndWhiteScraper.class, threadUrl);
            else if (threadUrl.contains("forum.talkchelsea.net"))
                addPostsToMap(ForumType.CHELSEA_TALK_CHELSEA, TalkChelseaScraper.class, threadUrl);
            else if (threadUrl.contains("forums.bluemoon-mcfc.co.uk"))
                addPostsToMap(ForumType.MAN_CITY_BLUE_MOON_MCFC, BlueMoonMCFCScraper.class, threadUrl);
            else if (threadUrl.contains("arsenal-mania.com"))
                addPostsToMap(ForumType.ARSENAL_ARSENAL_MANIA, ArsenalManiaScraper.class, threadUrl);
            else if (threadUrl.contains("www.cpfc.org"))
                addPostsToMap(ForumType.CRYSTAL_PALACE_CPFC, CPFCScraper.class, threadUrl);
            else if (threadUrl.contains("www.foxestalk.co.uk"))
                addPostsToMap(ForumType.LEICESTER_CITY_FOXES_TALK, FoxesTalkScraper.class, threadUrl);
            else if (threadUrl.contains("www.grandoldteam.com"))
                addPostsToMap(ForumType.EVERTON_GRAND_OLD_TEAM, GrandOldTeamScraper.class, threadUrl);
            else if (threadUrl.contains("bournemouth-forum.vitalfootball.co.uk"))
                addPostsToMap(ForumType.BOURNEMOUTH_BOURNEMOUTH_FORUM, BournemouthForumScraper.class, threadUrl);
            else if (threadUrl.contains("boards.footymad.net"))
                addPostsToMap(ForumType.BURNLEY_FOOTY_MAD, FootyMadScraper.class, threadUrl);
            else
                System.err.println("Site: \"" + threadUrl + "\" could not be found, if this is blank please remove any trailing spaces or commas after the last thread link");


        return postMap;
    }

    private static void addPostsToMap(ForumType forumType,Class<?> scraperClass, String threadUrl) throws IllegalAccessException, InstantiationException, IOException {
        Scraper scraper = getScraper(scraperClass);

        List<ForumPost> forumPosts = scraper.retrievePostsForForum(threadUrl);

        postMap.computeIfAbsent(forumType, k -> new LinkedList<>());

        postMap.get(forumType).addAll(forumPosts);
    }

    private static String[] getURLs(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));

        String urls = bufferedReader.readLine();

        if (urls == null)
            return null;

        return urls.split(",");
    }

    private static Scraper getScraper(Class<?> scraperClass) throws IllegalAccessException, InstantiationException {
        Scraper scraper = scrapers.get(scraperClass);

        if (scraper == null) {
            scrapers.put(scraperClass,(Scraper) scraperClass.newInstance());
            return scrapers.get(scraperClass);
        }

        return scraper;
    }
}
