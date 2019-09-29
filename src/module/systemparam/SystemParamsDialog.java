package module.systemparam;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;

import common.component.SButton;
import common.component.SComboBox;
import common.component.SDialog;
import common.component.SLabel;
import common.component.SScrollPane;
import common.component.SSplitPane;
import common.component.STextArea;
import common.component.STextField;
import common.component.ShowMsg;
import common.util.log.UtilLog;
import consts.ImageContext;
 
public class SystemParamsDialog extends SDialog {
	private static final long serialVersionUID = 1L;
	private SSplitPane splMain;
	private SSplitPane splTool;
	private SScrollPane sclMain;
	private JTable tbl;
	private SButton btnExit;
	private SButton btnPost;
	private JPanel pnlTool;
	private JPanel pnlParam;
	private SLabel lModule;
	private SLabel lParamName;
	private STextField txtModule;
	private STextField txtParamName;
	private SLabel lParamValue;
	private STextField txtParamValue;
	private SLabel lMemo;
	private STextArea txtMemo;
	private SScrollPane scrl;
	private JToolBar tbQuery;
	private SSplitPane splTable;
	private SLabel lModule1;
	private SComboBox cmbModule;
	private SLabel lParanmName;
	private SComboBox cmbParamName;
	private SButton btnQuery;

	private SystemParams systemParams;

	// public static void main(String[] args) {
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// SystemParamsDialog inst = new SystemParamsDialog();
	// inst.setVisible(true);
	// }
	// });
	// }

	// 构造
	public SystemParamsDialog() {
		super();
		try {
			systemParams = new SystemParams();
			initGUI();
		} catch (Exception e) {
			UtilLog.logError("系统参数对话框构造错误:", e);
		} finally {
		}
	}

