package org.xika.auto.code.db;

import java.util.ArrayList;
import java.util.List;

import org.xika.auto.code.utils.ConvertUtils;

// http://livvyguo.blog.51cto.com/4530078/1120348
public class Table {

	/**
	 * 表名称
	 */
	private String name;
	/**
	 * 存储空间(库名)
	 */
	private String space;
	/**
	 * 表中的列
	 */
	private List<Column> columns;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<String> getImports() {
		List<String> improts = new ArrayList<String>();

		for (Column col : this.getColumns()) {
			String typeStr = col.getJavaType();
			if(typeStr.indexOf(".")>0&&!improts.contains(typeStr)){
				improts.add(typeStr);
			}
		}

		return improts;

	}
	
	
	public List<String> getFullFields(){
		List<String> fullFields  = new ArrayList<String>();
		String tmp = null;
		for(Column col :this.getColumns()){
			tmp = "private " + col.getShortJavaType() +" " + col.getJavaFieldName(); 
			fullFields.add(tmp);
		}
		return fullFields;
	}

	@Override
	public String toString() {
		return "Table [name=" + name + ", space=" + space + ", columns="
				+ columns + "]";
	}

}
