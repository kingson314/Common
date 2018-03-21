package common.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import common.util.log.UtilLog;

public class JettyServer {
	private Server server;
	private int port;
	private String state;

	// 构造
	public JettyServer(int port) {
		server = new Server();
		this.port = port;
		Connector conn = new SocketConnector();
		conn.setPort(port);
		// conn.setHost("192.1.50.42");
		server.setConnectors(new Connector[] { conn });

		ContextHandlerCollection handler = new ContextHandlerCollection();
		ServletContextHandler servlethandler = new ServletContextHandler();
		servlethandler.addServlet(DtServlet.class, "*.do");
		handler.addHandler(servlethandler);

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setResourceBase("./jsp");
		handler.addHandler(webapp);
		server.setHandler(handler);
	}

	// 启动服务
	public void start() {
		try {
			server.start();
			state = server.getState();
		} catch (Exception e) {
			UtilLog.logError("Jetty服务器启动失败:", e);
		}
	}

	// 停止服务
	public void stop() {
		try {
			server.stop();
			state = server.getState();
		} catch (Exception e) {
			UtilLog.logError("Jetty服务器停止失败:", e);
		}
	}

	// 获取服务状态
	public String getState() {
		return state;
	}

	// 获取服务端口
	public int getPort() {
		return port;
	}

	// public static void main(String[] args) {
	// new JettyServer(9999).start();
	// }
}