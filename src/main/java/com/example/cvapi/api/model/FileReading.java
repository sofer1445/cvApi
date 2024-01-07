package com.example.cvapi.api.model;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class FileReading {
    private  String[] jobDescription = new String[100];
    private File[] files;
    private int[] arrayOfCommonKeywords = new int[100];
    private File cvFile;
    private JobDetails[] jobDetailsArray = new JobDetails[100];

    public FileReading(){
        this.cvFile = new CvFile().getCvFile();
        addFile();
        checksOtherJobs();
    }
    public String readCvFile() { // קוראת את הקובץ ומציגה את התוכן
        CvFile cvFile = new CvFile();
        return (displayTheFileContents(cvFile.getCvFile()));
    }

    public File getCvFile() {
        return cvFile;
    }

    public void setCvFile(File cvFile) {
        this.cvFile = cvFile;
    }

    public String[] getJobDescription() {
        return jobDescription;
    }
    public void setJobDescription(String[] jobDescription) {
        this.jobDescription = jobDescription;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public JobDetails[] getJobDetailsArray() {
        return jobDetailsArray;
    }

    public void setJobDetailsArray(JobDetails[] jobDetailsArray) {
        this.jobDetailsArray = jobDetailsArray;
    }

    public void addFile() {
        File folder = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט");
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".docx"));
        if (files != null) {
            this.files = files;
            System.out.println("Files added ");
            toFillTheJobDescription();
        } else {
            System.out.println("No .docx files found in the directory.");
        }
    }

    public void toFillTheJobDescription() {
        String[] newJobDescription = new String[this.files.length];
        JobDetails[] newJobDetailsArray = new JobDetails[this.files.length];
        for (int i = 0; i < this.files.length; i++) {
            newJobDescription[i] = deleteDocx(this.files[i].getName());
            newJobDetailsArray[i] = new JobDetails(newJobDescription[i], null,  null, null,0);
        }
        this.jobDescription = newJobDescription;
        this.jobDetailsArray = newJobDetailsArray;

    }
    public String deleteDocx(String decJob) { // מוריד משם הקובץ את הסיומת שלו
        String newDecJob = decJob.replace(".docx", "");
        return newDecJob;
    }

    public String displayTheFileContents(File file) { // מציגה את התוכן של הקובץ
        System.out.println(); // down line
        String fileName = file.getName();
//        System.out.println("File name: " + fileName);
        StringBuilder fileContent = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        fileContent.append(text);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileContentString = fileContent.toString();
        return fileContentString;
    }
    public void checksOtherJobs() { // בודק אם יש משרה אחרת מתאימה
        System.out.println("start checksOtherJobs" + this.cvFile);
        int[] counter = new int[this.files.length];
        for (int i = 0; i < this.files.length; i++) {
            Set<String> newSetCommonPlusMissing = new HashSet<>();
            CheckFile checkFile1 = new CheckFile(displayTheFileContents(this.files[i]) , displayTheFileContents(this.cvFile));
            counter[i] = checkFile1.cvKeywordChecker(displayTheFileContents(this.files[i]), i);
            System.out.println("Job Name - " + deleteDocx(this.files[i].getName()) + "\n" + "Total keywords: " + counter[i]);
            this.jobDetailsArray[i].setMissingKeywords(checkFile1.showMissingKeyWords());
            this.jobDetailsArray[i].setCommonKeywords(checkFile1.showCommonKeyWords());
            this.jobDetailsArray[i].setSumOfCommonKeywords(checkFile1.showCommonKeyWords().size());
            newSetCommonPlusMissing.addAll(this.jobDetailsArray[i].getCommonKeywords());
            newSetCommonPlusMissing.addAll(this.jobDetailsArray[i].getMissingKeywords());
            this.jobDetailsArray[i].setKeywords(newSetCommonPlusMissing);
//            double percent = ( (double) counter[i] / this.jobDetailsArray[i].getKeywords().size()) * 100;
//            percent = Math.round(percent * 100.0) / 100.0;
//            System.out.println("Percent of common keywords: " + percent + "%");
        }
        this.arrayOfCommonKeywords = counter;
    }

    public Map<String, Integer> getTheMostSuitableJob(){
        Map<String, Integer> max = new HashMap<>();
        for (int i = 0; i < arrayOfCommonKeywords.length; i++) {
            max.put(deleteDocx(this.files[i].getName()), arrayOfCommonKeywords[i]);
        }
        int maxValueInMap = (Collections.max(max.values()));  // This will return max value in the Hashmap
        for (Map.Entry<String, Integer> entry : max.entrySet()) {  // Itrate through hashmap
            if (entry.getValue() == maxValueInMap) {
                System.out.println("_________________________");
                System.out.println("The most suitable job is: " + entry.getKey());     // Print the key with max value
                System.out.println("The number of keywords is: " + entry.getValue());
                System.out.println("_________________________");
            }
        }
        return max;
    }

    public double[] getTheMatchingPercentage(){
        double[] percent = new double[this.files.length];
        for (int i = 0; i < this.files.length; i++) {
            percent[i] = ( (double) arrayOfCommonKeywords[i] / this.jobDetailsArray[i].getKeywords().size()) * 100;
            percent[i] = Math.round(percent[i] * 100.0) / 100.0;

        }
        return percent;
    }
}