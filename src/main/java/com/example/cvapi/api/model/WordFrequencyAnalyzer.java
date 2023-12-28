package com.example.cvapi.api.model;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordFrequencyAnalyzer {
    public static void main(String[] args) {
        WordFrequencyAnalyzer wordFrequencyAnalyzer = new WordFrequencyAnalyzer();
        wordFrequencyAnalyzer.analyzeFiles();
    }

    //private static final String DIRECTORY_PATH = "C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\פרטי משרות";
    public void analyzeFiles() {
        //"C:\Users\sofer\OneDrive\שולחן העבודה\פרויקט\פרטי משרות\Automation Engineer.docx"
        String DIRECTORY_PATH = "C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\פרטי משרות";
        File folder = new File(DIRECTORY_PATH);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".docx"));

        if (files != null) {
            for (File file : files) {
                Map<String, Integer> wordFrequencies = getWordFrequencies(file);
                System.out.println("File: " + file.getName());
                System.out.println("Most frequent words: " + wordFrequencies);
            }
        } else {
            System.out.println("No .docx files found in the directory.");
        }
    }

    private Map<String, Integer> getWordFrequencies(File file) {
        Map<String, Integer> wordFrequencies = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        String[] words = text.split("\\s+");
                        for (String word : words) {
                            wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return getMostFrequentWords(wordFrequencies);
    }

    private Map<String, Integer> getMostFrequentWords(Map<String, Integer> wordFrequencies) {
        // Sort the map by value in descending order
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordFrequencies.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        // Get the top 9 most frequent words
        Map<String, Integer> result = new LinkedHashMap<>();
        for (int i = 0; i < Math.min(9, list.size()); i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
