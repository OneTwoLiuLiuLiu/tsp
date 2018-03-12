package com.sunyard.frameworkset.plugin.tsp.execute.kettle;

import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
	
	private static Properties properties;
	

	public static void init() throws Exception{
		InputStream in=PropertyManager.class.getClassLoader().getResourceAsStream("launcher.properties");
		properties = new Properties();
		properties.load(in);
	}
	
	public static String getVaule(String name){
		return properties.getProperty(name);
	}
	
}
