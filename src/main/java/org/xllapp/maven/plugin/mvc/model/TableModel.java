package org.xllapp.maven.plugin.mvc.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.xllapp.maven.plugin.mvc.model.ColumnModel.WhereFilter;

/**
 * 
 * 
 * @author dylan.chen Jul 6, 2014
 * 
 */
public class TableModel {
	
	private String tableName;

	private String entityClassName;
	
	private List<ColumnModel> columns = new ArrayList<ColumnModel>();
	
	private List<ColumnModel> includedColumns = new ArrayList<ColumnModel>();
	
	private ColumnModel idColumn;
	
	public List<ColumnModel> getNotIdIncludedColumns() {
		List<ColumnModel> results = new ArrayList<ColumnModel>();
		String idColumnName=this.idColumn.getColumnName();
		for (ColumnModel columnModel : this.includedColumns) {
			if(!columnModel.getColumnName().equals(idColumnName)){
				results.add(columnModel);
			}
		}
		return results;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ColumnModel> getIncludedColumns() {
		return this.includedColumns;
	}
	
	public List<String> getIncludedColumnTypes(){
		//去除重复的类型
		Set<String> set=new HashSet<String>();
		for (ColumnModel column : this.getIncludedColumns()) {
			set.add(column.getJavaType());
		}
		return new ArrayList<String>(set);
	}
	
	public void addIncludedColumn(ColumnModel columnModel){
		this.includedColumns.add(columnModel);
	}
	
	public List<ColumnModel> getColumns() {
		return this.columns;
	}

	public void addColumn(ColumnModel columnModel){
		this.columns.add(columnModel);
	}

	public String getEntityClassName() {
		return this.entityClassName;
	}
	
	public String getEntityClassNameFirstLower() {
	    return StringUtils.uncapitalize(this.entityClassName);
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}
	
	public ColumnModel getDefaultIdColumn(){
		if (null == this.idColumn) {
			for (ColumnModel columnModel : this.columns) {
				if ("id".equalsIgnoreCase(columnModel.getColumnName())) {
					return columnModel;
				}
			}
		}
		return null;
	}
	
	public ColumnModel getIdColumn() {
		return this.idColumn;
	}

	public void setIdColumn(String idColumn) {
		for (ColumnModel columnModel : this.getColumns()) {
			if (columnModel.getColumnName().equalsIgnoreCase(idColumn)) {
				this.idColumn = columnModel;
				break;
			}
		}
	}

	public boolean hasWhereFilter() {
		for (ColumnModel columnModel : this.includedColumns) {
			if(WhereFilter.NONE!=columnModel.getWhereFilter()){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
