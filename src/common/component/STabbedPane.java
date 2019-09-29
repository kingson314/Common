package common.component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import common.util.array.UtilArray;
import common.util.log.UtilLog;
import consts.Const;
import consts.ImageContext;

public class STabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	private String[] persistTab;// 保留不删除的tab

	// 构造_带保留tab
	public STabbedPane(String[] persistTabText) {
		super();
		this.persistTab = persistTabText;
		this.setFont(Const.tfont);
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					removeTab();
				}
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					getPupuMenu().show(e.getComponent(), e.getX(), e.getY());
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
			}

			public void mouseMoved(MouseEvent e) {
				//System.out.println(e.paramString());
			}
		});
	}

	// 构造
	public STabbedPane() {
		super();
		this.setFont(Const.tfont);
	}

	// 添加组件
	public void addTab(String title, String iconPath, Component component, String tip, boolean closable) {
		if (hasTab(title))
			return;
		ImageIcon icon = new ImageIcon(iconPath);
		addTab(title, icon, component, tip);
	}

	// 移除组件
	private void removeTab() {
		if (getSelectedIndex() < 0)
			return;
		if (persistTab != null)
			for (int i = 0; i < persistTab.length; i++) {
				if (getTitleAt(getSelectedIndex()).equals(persistTab[i])) {
					return;
				}
			}
		remove(getSelectedIndex());
	}

	// 移除所有组件
	private void removeAllTab() {
		for (int i = getTabCount() - 1; i >= 0; i--) {
			if (persistTab != null) {
				if (UtilArray.getArrayIndex(persistTab, getTitleAt(i)) >= 0)
					continue;
			}
			remove(i);
		}
	}

	// 创建浮动菜单栏
	private JPopupMenu getPupuMenu() {
		JPopupMenu pm = new JPopupMenu();
		try {
			final SMenuItem miClose = new SMenuItem("关闭", ImageContext.Close);
			miClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					removeTab();
				}
			});
			pm.add(miClose);
			final SMenuItem miCloseAll = new SMenuItem("关闭全部", ImageContext.CloseAll);
			miCloseAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					removeAllTab();
				}
			});
			pm.add(miCloseAll);
		} catch (Exception e) {
			UtilLog.logError("菜单错误:", e);
		}
		return pm;
	}

	public void remove(String title) {
		for (int i = 0; i < getTabCount(); i++) {
			if (title.equals(getTitleAt(i))) {
				remove(i);
			}
		}
	}

	public void remove(String[] arrTitle) {
		for (String title : arrTitle) {
			for (int i = 0; i < getTabCount(); i++) {
				if (title.equals(getTitleAt(i))) {
					remove(i);
				}
			}
		}
	}

	// 判断是否已存在页面
	public boolean hasTab(String title) {
		for (int i = 0; i < getTabCount(); i++) {
			if (title.equals(getTitleAt(i))) {
				setSelectedIndex(i);
				return true;
			}
		}
		return false;
	}

	public void setSelected(String title) {
		for (int i = 0; i < getTabCount(); i++) {
			if (title.equals(getTitleAt(i))) {
				setSelectedIndex(i);
			}
		}
	}

	public String[] getPersistTab() {
		return persistTab;
	}

	public void setPersistTab(String[] persistTab) {
		if (this.getPersistTab() != null) {
			ArrayList<String> list = new ArrayList<String>();
			for (String tab : this.getPersistTab())
				list.add(tab);
			for (String tab : persistTab) {
				if (list.indexOf(tab) > 0)
					continue;
				list.add(tab);
			}
			this.persistTab = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				this.persistTab[i] = list.get(i);
			}

		} else
			this.persistTab = persistTab;
	}
}
