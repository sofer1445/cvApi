package com.example.cvapi.api.utils;

import com.example.cvapi.api.model.InternetJob;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.*;

@Component
public class DbUtils {
    private Connection connection;
    private String tableName;

    @PostConstruct
    public void init () {
        createDbConnection("root", "1234");
    }

    private void createDbConnection (String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cvcheckandsearch", username, password);
            System.out.println("Connection successfull!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertJobToDb(InternetJob internetJob , int index){
        System.out.println("Inserting job to DB");
        boolean success = false;
        init();
        if(CheckJobLinkAvailableInDb(internetJob.getWebSite() + internetJob.getJobLink())){
            System.out.println("Job already in DB");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + this.tableName + " (jobName, webSite," +
                    " companyName, location," +
                    " date, jobLink," +
                    " jobDetails)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, internetJob.getJobName());
            preparedStatement.setString(2, internetJob.getWebSite());
            preparedStatement.setString(3, internetJob.getCompanyName());
            preparedStatement.setString(4, internetJob.getLocation());
            preparedStatement.setString(5, internetJob.getDate());
            preparedStatement.setString(6, internetJob.getWebSite() + internetJob.getJobLink());
            preparedStatement.setString(7, internetJob.getJobDetailText()[index]);
            preparedStatement.executeUpdate();
            success = true;
            System.out.println("Job added to DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(success);
    }

    public boolean CheckJobLinkAvailableInDb(String jonLink){
        boolean available = false;
        try{
            String tableName = this.tableName;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT jobLink FROM " + tableName + " WHERE jobLink = ?");
            preparedStatement.setString(1, jonLink);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                available = true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        return available;

    }

    public void createTableInDb(String tableName){
        this.init();
        tableName = tableName.replace(" ", "_"); // replace spaces with underscores
        System.out.println("Creating table in DB: " + tableName);
        this.tableName = tableName;
        try{
            if (connection != null) {
                DatabaseMetaData dbm = connection.getMetaData();
                ResultSet tables = dbm.getTables(null, null, tableName, null);
                if (!tables.next()) {
                    // Table does not exist
                    PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE " + tableName + " (id INT PRIMARY KEY AUTO_INCREMENT," +
                            " jobName TEXT," +
                            " webSite TEXT," +
                            " companyName TEXT," +
                            " location TEXT," +
                            " date TEXT," +
                            " jobLink TEXT," +
                            " jobDetails MEDIUMTEXT)"); // Change TEXT to MEDIUMTEXT
                    preparedStatement.executeUpdate();
                } else {
                    System.out.println("Table " + tableName + " already exists");
                }
            } else {
                System.out.println("Connection is null");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}