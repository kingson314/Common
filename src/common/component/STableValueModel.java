package common.component;

import java.sql.*;

import javax.swing.table.AbstractTableModel;

public class STableValueModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private ResultSet rs;
	private ResultSetMetaData rsmd;

	public STableValueModel(ResultSet rs) {
		this.rs = rs;
		try {
			this.rsmd = rs.getMetaData();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public int getRowCount() {
		try {
			rs.last();
			return rs.getRow();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public int getColumnCount() {
		try {
			return rsmd.getColumnCount();
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public String getColumnName(int columnIndex) {
		try {
			return rsmd.getColumnName(columnIndex + 1);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			rs.absolute(rowIndex + 1);
			return rs.getObject(columnIndex + 1);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}