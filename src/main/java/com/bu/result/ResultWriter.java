package com.bu.result;

import com.bu.forum.Club;
import com.bu.forum.ForumPost;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class ResultWriter {

    private CSVWriter csvWriter;

    public ResultWriter() throws IOException {
        csvWriter = new CSVWriter(
                new FileWriter("results.txt"),
                '|',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.RFC4180_LINE_END
        );
    }

    public void writeToCSV(Map<Club, List<ForumPost>> resultsMap) {
        for (Club club : resultsMap.keySet())
            for (ForumPost forumPost : resultsMap.get(club))
                csvWriter.writeNext(new String[]{
                        club.toString(),
                        forumPost.getPostContent().replace("\n", ""),
                        forumPost.getUsername(),
                        forumPost.getTime()
                });
    }
}