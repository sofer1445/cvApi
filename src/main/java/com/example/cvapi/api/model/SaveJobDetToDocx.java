package com.example.cvapi.api.model;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveJobDetToDocx {

    public void saveJobDetailsToFile(InternetJob[] jobs , String fileName) {
        System.out.println("Saving job details to file");
        XWPFDocument document = new XWPFDocument();

        for (InternetJob job : jobs) {
            if (job != null) {
                addParagraph(document, "Job Name: " + job.getJobName());
                for (String detail : job.getJobDetailText()) {
                    addParagraph(document, detail);
                }
                // Add an empty line between jobs
                addParagraph(document, "");
            }
        }

        String filePath = "C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\פרטי משרות\\" + fileName + ".docx";
        File file = new File(filePath);
        File parentDirectory = file.getParentFile();
        if (!parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        try (FileOutputStream out = new FileOutputStream(file)) {
            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addParagraph(XWPFDocument document, String content) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(content);
    }
}
