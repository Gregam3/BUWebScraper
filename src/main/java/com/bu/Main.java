package com.bu;

import com.bu.forum.Club;
import com.bu.forum.ForumPost;
import com.bu.result.ResultWriter;
import com.bu.webscraping.scrapers.RedAndWhiteScraper;
import com.bu.webscraping.scrapers.TalkChelseaScraper;
import com.bu.webscraping.scrapers.BlueMoonMCFCScraper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static int siteNumber = 1;
    public static int cumulativePageCount = 1;

    public static void main(String[] args) throws IOException {
        Map<Club, List<ForumPost>> resultsMap = new LinkedHashMap<>();

        resultsMap.put(Club.LIVERPOOL_RED_AND_WHITE,
                new RedAndWhiteScraper().retrieveAllPosts("https://www.redandwhitekop.com/forum/index.php?topic=315580"));
        resultsMap.put(Club.CHELSEA_TALK_CHELSEA,
                new TalkChelseaScraper().retrieveAllPosts("http://forum.talkchelsea.net/topic/18530-how-to-watch-hd-streams/"));
        resultsMap.put(Club.MAN_CITY_BLUE_MOON_MCFC,
                new BlueMoonMCFCScraper().retrieveAllPosts("https://forums.bluemoon-mcfc.co.uk/threads/fabian-delph-2017-18.330191/"));

        ResultWriter resultWriter = new ResultWriter();

        resultWriter.writeToCSV(resultsMap);
    }
}
