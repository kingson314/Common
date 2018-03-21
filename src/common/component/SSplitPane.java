package common.component;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JSplitPane;

public class SSplitPane extends JSplitPane {
	private static final long serialVersionUID = -151180503424927655L;

	// SSplitPane.VERTICAL_SPLIT =0;HORIZONTAL_SPLIT=1
	public SSplitPane(int direction, final double dividerLocation, boolean TouchExpandable) {
		super();
		this.setBorder(SBorder.getTitledBorder());
		this.setOneTouchExpandable(TouchExpandable);
		this.setOrientation(direction);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setDividerLocation(dividerLocation);
			}
		});
	}

	public SSplitPane(int direction, final int dividerLocation, boolean TouchExpandable) {
		super();
		this.setBorder(SBorder.getTitledBorder());
		this.setOneTouchExpandable(TouchExpandable);
		this.setOrientation(direction);
		this.setDividerLocation(dividerLocation);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setDividerLocation(dividerLocation);
			}
		});
	}

	public void removeByName(String name) {
		for (int i = 0; i < this.getComponentCount(); i++) {
			if (name.equals(this.getComponent(i).getName())) {
				this.remove(i);
			}
		}
	}
}
