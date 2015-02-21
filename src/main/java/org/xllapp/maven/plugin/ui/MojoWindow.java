package org.xllapp.maven.plugin.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.xllapp.maven.plugin.log.LogHolder;

/**
 * 可以在Maven执行环境中打开的窗口.
 * 
 * @author dylan.chen Apr 15, 2014
 * 
 */
public class MojoWindow extends JFrame {

	private static final long serialVersionUID = -3833264794234059538L;

	private JPanel contentPane;

	private CountDownLatch exitSignal = new CountDownLatch(1);

	public void showOnCaller() {
		setVisible(true);
		try {
			this.exitSignal.await(); // 等待退出信号
		} catch (InterruptedException e) {
			LogHolder.getLog().warn("interrupted exitSignal.await()");
		}
	}

	public MojoWindow() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.contentPane = new JPanel();
		this.contentPane.setLayout(null);
		setContentPane(this.contentPane);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MojoWindow.this.exitSignal.countDown();
			}
		});
	}

}
