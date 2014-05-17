package org.xika.auto.code.db;

public class Column {
	/**
	 * 表名称
	 */
	private String tableName;
	/**
	 * 列名称(字段名称)
	 */
	private String name;
	/**
	 * 是否主键
	 */
	private boolean isPk;
	/**
	 * 默认值
	 */
	private String value;
	/**
	 * 是否为空
	 */
	private boolean isNotNull;
	/**
	 * 数据类型
	 */
	private String type;
	/**
	 * 数据长度
	 */
	private int length;
	/**
	 * 代码类型
	 */
	private int codeType;
	
	private String javaType;
	
	private String shortJavaType;
	
	private String javaFieldName;
	

	
	
	public String getShortJavaType() {
		return shortJavaType;
	}

	public void setShortJavaType(String shortJavaType) {
		this.shortJavaType = shortJavaType;
	}

	public String getJavaFieldName() {
		return javaFieldName;
	}

	public void setJavaFieldName(String javaFieldName) {
		this.javaFieldName = javaFieldName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public boolean isPk() {
		return isPk;
	}

	public void setPk(boolean isPk) {
		this.isPk = isPk;
	}

	public boolean isNotNull() {
		return isNotNull;
	}

	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	@Override
	public String toString() {
		return "Column [tableName=" + tableName + ", name=" + name + ", isPk="
				+ isPk + ", value=" + value + ", isNotNull=" + isNotNull
				+ ", type=" + type +", javaType=" + javaType + ", length=" 
				+ length + ", codeType="
				+ codeType + "]";
	}

}
