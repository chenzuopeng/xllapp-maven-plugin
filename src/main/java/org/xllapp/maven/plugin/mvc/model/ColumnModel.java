package org.xllapp.maven.plugin.mvc.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.xllapp.maven.plugin.mvc.utils.MyUtils;

/**
 * 
 * 
 * @author dylan.chen Jul 6, 2014
 * 
 */
public class ColumnModel {

	// 查询过滤条件相关字段
	public enum WhereFilter {

		NONE("无"), EQ("等于"), NE("不等于"), LIKE("like"), NOT_LIKE("not like"), GT("大于"), GE("大等于"), LT("小于"), LE("小等于"), BETWEEN("between"), NOT_BETWEEN("not between"), IN("in"), NOT_IN("not in");

		private String label;

		private WhereFilter(String label) {
			this.label = label;
		}

		public static WhereFilter getWhereFilter(String label) {
			for (WhereFilter whereFilter : WhereFilter.values()) {
				if (whereFilter.label.equals(label)) {
					return whereFilter;
				}
			}
			return null;
		}

		@Override
		public String toString() {
			return this.label;
		}
	}

	private WhereFilter whereFilter;

	// 表单相关字段
	public enum InputType {
		TEXT, TEXTAREA, DATE, SELECT, FILE, IMAGE
	}

	// 代码

	private String columnName;

	private String javaType;

	private String javaFieldName;

	private boolean isNullable;

	// 页面

	private String label;

	private InputType inputType;

	private String selectKeys;

	private String selectValues;

	private String validateString;

	private boolean isHtmlTableRow;

	private boolean isSysdateAsValue;

	public ColumnModel() {
	}

	public boolean isEnumColumn() {
		return StringUtils.isNotBlank(this.selectKeys) && StringUtils.isNotBlank(this.selectValues);
	}

	public boolean isNullable() {
		return this.isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	public boolean isDateTime() {
		return "java.util.Date".equals(this.javaType);
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getJavaType() {
		return this.javaType;
	}

	public String getSimpleJavaType() {
		if (StringUtils.isBlank(this.javaType)) {
			return this.javaType;
		}
		int index = this.javaType.lastIndexOf(".");
		if (index < 0) {
			return this.javaType;
		}
		return this.javaType.substring(index + 1);
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJavaFieldName() {
		return this.javaFieldName;
	}

	public void setJavaFieldName(String javaFieldName) {
		this.javaFieldName = javaFieldName;
	}

	public String getJavaFieldNameFirstUpper() {
		return StringUtils.capitalize(this.javaFieldName);
	}

	public boolean isWhereFilter() {
		return null != this.whereFilter && this.whereFilter != WhereFilter.NONE;
	}

	public WhereFilter getWhereFilter() {
		return this.whereFilter;
	}

	public String getWhereFilterAsString() {
		return this.whereFilter.name();
	}

	public void setWhereFilter(WhereFilter whereFilter) {
		this.whereFilter = whereFilter;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public InputType getInputType() {
		return this.inputType;
	}

	public String getInputTypeAsString() {
		return this.inputType.toString();
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	public String[] getSelectKeyArray() {
		return MyUtils.split(this.selectKeys);
	}

	public String getSelectKeys() {
		return this.selectKeys;
	}

	public void setSelectKeys(String selectKeys) {
		this.selectKeys = selectKeys;
	}

	public String[] getSelectValueArray() {
		return MyUtils.split(this.selectValues);
	}

	public String getSelectValues() {
		return this.selectValues;
	}

	public void setSelectValues(String selectValues) {
		this.selectValues = selectValues;
	}

	public String[] getValidateStringArray() {
		return MyUtils.split(this.validateString);
	}

	public String getValidateString() {
		return this.validateString;
	}

	public void setValidateString(String validateString) {
		this.validateString = validateString;
	}

	public boolean isHtmlTableRow() {
		return this.isHtmlTableRow;
	}

	public void setHtmlTableRow(boolean isHtmlTableRow) {
		this.isHtmlTableRow = isHtmlTableRow;
	}

	public boolean isNotModified() {
		return this.isSysdateAsValue;
	}

	public boolean isSysdateAsValue() {
		return this.isSysdateAsValue;
	}

	public void setSysdateAsValue(boolean isSysdateAsValue) {
		this.isSysdateAsValue = isSysdateAsValue;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
