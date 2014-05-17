package org.xika.auto.code.utils;

import java.util.UUID;

/**
 * 1 在数据库中建一个表，有日期和最大号 写一个synchronized的方法， 如果数据库中没有今日的最大号，那添加一条数据。存上今日和初始号。
 * 如果数据库中有今日的最大号，则取出最大号+1，再update 2 直接用数据库的sequence 3 java 的uuid 4
 * 根据数据库某表中，取前的最大编号 + 1
 * 
 * 
 */
public class GenerateAutoId {

	public static void main(String[] args) {

		UUID uuid = UUID.randomUUID();
		System.out.println(uuid);
	}

}
