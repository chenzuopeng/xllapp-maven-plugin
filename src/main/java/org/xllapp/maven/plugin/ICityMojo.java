package org.xllapp.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.xllapp.maven.plugin.log.LogHolder;
import org.xllapp.maven.plugin.log.MavenPluginLogAdapter;

/**
 *
 *
 * @author dylan.chen Apr 18, 2014
 * 
 */
public abstract class ICityMojo extends AbstractMojo{
	
	public ICityMojo(){
		LogHolder.setLog(new MavenPluginLogAdapter(this.getLog()));
	}
	
}
