package org.xllapp.maven.plugin.mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;
import org.xllapp.maven.plugin.log.LogHolder;
import org.xllapp.maven.plugin.mvc.jdbc.ColumnHelper;
import org.xllapp.maven.plugin.mvc.jdbc.JDBCMetadataResolver;
import org.xllapp.maven.plugin.mvc.model.ColumnModel;
import org.xllapp.maven.plugin.mvc.model.TableModel;
import org.xllapp.maven.plugin.mvc.model.TemplateModel;
import org.xllapp.maven.plugin.mvc.model.ColumnModel.InputType;
import org.xllapp.maven.plugin.mvc.model.ColumnModel.WhereFilter;
import org.xllapp.maven.plugin.ui.CheckBoxCellEditor;
import org.xllapp.maven.plugin.ui.CheckBoxRenderer;
import org.xllapp.maven.plugin.ui.ComboBoxCellRenderer;
import org.xllapp.maven.plugin.ui.DialogUtils;
import org.xllapp.maven.plugin.ui.JTableUtil;
import org.xllapp.maven.plugin.ui.MojoWindow;
import org.xllapp.maven.plugin.ui.TableWithCellTip;

/**
 * 
 * 
 * @author dylan.chen Apr 4, 2014
 * 
 */
public class MainWindow extends MojoWindow {

	private static final long serialVersionUID = 3169064316382205345L;

	private JPanel infoPanel;

	private JTable table;
	private JComboBox tableNamesComboBox;
	private JTextField entityNameTextField;
	private JTextField packageTextField;
	private JComboBox idComboBox;
	private JComboBox templateTypeComboBox;

	private JButton generateButton;

	private JDBCMetadataResolver jdbcMetadataResolver;

	public MainWindow() {
		setTitle("代码生成器");
		setBounds(100, 100, 736, 630);
		initialize();
	}

	private void initialize() {

		initJdbcPanel();
		initInfoPanel();

		initTableNameComboBox();
		initTable();
		initGenerateButton();

		initEntityNameTextField();

		initPackageTextField();
		initTemplateTypeComboBox();
		initIdComboBox();
	}

	private void initJdbcPanel() {
		DatebaseMetadataModule datebaseMetadataModule = new DatebaseMetadataModule(new DatebaseMetadataModule.ConnectionListener() {

			@Override
			public void connPerformed(JDBCMetadataResolver jdbcMetadataResolver) {

				MainWindow.this.jdbcMetadataResolver = jdbcMetadataResolver;

				MainWindow.this.tableNamesComboBox.removeAllItems();
				MainWindow.this.tableNamesComboBox.addItem("选择数据库表");
				MainWindow.this.entityNameTextField.setText("");
				MainWindow.this.idComboBox.removeAllItems();

				try {
					String[] tableNames = jdbcMetadataResolver.retriveTableNames();
					for (String tableName : tableNames) {
						MainWindow.this.tableNamesComboBox.addItem(tableName);
					}
					MainWindow.this.tableNamesComboBox.setEnabled(true);

				} catch (Exception exception) {
					DialogUtils.showErrorMessageOnDialog(exception.getLocalizedMessage());
					LogHolder.getLog().info(exception);
				}

			}

		});
		datebaseMetadataModule.setBounds(20, 15, 693, 175);
		this.getContentPane().add(datebaseMetadataModule);
	}

	private void initInfoPanel() {
		this.infoPanel = new JPanel();
		this.infoPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.infoPanel.setBounds(20, 202, 691, 326);
		this.getContentPane().add(this.infoPanel);
		this.infoPanel.setLayout(null);
	}

	private void initEntityNameTextField() {
		JLabel entityNameLabel = new JLabel("实体名:");
		entityNameLabel.setBounds(361, 58, 57, 20);
		this.infoPanel.add(entityNameLabel);

		this.entityNameTextField = new JTextField();
		this.entityNameTextField.setBounds(410, 57, 260, 25);
		this.infoPanel.add(this.entityNameTextField);
	}

	private void initPackageTextField() {
		JLabel packageLabel = new JLabel("父包名:");
		packageLabel.setBounds(15, 62, 44, 15);
		this.infoPanel.add(packageLabel);

		this.packageTextField = new JTextField();
		this.packageTextField.setBounds(83, 57, 260, 25);
		this.infoPanel.add(this.packageTextField);

		this.packageTextField.setText(InitParamHolder.getInitParam().getBasePackage());

	}

