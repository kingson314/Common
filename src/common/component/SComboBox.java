package common.component;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import consts.Const;


@SuppressWarnings("rawtypes")
public class SComboBox extends JComboBox {
	private static final long serialVersionUID = -8221080195850880297L;

	@SuppressWarnings("unchecked")
	public SComboBox(Object[] items, int rowCount) {
		super();
		ComboBoxModel comboBoxModel = new DefaultComboBoxModel(items);
		this.setModel(comboBoxModel);
		this.setFont(Const.tfont);
		this.setMaximumRowCount(20);
		this.setAutoscrolls(true);
		this.setDoubleBuffered(true);
	}

	@SuppressWarnings("unchecked")
	public SComboBox(Object[] items) {
		super();
		ComboBoxModel comboBoxModel = new DefaultComboBoxModel(items);
		this.setModel(comboBoxModel);
		this.setFont(Const.tfont);
		this.setAutoscrolls(true);
		this.setDoubleBuffered(true);
	}

	@SuppressWarnings("unchecked")
	public SComboBox(Vector<?> vector) {
		super(vector);
		this.setFont(Const.tfont);
		this.setAutoscrolls(true);
		this.setDoubleBuffered(true);
	}

}
