package com.bu;

import com.bu.result.ResultWriter;
import com.bu.webscraping.ScraperFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static int siteNumber = 1;
    public static String siteName = "";
    public static int cumulativePageCount = 1;

    public static void main(String[] args) throws IOException {
        long startTime = new Date().getTime();

        ResultWriter resultWriter = new ResultWriter();

        try {
            resultWriter.writePostsToCSV(ScraperFactory.retrievePostsForAllForums());
//            resultWriter.writeForumSizeToTxt(ScraperFactory.retrieveTotalForumPostsForAllForums());

            System.out.println("\nPress Enter key to close.");
            new Scanner(System.in).nextLine();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    private static String convertUnixDifferenceToStringRunTime(long startTime, long endTime) {
//        int seconds = (int) (endTime - startTime)/1000;
//        int minutes = 0;
//        int hours = 0;
//
//        for (;seconds > 60; seconds -= 60) {
//            minutes++;
//            if(minutes > 60) {
//                minutes -= 60;
//                hours++;
//            }
//        }
//
//        return hours +"hrs, " + minutes + " mins and " + seconds + " seconds";
//
//    }
}
