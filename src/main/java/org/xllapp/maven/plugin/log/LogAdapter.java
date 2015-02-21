package org.xllapp.maven.plugin.log;

/**
 * 
 * 
 * @author dylan.chen Apr 7, 2014
 * 
 */
public interface LogAdapter {

	boolean isDebugEnabled();

	void debug(Object content);

	void debug(Object content, Throwable error);

	void debug(Throwable error);

	boolean isInfoEnabled();

	void info(Object content);

	void info(Object content, Throwable error);

	void info(Throwable error);

	boolean isWarnEnabled();

	void warn(Object content);

	void warn(Object content, Throwable error);

	void warn(Throwable error);

	boolean isErrorEnabled();

	void error(Object content);

	void error(Object content, Throwable error);

	void error(Throwable error);
	
}
