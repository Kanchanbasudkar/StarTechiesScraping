package com.recipe.scrapping.lvf.Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.recipe.scrapping.base.BaseClass;
import com.recipe.scrapping.util.AppConstants;

public class RecipePage extends BaseClass {
	
	
//	 @Test
//		public void recipesAtoZ() {
//			driver.findElement(By.xpath(AppConstants.RecipeAToZ)).click();
////			JavascriptExecutor js = (JavascriptExecutor)driver;
////			js.executeScript("window.scrollBy(0,350)", "");
//			for(char alphabet = 'N'; alphabet <= 'O'; alphabet++){
//				//driver.findElement(By.xpath(AppConstants.RecipeAlphabetN)).click();
//				driver.findElement(By.linkText(String.valueOf(alphabet))).click();
//				JavascriptExecutor js = (JavascriptExecutor)driver;
//				js.executeScript("window.scrollBy(0,550)", "");			
//			}
	//--------------------------------		
//			int totalPages = 10;
//			for(int i = 1;i<=10;i++) {
//				WebElement nextButton = driver.findElement(By.xpath("contains(@class = \'respglink\')"));
//				nextButton.click();
//			}
//			List<WebElement> recipeElements = driver.findElements(By.xpath("//acontains(@href,'aam-chana-achar-rajasthani-pickle-3904r')")); 
//	        for (WebElement recipeElement : recipeElements) {
//	        	String recipeTitle = recipeElement.findElement(By.xpath("//acontains(@href,'aam-chana-achar-rajasthani-pickle-3904r')")).getText(); 
//	        System.out.println("Recipe Title" +recipeTitle);
//	        }
//	 }
//-----------------------------------------------------	
//@Test
//	public void recipesAtoZ() {
//		driver.findElement(By.xpath(AppConstants.RecipeAToZ)).click();
//	}

//	@Test
//	public void recipeAlphabeteN() {
//
//		JavascriptExecutor js = (JavascriptExecutor)driver;
//		js.executeScript("window.scrollBy(0,350)", "");
//		for(char alphabet = 'N'; alphabet <= 'O'; alphabet++){
//		//driver.findElement(By.xpath(AppConstants.RecipeAlphabetN)).click();
//		driver.findElement(By.linkText(String.valueOf(alphabet))).click();
//	}
//	}

}
