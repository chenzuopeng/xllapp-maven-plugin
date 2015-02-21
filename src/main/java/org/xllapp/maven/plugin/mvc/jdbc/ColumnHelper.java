package org.xllapp.maven.plugin.mvc.jdbc;

import org.xllapp.maven.plugin.mvc.model.ColumnModel;

/**
 *
 *
 * @author dylan.chen Jul 17, 2014
 * 
 */
public abstract class ColumnHelper {

	public static String getValidateString(ColumnModel column){
		if(!column.isNullable()){
			return "required:true";
		}
		return null;
	}
	
}
