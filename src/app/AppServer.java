package app;

import common.jetty.JettyServer;
import common.util.log.UtilLog;
import common.util.string.UtilString;

/**
 * @info 程序服务器
 * 
 * @author fgq 20120831
 * 
 */
public class AppServer {
	private static AppServer appServer;
	private static JettyServer jettyServer;

	public static AppServer getInstance() {
		if (appServer == null)
			return new AppServer();
		return appServer;
	}

	private AppServer() {
	}

	// 启动jetty服务
	public void startJetty() {
		try {
			String jettyPort = UtilString.isNil(AppConfig.getInstance().getMapAppConfig().get("jettyPort"), "0");
			if (jettyServer == null)
				jettyServer = new JettyServer(Integer.valueOf(jettyPort));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!"STARTED".equalsIgnoreCase(jettyServer.getState())) {
			jettyServer.start();
			AppStatus.getInstance().setStatus("jetty", "Jetty服务启动端口:" + jettyServer.getPort());
			UtilLog.logInfo("jetty is started");
		} else {
			UtilLog.logInfo("jetty is running");
		}
	}

	// 停止jetty服务
	public void stopJetty() {
		if (jettyServer != null) {
			jettyServer.stop();
			jettyServer = null;
			AppStatus.getInstance().remove("jetty");
			UtilLog.logInfo("jetty is stopped");
		} else {
			UtilLog.logInfo("jetty is stopped");
		}
	}

	// 自动启动jetty服务
	public void autoStartJetty() {
		String startJetty = AppConfig.getInstance().getMapAppConfig().get("startJetty");
		if (startJetty.equalsIgnoreCase("true")) {
			startJetty();
		}
	}

	// 启动自定义的jetty端口服务(暂不扩展)
	public void manuStartJetty(int port) {
		new JettyServer(port).start();
	}

}
