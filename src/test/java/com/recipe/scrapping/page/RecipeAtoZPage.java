package com.recipe.scrapping.page;

import com.recipe.scrapping.base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class RecipeAtoZPage extends BaseClass {
        WebDriver driver;
        public RecipeAtoZPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
//
//    @FindBy(xpath = "//a[@title='Recipea A to Z']")
//    WebElement clkReceipeAtoZ;
//
//    public void clickReceipeAtoZ() {
//      clkReceipeAtoZ.click();
//    }

}