	private void initTemplateTypeComboBox() {
		JLabel frameworkLabel = new JLabel("模板类型:");
		frameworkLabel.setBounds(15, 97, 57, 15);
		this.infoPanel.add(frameworkLabel);

		this.templateTypeComboBox = new JComboBox();
		this.templateTypeComboBox.setBounds(83, 93, 260, 25);

		for (TemplateType templateType : TemplateType.values()) {
			this.templateTypeComboBox.addItem(templateType);
		}
		this.templateTypeComboBox.setSelectedItem(TemplateType.ICITY_MVC);
		this.infoPanel.add(this.templateTypeComboBox);

	}

	private void initIdComboBox() {
		JLabel idLabel = new JLabel("ID 列:");
		idLabel.setBounds(361, 27, 49, 15);
		this.infoPanel.add(idLabel);

		this.idComboBox = new JComboBox();
		this.idComboBox.setBounds(410, 22, 260, 25);
		this.infoPanel.add(this.idComboBox);
	}

	private void initTableNameComboBox() {
		JLabel tableNamesLabel = new JLabel("数据库表:");
		tableNamesLabel.setBounds(15, 27, 57, 15);
		this.infoPanel.add(tableNamesLabel);

		this.tableNamesComboBox = new JComboBox();
		this.tableNamesComboBox.setBounds(83, 22, 260, 25);
		this.tableNamesComboBox.setEnabled(false);
		this.infoPanel.add(this.tableNamesComboBox);

		this.tableNamesComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String tableName = getSelectedTableName();

					if (null != tableName) {

						TableModel tableModel = MainWindow.this.jdbcMetadataResolver.retriveTable(tableName);

						MainWindow.this.idComboBox.removeAllItems();// 清空ID列选择框

						DefaultTableModel swingTableModel = (DefaultTableModel) MainWindow.this.table.getModel();
						swingTableModel.setRowCount(0);// 清空Table

						for (ColumnModel column : tableModel.getColumns()) {
							swingTableModel.addRow(new Object[] { column.getColumnName(), column.getJavaFieldName(), new Boolean(true), new Boolean(true), ColumnModel.WhereFilter.NONE, ColumnModel.InputType.TEXT, column.getLabel(), null, null, ColumnHelper.getValidateString(column), new Boolean(false) });
							MainWindow.this.idComboBox.addItem(column.getColumnName());
						}

						ColumnModel idColumn = tableModel.getDefaultIdColumn();
						if (idColumn != null) {
							MainWindow.this.idComboBox.setSelectedItem(idColumn.getColumnName());
						}

						MainWindow.this.table.getColumnModel().getColumn(2).setCellEditor(new CheckBoxCellEditor());
						MainWindow.this.table.getColumnModel().getColumn(2).setCellRenderer(new CheckBoxRenderer());

						MainWindow.this.table.getColumnModel().getColumn(3).setCellEditor(new CheckBoxCellEditor());
						MainWindow.this.table.getColumnModel().getColumn(3).setCellRenderer(new CheckBoxRenderer());

						MainWindow.this.table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JComboBox(ColumnModel.WhereFilter.values())));
						MainWindow.this.table.getColumnModel().getColumn(4).setCellRenderer(new ComboBoxCellRenderer(ColumnModel.WhereFilter.NONE));

