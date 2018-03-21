package common.component;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import consts.Const;


public class ShowDialog {
	static {
		UIManager.put("Button.font", Const.tfont);
		UIManager.put("Component.font", Const.tfont);
		UIManager.put("Label.font", Const.tfont);
		UIManager.put("ComboBox.font", Const.tfont);
		UIManager.put("ComboBoxItem.font", Const.tfont);
		UIManager.put("List.font", Const.tfont);
		// openType={
		// FILES_ONLY = 0;
		// DIRECTORIES_ONLY = 1;
		// FILES_AND_DIRECTORIES = 2;}
		// 更改图标:在Open的时候使用父类Component com的图标
	}

	// 打开对话框
	public static String open(Component com, String title, String filterName, String[] filter, int openType) {
		String path = "";
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setFileSelectionMode(openType);
		fc.setFileFilter(new FileNameExtensionFilter(filterName, filter));
		fc.setDialogTitle(title);
		int rs = fc.showDialog(com, "打开");
		if (rs == JFileChooser.APPROVE_OPTION) {
			path = fc.getSelectedFile().getAbsolutePath();
		}
		return path;
	}

	public static String open(Component com, String title, int openType) {
		String path = "";
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setFileSelectionMode(openType);
		fc.setDialogTitle(title);
		int rs = fc.showDialog(com, "打开");
		if (rs == JFileChooser.APPROVE_OPTION) {
			path = fc.getSelectedFile().getAbsolutePath();
		}
		return path;
	}

	// 如下4个函数在TaskTab.dialogTask中的Panel调用,可以使用TaskTab.dialogTask图标
	public static String openFile() {
		String path = "";
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setFileSelectionMode(0);
		fc.setDialogTitle("选择文件路径");
		int rs = fc.showDialog(null, "打开");
		if (rs == JFileChooser.APPROVE_OPTION) {
			path = fc.getSelectedFile().getAbsolutePath();
		}
		return path;
	}

	public static String[] openFileForPathAndFile() {
		String[] path = new String[] { "", "" };
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setFileSelectionMode(0);
		fc.setDialogTitle("选择文件路径");
		int rs = fc.showDialog(null, "打开");
		if (rs == JFileChooser.APPROVE_OPTION) {
			path[0] = fc.getSelectedFile().getParent() + "/";
			path[1] = fc.getSelectedFile().getName();
		}
		return path;
	}

	public static String openDir() {
		String path = "";
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setFileSelectionMode(1);
		fc.setDialogTitle("选择文件目录");
		int rs = fc.showDialog(null, "打开");
		if (rs == JFileChooser.APPROVE_OPTION) {
			path = fc.getSelectedFile().getPath() + "/";
		}
		return path;
	}

	public static String openFileAndDir() {
		String path = "";
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setFileSelectionMode(2);
		fc.setDialogTitle("选择文件路径");
		int rs = fc.showDialog(null, "打开");
		if (rs == JFileChooser.APPROVE_OPTION) {
			path = fc.getSelectedFile().getAbsolutePath();
		}
		return path;
	}

	// ---------------------------
	// 保存对话框
	public static String save(Component com, String title, String filterName, String[] filter, String defaultFileName) {
		String path = "";
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setSelectedFile(new File(defaultFileName));
		// String[] filter = { "xml" };
		fc.setFileFilter(new FileNameExtensionFilter(filterName, filter));
		fc.setDialogTitle(title);
		int rs = fc.showDialog(com, "保存");
		if (rs == JFileChooser.APPROVE_OPTION) {
			path = fc.getSelectedFile().getAbsolutePath();
			File file = new File(path);
			if (file.exists()) {
				int config = ShowMsg.showConfig("文件已存在，是否覆盖");
				if (config == 0) {
					file.delete();
				} else {
					return "";
				}
			}
		}
		return path;
	}

	public static String save(Component com, String title) {
		String path = "";
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setDialogTitle(title);
		int rs = fc.showDialog(com, "保存");
		if (rs == JFileChooser.APPROVE_OPTION) {
			path = fc.getSelectedFile().getAbsolutePath();
			File file = new File(path);
			if (file.exists()) {
				int config = ShowMsg.showConfig("文件已存在，是否覆盖");
				if (config == 0) {
					file.delete();
				} else {
					return "";
				}
			}
		}
		return path;
	}
}
