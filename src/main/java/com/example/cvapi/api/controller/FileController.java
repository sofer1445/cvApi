package com.example.cvapi.api.controller;

import com.example.cvapi.api.model.FileReading;
import com.example.cvapi.api.model.jobDetails;
import com.example.cvapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from http://localhost:3000
public class FileController {

    private FileService fileService;
    private FileReading fileReading = new FileReading();

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(path = "/file")
    public FileReading getFileReading() {
        return this.fileReading;
    }
}

//    @GetMapping(path ="/mostJob")
//    public Map<String,Integer> getMostJob(){
////        FileReading fileReading = new FileReading();
//        return this.fileReading.getTheMostSuitableJob();
//    }
//
//    @GetMapping(path = "/percentage")
//    public double[] getPercentage() {
//        return this.fileReading.getTheMatchingPercentage();
//    }


