package app;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.swing.JTree;

import org.dom4j.DocumentException;

import common.component.SScrollPane;
import common.component.STree;
import common.util.conver.UtilConver;
import consts.Const;


/**
 * @info 程序树形导航
 * 
 * @author fgq 20120831
 * 
 */
public class AppTree {
	private SScrollPane treeView;
	private JTree tree;

	private static AppTree appTree;

	public static AppTree getInstance() {
		if (appTree == null)
			return new AppTree();
		return appTree;
	}

	// 构造
	private AppTree() {
		try {
			// 读取数节点
			List<Object> list = UtilConver.xml2List(Const.XmlAppTree);
			tree = new STree(list);
			// 全部展开
			for (int i = 0; i < tree.getRowCount(); i++) {
				tree.expandRow(i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		treeView = new SScrollPane(tree);
	}

	public SScrollPane getTreeView() {
		return treeView;
	}
	public JTree getTree() {
		return tree;
	}

	// public static void main(String[] args) {
	// new AppTree();
	// }
}
