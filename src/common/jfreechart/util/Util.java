package common.jfreechart.util;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.title.TextTitle;

public class Util {
	public static Font font = new Font("黑体", Font.BOLD, 14);
	public static Font fontText = new Font("黑体", Font.PLAIN, 12);

	/**
	 * 图标乱码解决
	 */
	public static void setChartFont(JFreeChart chart) {
		Font title = new Font("楷体", Font.PLAIN, 15);
		Font f = new Font("楷体", Font.PLAIN, 12);
		// title(标题),
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(title);
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();
		// 设置X轴坐标上的文字
		domainAxis.setTickLabelFont(f);
		// 设置X轴的标题文字
		domainAxis.setLabelFont(f);

		ValueAxis numberaxis = plot.getRangeAxis();
		// 设置Y轴坐标上的文字
		numberaxis.setTickLabelFont(f);
		// 设置Y轴的标题文字
		numberaxis.setLabelFont(f);
		chart.getLegend().setItemFont(f);
	}
}
