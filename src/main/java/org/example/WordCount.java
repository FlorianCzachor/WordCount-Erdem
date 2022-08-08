package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCount {
    private final String phrase;
    private final String stopWordsPath = "src/main/resources/stopwords.txt";
    private final List<String> stopWords = new ArrayList<>();
    private boolean stopWord = false;

    public WordCount(String phrase, boolean stopWords) {
        this.phrase = phrase;
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

        var parts = phrase.split(" ");
        if (!stopWord) return parts.length;
        var stopWordsCount = amountOfStopWords();
        return parts.length - stopWordsCount;
    }

    private int amountOfStopWords() {
        int count = 0;
        Pattern pattern = Pattern.compile(String.join("|", stopWords));
        Matcher matcher = pattern.matcher(phrase);

        while (matcher.find()) {count++;}
        return count;
    }

    private boolean valid() {
        Pattern pattern = Pattern.compile("[^a-zA-Z]");
        Matcher matcher = pattern.matcher(phrase);
        if (matcher.find()) return true;
        return false;
    }
}
