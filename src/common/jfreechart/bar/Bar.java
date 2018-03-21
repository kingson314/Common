package common.jfreechart.bar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import common.jfreechart.util.Util;

public class Bar {
	private String titleChart;
	private String titleSide;
	private String titleUnder;
	private DefaultCategoryDataset dataSet;

	// private Font fontAxis = new Font("黑体", Font.PLAIN, 12);

	public Bar(String titleChart, String titleSide, String titleUnder, DefaultCategoryDataset dataset) {
		this.titleChart = titleChart;
		this.titleSide = titleSide;
		this.titleUnder = titleUnder;
		this.dataSet = dataset;

	}

	public ChartPanel getChart() {
		JFreeChart barChart = ChartFactory.createBarChart(this.titleChart, this.titleUnder, this.titleSide, this.dataSet, PlotOrientation.VERTICAL, true, false, false);
		Util.setChartFont(barChart);
		setProperty(barChart);
		final ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.addChartMouseListener(new ChartMouseListener() {
			public void chartMouseClicked(ChartMouseEvent e) {
				if (e.getTrigger().getClickCount() == 2) {
					if (chartPanel.getHorizontalAxisTrace()) {
						chartPanel.setHorizontalAxisTrace(false); //
					} else {
						chartPanel.setHorizontalAxisTrace(true); //
					}

					if (chartPanel.getVerticalAxisTrace()) {
						chartPanel.setVerticalAxisTrace(false); //
					} else {
						chartPanel.setVerticalAxisTrace(true); //
					}
				}
			}

			public void chartMouseMoved(ChartMouseEvent arg0) {
			}
		});
		return chartPanel;
	}

	// 
	// private void setProperty1(JFreeChart chart) {
	// CategoryPlot plot = chart.getCategoryPlot();
	//
	// ValueAxis rangeAxis = plot.getRangeAxis();
	// // 设置最高的一个 Item 与图片顶端的距离
	// rangeAxis.setUpperMargin(0.15);
	// // 设置最低的一个 Item 与图片底端的距离
	// rangeAxis.setLowerMargin(0.15);
	// plot.setRangeAxis(rangeAxis);
	// BarRenderer3D renderer = new BarRenderer3D();
	// // 设置平行柱之间距离
	// renderer.setItemMargin(0.1);
	// // 显示每个柱的数值，并修改该数值的字体属性
	// renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	// renderer.setItemLabelFont(new Font("黑体", Font.PLAIN, 12));
	// renderer.setItemLabelsVisible(true);
	// plot.setRenderer(renderer);
	// }

	@SuppressWarnings("deprecation")
	private void setProperty(JFreeChart chart) {
		chart.setBackgroundPaint(Color.white);// 设置曲线图背景色
		TextTitle title = new TextTitle(this.titleChart, Util.font);// 设置字体大小，形状
		chart.setTitle(title);
		chart.setTextAntiAlias(true);
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		categoryplot.setNoDataMessage("没有数据!");
		categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		// 映射折线数据集
		categoryplot.setDataset(1, this.dataSet);
		categoryplot.mapDatasetToRangeAxis(1, 1);
		categoryplot.setBackgroundPaint(Color.white);
		categoryplot.setRangeGridlinesVisible(true);// 设置横虚线可见
		categoryplot.setRangeGridlinePaint(Color.gray);// 虚线色彩
		categoryplot.setDomainGridlinePaint(Color.DARK_GRAY);// 设置网格Y(Domain轴)颜色
		categoryplot.setRangeGridlinePaint(Color.DARK_GRAY);// 设置网格X横线颜色
		categoryplot.setRangeGridlineStroke(new BasicStroke(0.2f)); // 数据X轴网格线条笔触
		categoryplot.setDomainGridlineStroke(new BasicStroke(0.1f)); // 数据Y轴网格线条笔触
		categoryplot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));// 设置图与xy轴的距离
		categoryplot.setDomainCrosshairVisible(true);
		categoryplot.setRangeCrosshairVisible(true);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();// X轴
		categoryaxis.setLabelFont(Util.fontText);
		categoryaxis.setTickLabelFont(Util.fontText);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 7.0));
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);// X轴显示位置为90度

		NumberFormat numFormater = NumberFormat.getNumberInstance();
		numFormater.setMinimumFractionDigits(2);// 显示小数点后2 位
		NumberAxis left = new NumberAxis(this.titleSide);// Y轴
		NumberAxis numberaxis = new NumberAxis("比率");// Y轴
		numberaxis.setLabelFont(Util.fontText);
		left.setLabelFont(Util.fontText);
		left.setNumberFormatOverride(numFormater);
		numberaxis.setNumberFormatOverride(numFormater);

		categoryplot.setRangeAxis(0, left);// 左侧Y轴
		categoryplot.setRangeAxis(1, numberaxis);// 右侧Y轴

		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseOutlinePaint(Color.BLACK);
		// 设置每个地区所包含的平行柱之间的距离
		renderer.setItemMargin(0.1);
		renderer.setMaximumBarWidth(0.05);// 设置柱子宽度
		renderer.setMinimumBarLength(0.1);// 设置柱子高度
		renderer.setBaseOutlinePaint(Color.BLACK);// 设置柱的边框颜色
		renderer.setDrawBarOutline(true);// 设置柱的边框可见
		renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setItemMargin(0.05);
		renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setItemLabelFont(new Font("黑体", Font.PLAIN, 12));
		renderer.setItemLabelsVisible(true);

		LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();// 折线对象
		lineandshaperenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
		lineandshaperenderer.setSeriesPaint(0, new Color(0, 0, 255));
		categoryplot.setRenderer(0, renderer);
		categoryplot.setRenderer(1, lineandshaperenderer);
		categoryplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		// 图例各个属性
		LegendTitle legendtitle = chart.getLegend(0);
		legendtitle.setMargin(new RectangleInsets(2D, 2D, 2D, 2D));
		legendtitle.setBorder(new BlockBorder());
		legendtitle.setItemFont(Util.fontText);
	}

	// //创建主题样式
	// StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
	// //设置标题字体
	// standardChartTheme.setExtraLargeFont(new Font("宋体",
	// Font.LAYOUT_LEFT_TO_RIGHT,20));
	// //设置图例的字体
	// standardChartTheme.setRegularFont(new Font("宋体",Font.PLAIN,15));
	// //设置轴向的字体
	// standardChartTheme.setLargeFont(new Font("宋体",Font.PLAIN,15));
	// //应用主题样式
	// ChartFactory.setChartTheme(standardChartTheme);
}
