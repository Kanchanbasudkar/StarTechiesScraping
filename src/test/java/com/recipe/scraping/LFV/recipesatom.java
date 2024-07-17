package com.recipe.scraping.LFV;

import java.util.List;

import org.apache.logging.log4j.core.util.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.recipe.scrapping.base.BaseClass;
import com.recipe.scrapping.util.AppConstants;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class recipesatom extends BaseClass{



@Test
public void clickAtoZrecipe(WebElement[] recipercard) throws InterruptedException {
	driver.findElement(By.xpath(AppConstants.recipea_z)).click();
	JavascriptExecutor js = (JavascriptExecutor)driver;
	js.executeScript("window.scrollBy(0,350)", "");
	for(char alphabet = 'A'; alphabet <= 'M'; alphabet++){
		//driver.findElement(By.xpath(AppConstants.alphabet)).click();
		driver.findElement(By.linkText(String.valueOf(alphabet))).click();
		//int pagenumber=1;
		//while(true) {
			System.out.println("current page url: " +driver.getCurrentUrl());
		//
        WebElement element = driver.findElement(By.xpath("//div[@id='rcp37154']/div[2]/span/text[1]"));
		String id = element.getText().trim();
        String lastFiveDigits = id.substring(id.length() - 5);
        System.out.println(lastFiveDigits); 
        
        
        
		driver.findElement(By.xpath("//a[text()=\"A Checkerboard Of Roses ( Flower Arrangements)\"]")).click();
		WebElement recipe1=driver.findElement(By.xpath("//span[@id=\"ctl00_cntrightpanel_lblRecipeName\"]"));
		System.out.println(recipe1.getText());
		
		
		
		
	}
}
}


