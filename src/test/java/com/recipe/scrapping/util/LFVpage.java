package com.recipe.scrapping.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.recipe.scrapping.base.BaseClass;

public class LFVpage {

	 WebDriver driver;
	public LFVpage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
}
