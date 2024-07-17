package com.recipe.scrapping.test;


import com.recipe.scrapping.base.BaseClass;
import com.recipe.scrapping.util.AppConstants;
import org.openqa.selenium.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


public class RecipeScrappingRunner extends BaseClass {

    @Test
    public void startScraping() {
        System.out.println("Started scrapping");
    }


    @Test
    public void recipes() throws InterruptedException {
        driver.findElement(By.xpath(AppConstants.recipea_z)).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,350)", "");
        for(char alphabet = 'A'; alphabet <= 'A'; alphabet++) {
            driver.findElement(By.linkText(String.valueOf(alphabet))).click();
            try {
                WebElement paginationDiv = driver.findElement(By.xpath("//div[contains(text(), 'Goto Page')]"));
                List<WebElement> pageLinks = paginationDiv.findElements(By.tagName("a"));
                int totalPages = Integer.parseInt(pageLinks.get(pageLinks.size() - 1).getText());
                System.out.println("totalPages:::::::" + totalPages);
                for (int i = 1; i <= totalPages; i++) {
                    // Fetch the list of recipe elements once per page
                    List<WebElement> recipeElements = driver.findElements(By.xpath("//span[@class='rcc_recipename']/a"));
                    System.out.println("Number of recipes on page " + i + ": " + recipeElements.size());
                    for (int j = 0; j < recipeElements.size(); j++) {

                        if (recipeElements.size() > j) {
                            List<WebElement> recipeElementsPerEachPage = driver.findElements(By.xpath("//span[@class='rcc_recipename']/a"));
                            WebElement recipeElement = recipeElementsPerEachPage.get(j);
                            String recipeName = recipeElement.getText();
                            System.out.println(" recipeElement:::"+ recipeName);
                            String recipeUrl = recipeElement.getAttribute("href");
                            String recipeId = extractRecipeId(recipeUrl);

                            System.out.println("Recipe Name: " + recipeName + ", Recipe ID: " + recipeId);
                            recipeElement.click();
                            // Extract recipe details
                            WebElement tagsDiv = driver.findElement(By.xpath("//div[@class='tags' and @id='recipe_tags']"));
                            List<WebElement> tagLinks = tagsDiv.findElements(By.tagName("a"));
                            System.out.println("Tags:");
                            for (WebElement tagLink : tagLinks) {
                                String tagName = tagLink.getText();
                                System.out.println(tagName);
                            }
                            try {
                                String prepTime = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
                                System.out.println("Preparation Time: " + prepTime);
                            }
                            catch(Exception e){
                                System.out.println("Preparation Time not found.");
                            }
                            try {
                                String cookTime = driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
                                System.out.println("Cooking Time: " + cookTime);
                            }
                            catch(Exception e){
                                System.out.println("Cooking Time not found.");
                            }
                            try {
                                String totalTime = driver.findElement(By.xpath("//time[@itemprop='totalTime']")).getText();
                                System.out.println("Total Time: " + totalTime);
                            }
                            catch(Exception e){
                                System.out.println("Total Time not found.");
                            }
                            String servesInfo = driver.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblServes']")).getText();
                            System.out.println("Serves: " + servesInfo);

                            WebElement prepmethod = driver.findElement(By.xpath("//div[11]/section/div/div[1]"));
                            String strValue = prepmethod.getText();
                            if (strValue.contains("tsp") || strValue.contains("tbsp")) {
                            } else {
                                System.out.println("Preparation Method:" + " " + prepmethod.getText());
                            }
                            WebElement ingrendientlistA = driver.findElement(By.xpath("//div[@id=\"rcpinglist\"]"));
                            System.out.println("Ingredients List:" + " " + ingrendientlistA.getText());
                        }
                        String mainPageUrl = "https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=" + alphabet + "&pageindex=" + i;
                        driver.navigate().to(mainPageUrl);
                    }
                } }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static String extractRecipeId(String url) {
        // Extract the numeric part from the URL
        String idPart = url.replaceAll("[^0-9]", ""); // Remove all non-numeric characters
        return idPart;
    }
}

        /*
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement clickRecipeAtoZ = driver.findElement(By.xpath(AppConstants.clkReceipeAtoZ));
        clickRecipeAtoZ.click();

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,350)","");

//        driver.findElement(By.linkText("J")).click();
        for(char alphabet = 'N'; alphabet <= 'O'; alphabet++){
            driver.findElement(By.linkText(String.valueOf(alphabet))).click();
            int pageNumber =1;

            while (true){
                // Print the current page URL for debugging
                System.out.println("Current Page URL: " + driver.getCurrentUrl());

                // Get all recipe links on the current page
                List<WebElement> recipeLinks;
                try {
                    // Wait for the page to load completely

                    recipeLinks = driver.findElements(By.cssSelector(".rcc_recipecard .rcc_recipename a"));
                    System.out.println("Number of recipes found: " + recipeLinks.size());
                } catch (Exception e) {
                    System.out.println("Error finding recipe links on page " + pageNumber + " for letter " + alphabet + ": " + e.getMessage());
                    break; // Exit the loop for the current letter
                }

                // Loop through each recipe link and extract data
                for (WebElement recipeLink : recipeLinks) {
//                    recipeUrl = driver.findElement(By.cssSelector(".rcc_rcpname")).click();
                    String recipeUrl = recipeLink.getAttribute("href");
//                    driver.navigate().back();
//                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".rcc_recipecard a"))); // Ensure page is loaded

                    try {
                        // Extract recipe details
                        String[] urlParts = recipeUrl.split("-");
                        String recipeId = urlParts[urlParts.length - 1].replaceAll("\\D+", ""); // Extract numbers from the last part
                        String recipeName = recipeLink.getText();

                        // Print or store the recipe details
                        System.out.println("Recipe ID: " + recipeId);
                        System.out.println("Recipe Name: " + recipeName);
//                        System.out.println("Description: " + description);
//                        System.out.println("Ingredients: " + ingredients.toString());
                    } catch (Exception e) {
                        System.out.println("Error extracting details from recipe at " + recipeUrl + ": " + e.getMessage());
                    }

                    // Navigate back to the alphabet list page
                    driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=N&pageindex=1");
                }

                // Check for the next page link
                List<WebElement> pageLinks = driver.findElements(By.cssSelector(".rcpagelist a"));
                boolean nextPageExists = false;

                for (WebElement pageLink : pageLinks) {
                    if (pageLink.getText().equals(String.valueOf(pageNumber + 1))) {
                        pageLink.click();
                        nextPageExists = true;
                        pageNumber++;
                        break;
                    }
                }

                if (!nextPageExists) {
                    // No more pages
                    break;
                }
            }
            }
        }

         */




