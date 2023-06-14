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
    private cvFile cvFile;
    private jobDetails[] jobDetailsArray = new jobDetails[100];

    public FileReading(){
        this.cvFile = new cvFile();
        addFile();
        checksOtherJobs();
    }
    public String readCvFile() { // קוראת את הקובץ ומציגה את התוכן
        cvFile cvFile = new cvFile();
        return (displayTheFileContents(cvFile.getCvFile()));
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

    public jobDetails[] getJobDetailsArray() {
        return jobDetailsArray;
    }

    public void setJobDetailsArray(jobDetails[] jobDetailsArray) {
        this.jobDetailsArray = jobDetailsArray;
    }

    public void addFile() {
        File file = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Automation Engineer.docx");
        File file1 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\DevOps Engineer.docx");
        File file2 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Front.docx");
        File file3 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Full-stack.docx");
        File file4 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Software Developer.docx");
        File file5 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Software Tester.docx");
        File file6 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Web Developer.docx");
//        File cvFile = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\CV shoham sofer P.docx");
        File[] files = {file, file1, file2, file3, file4, file5, file6};
//        this.cvFile = cvFile;
        this.files = files;
        System.out.println("Files added ");
        toFillTheJobDescription();

    }

    public void toFillTheJobDescription() {
        String[] newJobDescription = new String[this.files.length];
        jobDetails[] newJobDetailsArray = new jobDetails[this.files.length];
        for (int i = 0; i < this.files.length; i++) {
            newJobDescription[i] = deleteDocx(this.files[i].getName());
            newJobDetailsArray[i] = new jobDetails(newJobDescription[i], null,  null, null,0);
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
        System.out.println("start checksOtherJobs");
        int[] counter = new int[this.files.length];
        for (int i = 0; i < this.files.length; i++) {
            Set<String> newSetCommonPlusMissing = new HashSet<>();
            checkFile checkFile1 = new checkFile(displayTheFileContents(this.files[i]) , displayTheFileContents(this.cvFile.getCvFile()));
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
