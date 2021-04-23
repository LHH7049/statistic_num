package com.lz.demo.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lz
 * @Date: 2021/4/20 14:34
 */
public class ExcelUtil {

    public static final ExcelUtil INSTANCE = new ExcelUtil();

    private static final String XLSX = ".xlsx";
    private static final String XLS = ".xls";

    private ExcelUtil() {
    }

    public List<List<String>> read(File file, int headRows) throws Exception {
        String fileName = file.getName();
        Workbook workbook = null;
        if (fileName.contains(XLSX)){
            workbook = new XSSFWorkbook(new FileInputStream(file));
        }
        if (workbook == null && fileName.contains(XLS)){
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }
        if (workbook == null){
            throw new IllegalArgumentException("非Excel文件");
        }

        return read(workbook, headRows);
    }

    public List<List<String>> read(MultipartFile file, int headRows) throws Exception {
        String fileName = file.getName();
        Workbook workbook = null;
        if (fileName.contains(XLSX)){
            workbook = new XSSFWorkbook(file.getInputStream());
        }
        if (workbook == null && fileName.contains(XLS)){
            workbook = new HSSFWorkbook(file.getInputStream());
        }
        if (workbook == null){
            throw new IllegalArgumentException("非Excel文件");
        }

        return read(workbook, headRows);
    }

    private List<List<String>> read(Workbook workbook, int headRows){
        List<List<String>> result = new ArrayList<>();

        Sheet sheet = workbook.getSheetAt(0);
        int lastRow = sheet.getPhysicalNumberOfRows();
        for (int i = headRows ; i <= lastRow ; i ++){
            Row row = sheet.getRow(i);
            if (row == null){
                continue;
            }
            List<String> items = new ArrayList<>();
            for (int j = row.getFirstCellNum() ; j <= row.getLastCellNum() ; j ++){
                Cell cell = row.getCell(j);
                if (cell == null){
                    continue;
                }
                cell.setCellType(CellType.STRING);;
                String item = cell.getRichStringCellValue().getString();
                items.add(item);
            }
            result.add(items);
        }

        return result;
    }
}
