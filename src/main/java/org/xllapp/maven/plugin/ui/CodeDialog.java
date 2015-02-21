package org.xllapp.maven.plugin.ui;

import java.awt.Component;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * 用于展示生成的代码的对话框.
 *
 * @author dylan.chen Apr 7, 2014
 * 
 */
public class CodeDialog extends JDialog {

	private static final long serialVersionUID = 3936711966835135469L;
	
	private Map<String,String> codes;
	
	public CodeDialog(JFrame owner,Map<String,String> codes){
		super(owner, "生成的代码");
		this.codes=codes;
		initTabbedPane();
	}
	
	private void initTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		setContentPane(tabbedPane);

	    Set<String> keys=this.codes.keySet();
	    for (String key : keys) {
	    	tabbedPane.add(key, initContentPane(this.codes.get(key)));
		}
	}

	private Component initContentPane(String content) {
		JScrollPane scrollPane = new JScrollPane();
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setText(content);
		return scrollPane;
	}

	public static void show(JFrame owner,Map<String,String> codes){
		CodeDialog dialog=new CodeDialog(null,codes);
		dialog.setBounds(owner.getX(), owner.getY(), 600, 500);
		dialog.setVisible(true);
	}
	
}
