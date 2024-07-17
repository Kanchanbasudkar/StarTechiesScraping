package com.recipe.scrapping.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ReadExcel {
	
	   public static void main(String[] args) {
		
	        System.setProperty("user.dir", "//ingredientsToEliminate.xlsx");

	      //WebDriver driver = new ChromeDriver();


	        try {
	            List<String> ingredientsToRemove = readIngredientsFromExcel("ingredientsToEliminate.xlsx");

	            for (String ingredient : ingredientsToRemove) {
	               
	              //  WebElement ingredientCheckbox = driver.findElement(By.xpath("//div[@id=\"rcpinglist\"]"));
	             //   ingredientCheckbox.click();

//	                WebDriverWait wait = new WebDriverWait(driver, 10);
//	                wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton"))); // Adjust this based on your site's behavior
	            }         
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 

	    }

		public static List<String> readIngredientsFromExcel(String filePath) throws Exception {
	        List<String> ingredientsList = new ArrayList<>();
	        
		File excelFile = new File(System.getProperty("user.dir")+"\\ingredientsToEliminate.xlsx");
		FileInputStream fis = new FileInputStream(excelFile);
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		//XSSFSheet sheet = workbook.getSheet("Sheet");
		XSSFSheet sheet = workbook.getSheetAt(0); 
		
		 for (Row row : sheet) {
	            Cell cell = row.getCell(0); // Assuming ingredient names are in the first column

	            if (cell != null && cell.getCellType() == CellType.STRING) {
	                ingredientsList.add(cell.getStringCellValue().trim());
	            }
	        }

	        workbook.close();
	        fis.close();
	        return ingredientsList;
	
	}
}
