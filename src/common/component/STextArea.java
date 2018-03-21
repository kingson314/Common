package common.component;

import javax.swing.JTextArea;

import consts.Const;

public class STextArea extends JTextArea {
    private static final long serialVersionUID = 3652273793239775794L;

    public STextArea() {
	super("");
	this.setFont(Const.tfont);
	this.setLineWrap(true);
	this.setDoubleBuffered(true);
	this.setWrapStyleWord(true);
	this.getDocument().addUndoableEditListener(DoManager.getInstance().getUndoManager());
    }

    public STextArea(String content) {
	super(content);
	this.setFont(Const.tfont);
	this.setLineWrap(true);
	this.setDoubleBuffered(true);
	this.setWrapStyleWord(true);
	this.getDocument().addUndoableEditListener(DoManager.getInstance().getUndoManager());
    }

}
