package common.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UtilWeb {
	public static Document getDoc(String url) throws IOException {
//		String date=UtilConver.dateToStr(this.getDate(), Const.fm_yyyy_MM_dd);
		org.jsoup.Connection conn =  Jsoup.connect(url).timeout(20000).ignoreContentType(true);
		conn.header("Accept", "application/json, text/javascript, */*; q=0.01");
		conn.header("Accept-Encoding", "gzip, deflate, sdch");
		conn.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		conn.header("Cache-Control", "no-cache");
		conn.header("Connection", "keep-alive");
//		conn.header("Cookie", bean.getCookie().replace("$$$", "%"));
//		conn.header("Host", "");
//		conn.header("Referer", "");
		conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
		conn.header("X-Requested-With", "XMLHttpRequest");
		conn.header("Pragma", "no-cache");
		Document doc = conn.get();
//		System.out.println(doc.html());
		return doc;
	}
	
	public static void main(String[] args) throws IOException {
		String url="http://zwgk.gd.gov.cn/006939748/201506/t20150626_587391.html";
		Document doc=getDoc(url);
		Elements main=doc.getElementsByClass("main");
		Element elMain=main.first();
		System.out.println(elMain.html());
	}
}