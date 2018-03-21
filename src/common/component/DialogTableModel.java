package common.component;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;


import common.util.log.UtilLog;
import consts.ImageContext;

public class DialogTableModel extends SDialog {
	private static final long serialVersionUID = 1L;
	private SSplitPane splView;
	private SSplitPane splMain;
	private SSplitPane splTool;
	protected SScrollPane scrlTable;
	protected JTable jTable;
	private SButton btnMod;
	private SButton btnExit;
	private SButton btnDel;
	private SButton btnCancle;
	private SButton btnPost;
	private SButton btnAdd;
	private SButton btnTest;
	private int x = 0;
	protected int paramsPanelHeight = 145;

	// public static void main(String[] args) {
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// MailDialog inst = new MailDialog();
	// inst.setVisible(true);
	// }
	// });
	// }

	// 构造
	public DialogTableModel(int type) {
		super();
		try {
			if (type == 0) {// 非测试类型
				this.x = 60;
			} else if (type == 1) {// 测试类型
				this.x = 0;
			}
		} catch (Exception e) {
			UtilLog.logError("表格模型对话框构造错误:", e);
		} finally {
		}
	}

	// 初始化
	protected void ini() {
		try {
			initGUI();
			btnAdd.setEnabled(true);
			btnMod.setEnabled(true);
			btnDel.setEnabled(true);
			btnPost.setEnabled(false);
			btnCancle.setEnabled(false);
			btnExit.setEnabled(true);
			jTable.setEnabled(true);
			if (this.x > 0) {// 非测试类型
				btnTest.setVisible(false);
			} else if (this.x == 0) {// 测试类型
				btnTest.setVisible(true);
			}
			// this.addKeyListener(new KeyListener());
		} catch (Exception e) {
			UtilLog.logError("表格模型对话框初始化错误:", e);
		} finally {
		}
	}

	protected JToolBar getQueryTb() {
		return null;
	}

	// 获取参数面板
	protected JPanel GetParamPanel() {
		return null;
	}

