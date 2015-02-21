package org.xllapp.maven.plugin.ui;

import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 * 带有列提示的表格.
 * 
 * @author dylan.chen Jul 10, 2014
 * 
 */
public class TableWithCellTip extends JTable {

	private static final long serialVersionUID = 6363782600203911769L;

	private String[] cellToolTips;

	public TableWithCellTip(String[] cellToolTips) {
		this.cellToolTips = cellToolTips;
	}

	@SuppressWarnings("serial")
	@Override
	protected JTableHeader createDefaultTableHeader() {
		return new JTableHeader(this.columnModel) {
			@Override
			public String getToolTipText(MouseEvent e) {
				int index = this.columnModel.getColumn(this.columnModel.getColumnIndexAtX(e.getPoint().x)).getModelIndex();
				return TableWithCellTip.this.cellToolTips[index];
			}
		};
	}

}
