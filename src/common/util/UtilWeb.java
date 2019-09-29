package common.util;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

import net.anthavio.phanbedder.Phanbedder;

public class UtilWeb {
	public static Document getDoc(String url) throws Exception {
		return getDoc(url, "");
	}

	public static Document getDoc(String url, String host) throws Exception {
		return getDoc(url, host, 30000);
	}

	public static Document getDoc(String url, String host, int timeout) throws Exception {
//		String date=UtilConver.dateToStr(this.getDate(), Const.fm_yyyy_MM_dd);
		org.jsoup.Connection conn = Jsoup.connect(url).timeout(timeout).ignoreContentType(true).ignoreHttpErrors(true);
		conn.header("Accept", "application/json, text/javascript, */*; q=0.01");
		conn.header("Accept-Encoding", "gzip, deflate, sdch");
		conn.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		conn.header("Cache-Control", "no-cache");
		conn.header("Connection", "keep-alive");
//		conn.header("Cookie", bean.getCookie().replace("$$$", "%"));
		conn.header("Host", host);
//		conn.header("Referer", "");
		conn.header("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
		conn.header("X-Requested-With", "XMLHttpRequest");
		conn.header("Pragma", "no-cache");
		conn.header("If-Modified-Since", new Date().toString());

		Document doc = conn.get();
//		System.out.println(doc.html());
		return doc;
	}
	
	public static Document getDocument(String html) {
		return Jsoup.parse(html);
	}

	public static Page getPage(String url, String host) throws Exception {

		WebClient webclient = new WebClient();
		webclient.waitForBackgroundJavaScript(600 * 1000);
		webclient.addRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		webclient.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
		webclient.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		webclient.addRequestHeader("Cache-Control", "no-cache");
		webclient.addRequestHeader("Connection", "keep-alive");
//		webclient.addRequestHeader("Cookie", bean.getCookie().replace("$$$", "%"));
		webclient.addRequestHeader("Host", host);
//		webclient.addRequestHeader("Referer", "");
		webclient.addRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
		webclient.addRequestHeader("X-Requested-With", "XMLHttpRequest");
		webclient.addRequestHeader("Pragma", "no-cache");
		return webclient.getPage(url);
	}
	//driver.quit(); 必须手动销毁
	public static PhantomJSDriver getDriver(String url) throws Exception {
		// Phanbedder to the rescue!
		File phantomjs = Phanbedder.unpack();
		DesiredCapabilities dcaps = new DesiredCapabilities();
		// ssl证书支持
		dcaps.setCapability("acceptSslCerts", true);
		// 截屏支持
		dcaps.setCapability("takesScreenshot", true);
		// css搜索支持
		dcaps.setCapability("cssSelectorsEnabled", true);
		// js支持
		dcaps.setJavascriptEnabled(true);
		// 驱动支持
		dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
		PhantomJSDriver driver = new PhantomJSDriver(dcaps);
		driver.setLogLevel(Level.WARNING);
		// Usual Selenium stuff...
		driver.get(url);
//				driver.get("http://zbtb.gd.gov.cn/");
//				driver.manage().addCookie(new Cookie("__jsluid_h", "ada6750f4c983626c6579e8b862c73d3"));
//				driver.manage().addCookie(new Cookie("_gscu_1834442730", "659221499vepds21"));
//				driver.manage().addCookie(new Cookie("_gscbrs_1834442730", "1"));
//				driver.manage().addCookie(new Cookie("tabmode", "1"));
//				driver.manage().addCookie(new Cookie("JSESSIONID", "2070FA50554F992A783A22A68C3B884B"));
//				driver.manage().addCookie(new Cookie("tl.session.id", "a17c68f676944d0b9bcf7a0325544819"));
//				driver.manage().addCookie(new Cookie("_gscs_1834442730", "t66282063t214bx10|pv:3"));
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//				System.out.println(driver.getPageSource());
//				getElement(driver,10,By.className("stepzb"));
//		        WebElement query = driver.findElement(By.id("head"));
//		        query.sendKeys("Phanbedder");
//		        query.submit();
		// 设置线程休眠时间等待页面加载完成
//				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//				System.out.println(driver.findElement(By.id("cencol")));
//				System.out.println(driver.findElement(By.id("iframeInfo")).getText());
//				driver.findElement(By.id("zbgg")).click();
//				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//				// 获取新页面窗口句柄并跳转，模拟登陆完成
//				String windowHandle = driver.getWindowHandle();
//				driver.switchTo().window(windowHandle);
		//
//				// 设置说说详情数据页面的加载时间并跳转
//				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//				WebElement webElement = driver.findElement(By.className("main_center"));
//				System.out.println(driver.getTitle());
//				System.out.println("=========");
//				System.out.println(webElement.getText());
//				driver.findElementById("tab-title").findElements(By.tagName("span")).get(1).click();
//				Thread.sleep(10000);
//		System.out.println(driver.getPageSource());
//		driver.quit(); 
		return driver;
	}

	public static String getPageSource(String url) throws Exception {
		PhantomJSDriver driver = null;
		String rs = "";
		try {
			driver = getDriver(url);
			rs = driver.getPageSource();
//			System.out.println(rs);
		} finally {
			driver.quit();
		}
		return rs;
	}
	
	public static Document getPageDocument(String url) throws Exception {
		String html=getPageSource(url);
		return getDocument(html);
	}

	public static void main(String[] args) throws Exception {
		String url = "http://zwgk.gd.gov.cn/006939748/201506/t20150626_587391.html";
		Document doc = getDoc(url);
		Elements main = doc.getElementsByClass("main");
		Element elMain = main.first();
		System.out.println(elMain.html());
	}
}