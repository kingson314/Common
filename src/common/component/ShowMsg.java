package common.component;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * 提示信息框
 * 
 * @author fgq 20120815
 * 
 */
public class ShowMsg {
	private static Font tfont = new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12);
	static {
		UIManager.put("Button.font", tfont);
		UIManager.put("Component.font", tfont);
		UIManager.put("Label.font", tfont);
		// UIManager.put("Label.foreground", Color.black);
	}

	private static JLabel getMsg(String text) {
		JLabel lmsg = new JLabel(text.trim() + "        ",
				SwingConstants.CENTER);
		lmsg.setFont(tfont);
		// lmsg.setHorizontalAlignment(0);
		return lmsg;
	}

	// 信息提示
	public static void showMsg(Component com, String titile, String msg) {
		JOptionPane.showMessageDialog(com, getMsg(msg), titile,
				JOptionPane.INFORMATION_MESSAGE);
	}

	// 警告提示
	public static void showWarn(Component com, String titile, String msg) {
		JOptionPane.showMessageDialog(com, getMsg(msg), titile,
				JOptionPane.WARNING_MESSAGE);
	}

	// 错误提示
	public static void showError(Component com, String titile, String msg) {
		JOptionPane.showMessageDialog(com, getMsg(msg), titile,
				JOptionPane.ERROR_MESSAGE);
	}

	// 确认提示
	public static int showConfig(Component com, String titile, String msg) {
		return JOptionPane.showConfirmDialog(com, getMsg(msg), titile,
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	// 信息提示
	public static void showMsg(Component com, String msg) {
		JOptionPane.showMessageDialog(com, getMsg(msg), "提示",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// 警告提示
	public static void showWarn(Component com, String msg) {
		JOptionPane.showMessageDialog(com, getMsg(msg), "警告",
				JOptionPane.WARNING_MESSAGE);
	}

	// 错误提示
	public static void showError(Component com, String msg) {
		JOptionPane.showMessageDialog(com, getMsg(msg), "错误",
				JOptionPane.ERROR_MESSAGE);
	}

	// 确认提示
	public static int showConfig(Component com, String msg) {
		return JOptionPane.showConfirmDialog(com, getMsg(msg), "确认",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	// 信息提示
	public static void showMsg(String msg) {
		JOptionPane.showMessageDialog(null, getMsg(msg), "提示",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// 警告提示
	public static void showWarn(String msg) {
		JOptionPane.showMessageDialog(null, getMsg(msg), "警告",
				JOptionPane.WARNING_MESSAGE);
	}

	// 错误提示
	public static void showError(String msg) {
		JOptionPane.showMessageDialog(null, getMsg(msg), "错误",
				JOptionPane.ERROR_MESSAGE);
	}

	// 确认提示
	public static int showConfig(String msg) {
		return JOptionPane.showConfirmDialog(null, getMsg(msg), "确认",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	public static void showText(String msg){
			STextArea textArea =new STextArea(msg);
			textArea.setSize(200, 120);
			JOptionPane.showMessageDialog(null, new JScrollPane(textArea));
	}
}
