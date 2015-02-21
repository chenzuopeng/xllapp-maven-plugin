package org.xllapp.maven.plugin.mvc.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.table.TableModel;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author dylan.chen Apr 5, 2014
 * 
 */
public abstract class MyUtils {

	/**
	 * 将下划线格式字符串转换成驼峰格式字符串
	 */
	public static String toCamelCase(String input) {
		if (null == input || "".equals(input.trim())) {
			return input;
		}
		StringBuilder result = new StringBuilder();
		String[] elems = input.split("_");
		for (int i = 0; i < elems.length; i++) {
			elems[i] = elems[i].toLowerCase();
			if (i > 0) {
				String tmp = elems[i];
				char first = tmp.toCharArray()[0];
				result.append((char) (first - 32) + tmp.substring(1));
			} else {
				result.append(elems[i]);
			}
		}
		return result.toString();
	}
	
	public static String getEntityNameByTableName(String tableName){
		return StringUtils.capitalize(toCamelCase(tableName));
	}

	/**
	 * 获取异常堆栈信息
	 */
	public static String getThrowableStackTraceAsString(Throwable throwable) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		throwable.printStackTrace(printWriter);
		return stringWriter.toString();
	}

	public static String[] split(String input){
		if(StringUtils.isNotBlank(input)){
			return input.trim().split(",");
		}
		return null;
	}
	
	public static String getStringFromTable(TableModel model,int row, int column){
		Object object=model.getValueAt(row, column);
		if(null == object){
			return null; 
		}
		return object+"";
	}
	
}
