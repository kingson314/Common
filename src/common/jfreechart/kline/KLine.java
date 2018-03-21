package common.jfreechart.kline;

/**
 * @msg:K
 * @date:2012-06-01
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.ui.RectangleInsets;

import common.component.SSplitPane;
import common.component.STabbedPane;
import common.util.Math.UtilMath;
import common.util.conver.UtilConver;
import common.util.date.UtilDate;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.string.UtilString;
import config.symbol.SymbolDao;
import consts.Const;
import consts.ImageContext;

public class KLine {
	private static KLine kline = null;
	private SSplitPane splt;
	private String DateType = "DATELOCAL";// "DATESERVER";
	private String TimeType = "TIMELOCAL";// "TIMESERVER"
	 private Color ColorBackgroundChart=Color.black;
	 private Color ColorUpPaint=Color.black;//阳线内部颜色
	 private Color ColorDownPaint=Color.white;//阴线内部颜色
	 private Color ColorBaseOutlinePaint=Color.green;//K线边框颜色
	 private Color ColorBackgroundPaintPlot=Color.black;//画板背景颜色
	 private Color ColorRangeGridlinePaintPlot=Color.black;
	 private Color ColorDomainGridlinePaintPlot=Color.black;

//	private static Color ColorBackgroundChart = Color.white;
//	private static Color ColorUpPaint = Color.white;//new Color(193, 255, 193);//阳线内部颜色
//	private static Color ColorDownPaint = Color.white;// 阴线内部颜色
//	private static Color ColorBaseOutlinePaint = new Color(0, 205, 0);// K线边框颜色
//	private static Color ColorBackgroundPaintPlot = Color.white;// 画板背景颜色
//	private static Color ColorRangeGridlinePaintPlot = Color.white;
//	private static Color ColorDomainGridlinePaintPlot = Color.white;

	public static KLine getInstance() {
		if (kline == null)
			kline = new KLine();
		return kline;
	}

	private KLine() {
	}

	/**
	 * @Description: 返回Tab多页图
	 * @param beanArr
	 * @return STabbedPane
	 * @date Apr 9, 2014
	 * @author:fgq
	 */
	public STabbedPane getTabKline(BeanKLine[] beanArr) {
		String[] symbolArr = new String[beanArr.length];
		STabbedPane tb = new STabbedPane();
		for (int i = 0; i < beanArr.length; i++) {
			symbolArr[i] = beanArr[i].getSymbol();
			tb.addTab(beanArr[i].getSymbol(), ImageContext.TabTask, getChartPanel(beanArr[i]), beanArr[i].getSymbol(), false);
		}
		tb.setPersistTab(symbolArr);
		return tb;
	}

	/**
	 * @Description:返回60分钟与5分钟组合分割图
	 * @param bean5
	 * @param bean60
	 * @return SSplitPane
	 * @date Apr 9, 2014
	 * @author:fgq
	 */
	public SSplitPane getCombinationKline(String dbName, String symbol, String date, String time) {
		BeanKLine bean60 = BeanKLine.getBean60(symbol, dbName);
		BeanKLine bean5 = BeanKLine.getBean5(symbol, dbName);
		bean60.setSDate(date);
		bean60.setSTime(time);
		bean5.setSDate(date);
		bean5.setSTime(time);

		SSplitPane splt = new SSplitPane(1, 0.5, false);
		splt.setDividerSize(1);
		this.splt = splt;
		String tabName5 = bean5.getSymbol() + bean5.getPeriod();
		String tabName60 = bean60.getSymbol() + bean60.getPeriod();
		STabbedPane tb5 = new STabbedPane(new String[] { tabName5 });
		STabbedPane tb60 = new STabbedPane(new String[] { tabName60 });

		ChartPanel chartPanel5 = getChartPanel(bean5);
		ChartPanel chartPanel60 = getChartPanel(bean60);

		tb5.addTab(tabName5, ImageContext.TabTask, chartPanel5, tabName5, false);
		tb60.addTab(tabName60, ImageContext.TabTask, chartPanel60, tabName60, false);

		splt.add(tb5, SSplitPane.RIGHT);
		splt.add(tb60, SSplitPane.LEFT);
		return splt;
	}

	/**
	 * @Description:返回60分钟与5分钟组合分割图
	 * @param bean5
	 * @param bean60
	 * @return SSplitPane
	 * @date Apr 9, 2014
	 * @author:fgq
	 */
	public SSplitPane getCombinationKline(BeanKLine bean5, BeanKLine bean60) {
		SSplitPane splt = new SSplitPane(1, 0.5, false);
		splt.setDividerSize(1);
		this.splt = splt;
		String tabName5 = bean5.getSymbol() + bean5.getPeriod();
		String tabName60 = bean60.getSymbol() + bean60.getPeriod();
		STabbedPane tb5 = new STabbedPane(new String[] { tabName5 });
		STabbedPane tb60 = new STabbedPane(new String[] { tabName60 });

		ChartPanel chartPanel5 = getChartPanel(bean5);
		ChartPanel chartPanel60 = getChartPanel(bean60);

		tb5.addTab(tabName5, ImageContext.TabTask, chartPanel5, tabName5, false);
		tb60.addTab(tabName60, ImageContext.TabTask, chartPanel60, tabName60, false);

		splt.add(tb5, SSplitPane.RIGHT);
		splt.add(tb60, SSplitPane.LEFT);
		return splt;
	}

	private void setKline5(BeanKLine bean5) {
		STabbedPane tb5 = new STabbedPane(new String[] { bean5.getSymbol() });
		ChartPanel chartPanel5 = getChartPanel(bean5);
		tb5.addTab(bean5.getSymbol(), ImageContext.TabTask, chartPanel5, bean5.getSymbol(), false);
		splt.add(tb5, SSplitPane.RIGHT);
		splt.setDividerLocation(0.5);
	}

	/**
	 * @Description:获取单个ChartPanel
	 * @param bean
	 * @return ChartPanel
	 * @date Apr 9, 2014
	 * @author:fgq
	 */
	public ChartPanel getChartPanel(final BeanKLine bean) {
		SeriesGroup sg = getSeriesGroup(bean);
		TimeSeriesCollectionGroup tscg = new TimeSeriesCollectionGroup();
		tscg.addSeries(sg);//
		final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();//
		seriesCollection.addSeries(sg.kline);
		seriesCollection.setXPosition(TimePeriodAnchor.MIDDLE);
		final CandlestickRenderer candlestickRender = getCandlestickRenderer();
		NumberAxis y1Axis = getNumberAxis(bean);
		DateAxis axis = getDateAxis(bean.getBegTime(), bean.getEndTime());
		XYPlot[] xyplot = null;
		if (bean.isHasKdj()) {
			xyplot = new XYPlot[2];
			xyplot[0] = getXYPlot(seriesCollection, axis, y1Axis, candlestickRender);
			tscg.setMainPlot(xyplot[0]);//
			xyplot[1] = getXYPlot_Kdj(tscg, axis);
		} else {
			xyplot = new XYPlot[1];
			xyplot[0] = getXYPlot(seriesCollection, axis, y1Axis, candlestickRender);
			tscg.setMainPlot(xyplot[0]);//
		}
		CombinedDomainXYPlot combinedDomainXYPlot = getCombinedDomainXYPlot(axis, xyplot);
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, combinedDomainXYPlot, false);
		chart.setAntiAlias(true);
		chart.setBackgroundPaint(ColorBackgroundChart);
		final ChartPanel chartPanel = new ChartPanel(chart);
		final JLabel lDateTime = new JLabel();
		final JLabel lDiffHighLow = new JLabel();
		chartPanel.setName(String.valueOf(bean.getPeriod()));
		chartPanel.setMouseZoomable(false);
		chartPanel.setAutoscrolls(true);
		chartPanel.setDisplayToolTips(true);
		chartPanel.setDoubleBuffered(true);
		chartPanel.setFocusable(true);
		chartPanel.setInheritsPopupMenu(true);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setRefreshBuffer(true);
		chartPanel.setRequestFocusEnabled(true);
		chartPanel.setVerifyInputWhenFocusTarget(true);
		chartPanel.addChartMouseListener(new ChartMouseListener() {
			public void chartMouseClicked(ChartMouseEvent e) {
				if (e.getTrigger().getClickCount() == 2) {
					if (chartPanel.getHorizontalAxisTrace()) {
						chartPanel.setHorizontalAxisTrace(false); //
					} else {
						chartPanel.setHorizontalAxisTrace(true); //
					}
				}
				if (bean.getPeriod() == 60) {
					if (e.getEntity().toString().indexOf("null") >= 0)
						return;
					XYItemEntity xyItemEntity = (XYItemEntity) e.getEntity();
					if (xyItemEntity == null)
						return;

					String tip = UtilString.isNil(xyItemEntity.getToolTipText());
					if (tip.indexOf("=") < 0)
						return;
					String dateTime = tip.substring(tip.indexOf("=") + 1);
					String date = dateTime.substring(0, dateTime.indexOf(" "));
					String[] dateArr = date.split("-");
					if (dateArr[1].length() == 1)
						dateArr[1] = "0" + dateArr[1];
					if (dateArr[2].length() == 1)
						dateArr[2] = "0" + dateArr[2];
					date = "20" + dateArr[0] + "." + dateArr[1] + "." + dateArr[2];
					String timeTmp = dateTime.substring(dateTime.indexOf(" ") + 1, dateTime.indexOf("High"));
					String noon = dateTime.substring(dateTime.indexOf(" ") + 1, dateTime.indexOf(" ") + 3);
					String hour = dateTime.substring(dateTime.indexOf(noon) + 2, dateTime.indexOf(":"));
					String minute = timeTmp.substring(timeTmp.indexOf(":"));
					if (noon.equals("下午")) {
						hour = String.valueOf(Integer.valueOf(hour) + 12);
					}
					String time = hour + minute;
					String[] timeArr = time.split(":");
					if (timeArr[0].trim().length() == 1)
						timeArr[0] = "0" + timeArr[0].trim();
					if (timeArr[1].trim().length() == 1)
						timeArr[1] = "0" + timeArr[1].trim();
					time = timeArr[0].trim() + ":" + timeArr[1].trim() + ":00";

					BeanKLine bean5 = BeanKLine.getBean5(bean.getSymbol().replace(String.valueOf(bean.getPeriod()), "") + "5", bean.getDbName());
					bean5.setSDate(date);
					bean5.setSTime(time);
					bean5.setSymbol(bean5.getSymbol() + date + " " + time);
					// chartPanel5
					if (splt != null) {
						splt.removeByName(String.valueOf(bean5.getPeriod()));
						setKline5(bean5);
					}
				}
			}

			public void chartMouseMoved(ChartMouseEvent e) {
				if (e.getEntity().toString().indexOf("null") >= 0)
					return;
				XYItemEntity xyItemEntity = (XYItemEntity) e.getEntity();
				if (xyItemEntity == null)
					return;
				String tip = UtilString.isNil(xyItemEntity.getToolTipText());
				if (tip.indexOf("=") < 0)
					return;
				String dateTime = tip.substring(tip.indexOf("=") + 1);
				String date = dateTime.substring(0, dateTime.indexOf(" "));
				String[] dateArr = date.split("-");
				if (dateArr[1].length() == 1)
					dateArr[1] = "0" + dateArr[1];
				if (dateArr[2].length() == 1)
					dateArr[2] = "0" + dateArr[2];
				date = "20" + dateArr[0] + "." + dateArr[1] + "." + dateArr[2];
				String timeTmp = dateTime.substring(dateTime.indexOf(" ") + 1, dateTime.indexOf("High"));
				String noon = dateTime.substring(dateTime.indexOf(" ") + 1, dateTime.indexOf(" ") + 3);
				String hour = dateTime.substring(dateTime.indexOf(noon) + 2, dateTime.indexOf(":"));
				String minute = timeTmp.substring(timeTmp.indexOf(":"));
				if (noon.equals("下午")) {
					hour = String.valueOf(Integer.valueOf(hour) + 12);
				}
				String time = hour + minute;
				String[] timeArr = time.split(":");
				if (timeArr[0].trim().length() == 1)
					timeArr[0] = "0" + timeArr[0].trim();
				if (timeArr[1].trim().length() == 1)
					timeArr[1] = "0" + timeArr[1].trim();
				time = timeArr[0].trim() + ":" + timeArr[1].trim() + ":00";
				lDateTime.setText(date + " " + time);
				lDateTime.setBounds(e.getTrigger().getX(), e.getTrigger().getY(), 200, 19);

				String[] priceArr = tip.substring(tip.indexOf("High")).replace(",", "").split(" ");
				double high = 0.0;
				double low = 0.0;
				double open = 0.0;
				double close = 0.0;
				for (String price : priceArr) {
					if (price.indexOf("High") >= 0) {
						high = Double.valueOf(price.substring(price.indexOf("=") + 1));
					} else if (price.indexOf("Low") >= 0) {
						low = Double.valueOf(price.substring(price.indexOf("=") + 1));
					} else if (price.indexOf("Open") >= 0) {
						open = Double.valueOf(price.substring(price.indexOf("=") + 1));
					} else if (price.indexOf("Close") >= 0) {
						close = Double.valueOf(price.substring(price.indexOf("=") + 1));
					}
				}
				String highLow = getValueDiff(bean.getSymbol(), high, low);
				lDiffHighLow.setText(highLow);
				lDiffHighLow.setBounds(e.getTrigger().getX(), e.getTrigger().getY(), 50, 19);

				xyItemEntity.setToolTipText(highLow + "  [" + date + " " + time + "] H[" + high + "] L[" + low + "] O[" + open + "] C[" + close + "]");
				if (splt != null) {
					splt.setDividerLocation(0.5);
				}
			}
		});
		lDateTime.setBackground(Color.black);
		lDateTime.setForeground(Color.red);
		chartPanel.add(lDateTime);
		lDiffHighLow.setBackground(Color.black);
		lDiffHighLow.setForeground(Color.white);
		chartPanel.add(lDiffHighLow);
		return chartPanel;
	}

	private String getValueDiff(String symbol, double high, double low) {
		try{
			int digits = SymbolDao.getInstance().getMapSymbol().get(symbol.toUpperCase()).getDigits();
			double valueDiff = UtilMath.round((high - low) * Math.pow(10, digits - 1), 2);// 减掉一位，貌似是券商精确了多一位
			return String.valueOf(valueDiff);
		}catch(Exception e){
			return null;
		}
	}

	// 模拟时获取最新价格sql
	private String getNewestPrice(Connection con, BeanKLine bean, String endDate, String endTime) throws Exception {
		StringBuilder sbSqlDetail = new StringBuilder();
		sbSqlDetail.append("select  max(").append(TimeType).append(")  from ").append(bean.getTableName()).append("_Detail").append(" where ").append(DateType).append("='")
				.append(endDate).append("' and concat(").append(DateType).append(",' ',").append(TimeType).append(")<='").append(endTime).append("'");

		String maxEndTime = UtilSql.QueryForMax(con, sbSqlDetail.toString(), new Object[] {});
		sbSqlDetail.delete(0, sbSqlDetail.length());
		sbSqlDetail.append("select distinct  substr(").append(DateType).append(",1,4)year,substr(").append(DateType).append(",5,2)month,substr(").append(DateType).append(
				",7,2)day,").append(" substr(").append(TimeType).append(",1,2)hour,substr(").append(TimeType).append(",3,2)minute,substr(").append(TimeType)
				.append(",5,2)second, ").append(" open,close,high,low,").append(DateType).append(",").append(TimeType).append(",ma5,ma20,ma60,kdj from ").append(
						bean.getTableName()).append("_Detail").append(" where ").append(DateType).append("='").append(endDate).append("' and concat(").append(DateType).append(",' ',")
				.append(TimeType).append(")='").append(endDate + " " + maxEndTime).append("'");
		return sbSqlDetail.toString();
	}

	//
	private SeriesGroup getSeriesGroup(BeanKLine bean) {
		java.sql.Statement sm = null;
		ResultSet rs = null;
		Connection con = null;
		SeriesGroup sg = new SeriesGroup();
		try {
			con = UtilJDBCManager.getConnection(bean.getDbName());
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			StringBuilder sbSql = new StringBuilder();
			String dateTime = bean.getSDate() + " " + bean.getSTime();

			String begTime = UtilDate.addDateMinut(dateTime, -bean.getPeriod() * bean.getLeftlBars(), Const.fm_yyyyMMdd_HHmmss);
			String endTime = UtilDate.addDateMinut(dateTime, bean.getPeriod() * bean.getRigthBars(), Const.fm_yyyyMMdd_HHmmss);
			String begDate = UtilConver.dateToStr(UtilConver.strToDate(begTime, Const.fm_yyyyMMdd_HHmmss), Const.fm_yyyyMMdd);
			String endDate = UtilConver.dateToStr(UtilConver.strToDate(endTime, Const.fm_yyyyMMdd_HHmmss), Const.fm_yyyyMMdd);
			if (bean.isSimulate()) {
				sbSql.append(getNewestPrice(con, bean, endDate, endTime)).append(" union all ");
			}

			sbSql.append("select  substr(").append(DateType).append(",1,4)year,substr(").append(DateType).append(",5,2)month,substr(").append(DateType).append(",7,2)day,").append(
					" substr(").append(TimeType).append(",1,2)hour,substr(").append(TimeType).append(",3,2)minute,substr(").append(TimeType).append(",5,2)second, ").append(
					" open,close,high,low,").append(DateType).append(",").append(TimeType).append(",ma5,ma20,ma60,kdj from ").append(bean.getTableName()).append(" where ").append(
					DateType).append(" >= '").append(begDate).append("' and ").append(DateType).append("<='").append(endDate).append("' and concat(").append(DateType).append(",' ',")
					.append(TimeType).append(") >= '").append(begTime).append("' and  concat(").append(DateType).append(",' ',").append(TimeType).append(")<='").append(endTime).append(
							"' order by  ").append(DateType).append("  desc,").append(TimeType).append(" desc  ");

			System.out.println(sbSql.toString());

			rs = sm.executeQuery(sbSql.toString());
			String year = "0";
			String month = "0";
			String day = "0";
			String hour = "0";
			String minute = "0";
			String nowDateTime = "";
			rs.last();
			rs.beforeFirst();
			double beforeLow = 0;
			double lowest = 999999999;
			double highest = 0;
			while (rs.next()) {
				lowest = Math.min(lowest, rs.getDouble("low"));
				highest = Math.max(highest, rs.getDouble("high"));
				nowDateTime = rs.getString(DateType) + " " + rs.getString(TimeType);
				year = rs.getString("year");
				month = rs.getString("month");
				day = rs.getString("day");
				hour = rs.getString("hour");
				minute = rs.getString("minute");
				if (rs.isFirst()) {
					bean.setEndTime(year + month + day + " " + hour + ":" + minute);
				}
				if (rs.isLast()) {
					bean.setBegTime(year + month + day + " " + hour + ":" + minute);
				}

				Day d = new Day(Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year));
				Hour h = new Hour(Integer.valueOf(hour), d);
				RegularTimePeriod regularTimePeriod = null;

				if (bean.getPeriod() >= 60) {
					regularTimePeriod = h;
				} else if (bean.getPeriod() < 60) {
					if (bean.isSimulate()) {
						minute = Integer.valueOf(minute) + bean.getPeriod() - Integer.valueOf(minute) % bean.getPeriod() + "";
					}
					Minute m = new Minute(Integer.valueOf(minute), h);
					regularTimePeriod = m;
				}
				try {
					sg.kline.add(regularTimePeriod, rs.getDouble("open"), rs.getDouble("high"), rs.getDouble("low"), rs.getDouble("close"));
				} catch (Exception e) {
					continue;
				}
				sg.ma5.add(regularTimePeriod, rs.getDouble("ma5"));
				sg.ma20.add(regularTimePeriod, rs.getDouble("ma20"));
				sg.ma60.add(regularTimePeriod, rs.getDouble("ma60"));
				sg.kdj.add(regularTimePeriod, rs.getDouble("kdj"));
				if (nowDateTime.compareToIgnoreCase(dateTime) <= 0) {
					if (beforeLow == 0) {
						beforeLow = rs.getDouble("low");
					}
					sg.dividingLine.addOrUpdate(regularTimePeriod, beforeLow);
				}
			}
			bean.setHigtest(highest);
			bean.setLowest(lowest);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			UtilSql.close(rs, sm, con);
		}
		return sg;
	}

	private CandlestickRenderer getCandlestickRenderer() {
		CandlestickRenderer candlestickRender = new CandlestickRenderer();//
		candlestickRender.setUseOutlinePaint(true); //
		candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//
		candlestickRender.setAutoWidthFactor(0.2);// K
		candlestickRender.setUpPaint(ColorUpPaint);// 阳线
		candlestickRender.setDownPaint(ColorDownPaint);// 引线
		candlestickRender.setBaseOutlinePaint(ColorBaseOutlinePaint); //
		return candlestickRender;
	}

	private DateAxis getDateAxis(String begTime, String endTime) {
		Font xfont = new Font("宋体", Font.CENTER_BASELINE, 9);//
		DateAxis xAxis = new DateAxis();//
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmm");//
		xAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//
		xAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//
		xAxis.setDateFormatOverride(dateFormat);//
		xAxis.setAutoTickUnitSelection(true, true);//
		xAxis.setAutoRange(true);
		xAxis.setTickLabelFont(xfont);
		xAxis.setTickLabelPaint(Color.white);
		xAxis.setAxisLinePaint(Color.white);
		xAxis.setTickMarkPaint(Color.white);
		return xAxis;
	}

	private NumberAxis getNumberAxis(BeanKLine bean) {
		Font yfont = new Font("宋体", Font.CENTER_BASELINE, 9);// Y
		NumberAxis yAxis = new NumberAxis();//
		yAxis.setAutoRange(false);//
		if (bean.getLowest() < bean.getHigtest()) {
			double unit = (bean.getHigtest() - bean.getLowest()) / 50;
			yAxis.setRange(bean.getLowest() - unit, bean.getHigtest() + unit);//
		} else {
			yAxis.setRange(0, 0);//
		}
		yAxis.setTickUnit(new NumberTickUnit((bean.getHigtest() - bean.getLowest()) / 10));//
		yAxis.setLabelFont(yfont);
		yAxis.setTickLabelFont(yfont);
		yAxis.setTickLabelPaint(Color.white);
		yAxis.setAxisLinePaint(Color.white);
		yAxis.setTickMarkPaint(Color.white);
		return yAxis;
	}

	private XYPlot getXYPlot(OHLCSeriesCollection seriesCollection, DateAxis x1Axis, NumberAxis y1Axis, CandlestickRenderer candlestickRender) {
		XYPlot plot = new XYPlot(seriesCollection, x1Axis, y1Axis, candlestickRender); //
		plot.setBackgroundPaint(ColorBackgroundPaintPlot);
		plot.setRangeGridlinePaint(ColorRangeGridlinePaintPlot);
		plot.setDomainGridlinePaint(ColorDomainGridlinePaintPlot);
		// plot.setRangeGridlinesVisible(true);
		// plot.setDomainGridlinesVisible(true);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT); // Y
		plot.setForegroundAlpha(0.9f);
		plot.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
		return plot;
	}

	private XYPlot getXYPlot_Kdj(TimeSeriesCollectionGroup tscg, DateAxis x1Axis) {
		Font yfont = new Font("宋体", Font.CENTER_BASELINE, 9);// Y
		NumberAxis yAxis = new NumberAxis();// 
		yAxis.setAutoRange(false);//
		yAxis.setRange(0, 100);//
		yAxis.setTickUnit(new NumberTickUnit(10));//
		yAxis.setLabelFont(yfont);
		yAxis.setTickLabelFont(yfont);
		yAxis.setTickLabelPaint(Color.white);
		yAxis.setAxisLinePaint(Color.white);
		yAxis.setTickMarkPaint(Color.white);
		XYPlot plot = new XYPlot(tscg.tsKdj, null, yAxis, null);
		plot.setRenderer(0, tscg.xylineKdj);// kdj
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		plot.setBackgroundPaint(ColorBackgroundPaintPlot);
		plot.setRangeGridlinePaint(ColorRangeGridlinePaintPlot);
		plot.setDomainGridlinePaint(ColorDomainGridlinePaintPlot);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT); // Y
		plot.setForegroundAlpha(0.9f);
		plot.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
		return plot;
	}

	private CombinedDomainXYPlot getCombinedDomainXYPlot(DateAxis x1Axis, XYPlot[] plot) {
		CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//
		if (plot.length == 1) {
			combineddomainxyplot.add(plot[0], 1);// 2/3
		} else if (plot.length == 2) {
			combineddomainxyplot.add(plot[0], 79);// 3/4
			combineddomainxyplot.add(plot[1], 21);// 1/4
			combineddomainxyplot.setGap(0);//
		}

		return combineddomainxyplot;
	}

	class SeriesGroup {
		public OHLCSeries kline;
		public TimeSeries ma5;
		public TimeSeries ma20;
		public TimeSeries ma60;
		public TimeSeries kdj;
		public TimeSeries dividingLine;

		public SeriesGroup() {
			kline = new OHLCSeries("");
			ma5 = new TimeSeries("ma5");
			ma20 = new TimeSeries("ma20");
			ma60 = new TimeSeries("ma60");
			kdj = new TimeSeries("kdj");
			dividingLine = new TimeSeries("dividingLine");
		}
	}

	class TimeSeriesCollectionGroup {
		public TimeSeriesCollection tsMa5;
		public TimeSeriesCollection tsMa20;
		public TimeSeriesCollection tsMa60;
		public TimeSeriesCollection tsDividingLine;
		public TimeSeriesCollection tsKdj;

		public XYLineAndShapeRenderer xylineMa5;
		public XYLineAndShapeRenderer xylineMa20;
		public XYLineAndShapeRenderer xylineMa60;
		public XYLineAndShapeRenderer xylineDividingLine;
		public XYLineAndShapeRenderer xylineKdj;

		public TimeSeriesCollectionGroup() {
			tsMa5 = new TimeSeriesCollection();
			tsMa20 = new TimeSeriesCollection();
			tsMa60 = new TimeSeriesCollection();
			tsDividingLine = new TimeSeriesCollection();
			tsKdj = new TimeSeriesCollection();

			// 
			xylineMa5 = new XYLineAndShapeRenderer(true, false);
			xylineMa20 = new XYLineAndShapeRenderer(true, false);
			xylineMa60 = new XYLineAndShapeRenderer(true, false);
			xylineDividingLine = new XYLineAndShapeRenderer(true, false);
			xylineKdj = new XYLineAndShapeRenderer(true, false);
			xylineKdj.setSeriesStroke(0, new BasicStroke(0.8F, 1, 1, 1.0F, new float[] { 6F, 6F }, 0.0F));// 

			xylineMa5.setSeriesPaint(0, Color.red);
			xylineMa20.setSeriesPaint(0, Color.yellow);
			xylineMa60.setSeriesPaint(0, Color.white);
			xylineDividingLine.setSeriesPaint(0, Color.white);
			xylineKdj.setSeriesPaint(0, new Color(34, 136, 187));
		}

		//
		public void setMainPlot(XYPlot plot) {
			plot.setDataset(1, tsMa5);
			plot.setRenderer(1, xylineMa5);
			plot.setDataset(2, tsMa20);
			plot.setRenderer(2, xylineMa20);
			plot.setDataset(3, tsMa60);
			plot.setRenderer(3, xylineMa60);
			plot.setDataset(4, tsDividingLine);
			plot.setRenderer(4, xylineDividingLine);
			plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		}

		public void addSeries(SeriesGroup sg) {
			tsMa5.addSeries(sg.ma5);
			tsMa20.addSeries(sg.ma20);
			tsMa60.addSeries(sg.ma60);
			tsKdj.addSeries(sg.kdj);
			tsDividingLine.addSeries(sg.dividingLine);

			tsMa5.setXPosition(TimePeriodAnchor.MIDDLE);
			tsMa20.setXPosition(TimePeriodAnchor.MIDDLE);
			tsMa60.setXPosition(TimePeriodAnchor.MIDDLE);
			tsKdj.setXPosition(TimePeriodAnchor.MIDDLE);
			tsDividingLine.setXPosition(TimePeriodAnchor.MIDDLE);
		}
	}
}
