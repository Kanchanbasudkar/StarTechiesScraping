package com.recipe.scrapping.util;


import com.recipe.scrapping.model.RecipeInformation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WriteExcel {


    public static List<List<String>> readInputConditions(String excelFilePath, String sheetName) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(excelFilePath));
        Sheet sheet = workbook.getSheet(sheetName);
        workbook.close();
        Iterator<Row> rowIterator = sheet.iterator();

        List<String> eliminatorsList = new ArrayList<>();
        List<String> toAddList = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row currRow = rowIterator.next();

            // First two rows are headers, hence skip them
            if (currRow.getRowNum() == 0 || currRow.getRowNum() == 1) {
                continue;
            }

            // Get eliminator
            Cell eliminatorCell = currRow.getCell(0);
            // Get toAdd
            Cell toAddCell = currRow.getCell(1);

            if (eliminatorCell != null) {
                String value = eliminatorCell.getStringCellValue().trim();
                if (!value.isEmpty()) {
                    eliminatorsList.add(value);
                }
            }

            if (toAddCell != null) {
                String value = toAddCell.getStringCellValue().trim();
                if (!value.trim().isEmpty()) {
                    toAddList.add(value);
                }
            }
        }

        // Create return list
        List<List<String>> result = new ArrayList<List<String>>();
        result.add(eliminatorsList);
        result.add(toAddList);
        return result;
    }

    public static void writeInLCHFSheet(List<RecipeInformation> outputData, String fileNameProperty) throws IOException {
        // Create a Workbook
        Workbook workbook;
        File file = new File(ReadConfig.loadConfig().getProperty(fileNameProperty));

        if (file.exists()) {
            // If the file already exists, read it
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
        } else {
            // If the file doesn't exist, create a new Workbook

            workbook = new XSSFWorkbook();
        }

        // Check if the sheet already exists
        Sheet sheet = workbook.getSheet("LCHF");

        if (sheet == null) {
            // If the sheet doesn't exist, create a new one
            sheet = workbook.createSheet("LCHF");

            // add header record, if new sheet
            int cellNum = 0;
            Row row = sheet.createRow(0);
            row.createCell(cellNum++).setCellValue("Recipe ID");
            row.createCell(cellNum++).setCellValue("Recipe Name");
            row.createCell(cellNum++).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
            row.createCell(cellNum++).setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
            row.createCell(cellNum++).setCellValue("Ingredients");
            row.createCell(cellNum++).setCellValue("Preparation Time");
            row.createCell(cellNum++).setCellValue("Cooking Time");
            row.createCell(cellNum++).setCellValue("Preparation method");
            row.createCell(cellNum++).setCellValue("Nutrient values");
            row.createCell(cellNum++).setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
            row.createCell(cellNum++).setCellValue("Recipe URL");
        }

        // Get the last row number
        int lastRowNum = sheet.getLastRowNum();

        // Create a CellStyle for formatting
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        // Iterate through the RecipeData list and write to the Excel sheet
        for (RecipeInformation recipeInformation : outputData) {
            Row row = sheet.createRow(++lastRowNum);
            int cellNum = 0;
            // Iterate through the RecipeData fields using getter methods
            row.createCell(cellNum++).setCellValue(recipeInformation.getRecipeID());
            row.createCell(cellNum++).setCellValue(recipeInformation.getRecipeName());
            if (recipeInformation.getRecipeCategory() != null) {
                row.createCell(cellNum++).setCellValue(recipeInformation.getRecipeCategory().toString());
            } else {
                row.createCell(cellNum++).setCellValue("");
            }
            if (recipeInformation.getFoodCategory() != null){
                row.createCell(cellNum++).setCellValue(recipeInformation.getFoodCategory().toString());
            } else {
                row.createCell(cellNum++).setCellValue("");
            }
            row.createCell(cellNum++).setCellValue(recipeInformation.getIngredients());
            row.createCell(cellNum++).setCellValue(recipeInformation.getPreparationTime());
            row.createCell(cellNum++).setCellValue(recipeInformation.getCookingTime());
            row.createCell(cellNum++).setCellValue(recipeInformation.getPreparationMethod());
            row.createCell(cellNum++).setCellValue(recipeInformation.getNutrientValues());
            row.createCell(cellNum++).setCellValue("LCHF");
            row.createCell(cellNum++).setCellValue(recipeInformation.getRecipeURL());
        }

        // Write the workbook to the file
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }

        // Close the workbook
        workbook.close();
    }
}