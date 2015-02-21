package org.xllapp.maven.plugin.log;

/**
 *
 *
 * @author dylan.chen Apr 7, 2014
 * 
 */
public abstract class LogHolder {
   
	private static LogAdapter log = new JULLogAdapter();

	public static LogAdapter getLog() {
		return log;
	}

	public static void setLog(LogAdapter log) {
		LogHolder.log = log;
	}
	
}
