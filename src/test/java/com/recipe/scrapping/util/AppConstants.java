package com.recipe.scrapping.util;

public class AppConstants {
	
	//public static final String RecipeUrl = "https://www.tarladalal.com/RecipeAtoZ.aspx";
	public static final String RecipeAToZ = "//a[@title='Recipea A to Z']";	 
	public static final String recipes_list = "//span[@class='rcc_recipename']/a";
	public static final String ingredients_list = "rcpinglist";
	public static final String preparation_time = "time[itemprop='prepTime']";
	public static final String cooking_time = "time[itemprop='cookTime']";
	public static final String tags =  "//div[@id='recipe_tags']/a/span";
	public static final String number_servings = "//span[@id='ctl00_cntrightpanel_lblServes']";
	public static final String prep_method = "//div[@id='ctl00_cntrightpanel_pnlRcpMethod']";
	public static final String nutrient_value ="//span[@itemprop='nutrition']";
	
}
