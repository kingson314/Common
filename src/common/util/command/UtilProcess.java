package common.util.command;

public class UtilProcess {
	/**
	 * @Description:
	 * @date Jan 27, 2014
	 * @author:fgq
	 */

	// 关闭进程
	public static void processclose(String exeName) {
		String cmmtext = "";
		try {
			cmmtext = "taskkill /f /im " + exeName;
			Process process = Runtime.getRuntime().exec("cmd /c " + cmmtext);
			process.waitFor();
		} catch (Exception e) {
		}
	}

	// 启动进程
	public static void processopen(String exeName) {
		try {
			String cmmtext = "";
			cmmtext = "\"" + System.getProperty("user.dir") + "/" + exeName + "\"";
			Process process = Runtime.getRuntime().exec("cmd /c " + cmmtext);
			process.waitFor();
		} catch (Exception e) {
		}
	}
}
