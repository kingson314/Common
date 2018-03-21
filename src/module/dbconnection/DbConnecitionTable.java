package module.dbconnection;

import java.util.Vector;

import javax.swing.JTable;

import common.component.STable;
import common.component.STableBean;
import common.util.log.UtilLog;

public class DbConnecitionTable {
	private JTable jtable;

	// 构造
	public DbConnecitionTable() {
		try {
			Vector<String> columnName = new Vector<String>();
			columnName.add("数据源名称");
			columnName.add("数据源类型");
			columnName.add("数据源驱动程序类");
			columnName.add("链接字符串");
			columnName.add("用户名");
			columnName.add("密码");

			Vector<?> tableValue = DbConnectionDao.getInstance().getDbConn();
			int[] cellEditableColumn = null;
			int[] columnWidth = null;
			int[] columnHide = new int[] { 5 };
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);

			STable table = new STable(bean);
			jtable = table.getJtable();

		} catch (Exception e) {
			UtilLog.logError("数据源配置列表构造错误:", e);
		} finally {
		}
	}

	public JTable getJtable() {
		return jtable;
	}

}
