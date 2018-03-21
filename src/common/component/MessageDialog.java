package common.component;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import javax.swing.JTable;
import common.util.log.UtilLog;

public class MessageDialog extends SDialog {

	private SScrollPane SScrollPane;
	private static final long serialVersionUID = 1L;
	public JTable jTable;

	// public static void main(String[] args) {
	//
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// MessageDialog inst = new MessageDialog("FTP信息",
	// ImageContext.Ftp, new FtpTable().getJtable());
	// inst.setVisible(true);
	// }
	// });
	// }

	// 构造
	public MessageDialog(String title, String imgePath, JTable table) {
		super();
		try {
			this.setTitle(title);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(imgePath));
			jTable = table;
			initGUI();
		} catch (Exception e) {
			UtilLog.logError("信息显示对话框构造错误:", e);
		} finally {
		}

	}

	// 初始化界面
	private void initGUI() {
		try {
			setModal(true);
			setAlwaysOnTop(false);
			GridLayout thisLayout = new GridLayout(1, 1);
			thisLayout.setColumns(1);
			thisLayout.setHgap(5);
			thisLayout.setVgap(5);
			getContentPane().setLayout(thisLayout);
			this.setBounds(0, 0, 667, 300);
			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
			this.setLocation(w, h);
			{
				SScrollPane = new SScrollPane(jTable);
				getContentPane().add(SScrollPane, SSplitPane.TOP);
			}
			this.addWindowListener(new WindowAdapter() {// 添加窗体退出事件
						public void windowClosing(java.awt.event.WindowEvent evt) {
							dispose();
						}
					});
		} catch (Exception e) {
			UtilLog.logError("对话框初始化界面错误:", e);
		} finally {
		}
	}

}
