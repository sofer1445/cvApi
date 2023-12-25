//package com.example.cvapi.api.model;
//
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class saveJobDetails {
//    private FindingJob findingJob;
//
//    public void saveNewJobDetailsToFile(FindingJob findingJob , String jobName) {
//        System.out.println("Saving job details to file: " + jobName + ".txt");
////        System.out.println(findingJob.getInternetJobs()[0].getJobName());
//        BufferedWriter writer = null;
//        try {
//            writer = new BufferedWriter(new FileWriter("C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\פרויקט\\פרטי משרות\\" + jobName + ".txt"));
//            for (int i = 0; i < 100; i++) {
////                if (findingJob.getInternetJobs()[i].getJobName() == null) {
////                    break;
////                }
//                writer.write("1 " + findingJob.getInternetJobs()[i].getJobName() + "\n");
//                writer.write("2 " + findingJob.getInternetJobs()[i].getWebSite() + "\n");
//                writer.write("3 " + findingJob.getInternetJobs()[i].getCompanyName() + "\n");
//                writer.write("4 " + findingJob.getInternetJobs()[i].getLocation() + "\n");
//                writer.write("5 " + findingJob.getInternetJobs()[i].getDate() + "\n");
////                writer.write("6 " + findingJob.getJobDetailsArray()[i]. + "\n");
//                writer.write("6 " + findingJob.getInternetJobs()[i].getJobLink() + "\n");
//                writer.write("7 " + findingJob.getInternetJobs()[i].getJobDetailText() + "\n");
////                writer.write("8 " + findingJob.getInternetJobs()[i][Final.JOB_DETAIL_TEXT + i] + "\n");
//
//                writer.write("\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}
