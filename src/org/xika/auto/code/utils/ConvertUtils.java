package org.xika.auto.code.utils;

public class ConvertUtils {

	/**
	 * 数据库字段转换位java field字段，转换原则：除去下划线，驼峰标识 97-122
	 * 
	 * @param columnName
	 * @return
	 */
	public static String toJavaField(String columnName) {
		if (!StringUtils.isEmpty(columnName)) {
			char[] arryChar = columnName.toLowerCase().toCharArray();
			for (int i = 0; i < arryChar.length; i++) {
				if (95 == arryChar[i] && Character.isLowerCase(arryChar[i + 1])) {
					arryChar[i + 1] -= 32;
				}
			}
			return String.valueOf(arryChar).replace("_", "");
		}

		return null;
	}

	/**
	 * 65 -90 字母大写
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String toColumnField(String fieldName) {
		StringBuffer buf = new StringBuffer();
		if (!StringUtils.isEmpty(fieldName)) {
			char[] arryChar = fieldName.toCharArray();
			for (int i = 0; i < arryChar.length; i++) {
				if (i != 0 && Character.isUpperCase(arryChar[i])) {
					buf.append("_");
				}
				buf.append(arryChar[i]);
			}

		}

		return buf.toString().toUpperCase();
	}
	
	public static String getShortJavaType(String javaType){
		int t = javaType.lastIndexOf(".");
		if(t>0){
			javaType =  javaType.substring(t+1);
		}
		return javaType;
	}
	
	
	

	public static void main(String[] args) {
		System.out.println(toJavaField("col_NaMe_hel_p"));
		System.out.println(toColumnField("LasNameBe"));
		System.out.println(getShortJavaType("java.util.ArrayList"));
		
		/**
		for (int i = 0; i < 128; i++) {
			System.out.print(i + " = " + String.valueOf((char) i) + ",  ");
		}
		*/
	}
}
