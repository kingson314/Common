package module.ftp;

import it.sauronsoftware.ftp4j.FTPClient;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Properties;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import common.component.DialogTableModel;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.STextField;
import common.component.ShowMsg;
import common.util.log.UtilLog;
import common.util.security.UtilCrypt;
import common.util.string.UtilString;
import consts.Const;
import consts.ImageContext;

public class FtpDialog extends DialogTableModel {

	private static final long serialVersionUID = 1L;
	private SLabel lFtpID;
	private SLabel lFtpIP;
	private SLabel lFtpUser;
	private SLabel lFtpFoder;
	private SLabel lFtpName;
	private SLabel lFtpPor;
	private SLabel lFtpPsw;
	private SLabel lFtpInfo;
	private STextField txtFtpID;
	private STextField txtFtpIP;
	private STextField txtFtpUser;
	private STextField txtFtpFoder;
	private STextField txtFtpName;
	private STextField txtFtpPor;
	private JPasswordField txtFtpPsw;
	private STextField txtFtpInfo;
	private JPanel pnlParam;
	private FtpSite ftpSite;
	private int Addmod;
	private SLabel lFtpType;
	private SComboBox cmbFtpType;

	// public static void main(String[] args) {
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// FtpDialog inst = new FtpDialog();
	// inst.setVisible(true);
	// }
	// });
	// }
	// 构造
	public FtpDialog() {
		super(1);
		try {
			ftpSite = new FtpSite();
			super.setTitle("FTP信息");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.Ftp));
			super.jTable = getTable();

