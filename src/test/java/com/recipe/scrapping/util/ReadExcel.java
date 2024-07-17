package com.recipe.scrapping.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.util.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ReadExcel {

    public static Map <String, List<String>> readExcelDataLCHFElimination () throws IOException {
        ReadConfig readConfig = new ReadConfig();
        File excelFile = new File(readConfig.loadConfig().getProperty("eliminatorsList"));
        FileInputStream fileInputStream = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet("LCHFElimination");
        Iterator<Row> row = sheet.rowIterator();
        Map<String, List<String>> eliminators_ToAdd_Map = new HashMap<String, List<String>>();
        int rowCount = 0;
        List<String> valuesEliminate = new ArrayList<>();
        List<String> valuesToAdd = new ArrayList<>();
        while (row.hasNext()) {
            rowCount++;
            Row currRow = row.next();
            if (rowCount < 3) {
                continue;
            }


            //running loop to add all eliminator list in list values
//            for (int i = rowCount; i <= sheet.getLastRowNum(); i++) {
                Cell valueCellEliminate = currRow.getCell(0);
                Cell valueCellToAdd = currRow.getCell(1);
                if (valueCellEliminate != null && !Strings.isNullOrEmpty(valueCellEliminate.getStringCellValue())) {
                    valuesEliminate.add(valueCellEliminate.getStringCellValue());
                }
                if (valueCellToAdd != null && !Strings.isNullOrEmpty(valueCellToAdd.getStringCellValue())) {
                    valuesToAdd.add(valueCellToAdd.getStringCellValue());
                }

//            }



        }
        eliminators_ToAdd_Map.put(AppConstants.ELIMINATORS, valuesEliminate);
        eliminators_ToAdd_Map.put(AppConstants.TO_ADD, valuesToAdd);
        workbook.close();
        System.out.println(eliminators_ToAdd_Map);
        return eliminators_ToAdd_Map;
    }


    public static Map <String, List<String>> readExcelDataLFVElimination () throws IOException {
        ReadConfig readConfig = new ReadConfig();
        File excelFile = new File(readConfig.loadConfig().getProperty("eliminatorsList"));
        FileInputStream fileInputStream = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet("LFVElimination");
        Iterator<Row> row = sheet.rowIterator();
        Map<String, List<String>> eliminators_ToAdd_Map = new HashMap<String, List<String>>();
        int rowCount = 0;
        List<String> valuesEliminate = new ArrayList<>();
        List<String> valuesToAdd = new ArrayList<>();
        while (row.hasNext()) {
            rowCount++;
            Row currRow = row.next();
            if (rowCount < 3) {
                continue;
            }


            //running loop to add all eliminator list in list values
//            for (int i = rowCount; i <= sheet.getLastRowNum(); i++) {
                Cell valueCellEliminate = currRow.getCell(0);
                Cell valueCellToAdd = currRow.getCell(1);
                if (valueCellEliminate != null && !Strings.isNullOrEmpty(valueCellEliminate.getStringCellValue())) {
                    valuesEliminate.add(valueCellEliminate.getStringCellValue());
                }
                if (valueCellToAdd != null && !Strings.isNullOrEmpty(valueCellToAdd.getStringCellValue())) {
                    valuesToAdd.add(valueCellToAdd.getStringCellValue());
                }

//            }



        }
        eliminators_ToAdd_Map.put(AppConstants.ELIMINATORS, valuesEliminate);
        eliminators_ToAdd_Map.put(AppConstants.TO_ADD, valuesToAdd);
        workbook.close();
        System.out.println(eliminators_ToAdd_Map);
        return eliminators_ToAdd_Map;
    }

}


