package org.xllapp.maven.plugin.mvc;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * 
 * @author dylan.chen Jul 7, 2014
 * 
 */
public enum TemplateType {
	
	ICITY_MVC("xllapp-mvc", 
			  "xllapp-mvc", 
			  new Template[] { new Template("Mapper", "mapper.ftl"), 
			                   new Template("Entity", "entity.ftl"), 
			                   new Template("Dao", "dao.ftl"), 
			                   new Template("Controller", "controller.ftl"), 
			                   new Template("List", "list.ftl"),
			                   new Template("Form", "form.ftl")
			                 }, 
			  new Template[] { new Template("common.ftl") }
	),
	
	ENTITY("entity","entity",new Template[] { new Template("Entity", "entity.ftl")},null);

	private String label;

	private String dir;

	private Template[] templates;

	private Template[] autoIncludeTemplates;

	private TemplateType(String label, String dir, Template[] templates, Template[] autoIncludeTemplates) {
		this.label = label;
		this.dir = dir;
		this.templates = templates;
		this.autoIncludeTemplates = autoIncludeTemplates;
	}

	public String getLabel() {
		return label;
	}

	public String getTemplatePath(Template template) {
		return this.dir + "/" + template.getFileName();
	}

	public Template[] getTemplates() {
		return templates;
	}

	public Template[] getAutoIncludeTemplates() {
		return autoIncludeTemplates;
	}

	@Override
	public String toString() {
		return this.label;
	}

	public static class Template {

		private String label;

		private String fileName;

		public Template(String fileName) {
			this(null, fileName);
		}

		public Template(String label, String fileName) {
			super();
			this.label = label;
			this.fileName = fileName;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}

	}

}
