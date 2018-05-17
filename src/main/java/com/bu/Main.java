package com.bu;

import com.bu.result.ResultWriter;
import com.bu.webscraping.ScraperFactory;

import java.io.IOException;

public class Main {

    public static int siteNumber = 1;
    public static int cumulativePageCount = 1;

    public static void main(String[] args) throws IOException {
        ResultWriter resultWriter = new ResultWriter();

        try {
            resultWriter.writeToCSV(ScraperFactory.retrievePostsForAllForums());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
