package com.recipe.scrapping.test;


import com.recipe.scrapping.base.BaseClass;
import org.testng.annotations.Test;

public class RecipeScrappingRunner extends BaseClass {

    @Test
    public void startScraping(){
        System.out.println("started scrapping");
    }
}
