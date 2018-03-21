package app;

import java.awt.Window;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.luna.LunaLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.jtattoo.plaf.texture.TextureLookAndFeel;
import common.util.array.UtilArray;

/**
 * @info 程序皮肤
 * 
 * @author fgq 20120831
 * 
 */
public class AppLookAndFeel {
	private static final String[] LookAndFeel = new String[] { "javax.swing.plaf.metal.MetalLookAndFeel", "com.jtattoo.plaf.noire.NoireLookAndFeel", "com.jtattoo.plaf.smart.SmartLookAndFeel",
			"com.jtattoo.plaf.mint.MintLookAndFeel", "com.jtattoo.plaf.mcwin.McWinLookAndFeel", "com.jtattoo.plaf.luna.LunaLookAndFeel", "com.jtattoo.plaf.hifi.HiFiLookAndFeel",
			"com.jtattoo.plaf.fast.FastLookAndFeel", "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel", "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel", "com.jtattoo.plaf.aero.AeroLookAndFeel",
			"com.jtattoo.plaf.acryl.AcrylLookAndFeel" };
	// private static final String[] LookAndFeelEnName = new String[] {
	// "default","noire",
	// "smart", "mint", "mcwin", "luna", "hifi", "fast", "bernstein",
	// "aluminium", "aero", "acryl" };
	private GUIProperties guiProps;
	private static AppLookAndFeel appLookAndFeel;

	public static final String[] LookAndFeelCnName = new String[] { "默认风格", "柔和黑", "木质感+XP风格", "椭圆按钮+黄色按钮背景", "椭圆按钮+绿色按钮背景", "纯XP风格", "黑色风格", "普通swing风格+蓝色边框", "黄色风格", "椭圆按钮+翠绿色按钮背景+金属质感(默认)",
			"XP清新风格", " 布质感+swing纯风格" };

	public static AppLookAndFeel getInstance() {
		if (appLookAndFeel == null)
			appLookAndFeel = new AppLookAndFeel();
		return appLookAndFeel;
	}

	// 构造
	private AppLookAndFeel() {
		guiProps = new GUIProperties();
	}

