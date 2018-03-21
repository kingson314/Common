package common.component;

import java.util.Vector;

//表格模型
public class STableBean {
	// 表头
	private Vector<String> columnName;
	// 表值
	private Vector<?> tableValue;
	// 可编辑列
	private int[] cellEditableColumn;
	// 列宽
	private int[] columnWidth;
	// 隐藏列
	private int[] columnHide;
	// 是否为可选
	private boolean isChenckHeader;
	// 是否禁止拖动列
	private boolean isReorderingAllowed;
	// 是否允许改变列
	private boolean isResizingAllowed;

	public STableBean(Vector<String> columnName, Vector<?> tableValue,
			int cellEditableColumn[], int[] columnWidth, int[] columnHide,
			boolean isChenckHeader, boolean isReorderingAllowed,
			boolean isResizingAllowed) {
		this.columnName = columnName;
		this.tableValue = tableValue;
		this.cellEditableColumn = cellEditableColumn;
		this.columnWidth = columnWidth;
		this.columnHide = columnHide;
		this.isChenckHeader = isChenckHeader;
		this.isReorderingAllowed = isReorderingAllowed;
		this.isResizingAllowed = isResizingAllowed;
	}

	public boolean isChenckHeader() {
		return isChenckHeader;
	}

	public void setChenckHeader(boolean isChenckHeader) {
		this.isChenckHeader = isChenckHeader;
	}

	public boolean isReorderingAllowed() {
		return isReorderingAllowed;
	}

	public void setReorderingAllowed(boolean isReorderingAllowed) {
		this.isReorderingAllowed = isReorderingAllowed;
	}

	public Vector<String> getColumnName() {
		return columnName;
	}

	public void setColumnName(Vector<String> columnName) {
		this.columnName = columnName;
	}

	public Vector<?> getTableValue() {
		return tableValue;
	}

	public void setTableValue(Vector<?> tableValue) {
		this.tableValue = tableValue;
	}

	public int[] getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(int[] columnWidth) {
		this.columnWidth = columnWidth;
	}

	public int[] getColumnHide() {
		return columnHide;
	}

	public void setColumnHide(int[] columnHide) {
		this.columnHide = columnHide;
	}

	public int[] getCellEditableColumn() {
		return cellEditableColumn;
	}

	public void setCellEditableColumn(int[] cellEditableColumn) {
		this.cellEditableColumn = cellEditableColumn;
	}

	public boolean isResizingAllowed() {
		return isResizingAllowed;
	}

	public void setResizingAllowed(boolean isResizingAllowed) {
		this.isResizingAllowed = isResizingAllowed;
	}
}
