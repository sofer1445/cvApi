package com.example.cvapi.api.model;

import java.io.File;

public class cvFile {
    private File cvFile;

    public cvFile() {
        this.cvFile = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\קורות חיים\\CV shoham sofer P.docx");
    }

    public File getCvFile() {
        return cvFile;
    }

    public void setCvFile(File cvFile) {
        this.cvFile = cvFile;
    }
}
