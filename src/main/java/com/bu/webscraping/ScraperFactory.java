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
            else if (forumUrl.contains("lfcreds.com"))
                addThreadSizeToMap(ForumType.LIVERPOOL_LFC_REDS, LFCRedsScraper.class, forumUrl);
            else if (forumUrl.contains("www.redcafe.net"))
                System.err.println("www.redcafe.net is not compatible with the forum size functionality, as such it will be omitted.");
            else if (forumUrl.contains("www.spurscommunity.co.uk"))
                addThreadSizeToMap(ForumType.TOTTENHAM_HOTSPURS_SPURS_COMMUNITY, SpursCommunityScraper.class, forumUrl);
            else if (forumUrl.contains("www.toontastic.net"))
                addThreadSizeToMap(ForumType.NEWCASTLE_TOONTASTIC, ToontasticScraper.class, forumUrl);
            else if (forumUrl.contains("westbrom.com"))
                addThreadSizeToMap(ForumType.WEST_BROMICH_ALBION_WEST_BROM, WestBromScraper.class, forumUrl);
            else if (forumUrl.contains("www.westhamonline.net"))
                throw new AssertionError("www.westhamonline.net is not compatible with forum size functionality, please remove it from forums.txt.");
            else if (forumUrl.contains("oatcakefanzine.proboards.com"))
                addThreadSizeToMap(ForumType.STOKE_CITY_OAKCAKE_FANZINE, OatcakeFanzineScraper.class, forumUrl);
            else if (forumUrl.contains("www.holmesdale.net"))
                addThreadSizeToMap(ForumType.CRYSTAL_PALACE_HOLMES_DALES, HolmesDaleScraper.class, forumUrl);
            else if (forumUrl.contains("www.saintsweb.co.uk"))
                addThreadSizeToMap(ForumType.SOUTHAMPTON_SAINTS_WEBB, SaintsWebScraper.class, forumUrl);
            else if (forumUrl.contains("wfcforums.com"))
                addThreadSizeToMap(ForumType.WATFORD_WFC_FORUMS, WFCForumsScraper.class, forumUrl);
            else if (forumUrl.contains("www.fansnetwork.co.uk"))
                addThreadSizeToMap(ForumType.SWANSEA_CITY_FANS_NETWORK, FansNetworkScraper.class, forumUrl);
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

        for (String threadUrl : threadUrls) {
            if(threadUrl.charAt(threadUrl.length() - 1) == '/')
                threadUrl = threadUrl.substring(0 , threadUrl.length() - 1);

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
            else if (threadUrl.contains("lfcreds.com"))
                addPostsToMap(ForumType.LIVERPOOL_LFC_REDS, LFCRedsScraper.class, threadUrl);
            else if (threadUrl.contains("www.redcafe.net"))
                addPostsToMap(ForumType.MANCHESTER_UNITED_RED_CAFE, RedCafeScraper.class, threadUrl);
            else if (threadUrl.contains("www.spurscommunity.co.uk"))
                addPostsToMap(ForumType.TOTTENHAM_HOTSPURS_SPURS_COMMUNITY, SpursCommunityScraper.class, threadUrl);
            else if (threadUrl.contains("www.toontastic.net"))
                addPostsToMap(ForumType.NEWCASTLE_TOONTASTIC, ToontasticScraper.class, threadUrl);
            else if (threadUrl.contains("westbrom.com"))
                addPostsToMap(ForumType.WEST_BROMICH_ALBION_WEST_BROM, WestBromScraper.class, threadUrl);
            else if (threadUrl.contains("www.westhamonline.net"))
                addPostsToMap(ForumType.WEST_HAM_WEST_HAM_ONLINE, WestHamOnlineScraper.class, threadUrl);
            else if (threadUrl.contains("oatcakefanzine.proboards.com"))
                addPostsToMap(ForumType.STOKE_CITY_OAKCAKE_FANZINE, OatcakeFanzineScraper.class, threadUrl);
            else if (threadUrl.contains("www.holmesdale.net"))
                addPostsToMap(ForumType.CRYSTAL_PALACE_HOLMES_DALES, HolmesDaleScraper.class, threadUrl);
            else if (threadUrl.contains("www.saintsweb.co.uk"))
                addPostsToMap(ForumType.SOUTHAMPTON_SAINTS_WEBB, SaintsWebScraper.class, threadUrl);
            else if (threadUrl.contains("wfcforums.com"))
                addPostsToMap(ForumType.WATFORD_WFC_FORUMS, WFCForumsScraper.class, threadUrl);
            else if (threadUrl.contains("www.fansnetwork.co.uk"))
                addPostsToMap(ForumType.SWANSEA_CITY_FANS_NETWORK, FansNetworkScraper.class, threadUrl);
            else
                System.err.println("Site: \"" + threadUrl + "\" could not be found, if this is blank please remove any trailing spaces or commas after the last thread link");
        }

        return postMap;
    }

    private static void addPostsToMap(ForumType forumType,Class<?> scraperClass, String threadUrl) throws IllegalAccessException, InstantiationException, IOException {
        Scraper scraper = getScraper(scraperClass);

        List<ForumPost> forumPosts = scraper.retrievePostsForThread(threadUrl);

        postMap.computeIfAbsent(forumType, k -> new LinkedList<>());

        postMap.get(forumType).addAll(forumPosts);
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
            scrapers.put(scraperClass,(Scraper) scraperClass.newInstance());
            return scrapers.get(scraperClass);
        }

        return scraper;
    }
}
