package common.jetty;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DtServlet extends HttpServlet {
	private static final long serialVersionUID = -6197211740957851460L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}

	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=gb2312");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = response.getWriter();
		try {
			@SuppressWarnings("unused")
			String action = getActionName(request.getRequestURI());
			String xml = "";
			// if (action.equals(IServlet.Sjshq)) {// 深圳行情
			// xml = getRealDealSZ();
			// } else if (action.equals(IServlet.SjshqByKey)) {// 某证券深圳行情
			// String param = request.getParameter("HQZQDM");
			// xml = getRealDealSZByKey(URLDecoder.decode(param));
			// } else if (action.equals(IServlet.Show)) {// 上海行情
			// xml = getRealDealSH();
			// } else if (action.equals(IServlet.ShowByKey)) {// 某证券上海行情
			// String param = request.getParameter("S1");
			// xml = getRealDealSHByKey(URLDecoder.decode(param));
			// } else if (action.equals(IServlet.StockInfo)) {// 股指期货行情
			// xml = getRealQuoteFuture();
			// } else if (action.equals(IServlet.StockInfoByKey)) {// 某证券股指期货行情
			// String param = request.getParameter("VC_REPORT_CODE");
			// xml = getRealQuoteFutureByKey(URLDecoder.decode(param));
			//			}
			out.print(xml);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			out.print(e.getMessage());
			out.flush();
		}
	}

	// 过滤url
	private String getActionName(String uri) {
		try {
			int pos1 = uri.lastIndexOf("/");
			int pos2 = uri.lastIndexOf(".do");
			String actionname = uri.substring(pos1 + 1, pos2);
			return actionname;
		} catch (Exception e) {
			System.out.println("无法获得action名字！");
			return null;
		}
	}
	//
	// // 增加主键获取深圳内存某只股票行情
	// 
	// private String getRealDealSZByKey(String hqzqdm) {
	// String xml = "";
	// try {
	// ConcurrentHashMap<String, String> map = DtSjshqMemory.MapMemory
	// .get(hqzqdm);
	// if (map == null)
	// return "";
	// xml = Conver.concurrentHashMapToXml(map, hqzqdm);
	// System.out.println(xml);
	// // String filePath =
	// // "D:/SVN/eFund/RealDataProcess/temp/SjshqMemory.xml";
	// // Conver.concurrentHashMapsToXmlFile(DtSjshqMemory.MapMemory,
	// // "SjshqMemory", filePath);
	// } catch (Exception e) {
	// Log.logError("根据主键获取深圳内存行情xml数据出错", e);
	// }
	// return xml;
	// }
	//
	// // 获取深圳内存行情
	// private String getRealDealSZ() {
	// String xml = "";
	// try {
	// xml = Conver.concurrentHashMapsToXml(DtSjshqMemory.MapMemory,
	// "SjshqMemory");
	// // String filePath =
	// // "D:/SVN/eFund/RealDataProcess/temp/SjshqMemory.xml";
	// // Conver.concurrentHashMapsToXmlFile(DtSjshqMemory.MapMemory,
	// // "SjshqMemory", filePath);
	// } catch (Exception e) {
	// Log.logError("获取深圳内存行情xml数据出错", e);
	// }
	// return xml;
	// }
	//
	// // 增加主键获取上海内存某只股票行情
	// 
	// private String getRealDealSHByKey(String key) {
	// String xml = "";
	// try {
	// ConcurrentHashMap<String, String> map = DtShowMemory.MapMemory
	// .get(key);
	// if (map == null)
	// return "";
	// xml = Conver.concurrentHashMapToXml(map, key);
	// System.out.println(xml);
	// // String filePath =
	// // "D:/SVN/eFund/RealDataProcess/temp/SjshqMemory.xml";
	// // Conver.concurrentHashMapsToXmlFile(DtSjshqMemory.MapMemory,
	// // "SjshqMemory", filePath);
	// } catch (Exception e) {
	// Log.logError("根据主键获取上海内存行情xml数据出错", e);
	// }
	// return xml;
	// }
	//
	// // 获取上海内存行情
	// private String getRealDealSH() {
	// String xml = "";
	// try {
	// xml = Conver.concurrentHashMapsToXml(DtShowMemory.MapMemory,
	// "ShowMemory");
	// // String filePath =
	// // "D:/SVN/eFund/RealDataProcess/temp/SjshqMemory.xml";
	// // Conver.concurrentHashMapsToXmlFile(DtSjshqMemory.MapMemory,
	// // "SjshqMemory", filePath);
	// } catch (Exception e) {
	// Log.logError("获取上海内存行情xml数据出错", e);
	// }
	// return xml;
	// }
	//
	// // 增加主键获取股指期货内存某只股票行情
	// 
	// private String getRealQuoteFutureByKey(String key) {
	// String xml = "";
	// try {
	// ConcurrentHashMap<String, String> map = DtZhonghqMemory.MapMemory
	// .get(key);
	// if (map == null)
	// return "";
	// xml = Conver.concurrentHashMapToXml(map, key);
	// System.out.println(xml);
	// // String filePath =
	// // "D:/SVN/eFund/RealDataProcess/temp/SjshqMemory.xml";
	// // Conver.concurrentHashMapsToXmlFile(DtSjshqMemory.MapMemory,
	// // "SjshqMemory", filePath);
	// } catch (Exception e) {
	// Log.logError("根据主键获取股指期货内存行情xml数据出错", e);
	// }
	// return xml;
	// }
	//
	// // 获取深圳内存行情
	// private String getRealQuoteFuture() {
	// String xml = "";
	// try {
	// xml = Conver.concurrentHashMapsToXml(DtZhonghqMemory.MapMemory,
	// "ZhongMemory");
	// // String filePath =
	// // "D:/SVN/eFund/RealDataProcess/temp/SjshqMemory.xml";
	// // Conver.concurrentHashMapsToXmlFile(DtSjshqMemory.MapMemory,
	// // "SjshqMemory", filePath);
	//		} catch (Exception e) {
	//			Log.logError("获取股指期货内存行情xml数据出错", e);
	//		}
	//		return xml;
	//	}

}