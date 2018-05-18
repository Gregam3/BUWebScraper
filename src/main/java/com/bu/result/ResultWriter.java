package com.bu.result;

import com.bu.forum.ForumType;
import com.bu.forum.ForumPost;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class ResultWriter {

    private CSVWriter createCSVWriter(String fileName) throws IOException {
        return new CSVWriter(
                new FileWriter(fileName),
                '|',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.RFC4180_LINE_END
        );
    }

    public void writePostsToCSV(Map<ForumType, List<ForumPost>> postMap) throws IOException {
        System.out.println("Writing posts to posts.txt");


        CSVWriter csvWriter = createCSVWriter("posts.txt");

        if (postMap == null)
            System.out.println("No thread URLs could be found in threads.txt.");
        else
            for (ForumType forumType : postMap.keySet())
                for (ForumPost forumPost : postMap.get(forumType))
                    csvWriter.writeNext(new String[]{
                            forumType.toString(),
                            forumPost.getPostContent().replace("\n", ""),
                            forumPost.getUsername(),
                            forumPost.getTime()
                    });

        csvWriter.flush();
        csvWriter.close();

        System.out.print("Retrieved and wrote posts.txt");// a " + getFileSizeAsString("posts.txt"));
    }

    public void writeForumSizeToTxt(Map<ForumType, Long> forumSizeMap) throws IOException {
        FileWriter fileWriter = new FileWriter(new File("forum-sizes.txt"));

        if (forumSizeMap == null)
            System.out.println("No forum URLs could be found in forums.txt.");
        else {
            for (ForumType forumType : forumSizeMap.keySet())
                fileWriter.write(forumType.toString() + " total posts - " + String.valueOf(forumSizeMap.get(forumType)) +","
                        + System.getProperty("line.separator"));
        }

        fileWriter.flush();
        fileWriter.close();

        System.out.print("Retrieved and wrote forum-sizes.txt");// a " + getFileSizeAsString("forum-size.txt"));
    }

//    private String getFileSizeAsString(String fileName) {
//        File file = new File(fileName);
//
//        if(file.length() > (1024 * 1024))
//            return (((double) file.length() / 1024) / 1024 + "MB file ");
//        if(file.length() > 1024)
//            return (((double) file.length() / 1024) / 1024 + "KB file ");
//        return file.length() + "Byte file ";
//    }
}