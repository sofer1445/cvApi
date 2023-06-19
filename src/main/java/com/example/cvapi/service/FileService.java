package com.example.cvapi.service;

import com.example.cvapi.api.model.FileReading;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public class FileService {
    private File[] files;



     public FileService() {
            File file = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Automation Engineer.docx");
            File file1 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\DevOps Engineer.docx");
            File file2 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Front.docx");
            File file3 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Full.docx");
            File file4 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Software Developer.docx");
            File file5 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Software Tester.docx");
            File file6 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Web Developer.docx");
            File file7 = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\Backend Developer.docx");
    //        File cvFile = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\CV shoham sofer P.docx");
            File[] files = {file, file1, file2, file3, file4, file5, file6, file7};
    //        this.cvFile = cvFile;
            this.files = files;
            System.out.println("Files added ");


        }

//    public File getFileReading(String fileName) {
//        Optional<File> file = Optional.ofNullable(null);
//        for (File file1 : files) {
//            if (file1.getName().equals(fileName)) {
//                file = Optional.of(file1);
//            }
//        }
//        return file.orElse(null);
//
//
//    }
}
