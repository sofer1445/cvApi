package com.example.cvapi.api.controller;

import com.example.cvapi.api.model.FileReading;
import com.example.cvapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.cvapi.api.model.FindingJob;
import org.springframework.web.bind.annotation.GetMapping;


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

    @GetMapping(path = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
    public FileReading getFileReading() {
        return this.fileReading;
    }

    @PostMapping(path = "/upload")
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //Write me a function that receives the job name from the user and then send to the findingJob class and create an object of it and send it to the user
    @GetMapping(path = "/search")
    public FindingJob searchJobGet(@RequestParam("jobName") String jobName) {
        FindingJob findingJob = new FindingJob(jobName);
        findingJob.searchOnDrushim(jobName);
        return findingJob;
    }
       @PostMapping(path = "/search")
        public FindingJob searchJobPost(@RequestParam("jobName") String jobName) {
            FindingJob findingJob = new FindingJob(jobName);
            findingJob.searchOnDrushim(jobName);
            return findingJob;
        }


    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return file;
    }
}
