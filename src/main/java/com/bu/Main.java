package com.bu;

import com.bu.forum.Club;
import com.bu.forum.ForumPost;
import com.bu.result.ResultWriter;
import com.bu.webscraping.scrapers.PixelExitScraper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<Club, List<ForumPost>> resultsMap = new LinkedHashMap<>();

        resultsMap.put(Club.MAN_CITY, new PixelExitScraper().retrieveAllPosts("https://forums.bluemoon-mcfc.co.uk/threads/fabian-delph-2017-18.330191/"));

        ResultWriter resultWriter = new ResultWriter();

        resultWriter.writeToCSV(resultsMap);
    }
}
