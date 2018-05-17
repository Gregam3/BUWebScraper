package com.bu.result;

import com.bu.forum.ForumType;
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

    public void writeToCSV(Map<ForumType, List<ForumPost>> resultsMap) {
        System.out.println("Writing results to results.txt");
        for (ForumType forumType : resultsMap.keySet())
            for (ForumPost forumPost : resultsMap.get(forumType))
                csvWriter.writeNext(new String[]{
                        forumType.toString(),
                        forumPost.getPostContent().replace("\n", ""),
                        forumPost.getUsername(),
                        forumPost.getTime()
                });
    }
}