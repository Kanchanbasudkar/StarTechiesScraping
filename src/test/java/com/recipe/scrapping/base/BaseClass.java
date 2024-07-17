package com.recipe.scrapping.base;

import com.recipe.scrapping.util.ReadConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.sql.SQLException;


public class BaseClass {
    public static WebDriver driver;

    @BeforeTest
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();

        ReadConfig readConfig = new ReadConfig();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
  //      options.addArguments("--incognito");
        options.addArguments("--headless");
        options.addArguments("--window-position=2000,0");  // To 2nd monitor.
        driver = new ChromeDriver(options);
        driver.navigate().to(readConfig.loadConfig().getProperty("url"));
        driver.manage().window().maximize();
    }
    @AfterTest
    public void tearDown() throws SQLException {
        driver.close();
    }
}
