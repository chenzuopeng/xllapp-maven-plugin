package org.xllapp.maven.plugin.cleanlastupdated;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.xllapp.maven.plugin.ICityMojo;
import org.xllapp.maven.plugin.log.LogHolder;

/**
 * 清理本地缓存中的LastUpdated文件.
 * 
 * @goal lufclean
 * 
 * @author dylan.chen Apr 18, 2014
 * 
 */
public class CleanLastUpdatedFileMojo extends ICityMojo {

	/**
	 * @parameter expression="${settings.localRepository}"
	 */
	private String localRepository;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		LogHolder.getLog().debug("local repository path:"+this.localRepository);
		Collection<File> lastUpdatedFiles=FileUtils.listFiles(new File(localRepository), new String[]{"lastUpdated"}, true);
		for (File lastUpdatedFile : lastUpdatedFiles) {
			try {
				boolean s=lastUpdatedFile.delete();
				if(s){
					LogHolder.getLog().debug("deleted "+lastUpdatedFile.getAbsolutePath());
				}else{
					LogHolder.getLog().debug("failure to delete "+lastUpdatedFile.getAbsolutePath());
				}
			} catch (Exception e) {
				LogHolder.getLog().debug("failure to delete "+lastUpdatedFile.getAbsolutePath(), e);
			}
		}
		
	}

}
