

/*public class excelwriter {
    public void writeToExcel(List<profiledata> data, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("LinkedIn Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Headline");

        // Add data rows
        int rowNum = 1;
        for (profiledata profile : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(profile.getName());
            row.createCell(1).setCellValue(profile.getHeadline());
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}*/
package com.linkedin.scraper;

import com.linkedin.scraper.models.profiledata;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class excelwriter {
    public void writeToExcel(List<profiledata> data) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Job Listings");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Title");
        headerRow.createCell(1).setCellValue("Company");
        headerRow.createCell(2).setCellValue("Location");
        headerRow.createCell(3).setCellValue("Description");

        // Set column widths (Description wider)
        sheet.setColumnWidth(0, 15 * 256); // Title: ~15 characters wide
        sheet.setColumnWidth(1, 20 * 256); // Company: ~20 characters wide
        sheet.setColumnWidth(2, 15 * 256); // Location: ~15 characters wide
        sheet.setColumnWidth(3, 60 * 256); // Description: ~60 characters wide (wider)

        // Create a style for wrapping text in Description column
        CellStyle wrapStyle = workbook.createCellStyle();
        wrapStyle.setWrapText(true);

        // Add data rows
        int rowNum = 1;
        for (profiledata job : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(job.getTitle());
            row.createCell(1).setCellValue(job.getCompany());
            row.createCell(2).setCellValue(job.getLocation());
            Cell descriptionCell = row.createCell(3);
            descriptionCell.setCellValue(job.getDescription());
            descriptionCell.setCellStyle(wrapStyle); // Apply wrapping to Description
        }

        // Auto-size rows to fit wrapped text (optional, improves readability)
        for (int i = 0; i <= data.size(); i++) {
            sheet.autoSizeColumn(i); // Adjusts column width (optional, might override set width)
            Row row = sheet.getRow(i);
            if (row != null) {
                row.setHeight((short) -1); // -1 means auto-height
            }
        }

        // Generate filename with current date
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fileName = "Jobs_" + date + ".xlsx";

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
            System.out.println("Data written to " + fileName);
        }
        workbook.close();
    }
}