package com.example.cvapi.api.controller;

import com.example.cvapi.api.model.CheckFile;
import com.example.cvapi.api.model.FileReading;
import com.example.cvapi.api.model.InternetJob;
import com.example.cvapi.api.utils.DbUtils;
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
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")

public class FileController {

    private FileService fileService;
    private FileReading fileReading = new FileReading();
    public DbUtils dbUtils;



    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(path = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
    public FileReading getFileReading() {
        return this.fileReading;
    }

    public void setFileReading(FileReading fileReading) {
        this.fileReading = fileReading;
    }

    @PostMapping(path = "/upload")
    public FileReading uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            System.out.println("Received file: " + multipartFile.getOriginalFilename());

            // Save the uploaded file to a temporary file
            File tempFile = File.createTempFile("upload-", multipartFile.getOriginalFilename());
            multipartFile.transferTo(tempFile);
            System.out.println("Saved file to: " + tempFile.getAbsolutePath());

            // Use the original file name for the FileReading object
            File actualFile = new File(tempFile.getParentFile(), Objects.requireNonNull(multipartFile.getOriginalFilename()));
            int counter = 0;
            while (actualFile.exists()) {
                counter++;
                actualFile = new File(tempFile.getParentFile(), "new-" + counter + "-" + multipartFile.getOriginalFilename());
            }
            if (!tempFile.renameTo(actualFile)) {
                throw new IOException("Could not rename file to " + multipartFile.getOriginalFilename());
            }
            System.out.println("Renamed file to: " + actualFile.getAbsolutePath());

            FileReading fileReading = new FileReading();
            fileReading.setCvFile(actualFile);
            fileReading.checksOtherJobs();
            this.fileReading = fileReading;
            return fileReading;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/compatibilityTest")
    public int[] CompatibilityTest(@RequestBody String jobDetailText) {
        System.out.println("jobDetailText: " + jobDetailText);
        CheckFile checkFile = new CheckFile(jobDetailText, this.fileReading.displayTheFileContents(this.fileReading.getCvFile()));
        System.out.println(this.fileReading.displayTheFileContents(this.fileReading.getCvFile()));
        System.out.println(checkFile.cvKeywordChecker(jobDetailText, 1));
        int missingKeyWords = checkFile.getMissingKeyWords().size();
        int commonKeyWords = checkFile.cvKeywordChecker(jobDetailText, 1);
        return new int[]{missingKeyWords, commonKeyWords};
    }

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

    @PostMapping(path = "/takeFromDB")
    public List<InternetJob> takeFromDB(@RequestParam("jobName") String jobName) {
        dbUtils = new DbUtils();
        dbUtils.init();
        return dbUtils.getSortedDataFromTable(jobName);

    }


    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return file;
    }
}
