package common.component;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;

import javax.swing.JTable;

class CheckButtonEditor extends DefaultCellEditor implements ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SCheckBox button;

	public CheckButtonEditor(SCheckBox checkBox) {
		super(checkBox);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value == null)
			return null;
		button = (SCheckBox) value;
		button.addItemListener(this);
		return (Component) value;
	}

	public Object getCellEditorValue() {
		button.removeItemListener(this);
		return button;
	}

	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}
}
