package common.jfreechart.line;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;

import common.jfreechart.util.Util;
/**
 * @Description:
 * @date 2014-4-13
 * @author:fgq
 */
public class Line {

	private String titleChart;
	private String titleSide;
	private String titleUnder;
	private CategoryDataset dataSet;

	public Line(String titleChart, String titleSide, String titleUnder, CategoryDataset dataSet) {
		this.titleChart = titleChart;
		this.titleSide = titleSide;
		this.titleUnder = titleUnder;
		this.dataSet = dataSet;

	}

	public ChartPanel getChart() {
		StandardChartTheme mChartTheme = new StandardChartTheme("CN");
		mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
		mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
		mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
		ChartFactory.setChartTheme(mChartTheme);
		JFreeChart jFreeChart = ChartFactory.createLineChart(this.titleChart, this.titleUnder, this.titleSide, this.dataSet, PlotOrientation.VERTICAL, true, true, false);
		Util.setChartFont(jFreeChart);
		CategoryPlot plot = (CategoryPlot) jFreeChart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.gray);// 背景底部横虚线
		plot.setOutlinePaint(Color.white);// 边界线
		LineAndShapeRenderer render=(LineAndShapeRenderer)plot.getRenderer();
		render.setSeriesPaint(0, Color.green);// new Color(0, 0 ,255 )
		render.setSeriesPaint(1, Color.green);
		render.setSeriesPaint(2, Color.blue);
		render.setSeriesPaint(3, Color.red);
		final ChartPanel chartPanel = new ChartPanel(jFreeChart);
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

	// public CategoryDataset GetDataset() {
	// DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
	// mDataset.addValue(1, "First", "2013");
	// mDataset.addValue(3, "First", "2014");
	// mDataset.addValue(2, "First", "2015");
	// mDataset.addValue(6, "First", "2016");
	// mDataset.addValue(5, "First", "2017");
	// mDataset.addValue(12, "First", "2018");
	// mDataset.addValue(14, "Second", "2013");
	// mDataset.addValue(13, "Second", "2014");
	// mDataset.addValue(12, "Second", "2015");
	// mDataset.addValue(9, "Second", "2016");
	// mDataset.addValue(5, "Second", "2017");
	// mDataset.addValue(7, "Second", "2018");
	// return mDataset;
	// }
}
