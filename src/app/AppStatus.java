package app;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import common.component.SBorder;
import common.component.SLabel;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;
import module.datetype.NowDateDao;

/**
 * @info 程序状态栏
 * 
 * @author fgq 20120831
 * 
 */
public class AppStatus {
	private JToolBar statusbar;
	private static AppStatus status;

	public static AppStatus getInstance() {
		if (status == null)
			status = new AppStatus();
		return status;
	}

	// 创建状态栏
	public AppStatus() {
		try {
			statusbar = new JToolBar();
			statusbar.setBorder(SBorder.getTitledBorder());
			String nowTime = UtilConver.dateToStr(Const.fm_yyyyMMdd_HHmmss);
			setStatus("bootTime", " 程序启动时间:" + nowTime);
		} catch (Exception e) {
			UtilLog.logError("主程序创建状态栏错误:", e);
		} finally {
		}
	}

	// 设置当前执行日期
	public void setNowDate() {
		if (NowDateDao.nowDate != null) {
			setStatus("nowDate", "程序当前执行日期:" + NowDateDao.nowDate.substring(0, 4) + "-" + NowDateDao.nowDate.substring(4, 6) + "-" + NowDateDao.nowDate.substring(6, 8));
		}
	}

	// 新增/修改一个状态栏项
	public void setStatus(String name, String text) {
		int index = getIndex(name);
		if (index > 0) {
			((SLabel) statusbar.getComponentAtIndex(index)).setText(text);
		} else {
			SLabel lstatus = new SLabel(text);
			lstatus.setName(name);
			statusbar.add(lstatus);
		}
		autoRefresh();
	}

	// 获取状态栏项目的索引号
	private int getIndex(String name) {
		int rs = -1;
		if (statusbar == null)
			return rs;
		for (int i = 0; i < statusbar.getComponentCount(); i++) {
			if (statusbar.getComponentAtIndex(i) instanceof SLabel) {
				if (UtilString.isNil(statusbar.getComponentAtIndex(i).getName()).equals(UtilString.isNil(name))) {
					rs = i;
					break;
				}
			}
		}
		return rs;
	}

	// 移除一个状态栏项
	public void remove(String name) {
		for (int i = 0; i < statusbar.getComponentCount(); i++) {
			if (statusbar.getComponentAtIndex(i).getName().equals(name)) {
				statusbar.remove(i);
			}
		}
		autoRefresh();
	}

	// 重新刷新状态栏
	public void autoRefresh() {
		try {
			for (int i = 0; i < statusbar.getComponentCount(); i++) {
				if (statusbar.getComponentAtIndex(i) instanceof SLabel) {
					((SLabel) statusbar.getComponentAtIndex(i)).setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / statusbar.getComponentCount(), 14));
					if (i == 0) {
						((SLabel) statusbar.getComponentAtIndex(i)).setHorizontalAlignment(SwingConstants.LEFT);
					} else if (i == statusbar.getComponentCount() - 1) {
						((SLabel) statusbar.getComponentAtIndex(i)).setHorizontalAlignment(SwingConstants.RIGHT);
					} else {
						((SLabel) statusbar.getComponentAtIndex(i)).setHorizontalAlignment(SwingConstants.CENTER);
					}
				}

			}
			statusbar.updateUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JToolBar getStatusbar() {
		return statusbar;
	}

}
