package common.component;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import common.util.string.UtilString;

import consts.ImageContext;

public class STable {

	private JTable jtable;
	protected JPopupMenu pmenu;

	// 根据StableBean构造
	@SuppressWarnings({ "unchecked"})
	public STable(final STableBean bean) {
		try {
			this.jtable = new JTable() {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					boolean rs = false;
					if (bean.getCellEditableColumn() != null)
						for (int i = 0; i < bean.getCellEditableColumn().length; i++) {
							if (column == bean.getCellEditableColumn()[i]) {
								rs = true;
							}
						}
					return rs;
				}

				public void tableChanged(TableModelEvent e) {
					super.tableChanged(e);
					repaint();
				}

			};
			this.jtable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					if (evt.getClickCount() == 2) {
						mouseDoubleClick();
					}
					mouseClick();
				}

				public void mouseReleased(MouseEvent e) {
					if (pmenu != null) {
						if (e.isPopupTrigger()) {
							pmenu.show(e.getComponent(), e.getX(), e.getY());
						}
					}
				}
			});
			if (bean.isChenckHeader()) {
				CheckTableModle tableModel = new CheckTableModle(bean.getTableValue(), bean.getColumnName());
				this.jtable.setModel(tableModel);
				this.jtable.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(this.jtable));
			} else {
				DefaultTableModel tableModel = new DefaultTableModel(bean.getTableValue(), bean.getColumnName());
				this.jtable.setModel(tableModel);
			}
			RowSorter<?> sorter = new TableRowSorter(this.jtable.getModel());
			this.jtable.setRowSorter((RowSorter<? extends TableModel>) sorter);

			this.jtable.getTableHeader().setReorderingAllowed(bean.isReorderingAllowed());// 禁止拖动列
			this.jtable.getTableHeader().setResizingAllowed(bean.isResizingAllowed());// 禁止改变列
			this.setColumnWidth(bean.getColumnWidth());
			this.hideColumn(bean.getColumnHide());
			this.setPmenu(null);

			this.jtable.addMouseMotionListener(new MouseMotionListener() {
				public void mouseDragged(MouseEvent e) {
				}

				public void mouseMoved(MouseEvent e) {
					Point point = e.getPoint();
					int row = jtable.rowAtPoint(point);
					int col = jtable.columnAtPoint(point);
					String value = UtilString.isNil(jtable.getValueAt(row, col));
					if (value != null) {
						jtable.setToolTipText(value);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void autoColumnWidth(JTable table) {
		JTableHeader header = table.getTableHeader();
		int rowCount = table.getRowCount();
		Enumeration<?> columns = table.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, column.getIdentifier(), false, false,
					-1, col).getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) table.getCellRenderer(row, col).getTableCellRendererComponent(table, table.getValueAt(row, col), false,
						false, row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width + table.getIntercellSpacing().width + 10);
		}
	}

	// 设置列宽
	private void setColumnWidth(int[] width) {
		if (width == null) {
			this.jtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			autoColumnWidth(this.jtable);
		} else {
			this.jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnModel cm = jtable.getColumnModel(); // 表格的列模型
			if (width != null) {
				for (int i = 0; i < width.length; i++) {
					if (width[i] >= 0)
						cm.getColumn(i).setPreferredWidth(width[i]);
				}
			}
		}
	}

	// 设置隐藏列
	private void hideColumn(int[] column) {
		if (column != null) {
			for (int i = 0; i < column.length; i++) {
				jtable.getTableHeader().getColumnModel().getColumn(column[i]).setMaxWidth(0);
				jtable.getTableHeader().getColumnModel().getColumn(column[i]).setMinWidth(0);
				jtable.getTableHeader().getColumnModel().getColumn(column[i]).setWidth(0);
				jtable.getTableHeader().getColumnModel().getColumn(column[i]).setPreferredWidth(0);
			}
		}
	}

	protected void mouseClick() {

	}

	protected void mouseDoubleClick() {

	}

	public JTable getJtable() {
		return jtable;
	}

	public void setJtable(JTable jtable) {
		this.jtable = jtable;
	}

	public JPopupMenu getPmenu() {
		return pmenu;
	}

	public String getColumnValue(int row, int col) {
		return UtilString.isNil(this.jtable.getValueAt(row, col));
	}

	public String getColumnValue(int row, String columnName) {
		int col = 0;
		for (int i = 0; i < this.jtable.getColumnCount(); i++) {
			if (columnName.trim().equalsIgnoreCase(this.jtable.getColumnName(i).trim())) {
				col = i;
				break;
			}
		}
		return UtilString.isNil(this.jtable.getValueAt(row, col));
	}

	// 设置浮动菜单
	public void setPmenu(JPopupMenu pmenu) {
		if (pmenu == null)
			pmenu = new JPopupMenu();
		this.pmenu = pmenu;
		this.pmenu.addSeparator();
		final SMenuItem miCopyRecord = new SMenuItem("复制记录", ImageContext.Copy);
		miCopyRecord.addActionListener(new ActionListener() {// 浮动菜单
					public void actionPerformed(ActionEvent arg0) {
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(getRecord()), null);
					}
				});

		this.pmenu.add(miCopyRecord);

		final SMenuItem miCopyField = new SMenuItem("复制列值", ImageContext.Copy);
		miCopyField.addActionListener(new ActionListener() {// 浮动菜单
					public void actionPerformed(ActionEvent arg0) {
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(getFieldValue()), null);
					}
				});

		this.pmenu.add(miCopyField);

	}

	public String getRecord() {
		if (jtable.getSelectedRow() < 0)
			return "";
		StringBuilder sbRecord = new StringBuilder();
		for (int i = 0; i < jtable.getColumnCount(); i++) {
			sbRecord.append(jtable.getValueAt(jtable.getSelectedRow(), i)).append(" ");
		}
		return sbRecord.toString();
	}

	public String getFieldValue() {
		if (jtable.getSelectedRow() < 0)
			return "";
		return jtable.getValueAt(jtable.getSelectedRow(), jtable.getSelectedColumn()).toString();
	}
}
