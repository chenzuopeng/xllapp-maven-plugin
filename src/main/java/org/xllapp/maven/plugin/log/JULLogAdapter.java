package org.xllapp.maven.plugin.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author dylan.chen Apr 7, 2014
 * 
 */
public class JULLogAdapter implements LogAdapter{
	
	private Logger logger=Logger.getLogger(JULLogAdapter.class.getName());

	public boolean isDebugEnabled() {
		return logger.isLoggable(Level.INFO);
	}

	public void debug(Object content) {
		logger.log(Level.INFO, LogUtils.toString(content));
	}

	public void debug(Object content, Throwable error) {
		logger.log(Level.INFO, LogUtils.toString(content),error);
	}

	public void debug(Throwable error) {
		debug(error.getLocalizedMessage(), error);
	}

	public boolean isInfoEnabled() {
		return isDebugEnabled();
	}

	public void info(Object content) {
		debug(content);
	}

	public void info(Object content, Throwable error) {
		debug(content, error);
	}

	public void info(Throwable error) {
		debug(error);
	}

	public boolean isWarnEnabled() {
		return logger.isLoggable(Level.WARNING);
	}

	public void warn(Object content) {
		logger.log(Level.WARNING, LogUtils.toString(content));
	}

	public void warn(Object content, Throwable error) {
		logger.log(Level.WARNING, LogUtils.toString(content),error);
	}

	public void warn(Throwable error) {
		warn(error.getLocalizedMessage(), error);
	}

	public boolean isErrorEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	public void error(Object content) {
		logger.log(Level.SEVERE, LogUtils.toString(content));
	}

	public void error(Object content, Throwable error) {
		logger.log(Level.SEVERE, LogUtils.toString(content),error);
	}

	public void error(Throwable error) {
		error(error.getLocalizedMessage(), error);
	}

}
