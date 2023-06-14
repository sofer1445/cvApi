package com.example.cvapi.api.model;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class checkFile {
    //    private String jobTitle;
    private int numOfJob;
    private String cvFileContent;
    private Set<String> missingKeyWords = new HashSet<>();
    private Set<String> commonKeyWords = new HashSet<>();
    private final String cvFile;

    public checkFile(String cvFileContent , String cvFile) {
        this.cvFile = cvFile;
        this.cvFileContent = cvFileContent;
    }

    public int getNumOfJob() {
        return numOfJob;
    }

    public void setNumOfJob(int numOfJob) {
        this.numOfJob = numOfJob;
    }

    public String getCvFileContent() {
        return cvFileContent;
    }

    public void setCvFileContent(String cvFileContent) {
        this.cvFileContent = cvFileContent;
    }

    public Set<String> getMissingKeyWords() {
        return missingKeyWords;
    }

    public void setMissingKeyWords(Set<String> missingKeyWords) {
        this.missingKeyWords = missingKeyWords;
    }

    public Set<String> getCommonKeyWords() {
        return commonKeyWords;
    }

    public void setCommonKeyWords(Set<String> commonKeyWords) {
        this.commonKeyWords = commonKeyWords;
    }

    public int cvKeywordChecker(String jobContent,int index) { // מחזיר לי את סכום המילות המפתח המשותפות
        System.out.println("____________________");
        String[] newKeyWords = jobContent.split("\\W+");
        Set<String> missingWords = new HashSet<>();
        Set<String> commonWords = new HashSet<>();

        for (String keyword : newKeyWords) {
            String theKeyWord = keyword.toLowerCase();
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(theKeyWord) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(this.cvFile);
            if (matcher.find()) {
                commonWords.add(theKeyWord);
            } else {
                missingWords.add(theKeyWord);
            }
        }

        this.missingKeyWords = missingWords;
        this.commonKeyWords = commonWords;
        return commonWords.size();
    }

    public String deleteCounter(String keyWord, int index) { // Removes the number from the word
        if (keyWord.charAt(keyWord.length() ) == '.' || !(keyWord.charAt(keyWord.length() - 1) >= '1' && keyWord.charAt(keyWord.length() - 1) <= '9')) {
            return keyWord;
        }
        String newKeyWord = "";
        int length = keyWord.length();
        if (index > 9 && index < 100) {
            if (length != 1) {
                newKeyWord = keyWord.substring(0, length - 2);
            }
        } else if (index > 99 && index < 1000) {
            if (length != 1) {
                newKeyWord = keyWord.substring(0, length - 3);
            }
        } else {
            if (length != 1) {
                newKeyWord = keyWord.substring(0, length - 1);
            }

        }
        return newKeyWord;
    }

    public Set<String> showMissingKeyWords() { // מראה את מילות המפתח החסרות
        System.out.println("show missing key words");
//        if (this.missingKeyWords.size() == 0) {
//            System.out.println("If you have not selected a job type, please select one");
//        } else {
//            System.out.println("The missing keywords are: " + this.missingKeyWords + "\n" + "The number of missing keywords is: " + this.missingKeyWords.size());
//        }
        return this.missingKeyWords;
    }

    public Set<String> showCommonKeyWords() { // מראה את מילות המפתח המשותפות
        System.out.println("show common key words");
//        if (this.commonKeyWords.size() == 0) {
//            System.out.println("If you have not selected a job type, please select one");
//        } else {
//            System.out.println("The common keywords are: " + this.commonKeyWords + "\n" + "The number of common keywords is: " + this.commonKeyWords.size());
//        }
        return this.commonKeyWords;
    }

}

