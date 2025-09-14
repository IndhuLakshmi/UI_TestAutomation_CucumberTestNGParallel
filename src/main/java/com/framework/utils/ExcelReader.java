package com.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple Excel reader for data-driven tests.
 */
public class ExcelReader {

    public static List<String[]> readSheet(String path, String sheetName) {
        List<String[]> rows = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(path);
             Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheet(sheetName);
            for (Row r : sheet) {
                int last = r.getLastCellNum();
                String[] vals = new String[last];
                for (int i = 0; i < last; i++) {
                    Cell c = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    vals[i] = c.toString();
                }
                rows.add(vals);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel: " + e.getMessage(), e);
        }
        return rows;
    }
}
