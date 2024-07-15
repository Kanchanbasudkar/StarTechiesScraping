package com.recipe.scrapping.test;


import com.recipe.scrapping.base.BaseClass;
import com.recipe.scrapping.model.RecipeInformation;
import com.recipe.scrapping.util.AppConstants;
import net.bytebuddy.description.field.FieldDescription;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeScrappingRunner extends BaseClass {

    String mainPageUrl = "";

    @Test
    public void startScraping() throws InterruptedException {
        System.out.println("started scrapping");
//        clickAToZ();
//        collectRecipeInformationWithRecipeCard();
//        clickEachRecipe();
        collectRecipeInformation();
    }

    public void clickAToZ(){
        driver.findElement(By.xpath(AppConstants.Recipe_AtoZ)).click();
    }

    public int getPageCount(){
        int maxPageNumber = 0;
        List<WebElement> pageNumbers =  driver.findElements(By.xpath(AppConstants.PageNumberXpath));
        for (WebElement element: pageNumbers) {
            int pageNumber = Integer.parseInt(element.getText());
            if (maxPageNumber < pageNumber){
                maxPageNumber = pageNumber;
            }
        }
        return maxPageNumber;
    }


    public void collectRecipeInformationWithRecipeCard(){
        Map<Integer, RecipeInformation> recipeInformationMap = new HashMap<>();
        List<WebElement> recipeCardList =  driver.findElements(By.xpath(AppConstants.recipeCardXpath));
        mainPageUrl = driver.getCurrentUrl();

        for (WebElement recipeId:
                recipeCardList) {
            Integer recipeIdIntValue = Integer.parseInt(recipeId.getAttribute("id").replace("rcp",""));
            String recipeUrl = recipeId.findElement(By.xpath(".//a")).getAttribute("href");
            String recipeName = recipeId.findElement(By.xpath(".//a")).getText();
            RecipeInformation recipeInformation = new RecipeInformation();
            recipeInformation.setRecipeID(recipeIdIntValue);
            recipeInformation.setRecipeURL(recipeUrl);
            recipeInformation.setRecipeName(recipeName);
            recipeInformationMap.put(recipeIdIntValue, recipeInformation);
        }
        System.out.println("done with getting details");
    }

    public void clickEachRecipe() throws InterruptedException {
        List<WebElement> recipeNameList =  driver.findElements(By.xpath(AppConstants.RecipeName));
        for (WebElement recipeName:
        recipeNameList) {
            recipeName.click();
//            driver.wait(1000l);
            driver.navigate().to(mainPageUrl);
        }
    }

    public void collectRecipeInformation(){
        RecipeInformation recipeInformation = new RecipeInformation();
        WebElement cookingTime =  driver.findElement(By.xpath(AppConstants.CookTime));
        recipeInformation.setCookingTime(cookingTime.getText());
        //System.out.println("collectRecipeInformation end");
        WebElement preparationTime = driver.findElement(By.xpath(AppConstants.PreparationTime));
        recipeInformation.setPreparationTime(preparationTime.getText());
        WebElement tag = driver.findElement(By.xpath(AppConstants.Tag));
        recipeInformation.setTag(tag.getText());
        WebElement noOfServings = driver.findElement(By.xpath(AppConstants.NoOfServings));
        recipeInformation.setNoOfServings(noOfServings.getText());

    }

}
