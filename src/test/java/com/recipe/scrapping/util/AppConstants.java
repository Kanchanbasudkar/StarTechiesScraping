package com.recipe.scrapping.util;

public class AppConstants {

    public static final String Recipe_AtoZ = "//a[@title=\"Recipea A to Z\"]";
    public static final String AlphabetXpath= "//a[contains(@class, 'ctl00_cntleftpanel_mnuAlphabets') and text()='A']";
    public static final String PageNumberXpath= "//div[@style='text-align:right;padding-bottom:15px;']//a[@class='respglink']";
    public static final String RecipeName = "//div[@class='rcc_rcpcore']//span[@class='rcc_recipename']//a";
    public static final String RecipeId = "//div[@style='float:right;width:75px;height:80px;margin-right:5px;margin-left:10px;text-align:center;']//span";
    public static final String IngrediantsList = "//div[@id='rcpinglist']";
    public static final String CookingTime = "//time[@itemprop=\"cookTime\"]";
    public static final String NoOfServings = "//span[@id=\"ctl00_cntrightpanel_lblSrv\"]";
    public static final String recipeCardXpath = "//div[@class=\"rcc_recipecard\"]";
    public static final String Ingrediants = "rcpinglist";
    public static final String PreparationTime = "//time[@itemprop=\"prepTime\"]";
    public static final String CookTime = "//time[@itemprop=\"cookTime\"]";
    public static final String Tag = "//div[@class=\"tags\"]";
    public static final String CuisineCategory = "//a[@href=\"recipes-for-veg-recipes-south-indian-44\"]";
    public static final String PreparationMethod ="//ol[@itemprop=\"recipeInstructions\"]";
    public static final String NutrientValues = "//table[@id=\"rcpnutrients\"]";





}
