package common.jfreechart.pie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.TableOrder;

import common.jfreechart.util.Util;

public class Pie {
	private String titleChart;
	private DefaultCategoryDataset dataSet;

	public Pie(String titleChart, DefaultCategoryDataset dataset) {
		this.titleChart = titleChart;
		this.dataSet = dataset;

	}

	@SuppressWarnings("deprecation")
	public ChartPanel getChart() {
		JFreeChart chart = ChartFactory.createMultiplePieChart3D(this.titleChart, this.dataSet, TableOrder.BY_ROW, true, false, false);
		chart.setBackgroundPaint(Color.white);// 设置图背景色
		TextTitle title = new TextTitle(this.titleChart, Util.font);// 设置字体大小，形状
		chart.setTitle(title);
		chart.setTextAntiAlias(true);
		MultiplePiePlot multiplepieplot = (MultiplePiePlot) chart.getPlot();
		JFreeChart jfreechart = multiplepieplot.getPieChart();
		jfreechart.getTitle().setFont(Util.fontText);
		PiePlot plot = (PiePlot) jfreechart.getPlot();

		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}", NumberFormat.getNumberInstance(), new DecimalFormat("0.0%")));
		// 图例显示百分比:自定义方式， 同上说明
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}"));
		// 无数据是显示提示和颜色。
		plot.setNoDataMessage("没有数据!");
		plot.setNoDataMessagePaint(Color.BLACK);
		// 设置背景颜色
		plot.setBackgroundPaint(Color.WHITE);
		// 图形边框颜色
		plot.setBaseSectionOutlinePaint(Color.RED);
		// plot.setBaseSectionPaint(Color.WHITE);
		// 图形边框粗细
		plot.setBaseSectionOutlineStroke(new BasicStroke(1.0f));
		// 指定图片的透明度(0.0-1.0)
		plot.setForegroundAlpha(0.65f);
		// 指定显示的饼图上圆形(false)还椭圆形(true)
		plot.setCircular(true);
		// 设置第一个 饼块section 的开始位置，默认是12点钟方向
		plot.setStartAngle(360);
		// 设置鼠标悬停提示
		plot.setToolTipGenerator(new StandardPieToolTipGenerator());
		// 设置突出显示的数据块
		plot.setExplodePercent("One", 0.1D);
		// 设置饼图各部分标签字体
		plot.setLabelFont(Util.fontText);
		// 设置分饼颜色
		plot.setSectionPaint(0, new Color(244, 194, 144));
		// 设置图例说明Legend上的文字
		chart.getLegend().setItemFont(Util.fontText);
		// 定义字体格式
		title.setFont(Util.font);
		// 设置字体,非常关键不然会出现乱码的,下方的字体
		chart.setTitle(title);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// 创建饼图数据对象
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("管理人员", 25);
		dataSet.setValue("市场人员", 35);
		dataSet.setValue("开发人员", 20);
		dataSet.setValue("后勤人员", 5);
		dataSet.setValue("财务人员", 15);
		// createpieChart3D创建3D饼图
		JFreeChart chart = ChartFactory.createPieChart3D("CityInfoPort公司组织架构图", dataSet, true, true, true);
		// 图片背景色
		chart.setBackgroundPaint(Color.red);
		// 设置标题文字
		ChartFrame frame = new ChartFrame("CityInfoPort公司组织架构图 ", chart, true);
		// 取得饼图plot对象
		// PiePlot plot = (PiePlot) chart.getPlot();
		// 取得3D饼图对象
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// 图形边框颜色
		plot.setBaseSectionOutlinePaint(Color.RED);
		// plot.setBaseSectionPaint(Color.WHITE);
		// 图形边框粗细
		plot.setBaseSectionOutlineStroke(new BasicStroke(1.0f));
		// 指定图片的透明度(0.0-1.0)
		plot.setForegroundAlpha(0.65f);
		// 指定显示的饼图上圆形(false)还椭圆形(true)
		plot.setCircular(true);
		// 设置第一个 饼块section 的开始位置，默认是12点钟方向
		plot.setStartAngle(360);
		// 设置鼠标悬停提示
		plot.setToolTipGenerator(new StandardPieToolTipGenerator());
		// 设置突出显示的数据块
		plot.setExplodePercent("One", 0.1D);
		// 设置饼图各部分标签字体
		plot.setLabelFont(new Font("宋体", Font.ITALIC, 20));
		// 设置分饼颜色
		plot.setSectionPaint(0, new Color(244, 194, 144));
		// plot.setSectionPaint("2", new Color(144, 233, 144));
		// 设置图例说明Legend上的文字
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 30));
		// 定义字体格式
		Font font = new java.awt.Font("黑体", java.awt.Font.CENTER_BASELINE, 50);
		TextTitle title = new TextTitle("项目状态分布");
		title.setFont(font);
		// 设置字体,非常关键不然会出现乱码的,下方的字体
		chart.setTitle(title);
		frame.pack();
		frame.setVisible(true);
	}

	// PiePlot plot = (PiePlot) jfreechart.getPlot();

	// // 设置透明度
	// plot.setForegroundAlpha(0.65f);
	// // 指定饼图轮廓线的颜色
	// plot.setBaseSectionOutlinePaint(Color.BLACK);
	// plot.setBaseSectionPaint(Color.BLACK);
	// plot.setCircular(true);
	// // 设置第一个 饼块section 的开始位置，默认是12点钟方向
	// plot.setStartAngle(360);
	// plot.setLabelLinkPaint(Color.BLUE);
	// plot.setSectionOutlinesVisible(false);
	// // 无数据是显示提示和颜色。
	// plot.setNoDataMessage("没有数据!");
	// plot.setNoDataMessagePaint(Color.BLACK);
	// plot.setToolTipGenerator(new StandardPieToolTipGenerator());
	// plot.setInteriorGap(0.30);
	// // 设置扇区分离显示
	// plot.setExplodePercent("", 0.2D);
	// // 设置扇区边框不可见
	// plot.setSectionOutlinesVisible(false);
	// // 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
	// plot.setLabelGenerator(new
	// StandardPieSectionLabelGenerator("{2}",NumberFormat.getNumberInstance(),
	// new DecimalFormat("0.0%")));
	// // 图例显示百分比:自定义方式， 同上说明
	// plot.setLegendLabelGenerator(new
	// StandardPieSectionLabelGenerator("{2}"));
	// plot.setLabelFont(Const.fontText);
	// // 图例各个属性
	// LegendTitle legendtitle = new LegendTitle(jfreechart.getPlot());
	// legendtitle.setMargin(new RectangleInsets(2D, 2D, 2D, 2D));
	// legendtitle.setBorder(new BlockBorder());
	// legendtitle.setItemFont(Const.fontText);
	// 曲线图形的微调方法。
	@SuppressWarnings("unused")
	private void processRenderer(XYLineAndShapeRenderer renderer, XYDataset dataset) {
		Ellipse2D ell = new Ellipse2D.Float(-4, -4, 8, 8);
		if (dataset.getSeriesCount() == 8) {
			renderer.setSeriesStroke(2, new BasicStroke(2.0F, 1, 1, 1.0F, new float[] { 6F, 6F }, 0.0F));// 虚线
			renderer.setSeriesStroke(3, new BasicStroke(2.0F, 1, 1, 1.0F, new float[] { 6F, 6F }, 0.0F));// 虚线
			renderer.setSeriesShape(2, ell); // 设置第一个 XYSeries数据点大小
			renderer.setSeriesPaint(2, Color.cyan);
			renderer.setSeriesShape(3, new Rectangle(-3, -3, 6, 6));// 设置第二个XYSeries数据点大小
			renderer.setSeriesPaint(3, Color.green);
			renderer.setSeriesStroke(4, new BasicStroke(2.0F, 1, 1, 1.0F, new float[] { 6F, 6F }, 0.0F));// 虚线
			renderer.setSeriesStroke(5, new BasicStroke(2.0F, 1, 1, 1.0F, new float[] { 6F, 6F }, 0.0F));// 虚线
			renderer.setSeriesShape(4, ell); // 设置第一个XYSeries数据点大小
			renderer.setSeriesPaint(4, Color.cyan);
			renderer.setSeriesShape(5, new Rectangle(-3, -3, 6, 6));// 设置第二个XYSeries数据点大小
			renderer.setSeriesPaint(5, Color.green);
			renderer.setSeriesStroke(6, new BasicStroke(2.0F, 1, 1, 1.0F, new float[] { 6F, 6F }, 0.0F));// 虚线
			renderer.setSeriesStroke(7, new BasicStroke(2.0F, 1, 1, 1.0F, new float[] { 6F, 6F }, 0.0F));// 虚线
			renderer.setSeriesShape(6, ell); // 设置第一个XYSeries数据点大小
			renderer.setSeriesPaint(6, Color.pink);
			renderer.setSeriesShape(7, new Rectangle(-3, -3, 6, 6));// 设置第二个XYSeries数据点大小
			renderer.setSeriesPaint(7, Color.yellow);
			renderer.setSeriesStroke(8, new BasicStroke(2.0F, 1, 1, 1.0F, new float[] { 6F, 6F }, 0.0F));// 虚线
			renderer.setSeriesStroke(9, new BasicStroke(2.0F, 1, 1, 1.0F, new float[] { 6F, 6F }, 0.0F));// 虚线
			renderer.setSeriesShape(8, ell); // 设置第一个 XYSeries数据点大小
			renderer.setSeriesPaint(8, Color.pink);
			renderer.setSeriesShape(9, new Rectangle(-3, -3, 6, 6));// 设置第二个XYSeries数据点大小
			renderer.setSeriesPaint(9, Color.yellow);
		}
	}

}
