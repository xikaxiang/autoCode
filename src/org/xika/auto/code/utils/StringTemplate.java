package org.xika.auto.code.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StringTemplate {

	/**
	 * 替换文件名  java\entity\${entity}.java
	 * @param sourcePath
	 * @param Maps
	 * @return
	 */
	public static String replace(String sourcePath,Map<String,Object> maps){
		String result = sourcePath;
		for(Iterator it = maps.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry entry = (Map.Entry)it.next();
			String key = (String)entry.getKey();
			Object value = entry.getValue();
			String strValue = value == null ? "" : value.toString();
			result = StringHelper.replace(result, "${"+key+"}", strValue);
		}
		return result;
	}
	
	public static void main(String[] args) {
		Map<String,Object> maps = new HashMap<String, Object>();
		
		maps.put("entity", "Stu");
		maps.put("${entity}", "Student");
		String s = "java\\entity\\${entity}.java";
		String t  = replace(s,maps);
		System.out.println(t);
		
	}
}
