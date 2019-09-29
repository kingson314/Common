package common.component;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;

import common.util.conver.UtilConver;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import common.util.swing.UtilComponent;
import consts.ImageContext;


public class SMultiCombobox extends JPanel {
	private static final long serialVersionUID = 3210207356439928422L;
	private JTable jtable;
	private STextField txtOut;
	private SButton btnOk;
	private SButton btnExit;
	private SDialog dialog;
	private SButton btnSelect;
	private String keyValue;
	private String textValue;
	private int textIndex;
	private int keyIndex;
	private String[] columnName;
	private Vector<?> tableValue;
	private String title;

	// 根据map参数构造table
	public SMultiCombobox(int width, int height, String title, String[] columnName, Vector<?> tableValue, int textIndex, int keyIndex) {
		this.setLayout(null);
		this.setSize(width, height);
		this.title = title;
		this.textIndex = textIndex;
		this.keyIndex = keyIndex;
		this.columnName = columnName;
		this.tableValue = tableValue;
		txtOut = new STextField();
		txtOut.setBounds(0, 0, width - 22, height);
		this.add(txtOut);
		btnSelect = new SButton("..");
		btnSelect.setBounds(width - 22, 0, 22, height - 1);
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog();
			}

		});
		this.add(btnSelect);
	}

	private void showDialog() {
		dialog = new SDialog();
		dialog.setSize(300, 300);
		dialog.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.MultiCombobox));
		dialog.setTitle(this.title);
		dialog.setModal(true);
		SSplitPane splt = new SSplitPane(0, 225, false);
		splt.setDividerSize(1);
		SScrollPane scrl = new SScrollPane(getJtable(columnName, tableValue));
		splt.add(scrl, SSplitPane.TOP);
		JPanel pnlTool = new JPanel();
		{
			btnOk = new SButton(" 确  定", ImageContext.Ok);
			pnlTool.add(btnOk);
			btnOk.setSize(100, 25);
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ok();
				}
			});
		}
		{
			btnExit = new SButton("取  消", ImageContext.Exit);
			pnlTool.add(btnExit);
			btnExit.setSize(100, 25);
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					exit();
				}
			});
		}
		dialog.add(splt);
		int x = (int) txtOut.getLocationOnScreen().getX();
		int y = (int) txtOut.getLocationOnScreen().getY() + 20;
		dialog.setLocation(x, y);
		UtilComponent.setTbaleSelect(this.jtable, txtOut.getText(), 2);
		this.setVisible(true);
	}

	private JTable getJtable(String[] columnName, Vector<?> tableVaule) {
		try {
			Vector<String> vectorColumn = UtilConver.arrayToVector(columnName);
			int[] cellEditableColumn = new int[] { 0 };
			int[] columnWidth = new int[] { 50, 100, 100 };
			int[] columnHide = null;
			boolean isChenckHeader = true;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(vectorColumn, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
			STable table = new STable(bean);
			jtable = table.getJtable();
		} catch (Exception e) {
			UtilLog.logError("构造错误:", e);
		} finally {
		}
		return jtable;

	}

	private void ok() {
		for (int i = 0; i < this.jtable.getRowCount(); i++) {
			Boolean selected = Boolean.valueOf(this.jtable.getValueAt(i, 0).toString());
			if (selected) {
				if ("".equals(UtilString.isNil(this.keyValue))) {
					this.keyValue = this.jtable.getValueAt(i, this.keyIndex).toString();
				} else {
					this.keyValue = this.keyValue + "," + this.jtable.getValueAt(i, this.keyIndex).toString();
				}
				if ("".equals(UtilString.isNil(this.textValue))) {
					this.textValue = this.jtable.getValueAt(i, this.textIndex).toString();
				} else {
					this.textValue = this.textValue + "," + this.jtable.getValueAt(i, this.textIndex).toString();
				}
			}
			if (this.txtOut != null)
				this.txtOut.setText(this.textValue);
			exit();
		}
	}

	private void exit() {
		this.dialog.dispose();
	}

}