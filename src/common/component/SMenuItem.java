package common.component;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import consts.Const;


public class SMenuItem extends JMenuItem {
	private static final long serialVersionUID = 3652273793239775794L;

	public SMenuItem(String text, int mnemonic, String imagePath) {
		super(text);
		// 快捷键
		this.setMnemonic(mnemonic);
		this.setFont(Const.tfont);
		this.setIcon(new ImageIcon(imagePath));
	}

	public SMenuItem(String text, String imagePath) {
		super(text);
		this.setFont(Const.tfont);
		this.setIcon(new ImageIcon(imagePath));
	}

	public SMenuItem(String text) {
		super(text);
		this.setFont(Const.tfont);
	}
}
