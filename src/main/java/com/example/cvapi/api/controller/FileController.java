package com.example.cvapi.api.controller;

import com.example.cvapi.api.model.FileReading;
import com.example.cvapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    private FileService fileService;
    private FileReading fileReading = new FileReading();

    public void setFileReading(FileReading fileReading) {
        this.fileReading = fileReading;
    }

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(path = "/file")
    public FileReading getFileReading() {
        return this.fileReading;
    }

    @PostMapping(path = "/upload") //להמשיך מכאן
    public FileReading uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            File file = convertMultipartFileToFile(multipartFile);
            String nameFile = file.getName();
            file = new File("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\קורות חיים\\" + nameFile);
            FileReading fileReading = new FileReading();
            fileReading.setCvFile(file);
            fileReading.checksOtherJobs();
            this.fileReading = fileReading;
            return fileReading;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return file;
    }
}
