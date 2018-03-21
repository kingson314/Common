package module.datetype;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import app.AppCon;

import common.util.conver.UtilConver;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;
import consts.VariableApp;

public class HolidayDao {
	// 记录各种日期类型的节假日
	public static ConcurrentHashMap<String, HashSet<String>> mapHoliday = new ConcurrentHashMap<String, HashSet<String>>();
	private static HolidayDao dao;
	private Connection con;
	private String[][] dateTypeArrs;

	public static HolidayDao getInstance() {
		if (dao == null)
			dao = new HolidayDao();
		return dao;
	}

	private HolidayDao() {
		this.con = UtilJDBCManager.getConnection(AppCon.DbconDateType);
		dateTypeArrs = UtilString.splitStr(VariableApp.systemParamsValue.getDateType());
	}

	// 获取某日期类型的节假日 dateType:为英文,即SystemParams.getDateType中的第二维度
	public HashSet<String> getHolidaySet(String dateType) {
		HashSet<String> holidaySet = mapHoliday.get(dateType);
		if (holidaySet == null) {
			holidaySet = fillHolidaySet(dateType);
			holidaySet = mapHoliday.put(dateType, holidaySet);
		}
		return holidaySet;
	}

	// 加载所有的日期类型节假日
	public void loadMapHoliday(String systemParamValueDateType) {
		String[] dateTypeArr = UtilString.splitStr(systemParamValueDateType, 1);
		for (String dateType : dateTypeArr) {
			if (!"".equals(UtilString.isNil(dateType)))
				getHolidaySet(dateType);
		}
		// for (Map.Entry entry : mapHoliday.entrySet()){
		// System.out.println(entry.getKey());
		// }
		// Iterator it = HolidayDao.mapHoliday.get("CHEXCH").iterator();
		// while (it.hasNext()) {
		// System.out.println(it.next().toString());
		//
		// }
	}

