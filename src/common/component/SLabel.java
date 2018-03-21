package common.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import consts.Const;


public class SLabel extends JLabel {
	private static final long serialVersionUID = 3652273793239775794L;

	public SLabel(String text) {
		super(text, SwingConstants.LEFT);
		this.setFont(Const.tfont);
	}

	public SLabel(String text, String imagePath) {
		super(text, SwingConstants.LEFT);
		this.setFont(Const.tfont);
		this.setIcon(new ImageIcon(imagePath));
		this.setHorizontalAlignment(0);
	}

	public SLabel(String text, int h) {
		super(text);
		// this.setFont(Const.tfont);
		this.setHorizontalAlignment(h);
		this.setHorizontalTextPosition(h);
	}
}
