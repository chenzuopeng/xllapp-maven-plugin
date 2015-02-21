package org.xllapp.maven.plugin.mvc;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.xllapp.maven.plugin.ICityMojo;

/**
 * 
 * @goal mvc-code-generate
 * 
 * @author dylan.chen Apr 7, 2014
 * 
 */
public class MVCCodeGenerateMojo extends ICityMojo {

	/**
	 * @parameter expression="${generate.jdbcURL}"
	 */
	private String jdbcURL;

	/**
	 * @parameter expression="${generate.jdbcUserName}"
	 */
	private String jdbcUserName;

	/**
	 * @parameter expression="${generate.jdbcPassword}"
	 */
	private String jdbcPassword;

	/**
	 * @parameter expression="${generate.basePackage}"
	 */
	private String basePackage;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		extractInitParam();
		new MainWindow().showOnCaller();
	}

	public void extractInitParam() {
		InitParamHolder.getInitParam().setJdbcURL(this.jdbcURL);
		InitParamHolder.getInitParam().setJdbcUserName(this.jdbcUserName);
		InitParamHolder.getInitParam().setJdbcPassword(this.jdbcPassword);
		InitParamHolder.getInitParam().setBasePackage(this.basePackage);
	}

}
