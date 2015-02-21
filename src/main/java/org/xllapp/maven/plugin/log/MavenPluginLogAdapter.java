package org.xllapp.maven.plugin.log;

import org.apache.maven.plugin.logging.Log;


/**
 * 
 * 
 * @author dylan.chen Apr 7, 2014
 * 
 */
public class MavenPluginLogAdapter implements LogAdapter{
	


	private Log log;

	public MavenPluginLogAdapter(Log log) {
		this.log = log;
	}

	public boolean isDebugEnabled() {
		return this.log.isDebugEnabled();
	}

	public void debug(Object content) {
		this.log.debug(LogUtils.toString(content));
	}

	public void debug(Object content, Throwable error) {
        this.log.debug(LogUtils.toString(content), error);		
	}

	public void debug(Throwable error) {
		this.log.debug(error);
	}

	public boolean isInfoEnabled() {
		return this.log.isInfoEnabled();
	}

	public void info(Object content) {
		this.log.info(LogUtils.toString(content));
	}

	public void info(Object content, Throwable error) {
		this.log.info(LogUtils.toString(content), error);
	}

	public void info(Throwable error) {
		this.log.info(error);
	}

	public boolean isWarnEnabled() {
		return this.log.isWarnEnabled();
	}

	public void warn(Object content) {
		this.log.warn(LogUtils.toString(content));		
	}

	public void warn(Object content, Throwable error) {
		this.log.warn(LogUtils.toString(content), error);
	}

	public void warn(Throwable error) {
		this.log.warn(error);
	}

	public boolean isErrorEnabled() {
		return false;
	}

	public void error(Object content) {
		this.log.error(LogUtils.toString(content));
	}

	public void error(Object content, Throwable error) {
		this.log.error(LogUtils.toString(content), error);
	}

	public void error(Throwable error) {
		this.log.error(error);
	}

}
