package org.xllapp.maven.plugin.mvc.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.xllapp.maven.plugin.mvc.TemplateType;

/**
 * 
 * 
 * @author dylan.chen Apr 5, 2014
 * 
 */
public class TemplateModel {

	private String basePackage;

	private TemplateType templateType;

	private TableModel tableModel;

	public String getBasePackage() {
		return this.basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public TableModel getTableModel() {
		return this.tableModel;
	}

	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public TemplateType getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
