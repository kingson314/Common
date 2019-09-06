package common.util.conver;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import common.util.string.UtilString;
import common.util.xml.UtilXml;
import consts.Const;

/**
 * 格式转换
 * 
 * @author fgq 20120815
 * 
 */
public class UtilConver {
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '/') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   /uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}
	// ArrayToString
	public static String ArrayToString(String[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sb.append(UtilString.isNil(arr[i]));
		}
		return sb.toString();
	}

	// 数组转Vector
	public static Vector<String> arrayToVector(String[] arr) {
		Vector<String> vector = new Vector<String>();
		for (String s : arr) {
			vector.add(s);
		}
		return vector;
	}

	// 格式化长度
	public static String formatInt(int value, String format) {
		String rs = String.valueOf(value);
		while (rs.length() < format.length()) {
			rs = "0" + rs;
		}
		return rs;
	}

	// 日期转换为字符串
	public synchronized static String dateToStr(java.util.Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	// 当期日期转换为字符串
	public synchronized static String dateToStr(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar cl = Calendar.getInstance();
		return df.format(cl.getTimeInMillis());
	}

	// 字符串转换为日期
	public synchronized static Date strToDate(String sDate, String format) throws ParseException {
		if (sDate == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(sDate);
	}

	/** **********************************beanMap******************************************* */
	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return returnMap 转化出来的 Map 对象
	 */
	public static <T> Map<String, Object> beanToMap(T bean) {
		Class<? extends Object> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				// System.out.println(propertyName);
				if (!"class".equals(propertyName) && !"date".equals(propertyName)) {
					Method readMethod = descriptor.getReadMethod();
					if (readMethod == null)
						continue;
					Object result = readMethod.invoke(bean, new Object[0]);
					returnMap.put(propertyName, result != null ? result : "");
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException("分析类属性失败", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("分析类属性失败", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("分析类属性失败", e);
		}
		return returnMap;
	}

	/**
	 * 将一个Map对象转化为一个JavaBean
	 * 
	 * @param map
	 *            包含属性值的map
	 * @param bean
	 *            要转化的类型
	 * @return beanObj 转化出来的JavaBean对象
	 */
	public static <T> T mapToBean(Map<String, Object> paramMap, Class<T> clazz) {
		T beanObj = null;
		try {
			beanObj = clazz.newInstance();
			String propertyName = null;
			Object propertyValue = null;
			for (Map.Entry<String, Object> entity : paramMap.entrySet()) {
				propertyName = entity.getKey();
				propertyValue = entity.getValue();
				if (propertyValue == null)
					continue;
				Object value = null;
				if (propertyValue instanceof Object[]) {
					Object[] arrPropertyValue = (Object[]) propertyValue;
					for (int i = 0; i < arrPropertyValue.length; i++) {
						if (i == arrPropertyValue.length - 1) {
							value = UtilString.isNil(value) + UtilString.isNil((Object) arrPropertyValue[i]);
						} else
							value = UtilString.isNil(value) + UtilString.isNil((Object) arrPropertyValue[i]) + ";";
					}
				} else if (propertyValue instanceof String) {
					value = UtilString.isNil(propertyValue);
				} else if (propertyValue instanceof Integer) {
					value = propertyValue == null ? 0 : propertyValue;
				}
				// System.out.println(propertyName + ":" + value);
				setProperties(beanObj, propertyName, value);
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("不合法或不正确的参数", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("实例化JavaBean失败", e);
		} catch (Exception e) {
			throw new RuntimeException("Map转换为Java Bean对象失败", e);
		}
		return beanObj;
	}
	
	public static <T> void setProperties(T entity, String propertyName, Object propertyValue) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(propertyName, entity.getClass());
			Method methodSet = pd.getWriteMethod();
			// System.out.println(pd.getPropertyType());
			Class<?> clazz = pd.getPropertyType();
			// System.out.println(propertyName + " " + propertyValue);
			// System.out.println(clazz);
			if (clazz.equals(String.class)) {
				methodSet.invoke(entity, propertyValue);
			} else if (clazz.equals(Date.class)) {
				// 日期字段暂时忽略
			} else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
				methodSet.invoke(entity, Integer.valueOf(propertyValue == null ? "0" : propertyValue.toString()));
			} else {
				Method m = clazz.getMethod("valueOf", String.class);
				methodSet.invoke(entity, m.invoke(clazz, propertyValue));
			}
		} catch (Exception e) {
			// Log.logInfo(propertyName + " 字段没转换");
			//e.printStackTrace();
			System.out.println(propertyName + " 字段没转换");
		}
	}

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings("rawtypes")
	public static Object convertMap(Map map, Class type) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象
		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName().toUpperCase();
			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;
				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map convertBean(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/** **********************************beanMap******************************************* */

	/** **********************************xmlList******************************************* */

	// 根据List<Map<String, String>>生成xml文档
	public static Document listToXmlDocument(List<Map<String, Object>> list, String rootName) throws IOException {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement(rootName);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Element nodeElement = rootElement.addElement("Map" + String.valueOf(i));
			Iterator<?> itRow = map.entrySet().iterator();
			while (itRow.hasNext()) {
				Entry<?, ?> entryRow = (Entry<?, ?>) itRow.next();
				Element rowElement = nodeElement.addElement(entryRow.getKey().toString());
				rowElement.setText(entryRow.getValue().toString());
			}
		}
		return document;
	}

	// 根据Map<String,String>生成xml文件
	public static void listToXmlFile(List<Map<String, Object>> list, String rootName, String filePath) throws IOException {
		Document document = listToXmlDocument(list, rootName);
		UtilXml.docToFile(document, filePath);
	}

	// 根据Map<String,String>生成xml字符串
	public static String listToXml(List<Map<String, Object>> list, String rootName) throws IOException {
		Document document = listToXmlDocument(list, rootName);
		String rs = UtilXml.docToString(document);
		return rs;
	}

	// 根据xml字符串生成List<Map<String, Object>>
	public static List<Map<String, Object>> xmlToList(String xml) throws DocumentException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Document document = DocumentHelper.parseText(xml);
		Element nodesElement = document.getRootElement();
		List<?> nodes = nodesElement.elements();
		for (Iterator<?> its = nodes.iterator(); its.hasNext();) {
			Element nodeElement = (Element) its.next();
			Map<String, Object> map = xmlToMap(nodeElement.asXML());
			list.add(map);
		}
		return list;
	}

	// 根据xml文件生成List<Map<String, Object>>
	public static List<Map<String, Object>> xmlFileToList(String filePath) throws DocumentException, FileNotFoundException, UnsupportedEncodingException {
		Document document = UtilXml.getDocument(filePath);
		List<Map<String, Object>> list = xmlToList(document.asXML());
		return list;
	}

	/** **********************************xmlList******************************************* */

	/** **********************************xmlMap******************************************* */
	// 根据Map<String,String>生成xml文档
	public static Document mapToXmlDocument(Map<String, String> map, String rootName) throws IOException {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement(rootName);
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<?, ?> entry = (Entry<?, ?>) it.next();
			Element nodeElement = rootElement.addElement(entry.getKey().toString());
			nodeElement.setText(entry.getValue().toString());
		}
		return document;
	}

	// 根据Map<String,String>生成xml文件
	public static void mapToXmlFile(Map<String, String> map, String rootName, String filePath) throws IOException {
		Document document = mapToXmlDocument(map, rootName);
		UtilXml.docToFile(document, filePath);
	}

	// 根据Map<String,String>生成xml字符串
	public static String mapToXml(Map<String, String> map, String rootName) throws IOException {
		Document document = mapToXmlDocument(map, rootName);
		String rs = UtilXml.docToString(document);
		return rs;
	}

	// 根据xml文件生成Map<String,Object>
	public Map<String, Object> xmlFileToMap(String filePath) throws DocumentException, FileNotFoundException, UnsupportedEncodingException {
		Document document = UtilXml.getDocument(filePath);
		Map<String, Object> map = xmlToMap(document.asXML());
		return map;
	}

	// 根据xml字符串生成Map<String,Object>
	@SuppressWarnings("unchecked")
	public static Map<String, Object> xmlToMap(String xml) throws DocumentException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> node = DocumentHelper.parseText(xml).getRootElement().elements();
		for (Iterator<Object> it = node.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			map.put(elm.getName(), elm.getText());
			// System.out.println(elm.getName() + " " + elm.getText());
		}
		return map;

	}

	/** **********************************xmlMap******************************************* */
	/** **********************************xmlMaps******************************************* */

	// 根据Map<String, Map<String, String>>生成xml文档
	@SuppressWarnings("unchecked")
	public static Document mapsToXmlDocument(Map<String, Map<String, Object>> map, String rootName) throws IOException {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement(rootName);
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<?, ?> entry = (Entry<?, ?>) it.next();
			Element nodeElement = rootElement.addElement(entry.getKey().toString());
			Map<String, String> mapRow = (Map<String, String>) entry.getValue();
			Iterator<?> itRow = mapRow.entrySet().iterator();
			while (itRow.hasNext()) {
				Entry<?, ?> entryRow = (Entry<?, ?>) itRow.next();
				Element rowElement = nodeElement.addElement(entryRow.getKey().toString());
				rowElement.setText(entryRow.getValue().toString());
			}
		}
		return document;
	}

	// 根据Map<String,String>生成xml文件
	public static void mapsToXmlFile(Map<String, Map<String, Object>> mapParent, String rootName, String filePath) throws IOException {
		Document document = mapsToXmlDocument(mapParent, rootName);
		UtilXml.docToFile(document, filePath);
	}

	// 根据Map<String,String>生成xml字符串
	public static String mapsToXml(Map<String, Map<String, Object>> map, String rootName) throws IOException {
		Document document = mapsToXmlDocument(map, rootName);
		String rs = UtilXml.docToString(document);
		return rs;
	}

	// 根据xml文件生成Map<String, Map<String, Object>>
	public static Map<String, Map<String, Object>> xmlFileToMaps(String filePath) throws DocumentException, FileNotFoundException, UnsupportedEncodingException {
		Document document = UtilXml.getDocument(filePath);
		Map<String, Map<String, Object>> map = xmlToMaps(document.asXML());
		return map;
	}

	// 根据xml字符串生成Map<String, Map<String, Object>>
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, Object>> xmlToMaps(String xml) throws DocumentException {
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		Element nodeElement = DocumentHelper.parseText(xml).getRootElement();
		List<Map<String, String>> node = nodeElement.elements();
		for (Iterator<Map<String, String>> it = node.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			map.put(elm.getName(), xmlToMap(elm.asXML()));
		}
		return map;
	}

	/** **********************************xmlMaps******************************************* */

	/** **********************************xmlConcurrentHashMap******************************************* */
	// 根据ConcurrentHashMap<String,String>生成xml文档
	public static Document concurrentHashMapToXmlDocument(ConcurrentHashMap<String, String> map, String rootName) throws IOException {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement(rootName);
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<?, ?> entry = (Entry<?, ?>) it.next();
			Element nodeElement = rootElement.addElement(entry.getKey().toString());
			nodeElement.setText(entry.getValue().toString());
		}
		return document;
	}

	// 根据ConcurrentHashMap<String,String>生成xml文件
	public static void concurrentHashMapToXmlFile(ConcurrentHashMap<String, String> map, String rootName, String filePath) throws IOException {
		Document document = mapToXmlDocument(map, rootName);
		UtilXml.docToFile(document, filePath);
	}

	// 根据ConcurrentHashMap<String,String>生成xml字符串
	public static String concurrentHashMapToXml(ConcurrentHashMap<String, String> map, String rootName) throws IOException {
		Document document = mapToXmlDocument(map, rootName);
		String rs = UtilXml.docToString(document);
		return rs;
	}

	// 根据xml文件生成ConcurrentHashMap<String,Object>
	public ConcurrentHashMap<String, Object> xmlFileToConcurrentHashMap(String filePath) throws DocumentException, FileNotFoundException, UnsupportedEncodingException {
		Document document = UtilXml.getDocument(filePath);
		ConcurrentHashMap<String, Object> map = xmlToConcurrentHashMap(document.asXML());
		return map;
	}

	// 根据xml字符串生成ConcurrentHashMap<String,Object>
	@SuppressWarnings("unchecked")
	public static ConcurrentHashMap<String, Object> xmlToConcurrentHashMap(String xml) throws DocumentException {
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
		List<Object> node = DocumentHelper.parseText(xml).getRootElement().elements();
		for (Iterator<Object> it = node.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			// System.out.println(elm.getName()+" "+elm.getText());
			map.put(elm.getName(), elm.getText());
		}
		return map;

	}

	/** **********************************xmlConcurrentHashMap******************************************* */
	/** **********************************xmlConcurrentHashMaps******************************************* */

	// 根据ConcurrentHashMap<String, ConcurrentHashMap<String, String>>生成xml文档
	@SuppressWarnings("unchecked")
	public static Document concurrentHashMapsToXmlDocument(ConcurrentHashMap<String, ConcurrentHashMap<String, String>> map, String rootName) throws IOException {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement(rootName);
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<?, ?> entry = (Entry<?, ?>) it.next();
			Element nodeElement = rootElement.addElement(entry.getKey().toString());
			ConcurrentHashMap<String, String> mapRow = (ConcurrentHashMap<String, String>) entry.getValue();
			Iterator<?> itRow = mapRow.entrySet().iterator();
			while (itRow.hasNext()) {
				Entry<?, ?> entryRow = (Entry<?, ?>) itRow.next();
				Element rowElement = nodeElement.addElement(entryRow.getKey().toString());
				rowElement.setText(entryRow.getValue().toString());
			}
		}
		return document;
	}

	// 根据ConcurrentHashMap<String,String>生成xml文件
	public static void concurrentHashMapsToXmlFile(ConcurrentHashMap<String, ConcurrentHashMap<String, String>> map, String rootName, String filePath) throws IOException {
		Document document = concurrentHashMapsToXmlDocument(map, rootName);
		UtilXml.docToFile(document, filePath);
	}

	// 根据ConcurrentHashMap<String,String>生成xml字符串
	public static String concurrentHashMapsToXml(ConcurrentHashMap<String, ConcurrentHashMap<String, String>> map, String rootName) throws IOException {
		Document document = concurrentHashMapsToXmlDocument(map, rootName);
		String rs = UtilXml.docToString(document);
		return rs;
	}

	// 根据xml文件生成ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>
	public static ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> xmlFileToConcurrentHashMaps(String filePath) throws DocumentException, FileNotFoundException,
			UnsupportedEncodingException {
		Document document = UtilXml.getDocument(filePath);
		ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> map = xmlToConcurrentHashMaps(document.asXML());
		return map;
	}

	// 根据xml字符串生成ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>
	@SuppressWarnings("unchecked")
	public static ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> xmlToConcurrentHashMaps(String xml) throws DocumentException {
		ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> map = new ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>();
		Element nodeElement = DocumentHelper.parseText(xml).getRootElement();
		List<ConcurrentHashMap<String, String>> node = nodeElement.elements();
		for (Iterator<ConcurrentHashMap<String, String>> it = node.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			map.put(elm.getName(), xmlToConcurrentHashMap(elm.asXML()));
		}
		return map;
	}

	/** **********************************xmlMaps******************************************* */

	/** *****************************(非固定格式)xmlToMap************************************** */
	/**
	 * // Dom4j把xml转换成Map(非固定格式) // 将xml转换成Map,能够应对不用结构的xml,而不是只针对固定格式的xml. //
	 * 转换规则: // 1.主要是Map与List的互相嵌套 // 2.同名称的节点会被装进List
	 * 
	 * @throws DocumentException
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * 
	 */
	@SuppressWarnings({  "rawtypes" })
	public static Map<String, Object> xml2Map(String filePath) throws FileNotFoundException, UnsupportedEncodingException, DocumentException {
		Document doc = UtilXml.getDocument(filePath);
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), xmlToMap(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map xmlToMap(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();
				if (iter.elements().size() > 0) {
					Map m = xmlToMap(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	/** *****************************(非固定格式)xmlToMap************************************** */

	/** *****************************(非固定格式)xmlToList************************************** */

	public static List<Object> xml2List(String filePath) throws FileNotFoundException, UnsupportedEncodingException, DocumentException {
		Document doc = UtilXml.getDocument(filePath);
		List<Object> list = new ArrayList<Object>();
		if (doc == null)
			return list;
		Element eroot = doc.getRootElement();
		list.add(xmlToList(eroot));
		return list;
	}

	@SuppressWarnings("rawtypes")
	public static List xmlToList(Element e) {
		List<Object> list = null;
		list = new ArrayList<Object>();
		List elist = e.elements();
		for (int i = 0; i < elist.size(); i++) {
			Element eit = (Element) elist.get(i);
			// List<Object> itlist = new ArrayList<Object>();
			// itlist.add(eit.getTextTrim());
			// list.add(itlist);
			list.add(eit.getTextTrim());
			List eitem = eit.elements();
			if (eitem.size() > 0)
				list.add(xmlToList(eit));
		}
		return list;

	}

	/** *****************************(非固定格式)xmlToList************************************** */

	public static Map<String, Object> getLowercaseKeyMap(Map<String, Object> map) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			rsMap.put(entry.getKey().toLowerCase(), entry.getValue());
		}
		return rsMap;
	}

	/** **********************************Test******************************************* */

	public static void testListToXml() throws IOException {
		Map<String, Object> mapRow = new HashMap<String, Object>();
		// mapRow.put("任务Id", "01");
		// mapRow.put("任务名称", "内存行情");
		mapRow.put("任务ID", "01");
		mapRow.put("任务名称", "深圳行情");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(mapRow);
		String filePath = "D:/SVN/eFund/RealDataProcess/temp/taskList.xml";
		listToXmlFile(list, "TaskList", filePath);
	}

	@SuppressWarnings("rawtypes")
	public static void testXmlToList() throws FileNotFoundException, UnsupportedEncodingException {
		try {
			List<Map<String, Object>> list = xmlFileToList("D:/SVN/eFund/RealDataProcess/temp/taskList.xml");
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				for (Iterator<?> itRow = map.entrySet().iterator(); itRow.hasNext();) {
					Entry entryRow = (Entry) itRow.next();
					System.out.println(entryRow.getKey());
					System.out.println(entryRow.getValue());
				}
				System.out.println("-----------");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void testMapsToXml() throws IOException {
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		Map<String, Object> mapRow = new HashMap<String, Object>();
		// mapRow.put("任务Id", "01");
		// mapRow.put("任务名称", "内存行情");
		mapRow.put("taskId", "01");
		mapRow.put("taskName", "sjshq");
		map.put("Id01", mapRow);
		mapRow = new HashMap<String, Object>();
		mapRow.put("taskId", "02");
		mapRow.put("taskName", "show2003");
		map.put("Id02", mapRow);
		mapsToXmlFile(map, "TaskList", "D:/SVN/eFund/RealDataProcess/temp/taskMap.xml");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void testXmlToMaps() throws FileNotFoundException, UnsupportedEncodingException {
		try {
			Document document = UtilXml.getDocument("D:/SVN/eFund/RealDataProcess/temp/taskMap.xml");
			Map<String, Map<String, Object>> map = xmlToMaps(document.asXML());
			for (Iterator<?> it = map.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();
				System.out.println(entry.getKey());
				System.out.println("-----------");
				for (Iterator<?> itRow = ((Map<String, Object>) entry.getValue()).entrySet().iterator(); itRow.hasNext();) {
					Entry entryRow = (Entry) itRow.next();
					System.out.println(entryRow.getKey());
					System.out.println(entryRow.getValue());
				}
				System.out.println("-----------");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void testXmlFileToConMaps() throws FileNotFoundException, UnsupportedEncodingException {
		try {
			String filePath = "D:/SVN/eFund/RealDataProcess/temp/SjshqMemory.xml";
			ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> map = UtilConver.xmlFileToConcurrentHashMaps(filePath);
			Iterator<?> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry<?, ?> entry = (Entry<?, ?>) it.next();
				ConcurrentHashMap<String, Object> mapRow = (ConcurrentHashMap<String, Object>) entry.getValue();
				Iterator<?> itRow = mapRow.entrySet().iterator();
				String header = "";
				String rowValue = "";
				while (itRow.hasNext()) {
					Entry<?, ?> entryRow = (Entry<?, ?>) itRow.next();
					header = header + entryRow.getKey() + " ";
					rowValue = rowValue + entryRow.getValue().toString() + " ";
				}
				System.out.println(header);
				System.out.println(rowValue);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void testMapToXml() throws IOException {
		Map<String, String> mapRow = new HashMap<String, String>();
		// mapRow.put("任务Id", "01");
		// mapRow.put("任务名称", "内存行情");
		mapRow.put("taskId", "01");
		mapRow.put("taskName", "sjshq");
		mapToXmlFile(mapRow, "TaskList", "D:/SVN/eFund/RealDataProcess/temp/taskMap.xml");
	}

	@SuppressWarnings("rawtypes")
	public static void testXmlToMap() throws FileNotFoundException, UnsupportedEncodingException {
		try {
			Document document = UtilXml.getDocument("D:/SVN/eFund/RealDataProcess/temp/taskMap.xml");
			Map<String, Object> map = xmlToMap(document.asXML());
			for (Iterator<?> it = map.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void test3() throws DocumentException, FileNotFoundException, UnsupportedEncodingException {
		String path = System.getProperty("user.dir") + "/指令任务.txt";
		List<Map<String, Object>> list = UtilConver.xmlFileToList(path);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Iterator<?> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) it.next();
				System.out.println(entry.getKey() + "  " + entry.getValue());
			}
		}
	}

	public static void testMapBean() {
		// TaskSpace ts = new TaskSpace();
		// ts.setTaskId(1L);
		//
		// Map<String, Object> um = beanToMap(ts);
		// for (Map.Entry<String, Object> entity : um.entrySet()) {
		// System.out.println(entity.getKey() + ":" + entity.getValue());
		// }
		//
		// TaskSpace newTs = mapToBean(um, TaskSpace.class);
		// System.out.println(newTs.getTaskId());
	}

	// @SuppressWarnings("unchecked")
	// public static void main(String[] args) throws IOException {
	// // testMapBean();
	// // testMapsToXml();
	// // testXmlToMaps();
	// // testMapToXml();
	// // testXmlToMap();
	// // testListToXml();
	// // testXmlToList();
	// testXmlFileToConMaps();
	// System.out.println("Done");
	//
	// }
	/** **********************************Test******************************************* 
	 * @throws ParseException */
	
	public static void main(String[] args) throws ParseException {
		System.out.println(UtilConver.strToDate("20170315 01:05:01", Const.fm_yyyyMMdd_HHmmss));
	}
}
