package org.xllapp.maven.plugin.log;

import java.util.Arrays;

/**
 *
 *
 * @author dylan.chen Apr 7, 2014
 * 
 */
public abstract class LogUtils {

	public static String toString(Object object){
		if(null!=object){
			if(object.getClass().isArray()){
				return Arrays.toString((Object[])object);
			}else{
				return object.toString();
			}
		}
		return "";
	}
	
}
