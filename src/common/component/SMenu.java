package common.component;

import javax.swing.ImageIcon;
import javax.swing.JMenu;

import consts.Const;


public class SMenu extends JMenu {
	private static final long serialVersionUID = 3652273793239775794L;

	public SMenu(String text, int mnemonic) {
		super(text);
		this.setMnemonic(mnemonic);
		this.setFont(Const.tfont);
	}

	public SMenu(String text, int mnemonic, String imagePath) {
		super(text);
		// 快捷键
		this.setMnemonic(mnemonic);
		this.setFont(Const.tfont);
		this.setIcon(new ImageIcon(imagePath));
	}
}
