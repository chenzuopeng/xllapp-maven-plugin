package org.xllapp.maven.plugin.mvc;

/**
 * 
 * 
 * @author dylan.chen Apr 7, 2014
 * 
 */
public abstract class InitParamHolder {
	
	private static InitParam initParam=new InitParam();

	public static InitParam getInitParam() {
		return initParam;
	}

	public static class InitParam {

		private String jdbcURL;

		private String jdbcUserName;

		private String jdbcPassword;

		private String basePackage;

		public String getJdbcURL() {
			return jdbcURL;
		}

		public void setJdbcURL(String jdbcURL) {
			this.jdbcURL = jdbcURL;
		}

		public String getJdbcUserName() {
			return jdbcUserName;
		}

		public void setJdbcUserName(String jdbcUserName) {
			this.jdbcUserName = jdbcUserName;
		}

		public String getJdbcPassword() {
			return jdbcPassword;
		}

		public void setJdbcPassword(String jdbcPassword) {
			this.jdbcPassword = jdbcPassword;
		}

		public String getBasePackage() {
			return basePackage;
		}

		public void setBasePackage(String basePackage) {
			this.basePackage = basePackage;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("InitParam [jdbcURL=");
			builder.append(jdbcURL);
			builder.append(", jdbcUserName=");
			builder.append(jdbcUserName);
			builder.append(", jdbcPassword=");
			builder.append(jdbcPassword);
			builder.append(", basePackage=");
			builder.append(basePackage);
			builder.append("]");
			return builder.toString();
		}
		
	}

}
