package com.example.cvapi.api.model;

import com.example.cvapi.api.utils.DbUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class FindingJob {
    //    private final String[][] jobDetailsArray = new String[100][100];
    private final InternetJob[] internetJobs = new InternetJob[100];
    public DbUtils dbUtils;



    public FindingJob(String nameOfJob) {
        searchOnDrushim(nameOfJob);
        SaveJobDetToDocx saveJobDetails = new SaveJobDetToDocx();
        saveJobDetails.saveJobDetailsToFile(this.internetJobs, nameOfJob);


    }
//    public findingJob(String nameOfJob) {
//        CompletableFuture.runAsync(() -> {
//            searchOnDrushim(nameOfJob);
//            saveJobDetails saveJobDetails = new saveJobDetails();
//            saveJobDetails.saveNewJobDetailsToFile(this, nameOfJob);
//        });
//    }

//    public static void main(String[] args) {
//        findingJob findingJob = new findingJob("software engineer");
//        System.out.println(findingJob.jobDetailsArray[2][Final.JOB_NAME]);
//    }

//    public String[][] getJobDetailsArray() {
//        return jobDetailsArray;
//    }


    public InternetJob[] getInternetJobs() {
        return internetJobs;
    }

    public void searchOnDrushim(String nameOfJob) {
        System.out.println("Searching on Drushim");

        try {
            Document doc = Jsoup.connect(Final.DRUSHIM_URL + nameOfJob + "&geolexid=537905&range=3&ssaen=1")
                    .timeout(30 * 1000)
                    .userAgent("Mozilla")
                    .get();

            Elements jobs = doc.select(".flex.jobs-row.sm12.md8.lg10 [data-v-58690ec5] .pt-0.mb-6.jobList_vacancy");

            ExecutorService executor = Executors.newFixedThreadPool(10);

            for (Element job : jobs) {
                executor.submit(() -> jobDetails(job, jobs.indexOf(job),nameOfJob));
            }

            executor.shutdown();
            executor.awaitTermination(30, TimeUnit.SECONDS);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void jobDetails(Element job, int index,String nameOfJob) {
        String jobName = job.select(".job-url.primary--text.font-weight-medium.primary--text").text();
        Elements company = job.select(".display-22.view-on-submit.disabledLink.pb-1.mb-0");
        String webSite = company.select("[href]").attr("href");
        String companyName = company.text();
        String location = job.select(".flex.nowrap.flex-basis-0.flex-grow-0").text();
        String date = job.select(".display-18.inline-flex").text();
        String jobLink = job.select("[href]").attr("href");
        String fullUrl = "https://www.drushim.co.il" + jobLink;

        String jobDetailText2 = null;
        String[] jobDetArray = null;
        try {

            Document jobDetailDoc = Jsoup.connect(fullUrl).timeout(10000).get();
            Elements jobDetail2 = jobDetailDoc.getElementsByClass("layout job-details-wrap wrap jobDes px-4 ltr");
            if (jobDetail2.isEmpty()) {
                jobDetail2 = jobDetailDoc.getElementsByClass("layout job-details-wrap wrap jobDes px-4");
            }
            jobDetailText2 = jobDetail2.text();
            jobDetArray = jobDetailText2.split("[,.]");

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.internetJobs[index] = new InternetJob(jobName, webSite, companyName, location, date, jobLink, jobDetArray);
        this.dbUtils = new DbUtils();
        dbUtils.createTableInDb(nameOfJob);
        dbUtils.insertJobToDb(this.internetJobs[index], index,nameOfJob);

//        SaveJobDetToDocx saveJobDetails = new SaveJobDetToDocx();
//        saveJobDetails.saveJobDetailsToFile(this.internetJobs[index]);
    }


    public String toString() {
        return Arrays.toString(internetJobs);
    }
}