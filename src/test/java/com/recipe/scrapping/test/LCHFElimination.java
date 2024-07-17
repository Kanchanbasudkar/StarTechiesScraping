package com.recipe.scrapping.test;


import com.recipe.scrapping.base.BaseClass;
import com.recipe.scrapping.util.AppConstants;
//import com.recipe.scrapping.util.RecipeDAO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


public class LCHFElimination extends BaseClass {

    private static final List<String> EXCLUSION_KEYWORDS = Arrays.asList(
            "Ham", "sausage", "tinned fish", "tuna", "sardines", "yams", "beets", "parsnip", "turnip", "rutabagas", "carrot",
            "yuca", "kohlrabi", "celery root", "horseradish", "daikon", "jicama", "radish", "pumpkin", "squash", "Whole fat milk",
            "low fat milk", "fat free milk", "Evaporated milk", "condensed milk", "curd", "buttermilk", "ice cream", "flavored milk",
            "sweetened yogurt", "soft cheese", "grain", "Wheat", "oat", "barely", "rice", "millet", "jowar", "bajra", "corn",
            "dal", "lentil", "banana", "mango", "papaya", "plantain", "apple", "orange", "pineapple", "pear", "tangerine",
            "all melon varieties", "peach", "plum", "nectarine", "Avocado", "olive oil", "coconut oil", "soybean oil", "corn oil",
            "safflower oil", "sunflower oil", "rapeseed oil", "peanut oil", "cottonseed oil", "canola oil", "mustard oil",
            "sugar", "jaggery", "glucose", "fructose", "corn syrup", "cane sugar", "aspartame", "cane solids", "maltose",
            "dextrose", "sorbitol", "mannitol", "xylitol", "maltodextrin", "molasses", "brown rice syrup", "splenda", "nutra sweet",
            "stevia", "barley malt", "potato", "corn", "pea"
    );

    @Test
    public void startScraping() {
        System.out.println("Elimination scrapping");
    }

    @Test
    public void recipes() throws InterruptedException {
        driver.findElement(By.xpath(AppConstants.recipea_z)).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,350)", "");

        for (char alphabet = 'N'; alphabet <= 'N'; alphabet++) {
            driver.findElement(By.linkText(String.valueOf(alphabet))).click();

            try {
                WebElement paginationDiv = driver.findElement(By.xpath(AppConstants.gotoPage));
                List<WebElement> pageLinks = paginationDiv.findElements(By.tagName("a"));
                int totalPages = Integer.parseInt(pageLinks.get(pageLinks.size() - 1).getText());
                System.out.println("totalPages:::::::" + totalPages);

                for (int i = 1; i <= totalPages; i++) {
                    List<WebElement> recipeElements = driver.findElements(By.xpath(AppConstants.recipeCards));
                    System.out.println("Number of recipes on page " + i + ": " + recipeElements.size());

                    for (int j = 0; j < recipeElements.size(); j++) {
                        if (recipeElements.size() > j) {
                            List<WebElement> recipeElementsPerEachPage = driver.findElements(By.xpath(AppConstants.recipeCards));
                            WebElement recipeElement = recipeElementsPerEachPage.get(j);
                            String recipeName = recipeElement.getText();
                            System.out.println(" recipeElement:::" + recipeName);
                            String recipeUrl = recipeElement.getAttribute("href");
                            String recipeId = extractRecipeId(recipeUrl);

                            System.out.println("Recipe Name: " + recipeName + ", Recipe ID: " + recipeId);
                            recipeElement.click();

                            // Extract recipe details
                            WebElement tagsDiv = driver.findElement(By.xpath(AppConstants.tagsDiv));
                            List<WebElement> tagLinks = tagsDiv.findElements(By.tagName("a"));
                            System.out.println("Tags:");
                            for (WebElement tagLink : tagLinks) {
                                String tagName = tagLink.getText();
                                System.out.println(tagName);
                            }

                            String prepTime = "";
                            try {
                                prepTime = driver.findElement(By.xpath(AppConstants.preparationTime)).getText();
                                System.out.println("Preparation Time: " + prepTime);
                            } catch (Exception e) {
                                System.out.println("Preparation Time not found.");
                            }

                            String cookTime = "";
                            try {
                                cookTime = driver.findElement(By.xpath(AppConstants.cookingTime)).getText();
                                System.out.println("Cooking Time: " + cookTime);
                            } catch (Exception e) {
                                System.out.println("Cooking Time not found.");
                            }

                            String totalTime = "";
                            try {
                                totalTime = driver.findElement(By.xpath("//time[@itemprop='totalTime']")).getText();
                                System.out.println("Total Time: " + totalTime);
                            } catch (Exception e) {
                                System.out.println("Total Time not found.");
                            }

                            String servesInfo = driver.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblServes']")).getText();
                            System.out.println("Serves: " + servesInfo);

                            WebElement prepmethod = driver.findElement(By.xpath("//div[11]/section/div/div[1]"));
                            String preparationMethod = prepmethod.getText();
                            if (!(preparationMethod.contains("tsp") || preparationMethod.contains("tbsp"))) {
                                System.out.println("Preparation Method:" + " " + preparationMethod);
                            }

                            WebElement ingredientListElement = driver.findElement(By.xpath("//div[@id=\"rcpinglist\"]"));
                            String ingredients = ingredientListElement.getText();
                            System.out.println("Ingredients List:" + " " + ingredients);

                            // Filter based on exclusion keywords
                            List<String> matchedKeywords = EXCLUSION_KEYWORDS.stream()
                                    .filter(keyword -> ingredients.toLowerCase().contains(keyword.toLowerCase()) || preparationMethod.toLowerCase().contains(keyword.toLowerCase()))
                                    .collect(Collectors.toList());

                            if (!matchedKeywords.isEmpty()) {
                                // Print the excluded recipe details
                                System.out.println("Recipe excluded due to the following keywords: " + matchedKeywords);
                                System.out.println("Excluded Recipe Name: " + recipeName);
                                System.out.println("Excluded Recipe URL: " + recipeUrl);
                            } else {
                                // Insert the recipe into the database if it doesn't contain exclusion keywords
                                // RecipeDAO.insertRecipe(recipeName, recipeUrl, prepTime, cookTime, totalTime, servesInfo, ingredients, preparationMethod);
                                System.out.println("Recipe included: " + recipeName);
                            }

                            String mainPageUrl = "https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=" + alphabet + "&pageindex=" + i;
                            driver.navigate().to(mainPageUrl);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String extractRecipeId(String url) {
        String idPart = url.replaceAll("[^0-9]", ""); // Remove all non-numeric characters
        return idPart;
    }

    private String extractRecipeCategory() {
        // Logic to extract recipe category
        try {
            WebElement categoryElement = driver.findElement(By.xpath("//span[@itemprop='recipeCategory']"));
            return categoryElement.getText();
        } catch (Exception e) {
            System.out.println("Recipe Category not found.");
            return "Unknown";
        }
    }

    private String extractFoodCategory() {
        // Logic to extract food category
        try {
            WebElement foodCategoryElement = driver.findElement(By.xpath("//span[@itemprop='recipeCuisine']"));
            return foodCategoryElement.getText();
        } catch (Exception e) {
            System.out.println("Food Category not found.");
            return "Unknown";
        }
    }
}








