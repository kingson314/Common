package module.encrypt;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import common.component.SButton;
import common.component.SDialog;
import common.component.SLabel;
import common.component.STextField;
import common.util.log.UtilLog;
import common.util.security.UtilCrypt;
import common.util.string.UtilString;
import consts.ImageContext;

/**
 * 加密工具
 * 
 * @author fgq 20120816
 * 
 */
public class EncryptAESDialog extends SDialog {
	private static final long serialVersionUID = 1L;
	private SLabel lInput;
	private SButton btnCopy;
	private SButton btnEncrypt;
	private STextField txtOutput;
	private STextField txtInput;
	private SLabel lOutput;

	// 构造
	public EncryptAESDialog() {
		initGUI();
	}

	// 初始化界面
	private void initGUI() {
		try {
			this.setSize(475, 162);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.EncryptAES));
			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
			this.setLocation(w, h);
			this.setTitle("加密工具");
			this.setModal(true);
			this.setLayout(null);
			{
				lInput = new SLabel("\u8f93\u5165");
				getContentPane().add(lInput);
				lInput.setBounds(47, 40, 35, 14);
				lInput.setSize(30, 14);
			}
			{
				lOutput = new SLabel("\u8f93\u51fa");
				getContentPane().add(lOutput);
				lOutput.setBounds(47, 77, 37, 14);
				lOutput.setSize(30, 14);
			}
			{
				txtInput = new STextField();
				getContentPane().add(txtInput);
				txtInput.setBounds(80, 33, 250, 21);
			}
			{
				txtOutput = new STextField();
				txtOutput.setEditable(false);
				getContentPane().add(txtOutput);
				txtOutput.setBounds(80, 70, 250, 21);
			}
			{
				btnEncrypt = new SButton("\u52a0\u5bc6", ImageContext.TxtEncryptAES);
				getContentPane().add(btnEncrypt);
				btnEncrypt.setBounds(333, 31, 90, 23);
				btnEncrypt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if (!UtilString.isNil(txtInput.getText()).equals("")) {
							txtOutput.setText(UtilCrypt.getInstance().encryptAES(txtInput.getText(), UtilCrypt.key));
						}
					}
				});
			}
			{
				btnCopy = new SButton("\u590d\u5236", ImageContext.Copy);
				getContentPane().add(btnCopy);
				btnCopy.setBounds(333, 68, 90, 23);
				btnCopy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
						Transferable tf = new StringSelection(txtOutput.getText());
						cb.setContents(tf, null);
					}
				});
			}
		} catch (Exception e) {
			UtilLog.logError("加密工具始化界面错误:", e);
		}
	}

}