	// 填充该日期类型的节假日set
	private HashSet<String> fillHolidaySet(String dateType) {
		HashSet<String> holidayHashSet = new HashSet<String>(365, 0.75f);
		Statement sm = null;
		ResultSet rs = null;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			try {// 非mdb表
				rs = sm.executeQuery("select DATE_FORMAT(T_DATE,'%Y%m%d') T_DATE from t_conf_calendar " + "  where S_CALENDAR_GRP='" + dateType + "' and S_IS_TRADEDAY='N' order by T_DATE desc ");
				while (rs.next()) {
					holidayHashSet.add(rs.getString("t_date"));
				}
			} catch (Exception e) {// mdb
				// dateType:1表示沪深,0表示香港，2表示银行间; flag :1表示为节假日,0表示为交易日
				if ("HK".equalsIgnoreCase(dateType)) {
					rs = sm.executeQuery("select holiday from  " + AppCon.TN_Holidays + "  where dateType=0 and flag=1 order by HOLIDAY desc ");
				} else if ("CHEXCH".equalsIgnoreCase(dateType)) {
					rs = sm.executeQuery("select holiday from  " + AppCon.TN_Holidays + "  where dateType=1 and flag=1 order by HOLIDAY desc ");
				} else if ("CHBANK".equalsIgnoreCase(dateType)) {
					rs = sm.executeQuery("select holiday from  " + AppCon.TN_Holidays + "  where dateType=2 and flag=1 order by HOLIDAY desc ");
				}
				while (rs.next()) {
					holidayHashSet.add(rs.getString("holiday"));
				}
			}
		} catch (Exception e) {
			UtilLog.logError("获取节假日错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return holidayHashSet;
	}

	// 判断交易日
	public synchronized Boolean isTraceDate(String nowDate, String dtType) {
		try {
			if (dtType.equals("全部")) {
				return true;
			} else if (dtType.equals("工作日(周一至周五)")) {
				if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 1 || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 7) {
					return false;
				} else {
					return true;
				}
			} else if (dtType.equals("节假日(周六周日)")) {

				if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 1 || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 7) {
					return true;
				} else {
					return false;
				}
			} else
				for (int i = 0; i < this.dateTypeArrs.length; i++) {
					if (dtType.equals(this.dateTypeArrs[i][0])) {
						HashSet<String> holidaySet = mapHoliday.get(this.dateTypeArrs[i][1]);
						// 如果没有维护相关数据，则返回true
						if (holidaySet == null)
							return true;

						if (dtType.indexOf("交易日") >= 0) {
							if (holidaySet.contains(nowDate)) {
								return false;
							} else {
								return true;
							}
						} else if (dtType.indexOf("节假日") >= 0) {
							if (holidaySet.contains(nowDate)) {
								return true;
							} else {
								return false;
							}
						}
					}
				}
		} catch (Exception e) {
			UtilLog.logError("判断交易日错误:", e);
			return false;
		}
		return false;
	}

	// 判断特殊日期
	public boolean IsSpecialDate(String specialDate, String nowDate, String dateType) {
		boolean IsDate = true;
		try {
			Long nDate = Long.valueOf(nowDate);
			if (specialDate == null) {
				IsDate = true;
			} else if (specialDate.equals("全部") || specialDate.equals("")) {
				IsDate = true;
			} else if (specialDate.equals("本周第一个工作日")) {
				if (!HolidayDao.getInstance().isFirstTraceOfWeek(nDate, dateType)) {
					IsDate = false;
				}
			} else if (specialDate.equals("本周最后一个工作日")) {
				if (!HolidayDao.getInstance().isLastTraceOfWeek(nDate, dateType)) {
					IsDate = false;
				}
			} else if (specialDate.equals("本月第一个工作日")) {
				if (!HolidayDao.getInstance().isFirstTraceOfMonth(nDate, dateType)) {
					IsDate = false;
				}
			} else if (specialDate.equals("本月最后一个工作日")) {
				if (!HolidayDao.getInstance().isLastTraceOfMonth(nDate, dateType)) {
					IsDate = false;
				}
			} else if (specialDate.equals("本季第一个工作日")) {
				if (!HolidayDao.getInstance().isFirstTraceOfSeason(nDate, dateType)) {
					IsDate = false;
				}
			} else if (specialDate.equals("本季最后一个工作日")) {
				if (!HolidayDao.getInstance().isLastTraceOfSeason(nDate, dateType)) {
					IsDate = false;
				}
			} else if (specialDate.equals("本年第一个工作日")) {
				if (!HolidayDao.getInstance().isFirstTraceOfYear(nDate, dateType)) {
					IsDate = false;
				}
			} else if (specialDate.equals("本年最后一个工作日")) {
				if (!HolidayDao.getInstance().isLastTraceOfYear(nDate, dateType)) {
					IsDate = false;
				}
			}
		} catch (Exception e) {
			IsDate = true;
		}
		return IsDate;

	}

	// 获取沪深市场，输入日期前后n天为交易日的日期

	@SuppressWarnings("deprecation")
	public int GetTraceDate(int date, int ndays) {
		int rsDate = date;
		try {

			Date tmpDate = UtilConver.strToDate(String.valueOf(date), Const.fm_yyyyMMdd);
			int cnt = Math.abs(ndays);
			int step = ndays > 0 ? 1 : -1;
			// 沪深 "CHEXCH"
			HashSet<String> holidaySet = mapHoliday.get("CHEXCH");
			if (holidaySet != null) {// 市场交易日类型
				while (cnt > 0) {
					tmpDate.setDate(tmpDate.getDate() + step);
					if (!holidaySet.contains(UtilConver.dateToStr(tmpDate, Const.fm_yyyyMMdd))) {
						--cnt;
					}
				}
				rsDate = Integer.valueOf(UtilConver.dateToStr(tmpDate, Const.fm_yyyyMMdd));
			}
		} catch (Exception e) {
			UtilLog.logError("判断是否为沪深工作日错误:", e);
		}
		return rsDate;
	}

	// 获取输入日期前后n天为交易日的日期

	@SuppressWarnings("deprecation")
	public Long GetTraceDate(Long date, int ndays, String dateType) {
		Long rsDate = date;
		try {
			for (int i = 0; i < this.dateTypeArrs.length; i++) {
				if (dateType.equals(this.dateTypeArrs[i][0])) {
					Date tmpDate = UtilConver.strToDate(String.valueOf(date), Const.fm_yyyyMMdd);
					int cnt = Math.abs(ndays);
					int step = ndays > 0 ? 1 : -1;

					HashSet<String> holidaySet = mapHoliday.get(this.dateTypeArrs[i][1]);
					if (holidaySet != null) {// 市场交易日类型
						while (cnt > 0) {
							tmpDate.setDate(tmpDate.getDate() + step);
							if (!holidaySet.contains(UtilConver.dateToStr(tmpDate, Const.fm_yyyyMMdd))) {
								--cnt;
							}
						}
					} else {// 自然日类型
						Calendar calendar = Calendar.getInstance();
						while (cnt > 0) {
							tmpDate.setDate(tmpDate.getDate() + step);
							calendar.setTime(tmpDate);
							if (!(calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7)) {
								--cnt;
							}
						}
					}
					rsDate = Long.valueOf(UtilConver.dateToStr(tmpDate, Const.fm_yyyyMMdd));
					break;
				}
			}
		} catch (Exception e) {
			UtilLog.logError("判断是否为工作日错误:", e);
		}
		return rsDate;
	}

	// 是否为本周最后一个交易日
	public boolean isLastTraceOfWeek(Long date, String dateType) {
		boolean rs = false;
		long nextTraceDate_l = GetTraceDate(date, 1, dateType);
		if (nextTraceDate_l == 0l)
			return false;
		Date nextTraceDate_d = null;
		try {
			nextTraceDate_d = UtilConver.strToDate(String.valueOf(nextTraceDate_l), Const.fm_yyyyMMdd);
		} catch (ParseException e) {
			return rs;
		}
		Calendar nextTraceDate_c = Calendar.getInstance();
		nextTraceDate_c.setTime(nextTraceDate_d);
		Calendar now_c = Calendar.getInstance();
		try {
			now_c.setTime(UtilConver.strToDate(String.valueOf(date), Const.fm_yyyyMMdd));
		} catch (ParseException e) {
			return false;
		}
		if (nextTraceDate_c.get(Calendar.WEEK_OF_YEAR) != now_c.get(Calendar.WEEK_OF_YEAR))
			rs = true;
		return rs;
	}

	// 是否为本周第一个交易日
	public boolean isFirstTraceOfWeek(Long date, String dateType) {
		boolean rs = false;
		long pirorTraceDate_l = GetTraceDate(date, -1, dateType);
		if (pirorTraceDate_l == 0l)
			return false;
		Date pirorTraceDate_d = null;
		try {
			pirorTraceDate_d = UtilConver.strToDate(String.valueOf(pirorTraceDate_l), Const.fm_yyyyMMdd);
		} catch (ParseException e) {
			return rs;
		}
		Calendar pirorTraceDate_c = Calendar.getInstance();
		pirorTraceDate_c.setTime(pirorTraceDate_d);
		Calendar now_c = Calendar.getInstance();
		try {
			now_c.setTime(UtilConver.strToDate(String.valueOf(date), Const.fm_yyyyMMdd));
		} catch (ParseException e) {
			return false;
		}
		if (pirorTraceDate_c.get(Calendar.WEEK_OF_YEAR) != now_c.get(Calendar.WEEK_OF_YEAR))
			rs = true;
		// System.out.println(rs);
		return rs;
	}

	// 是否为本月最后一个交易日
	public boolean isLastTraceOfMonth(Long date, String dateType) {
		boolean rs = false;
		long nextTraceDate_l = GetTraceDate(date, 1, dateType);
		if (nextTraceDate_l == 0l)
			return false;
		// System.out.println(nextTraceDate_l);
		Date nextTraceDate_d = null;
		try {
			nextTraceDate_d = UtilConver.strToDate(String.valueOf(nextTraceDate_l), Const.fm_yyyyMMdd);
		} catch (ParseException e) {
			return rs;
		}
		Calendar nextTraceDate_c = Calendar.getInstance();
		nextTraceDate_c.setTime(nextTraceDate_d);
		Calendar now_c = Calendar.getInstance();
		try {
			now_c.setTime(UtilConver.strToDate(String.valueOf(date), Const.fm_yyyyMMdd));
		} catch (ParseException e) {
			return false;
		}
		if (nextTraceDate_c.get(Calendar.MONTH) != now_c.get(Calendar.MONTH))
			rs = true;
		return rs;
	}

	// 是否为本月第一个交易日
	public boolean isFirstTraceOfMonth(Long date, String dateType) {
		boolean rs = false;
		long pirorTraceDate_l = GetTraceDate(date, -1, dateType);
		if (pirorTraceDate_l == 0l)
			return false;
		Date pirorTraceDate_d = null;
		try {
			pirorTraceDate_d = UtilConver.strToDate(String.valueOf(pirorTraceDate_l), Const.fm_yyyyMMdd);
		} catch (ParseException e) {
			return rs;
		}
		Calendar pirorTraceDate_c = Calendar.getInstance();
		pirorTraceDate_c.setTime(pirorTraceDate_d);
		Calendar now_c = Calendar.getInstance();
		try {
			now_c.setTime(UtilConver.strToDate(String.valueOf(date), Const.fm_yyyyMMdd));
		} catch (ParseException e) {
			return false;
		}
		if (pirorTraceDate_c.get(Calendar.MONTH) != now_c.get(Calendar.MONTH))
			rs = true;
		return rs;
	}

	// 是否为本季最后一个交易日
	public boolean isLastTraceOfSeason(Long date, String dateType) {
		boolean rs = false;
		long nextTraceDate_l = GetTraceDate(date, 1, dateType);
		if (nextTraceDate_l == 0l)
			return false;
		Date nextTraceDate_d = null;
		try {
			nextTraceDate_d = UtilConver.strToDate(String.valueOf(nextTraceDate_l), Const.fm_yyyyMMdd);
		} catch (ParseException e) {
			return rs;
		}
		Calendar nextTraceDate_c = Calendar.getInstance();
		nextTraceDate_c.setTime(nextTraceDate_d);
		Calendar now_c = Calendar.getInstance();
		try {
			now_c.setTime(UtilConver.strToDate(String.valueOf(date), Const.fm_yyyyMMdd));
		} catch (ParseException e) {
			return false;
		}
		if (getSeason(nextTraceDate_c.get(Calendar.MONTH)) != getSeason(now_c.get(Calendar.MONTH)))
			rs = true;
		return rs;
	}

	// 获取季节
	private int getSeason(int month) {
		int season = 0;
		if (month <= 2)
			season = 1;
		else if (month <= 5)
			season = 2;
		else if (month <= 8)
			season = 3;
		else if (month <= 11)
			season = 4;
		return season;
	}

	// 是否为本季第一个交易日
	public boolean isFirstTraceOfSeason(Long date, String dateType) {
		boolean rs = false;
		long pirorTraceDate_l = GetTraceDate(date, -1, dateType);
		if (pirorTraceDate_l == 0l)
			return false;
		Date pirorTraceDate_d = null;
		try {
			pirorTraceDate_d = UtilConver.strToDate(String.valueOf(pirorTraceDate_l), Const.fm_yyyyMMdd);
		} catch (ParseException e) {
			return rs;
		}
		Calendar pirorTraceDate_c = Calendar.getInstance();
		pirorTraceDate_c.setTime(pirorTraceDate_d);
		Calendar now_c = Calendar.getInstance();
		try {
			now_c.setTime(UtilConver.strToDate(String.valueOf(date), Const.fm_yyyyMMdd));
		} catch (ParseException e) {
			return false;
		}
		if (getSeason(pirorTraceDate_c.get(Calendar.MONTH)) != getSeason(now_c.get(Calendar.MONTH)))
			rs = true;
		return rs;
	}

	// 是否为本年最后一个交易日
	public boolean isLastTraceOfYear(Long date, String dateType) {
		boolean rs = false;
		long nextTraceDate_l = GetTraceDate(date, 1, dateType);
		if (nextTraceDate_l == date)
			return true;
		if (nextTraceDate_l == 0l)
			return false;
		Date nextTraceDate_d = null;
		try {
			nextTraceDate_d = UtilConver.strToDate(String.valueOf(nextTraceDate_l), Const.fm_yyyyMMdd);
		} catch (ParseException e) {
			return rs;
		}
		Calendar nextTraceDate_c = Calendar.getInstance();
		nextTraceDate_c.setTime(nextTraceDate_d);
		Calendar now_c = Calendar.getInstance();
		try {
			now_c.setTime(UtilConver.strToDate(String.valueOf(date), Const.fm_yyyyMMdd));
		} catch (ParseException e) {
			return false;
		}
		if (nextTraceDate_c.get(Calendar.YEAR) != now_c.get(Calendar.YEAR))
			rs = true;
		return rs;
	}

	// 是否为本年第一个交易日
	public boolean isFirstTraceOfYear(Long date, String dateType) {
		boolean rs = false;
		long pirorTraceDate_l = GetTraceDate(date, -1, dateType);
		if (pirorTraceDate_l == 0l)
			return false;
		Date pirorTraceDate_d = null;
		try {
			pirorTraceDate_d = UtilConver.strToDate(String.valueOf(pirorTraceDate_l), Const.fm_yyyyMMdd);
		} catch (ParseException e) {
			return rs;
		}
		Calendar pirorTraceDate_c = Calendar.getInstance();
		pirorTraceDate_c.setTime(pirorTraceDate_d);
		Calendar now_c = Calendar.getInstance();
		try {
			now_c.setTime(UtilConver.strToDate(String.valueOf(date), Const.fm_yyyyMMdd));
		} catch (ParseException e) {
			return false;
		}
		if (pirorTraceDate_c.get(Calendar.YEAR) != now_c.get(Calendar.YEAR))
			rs = true;
		return rs;
	}

	// public static void main(String[] args) {
	// // System.out.println(DateTypeDao.getInstance().GetTraceDate(20120621,
	// // 1,
	// // 1));
	// System.out.println(HolidayDao.getInstance().isFirstTraceOfSeason(
	// 20120702l, 1));
	// }

}
