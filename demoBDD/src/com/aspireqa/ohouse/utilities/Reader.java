package com.aspireqa.ohouse.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Reader {
	
	private static final String LOCATOR_PATH = "src/test/resources/com/aspireqa/ohouse/data/locators.yml";
	private static final String CONFIG_PATH = "src/test/resources/com/aspireqa/ohouse/data/config.yml";
	
	
	public static final HashMap<String, String> locatorsMap =new HashMap<String, String>();
	public static final HashMap<String, String> configMap =new HashMap<String, String>();
	
	public static void initialize()
	{
		populatelocatorsMap();
		populateConfigValuesMap();
	}
	
	
	
	private static void populatelocatorsMap()
	{
		loadMap(LOCATOR_PATH, locatorsMap);
	}
	
	private static void populateConfigValuesMap()
	{
		loadMap(CONFIG_PATH, configMap);
    }
	

	private static void loadMap(String filePath, HashMap<String, String> mapToLoad)
	{

	    Yaml yaml = new Yaml();

	    try {
	    	FileInputStream ios = new FileInputStream(new File(filePath));

	        // Parse the YAML file and return the output as a series of Maps and Lists
	        @SuppressWarnings("unchecked")
			Map< String, Object> result = (Map< String, Object>) yaml.load(ios);
	        for (Object name : result.keySet()) {   
	        	mapToLoad.put(name.toString(), result.get(name).toString());

	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } 

	}
	
	
}
