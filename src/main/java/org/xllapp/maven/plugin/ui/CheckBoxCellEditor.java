package org.xllapp.maven.plugin.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

public class CheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor {

		private static final long serialVersionUID = 8204213144386536326L;

		protected JCheckBox checkBox;

		public CheckBoxCellEditor() {
			this.checkBox = new JCheckBox();
			this.checkBox.setHorizontalAlignment(SwingConstants.CENTER);
			this.checkBox.setBackground(Color.white);
		}

		@Override
		public Object getCellEditorValue() {
			return Boolean.valueOf(this.checkBox.isSelected());
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			this.checkBox.setSelected(((Boolean) value).booleanValue());
			return this.checkBox;
		}

	}