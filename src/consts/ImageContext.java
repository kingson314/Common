package consts;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import common.util.string.UtilString;

/* 
 @author Feng Guoqu
 @description 本系统的图片注册表
 */
public class ImageContext {

	static {
		try {
			String basePath = URLDecoder.decode(UtilString.isNil(ImageContext.class.getResource("")), "utf-8");
			if(!"".equals(basePath)){
				IconPath = basePath.substring(basePath.indexOf("file:/") + "file:/".length(), basePath.indexOf("bin/")) + "icons/";
			}else{
				IconPath="icons/";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public static String IconPath;
	public final static String Sys = IconPath + "Sys.gif";
	public final static String Add = IconPath + "add.gif";
	public final static String AddSche = IconPath + "AddSche.gif";
	public final static String Mod = IconPath + "Mod.gif";
	public final static String Post = IconPath + "Post.gif";
	public final static String Cancel = IconPath + "Cancel.gif";
	public final static String Exit = IconPath + "Exit.gif";
	public final static String Del = IconPath + "Del.gif";
	public final static String Test = IconPath + "Test.gif";
	public final static String Ok = IconPath + "Ok.gif";

	public final static String Copy = IconPath + "Copy.gif";
	public final static String Export = IconPath + "Export.gif";
	public final static String Import = IconPath + "Import.gif";
	public final static String Exec = IconPath + "Exec.gif";
	public final static String Stop = IconPath + "Stop.gif";
	public final static String Start = IconPath + "Start.gif";
	public final static String Refresh = IconPath + "Refresh.gif";
	public final static String Clear = IconPath + "clear.gif";
	public final static String Query = IconPath + "query.gif";

	public final static String SystemParam = IconPath + "SystemParam.gif";
	public final static String DbCon = IconPath + "DbCon.gif";
	public final static String Settings = IconPath + "Settings.gif";
	public final static String Ftp = IconPath + "Ftp.gif";
	public final static String Mail = IconPath + "Mail.gif";
	public final static String Monitor = IconPath + "Monitor.gif";
	public final static String MonitorGroup = IconPath + "MonitorGroup.gif";
	public final static String Backup = IconPath + "Backup.gif";
	public final static String EncryptAES = IconPath + "EncryptAES.gif";
	public final static String TxtEncryptAES = IconPath + "txtEncryptAES.gif";
	public final static String About = IconPath + "About.gif";
	public final static String AboutImage = IconPath + "AboutImage.gif";

	public final static String Task = IconPath + "Task.gif";
	public final static String TaskGroup = IconPath + "TaskGroup.gif";
	public final static String Sche = IconPath + "Sche.gif";

	public final static String Up = IconPath + "up.gif";
	public final static String Down = IconPath + "Down.gif";
	public final static String FileName = IconPath + "FileName.gif";
	public final static String FilePath = IconPath + "FilePath.gif";

	public final static String DialogManuExecTaskParam = IconPath + "DialogManuExecTaskParam.gif";
	// 调度状态
	public final static String ViewStatus = IconPath + "ViewStatus.gif";
	public final static String ModStatus = IconPath + "ModStatus.gif";

	public final static String SystemExit = IconPath + "SystemExit.gif";
	public final static String SystemExitUI = IconPath + "SystemExitUI.gif";
	public final static String SystemBootUI = IconPath + "SystemBootUI.gif";

	public final static String LookAndFeel = IconPath + "LookAndFeel.gif";

	public final static String TabLogCommon = IconPath + "TabLogCommon.gif";
	public final static String TabLogAbridgement = IconPath + "TabLogAbridgement.gif";
	public final static String TabLogLately = IconPath + "TabLogLately.gif";
	public final static String TabLogSche = IconPath + "TabLogSche.gif";
	public final static String TabThread = IconPath + "TabThread.gif";
	public final static String TabTask = IconPath + "TabTask.gif";
	public final static String TabTaskGroup = IconPath + "TabTaskGroup.gif";
	public final static String TabSche = IconPath + "TabSche.gif";
	public final static String LogSys = IconPath + "LogSys.gif";// 系统日志图标
	public final static String LogTask = IconPath + "LogTask.gif";// 子任务日志图标

	public final static String LogView = IconPath + "LogView.gif";// 子任务日志图标
	public final static String Login = IconPath + "Login.gif";// 子任务日志图标
	public final static String LoginBackGround = IconPath + "LoginBackGround.gif";// 子任务日志图标

	public final static String SystemTray = IconPath + "SystemTray.gif";
	public final static String SystemLock = IconPath + "SystemLock.gif";
	public final static String TrayUnLock = IconPath + "TrayUnLock.gif";
	public final static String Calendar = IconPath + "Calendar.gif";

	public final static String NowDate = IconPath + "NowDate.gif";
	public final static String StartJetty = IconPath + "StartJetty.gif";
	public final static String StopJetty = IconPath + "StopJetty.gif";

	public final static String Close = IconPath + "Close.gif";
	public final static String CloseAll = IconPath + "CloseAll.gif";
	public final static String MultiCombobox = IconPath + "MultiCombobox.gif";

	public final static String MainView = IconPath + "mainView.gif";
	public final static String Account = IconPath + "Account.gif";
	public final static String Symbol = IconPath + "Symbol.gif";
	public final static String Dictionary = IconPath + "Dictionary.gif";
	public final static String PlaceOrder = IconPath + "PlaceOrder.gif";
	public final static String Match = IconPath + "Match.gif";
	public final static String Ready = IconPath + "ready.gif";
	public final static String Simulate = IconPath + "ready.gif";

	public static void main(String[] arg) throws UnsupportedEncodingException {
		// System.out.println(ImageContext.class.getClassLoader().getResource(""));
		// System.out.println(ClassLoader.getSystemResource(""));
		// System.out.println(ImageContext.class.getResource("/"));
		// System.out.println(new File("").getAbsolutePath());
		// System.out.println(System.getProperty("user.dir"));
		String string = ImageContext.class.getResource("").toString();
		System.out.println(URLDecoder.decode(string, "utf-8"));
		// System.out.println(new String("中文".getBytes(), "utf-8"));

	}
}