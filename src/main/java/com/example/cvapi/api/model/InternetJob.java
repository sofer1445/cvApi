package com.example.cvapi.api.model;

public class InternetJob {
    private String jobName;
    private String webSite;
    private String companyName;
    private String location;
    private String date;
    private String jobLink;
    private String[] jobDetailText;

    public InternetJob(String jobName, String webSite, String companyName, String location, String date, String jobLink, String[] jobDetailText) {
        this.jobName = jobName;
        this.webSite = webSite;
        this.companyName = companyName;
        this.location = location;
        this.date = date;
        this.jobLink = jobLink;
        this.jobDetailText = jobDetailText;
    }

    public InternetJob() {
    }

    public String getJobName() {
        return jobName;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getJobLink() {
        return jobLink;
    }

    public String[] getJobDetailText() {
        return jobDetailText;
    }

    public void setJobDetailText(String[] jobDetailText) {
        this.jobDetailText = jobDetailText;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setJobLink(String jobLink) {
        this.jobLink = jobLink;
    }
}
