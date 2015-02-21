package org.xllapp.maven.plugin.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 *
 * @author dylan.chen Apr 7, 2014
 * 
 */
public class DialogUtils {
	
	private DialogUtils(){
	}
	
	public static void showCodeOnDialog(JFrame owner,Map<String,String> codes){
		CodeDialog.show(owner, codes);
	}
	
	public static void showErrorMessageOnDialog(String message){
		JOptionPane.showMessageDialog(null, message,"错误", JOptionPane.ERROR_MESSAGE);	
	}

    public static void showErrorOnDialog(Throwable throwable){
    	ByteArrayOutputStream out=null;
    	try {
    		out=new ByteArrayOutputStream();
    		throwable.printStackTrace(new PrintStream(out));
    		JOptionPane.showMessageDialog(null, out.toString(),"错误", JOptionPane.ERROR_MESSAGE);	
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
    }
    
    public static void showWarningMessageOnDialog(String message){
    	JOptionPane.showMessageDialog(null, message,"提示", JOptionPane.WARNING_MESSAGE);	
    }
    
    public static void showMessageOnDialog(String message){
    	JOptionPane.showMessageDialog(null, message,null, JOptionPane.INFORMATION_MESSAGE);	
    }
}
