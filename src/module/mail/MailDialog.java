package module.mail;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;


import common.component.DialogTableModel;
import common.component.SLabel;
import common.component.STextField;
import common.component.ShowMsg;
import common.util.log.UtilLog;
import common.util.security.UtilCrypt;
import consts.ImageContext;

public class MailDialog extends DialogTableModel {
	private static final long serialVersionUID = 1L;
	private SLabel lUserName;
	private SLabel lServer;
	private SLabel lAdrress;
	private SLabel lPsw;
	private STextField txtUserName;
	private STextField txtServer;
	private STextField txtAddress;
	private JPasswordField txtPsw;
	private JPanel pnlParam;
	private MailSender mailSender;
	private int Addmod;

	// public static void main(String[] args) {
	//
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// MailDialog inst = new MailDialog();
	// inst.setVisible(true);
	// }
	// });
	// }

	// 构造
	public MailDialog() {
		super(0);
		try {
			mailSender = new MailSender();
			super.setTitle("邮箱信息");
			this.setSize(626, 375);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.Mail));
			super.jTable = getTable();

			super.ini();
			textEnabled(false);
		} catch (Exception e) {
			UtilLog.logError("邮箱信息对话框构造错误:", e);
		} finally {
		}
	}

	// 获取邮箱信息表格
	private JTable getTable() {
		final JTable table = new MailTable().getJtable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {

				UtilCrypt crypt = UtilCrypt.getInstance();
				mailSender = new MailSender();

				mailSender.setMailSendAdreess(table.getValueAt(table.getSelectedRow(), 0).toString());
				mailSender.setMailSendUserName(table.getValueAt(table.getSelectedRow(), 1).toString());
				mailSender.setMailSendPassword(crypt.decryptAES(table.getValueAt(table.getSelectedRow(), 2).toString(), UtilCrypt.key));
				mailSender.setMailServer(table.getValueAt(table.getSelectedRow(), 3).toString());
				txtAddress.setText(mailSender.getMailSendAdreess());
				txtUserName.setText(String.valueOf(mailSender.getMailSendUserName()));
				txtPsw.setText(mailSender.getMailSendPassword());
				txtServer.setText(mailSender.getMailServer());
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
			UtilLog.logError("邮箱信息对话框新增错误:", e);
		} finally {
		}
	}

	// 修改
	@Override
	protected boolean mod() {

		boolean rs = false;
		try {
			if (txtAddress.getText().length() < 1) {
				ShowMsg.showMsg("请选择需要修改的记录！");
				return rs;
			}
			if (super.mod()) {
				textEnabled(true);
				Addmod = 1;
				txtAddress.setEditable(false);
				rs = true;

			}
		} catch (Exception e) {
			UtilLog.logError("邮箱信息对话框修改错误:", e);
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
			UtilLog.logError("邮箱信息对话框取消错误:", e);
		} finally {
		}
	}

	// 删除
	@Override
	protected boolean del() {
		try {
			if (txtAddress.getText().length() < 1) {
				ShowMsg.showMsg("请选择需要删除的记录！");
				return false;
			}
			textEnabled(false);
			if (super.del() == true) {
				MailDao.getInstance().delmail(mailSender);
				refreshTable();
				textClear();
			}
		} catch (Exception e) {
			UtilLog.logError("邮箱信息对话框删除错误:", e);
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
			UtilLog.logError("邮箱信息对话框退出错误:", e);
		} finally {
		}
	}

	// 保存
	@Override
	protected void post() {
		try {
			if (txtAddress.getText().length() < 1) {
				ShowMsg.showMsg("请输入发送邮箱地址！");
				return;
			}
			UtilCrypt crypt = UtilCrypt.getInstance();
			mailSender.setMailSendAdreess(txtAddress.getText());
			mailSender.setMailSendUserName(txtUserName.getText());
			mailSender.setMailSendPassword(crypt.encryptAES(String.valueOf(txtPsw.getPassword()), UtilCrypt.key));
			mailSender.setMailServer(txtServer.getText());
			mailSender.setMailPort(String.valueOf(25));
			mailSender.setMailValidate(String.valueOf("true"));

			if (Addmod == 0) {
				if (MailDao.getInstance().mailSenderIsExist(mailSender.getMailSendAdreess()) == true) {
					ShowMsg.showWarn("已存在该邮件标志！");
					return;
				}
				MailDao.getInstance().addMail(mailSender);
			} else if (Addmod == 1) {
				MailDao.getInstance().modmail(mailSender);
			}
			textEnabled(false);
			super.post();
			refreshTable();
		} catch (Exception e) {
			UtilLog.logError("邮箱信息对话框保存错误:", e);
		} finally {
		}
	}

	// 获取参数面板
	@Override
	protected JPanel GetParamPanel() {
		pnlParam = new JPanel();
		try {
			pnlParam.setLayout(null);

			lUserName = new SLabel("发送邮箱名称");
			lUserName.setBounds(350, 20, 100, 20);
			pnlParam.add(lUserName);
			lServer = new SLabel("邮件服务器地址");
			lServer.setBounds(50, 50, 100, 20);
			pnlParam.add(lServer);

			txtUserName = new STextField("");
			txtUserName.setBounds(450, 20, 150, 20);
			pnlParam.add(txtUserName);
			txtServer = new STextField("");
			txtServer.setBounds(150, 50, 150, 20);
			pnlParam.add(txtServer);

			pnlParam.setLayout(null);
			lAdrress = new SLabel("发送邮箱地址");
			lAdrress.setBounds(50, 20, 100, 20);
			pnlParam.add(lAdrress);
			lPsw = new SLabel("发送邮箱密码");
			lPsw.setBounds(350, 50, 100, 20);
			pnlParam.add(lPsw);

			pnlParam.setLayout(null);
			txtAddress = new STextField("");
			txtAddress.setBounds(150, 20, 150, 20);
			pnlParam.add(txtAddress);
			txtPsw = new JPasswordField("");
			txtPsw.setBounds(450, 50, 150, 20);
			pnlParam.add(txtPsw);
		} catch (Exception e) {
			UtilLog.logError("邮箱信息对话框初始化界面获取jPanelParam错误:", e);
		} finally {
		}
		return pnlParam;
	}

	// 状态控制
	private void textEnabled(boolean flag) {
		txtUserName.setEditable(flag);
		txtAddress.setEditable(flag);
		txtPsw.setEditable(flag);
		txtServer.setEditable(flag);
	}

	// 清空txt
	private void textClear() {
		txtUserName.setText("");
		txtAddress.setText("");
		txtPsw.setText("");
		txtServer.setText("");
	}
}
