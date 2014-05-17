package org.xika.auto.code.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigUtils {

	public static Map<String, String> maps = new HashMap<String, String>();

	private static Logger log = Logger.getLogger(ConfigUtils.class.getName());

	static {

		Properties config = new Properties();
		try {
			config.load(ClassLoader
					.getSystemResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			log.log(Level.WARNING, e.getMessage());
		}

		loadConfigProp(maps, config);
	}

	/**
	 * 加载config配置文件到map
	 * 
	 * @param maps
	 * @param prop
	 */
	private static void loadConfigProp(Map<String, String> maps, Properties prop) {
		Enumeration<Object> enums = prop.keys();
		String key = null;
		while (enums.hasMoreElements()) {
			key = (String) enums.nextElement();
			maps.put(key, prop.getProperty(key));
		}
	}

	/**
	 * test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(maps.size());
	}
}
