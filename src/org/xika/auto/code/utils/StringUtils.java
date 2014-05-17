package org.xika.auto.code.utils;

public class StringUtils {

	public static boolean isEmpty(String value) {
		if (null == value || value.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isBlank(String val) {

		if (null != val && val.trim().length() == 0) {
			return true;
		}
		return false;
	}
}
