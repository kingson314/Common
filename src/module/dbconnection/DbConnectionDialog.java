package module.dbconnection;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;

import common.component.DialogTableModel;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.STextField;
import common.component.ShowMsg;
import common.util.jdbc.UtilJDBCManager;
import common.util.log.UtilLog;
import common.util.security.UtilCrypt;

import consts.Const;
import consts.ImageContext;

public class DbConnectionDialog extends DialogTableModel {

	private static final long serialVersionUID = 1L;
	private SLabel lDbName;
	private SLabel lDbType;
	private SLabel lDbClassName;
	private SLabel lDbPassword;
	private STextField txtDbName;
	private SComboBox cmbDbType;
	private STextField txtDbClassName;
	private STextField txtDbConn;
	private STextField txtDbUser;
	private JPasswordField tpPsw;
	private JPanel pnlParam;
	private int Addmod;
	private SLabel lUser;
	private SLabel lDbConn;

	// 构造
	public DbConnectionDialog() {
		super(1);
		try {
			super.setTitle("数据库连接配置信息");
			this.setSize(539, 213);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.DbCon));
			super.jTable = getTable();
			super.ini();
			textEnabled(false);
			// this.setVisible(true);
		} catch (Exception e) {
			UtilLog.logError("数据源配置对话框构造错误:", e);
		}
	}

	// 获取数据库连接信息表格
	private JTable getTable() {
		final JTable table = new DbConnecitionTable().getJtable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				UtilCrypt crypt = UtilCrypt.getInstance();
				txtDbName.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
				cmbDbType.setSelectedItem(table.getValueAt(table.getSelectedRow(), 1).toString());
				txtDbClassName.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
				txtDbConn.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
				txtDbUser.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
				tpPsw.setText(crypt.decryptAES(table.getValueAt(table.getSelectedRow(), 5).toString(), UtilCrypt.key));

				textEnabled(false);
			}
		});
		return table;
	}

	// 刷新表格
	private void refreshTable() {
		super.jTable = getTable();
		super.scrlTable.setViewportView(super.jTable);
	}

	// 新增
	@Override
	protected void add() {
		try {
			textEnabled(true);
			textClear();
			super.add();
			Addmod = 0;
			if (cmbDbType.getSelectedIndex() >= 0) {
				txtDbClassName.setText(Const.DbLinkDriverClass[cmbDbType.getSelectedIndex()]);
				txtDbConn.setText(Const.DbConnectionString[cmbDbType.getSelectedIndex()]);
			}
		} catch (Exception e) {
			UtilLog.logError("数据源配置对话框新增错误:", e);
		} finally {
		}
	}

	// 修改
	@Override
	protected boolean mod() {
		boolean rs = false;
		try {
			if (txtDbName.getText().length() < 1) {
				ShowMsg.showMsg("请选择需要修改的记录！");
				return rs;
			}
			if (super.mod()) {
				textEnabled(true);
				Addmod = 1;
				txtDbName.setEditable(false);
				rs = true;
			}
		} catch (Exception e) {
			UtilLog.logError("数据源配置对话框修改错误:", e);
			return false;
		} finally {
		}
		return rs;
	}

	// 取消
	@Override
	protected void cancle() {
		try {
			textEnabled(false);
			super.cancle();
			textClear();
		} catch (Exception e) {
			UtilLog.logError("数据源配置对话框取消错误:", e);
		} finally {
		}
	}

	// 删除
	@Override
	protected boolean del() {
		try {
			if (txtDbName.getText().length() < 1) {
				ShowMsg.showMsg("请选择需要删除的记录！");
				return false;
			}
			textEnabled(false);
			textEnabled(false);
			if (super.del() == true) {
				DbConnectionDao.getInstance().delConn(txtDbName.getText().trim());
				refreshTable();
				textClear();
			}
		} catch (Exception e) {
			UtilLog.logError("数据源配置对话框删除错误:", e);
			return false;
		} finally {
		}
		return true;
	}

	// 退出
	@Override
	protected void exit() {
		try {
			super.exit();
		} catch (Exception e) {
			UtilLog.logError("数据源配置对话框退出错误:", e);
		} finally {
		}
	}

	// 测试
	@Override
	protected void test() {
		try {
			if (txtDbName.getText().length() < 1) {
				ShowMsg.showMsg("请选择测试的数据源！");
				return;
			}
			super.test();
			UtilCrypt crypt = UtilCrypt.getInstance();
			DbConnection dbCon = new DbConnection();
			dbCon.setDbName("测试:" + txtDbName.getText());
			dbCon.setDbType(cmbDbType.getSelectedItem().toString());
			dbCon.setDbClassName(txtDbClassName.getText());
			dbCon.setDbCon(txtDbConn.getText());
			dbCon.setDbUser(txtDbUser.getText());
			dbCon.setDbPassword(crypt.encryptAES(String.valueOf(tpPsw.getPassword()), UtilCrypt.key));
			System.out.println("psw:" + String.valueOf(tpPsw.getPassword()));
			boolean connected = false;
			connected = UtilJDBCManager.TestConnection(dbCon);
			if (connected == true) {
				ShowMsg.showMsg("连接成功！");
			} else {
				ShowMsg.showMsg(dbCon.getDbName() + "  连接失败！");
			}
		} catch (HeadlessException e) {
			UtilLog.logError("测试数据看连接错误1:", e);
		} catch (SQLException e) {
			UtilLog.logError("测试数据看连接错误2:", e);
		} catch (Exception e) {
			UtilLog.logError("测试数据看连接错误3:", e);
		} finally {
			super.setTestEnable();
		}
	}

	// 保存
	@Override
	protected void post() {
		try {
			if (txtDbName.getText().trim().length() < 1) {
				ShowMsg.showMsg("请输入数据源名称！");
				return;
			}
			UtilCrypt crypt = UtilCrypt.getInstance();
			DbConnection dbCon = new DbConnection();
			dbCon.setDbName(txtDbName.getText());
			dbCon.setDbType(cmbDbType.getSelectedItem().toString());
			dbCon.setDbClassName(txtDbClassName.getText());
			dbCon.setDbCon(txtDbConn.getText());
			dbCon.setDbUser(txtDbUser.getText());
			dbCon.setDbPassword(crypt.encryptAES(String.valueOf(tpPsw.getPassword()), UtilCrypt.key));

			if (Addmod == 0) {
				if (DbConnectionDao.getInstance().DbConnIsExist(dbCon.getDbName()) == true) {
					ShowMsg.showWarn("已存在该数据连接配置！");
					return;
				}
				DbConnectionDao.getInstance().addDbCon(dbCon);
			} else if (Addmod == 1) {
				DbConnectionDao.getInstance().modDbcon(dbCon);
			}
			textEnabled(false);
			super.post();
			refreshTable();
		} catch (Exception e) {
			UtilLog.logError("数据源配置对话框保存错误:", e);
		} finally {
		}
	}

	// 获取参数面板
	@Override
	protected JPanel GetParamPanel() {
		pnlParam = new JPanel();
		try {
			pnlParam.setLayout(null);
			lDbName = new SLabel("数据源名称");
			lDbName.setBounds(50, 20, 100, 20);
			pnlParam.add(lDbName);
			txtDbName = new STextField("");
			txtDbName.setBounds(150, 20, 150, 20);
			pnlParam.add(txtDbName);

			lDbType = new SLabel("数据源类型");
			lDbType.setBounds(350, 20, 100, 20);
			pnlParam.add(lDbType);
			cmbDbType = new SComboBox(Const.DbLinkType, 8);
			cmbDbType.setBounds(450, 20, 150, 20);
			cmbDbType.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					txtDbClassName.setText(Const.DbLinkDriverClass[cmbDbType.getSelectedIndex()]);
					txtDbConn.setText(Const.DbConnectionString[cmbDbType.getSelectedIndex()]);
				}
			});
			pnlParam.add(cmbDbType);

			pnlParam.setLayout(null);
			lDbClassName = new SLabel("数据源驱动程序类");
			lDbClassName.setBounds(50, 50, 100, 20);
			pnlParam.add(lDbClassName);
			pnlParam.setLayout(null);
			txtDbClassName = new STextField("");
			txtDbClassName.setBounds(150, 50, 150, 20);
			pnlParam.add(txtDbClassName);

			pnlParam.setLayout(null);
			lDbConn = new SLabel("数据库连接");
			lDbConn.setBounds(350, 50, 100, 20);
			pnlParam.add(lDbConn);
			pnlParam.setLayout(null);
			txtDbConn = new STextField("");
			txtDbConn.setBounds(450, 50, 150, 20);
			pnlParam.add(txtDbConn);

			lUser = new SLabel("用户名");
			lUser.setBounds(50, 80, 100, 20);
			pnlParam.add(lUser);
			txtDbUser = new STextField("");
			txtDbUser.setBounds(150, 80, 150, 20);
			pnlParam.add(txtDbUser);

			lDbPassword = new SLabel("密码");
			lDbPassword.setBounds(350, 80, 100, 20);
			pnlParam.add(lDbPassword);
			tpPsw = new JPasswordField("");
			tpPsw.setBounds(450, 80, 150, 20);
			pnlParam.add(tpPsw);
		} catch (Exception e) {
			UtilLog.logError("数据源配置对话框初始化界面获取jPanelParam错误:", e);
		} finally {
		}
		return pnlParam;
	}

	// 状态控制
	private void textEnabled(boolean flag) {
		txtDbName.setEditable(flag);
		cmbDbType.setEnabled(flag);
		txtDbClassName.setEditable(flag);
		txtDbConn.setEditable(flag);
		txtDbUser.setEditable(flag);
		tpPsw.setEditable(flag);

	}

	// 清空txt
	private void textClear() {
		txtDbName.setText("");
		cmbDbType.setSelectedIndex(0);
		txtDbClassName.setText("");
		txtDbConn.setText("");
		txtDbUser.setText("");
		tpPsw.setText("");
	}
}
