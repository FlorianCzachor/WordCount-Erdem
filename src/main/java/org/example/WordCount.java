package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordCount {
    private String Phrase;

    private String stopWordsPath = "src/main/resources/stopwords.txt";
    private List<String> stopWords = new ArrayList<>();
    private boolean stopWord = false;

    public WordCount(String phrase, boolean stopWords) {
        Phrase = phrase;
        stopWord = stopWords;
        if (stopWords) {
            var content = readFile(stopWordsPath);
            content.stream().forEach((stopWord) -> {
                //Adding spaces at the beginnning and end
                //makes sure not words cotaining the stopwords are getting filtered
                //Eg. " the "
                // (the) filtered | (there) not filtered
                this.stopWords.add(" " + stopWord + " ");
            });

        }
    }

    public static List<String> readFile(String path) {
        List<String> content = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                content.add(line);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return content;
    }

    public int getWordCount() {
        if (!valid()) return -1;

        var parts = Phrase.split(" ");

        if (!stopWord) return parts.length;

        var stopWordsCount = amountOfStopWords(parts);
        return parts.length - stopWordsCount;
    }

    private int amountOfStopWords(String[] parts) {
        int count = 0;
        Pattern pattern = Pattern.compile(stopWords.stream().collect(Collectors.joining("|")));
        Matcher matcher = pattern.matcher(Phrase);

        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private boolean valid() {
        Pattern pattern = Pattern.compile("[^a-zA-Z]");
        Matcher matcher = pattern.matcher(Phrase);

        if (matcher.find()) return true;
        return false;
    }
}
