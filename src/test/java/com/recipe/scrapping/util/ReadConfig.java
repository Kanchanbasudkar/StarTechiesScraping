package com.recipe.scrapping.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {
    static Properties props = new Properties();
    public static Properties loadConfig() {


        System.out.println("executing LoadProperties.....");
        try {

            System.out.println(System.getProperty("user.dir") + "/src/test/resources/config.properties");

            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/config.properties");
            props.load(ip);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;

    }
}
