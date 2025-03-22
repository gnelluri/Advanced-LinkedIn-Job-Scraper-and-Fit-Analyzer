package com.linkedin.scraper;

import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // Initialize scraper and Excel writer
            scraper scraper = new scraper();
            excelwriter excelWriter = new excelwriter();

            // Log in to LinkedIn
            scraper.login(config.getEmail(), config.getPassword());

            // Step 1: Navigate to LinkedIn Jobs page
            scraper.navigateToJobsPage();

            // Step 2: Search for "java"
            scraper.searchForJava();
            

            scraper.scrapeJobCards();

            // Close the scraper
            scraper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}