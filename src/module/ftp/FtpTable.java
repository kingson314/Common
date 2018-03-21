package module.ftp;

import java.util.Vector;
import javax.swing.JTable;

import common.component.STable;
import common.component.STableBean;
import common.util.log.UtilLog;


public class FtpTable {
	private JTable jtable;

	// 构造ftp信息表格
	public FtpTable() {
		try {
			Vector<String> columnName = new Vector<String>();
			columnName.add("FTP标识");
			columnName.add("FTP名称");
			columnName.add("FTP类型");
			columnName.add("IP地址");
			;
			columnName.add("端口");
			columnName.add("用户名");
			columnName.add("密码");
			columnName.add("文件路径");
			columnName.add("FTP说明");

			Vector<?> tableValue = FtpDao.getInstance().getFtpSiteVector();
			int[] cellEditableColumn = null;
			int[] columnWidth = null;
			int[] columnHide = new int[] { 5, 6 };
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
			STable table = new STable(bean);
			jtable = table.getJtable();
		} catch (Exception e) {
			UtilLog.logError("FTP信息列表构造错误:", e);
		}
	}

	public JTable getJtable() {
		return jtable;
	}

}
