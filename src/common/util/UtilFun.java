package common.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import common.util.array.UtilArray;
import common.util.conver.UtilConver;
import common.util.string.UtilString;

/**
 * 公共函数
 * 
 * @author fgq 20120815
 * 
 */
public class UtilFun {

	/**
	 * @Description:根据指标获取国家
	 * @param indicator
	 * @param arrCountry
	 * @return String
	 * @date 2014-3-26
	 * @author:fgq
	 */
	public static String getCountry(String indicator, String[] arrCountry) {
		String country = "";
		if ("".equals(UtilString.isNil(indicator)))
			return "";
		if (indicator.length() < 2)
			return indicator;
		if (indicator.length() >= 5) {
			country = indicator.substring(0, 5);
			if (UtilArray.getArrayIndex(arrCountry, country) >= 0) {
				return country;
			}
		}

		if (indicator.length() >= 4) {
			country = indicator.substring(0, 4);
			if (UtilArray.getArrayIndex(arrCountry, country) >= 0) {
				return country;
			}
		}
		if (indicator.length() >= 3) {
			country = indicator.substring(0, 3);
			if (UtilArray.getArrayIndex(arrCountry, country) >= 0) {
				return country;
			}
		}
		if (indicator.length() >= 2) {
			country = indicator.substring(0, 2);
			if (UtilArray.getArrayIndex(arrCountry, country) >= 0) {
				return country;
			}
		}
		return country;
	}

	/**
	 * @Description:当年当月，weekDay 出现 wewkdayCount次数时的日期
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param weekIndex
	 *            第几周
	 * @param weekDay
	 *            当周的星期几 1代表星期天...7代表星期六
	 * @return String
	 * @date Apr 8, 2014
	 * @author:fgq
	 * @throws ParseException
	 */
	public static Date getDate(int year, int month, int weekDayCount, int weekDay) throws ParseException {
		Date rsDate = null;
		Calendar c = Calendar.getInstance();
		String sMonth = "";
		if (month > 9)
			sMonth = "" + month;
		else
			sMonth = "0" + month;
		String sDate = "";
		int day = 1;
		String sDay = "";
		int count = 0;
		while (day <= 31) {
			if (day > 9)
				sDay = "" + day;
			else
				sDay = "0" + day;
			sDate = year + sMonth + sDay;
			Date date = UtilConver.strToDate(sDate, "yyyyMMdd");
			c.setTime(date);
			// System.out.println(Fun.dateToStr(date, "yyyyMMdd"));
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);// 获致是本周的第几天
			if (dayOfWeek == weekDay) {
				count += 1;
			}
			if (count == weekDayCount) {
				rsDate = c.getTime();
				break;
			}
			day += 1;
		}
		return rsDate;
	}

	// 欧洲的夏时制起始时间：3月最后一个周日，结束时间是10月最后一个周日，剩下时间为冬令时;

	// 美国夏令时 (3月第二个周日，结束时间是11月第一个周日，剩下时间为冬令时;)
	public static boolean isAmericanDaylightSaving(Date date) throws ParseException {
		boolean rs = false;
		int year = Integer.valueOf(UtilConver.dateToStr(date, "yyyy"));
		int begin = Integer.valueOf(UtilConver.dateToStr(getDate(year, 3, 2, 1), "yyyyMMdd"));
		int end = Integer.valueOf(UtilConver.dateToStr(getDate(year, 11, 1, 1), "yyyyMMdd"));
		int idate = Integer.valueOf(UtilConver.dateToStr(date, "yyyyMMdd"));
		if (idate >= begin && idate <= end)
			rs = true;
		return rs;
	}

	// 美国冬令时
	public static boolean isAmericanWinterTime(Date date) throws ParseException {
		return !isAmericanDaylightSaving(date);
	}

	/**
	 * @Description:根据国家获取默认货币
	 * @param country
	 * @return String
	 * @date Jul 11, 2014
	 * @author:fgq
	 */
	public static String getSymbol(String country) {
		String rs = "";
		String[] countryArr = new String[] { "美国", "欧元区", "英国", "澳大利亚", "日本", "瑞士", "加拿大" };
		String[] symbolArr = new String[] { "XAUUSD", "EURUSD", "GBPUSD", "AUDUSD", "USDJPY", "USDCHF", "USDCAD" };
		int i = UtilArray.getArrayIndex(countryArr, country);
		if (i >= 0) {
			rs = symbolArr[i];
		}
		return rs;
	}
	
	public static void main(String[] args) {
		String tip="Date=13-7-22 下午10:29 High=1,329.17 Low=1,323.88 Open=1,323.98 Close=1,326.99";
		System.out.println(tip.substring(tip.indexOf("High")));
	}
}
