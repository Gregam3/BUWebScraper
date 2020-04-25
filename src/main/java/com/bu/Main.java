package com.bu;

import com.bu.result.ResultWriter;
import com.bu.webscraping.ScraperFactory;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static int siteNumber = 1;
    public static int cumulativePageCount = 1;

    public static void main(String[] args) throws IOException {
        ResultWriter resultWriter = new ResultWriter();

        try {
            ScraperFactory.populateUrlToClassMap();
            resultWriter.writePostsToCSV(ScraperFactory.retrievePosts());
            resultWriter.writeForumSizeToTxt(ScraperFactory.retrieveForumSizes());
            System.out.println("\n Double tap enter key to exit, results are saved to posts.txt and forum-sizes.txt respectively.");
            new Scanner(System.in).nextLine();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
