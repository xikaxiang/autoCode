package org.xika.auto.code.utils;

import java.util.HashMap;
import java.util.Map;

public class EntityUtils {

	public static Map<String, String> typeMappings = new HashMap<String, String>();

	static {
		typeMappings.put("VARCHAR", "String");
		typeMappings.put("CHAR", "String");
		typeMappings.put("TEXT", "String");

		typeMappings.put("BLOB", "byte[]");
		typeMappings.put("CLOB", "byte[]");

		typeMappings.put("INTEGER", "int");
		typeMappings.put("TINYINT", "int");
		typeMappings.put("INT", "int");
		typeMappings.put("SMALLINT", "int");
		typeMappings.put("MEDIUMINT", "int");

		typeMappings.put("BIT", "int");
		typeMappings.put("BOOLEAN", "int");

		typeMappings.put("ID", "long");

		typeMappings.put("BIGINT", "java.math.BigInteger");
		typeMappings.put("FLOAT", "float");
		typeMappings.put("DOUBLE", "double");

		typeMappings.put("DECIMAL", "java.math.BigDecimal");

		typeMappings.put("DATE", "java.sql.Date");
		typeMappings.put("YEAR", "java.sql.Date");

		typeMappings.put("TIME", "java.sql.Time");
		typeMappings.put("DATETIME", "java.sql.Timestamp");
		typeMappings.put("TIMESTAMP", "java.sql.Timestamp");
		typeMappings.put("ID", "long");

	}

	/**
	 * 
	 * @param dbType
	 * @return
	 */
	public static String toJavaType4Mysql(String dbType) {
		dbType = dbType.toUpperCase();
		return typeMappings.get(dbType);

	}

}
