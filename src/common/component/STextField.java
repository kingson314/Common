package common.component;

import javax.swing.JTextField;

import consts.Const;

public class STextField extends JTextField {
	private static final long serialVersionUID = 3652273793239775794L;

	public STextField() {
		super("");
		this.setFont(Const.tfont);
		this.getDocument().addUndoableEditListener(
				DoManager.getInstance().getUndoManager());
	}

	public STextField(String text) {
		super(text);
		this.setFont(Const.tfont);
		this.getDocument().addUndoableEditListener(
				DoManager.getInstance().getUndoManager());
	}

	 
}