	// 获取表格
	private JTable getTable(String module, String paramName) {
		final JTable table = new SystemParamsTable(module, paramName)
				.getJtable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				systemParams.setModule(table.getValueAt(table.getSelectedRow(),
						0).toString());
				systemParams.setParamName(table.getValueAt(
						table.getSelectedRow(), 1).toString());
				systemParams.setParamValue(table.getValueAt(
						table.getSelectedRow(), 2).toString());
				systemParams.setMemo(table
						.getValueAt(table.getSelectedRow(), 3).toString());
				txtModule.setText(systemParams.getModule());
				txtParamName.setText(systemParams.getParamName());
				txtParamValue.setText(systemParams.getParamValue());
				txtMemo.setText(systemParams.getMemo());
			}
		});
		return table;
	}

	// 初始化界面
	private void initGUI() {
		try {
			setTitle("系统参数");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(
					ImageContext.SystemParam));
			setModal(true);
			setAlwaysOnTop(false);
			GridLayout thisLayout = new GridLayout(1, 1);
			thisLayout.setColumns(1);
			thisLayout.setHgap(5);
			thisLayout.setVgap(5);
			getContentPane().setLayout(thisLayout);
			this.setBounds(0, 0, 850, 550);
			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this
					.getWidth()) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this
					.getHeight()) / 2;
			this.setLocation(w, h);
			{
				splMain = new SSplitPane(0, this.getHeight() - 250, false);
				getContentPane().add(splMain);
				splMain.setPreferredSize(new java.awt.Dimension(659, 284));
				splMain.setEnabled(false);
				{
					splTable = new SSplitPane(0, 35, false);
					splMain.add(splTable, SSplitPane.TOP);
					splTable.setEnabled(false);
					splTable.setDividerSize(5);

					tbQuery = new JToolBar();
					tbQuery.setBounds(0, 0, 840, 120);
					tbQuery.setEnabled(false);
					splTable.add(tbQuery, SSplitPane.TOP);
					tbQuery.setLayout(null);
					int x = 20;
					{
						lModule1 = new SLabel("所属模块");

						lModule1.setBounds(x, 6, 54, 21);
						tbQuery.add(lModule1);
						lModule1.setMaximumSize(new java.awt.Dimension(52, 21));
						lModule1.setMinimumSize(new java.awt.Dimension(52, 21));
						x += lModule1.getWidth();
					}
					{
						List<String> list = SystemParamsDao.getInstance()
								.getSystemParamsKey(0);
						cmbModule = new SComboBox(list.toArray());
						tbQuery.add(cmbModule);
						cmbModule.setBounds(x, 4, 120, 21);
						cmbModule.setPreferredSize(new java.awt.Dimension(24,
								21));
						cmbModule.setMaximumSize(new java.awt.Dimension(32767,
								21));
						x += cmbModule.getWidth();

					}
					{
						lParanmName = new SLabel("参数名称");
						tbQuery.add(lParanmName);
						lParanmName.setLocation(new java.awt.Point(0, 0));
						lParanmName.setBounds(x, 6, 54, 21);
						x += lParanmName.getWidth();
					}
					{

						List<String> list = SystemParamsDao.getInstance()
								.getSystemParamsKey(1);
						cmbParamName = new SComboBox(list.toArray());
						tbQuery.add(cmbParamName);
						cmbParamName.setMaximumRowCount(20);
						cmbParamName.setBounds(x, 4, 150, 21);
						cmbParamName.setPreferredSize(new java.awt.Dimension(
								24, 21));
						cmbParamName.setMaximumSize(new java.awt.Dimension(
								32767, 21));
						x += cmbParamName.getWidth();

					}
					{
						btnQuery = new SButton("\u67e5  \u8be2",
								ImageContext.Query);
						tbQuery.add(btnQuery);
						btnQuery.setBounds(x + 50, 2, 90, 25);
						btnQuery.setMaximumSize(new java.awt.Dimension(90, 25));
						btnQuery.setMinimumSize(new java.awt.Dimension(90, 25));
						btnQuery.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								btnQuery();
							}
						});

					}
					tbl = getTable("", "");
					sclMain = new SScrollPane(tbl);

					splTable.add(sclMain, SSplitPane.BOTTOM);
					sclMain.setPreferredSize(new java.awt.Dimension(840, 92));
				}

				{
					splTool = new SSplitPane(0, 120, false);
					splMain.add(splTool, SSplitPane.BOTTOM);
					splTool.setEnabled(false);
					splTool.setDividerSize(5);
					{

						pnlParam = new JPanel();
						pnlParam.setLayout(null);
						{
							txtMemo = new STextArea();
							scrl = new SScrollPane(txtMemo);
							pnlParam.add(scrl, "bottom");
							scrl.setBounds(450, 53, 257, 38);
						}

						lModule = new SLabel("所属模块");
						lModule.setBounds(50, 20, 100, 20);
						pnlParam.add(lModule);
						txtModule = new STextField("");
						txtModule.setBounds(150, 20, 150, 20);
						txtModule.setEditable(false);
						pnlParam.add(txtModule);

						lParamName = new SLabel("参数名称");
						lParamName.setBounds(350, 20, 100, 20);
						pnlParam.add(lParamName);
						txtParamName = new STextField("");
						txtParamName.setBounds(450, 20, 150, 20);
						txtParamName.setEditable(false);
						pnlParam.add(txtParamName);

						lParamValue = new SLabel("参数值");
						lParamValue.setBounds(50, 50, 100, 20);
						pnlParam.add(lParamValue);
						txtParamValue = new STextField("");
						txtParamValue.setBounds(150, 50, 150, 20);
						pnlParam.add(txtParamValue);

						lMemo = new SLabel("参数说明");
						lMemo.setBounds(350, 50, 100, 20);
						pnlParam.add(lMemo);

						splTool.add(pnlParam, SSplitPane.TOP);
						pnlParam.setPreferredSize(new java.awt.Dimension(838,
								195));
					}
					{
						pnlTool = new JPanel();
						pnlTool.setLayout(null);
						splTool.add(pnlTool, SSplitPane.BOTTOM);
						{
							btnPost = new SButton("设  置", ImageContext.Post);
							pnlTool.add(btnPost);
							btnPost.setBounds(60 + 236, 25, 100, 25);
							btnPost.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									btnPost();
								}
							});
						}

						{
							btnExit = new SButton("退  出", ImageContext.Exit);
							pnlTool.add(btnExit);
							btnExit.setBounds(60 + 350, 25, 100, 25);
							btnExit.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									btnExit();
								}
							});
						}

					}

				}
			}
		} catch (Exception e) {
			UtilLog.logError("系统参数对话框初始化界面错误:", e);
		} finally {
		}
	}

	// 保存
	private void btnPost() {
		try {
			if (tbl.getSelectedRow() < 0) {
				ShowMsg.showMsg("请选择需要修改的记录！");
				return;
			}
			systemParams.setModule(txtModule.getText());
			systemParams.setParamName(txtParamName.getText());
			systemParams.setParamValue(txtParamValue.getText());
			systemParams.setMemo(txtMemo.getText());

			if (SystemParamsDao.getInstance().modSystemParams(systemParams)) {
				ShowMsg.showMsg("修改成功");
			}
			String module = "";
			String paramName = "";
			if (cmbModule.getSelectedIndex() >= 0)
				module = cmbModule.getSelectedItem().toString();
			if (cmbParamName.getSelectedIndex() >= 0)
				paramName = cmbParamName.getSelectedItem().toString();
			tbl = getTable(module, paramName);
			sclMain.setViewportView(tbl);
		} catch (Exception e) {
			UtilLog.logError("系统参数对话框保存错误:", e);
		} finally {
		}
	}

	// 退出
	private void btnExit() {
		try {
			this.dispose();
		} catch (Exception e) {
			UtilLog.logError("系统参数对话框退出错误:", e);
		} finally {
		}
	}

	// 查询
	private void btnQuery() {
		try {
			String module = "";
			String paramName = "";
			if (cmbModule.getSelectedIndex() >= 0)
				module = cmbModule.getSelectedItem().toString();
			if (cmbParamName.getSelectedIndex() >= 0)
				paramName = cmbParamName.getSelectedItem().toString();
			tbl = getTable(module, paramName);
			sclMain.setViewportView(tbl);
		} catch (Exception e) {
			UtilLog.logError("系统参数对话框过滤错误:", e);
		} finally {
		}
	}
}
