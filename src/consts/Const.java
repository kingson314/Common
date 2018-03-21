package consts;

import java.awt.Color;
import java.awt.Font;

public interface Const {
	public final static String DefaultDir = System.getProperty("user.dir");
	public final static String XmlTaskClass = DefaultDir + "/xml/taskClass.xml";
	public final static String XmlAppConfig = DefaultDir + "/xml/App/AppConfig.xml";
	public final static String DefaultMdbMdbPath = DefaultDir + "/db/db.mdb";
	public final static String XmlAppToolBar = DefaultDir + "/xml/App/AppToolBar.xml";
	public final static String XmlAppTree = DefaultDir + "/xml/App/AppTree.xml";
	public final static Font tfont = new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12); // 字体
	public final static Font tfontBtn = new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 8); // 字体
	public final static Font tTablefont = new Font("宋体", Font.BOLD, 12); // 字体
	// new Color(220, 217,202)
	public final static Color ColorSelection = new Color(184, 207, 229);
	public final static Color ColorHigh = Color.red;
	public final static Color ColorMiddle = Color.blue;
	public final static Color ColorLow = Color.green;

	public final static String fm_HHmm = "HHmm";
	public final static String fm_HH_mm = "HH:mm";
	public final static String fm_yyyy = "yyyy";
	public final static String fm_MM = "MM";
	public final static String fm_dd = "dd";
	public final static String fm_HHmmss = "HHmmss";
	public final static String fm_HH_mm_ss = "HH:mm:ss";
	public final static String fm_yyyyMMdd = "yyyyMMdd";
	public final static String fm_yyyy_MM_dd = "yyyy-MM-dd";
	public final static String fm_yyyyMMdd_HHmmss = "yyyyMMdd HHmmss";
	public final static String fm_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public final static String fm_yyyyMMdd_HHmmssSSS = "yyyyMMdd HHmmssSSS";
	public final static String fm_yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

	// 目录变量协议注册表
	public static final String PROTOCOL_DATE = "date";
	public static final String PROTOCOL_FTP_NAME = "ftpname";
	public static final String PROTOCOL_FUND = "fund";
	public static final String PROTOCOL_SEAT = "seat";

	public static final String VARCHARTYPE = "varchar";// 字符类型
	public static final String STRINGTYPE = "String";// 字符类型
	public static final String NUMBERTYPE = "number"; // 数字类型
	public static final String INTEGERTYPE = "int"; // 数字类型
	public static final String FLOATTYPE = "float"; // 数字类型
	public static final String DOUBLETYPE = "double"; // 数字类型
	public static final String DATETYPE = "date"; // 日期类型
	public static final String TIMETYPE = "time"; // 时间类型
	public static final String TIMESTAMPTYPE = "datetime"; // 时间戳类型
	public static final String BLOBTYPE = "blob"; // blob类型
	public static final String CLOBTYPE = "clob"; // clob类型

	// temp文件夹
	public static final String TEMP_DIR = DefaultDir + "/temp/";
	// 日期模板偏移量常量
	public static final int OFFSET_DAY = 201;
	public static final int OFFSET_TRADING_DAY = 202;

	// 文件分发类型常量
	public static final String TRANS_TYPE_FILE = "FILE";
	public static final String TRANS_TYPE_FTP = "FTP";

	public static final int Task_Group_ErrorContinue = 0;
	public static final int Task_Group_ErrorExit = 0;
	public static final String[] TASK_Group_ErrorType = new String[] { "继续", "退出" };
	public static final int Task_Group_Order = 0;
	public static final int Task_Group_UnOrder = 1;
	public static final String[] TASK_Group_ExecType = new String[] { "并行", "串行" };
	public static final String[] SCHE_Type = new String[] { "串行", "并行" };
	public static final String[] ExecStatus = new String[] { "", "执行成功", "执行失败", "执行提示" };
	public static final String[] IfSendMsg = new String[] { "", "是", "否" };
	public static final String[] ArrYesNO = new String[] { "是", "否" };
	public static final String[] ArrHML = new String[] { "高", "中", "低" };
	public static final String[] ScheStatus = new String[] { "", "正常", "停止", "结束" };
	public static final String[] DbLinkType = new String[] { "MySQL","ORACLE", "SQL SERVER2005", "SQL SERVER2008", "Access", "DBF" };//
	public static final String[] DbLinkDriverClass = new String[] { "com.mysql.jdbc.Driver","oracle.jdbc.driver.OracleDriver", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "net.sourceforge.jtds.jdbc.Driver", "sun.jdbc.odbc.JdbcOdbcDriver",
			"sun.jdbc.odbc.JdbcOdbcDriver" };
	public static final String[] DbConnectionString = { "jdbc:mysql://127.0.0.1:3306/patent?useUnicode=true&characterEncoding=UTF-8","jdbc:oracle:thin:@服务器:端口:数据库", "jdbc:sqlserver://服务器:端口;databaseName=数据库", "jdbc:jtds:sqlserver://服务器:端口/数据库",
			"jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=文件路径", "jdbc:odbc:DRIVER={Microsoft dBase Driver (*.dbf)};DBQ=文件路径;" };

	public static final String[] LogAbridgementType = new String[] { "当前日志摘要", "历史日志摘要" };
	public static final String[] ExecType = new String[] { "", "调度执行", "手工执行" };
	public static final String[] CompareType = new String[] { "大于等于", "大于", "等于", "不等于", "小于等于", "小于" };
	public static final String[] FtpType = new String[] { "FTP", "SFTP" };

	public static final String[] SpecialDate = new String[] { "全部", "本周第一个工作日", "本周最后一个工作日", "本月第一个工作日", "本月最后一个工作日", "本季第一个工作日", "本季最后一个工作日", "本年第一个工作日", "本年最后一个工作日" };
	public static final String[] WarnType = new String[] { "全部", "短信报警", "邮件报警" };

	// 转义词 用户导入指标任务
	public static final String[][] EscapeWord = new String[][] { { "帐", "账" }, { "谘", "咨" } };
	// 指标值的单位 以及 %
	public static final String[] UnitIndicator = new String[] { "万亿日元", "亿加元", "亿欧元", "亿英镑", "亿日元", "亿纽元", "亿澳元", "亿瑞郎", "亿美元", "亿瑞郎", "亿元", "万桶", "%", "万" };

	public String[] SymbolArr = new String[] { "全部", "XAUUSD", "EURUSD", "GBPUSD", "AUDUSD", "USDJPY", "USDCAD", "USDCHF" };
	public static int[] PeriodArr = new int[] { 1, 5, 15, 30, 60, 240, 1440 };
	public static String[] PeriodStrArr = new String[] { "1分钟", "5分钟", "15分钟", "30分钟", "60分钟", "240分钟" };
	public static String[] EconomicDataSource = new String[] { "福汇", "汇通" };
	public static int[] EconomicDataSourceKey = new int[] { 1, 0 };

	public static String DbName = "app";
	public static String DateType = "DATELOCAL";// "DATESERVER";
	public static String TimeType = "TIMELOCAL";// "TIMESERVER"
	public static String[] minutePointArr = new String[] { "-5", "30", "60", "90", "120" };

	public static String[] Indicatoreffect = new String[] { "公布值>预测值=利好美元", "公布值<预测值=利好美元", "公布值>预测值=利好欧元", "公布值<预测值=利好欧元", "公布值>预测值=利好英镑", "公布值<预测值=利好英镑", "公布值>预测值=利好澳元", "公布值<预测值=利好澳元", "公布值>预测值=利好日元", "公布值<预测值=利好日元",
			"公布值>预测值=利好瑞郎", "公布值<预测值=利好瑞郎", "公布值>预测值=利好加元", "公布值<预测值=利好加元", "公布值>预测值=利好纽元", "公布值<预测值=利好纽元" };
	public static String[] CompareResult = new String[] { "大于前值", "小于前值", "等于前值", "大于预测值;", "小于预测值;", "等于预测值;", "大于预测值;大于前值", "大于预测值;等于前值", "大于预测值;小于前值", "等于预测值;大于前值", "等于预测值;等于前值", "等于预测值;小于前值", "小于预测值;大于前值",
			"小于预测值;等于前值", "小于预测值;小于前值" };
	public static String[] Predictedresult = new String[] { "预测值大于前值", "预测值小于前值","预测值等于前值" };
}
