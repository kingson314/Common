package module.mail;

import java.util.Vector;

import javax.swing.JTable;

import common.component.STable;
import common.component.STableBean;
import common.util.log.UtilLog;

public class MailTable {
	private static JTable jtable;

	// 构造
	public MailTable() {
		try {
			Vector<String> columnName = new Vector<String>();
			columnName.add("发送邮箱地址");
			columnName.add("发送邮箱名称");
			columnName.add("发送邮箱密码");
			columnName.add("邮件服务器地址");

			Vector<?> tableValue = MailDao.getInstance().getMailSenderVector();
			int[] cellEditableColumn = null;
			int[] columnWidth = null;
			int[] columnHide = new int[] { 2 };
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
			STable table = new STable(bean);
			jtable = table.getJtable();

		} catch (Exception e) {
			UtilLog.logError("邮箱信息列表构造错误:", e);
		} finally {
		}
	}

	public JTable getJtable() {
		return jtable;
	}

}
