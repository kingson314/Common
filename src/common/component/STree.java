package common.component;

import java.awt.Color;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.dom4j.DocumentException;

import common.util.reflect.UtilReflect;

import consts.ImageContext;


public class STree extends JTree {
	private static final long serialVersionUID = -5419772856674664402L;
	private List<Object> ItemList;

	// 构造
	public STree(final List<Object> list) {
		super(getMNode(list));
		this.ItemList = list;
		this.putClientProperty("JTree.lineStyle", "Horizontal");
		this.putClientProperty("JTree.lineStyle", "Angled");
		// 采用渲染，点击事件会在界面重刷时重复按键//如不使用此渲染，可启用下面的this鼠标事件
		this.setCellRenderer(new MyTreeCellRenderer());
		// this.addMouseListener(new MouseListener() {
		// public void mouseClicked(MouseEvent e) {
		// MyTreeNode node = (MyTreeNode) getLastSelectedPathComponent();
		// System.out.println(node);
		// // if (node != null) {
		// // List<Map<String, String>> rs = new ArrayList<Map<String,
		// // String>>();
		// // while (!node.isRoot()) {
		// // Map<String, String> map = new HashMap<String, String>();
		// // map.put("nodeText", node.toString());
		// // map.put("action", getAction(list, node.toString()));
		// // rs.add(map);
		// // node = (DefaultMutableTreeNode) node.getParent();
		// // }
		// // // System.out.println(rs.size());
		// // for (int i = rs.size() - 1; i >= 0; i--) {
		// // String action = rs.get(i).get("action");
		// // String nodeText = rs.get(i).get("nodeText");
		// // String[] param = new String[] { nodeText };
		// // // System.out.println(nodeText);
		// // // System.out.println(action);
		// // new Reflect(action, param).invoke();
		// // }
		// // } else
		// // System.out.println("null");
		// }
		//
		// public void mouseEntered(MouseEvent e) {
		// }
		//
		// public void mouseExited(MouseEvent e) {
		// }
		//
		// public void mousePressed(MouseEvent e) {
		// }
		//
		// public void mouseReleased(MouseEvent e) {
		// }
		// });
	}

	// 获取树节点
	public static DefaultMutableTreeNode getMNode(List<Object> list) {
		MyDefaultMutableTreeNode node = new MyDefaultMutableTreeNode();
		node.getTreeNode(list, 0);
		return node.getRootNode();
	}

	// 自定义node类
	private static class MyDefaultMutableTreeNode {
		private DefaultMutableTreeNode rootNode;
		private Map<Integer, DefaultMutableTreeNode> nodeMap = new TreeMap<Integer, DefaultMutableTreeNode>();

		@SuppressWarnings("unchecked")
		private void getTreeNode(List<Object> list, int level) {
			level++;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getClass().toString().indexOf("java.lang.String") >= 0) {
					if (i == 0) {
						if (rootNode == null) {
							rootNode = new DefaultMutableTreeNode(new MyTreeNode(list.get(0).toString(), list.get(1).toString(), list.get(2).toString()));

							// rootNode = new DefaultMutableTreeNode(list.get(0)
							// .toString());
							nodeMap.put(level, rootNode);
						} else {
							DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(new MyTreeNode(list.get(0).toString(), list.get(1).toString(), list.get(2).toString()));
							// DefaultMutableTreeNode newChild = new
							// DefaultMutableTreeNode(
							// list.get(0).toString());
							nodeMap.get(level - 1).add(newChild);
							nodeMap.put(level, newChild);

						}
						// System.out.println(list.get(0).toString());
					}
				} else {
					getTreeNode((List<Object>) list.get(i), level);
				}
			}
		}

		public DefaultMutableTreeNode getRootNode() {
			return rootNode;
		}
	}

	// 树渲染类
	private class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = -3147870557553148166L;
		private String selectedText = "";

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			MyTreeNode jtreeNode = (MyTreeNode) node.getUserObject();

			this.setText(jtreeNode.getText());
			this.setName(jtreeNode.getText());
			this.setIcon(new ImageIcon(ImageContext.IconPath + jtreeNode.getIconSelect()));
			this.setOpaque(true);
			if (selected) {
				if (!selectedText.equals(jtreeNode.getText())) {
					selectedText = jtreeNode.getText();
					// this.setIcon(new ImageIcon(Const.DefaultDir + "/Icons/"
					// + jtreeNode.getIconSelect()));
					this.setBackground(new Color(178, 180, 191));
					String action = getAction(ItemList, this.getText());
					// System.out.println("action:" + action);
					String[] param = new String[] { this.getText() };
					new UtilReflect(action, param).invoke();
				} else {
					selectedText = "";
				}
			} else {
				// this.setIcon(new ImageIcon(Const.DefaultDir + "/Icons/"
				// + jtreeNode.getIconUnSelect()));
				this.setBackground(tree.getBackground());
			}
			return this;
		}
	}

	// 自定义节点属性类
	private static class MyTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = -7179177066512993550L;
		private String iconSelect;
		private String iconUnSelect;
		private String text;

		public MyTreeNode(String text, String iconSelect, String iconUnSelect) {
			this.text = text;
			this.iconSelect = iconSelect;
			this.iconUnSelect = iconUnSelect;
		}

		@SuppressWarnings("unused")
		public MyTreeNode() {
			super();
		}

		public String getIconSelect() {
			return iconSelect;
		}

		@SuppressWarnings("unused")
		public String getIconUnSelect() {
			return iconUnSelect;
		}

		public String getText() {
			return text;
		}

	}

	// 获取该item的Action
	public String getAction(List<?> list, String itemText) {
		String rs = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getClass().toString().indexOf("java.lang.String") >= 0) {
				// System.out.println(list.get(i).toString());
				if (itemText.equalsIgnoreCase(list.get(0).toString())) {
					rs = list.get(3).toString();
					break;
				}
			} else {
				rs = getAction((List<?>) list.get(i), itemText);
			}
			if (rs != null)
				break;
		}
		return rs;
	}

	// 获取该item父节点的Text
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, DocumentException {
		// List<Object> list = Conver.xml2List(Const.XmlAppTree);
		// String param = new Tree(list).getParent(list, "任务列表");
		// System.out.println("get:" + param);
	}
}
