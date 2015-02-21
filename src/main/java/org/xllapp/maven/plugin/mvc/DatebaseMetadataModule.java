package org.xllapp.maven.plugin.mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.xllapp.maven.plugin.log.LogHolder;
import org.xllapp.maven.plugin.mvc.jdbc.JDBCMetadataResolver;
import org.xllapp.maven.plugin.ui.DialogUtils;

/**
 * 
 * 
 * @author dylan.chen Apr 22, 2014
 * 
 * 
 */
public class DatebaseMetadataModule extends JPanel {

	private static final long serialVersionUID = -5914254219940519415L;

	private JTextField urlTextField;

	private JTextField userNameTextField;

	private JTextField passwordTextField;

	private JButton attachDBButton;

	private ConnectionListener connectionListener;

	public DatebaseMetadataModule(ConnectionListener connectionListener) {
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "连接数据库", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(20, 15, 515, 175);
		setLayout(null);

		this.connectionListener = connectionListener;

		initUrlTextField();
		initUserNameTextField();
		initPasswordTextField();
		initAttachDBButton();
	}

	private void initUrlTextField() {
		JLabel urlLabel = new JLabel("jdbc.url:");
		urlLabel.setBounds(15, 37, 127, 15);
		add(urlLabel);

		this.urlTextField = new JTextField();
		this.urlTextField.setBounds(140, 30, 350, 25);
		add(this.urlTextField);

		this.urlTextField.setText(InitParamHolder.getInitParam().getJdbcURL());
	}

	private void initUserNameTextField() {
		JLabel userNameLabel = new JLabel("jdbc.username:");
		userNameLabel.setBounds(15, 71, 127, 15);
		add(userNameLabel);

		this.userNameTextField = new JTextField();
		this.userNameTextField.setBounds(140, 65, 350, 25);
		add(this.userNameTextField);

		this.userNameTextField.setText(InitParamHolder.getInitParam().getJdbcUserName());
	}

	private void initPasswordTextField() {
		JLabel passwordLabel = new JLabel("jdbc.password:");
		passwordLabel.setBounds(15, 105, 127, 15);
		add(passwordLabel);

		this.passwordTextField = new JTextField();
		this.passwordTextField.setBounds(140, 100, 350, 25);
		add(this.passwordTextField);

		this.passwordTextField.setText(InitParamHolder.getInitParam().getJdbcPassword());
	}

	private void initAttachDBButton() {
		this.attachDBButton = new JButton("链接");
		this.attachDBButton.setBounds(15, 135, 85, 30);
		add(this.attachDBButton);

		this.attachDBButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JDBCMetadataResolver jdbcMetadataResolver=new JDBCMetadataResolver(JDBCMetadataResolver.MYSQL_DRIVER_CLASS_NAME, DatebaseMetadataModule.this.urlTextField.getText(), DatebaseMetadataModule.this.userNameTextField.getText(), DatebaseMetadataModule.this.passwordTextField.getText());
					if (null != DatebaseMetadataModule.this.connectionListener) {
						DatebaseMetadataModule.this.connectionListener.connPerformed(jdbcMetadataResolver);
					}
				} catch (Exception exception) {
					DialogUtils.showErrorMessageOnDialog(exception.getLocalizedMessage());
					LogHolder.getLog().info(exception);
				}
			}
		});
	}

	public interface ConnectionListener {
		void connPerformed(JDBCMetadataResolver jdbcMetadataResolver);
	}

}
