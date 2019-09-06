package common.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import common.util.string.UtilString;

/**
 * @Description:
 * @date May 9, 2014
 * @author:fgq
 */
public class UtilCollection {
	public static <T> T[] concatAll(T[] arg0, T[]... argn) {
		int totalLength = arg0.length;
		for (T[] array : argn) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(arg0, totalLength);
		int offset = arg0.length;
		for (T[] array : argn) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	// 获取数组索引
	public static int getListIndex(List<String> list, String item) {
		for (int i = 0; i < list.size(); i++) {
			if (UtilString.isNil(item).equalsIgnoreCase(list.get(i)))
				return i;
		}
		return -1;
	}

	// 判断null对象返回空值
	public static String isNilMap(Map<?, ?> map, String key) {
		if (map == null)
			return "";
		else if (map.get(key) == null)
			return "";
		return map.get(key).toString().trim();
	}

	// 判断null对象返回空值
	public static Object isNilMapIgnoreCase(Map<?, ?> map, String key) {
		if (map == null)
			return "";
		else if (map.get(key) == null) {
			if (map.get(key.toUpperCase()) == null) {
				return "";
			} else {
				return map.get(key.toUpperCase());
			}
		}
		return map.get(key);
	}

	// 判断null对象返回空值
	public static String isNilMap(Map<?, ?> map, String key, String defaultValue) {
		if (map == null)
			return defaultValue;
		else if (map.get(key) == null)
			return defaultValue;
		return map.get(key).toString();
	}

	/**
	 * @param map <String,String
	 * @return 对象元素map
	 * @date 2013-01-22
	 */
	public static Map<String, Object> getObjectMap(Map<String, String> map) {
		Map<String, Object> mapRs = new HashMap<String, Object>();
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<?, ?> entry = (Entry<?, ?>) it.next();
			mapRs.put(entry.getKey().toString(), (Object) entry.getValue());
		}
		return mapRs;
	}

	public static Map<String, Object> getMap(Object... keyVal) {
		Map<String, Object> map = new HashMap<String, Object>();
		String key = "";
		Object value = null;
		for (int i = 0; i < keyVal.length; i++) {
			if (i % 2 == 0) {
				key = String.valueOf(keyVal[i]);
			} else {
				value = keyVal[i];
				map.put(key, value);
			}
		}
		return map;
	}

	public static List<Object> getList(Object... obj) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < obj.length; i++) {
			list.add(obj[i]);
		}
		return list;
	}

	/**
	 * @param map <String,String>
	 * @return 对象元素map
	 * @date 2013-01-22
	 */
	public static Map<String, String> getStringMap(Map<String, Object> map) {
		Map<String, String> mapRs = new HashMap<String, String>();
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<?, ?> entry = (Entry<?, ?>) it.next();
			mapRs.put(entry.getKey().toString(), entry.getValue().toString());
		}
		return mapRs;
	}

	public static Map<String, Object> cloneMap(Map<String, Object> map) {
		Map<String, Object> mapRs = new HashMap<String, Object>();
		mapRs.putAll(map);
		return mapRs;
	}

	public static void main(String[] args) {
		Map<String, Object> map = getMap(1, "a", 2, "b");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "/" + String.valueOf(entry.getValue()));
		}
	}

	/*** Array ***/
	// 获取数组索引
	public static int getArrayIndex(String[] arr, String item) {
		for (int i = 0; i < arr.length; i++) {
			if (UtilString.isNil(item).equalsIgnoreCase(arr[i]))
				return i;
		}
		return -1;
	}

	// 获取数组索引
	public static int getArrayIndex(Integer[] arr, Integer item) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == item)
				return i;
		}
		return -1;
	}

	// 获取数组索引
	public static int getArrayIndex(int[] arr, int item) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == item)
				return i;
		}
		return -1;
	}

	// 交换位置
	private static void swap(int[] array, int i, int j) {
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}// 冒泡排序

	public static int[] bubbleOrder(int[] array, int method) {
		for (int i = 0; i < array.length; i++) {
			for (int j = array.length - 1; j > i; j--) {
				if (method == 2) {
					if (array[i] < array[j])
						swap(array, i, j);
				} else if (method == 1) {
					if (array[i] > array[j]) {
						swap(array, i, j);
					}
				}
			}
		}
		return array;
	}

	// 获取执行时段数组
	public static String[][] getArray2(String execTime) {
		String[][] etime = null;
		if (execTime == null)
			return null;
		if (execTime.trim().length() < 1 || execTime.equalsIgnoreCase("全部"))
			return null;
		String[] execTimeArray = execTime.split(";");
		int len = execTimeArray.length;

		etime = new String[len][2];
		for (int i = 0; i < len; i++) {
			execTimeArray[i] = execTimeArray[i].replace("[", "");
			execTimeArray[i] = execTimeArray[i].replace("]", "").trim();
			String[] p = execTimeArray[i].split(",");
			if (p.length > 0)
				etime[i][0] = p[0];
			else
				etime[i][0] = "";
			if (p.length > 1)
				etime[i][1] = p[1];
			else
				etime[i][1] = "";
		}
		return etime;
	}
}
