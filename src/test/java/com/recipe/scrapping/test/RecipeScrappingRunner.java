package com.recipe.scrapping.test;


import com.recipe.scrapping.Enum.FoodCategory;
import com.recipe.scrapping.Enum.RecipeCategory;
import com.recipe.scrapping.base.BaseClass;
import com.recipe.scrapping.model.RecipeInformation;
import com.recipe.scrapping.util.AppConstants;
import com.recipe.scrapping.util.ReadExcel;
import com.recipe.scrapping.util.WriteExcel;
import net.bytebuddy.description.field.FieldDescription;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class RecipeScrappingRunner extends BaseClass {

    // Connection object
    static Connection con = null;
    // Statement object
    private static Statement stmt;
    // Constant for Database URL
    public static String DB_URL = "jdbc:postgresql://localhost:5432/starTechies";
    //Database Username
    public static String DB_USER = "postgres";
    // Database Password
    public static String DB_PASSWORD = "admin";

    String mainPageUrl = "";

    @BeforeTest
    public void setUp() throws Exception {
        try{
// Database connection
            String dbClass = "org.postgresql.Driver";
            Class.forName(dbClass).newInstance();
// Get connection to DB
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
// Statement object to send the SQL statement to the Database
            stmt = con.createStatement();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void startScraping() throws InterruptedException, IOException {

        List<RecipeInformation> eliminatorRecipeInformation = new ArrayList<>();
        List<RecipeInformation> toAddRecipeInformation = new ArrayList<>();
        Map<String, List<String>> eliminators_ToAdd_Map = ReadExcel.readExcelDataLCHFElimination();
        System.out.println("started scrapping");
        clickAToZ();

        Map<Character, Integer> alphabetVsPageCount = new HashMap<>();
//        for (int i = 65; i<=90; i++) {
        for (int i = 65; i <= 65; i++) {
            char alphabet = (char) i;
            alphabetVsPageCount.put(alphabet, getPageNumbersByAlphabet(String.valueOf(alphabet)));
        }
        System.out.println("End of alphabet vs pages");
        Set<Character> alphabetChar = alphabetVsPageCount.keySet();
        Map<Integer, RecipeInformation> recipeInformationMap = new HashMap<>();
        for (Character alphabet : alphabetChar) {
            Integer numberOfPages = alphabetVsPageCount.get(alphabet);
//            for (int pageNum = 1; pageNum <= numberOfPages; pageNum++) {
            for (int pageNum = 1; pageNum <= 1; pageNum++) {
                navigateAlphabetAndPage(alphabet.toString(), pageNum);
                recipeInformationMap = collectRecipeInformationWithRecipeCard();
                Set<Integer> recipeIds = recipeInformationMap.keySet();
                for (Integer recipe : recipeIds) {
                    driver.navigate().to(recipeInformationMap.get(recipe).getRecipeURL());
                    collectRecipeInformation(recipeInformationMap.get(recipe));
                }
                System.out.println("Completed " + alphabet + " page number " + pageNum);
            }
            System.out.println("Completed " + alphabet);
        }

        for (RecipeInformation recipeInformation :
        recipeInformationMap.values()
             ) {
            Boolean eliminatorFoundInRecipe = Boolean.FALSE;
            Boolean toAddFoundInRecipe = Boolean.FALSE;
            List<String> eliminatorList = eliminators_ToAdd_Map.get(AppConstants.ELIMINATORS);
            List<String> toAddList = eliminators_ToAdd_Map.get(AppConstants.TO_ADD);
            for (String eliminatorItem: eliminatorList
                 ) {
                if(recipeInformation.getIngredients().contains(eliminatorItem)){
                    eliminatorFoundInRecipe = Boolean.TRUE;
                    break;
                }
            }
            for (String toAddItem: toAddList
                 ) {
                if(recipeInformation.getIngredients().contains(toAddItem)){
                    toAddFoundInRecipe = Boolean.TRUE;
                    break;
                }
            }

            if(!eliminatorFoundInRecipe){
                eliminatorRecipeInformation.add(recipeInformation);
            }
            if(toAddFoundInRecipe){
                toAddRecipeInformation.add(recipeInformation);
            }

        }
//        recipeInformationMap.values().stream().map(x -> x.getIngredients().contains())

        WriteExcel.writeInLCHFSheet(eliminatorRecipeInformation, "outputDataEliminatedRecipies");
        WriteExcel.writeInLCHFSheet(toAddRecipeInformation, "outputDataToAddRecipies");
        eliminatorRecipeInformation.stream().forEach(recipeInformation -> {
            try {
                insertDataEliminatorTable(recipeInformation);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        System.out.println("end of startscraping");
    }

    public void insertDataEliminatorTable(RecipeInformation recipeInformation) throws SQLException {
        String sql = "INSERT INTO eliminator_recipe(\n" +
                "  recipe_id,\n" +
                "  recipe_name,\n" +
                "  recipe_category,\n" +
                "  food_category,\n" +
                "  ingredients,\n" +
                "  preparation_time,\n" +
                "  cooking_time,\n" +
                "  preparation_method,\n" +
                "  nutrient_values,\n" +
                "  recipe_url\n" +
                ") VALUES ("+recipeInformation.getRecipeID()+",'"+recipeInformation.getRecipeName()+
                "','"+recipeInformation.getRecipeCategory()+"','"+recipeInformation.getFoodCategory()+
                "','"+recipeInformation.getIngredients()+"','"+recipeInformation.getPreparationTime()+
                "','"+recipeInformation.getCookingTime()+"','"+recipeInformation.getPreparationMethod()+
                "','"+recipeInformation.getNutrientValues()+"','"+recipeInformation.getRecipeURL()+"')";
        stmt.executeUpdate(sql);

    }

    public int getPageNumbersByAlphabet(String alphabet) {
        driver.navigate().to(AppConstants.urlByAphabet.concat(alphabet));
        return getPageCount();
    }

    public void navigateAlphabetAndPage(String alphabet, Integer pageNumber) {
        driver.navigate().to(AppConstants.urlByAphabet.concat(alphabet).concat(AppConstants.urlPageIndex).concat(pageNumber.toString()));
    }

    public void clickAToZ() {
        driver.findElement(By.xpath(AppConstants.Recipe_AtoZ)).click();
    }

    public int getPageCount() {
        int maxPageNumber = 0;
        List<WebElement> pageNumbers = driver.findElements(By.xpath(AppConstants.PageNumberXpath));
        for (WebElement element : pageNumbers) {
            int pageNumber = Integer.parseInt(element.getText());
            if (maxPageNumber < pageNumber) {
                maxPageNumber = pageNumber;
            }
        }
        return maxPageNumber;
    }


    public Map<Integer, RecipeInformation> collectRecipeInformationWithRecipeCard() {
        Map<Integer, RecipeInformation> recipeInformationMap = new HashMap<>();
        List<WebElement> recipeCardList = driver.findElements(By.xpath(AppConstants.recipeCardXpath));
        mainPageUrl = driver.getCurrentUrl();

        for (WebElement recipeId : recipeCardList) {
            Integer recipeIdIntValue = Integer.parseInt(recipeId.getAttribute("id").replace("rcp", ""));
            String recipeUrl = recipeId.findElement(By.xpath(".//a")).getAttribute("href");
            String recipeName = recipeId.findElement(By.xpath(".//a")).getText();
            RecipeInformation recipeInformation = new RecipeInformation();
            recipeInformation.setRecipeID(recipeIdIntValue);
            recipeInformation.setRecipeURL(recipeUrl);
            recipeInformation.setRecipeName(recipeName);
            recipeInformationMap.put(recipeIdIntValue, recipeInformation);
        }
        return recipeInformationMap;
    }

    public void clickEachRecipe() throws InterruptedException {
        List<WebElement> recipeNameList = driver.findElements(By.xpath(AppConstants.RecipeName));
        for (WebElement recipeName : recipeNameList) {
            recipeName.click();
            driver.navigate().to(mainPageUrl);
        }
    }

    public void collectRecipeInformation(RecipeInformation recipeInformation) {
        String recipeDescriptionValue = "";
        StringBuilder tagNames = new StringBuilder();
        try {
            WebElement cookingTime = driver.findElement(By.xpath(AppConstants.CookTime));
            recipeInformation.setCookingTime(cookingTime.getText());
        } catch (Exception ex) {
        }
        try {
            WebElement preparationTime = driver.findElement(By.xpath(AppConstants.PreparationTime));
            recipeInformation.setPreparationTime(preparationTime.getText());
        } catch (Exception ex) {
        }
        try {
            List<WebElement> tag = driver.findElements(By.xpath(AppConstants.Tag));

            for (WebElement ele : tag) {
                tagNames.append(ele.getText() + ", ");
            }
            recipeInformation.setTag(tagNames.toString());
        } catch (Exception ex) {
        }
        try {
            WebElement noOfServings = driver.findElement(By.xpath(AppConstants.NoOfServings));
            recipeInformation.setNoOfServings(noOfServings.getText());
        } catch (Exception ex) {
        }
        try {
            WebElement recipeDescription = driver.findElement(By.xpath(AppConstants.RecipeDescription));
            recipeDescriptionValue = recipeDescription.getText();
            recipeInformation.setRecipeDescription(recipeDescriptionValue);
        } catch (Exception ex) {
        }
        try {
            WebElement preparationMethod = driver.findElement(By.xpath(AppConstants.PreparationMethod));
            recipeInformation.setPreparationMethod(preparationMethod.getText());
        } catch (Exception ex) {
        }
        try {
            WebElement nutrientsValues = driver.findElement(By.xpath(AppConstants.NutrientValues));
            recipeInformation.setNutrientValues(nutrientsValues.getText());
        } catch (Exception ex) {
        }
        try {
            WebElement ingrediants = driver.findElement(By.id(AppConstants.Ingrediants));
            recipeInformation.setIngredients(ingrediants.getText());
        } catch (Exception ex) {
        }
        try {
            setFoodCategory(recipeInformation, tagNames.toString());
        } catch (Exception ex) {
        }
        try {
            setRecipeCategory(recipeInformation, tagNames.toString(), recipeDescriptionValue);
        } catch (Exception ex) {
        }
    }

    public void setFoodCategory(RecipeInformation recipeInformation, String tagName) {
        if (tagName.toLowerCase().contains(FoodCategory.JAIN.toString().toLowerCase())) {
            recipeInformation.setFoodCategory(FoodCategory.JAIN);
        } else if (tagName.toLowerCase().contains(FoodCategory.NONVEG.toString().toLowerCase())) {
            recipeInformation.setFoodCategory(FoodCategory.NONVEG);
        } else if (tagName.toLowerCase().contains(FoodCategory.VEGAN.toString().toLowerCase())) {
            recipeInformation.setFoodCategory(FoodCategory.VEGAN);
        } else if (tagName.toLowerCase().contains(FoodCategory.EGGITARIAN.toString().toLowerCase())) {
            recipeInformation.setFoodCategory(FoodCategory.EGGITARIAN);
        }
    }


    public void setRecipeCategory(RecipeInformation recipeInformation, String tagName, String recipeDescription) {
        if (tagName.toLowerCase().contains(RecipeCategory.BREAKFAST.toString().toLowerCase()) || recipeDescription.toLowerCase().contains(RecipeCategory.BREAKFAST.toString().toLowerCase())) {
            recipeInformation.setRecipeCategory(RecipeCategory.BREAKFAST);
        } else if (tagName.toLowerCase().contains(RecipeCategory.DINNER.toString().toLowerCase()) || recipeDescription.toLowerCase().contains(RecipeCategory.DINNER.toString().toLowerCase())) {
            recipeInformation.setRecipeCategory(RecipeCategory.DINNER);
        } else if (tagName.toLowerCase().contains(RecipeCategory.LUNCH.toString().toLowerCase()) || recipeDescription.toLowerCase().contains(RecipeCategory.LUNCH.toString().toLowerCase())) {
            recipeInformation.setRecipeCategory(RecipeCategory.LUNCH);
        } else if (tagName.toLowerCase().contains(RecipeCategory.SNACK.toString().toLowerCase()) || recipeDescription.toLowerCase().contains(RecipeCategory.SNACK.toString().toLowerCase())) {
            recipeInformation.setRecipeCategory(RecipeCategory.SNACK);
        }
    }

}