						MainWindow.this.table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JComboBox(ColumnModel.InputType.values())));
						MainWindow.this.table.getColumnModel().getColumn(5).setCellRenderer(new ComboBoxCellRenderer(ColumnModel.InputType.TEXT));

						MainWindow.this.table.getColumnModel().getColumn(10).setCellEditor(new CheckBoxCellEditor());
						MainWindow.this.table.getColumnModel().getColumn(10).setCellRenderer(new CheckBoxRenderer());

						MainWindow.this.entityNameTextField.setText(tableModel.getEntityClassName());
						MainWindow.this.entityNameTextField.setEnabled(true);

					}
				} catch (Exception exception) {
					DialogUtils.showErrorMessageOnDialog(exception.getLocalizedMessage());
					LogHolder.getLog().info(exception);
				}
			}
		});
	}

	private String getSelectedTableName() {
		int selectedIndex = this.tableNamesComboBox.getSelectedIndex();
		if (selectedIndex > 0) {
			return this.tableNamesComboBox.getSelectedItem().toString();
		}
		return null;
	}

	@SuppressWarnings("serial")
	private void initTable() {
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setBounds(15, 142, 664, 172);
		tableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.infoPanel.add(tableScrollPane);

		String[] cells = { "表列名", "实体类字段名", "包含在实体类中", "在表格中显示", "查询过滤条件", "表单域类型", "表单域的标签", "selectKeys", "selectValues", "表单域验证表达式", "初始值为系统时间" };
		final String[] cellToolTips = { "数据库中的列名", "与列名对应的实体类中字段名", "此列是否包含在实体类中", "是否在列表页面的表格中显示此列", "此列是否为列表页面查询表单的过滤条件", "在录入页面和列表页面的查询表单中与此列对应的表单域类型.在列表页面的查询表单中只支持text,select和date这3种类型,其他类型都使用text类型展示", "在录入页面和列表页面中与此列对应的表单域的标签名", "与此列对应的表单域类型为select时,选择项目的展示名列表.使用','分割", "与此列对应的表单域类型为select时,选择项目的值列表.使用','分割", "在录入页面中与此列对应的表单域的jquery.validate验证表达式.多个表达式,使用','分割", "在新增时,此列的值取当前的系统时间并且此列值无法修改.即在新增/修改页面中此列对应的输入框是灰的(一般对于数据库的'创建时间'等字段,勾选此选项)" };

		DefaultTableModel tableModel = new DefaultTableModel(null, cells) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return !(column == 0 || column == 1);
			}
		};

		this.table = new TableWithCellTip(cellToolTips);
		this.table.setModel(tableModel);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTableUtil.fitTableColumns(this.table);
		this.table.setRowHeight(20);
		tableScrollPane.setViewportView(this.table);
	}

	private void initGenerateButton() {
		this.generateButton = new JButton("生成");
		this.generateButton.setBounds(20, 540, 85, 30);
		this.generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					if (MainWindow.this.table.isEditing()) {
						MainWindow.this.table.getCellEditor().stopCellEditing();
					}

					generateCodeAndOut(buildTemplateModel());

				} catch (Exception exception) {
					DialogUtils.showErrorMessageOnDialog(exception.getLocalizedMessage());
					LogHolder.getLog().info(exception);
				}

			}
		});
		this.getContentPane().add(this.generateButton);
	}

	private TemplateModel buildTemplateModel() throws Exception {
		TemplateModel templateModel = new TemplateModel();
		templateModel.setTemplateType((TemplateType) MainWindow.this.templateTypeComboBox.getSelectedItem());

		String tableName = getSelectedTableName();
		if (StringUtils.isBlank(tableName)) {
			throw new Exception("表名不能为空");
		}

		TableModel tableModel = MainWindow.this.jdbcMetadataResolver.retriveTable(tableName);

		templateModel.setTableModel(tableModel);

		tableModel.setIdColumn(MainWindow.this.idComboBox.getSelectedItem() + "");

		String basePackage = MainWindow.this.packageTextField.getText();
		if (StringUtils.isBlank(basePackage)) {
			throw new Exception("包名不能为空");
		}
		templateModel.setBasePackage(basePackage);

		String entityName = MainWindow.this.entityNameTextField.getText();
		if (StringUtils.isBlank(entityName)) {
			throw new Exception("实体名不能为空");
		}
		templateModel.getTableModel().setEntityClassName(entityName);

		// 清空上一次生成的数据,解决：由于重复点击“生成”按钮,而导致的生成的Entity类中字段重复问题
		templateModel.getTableModel().getIncludedColumns().clear();

		DefaultTableModel defaultTableModel = (DefaultTableModel) MainWindow.this.table.getModel();
		int rowCount = defaultTableModel.getRowCount();

		ColumnModel[] columnModels = templateModel.getTableModel().getColumns().toArray(new ColumnModel[0]);

		int includedCount = 0;

		for (int i = 0; i < rowCount; i++) {

			Boolean included = (Boolean) defaultTableModel.getValueAt(i, 2);

			if (included) {

				ColumnModel columnModel = columnModels[i];
				columnModel.setHtmlTableRow((Boolean) defaultTableModel.getValueAt(i, 3));
				columnModel.setWhereFilter((WhereFilter) defaultTableModel.getValueAt(i, 4));
				columnModel.setInputType((InputType) defaultTableModel.getValueAt(i, 5));
				columnModel.setLabel((String) defaultTableModel.getValueAt(i, 6));
				columnModel.setSelectKeys((String) defaultTableModel.getValueAt(i, 7));
				columnModel.setSelectValues((String) defaultTableModel.getValueAt(i, 8));
				columnModel.setValidateString((String) defaultTableModel.getValueAt(i, 9));
				columnModel.setSysdateAsValue((Boolean) defaultTableModel.getValueAt(i, 10));

				templateModel.getTableModel().addIncludedColumn(columnModel);

				includedCount++;

			}

		}

		if (0 == includedCount) {
			throw new Exception("至少包含一列");
		}

		return templateModel;
	}

	private void generateCodeAndOut(TemplateModel templateModel) throws Exception {
		LogHolder.getLog().debug(templateModel);
		Map<String, String> codes = new CodeGenerator().generate(templateModel);
		// LogHolder.getLog().debug(codes);
		DialogUtils.showCodeOnDialog(MainWindow.this, codes);
	}

	public static void main(String[] args) {
		new MainWindow().showOnCaller();
	}
}
