package module.about;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JTextPane;

import app.AppConfig;
import common.component.SDialog;
import common.component.SLabel;
import common.util.log.UtilLog;
import consts.ImageContext;

public class DialogAbout extends SDialog {
	private static final long serialVersionUID = 1L;
	private SLabel lImage;
	private JTextPane tpAbout;

	// public static void main(String[] args) {
	//
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// DialogAbout inst = new DialogAbout();
	// inst.setVisible(true);
	// }
	// });
	// }

	// 构造
	public DialogAbout() {
		try {
			initGUI();
		} catch (Exception e) {
			UtilLog.logError("关于对话框构造错误:", e);
		} finally {
		}
	}

	// 初始化界面
	private void initGUI() {
		try {
			{
				// this.setContentPane(new GlassBox());
			}
			this.setSize(460, 216);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(
					ImageContext.About));
			setModal(true);
			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this
					.getWidth()) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this
					.getHeight()) / 2;
			this.setLocation(w, h);
			setTitle("关于");
			getContentPane().setLayout(null);
			{
				String about = AppConfig.getInstance().getMapAppConfig().get(
						"appTitle")
						+ "\n"
						+ "             Version:"
						+ AppConfig.getInstance().getMapAppConfig().get(
								"appVersion")
						+ "\n"
						+ "                "
						+ AppConfig.getInstance().getMapAppConfig().get(
								"appPublishDate");
				tpAbout = new JTextPane();
				tpAbout.setText(about);
				tpAbout.setEditable(false);
				tpAbout.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 13));
				tpAbout.setBackground(getContentPane().getBackground());
				tpAbout.setForeground(getContentPane().getForeground());
				getContentPane().add(tpAbout, "Center");
				tpAbout.setBounds(150, 35, 250, 70);

				String copyRigth = AppConfig.getInstance().getMapAppConfig()
						.get("appCopyRight").replaceAll("\n", " ");
				JTextPane tpCopyRight = new JTextPane();
				tpCopyRight.setText(copyRigth);
				tpCopyRight.setEditable(false);
				tpCopyRight.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT,
						11));
				tpCopyRight.setBackground(getContentPane().getBackground());
				tpCopyRight.setForeground(getContentPane().getForeground());
				getContentPane().add(tpCopyRight, "Center");
				tpCopyRight.setBounds(50, 135, 350, 60);

			}
			{
				lImage = new SLabel("", ImageContext.AboutImage);
				getContentPane().add(lImage);
				lImage.setBounds(80, 33, 53, 47);
			}
			GridLayout thisLayout = new GridLayout(1, 1);
			thisLayout.setColumns(1);
			thisLayout.setHgap(5);
			thisLayout.setVgap(5);

		} catch (Exception e) {
			UtilLog.logError("关于对话框初始化界面错误:", e);
		} finally {
		}
	}
}
