package module.waiting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JWindow;

import common.component.SLabel;


public class DialogWait extends JWindow {
	private static String msgData = "数据加载中,请稍后... ...";
	private static String msgProgram = "程序运行中,请稍后... ...";
	private static final long serialVersionUID = -235596413522543125L;

	private JWindow win;
	private static DialogWait dialogWait;

	public static DialogWait getInstance() {
		if (dialogWait == null)
			dialogWait = new DialogWait();
		return dialogWait;
	}

	private DialogWait() {
	}

	public void show(int type) {
		win = new JWindow();
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
		win.setLocation(w, h);
		win.setSize(200, 50);

		String msg = "";
		if (type == 0)
			msg = msgData;
		else if (type == 1)
			msg = msgProgram;
		SLabel lmsg = new SLabel(msg);
		lmsg.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 13));
		lmsg.setForeground(new Color(255, 175, 175));
		// lmsg.setBackground(new Color(255, 175, 175));
		win.add(lmsg);
		win.setAlwaysOnTop(true);
		win.setVisible(true);
		win.repaint();
	}

	@SuppressWarnings("deprecation")
	public void hide() {
		win.hide();
	}
}
