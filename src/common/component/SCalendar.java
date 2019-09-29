package common.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import common.util.conver.UtilConver;
import consts.Const;
import consts.ImageContext;

/**
 * Title: Swing日历
 */

public class SCalendar extends SDialog {
	private static final long serialVersionUID = 6911912213218085033L;
	// 动态表示年月日
	private int year = 0;
	private int month = 0;
	private int day = 0;
	// 主面板
	private JPanel pnlMain = new JPanel();
	// 日面板
	private JPanel pnlDate = new JPanel();

	private JSpinner spnYear;
	private JSpinner spnMonth;
	// 显示日期的位置
	private STextField txtOut;
	// 中国时区，以后可以从这里扩展可以设置时区的功能
	private Locale l = Locale.CHINESE;
	// 主日历
	private GregorianCalendar cal = new GregorianCalendar(l);
	// 星期面板
	private JPanel pnlWeek = new JPanel();
	// 天按钮组
	private JToggleButton[] tbtnDays = new JToggleButton[42];
	// 天面板
	private JPanel pnlDay = new JPanel();
	// 标示
	private JLabel lMon = new SLabel("一", SwingConstants.CENTER);
	private JLabel lTue = new SLabel("二", SwingConstants.CENTER);
	private JLabel lWen = new SLabel("三", SwingConstants.CENTER);
	private JLabel lThu = new SLabel("四", SwingConstants.CENTER);
	private JLabel lFri = new SLabel("五", SwingConstants.CENTER);
	private JLabel lSat = new SLabel("六", SwingConstants.CENTER);
	private JLabel lSun = new SLabel("日", SwingConstants.CENTER);
	// 1 2 3 4 5 6 7 8 9 10 11 12
	private int[] mm = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	// 空日期构造函数
	public SCalendar(STextField txt) {
		super(null, "日期选择", true);
		this.txtOut = txt;
		init();
		this.setVisible(true);
	}

	// // 空日期构造函数
	// public SCalendar() {
	// super(null, "日期选择", true);
	// init();
	// }

	// // 带日期设置的构造函数
	// public SCalendar(int year, int month, int day) {
	// this();
	// cal.set(year, month, day);
	// this.setVisible(true);
	// }
	//
	// // 带日历输入的构造函数
	// public SCalendar(GregorianCalendar calendar) {
	// this();
	// cal = calendar;
	// this.setVisible(true);
	// }
	//
	// // 带日期输入的构造函数
	// public SCalendar(Date date) {
	// this();
	// cal.setTime(date);
	// this.setVisible(true);
	// }

	// 初始化组件

	private void init() {
		try {
			// AppLookAndFeel.getInstance().updateLookAndFeel(
			// AppLookAndFeel.LookAndFeelCnName[0]);
			this.setSize(new Dimension(350, 250));
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int w = (screenSize.width - this.getWidth()) / 2;
			int h = (screenSize.height - this.getHeight()) / 2;
			this.setLocation(w, h);
			this.setResizable(false);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.Calendar));
			// this.setLayout(new BorderLayout());
			this.addWindowListener(new WindowAdapter() {// 添加窗体退出事件
						public void windowClosing(java.awt.event.WindowEvent evt) {
							dispose();
						}
					});

			// 初始化年、月、日
			iniCalender();
			pnlWeek.setLayout(new GridLayout(1, 7));
			pnlWeek.add(lSun);
			pnlWeek.add(lMon);
			pnlWeek.add(lTue);
			pnlWeek.add(lWen);
			pnlWeek.add(lThu);
			pnlWeek.add(lFri);
			pnlWeek.add(lSat);

