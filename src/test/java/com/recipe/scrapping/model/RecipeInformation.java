package com.recipe.scrapping.model;

import com.recipe.scrapping.Enum.FoodCategory;
import com.recipe.scrapping.Enum.RecipeCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeInformation {
    Integer recipeID;
    String recipeName;
    RecipeCategory recipeCategory;
    FoodCategory foodCategory;
    String ingredients;
    String preparationTime;
    String cookingTime;
    String tag;
    String noOfServings;
    String cuisineCategory;
    String recipeDescription;
    String preparationMethod;
    String nutrientValues;
    String recipeURL;
}
