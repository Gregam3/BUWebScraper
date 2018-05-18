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
    private static Map<ForumType, Scraper> scrapers = new LinkedHashMap<>();
    private static Map<ForumType, List<ForumPost>> resultsMap = new LinkedHashMap<>();

    public static Map<ForumType, List<ForumPost>> retrievePostsForAllForums() throws IOException, InstantiationException, IllegalAccessException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("threads.txt")));

        String[] threadUrls = bufferedReader.readLine().split(",");

        for (String threadUrl : threadUrls) {
            if (threadUrl.contains("www.redandwhitekop.com"))
                addResults(ForumType.LIVERPOOL_RED_AND_WHITE, RedAndWhiteScraper.class, threadUrl);
            else if (threadUrl.contains("forum.talkchelsea.net"))
                addResults(ForumType.CHELSEA_TALK_CHELSEA, TalkChelseaScraper.class, threadUrl);
            else if (threadUrl.contains("forums.bluemoon-mcfc.co.uk"))
                addResults(ForumType.MAN_CITY_BLUE_MOON_MCFC, BlueMoonMCFCScraper.class, threadUrl);
            else if (threadUrl.contains("arsenal-mania.com"))
                addResults(ForumType.ARSENAL_ARSENAL_MANIA, ArsenalManiaScraper.class, threadUrl);
            else if (threadUrl.contains("www.cpfc.org"))
                addResults(ForumType.CRYSTAL_PALACE_CPFC, CPFCScraper.class, threadUrl);
            else if (threadUrl.contains("www.foxestalk.co.uk"))
                addResults(ForumType.LEICESTER_CITY_FOXES_TALK, FoxesTalkScraper.class, threadUrl);
            else if (threadUrl.contains("www.grandoldteam.com"))
                addResults(ForumType.EVERTON_GRAND_OLD_TEAM, GrandOldTeamScraper.class, threadUrl);
            else if (threadUrl.contains("bournemouth-forum.vitalfootball.co.uk"))
                addResults(ForumType.BOURNEMOUTH_BOURNEMOUTH_FORUM, BournemouthForumScraper.class, threadUrl);
            else if (threadUrl.contains("boards.footymad.net"))
                addResults(ForumType.BURNLEY_FOOTY_MAD, FootyMadScraper.class, threadUrl);
            else
                System.err.println("Site: \"" + threadUrl + "\" could not be found, if this is blank please remove any trailing spaces or commas after the last thread link");
        }

        return resultsMap;
    }

    private static void addResults(ForumType forumType, Class<?> clazz, String threadUrl) throws IllegalAccessException, InstantiationException, IOException {
        Scraper scraper = scrapers.get(forumType);

        if (scraper == null) {
            scrapers.put(forumType, (Scraper) clazz.newInstance());
            scraper = scrapers.get(forumType);
        }

        List<ForumPost> forumPosts = scraper.retrievePostsForForum(threadUrl);

        resultsMap.computeIfAbsent(forumType, k -> new LinkedList<>());

        resultsMap.get(forumType).addAll(forumPosts);
    }
}
