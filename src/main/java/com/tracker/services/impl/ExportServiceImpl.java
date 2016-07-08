package com.tracker.services.impl;

import com.tracker.model.ExpenseDetail;
import com.tracker.services.ExportService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by nakulkumar on 27/6/16.
 */
@Service("exportService")
public class ExportServiceImpl implements ExportService {

    private static Logger LOG = Logger.getLogger(ExportServiceImpl.class);

    @Override
    public void exportToXLS(List<ExpenseDetail> expenseDetails, String dateq) {
        LOG.info("Exporting to Excel report for : " + dateq);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("Expense Worksheet");

        int startRowIndex = 0;
        int startColIndex = 0;

        buildReport(worksheet, startRowIndex, startColIndex);

        LOG.info("Writing details to the report");
        fillReport(worksheet, startRowIndex, startColIndex, expenseDetails);

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(dateq);
            String fileName = "Report-" + formatter.format(date) + ".xls";
            LOG.info("Creating report with name : " + fileName);
            String username = System.getProperty("user.name");
            File file = new File("/home/" + username + "/" + fileName);
            FileOutputStream outputStream = new FileOutputStream(file, false);
            workbook.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            LOG.error("Exception occurred while writing to file : ", e);
        }
    }

    private void buildReport(HSSFSheet worksheet, int startRowIndex, int startColIndex) {
        worksheet.setColumnWidth(0, 5000);
        worksheet.setColumnWidth(1, 5000);
        worksheet.setColumnWidth(2, 5000);
        worksheet.setColumnWidth(3, 5000);

        LOG.info("Writing title for report");
        buildTitle(worksheet, startRowIndex, startColIndex);

        LOG.info("Writing report headers");
        buildHeaders(worksheet, startRowIndex, startColIndex);
    }

    /**
     * Builds the report title and the date header
     *
     * @param worksheet
     * @param startRowIndex starting row offset
     * @param startColIndex starting column offset
     */
    private void buildTitle(HSSFSheet worksheet, int startRowIndex, int startColIndex) {
        // Create font style for the report title
        Font fontTitle = worksheet.getWorkbook().createFont();
        fontTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fontTitle.setFontHeight((short) 280);

        // Create cell style for the report title
        HSSFCellStyle cellStyleTitle = worksheet.getWorkbook().createCellStyle();
        cellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleTitle.setWrapText(true);
        cellStyleTitle.setFont(fontTitle);

        // Create report title
        HSSFRow rowTitle = worksheet.createRow((short) startRowIndex);
        rowTitle.setHeight((short) 500);
        HSSFCell cellTitle = rowTitle.createCell(startColIndex);
        cellTitle.setCellValue("Expense Report");
        cellTitle.setCellStyle(cellStyleTitle);

        // Create merged region for the report title
        worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        // Create date header
        HSSFRow dateTitle = worksheet.createRow((short) startRowIndex + 1);
        HSSFCell cellDate = dateTitle.createCell(startColIndex);
        cellDate.setCellValue("Report Generation Time : " + new Date());
    }

    /**
     * Builds the column headers
     *
     * @param worksheet
     * @param startRowIndex starting row offset
     * @param startColIndex starting column offset
     */
    private void buildHeaders(HSSFSheet worksheet, int startRowIndex, int startColIndex) {
        // Create font style for the headers
        Font font = worksheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Create cell style for the headers
        HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
        headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
        headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setFont(font);
        headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

        // Create the column headers
        HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
        rowHeader.setHeight((short) 500);

        HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
        cell1.setCellValue("Description");
        cell1.setCellStyle(headerCellStyle);

        HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
        cell2.setCellValue("Amount");
        cell2.setCellStyle(headerCellStyle);

        HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
        cell3.setCellValue("Created");
        cell3.setCellStyle(headerCellStyle);

        HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
        cell4.setCellValue("Updated");
        cell4.setCellStyle(headerCellStyle);
    }


    /**
     * Fills the report with content
     *
     * @param worksheet
     * @param startRowIndex  starting row offset
     * @param startColIndex  starting column offset
     * @param expenseDetails the data source
     */
    public static void fillReport(HSSFSheet worksheet, int startRowIndex, int startColIndex, List<ExpenseDetail> expenseDetails) {
        // Row offset
        startRowIndex += 2;

        // Create cell style for the body
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyCellStyle.setWrapText(true);

        // Create body
        for (int i = startRowIndex; i + startRowIndex - 2 < expenseDetails.size() + 2; i++) {
            // Create a new row
            HSSFRow row = worksheet.createRow((short) i + 1);

            // Retrieve the description value
            HSSFCell cell1 = row.createCell(startColIndex + 0);
            cell1.setCellValue(expenseDetails.get(i - 2).getDescription());
            cell1.setCellStyle(bodyCellStyle);

            // Retrieve the amount value
            HSSFCell cell2 = row.createCell(startColIndex + 1);
            cell2.setCellValue(expenseDetails.get(i - 2).getAmount());
            cell2.setCellStyle(bodyCellStyle);

            // Retrieve the created value
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String created = dateFormat.format(expenseDetails.get(i - 2).getCreated());
            HSSFCell cell3 = row.createCell(startColIndex + 2);
            cell3.setCellValue(created);
            cell3.setCellStyle(bodyCellStyle);

            // Retrieve the updated value
            String updated = dateFormat.format(expenseDetails.get(i - 2).getUpdated());
            HSSFCell cell4 = row.createCell(startColIndex + 3);
            cell4.setCellValue(updated);
            cell4.setCellStyle(bodyCellStyle);
        }
    }
}
