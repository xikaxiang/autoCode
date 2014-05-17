package org.xika.auto.code.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xika.auto.code.constant.Constants;
import org.xika.auto.code.db.Column;
import org.xika.auto.code.db.Table;

/**
 * DB 工具类
 * 
 * @author
 * 
 */
public class DBUtils {
	private static Logger log = Logger.getLogger(DBUtils.class.getName());

	static {
		try {
			Class.forName(ConfigUtils.maps.get(Constants.DBDriver));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	public static Connection getConn() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(
					ConfigUtils.maps.get(Constants.DBUrl),
					ConfigUtils.maps.get(Constants.DBUserName),
					ConfigUtils.maps.get(Constants.DBPassword));
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(Level.WARNING, e.getMessage());
		}
		return conn;
	}

	/**
	 * 获取表的主键名
	 * 
	 * @param conn
	 * @param schema
	 * @param TableName
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getPrimaryKeys(Connection conn, String schema,
			String TableName) {

		List<String> lists = new ArrayList<String>();
		ResultSet rs = null;
		try {
			rs = conn.getMetaData().getPrimaryKeys(null, schema, TableName);
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				lists.add(columnName);
				log.info("getPrimaryKeys(): columnName=" + columnName);
			}
		} catch (SQLException e) {
			log.log(Level.WARNING, e.getMessage());
		} finally {
			close(null, rs);
		}

		return lists;
	}

	/**
	 * 
	 * @param rs
	 */
	public static void close(Statement stmt, ResultSet rs) {

		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.log(Level.WARNING, e.getMessage());
			}
		}

		if (null != stmt) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.log(Level.WARNING, e.getMessage());
			}
		}
	}

	/**
	 * 获取所有的列信息
	 * 
	 * @param conn
	 *            数据库连接
	 * @param tableName
	 *            表名
	 * @return 列的详细信息
	 * @throws SQLException
	 */
	public static List<Column> getColumns(Connection conn, String tableName) {
		List<Column> cols = new ArrayList<Column>();
		// 获取这个表的主键 ，并存储在list中
		List<String> pks = getPrimaryKeys(conn, null, tableName);
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from " + tableName);
			// 此处需要优化 limit 1 top 1 rownum <= 1 根据不同数据库
			ResultSetMetaData rsCols = rs.getMetaData();

			int columnCount = rsCols.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				Column col = new Column();
				col.setTableName(rsCols.getTableName(i));
				col.setName(rsCols.getColumnName(i));
				col.setType(rsCols.getColumnTypeName(i));
				col.setPk(pks.contains(rsCols.getColumnName(i)));
				col.setLength(rsCols.getColumnDisplaySize(i));
				col.setNotNull(rsCols.isNullable(i) == 0 ? true : false);
				col.setJavaType(EntityUtils.toJavaType4Mysql(col.getType()));
				col.setJavaFieldName(ConvertUtils.toJavaField(col.getName()));
				col.setShortJavaType(ConvertUtils.getShortJavaType(col.getJavaType()));

				cols.add(col);
			}

		} catch (SQLException e) {
			log.log(Level.WARNING, e.getMessage());
		} finally {
			close(stmt, rs);
		}

		return cols;
	}

	/**
	 * 获取所有表信息
	 * 
	 * @param conn
	 *            数据库连接 s
	 * @param db
	 *            数据库
	 * @return 库中表信息
	 * @throws SQLException
	 */
	public static List<Table> collectAllTables(Connection conn, String db)
			throws SQLException {
		DatabaseMetaData dmd = conn.getMetaData();

		// 获取库中的所有表
		ResultSet rsTables = dmd.getTables(null, null, null,
				new String[] { "TABLE" });
		List<Table> tables = new ArrayList<Table>();
		// 将表存到list中
		while (rsTables.next()) {
			Table tb = new Table();
			tb.setSpace(db);
			// 获取表名称
			String tbName = rsTables.getString("TABLE_NAME");
			tb.setName(tbName);

			// 获取表中的字段及其类型
			List<Column> cols = getColumns(conn, tbName);
			tb.setColumns(cols);
			tables.add(tb);
		}
		rsTables.close();

		return tables; // connection未关闭
	}

	public static void main(String[] args) throws Exception {
		List<Table> ts = collectAllTables(getConn(), "test");
		for (Table t : ts) {
			System.out.println(t.toString());
		}
	}

}
