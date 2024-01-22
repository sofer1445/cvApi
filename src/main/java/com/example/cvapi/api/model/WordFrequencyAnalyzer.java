package com.example.cvapi.api.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.example.cvapi.api.model.Final.EXIT_CHOICE;

public class WordFrequencyAnalyzer {


    private final Set<String> excludedWords;
    private final Map<String, Integer> globalWordFrequencies;
    private final int threshold;

    public static void main(String[] args) {
        WordFrequencyAnalyzer wordFrequencyAnalyzer = new WordFrequencyAnalyzer();
        Scanner scanner = new Scanner(System.in);

        int choice = 0;

        while (choice != EXIT_CHOICE) {
            System.out.println("Choose what you want to scan resumes or job details (1/2), or exit (3)");
            choice = scanner.nextInt();

            if (choice == 1) {
                wordFrequencyAnalyzer.analyzeFiles("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\קורות חיים");
            } else if (choice == 2) {
                wordFrequencyAnalyzer.analyzeFiles("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\פרטי משרות");
            } else if (choice == EXIT_CHOICE) {
                System.out.println("Exiting...");
            } else {
                System.out.println("Wrong input");
            }
        }
    }

    public WordFrequencyAnalyzer() {
        this.excludedWords = new HashSet<>(Arrays.asList("-", "and", "עם", "לא", "לפחות", "כמו", "כן", "a", "the",
                "in", "of", "to", "for", "with", "on", "של",
                "as", "at", "from", "by", "an", "that", "this", "is", "are", "be", "will", "or", "it", "its", "their",
                "Name", "Job", "התפקיד", "משרה", "על", "תיאור", "•", ".", "=", ":","●","&" ));
        this.globalWordFrequencies = new HashMap<>();
        this.threshold = 7; // Adjust the threshold as needed
    }

    private void updateExcludedWords(Map<String, Integer> wordFrequencies) {
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            globalWordFrequencies.put(entry.getKey(), globalWordFrequencies.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }

        for (Map.Entry<String, Integer> entry : globalWordFrequencies.entrySet()) {
            if (entry.getValue() > threshold) {
                excludedWords.add(entry.getKey());
            }
        }
    }

    public void analyzeFiles(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".docx") || name.toLowerCase().endsWith(".pdf"));

        if (files != null) {
            for (File file : files) {
                Map<String, Integer> wordFrequencies = getWordFrequencies(file);
                //updateExcludedWords(wordFrequencies);  // Call the updateExcludedWords function
                System.out.println("File: " + file.getName());
                System.out.println("Most frequent words: " + wordFrequencies);
            }
            System.out.println();
        } else {
            System.out.println("No .docx or .pdf files found in the directory.");
        }
    }


    private Map<String, Integer> getWordFrequencies(File file) {
        if (file.getName().toLowerCase().endsWith(".docx")) {
            return getWordFrequenciesFromDocx(file);
        } else if (file.getName().toLowerCase().endsWith(".pdf")) {
            return getWordFrequenciesFromPDF(file);
        } else {
            return new HashMap<>();
        }
    }

    private Map<String, Integer> getWordFrequenciesFromDocx(File file) {
        Map<String, Integer> wordFrequencies = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        String[] words = text.split("\\s+");
                        for (String word : words) {
                            if (!excludedWords.contains(word)) {
                                wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            // Log the exception or provide a user-friendly message
            e.printStackTrace();
        }

        return getMostFrequentWords(wordFrequencies);
    }

    private Map<String, Integer> getWordFrequenciesFromPDF(File file) {
        Map<String, Integer> wordFrequencies = new HashMap<>();

        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            String[] words = text.split("\\s+");
            for (String word : words) {
                if (!excludedWords.contains(word)) {
                    wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            // Log the exception or provide a user-friendly message
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
