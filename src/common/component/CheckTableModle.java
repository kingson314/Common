package common.component;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class CheckTableModle extends DefaultTableModel {
	@SuppressWarnings("rawtypes")
	public CheckTableModle(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	// 根据类型返回显示空间 布尔类型返回显示checkbox
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
		Class cl = null;
		if (getRowCount() == 0)
			return String.class;
		if (null != getValueAt(0, c)) {
			cl = getValueAt(0, c).getClass();
		} else {
			cl = String.class;
		}
		return cl;
	}

	public void selectAllOrNull(boolean value) {
		for (int i = 0; i < getRowCount(); i++) {
			this.setValueAt(value, i, 0);
		}
	}

}
