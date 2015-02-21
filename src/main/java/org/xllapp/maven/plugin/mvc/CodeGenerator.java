package org.xllapp.maven.plugin.mvc;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.xllapp.maven.plugin.mvc.model.TemplateModel;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 
 * @author dylan.chen Apr 6, 2014
 * 
 */
public class CodeGenerator {

	public final static String FTL_PATH = "/ftl/";

	private Configuration configuration;

	public CodeGenerator() {
		this.configuration = new Configuration();
		this.configuration.setObjectWrapper(new DefaultObjectWrapper());
		this.configuration.setClassForTemplateLoading(this.getClass(), FTL_PATH);
	}

	public Map<String, String> generate(TemplateModel templateModel) throws Exception {
		TemplateType templateType=templateModel.getTemplateType();
		if(ArrayUtils.isNotEmpty(templateType.getAutoIncludeTemplates())){
			List<String> autoIncludeTemplates = new ArrayList<String>();
			for (org.xllapp.maven.plugin.mvc.TemplateType.Template template : templateType.getAutoIncludeTemplates()) {
				autoIncludeTemplates.add(templateType.getTemplatePath(template));
			}
			this.configuration.setAutoIncludes(autoIncludeTemplates);
		}
		Map<String, String> codes = new LinkedHashMap<String, String>();
		if(ArrayUtils.isNotEmpty(templateType.getTemplates())){
			for (org.xllapp.maven.plugin.mvc.TemplateType.Template template : templateType.getTemplates()) {
				codes.put(template.getLabel(), generate(templateType.getTemplatePath(template), templateModel));
			}
		}
		return codes;
	}

	private String generate(String ftl, TemplateModel templateModel) throws IOException, TemplateException {
		Template temp = this.configuration.getTemplate(ftl);
		StringWriter out = new StringWriter();
		temp.process(templateModel, out);
		out.flush();
		return out.toString();
	}

}
