
package com.linkedin.scraper;

import com.linkedin.scraper.models.profiledata;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class scraper {
    private WebDriver driver;

    public scraper() {
        // Set up ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        this.driver = new ChromeDriver(options);
        System.out.println("ChromeDriver initialized successfully!");
    }

    public void login(String email, String password) {
        try {
            System.out.println("Navigating to LinkedIn login page...");
            driver.get("https://www.linkedin.com/login");

            System.out.println("Entering email...");
            driver.findElement(By.id("username")).sendKeys(email);

            System.out.println("Entering password...");
            driver.findElement(By.id("password")).sendKeys(password);

            System.out.println("Clicking submit button...");
            driver.findElement(By.xpath("//button[@type='submit']")).click();

            // Wait for login to complete
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(18));
            wait.until(ExpectedConditions.urlContains("feed")); // Wait for the feed page to load
            System.out.println("Login successful!");
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*public profiledata scrapeProfile(String profileUrl) {
        try {
            System.out.println("Navigating to profile page: " + profileUrl);
            driver.get(profileUrl);

            // Wait for the profile name to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(18));
            WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(@class, 'inline') and contains(@class, 't-24') and contains(@class, 'v-align-middle')]")
            ));
            String name = nameElement.getText();
            System.out.println("Name: " + name);

            // Wait for the headline to load
            WebElement headlineElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'text-body-medium')]")
            ));
            String headline = headlineElement.getText();
            System.out.println("Headline: " + headline);

            System.out.println("Profile data extracted successfully!");
            return new profiledata(name, headline);
        } catch (Exception e) {
            System.out.println("Failed to scrape profile: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }*/
    public void navigateToJobsPage() {
        try {
            System.out.println("Navigating to LinkedIn Jobs page...");
            driver.get("https://www.linkedin.com/jobs");

            // Wait for the page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("jobs"));

            System.out.println("Successfully navigated to LinkedIn Jobs page!");
        } catch (Exception e) {
            System.out.println("Failed to navigate to LinkedIn Jobs page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void searchForJava() {
        try {
            System.out.println("Searching for 'java'...");

            // Wait for the search input field to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[contains(@id, 'jobs-search-box-keyword-id-ember')]")
            ));

            // Enter "java" and press Enter
            searchInput.sendKeys("java");
            searchInput.sendKeys(Keys.RETURN);

            // Wait for the search results to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'jobs-search-results-list')]")
            ));

            System.out.println("Successfully searched for 'java'!");
        } catch (Exception e) {
            System.out.println("Failed to search for 'java': " + e.getMessage());
            e.printStackTrace();
        }
    }


/*
    public void scrapeJobCards() {
        try {
            System.out.println("Scraping job cards...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Wait for the job listings container
            WebElement jobListingsContainer = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id='main']/div/div[2]/div[1]/div")
            ));

            // Get initial job cards and scroll to load more
            List<WebElement> jobCards = jobListingsContainer.findElements(By.xpath(".//div[contains(@class, 'job-card-container')]"));
            System.out.println("Number of job cards found: " + jobCards.size());

            // Scroll through job cards to load more jobs
            for (int i = 0; i < jobCards.size(); i++) {
                jobCards = jobListingsContainer.findElements(By.xpath(".//div[contains(@class, 'job-card-container')]"));
                WebElement jobCard = jobCards.get(i);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", jobCard);
                Thread.sleep(500);
            }

            // Loop through first 10 jobs
            for (int i = 0; i < Math.min(jobCards.size(), 10); i++) {
                try {
                    // Refresh job cards list
                    jobCards = jobListingsContainer.findElements(By.xpath(".//div[contains(@class, 'job-card-container')]"));
                    WebElement job = jobCards.get(i);

                    // Scroll and click
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", job);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", job);

                    // Wait for details panel to load (using jobs-description as the container)
                    WebElement detailsPanel = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(@class, 'jobs-description')]")
                    ));
                    System.out.println("Details panel found for job " + (i + 1));

                    // Extract job title
                    String title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(@class, 'job-details-jobs-unified-top-card__job-title')]")
                    )).getText();

                    // Extract company name
                    String company = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(@class, 'job-details-jobs-unified-top-card__company-name')]//a")
                    )).getText();

                    // Extract job location
                    String location = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(@class, 'job-details-preferences-and-skills__pill')]//span")
                    )).getText();

                    // Extract job description (updated with new class)
                    String description = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(@class, 'jobs-description__content')]")
                    )).getText();

                    // Print extracted details
                    System.out.println("Job " + (i + 1) + ":");
                    System.out.println("Title: " + title);
                    System.out.println("Company: " + company);
                    System.out.println("Location: " + location);
                    System.out.println("Description: " + description);
                    System.out.println("--------------------------------------");

                } catch (Exception e) {
                    System.out.println("Failed to extract job " + (i + 1) + ": " + e.getMessage());
                    String pageSourceSnippet = driver.getPageSource().substring(0, Math.min(1000, driver.getPageSource().length()));
                    System.out.println("Page source snippet: " + pageSourceSnippet);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to locate job cards: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // Extract and print job titles and company names
    */


        public void scrapeJobCards() {
            List<profiledata> jobData = new ArrayList<>(); // To store job data
            try {
                System.out.println("Scraping job cards...");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

                WebElement jobListingsContainer = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id='main']/div/div[2]/div[1]/div")
                ));

                List<WebElement> jobCards = jobListingsContainer.findElements(By.xpath(".//div[contains(@class, 'job-card-container')]"));
                System.out.println("Number of job cards found: " + jobCards.size());

                for (int i = 0; i < jobCards.size(); i++) {
                    jobCards = jobListingsContainer.findElements(By.xpath(".//div[contains(@class, 'job-card-container')]"));
                    WebElement jobCard = jobCards.get(i);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", jobCard);
                    Thread.sleep(500);
                }

                for (int i = 0; i < Math.min(jobCards.size(), 10); i++) {
                    try {
                        jobCards = jobListingsContainer.findElements(By.xpath(".//div[contains(@class, 'job-card-container')]"));
                        WebElement job = jobCards.get(i);

                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", job);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", job);

                        wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[contains(@class, 'jobs-description')]")
                        ));

                        String title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[contains(@class, 'job-details-jobs-unified-top-card__job-title')]")
                        )).getText();

                        String company = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[contains(@class, 'job-details-jobs-unified-top-card__company-name')]//a")
                        )).getText();

                        String location = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[contains(@class, 'job-details-preferences-and-skills__pill')]//span")
                        )).getText();

                        String description = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[contains(@class, 'jobs-description__content')]")
                        )).getText();

                        // Add to list instead of printing
                        jobData.add(new profiledata(title, company, location, description));

                    } catch (Exception e) {
                        System.out.println("Failed to extract job " + (i + 1) + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                // Write to Excel
                excelwriter writer = new excelwriter();
                writer.writeToExcel(jobData);

            } catch (Exception e) {
                System.out.println("Failed to locate job cards: " + e.getMessage());
                e.printStackTrace();
            }
        }

        public void close() {
        driver.quit();
        System.out.println("ChromeDriver closed successfully!");
    }
}