			super.ini();
			textEnabled(false);
		} catch (Exception e) {
			UtilLog.logError("FTP信息对话框构造错误:", e);
		} finally {
		}
	}

	// 获取FTP信息表格
	private JTable getTable() {
		final JTable table = new FtpTable().getJtable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				UtilCrypt crypt = UtilCrypt.getInstance();
				ftpSite.setFtpId(Long.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString()));
				ftpSite.setFtpName(table.getValueAt(table.getSelectedRow(), 1).toString());
				ftpSite.setFtpType(table.getValueAt(table.getSelectedRow(), 2) == null ? "FTP" : table.getValueAt(table.getSelectedRow(), 2).toString());
				ftpSite.setFtpIp(table.getValueAt(table.getSelectedRow(), 3).toString());
				ftpSite.setFtpPort(table.getValueAt(table.getSelectedRow(), 4).toString());
				ftpSite.setFtpUser(table.getValueAt(table.getSelectedRow(), 5).toString());
				ftpSite.setFtpPassword(crypt.decryptAES(table.getValueAt(table.getSelectedRow(), 6).toString(), UtilCrypt.key));
				ftpSite.setFtpFolder(table.getValueAt(table.getSelectedRow(), 7).toString());
				ftpSite.setFtpInfo(UtilString.isNil(table.getValueAt(table.getSelectedRow(), 8)));

				txtFtpID.setText(String.valueOf(ftpSite.getFtpId()));
				txtFtpName.setText(ftpSite.getFtpName());
				txtFtpIP.setText(String.valueOf(ftpSite.getFtpIp()));
				txtFtpPor.setText(ftpSite.getFtpPort());
				txtFtpUser.setText(ftpSite.getFtpUser());
				txtFtpPsw.setText(ftpSite.getFtpPassword());
				txtFtpFoder.setText(ftpSite.getFtpFolder());
				txtFtpInfo.setText(ftpSite.getFtpInfo());
				if (ftpSite.getFtpType() == null)
					cmbFtpType.setSelectedIndex(0);
				else if (ftpSite.getFtpType().equals("") || ftpSite.getFtpType().equals("FTP"))
					cmbFtpType.setSelectedIndex(0);
				else if (ftpSite.getFtpType().equals("SFTP"))
					cmbFtpType.setSelectedIndex(1);
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
		} catch (Exception e) {
			UtilLog.logError("FTP信息对话框新增错误:", e);
		} finally {
		}
	}

	// 修改
	@Override
	protected boolean mod() {
		boolean rs = false;
		try {
			if (txtFtpID.getText().length() < 1) {
				ShowMsg.showMsg("请选择需要修改的记录！");
				return rs;
			}
			if (super.mod()) {
				textEnabled(true);
				Addmod = 1;
				txtFtpID.setEditable(false);
				rs = true;
			}
		} catch (Exception e) {
			UtilLog.logError("FTP信息对话框修改错误:", e);
			return false;
		} finally {
		}
		return rs;

	}

	// 测试
	@Override
	protected void test() {
		try {
			if (txtFtpIP.getText().length() < 1) {
				ShowMsg.showMsg("请选择测试的FTP！");
				return;
			}
			super.test();
			UtilCrypt crypt = UtilCrypt.getInstance();
			ftpSite.setFtpId(Long.valueOf(txtFtpID.getText()));
			ftpSite.setFtpName(txtFtpName.getText());
			ftpSite.setFtpIp(txtFtpIP.getText());
			ftpSite.setFtpPort(txtFtpPor.getText());
			ftpSite.setFtpUser(txtFtpUser.getText());
			ftpSite.setFtpPassword(crypt.encryptAES(String.valueOf(txtFtpPsw.getPassword()), UtilCrypt.key));
			// ftpSite.setFtpFolder(tFtpFoder.getText());
			ftpSite.setFtpFolder("/");// 现在默认为根路径
			ftpSite.setFtpInfo(txtFtpInfo.getText());
			ftpSite.setFtpType(cmbFtpType.getSelectedItem().toString());

			if (ftpSite.getFtpType().equals("SFTP")) {
				JSch jsch = new JSch();
				jsch.getSession(ftpSite.getFtpUser(), ftpSite.getFtpIp(), Integer.parseInt(ftpSite.getFtpPort()));
				Session sshSession = jsch.getSession(ftpSite.getFtpUser(), ftpSite.getFtpIp(), Integer.parseInt(ftpSite.getFtpPort()));
				sshSession.setPassword(crypt.decryptAES(ftpSite.getFtpPassword(), UtilCrypt.key));
				Properties sshConfig = new Properties();
				sshConfig.put("StrictHostKeyChecking", "no");
				sshSession.setConfig(sshConfig);
				sshSession.connect();
				Channel channel = sshSession.openChannel("sftp");
				channel.connect();

			} else {
				FTPClient client = new FTPClient();
				client.connect(ftpSite.getFtpIp(), Integer.parseInt(ftpSite.getFtpPort()));
				if (!ftpSite.getFtpUser().equals("anonymous"))
					client.login(ftpSite.getFtpUser(), crypt.decryptAES(ftpSite.getFtpPassword(), UtilCrypt.key));
				else
					client.login("anonymous", "no@password.com");
				client.disconnect(true);
			}
			ShowMsg.showMsg("              连接成功！");

		} catch (Exception e) {
			ShowMsg.showMsg(" FTP标志为:" + ftpSite.getFtpId() + "  连接失败！");
			UtilLog.logError(" FTP标志为:" + ftpSite.getFtpId() + "  连接失败:", e);

		} finally {
			super.setTestEnable();
		}
	}

	// 取消
	@Override
	protected void cancle() {
		try {
			textEnabled(false);
			super.cancle();
			textClear();
		} catch (Exception e) {
			UtilLog.logError("FTP信息对话框取消错误:", e);
		} finally {
		}
	}

	// 删除
	@Override
	protected boolean del() {
		try {
			if (txtFtpID.getText().length() < 1) {
				ShowMsg.showMsg("请选择需要删除的记录！");
				return false;
			}
			textEnabled(false);
			if (super.del() == true) {
				FtpDao.getInstance().delFtpSite(ftpSite);
				refreshTable();
				textClear();
			}
		} catch (Exception e) {
			UtilLog.logError("FTP信息对话框删除错误:", e);
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
			UtilLog.logError("FTP信息对话框退出错误:", e);
		} finally {
		}
	}

	// 保存
	@Override
	protected void post() {
		try {
			if (txtFtpID.getText().length() < 1) {
				ShowMsg.showMsg("请输入FTP标志！");
				return;
			}
			UtilCrypt crypt = UtilCrypt.getInstance();
			try {
				ftpSite.setFtpId(Long.valueOf(txtFtpID.getText()));
			} catch (Exception e) {
				ShowMsg.showError("FTP标志为数字类型:" + e);
				return;
			}
			ftpSite.setFtpName(txtFtpName.getText());
			ftpSite.setFtpIp(txtFtpIP.getText());
			ftpSite.setFtpPort(txtFtpPor.getText());
			ftpSite.setFtpUser(txtFtpUser.getText());
			ftpSite.setFtpPassword(crypt.encryptAES(String.valueOf(txtFtpPsw.getPassword()), UtilCrypt.key));
			// ftpSite.setFtpFolder(tFtpFoder.getText());
			ftpSite.setFtpFolder("/");// 现在默认为根路径
			ftpSite.setFtpInfo(txtFtpInfo.getText());
			ftpSite.setFtpType(cmbFtpType.getSelectedItem().toString());
			if (Addmod == 0) {
				if (FtpDao.getInstance().getFtpSiteID(ftpSite.getFtpId()) == true) {
					ShowMsg.showWarn("已存在该FtpID！");
					return;
				}
				FtpDao.getInstance().addFtpSite(ftpSite);
			} else if (Addmod == 1) {
				FtpDao.getInstance().modFtpSite(ftpSite);
			}
			textEnabled(false);
			super.post();
			refreshTable();
		} catch (Exception e) {
			ShowMsg.showError("FTP信息对话框保存错误:" + e);
		} finally {
		}
	}

	// 获取参数面板
	@Override
	protected JPanel GetParamPanel() {
		pnlParam = new JPanel();
		try {
			pnlParam.setLayout(null);
			lFtpID = new SLabel("FTP标志");
			lFtpID.setBounds(50, 20, 60, 20);
			pnlParam.add(lFtpID);
			lFtpIP = new SLabel("Ip地址");
			lFtpIP.setBounds(50, 50, 60, 20);
			pnlParam.add(lFtpIP);
			lFtpUser = new SLabel("用户名");
			lFtpUser.setBounds(50, 80, 60, 20);
			pnlParam.add(lFtpUser);
			lFtpFoder = new SLabel("文件路径");
			lFtpFoder.setBounds(50, 110, 60, 20);
			lFtpFoder.setVisible(false);
			pnlParam.add(lFtpFoder);

			txtFtpID = new STextField("");
			txtFtpID.setBounds(110, 20, 150, 20);
			pnlParam.add(txtFtpID);
			txtFtpIP = new STextField("");
			txtFtpIP.setBounds(110, 50, 150, 20);
			pnlParam.add(txtFtpIP);
			txtFtpUser = new STextField("");
			txtFtpUser.setBounds(110, 80, 150, 20);
			pnlParam.add(txtFtpUser);
			txtFtpFoder = new STextField("");
			txtFtpFoder.setBounds(110, 110, 150, 20);
			txtFtpFoder.setVisible(false);
			pnlParam.add(txtFtpFoder);

			pnlParam.setLayout(null);
			lFtpName = new SLabel("FTP名称");
			lFtpName.setBounds(350, 20, 60, 20);
			pnlParam.add(lFtpName);
			lFtpPor = new SLabel("端口");
			lFtpPor.setBounds(350, 50, 60, 20);
			pnlParam.add(lFtpPor);
			lFtpPsw = new SLabel("密码");
			lFtpPsw.setBounds(350, 80, 60, 20);
			pnlParam.add(lFtpPsw);

			lFtpInfo = new SLabel("FTP说明");
			lFtpInfo.setBounds(350, 110, 60, 20);// (350, 110, 60, 20);
			pnlParam.add(lFtpInfo);

			lFtpType = new SLabel("FTP类型");
			lFtpType.setBounds(50, 110, 60, 20);// (350, 110, 60, 20);
			pnlParam.add(lFtpType);

			pnlParam.setLayout(null);
			txtFtpName = new STextField("");
			txtFtpName.setBounds(410, 20, 150, 20);
			pnlParam.add(txtFtpName);
			txtFtpPor = new STextField("");
			txtFtpPor.setBounds(410, 50, 150, 20);
			pnlParam.add(txtFtpPor);
			txtFtpPsw = new JPasswordField("");
			txtFtpPsw.setBounds(410, 80, 150, 20);
			pnlParam.add(txtFtpPsw);
			txtFtpInfo = new STextField("");
			txtFtpInfo.setBounds(410, 110, 150, 20);// (410, 110, 150, 20);
			pnlParam.add(txtFtpInfo);

			cmbFtpType = new SComboBox(Const.FtpType, 8);
			cmbFtpType.setBounds(110, 110, 150, 20);// (410, 110, 150, 20);
			pnlParam.add(cmbFtpType);

		} catch (Exception e) {
			UtilLog.logError("FTP信息对话框初始化界面获取jPanelParam错误:", e);
		} finally {
		}
		return pnlParam;
	}

	// 状态控制
	private void textEnabled(boolean flag) {
		txtFtpFoder.setEditable(flag);
		txtFtpID.setEditable(flag);
		txtFtpInfo.setEditable(flag);
		txtFtpIP.setEditable(flag);
		txtFtpName.setEditable(flag);
		txtFtpPor.setEditable(flag);
		txtFtpPsw.setEditable(flag);
		txtFtpUser.setEditable(flag);
		cmbFtpType.setEnabled(flag);
	}

	// 清空txt
	private void textClear() {
		txtFtpFoder.setText("");
		txtFtpID.setText("");
		txtFtpInfo.setText("");
		txtFtpIP.setText("");
		txtFtpName.setText("");
		txtFtpPor.setText("");
		txtFtpPsw.setText("");
		txtFtpUser.setText("");
	}
}
