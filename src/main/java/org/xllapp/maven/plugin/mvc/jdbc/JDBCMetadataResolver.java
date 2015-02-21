package org.xllapp.maven.plugin.mvc.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xllapp.maven.plugin.log.LogHolder;
import org.xllapp.maven.plugin.mvc.model.ColumnModel;
import org.xllapp.maven.plugin.mvc.model.TableModel;
import org.xllapp.maven.plugin.mvc.utils.MyUtils;

/**
 * 
 * 
 * @author dylan.chen Jun 2, 2012
 * 
 */
public class JDBCMetadataResolver {

	public final static String MYSQL_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

	private Connection connection;

	public JDBCMetadataResolver(String driverClassName, String url, String userName, String password) {

		try {
			Class.forName(driverClassName);
			connection = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}

	}
	
	public void release(){
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LogHolder.getLog().warn("Could not close JDBC Connection",e);
			} catch (Throwable e) {
				LogHolder.getLog().warn("Unexpected exception on closing JDBC Connection",e);
			}
		}
	}

	private void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				LogHolder.getLog().warn("Could not close JDBC Statement",e);
			} catch (Throwable e) {
				LogHolder.getLog().warn("Unexpected exception on closing JDBC Statement",e);
			}
		}
	}

	public String[] retriveTableNames() throws SQLException {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet rs = databaseMetaData.getTables(null, "%", "%", new String[] { "TABLE" });
			List<String> tableNames=new ArrayList<String>();
			while (rs.next()){
				tableNames.add(rs.getString("TABLE_NAME"));
			}
			rs.close();
			return tableNames.toArray(new String[0]);
	}
	
	public TableModel retriveTable(String tableName) throws Exception {
		TableModel tableModel=new TableModel();
		tableModel.setTableName(tableName);
		tableModel.setEntityClassName(MyUtils.getEntityNameByTableName(tableName));
		retriveColumns(tableModel);
		return tableModel;
	}
	
	private void retriveColumns(TableModel tableModel) throws SQLException{
		String tableName=tableModel.getTableName();
		Statement stmt=null;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from "+tableName);
			ResultSetMetaData rsmd = rs.getMetaData();
			Map<String,ColumnAddMetaData>  addMetadatas=retriveColumnAddMetaDatas(tableName);
			if (rsmd != null) {
				int count = rsmd.getColumnCount();
				for (int i = 1; i <= count; i++) {
					
					ColumnModel columnModel=new ColumnModel();
					
					columnModel.setColumnName(rsmd.getColumnName(i));
					columnModel.setJavaFieldName(MyUtils.toCamelCase(columnModel.getColumnName()));
					String javaType=rsmd.getColumnClassName(i);
					if("java.sql.Timestamp".equalsIgnoreCase(javaType) || "java.sql.Date".equalsIgnoreCase(javaType) || "java.sql.Time".equalsIgnoreCase(javaType)){
						javaType="java.util.Date";
					}
					columnModel.setJavaType(javaType);
					
					ColumnAddMetaData columnAddMetaData=addMetadatas.get(columnModel.getColumnName());
					columnModel.setLabel(columnAddMetaData.getRemarks());
					columnModel.setNullable(columnAddMetaData.isNullable);
					
					tableModel.addColumn(columnModel);
					
				}
			}
			rs.close();
		}finally{
			closeStatement(stmt);
		}
	}
	
	@SuppressWarnings("unused")
	private List<String> retrivePrimaryKeys(String tableName) throws SQLException {
		List<String> result = new ArrayList<String>();
		ResultSet rs = connection.getMetaData().getPrimaryKeys(null, "%", tableName);
		while (rs.next()) {
			String pkName = rs.getString("COLUMN_NAME");
			result.add(pkName);
		}
		rs.close();
		return result;
	}
	
	private Map<String,ColumnAddMetaData> retriveColumnAddMetaDatas(String tableName) throws SQLException{
		Map<String,ColumnAddMetaData> map=new HashMap<String, JDBCMetadataResolver.ColumnAddMetaData>();
		ResultSet rs=connection.getMetaData().getColumns(null, "%", tableName, null);
		while (rs.next()) {
			String key=rs.getString("COLUMN_NAME");
			ColumnAddMetaData columnAddMetaData=new ColumnAddMetaData();
			columnAddMetaData.setRemarks(rs.getString("REMARKS"));
			columnAddMetaData.setNullable(DatabaseMetaData.columnNullable == rs.getInt("NULLABLE"));
			map.put(key, columnAddMetaData);
		}
		rs.close();
		return map;
	}
	
	public static class ColumnAddMetaData{
		
		private boolean isNullable;
		
		private String remarks;

		public boolean isNullable() {
			return isNullable;
		}

		public void setNullable(boolean isNullable) {
			this.isNullable = isNullable;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ColumnAddMetaData [isNullable=");
			builder.append(isNullable);
			builder.append(", remarks=");
			builder.append(remarks);
			builder.append("]");
			return builder.toString();
		}

	}

}