			pnlDate.setLayout(new BorderLayout());
			pnlDay.setLayout(new GridLayout(6, 7));
			for (int i = 0; i < 42; i++) {
				tbtnDays[i] = new JToggleButton();
				tbtnDays[i].setBorder(SBorder.getTitledBorder());
				tbtnDays[i].setHorizontalAlignment(SwingConstants.CENTER);
				tbtnDays[i].setHorizontalTextPosition(SwingConstants.CENTER);
				tbtnDays[i].addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						day = Integer.parseInt(((JToggleButton) e.getSource()).getText());
						showMe();
						String date = UtilConver.dateToStr((Date) spnYear.getValue(), Const.fm_yyyy) + UtilConver.dateToStr((Date) spnMonth.getValue(), Const.fm_MM)
								+ UtilConver.formatInt(day, "00");
						// System.out.println(date);
						setText(date);
						dispose();
					}
				});
				pnlDay.add(tbtnDays[i]);
			}
			pnlMain.setPreferredSize(new Dimension(300, 30));
			pnlMain.setLayout(new GridBagLayout());
			pnlMain.setBorder(SBorder.getTitledBorder());
			this.add(pnlMain, BorderLayout.NORTH);
			this.spnYear = getSpnYear();
			pnlMain.add(this.spnYear);
			this.spnMonth = getSpnMonth();
			pnlMain.add(this.spnMonth);
			pnlDate.setBorder(SBorder.getTitledBorder());
			pnlWeek.setBorder(SBorder.getTitledBorder());
			pnlDate.add(pnlWeek, BorderLayout.NORTH);
			pnlDay.setBorder(SBorder.getTitledBorder());
			pnlDate.add(pnlDay, BorderLayout.CENTER);
			this.add(pnlDate, BorderLayout.CENTER);
			showMe();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setText(String date) {
		txtOut.setText(date);
	}

	private JSpinner getSpnYear() {
		SpinnerDateModel mdlYear = new SpinnerDateModel();
		JSpinner spnYear = new JSpinner(mdlYear);
		spnYear.setBorder(BorderFactory.createEtchedBorder());
		JSpinner.DateEditor edtYear = new JSpinner.DateEditor(spnYear, "yyyy年");
		spnYear.setEditor(edtYear);
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				SpinnerModel source = (SpinnerModel) e.getSource();
				if (source.getValue().toString().compareToIgnoreCase(source.getPreviousValue().toString()) > 0) {
					yearUp();
				} else {
					yearDown();
				}
			}
		};
		mdlYear.addChangeListener(listener);
		return spnYear;
	}

	private JSpinner getSpnMonth() {
		SpinnerDateModel mdlMonth = new SpinnerDateModel();
		JSpinner spnMonth = new JSpinner(mdlMonth);
		spnMonth.setBorder(BorderFactory.createEtchedBorder());
		JSpinner.DateEditor edtYear = new JSpinner.DateEditor(spnMonth, "MM月");
		spnMonth.setEditor(edtYear);
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				SpinnerModel source = (SpinnerModel) e.getSource();
				if (source.getValue().toString().compareToIgnoreCase(source.getPreviousValue().toString()) > 0) {
					monthUp();
				} else {
					monthDown();
				}
			}
		};
		mdlMonth.addChangeListener(listener);
		return spnMonth;
	}

	// 增加年份
	void yearUp() {
		year++;
		showMe();
	}

	// 减少年份
	void yearDown() {
		year--;
		showMe();
	}

	// 减少月份
	void monthDown() {
		month--;
		if (month < 0) {
			month = 11;
			year--;
		}
		showMe();
	}

	// 增加月份
	void monthUp() {
		month++;
		if (month == 12) {
			month = 0;
			year++;
		}
		showMe();
	}

	// 初始化年月日
	void iniCalender() {
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
	}

	// // 刷新日期
	// void showDate() {
	// txtOut.setText(Integer.toString(year) + "-"
	// + Integer.toString(month + 1) + "-" + Integer.toString(day));
	// }

	// 重画天数选择面板
	void showDay() {
		cal.set(year, month, 1);
		int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int n = mm[month];
		if (cal.isLeapYear(year) && month == 1)
			n++;
		int i = 0;
		for (; i < firstDayOfWeek - 1; i++) {
			tbtnDays[i].setVisible(false);
			tbtnDays[i].setSelected(false);
			tbtnDays[i].setText("");
		}
		int d = 1;
		for (; d <= n; d++) {
			tbtnDays[i].setText(Integer.toString(d));
			tbtnDays[i].setVisible(true);
			if (d == day)
				tbtnDays[i].setSelected(true);
			else
				tbtnDays[i].setSelected(false);
			;
			i++;
		}
		for (; i < 42; i++) {
			tbtnDays[i].setVisible(false);
			tbtnDays[i].setSelected(false);
			tbtnDays[i].setText("");
		}
	}

	// 以字符串形式输入日期,yyyy-mm-dd
//	public void setDate(String date) {
//		if (date != null) {
//			StringTokenizer f = new StringTokenizer(date, "-");
//			if (f.hasMoreTokens())
//				year = Integer.parseInt(f.nextToken());
//			if (f.hasMoreTokens())
//				month = Integer.parseInt(f.nextToken());
//			if (f.hasMoreTokens())
//				day = Integer.parseInt(f.nextToken());
//			cal.set(year, month, day);
//		}
//		this.showMe();
//	}

	// 以日期对象形式输入日期
	public void setTime(Date date) {
		cal.setTime(date);
		this.iniCalender();
		this.showMe();
	}

	// 返回日期对象
	public Date getTime() {
		return cal.getTime();
	}

	// 返回当前的日
	public int getDay() {
		return day;
	}

	// 设置当前的日
	public void setDay(int day) {
		this.day = day;
		cal.set(this.year, this.month, this.day);
		this.showMe();
	}

	// 设置当前的年
	public void setYear(int year) {
		this.year = year;
		cal.set(this.year, this.month, this.day);
		this.showMe();
	}

	// 返回当前的年
	public int getYear() {
		return year;
	}

	// 返回当前的月
	public int getMonth() {
		return month;
	}

	// 设置当前的月
	public void setMonth(int month) {
		this.month = month;
		cal.set(this.year, this.month, this.day);
		this.showMe();
	}

	// 刷新
	public void showMe() {
		this.showDay();
		// this.showDate();
	}

	// 测试用例
	// 
	// public static void main(String[] args) {
	// new SCalendar(null);
	// }
}
