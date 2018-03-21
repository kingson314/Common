package common.util.json;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import common.util.conver.UtilConver;
import common.util.string.UtilString;
import consts.Const;

/**
 * 
 * @author fgq 20120815
 * @memo Jsons格式转换
 */
public class UtilJson {

	// bean转换字符串
	public static String getJsonStr(Object bean) {
		if (bean != null)
			return JSONObject.fromObject(bean).toString();
		else
			return null;
	}

	// 字符串转换bean
	public static Object getJsonBean(String json, Class<?> cl) {
		return JSONObject.toBean(JSONObject.fromObject(json), cl);
	}

	/**
	 * 把一个json数组串转换成普通数组
	 * 
	 * @param jsonArrStr
	 *            e.g. ['get',1,true,null]
	 * @return Object[]
	 */
	public static Object[] jsonToArray(String jsonArrStr) {
		return JSONArray.fromObject(jsonArrStr).toArray();
	}

	/**
	 * 把一个json数组串转换成实体数组
	 * 
	 * @param jsonArrStr
	 *            e.g. [{'name':'get'},{'name':'set'}]
	 * @param clazz
	 *            e.g. Person.class
	 * @return Object[]
	 */
	public static Object[] jsonToArray(String jsonArrStr, Class<?> clazz) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		Object[] objArr = new Object[jsonArr.size()];
		for (int i = 0; i < jsonArr.size(); i++) {
			objArr[i] = JSONObject.toBean(jsonArr.getJSONObject(i), clazz);
		}
		return objArr;
	}

	/**
	 * 把一个json数组串转换成实体数组，且数组元素的属性含有另外实例Bean
	 * 
	 * @param jsonArrStr
	 *            e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
	 * @param clazz
	 *            e.g. MyBean.class
	 * @param classMap
	 *            e.g. classMap.put("data", Person.class)
	 * @return Object[]
	 */
	public static Object[] jsonToArray(String jsonArrStr, Class<?> clazz, Map<?, ?> classMap) {
		JSONArray array = JSONArray.fromObject(jsonArrStr);
		Object[] obj = new Object[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			obj[i] = JSONObject.toBean(jsonObject, clazz, classMap);
		}
		return obj;
	}

	/**
	 * 把一个json数组串转换成存放普通类型元素的集合
	 * 
	 * @param jsonArrStr
	 *            e.g. ['get',1,true,null]
	 * @return List
	 */
	public static List<Object> jsonToList(String jsonArrStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < jsonArr.size(); i++) {
			list.add(jsonArr.get(i));
		}
		return list;
	}

	/**
	 * 把一个json数组串转换成集合，且集合里存放的为实例Bean
	 * 
	 * @param jsonArrStr
	 *            e.g. [{'name':'get'},{'name':'set'}]
	 * @param clazz
	 * @return List
	 */
	public static List<Object> jsonToList(String jsonArrStr, Class<?> clazz) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < jsonArr.size(); i++) {
			list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz));
		}
		return list;
	}

	/**
	 * 把一个json数组串转换成集合，且集合里的对象的属性含有另外实例Bean
	 * 
	 * @param jsonArrStr
	 *            e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
	 * @param clazz
	 *            e.g. MyBean.class
	 * @param classMap
	 *            e.g. classMap.put("data", Person.class)
	 * @return List
	 */
	public static List<Object> jsonToList(String jsonArrStr, Class<?> clazz, Map<?, ?> classMap) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < jsonArr.size(); i++) {
			list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz, classMap));
		}
		return list;
	}

	/**
	 * 把json对象串转换成map对象
	 * 
	 * @param jsonObjStr
	 *            e.g. {'name':'get','int':1,'double',1.1,'null':null}
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map jsonToMap(String jsonObjStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);

		Map map = new HashMap();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

	/**
	 * 把json对象串转换成map对象，且map对象里存放的为其他实体Bean
	 * 
	 * @param jsonObjStr
	 *            e.g. {'data1':{'name':'get'},'data2':{'name':'set'}}
	 * @param clazz
	 *            e.g. Person.class
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map jsonToMap(String jsonObjStr, Class clazz) {
		JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);

		Map map = new HashMap();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, JSONObject.toBean(jsonObject.getJSONObject(key), clazz));
		}
		return map;
	}

	/**
	 * 把json对象串转换成map对象，且map对象里存放的其他实体Bean还含有另外实体Bean
	 * 
	 * @param jsonObjStr
	 *            e.g. {'mybean':{'data':[{'name':'get'}]}}
	 * @param clazz
	 *            e.g. MyBean.class
	 * @param classMap
	 *            e.g. classMap.put("data", Person.class)
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map jsonToMap(String jsonObjStr, Class clazz, Map classMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);
		Map map = new HashMap();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, JSONObject.toBean(jsonObject.getJSONObject(key), clazz, classMap));
		}
		return map;
	}

	/**
	 * 把实体Bean、Map对象、数组、列表集合转换成Json串
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public static String objToJson(Object obj) {
		String jsonStr = null;
		if (obj instanceof Collection || obj instanceof Object[]) {
			jsonStr = JSONArray.fromObject(obj).toString();
		} else {
			jsonStr = JSONObject.fromObject(obj).toString();
		}
		return jsonStr;
	}

	/**
	 * 把json串、数组、集合(collection map)、实体Bean转换成XML XMLSerializer API:
	 * http://json-lib.sourceforge.net/apidocs/net/sf/json/xml/XMLSerializer.html
	 * 具体实例请参考:
	 * http://json-lib.sourceforge.net/xref-test/net/sf/json/xml/TestXMLSerializer_writes.html
	 * http://json-lib.sourceforge.net/xref-test/net/sf/json/xml/TestXMLSerializer_writes.html
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 *             String
	 */
	public static String objToXml(Object obj) {
		XMLSerializer xmlSerial = new XMLSerializer();

		if ((String.class.isInstance(obj) && String.valueOf(obj).startsWith("[")) || obj.getClass().isArray() || Collection.class.isInstance(obj)) {
			JSONArray jsonArr = JSONArray.fromObject(obj);
			return xmlSerial.write(jsonArr);
		} else {
			JSONObject jsonObj = JSONObject.fromObject(obj);
			return xmlSerial.write(jsonObj);
		}
	}

	/**
	 * 从XML转json串
	 * 
	 * @param xml
	 * @return String
	 */
	public static String xmlToJson(String xml) {
		XMLSerializer xmlSerial = new XMLSerializer();
		return String.valueOf(xmlSerial.read(xml));
	}

	/**
	 * 从json串转XML
	 * 
	 * @param json
	 * @return String
	 */
	public static String jsonToXml(String json) {
		try {
			XMLSerializer serializer = new XMLSerializer();
			JSON jsonObject = JSONSerializer.toJSON(json);
			return serializer.write(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String buildArrayList(List<?> list) {
		return JSONArray.fromObject(list).toString();
	}

	// 一维map转Json
	public static String mapToJson(Map<String, Object> map) {
		StringBuilder sbJson = new StringBuilder("{");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() instanceof Date) {
				sbJson.append("\"").append(entry.getKey()).append("\":\"").append(UtilConver.dateToStr((Date) entry.getValue(), Const.fm_yyyyMMdd_HHmmss)).append("\"").append(",");
			} else {
				sbJson.append("\"").append(entry.getKey()).append("\":\"").append(UtilString.isNil(entry.getValue())).append("\"").append(",");
			}
		}
		sbJson.deleteCharAt(sbJson.length() - 1);
		sbJson.append("}");
		return sbJson.toString();
	}

	// 二维List<Map<String,Object>>转Json
	public static String listToJson(List<Map<String, Object>> list) {
		StringBuilder sbJson = new StringBuilder("[");
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			sbJson.append("{");
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() instanceof Date) {
					sbJson.append("\"").append(entry.getKey()).append("\":\"").append(UtilConver.dateToStr((Date) entry.getValue(), Const.fm_yyyyMMdd_HHmmss)).append("\"").append(",");
				} else {
					sbJson.append("\"").append(entry.getKey()).append("\":\"").append(UtilString.isNil(entry.getValue())).append("\"").append(",");
				}
			}
			sbJson.deleteCharAt(sbJson.length() - 1);
			sbJson.append("}");
			if (i < list.size() - 1) {
				sbJson.append(",");
			}
		}
		sbJson.append("]");
		return sbJson.toString();
	}

	public static void main(String[] args) throws ParseException {

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ID", "0");
		map1.put("ICONURL", null);
		map1.put("CREATEDATE", "2014-02-11");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ID", "1");
		map2.put("ICONURL", null);
		map2.put("CREATEDATE", UtilConver.strToDate("20140212", Const.fm_yyyyMMdd));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map1);
		list.add(map2);
		System.out.println(UtilJson.objToJson(list));
		System.out.println(UtilJson.listToJson(list));
		System.out.println(UtilJson.mapToJson(map2));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "true");
		map.put("data", list);
		System.out.println(UtilJson.objToJson(map));

	}
	// @SuppressWarnings("unchecked")
	// public static void main(String[] args) {
	// Map map = new HashMap();
	// map.put("A", "人");
	// map.put("B", "狗");
	// List list = new ArrayList();
	// list.add(map);
	// map = new HashMap();
	// map.put("C", "猪");
	// map.put("D", "猫");
	// list.add(map);
	// System.out.println(objToJson(list));
	// }

	// @SuppressWarnings("unchecked")
	// public static void testListJson() {
	// Map map = new HashMap();
	// map.put("A", "人");
	// map.put("B", "狗");
	// List list = new ArrayList();
	// list.add(map);
	// map = new HashMap();
	// map.put("C", "猪");
	// map.put("D", "猫");
	// list.add(map);
	// JSONArray ja = JSONArray.fromObject(list);
	// System.out.println(ja.toString());
	//
	// Iterator it = map.entrySet().iterator();
	// while (it.hasNext()) {
	// Entry entry = (Entry) it.next();
	// System.out.println(entry.getKey());
	// System.out.println(entry.getValue());
	// }
	//
	// }
	//
	// @SuppressWarnings("unchecked")
	// public static void testJsonMap() {
	// Map map = new HashMap();
	// map.put("A", "人");
	// map.put("B", "狗");
	// List list = new ArrayList();
	// list.add(map);
	// map = new HashMap();
	// map.put("C", "猪");
	// map.put("D", "猫");
	// list.add(map);
	// JSONArray ja = JSONArray.fromObject(list);
	// System.out.println(ja.toString());
	//
	// List<Map> list1 = JSONArray.toList(ja, Map.class);
	// for (int i = 0; i < list1.size(); i++) {
	// @SuppressWarnings("unused")
	// Iterator it = map.entrySet().iterator();
	// while (it.hasNext()) {
	// Entry entry = (Entry) it.next();
	// System.out.println(entry.getKey());
	// System.out.println(entry.getValue());
	// }
	// }
	// }
	//
	// @SuppressWarnings("unchecked")
	// public static void testListBeanJson() {
	// TaskSpace task = new TaskSpace();
	// task.setTaskId(1L);
	// task.setTaskName("空任务");
	// JSONArray ja = JSONArray.fromObject(task);
	// System.out.println(ja.toString());
	//
	// List<TaskSpace> list1 = JSONArray.toList(ja, TaskSpace.class);
	// for (int i = 0; i < list1.size(); i++) {
	// TaskSpace task1 = list1.get(i);
	// System.out.println(task1.getTaskName());
	// }
	//
	// }
	// /**
	// * 设置日期转换格式
	// */
	// static {
	// // 注册器
	// MorpherRegistry mr = JSONUtils.getMorpherRegistry();
	//
	// // 可转换的日期格式，即Json串中可以出现以下格式的日期与时间
	// DateMorpher dm = new DateMorpher(new String[] { "YYYY_MM_DD",
	// "YYYY_MM_DD_HH_MM_ss", "HH_MM_ss", "YYYYMMDD",
	// "YYYYMMDDHHMMSS", "HHMMss" });
	// mr.registerMorpher(dm);
	// }
}
