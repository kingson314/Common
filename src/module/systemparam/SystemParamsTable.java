package module.systemparam;

import java.util.Vector;
import javax.swing.JTable;

import common.component.STable;
import common.component.STableBean;
import common.util.log.UtilLog;

public class SystemParamsTable {
	private JTable jtable;

	// 构造系统参数表格
	public SystemParamsTable(String module, String paramName) {
		try {
			Vector<String> columnName = new Vector<String>();
			columnName.add("所属模块名称");
			columnName.add("参数名称");
			columnName.add("参数值");
			columnName.add("说明");

			Vector<?> tableValue = SystemParamsDao.getInstance()
					.getSysgemParams(module, paramName);
			int[] cellEditableColumn = null;
			int[] columnWidth = null;
			int[] columnHide = null;
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue,
					cellEditableColumn, columnWidth, columnHide,
					isChenckHeader, isReorderingAllowed, isResizingAllowed);
			STable table = new STable(bean);
			jtable = table.getJtable();
		} catch (Exception e) {
			UtilLog.logError("系统参数列表构造错误:", e);
		} finally {
		}
	}
	// 获取系统参数表格
	public JTable getJtable() {
		return jtable;
	}

}
