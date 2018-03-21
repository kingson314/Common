package app;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import javax.swing.JToolBar;
import org.dom4j.DocumentException;

import common.component.SButton;
import common.util.conver.UtilConver;
import common.util.reflect.UtilReflect;
import common.util.string.UtilString;
import consts.Const;
import consts.ImageContext;


/**
 * @info 程序工具栏
 * 
 * @author fgq 20120831
 * 
 */
public class AppToolBar {
	private static AppToolBar tb;
	private JToolBar toolBar;

	public JToolBar getToolBar() {
		return toolBar;
	}

	public static AppToolBar getInstance() {
		if (tb == null)
			tb = new AppToolBar();
		return tb;
	}

	// 构造
	private AppToolBar() {
		try {
			toolBar = new JToolBar();
			toolBar.setEnabled(false);
			List<Map<String, Object>> list = getAppToolBarConfig(Const.XmlAppToolBar);
			for (int i = 0; i < list.size(); i++) {
				final Map<String, Object> map = list.get(i);
				final String action = map.get("action").toString();
				if (UtilString.isNil(action).equals("")) {
					{
						toolBar.addSeparator(new Dimension(5, 30));
					}
				} else {
					final SButton btn = new SButton("", ImageContext.IconPath + map.get("ico"));
					// btn.setName(map.get("name").toString());
					btn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (!"".equals(action)) {
								if (action.lastIndexOf(".") >= 0) {
									String className = action.substring(0, action.lastIndexOf("."));
									String methodName = action.substring(action.lastIndexOf(".") + 1);
									// System.out.println(className + " " +
									// methodName);
									new UtilReflect(null, className, methodName, null).invoke();
								}
							}
						}
					});
					toolBar.add(btn);
					{
						toolBar.addSeparator(new Dimension(1, 30));
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	// 读取工具栏的按钮信息
	private List<Map<String, Object>> getAppToolBarConfig(String filePath) throws FileNotFoundException, UnsupportedEncodingException, DocumentException {
		return UtilConver.xmlFileToList(filePath);
	}
}