	private JPanel getToolPanel() {
		JPanel pnlTool = new JPanel();
		{
			pnlTool.setLayout(null);
			{
				btnAdd = new SButton("\u6dfb  \u52a0", ImageContext.Add);
				pnlTool.add(btnAdd);
				btnAdd.setBounds(x + 16, 25, 100, 25);
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						add();
					}
				});
			}
			{
				btnMod = new SButton("\u4fee  \u6539", ImageContext.Mod);
				pnlTool.add(btnMod);
				btnMod.setBounds(x + 126, 25, 100, 25);
				btnMod.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						mod();
					}
				});
			}
			{
				btnPost = new SButton("\u63d0  \u4ea4", ImageContext.Post);
				pnlTool.add(btnPost);
				btnPost.setBounds(x + 236, 25, 100, 25);
				btnPost.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						post();
					}
				});
			}
			{
				btnCancle = new SButton("\u53d6  \u6d88", ImageContext.Cancel);
				pnlTool.add(btnCancle);
				btnCancle.setBounds(x + 346, 25, 100, 25);
				btnCancle.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						cancle();
					}
				});
			}
			{
				btnDel = new SButton("\u5220  \u9664", ImageContext.Del);
				pnlTool.add(btnDel);
				btnDel.setBounds(x + 456, 25, 100, 25);
				btnDel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						del();
					}
				});
			}
			{
				btnExit = new SButton("\u9000  \u51fa", ImageContext.Exit);
				pnlTool.add(btnExit);
				btnExit.setBounds(x + 567, 25, 100, 25);
				btnExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						exit();
					}
				});
			}
			{
				btnTest = new SButton("测试连接", ImageContext.Test);
				pnlTool.add(btnTest);
				btnTest.setBounds(700, 25, 105, 25);
				btnTest.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						test();
					}
				});
			}
		}
		return pnlTool;
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
			this.setBounds(0, 0, 850, 550);
			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
			this.setLocation(w, h);
			{
				JToolBar tbQuery = getQueryTb();
				if (tbQuery == null)
					splMain = new SSplitPane(0, 0, false);
				else {
					splMain = new SSplitPane(0, 40, false);
					splMain.setDividerSize(1);
					splMain.add(tbQuery, SSplitPane.TOP);
					this.setBounds(0, 0, 850, 580);
				}
				getContentPane().add(splMain);

				splView = new SSplitPane(0, 250, false);
				splMain.add(splView, SSplitPane.BOTTOM);
				splView.setEnabled(false);
				scrlTable = new SScrollPane(jTable);
				splView.add(scrlTable, SSplitPane.TOP);
				{
					splTool = new SSplitPane(0, paramsPanelHeight, false);
					splView.add(splTool, SSplitPane.BOTTOM);
					splTool.setEnabled(false);
					splTool.add(GetParamPanel(), SSplitPane.TOP);
					splTool.add(getToolPanel(), SSplitPane.BOTTOM);
				}

			}
		} catch (Exception e) {
			UtilLog.logError("表格模型对话框初始化界面错误:", e);
		} finally {
		}
	}

	// 测试
	protected void test() {
		btnTest.setEnabled(false);
	}

	// 设置测试按钮状态
	protected void setTestEnable() {
		btnTest.setEnabled(true);
	}

	// 新增
	protected void add() {
		try {
			btnAdd.setEnabled(false);
			btnMod.setEnabled(false);
			btnDel.setEnabled(false);
			btnPost.setEnabled(true);
			btnCancle.setEnabled(true);
			btnExit.setEnabled(false);
			jTable.setEnabled(false);
		} catch (Exception e) {
			UtilLog.logError("表格模型对话框新增事件错误:", e);
		} finally {
		}
	}

	// 修改
	protected boolean mod() {
		boolean rs = true;
		try {
			if (jTable.getSelectedRowCount() <= 0) {
				ShowMsg.showMsg("请选择需要修改的记录！");
				return false;
			}
			btnAdd.setEnabled(false);
			btnMod.setEnabled(false);
			btnDel.setEnabled(false);
			btnPost.setEnabled(true);
			btnCancle.setEnabled(true);
			btnExit.setEnabled(false);
			jTable.setEnabled(false);
		} catch (Exception e) {
			UtilLog.logError("表格模型对话框修改错误:", e);
			return false;
		} finally {
		}
		return rs;
	}

	// 取消
	protected void cancle() {
		btnAdd.setEnabled(true);
		btnMod.setEnabled(true);
		btnDel.setEnabled(true);
		btnPost.setEnabled(false);
		btnCancle.setEnabled(false);
		btnExit.setEnabled(true);
		jTable.setEnabled(true);
	}

	// 删除
	protected boolean del() {
		try {
			int i = ShowMsg.showConfig("确定删除该记录?");
			if (i != 0) {
				return false;
			}
			btnAdd.setEnabled(true);
			btnMod.setEnabled(true);
			btnDel.setEnabled(true);
			btnPost.setEnabled(false);
			btnCancle.setEnabled(false);
			btnExit.setEnabled(true);
			jTable.setEnabled(true);
		} catch (Exception e) {
			UtilLog.logError("表格模型对话框删除错误:", e);
			return false;
		} finally {
		}
		return true;
	}

	// 保存
	protected void post() {
		btnAdd.setEnabled(true);
		btnMod.setEnabled(true);
		btnDel.setEnabled(true);
		btnPost.setEnabled(false);
		btnCancle.setEnabled(false);
		btnExit.setEnabled(true);
		jTable.setEnabled(true);
	}

	// 退出
	protected void exit() {
		this.dispose();
	}

	// 获取table
	public JTable getJTable() {
		return jTable;
	}

	public void setJTable(JTable table) {
		jTable = table;
	}

	protected void setParamsPanelHeight(int paramsPanelHeight) {
		this.paramsPanelHeight = paramsPanelHeight;
	}
}
