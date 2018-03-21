package common.util.Math;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JRadioButton;

import common.util.log.UtilLog;

public class UtilMath {
	/**
	 * @Description:
	 * @date Jan 27, 2014
	 * @author:fgq
	 */

	// 0、1取反
	public synchronized static int oppositeVaule(int oldValue) {
		int rs = 0;
		try {
			if (oldValue == 0)
				rs = 1;
			else
				rs = 0;
		} catch (Exception e) {
			UtilLog.logError("01取反错误:", e);
			return rs;
		}
		return rs;

	}

	// 布尔取反
	public static void oppositeVaule(JRadioButton r1, JRadioButton r2) {
		if (r1.isSelected())
			r2.setSelected(false);
		else
			r2.setSelected(true);
	}

	// 数字比较
	public static boolean compare(String compareType, long value1, long value2) {
		boolean rs = false;
		if (compareType.equals("大于等于")) {
			if (value1 >= value2) {
				rs = true;
			}
		} else if (compareType.equals("大于")) {
			if (value1 > value2) {
				rs = true;
			}
		} else if (compareType.equals("等于")) {
			if (value1 == value2) {
				rs = true;
			}
		} else if (compareType.equals("不等于")) {
			if (value1 != value2) {
				rs = true;
			}
		} else if (compareType.equals("小于等于")) {
			if (value1 <= value2) {
				rs = true;
			}
		} else if (compareType.equals("小于")) {
			if (value1 < value2) {
				rs = true;
			}
		}
		return rs;
	}

	/**
	 * 
	 * @param 浮点数
	 * @return 浮点数的小数位数
	 * @date 2013-01-21
	 */
	public static int getDigits(double value) {
		int digits = 0;
		String sValue = String.valueOf(value);
		digits = sValue.substring(sValue.indexOf(".") + 1).length();
		return digits;
	}

	/**
	 * @Description:保留n为小数，4舍5入
	 * @param number
	 * @param index
	 * @return double
	 * @date May 26, 2014
	 * @author:fgq
	 */
	public static double round(double number, int index) {
		double result = 0;
		double temp = Math.pow(10, index);
		result = Math.round(number * temp) / temp;
		return result;
	}

	
	//获取range之内的获取count个随机数
	public static List<Integer> getRandom(int range,int count){
		List<Integer> list=new ArrayList<Integer>();
		while(list.size()<count){
			int number = new Random().nextInt(range) + 1;
			if(!list.contains(number)){
				list.add(number);
			}
		}
//		Collections.sort(list);  
		return list;
	}
	//获取1 到 end 随机数，保留两位小数，用于红包
	public static double getRandomValue(int end){
		if(end==1){
			return 1.0;
		}
		end-=1;
		double number = 1+0.01*(new Random().nextInt(end*100) +1);
		BigDecimal   bigDecimal   =   new   BigDecimal(number);
		 number   =   bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return number;
	}
	public static void main(String[] p) {
		 
		System.out.println(getRandomValue(10));
	}
}
