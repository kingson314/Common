package common.component;

import java.awt.AWTEvent;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;

public class SDialog extends JDialog implements AWTEventListener {
	private static final long serialVersionUID = 5936959397305062870L;

	public SDialog() {
		super();
		// 只关心键盘事件
		Toolkit.getDefaultToolkit().addAWTEventListener(this,
				AWTEvent.KEY_EVENT_MASK);
	}

	public SDialog(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		// 只关心键盘事件
		Toolkit.getDefaultToolkit().addAWTEventListener(this,
				AWTEvent.KEY_EVENT_MASK);
	}

	public SDialog(Frame frame) {
		super(frame);
		// 只关心键盘事件
		Toolkit.getDefaultToolkit().addAWTEventListener(this,
				AWTEvent.KEY_EVENT_MASK);
	}

	public void eventDispatched(AWTEvent event) {
		if (event.getClass() == KeyEvent.class) {
			// 记录键盘的输入时间
			KeyEvent keyBoardKey = (KeyEvent) event;
			if (keyBoardKey.getID() == KeyEvent.KEY_PRESSED) {
				if (keyBoardKey.getKeyCode() == 27) {// Esc
					if (this.isActive())
						this.dispose();
				}
			}
		}
	}

}
