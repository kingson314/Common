package common.component;

import java.awt.Component;

import javax.swing.JScrollPane;

public class SScrollPane extends JScrollPane {
	private static final long serialVersionUID = -3495272396429116446L;

	public SScrollPane(Component view) {
		super();
		this.setViewportView(view);
	}
}
