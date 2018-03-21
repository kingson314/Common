package common.util.swing;

import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;
import common.util.log.UtilLog;
import common.util.string.UtilString;

public class UtilComponent {
	/**
	 * @Description:
	 * @date Jan 27, 2014
	 * @author:fgq
	 */

	// RadionButton组控制
	public static void enableRadion(JRadioButtonMenuItem[] radion, int index, boolean enabled) {
		for (int i = 0; i < radion.length; i++) {
			if (radion[i] != null) {
				if (i != index)
					radion[i].setSelected(enabled);
				else
					radion[i].setSelected(!enabled);
			}
		}
	}

	// TextField 插入字符串// TextArea 插入字符串
	public static void addString(JTextComponent com, String str) {
		try {
			int position = com.getCaretPosition();
			String text = com.getText();
			text = text.substring(0, position) + str + text.substring(position, text.length());
			com.setText(text);
			com.setCaretPosition(position);
		} catch (Exception e) {
			UtilLog.logError("JTextComponent插入字符串错误:", e);
		} finally {
		}
	}

	// 获取表格中选中所有行的某一列的字段值的字符串
	public static String getTableSelectedRowFiled(JTable jtable, int fieldCount) {
		String rs = "";
		try {
			for (int i = 0; i < jtable.getRowCount(); i++) {
				Boolean selected = Boolean.valueOf(jtable.getValueAt(i, 0).toString());
				if (selected) {
					rs += jtable.getValueAt(i, fieldCount).toString() + ",";
				}
			}
			if (rs.endsWith(","))
				rs = rs.substring(0, rs.length() - 1);

		} catch (Exception e) {
			UtilLog.logError("获取表格选择行数错误:", e);
			return rs;
		}
		return rs;
	}

	// 获取表格勾选的行数
	public static int getTableSelectedCount(JTable jtable) {
		int rs = 0;
		try {
			for (int i = 0; i < jtable.getRowCount(); i++) {
				Boolean selected = Boolean.valueOf(jtable.getValueAt(i, 0).toString());
				if (selected) {
					rs += 1;
				}
			}
		} catch (Exception e) {
			UtilLog.logError("获取表格选择行数错误:", e);
			return rs;
		}
		return rs;
	}

	public static void setTbaleSelect(JTable jtable, String items, int fieldIndex) {
		if ("".equals(UtilString.isNil(items)))
			return;
		String[] arrItem = items.split(",");
		for (int i = 0; i < jtable.getRowCount(); i++) {
			for (int j = 0; j < arrItem.length; j++) {
				if (arrItem[j].equalsIgnoreCase(jtable.getValueAt(i, fieldIndex).toString())) {
					jtable.setValueAt(true, i, 0);
					continue;
				}
			}
		}
	}

	public static String getColumnValue(JTable jtable, String columnName) {
		int row = jtable.getSelectedRow();
		return getColumnValue(jtable, row, columnName);
	}

	public static String getColumnValue(JTable jtable, int row, String columnName) {
		int col = 0;
		for (int i = 0; i < jtable.getColumnCount(); i++) {
			if (columnName.trim().equalsIgnoreCase(jtable.getColumnName(i).trim())) {
				col = i;
				break;
			}
		}
		return UtilString.isNil(jtable.getValueAt(row, col));
	}
}
