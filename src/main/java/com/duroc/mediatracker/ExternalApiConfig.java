package com.duroc.mediatracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class ExternalApiConfig {
    private Properties properties;
    private final String configFilePath = "src/main/resources/api.properties";

    public ExternalApiConfig() {
        File ConfigFile = new File(configFilePath);
        try {
            FileInputStream configFileReader = new FileInputStream(ConfigFile);
            properties = new Properties();
            try {
                properties.load(configFileReader);
                configFileReader.close();
            } catch (IOException e)
            { System.out.println(e.getMessage()); }
        }  catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException("config.properties not found at config file path " + configFilePath);
        }
    }

    public String getApiKey(){
        return properties.getProperty("api.key");
    }

}
