package common.jfreechart.kline;


import common.util.conver.UtilConver;
import common.util.log.UtilLog;
import consts.Const;

public class BeanKLine implements Cloneable {
	private String dbName;
	private String tableName;// 区分是历史图还是即时图，历史图使用gbpjpy5/gbpjpy60;即时图是用real_gbpjpy5/real_gbpjpy60
	private String symbol;
	private int period;
	private int bars;
	private String sDate;
	private String sTime;
	private int rigthBars;
	private String[] dateList;
	private int leftlBars;
	private String begTime;
	private String endTime;
	private double higtest;
	private double lowest;
	// 模拟或真实行情
	private boolean isSimulate;
	private String endDataSimulate;
	// 是否包含KDJ
	private boolean hasKdj;

	public String getEndDataSimulate() {
		return endDataSimulate;
	}

	public void setEndDataSimulate(String endDataSimulate) {
		this.endDataSimulate = endDataSimulate;
	}

	private String endTimeSimulate;

	public String getEndTimeSimulate() {
		return endTimeSimulate;
	}

	public void setEndTimeSimulate(String endTimeSimulate) {
		this.endTimeSimulate = endTimeSimulate;
	}

	public boolean isSimulate() {
		return isSimulate;
	}

	public void setSimulate(boolean isSimulate) {
		this.isSimulate = isSimulate;
	}

	public static BeanKLine getBean5(String symbol, String dbName) {// 5分钟历史图
		BeanKLine bean5 = new BeanKLine();
		bean5.setDbName(dbName);
		bean5.setTableName("price_" + symbol + "5");
		bean5.setPeriod(5);
		bean5.setSymbol(symbol);
		bean5.setLeftlBars(30);
		bean5.setRigthBars(78);
		bean5.setHasKdj(false);
		return bean5;
	}

	public static BeanKLine getBean60(String symbol, String dbName) {// 60分钟历史图
		BeanKLine bean60 = new BeanKLine();
		bean60.setDbName(dbName);
		bean60.setTableName("price_" + symbol + "60");
		bean60.setPeriod(60);
		bean60.setSymbol(symbol);
		bean60.setLeftlBars(30);
		bean60.setRigthBars(50);
		bean60.setHasKdj(false);
		return bean60;
	}

	public static BeanKLine getRealBean5(String symbol, String dbName) {// 5分钟即时图
		BeanKLine bean5 = new BeanKLine();
		bean5.setDbName(dbName);
		bean5.setTableName("price_" + symbol + "5_real");
		bean5.setPeriod(5);
		bean5.setSymbol(symbol);
		bean5.setLeftlBars(160);
		bean5.setRigthBars(0);
		bean5.setSDate(UtilConver.dateToStr(Const.fm_yyyyMMdd));
		bean5.setSTime(UtilConver.dateToStr(Const.fm_HHmmss));
		bean5.setHasKdj(false);
		return bean5;
	}

	public static BeanKLine getRealBean60(String symbol, String dbName) {// 60分钟即时图
		BeanKLine bean60 = new BeanKLine();
		bean60.setDbName(dbName);
		bean60.setTableName("price_" + symbol + "60_real");
		bean60.setPeriod(60);
		bean60.setSymbol(symbol);
		bean60.setLeftlBars(160);
		bean60.setRigthBars(0);
		bean60.setSDate(UtilConver.dateToStr(Const.fm_yyyyMMdd));
		bean60.setSTime(UtilConver.dateToStr(Const.fm_HHmmss));
		bean60.setHasKdj(false);
		return bean60;
	}

	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			UtilLog.logError("复制对象错误:", e);
		}
		return o;
	};

	public double getHigtest() {
		return higtest;
	}

	public void setHigtest(double higtest) {
		this.higtest = higtest;
	}

	public double getLowest() {
		return lowest;
	}

	public void setLowest(double lowest) {
		this.lowest = lowest;
	}

	public String getBegTime() {
		return begTime;
	}

	public void setBegTime(String begTime) {
		this.begTime = begTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getBars() {
		return bars;
	}

	public void setBars(int bars) {
		this.bars = bars;
	}

	public int getRigthBars() {
		return rigthBars;
	}

	public void setRigthBars(int rigthBars) {
		this.rigthBars = rigthBars;
	}

	public int getLeftlBars() {
		return leftlBars;
	}

	public void setLeftlBars(int leftlBars) {
		this.leftlBars = leftlBars;
	}

	public String getSDate() {
		return sDate;
	}

	public void setSDate(String date) {
		sDate = date;
	}

	public String getSTime() {
		return sTime;
	}

	public void setSTime(String time) {
		sTime = time;
	}

	public String[] getDateList() {
		return dateList;
	}

	public void setDateList(String[] dateList) {
		this.dateList = dateList;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isHasKdj() {
		return hasKdj;
	}

	public void setHasKdj(boolean hasKdj) {
		this.hasKdj = hasKdj;
	}

	public static void main(String[] args) {
		// String tip = "--> Date=14-3-2 上午3:1 High";
		// String dateTime = tip.substring(tip.indexOf("=") + 1);
		// String date = dateTime.substring(0, dateTime.indexOf(" "));
		// String[] dateArr = date.split("-");
		// if (dateArr[1].length() == 1)
		// dateArr[1] = "0" + dateArr[1];
		// if (dateArr[2].length() == 1)
		// dateArr[2] = "0" + dateArr[2];
		// date = "20" + dateArr[0] + "." + dateArr[1] + "." + dateArr[2];
		// System.out.println(date);
		// String timeTmp = dateTime.substring(dateTime.indexOf(" ") + 1,
		// dateTime.indexOf("High"));
		// String noon = dateTime.substring(dateTime.indexOf(" ") + 1,
		// dateTime.indexOf(" ") + 3);
		// String hour = dateTime.substring(dateTime.indexOf(noon) + 2,
		// dateTime.indexOf(":"));
		// String minute = timeTmp.substring(timeTmp.indexOf(":"));
		// if (noon.equals("下午")) {
		// hour = String.valueOf(Integer.valueOf(hour) + 12);
		// }
		// String time = hour + minute;
		// String[] timeArr = time.split(":");
		// if (timeArr[0].trim().length() == 1)
		// timeArr[0] = "0" + timeArr[0].trim();
		// if (timeArr[1].trim().length() == 1)
		// timeArr[1] = "0" + timeArr[1].trim();
		// time = timeArr[0] + ":" + timeArr[1];
		// String time="1234";
		// System.out.println(time.substring(2,4));
		System.out.println(1 % 5);
	}

}