	// // 设置皮肤主题
	// public void updateLookAndFeel(Component dialog, String lfName) {
	// int i = Fun.getArrayIndex(LookAndFeelCnName, lfName);
	// if (i >= 0) {
	// String newLookAndFeel = LookAndFeel[i];
	// try {
	// UIManager.setLookAndFeel(newLookAndFeel);
	// SwingUtilities.updateComponentTreeUI(dialog);
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// } catch (InstantiationException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (UnsupportedLookAndFeelException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	// 设置皮肤主题
	public void updateLookAndFeel(String lfName) {
		try {
			String lf = LookAndFeel[UtilArray.getArrayIndex(LookAndFeelCnName, lfName)];
			javax.swing.LookAndFeel oldLAF = UIManager.getLookAndFeel();
			boolean oldDecorated = false;
			if (oldLAF instanceof MetalLookAndFeel)
				oldDecorated = true;
			if (oldLAF instanceof AbstractLookAndFeel)
				oldDecorated = AbstractLookAndFeel.getTheme().isWindowDecorationOn();
			if (lf.equals("javax.swing.plaf.metal.MetalLookAndFeel")) {
				MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
			} else if (lf.equals("com.jtattoo.plaf.acryl.AcrylLookAndFeel"))
				AcrylLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.aero.AeroLookAndFeel"))
				AeroLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel"))
				AluminiumLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel"))
				BernsteinLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.fast.FastLookAndFeel"))
				FastLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.graphite.GraphiteLookAndFeel"))
				GraphiteLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.hifi.HiFiLookAndFeel"))
				HiFiLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.luna.LunaLookAndFeel"))
				LunaLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.mcwin.McWinLookAndFeel"))
				McWinLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.mint.MintLookAndFeel"))
				MintLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.noire.NoireLookAndFeel"))
				NoireLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.smart.SmartLookAndFeel"))
				SmartLookAndFeel.setTheme("Default");
			else if (lf.equals("com.jtattoo.plaf.texture.TextureLookAndFeel"))
				TextureLookAndFeel.setTheme("Default");
			guiProps.setTheme("Default");
			guiProps.setLookAndFeel(lf);
			UIManager.setLookAndFeel(guiProps.getLookAndFeel());
			javax.swing.LookAndFeel newLAF = UIManager.getLookAndFeel();
			boolean newDecorated = false;
			if (newLAF instanceof MetalLookAndFeel)
				newDecorated = true;
			if (newLAF instanceof AbstractLookAndFeel)
				newDecorated = AbstractLookAndFeel.getTheme().isWindowDecorationOn();
			if (oldDecorated != newDecorated) {
				// Rectangle savedBounds = getBounds();
				// app.dispose();
				// app = new JTattooDemo(savedBounds);
				// app.setBounds(savedBounds);
			} else {
				Window windows[] = Window.getWindows();
				for (int i = 0; i < windows.length; i++)
					if (windows[i].isDisplayable())
						SwingUtilities.updateComponentTreeUI(windows[i]);
			}
			// SwingUtilities.updateComponentTreeUI(AppTree.getInstance()
			// .getTree());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public class GUIProperties {
		public static final boolean IS_CUSTOM_ENABLED = false;
		public static final int TEXTURE_TYPE = 0;
		public static final String PLAF_METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
		public static final String PLAF_NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		public static final String PLAF_MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		public static final String PLAF_SYSTEM = ""; // UIManager.getSystemLookAndFeelClassName();
		public static final String PLAF_ACRYL = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";
		public static final String PLAF_AERO = "com.jtattoo.plaf.aero.AeroLookAndFeel";
		public static final String PLAF_ALUMINIUM = "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel";
		public static final String PLAF_BERNSTEIN = "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel";
		public static final String PLAF_FAST = "com.jtattoo.plaf.fast.FastLookAndFeel";
		public static final String PLAF_GRAPHITE = "com.jtattoo.plaf.graphite.GraphiteLookAndFeel";
		public static final String PLAF_HIFI = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
		public static final String PLAF_LUNA = "com.jtattoo.plaf.luna.LunaLookAndFeel";
		public static final String PLAF_MCWIN = "com.jtattoo.plaf.mcwin.McWinLookAndFeel";
		public static final String PLAF_MINT = "com.jtattoo.plaf.mint.MintLookAndFeel";
		public static final String PLAF_NOIRE = "com.jtattoo.plaf.noire.NoireLookAndFeel";
		public static final String PLAF_SMART = "com.jtattoo.plaf.smart.SmartLookAndFeel";
		public static final String PLAF_TEXTURE = "com.jtattoo.plaf.texture.TextureLookAndFeel";
		public static final String PLAF_CUSTOM = "com.jtattoo.plaf.custom.systemx.SystemXLookAndFeel";
		private String lookAndFeel;
		private String theme;

		public GUIProperties() {
			lookAndFeel = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";
			theme = "Default";
		}

		public void setLookAndFeel(String aLookAndFeel) {
			lookAndFeel = aLookAndFeel;
		}

		public String getLookAndFeel() {
			return lookAndFeel;
		}

		public void setTheme(String aTheme) {
			theme = aTheme;
		}

		public String getTheme() {
			return theme;
		}

		public boolean isMetalLook() {
			return lookAndFeel.equals("javax.swing.plaf.metal.MetalLookAndFeel");
		}

		public boolean isNimbusLook() {
			return lookAndFeel.equals("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}

		public boolean isMotifLook() {
			return lookAndFeel.equals("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}

		public boolean isSystemLook() {
			return lookAndFeel.equals(PLAF_SYSTEM);
		}

		public boolean isAcrylLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		}

		public boolean isAeroLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.aero.AeroLookAndFeel");
		}

		public boolean isAluminiumLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		}

		public boolean isBernsteinLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		}

		public boolean isFastLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.fast.FastLookAndFeel");
		}

		public boolean isGraphiteLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		}

		public boolean isHiFiLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		}

		public boolean isLunaLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.luna.LunaLookAndFeel");
		}

		public boolean isMcWinLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		}

		public boolean isMintLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.mint.MintLookAndFeel");
		}

		public boolean isNoireLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.noire.NoireLookAndFeel");
		}

		public boolean isSmartLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.smart.SmartLookAndFeel");
		}

		public boolean isTextureLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.texture.TextureLookAndFeel");
		}

		public boolean isCustomEnabled() {
			return false;
		}

		public boolean isCustomLook() {
			return lookAndFeel.equals("com.jtattoo.plaf.custom.systemx.SystemXLookAndFeel");
		}

	}

}
