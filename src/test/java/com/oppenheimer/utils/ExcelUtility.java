package com.oppenheimer.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtility {
    public static final String filePath = "src/test/resources/testdata/herodetails.xlsx";

    public static int getRowCount(String filePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        Workbook wb = new XSSFWorkbook(file);
        Sheet sheet = wb.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        wb.close();
        file.close();
        return rowCount;
    }

    public static int getCellCount(String filePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        Workbook wb = new XSSFWorkbook(file);
        Sheet sheet = wb.getSheet(sheetName);
        Row row = sheet.getRow(0);
        int cellCount=row.getLastCellNum();
        wb.close();
        file.close();
        return cellCount;
    }

    public static String[][] getCellData(String filePath, String sheetName) {
        String[][] array = null;
        try {
            FileInputStream file = new FileInputStream(filePath);
            Workbook wb = new XSSFWorkbook(file);
            Sheet sheet = wb.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum() + 1;
            array = new String[rowCount - 1][sheet.getRow(0).getLastCellNum()];
            for (int i = 0; i <= rowCount; i++) {
                Row row = sheet.getRow(i + 1);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    array[i][j] = row.getCell(j).getStringCellValue();
                }
            }
            wb.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
}
