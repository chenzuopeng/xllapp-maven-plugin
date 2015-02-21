package org.xllapp.maven.plugin.ui;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * 
 * @author dylan.chen May 12, 2014
 * 
 */
public class ComboBoxCellRenderer extends JComboBox implements TableCellRenderer {

	private static final long serialVersionUID = -3381980667434731276L;
	
	private Object defaultItem;
	
	public ComboBoxCellRenderer(Object defaultItem){
		this.defaultItem=defaultItem;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value != null) {
			removeAllItems();
			addItem(value);
		}else{
			addItem(this.defaultItem);
		}
		return this;
	}
}
