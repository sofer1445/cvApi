package com.example.cvapi.api.model;

import java.util.Set;

public class JobDetails {
    private String jobName;
    private Set<String> keywords;
    private int sumOfCommonKeywords;
    private Set<String> commonKeywords;
    private Set<String> missingKeywords;

    public JobDetails(String jobName, Set<String> keywords, Set<String> commonKeywords, Set<String> missingKeywords, int sumOfCommonKeywords) {
        this.jobName = jobName;
        this.keywords = keywords;
        this.commonKeywords = commonKeywords;
        this.missingKeywords = missingKeywords;
        this.sumOfCommonKeywords = sumOfCommonKeywords;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public int getSumOfCommonKeywords() {
        return sumOfCommonKeywords;
    }

    public void setSumOfCommonKeywords(int sumOfCommonKeywords) {
        this.sumOfCommonKeywords = sumOfCommonKeywords;
    }

    public Set<String> getCommonKeywords() {
        return commonKeywords;
    }

    public void setCommonKeywords(Set<String> commonKeywords) {
        this.commonKeywords = commonKeywords;
    }

    public Set<String> getMissingKeywords() {
        return missingKeywords;
    }

    public void setMissingKeywords(Set<String> missingKeywords) {
        this.missingKeywords = missingKeywords;
    }

    public String toString() {
        return "jobName: " + jobName + "\nkeywords: " + keywords + "\nsumOfKeywords: " + sumOfCommonKeywords + "\ncommonKeywords: " + commonKeywords + "\nmissingKeywords: " + missingKeywords;
    }


}
