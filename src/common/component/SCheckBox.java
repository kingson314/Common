package common.component;

import javax.swing.JCheckBox;

import consts.Const;


public class SCheckBox extends JCheckBox {
	private static final long serialVersionUID = 2580309542529454248L;

	public SCheckBox(String text) {
		super(text);
		this.setFont(Const.tfont);
	}
}
