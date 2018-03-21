package common.component;

import javax.swing.JRadioButton;

import consts.Const;

public class SRadioButton extends JRadioButton {
	private static final long serialVersionUID = -5468917967289911129L;

	public SRadioButton(String text) {
		super(text);
		this.setFont(Const.tfont);
	}
}
