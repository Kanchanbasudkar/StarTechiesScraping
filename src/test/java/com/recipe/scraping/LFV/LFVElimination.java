/*package com.recipe.scraping.LFV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.recipe.scrapping.base.BaseClass;

public class LFVElimination extends BaseClass{
	
	@Test
	public void elimination() {
		List<String> eliminators = Arrays.asList(new String[] { "pork", "Meat", "Poultry", "Fish", "Sausage","ham", "salami", "bacon", "milk", "cheese",
											"yogurt", "butter", "Ice cream", "egg", "prawn", "Oil", "olive oil", "coconut oil","soybean oil", 
											"corn oil", "safflower oil", "rapeseed oil", "peanut oil", "cottonseed oil", "canola oil", "mustard oil",
											"cereals", "tinned vegetable", "bread", "maida" , "atta", "sooji", "poha", "cornflake","cornflour", "pasta", 
											"White rice", "pastry",  "cakes", "biscuit", "soy", "soy milk", "white miso paste", "soy sauce", 
											"soy curls", "edamame", "soy yogurt", "soy nut", "tofu", "pies", "Chip", "cracker", "potatoe", "sugar",
											"jaggery", "glucose", "fructose", "corn syrup", "cane sugar", "aspartame", "cane solid", "maltose", 
											"dextrose", "sorbitol", "mannitol", "xylitol", "maltodextrin", "molasses", "brown rice syrup", "splenda",
											"nutra sweet", "stevia", "barley malt"	});
														
		driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();

		// run in a loop for all recipe in a page
		List<String> pageBeginsWithList = Arrays.asList(new String[] {"A", "B", "C", "D", "E", "F", "G", "H","I", "J", "K", "L", "M",});
		for (int j = 1; j <= 1; j++) {
			int pageindex = j;
			driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?pageindex=" + j);
			List<WebElement> recipeCardElements = driver.findElements(By.xpath("//div[@class='rcc_recipecard']"));
			List<String> recipeUrls = new ArrayList<>();
			Map<String, String> recipeIdUrls = new HashMap<>();

			// Looping through all recipes Web elements and generating a navigation URL
			recipeCardElements.stream().forEach(recipeCardElement -> {
				recipeUrls.add("https://www.tarladalal.com/" + recipeCardElement.findElement(By.xpath("//span[@class='rcc_recipename']/a")).getDomAttribute("href"));
				// example: recipeIdUrls.put("id","url");
				recipeIdUrls.put(recipeCardElement.getDomAttribute("id").replace("rcp", ""),
						"https://www.tarladalal.com/"
								+ recipeCardElement.findElement(By.tagName("a")).getDomAttribute("href"));

			}}}
			
			
			
			
			
			
			
			
			*/

																
																
																
																
																								
																
																
																
																
																
																
																
																
																
																
			
		
		
		
