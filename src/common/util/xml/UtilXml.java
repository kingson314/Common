package common.util.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class UtilXml {
	/** **********************************Transfer******************************************* */
	// 根据xml文件名称获取xml文档
	public static Document getDocument(String filePath) throws DocumentException, FileNotFoundException, UnsupportedEncodingException {
		File xmlFile = new File(filePath);
		FileInputStream fileIns = new FileInputStream(xmlFile);
		Reader reader = new InputStreamReader(fileIns, "GBK");
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(reader);
		return document;
	}

	// xml字符串转化为xml文档
	public static Document strToDoc(String xml) throws DocumentException {
		Document document = DocumentHelper.parseText(xml);
		return document;
	}

	// xml文档转换为字符串
	public static String docToString(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat("", true, "GBK");
			format.setLineSeparator(" ");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("GBK");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	// xml文档生成文件
	public static void docToFile(Document document, String filePath) throws IOException {
		XMLWriter writer = null;
		OutputFormat format = new OutputFormat("", true, "GBK");
		format.setLineSeparator(" ");
		writer = new XMLWriter(new FileWriter(filePath), format);
		writer.write(document);
		writer.close();
	}

	/** **********************************Transfer******************************************* */

	// 只能更新有唯一node名称的text
	public static void updateXml(String filePath, String newFilePath, String node, String newText) {
		try {
			String xml = docToString(getDocument(filePath));
			String xmlFront = xml.substring(0, xml.indexOf("<" + node + ">"));
			String newNode = "<" + node + ">" + newText + "</" + node + ">";
			String xmlBack = xml.substring(xml.indexOf("</" + node + ">") + node.length() + 3);
			Document document = strToDoc(xmlFront + newNode + xmlBack);
			docToFile(document, newFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// public static void main(String[] args) {
	// Xml.updateXml(Const.XmlAppConfig, Const.XmlAppConfig, "appLookAndFeel",
	// "默认风格");
	// }
}
