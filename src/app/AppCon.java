package app;

import module.dbconnection.DbConnection;
import common.util.string.UtilString;
import consts.Const;


/**
 * @info 程序数据库连接
 * 
 * @author fgq 20120831
 * 
 */
public class AppCon {
	public static String TN_DateType;
	public static String TN_DbConnection;
	public static String TN_ExecLogAbridgement;
	public static String TN_ExecResult;
	public static String TN_Ftpsite;
	public static String TN_Holidays;
	public static String TN_MailSender;
	public static String TN_Monitor;
	public static String TN_MonitorGroup;
	public static String TN_MonitorGroupDetail;
	public static String TN_ScheParam;
	public static String TN_Settings;
	public static String TN_SystemParams;
	public static String TN_Task;
	public static String TN_Taskgroup;
	public static String TN_TaskGroupDetail;
	// 程序应用数据库
	public static DbConnection DbconApp;
	// 当前执行日期数据库
	public static DbConnection DbconNowDate;
	// 节假日数据库
	public static DbConnection DbconDateType;

	// 初始化本程序使用的数据库以及数据表名称
	public AppCon() {
		loadDbconn();
		initTableName();
	}

	private void initTableName() {
		String TableTrefix = "";

		if (!"Access".equalsIgnoreCase(UtilString.isNil(DbconApp.getDbType()))) {
			TableTrefix = "ts_";
		}
		TN_DateType = TableTrefix + "DateType".toLowerCase();
		TN_DbConnection = TableTrefix + "DbConnection".toLowerCase();
		TN_ExecLogAbridgement = TableTrefix + "ExecLogAbridgement".toLowerCase();
		TN_ExecResult = TableTrefix + "ExecResult".toLowerCase();
		TN_Ftpsite = TableTrefix + "Ftpsite".toLowerCase();
		TN_Holidays = TableTrefix + "Holidays".toLowerCase();
		TN_MailSender = TableTrefix + "MailSender".toLowerCase();
		TN_Monitor = TableTrefix + "Monitor".toLowerCase();
		TN_MonitorGroup = TableTrefix + "MonitorGroup".toLowerCase();
		TN_MonitorGroupDetail = TableTrefix + "MonitorGroupDetail".toLowerCase();
		TN_ScheParam = TableTrefix + "ScheParam".toLowerCase();
		TN_Settings = TableTrefix + "Settings".toLowerCase();
		TN_SystemParams = TableTrefix + "SystemParams".toLowerCase();
		TN_Task = TableTrefix + "Task".toLowerCase();
		TN_Taskgroup = TableTrefix + "Taskgroup".toLowerCase();
		TN_TaskGroupDetail = TableTrefix + "TaskGroupDetail".toLowerCase();
	}

	private void loadDbconn() {
		 DbconApp = AppConfig.getInstance().getAppDbcon("dbApp");
		//DbconNowDate = AppConfig.getInstance().getAppDbcon("dbNowDate");
		//DbconDateType = AppConfig.getInstance().getAppDbcon("dbDateType");
		// 使用默认的数据库
		 if (DbconApp == null) {
			DbconApp = new DbConnection();
			DbconApp.setDbClassName("sun.jdbc.odbc.JdbcOdbcDriver");
			DbconApp.setDbCon("jdbc:odbc:driver={Microsoft Access Driver (*.mdb,*.accdb)};DBQ=" + Const.DefaultMdbMdbPath);
			DbconApp.setDbName("Default");
			DbconApp.setDbPassword("");
			DbconApp.setDbUser("");
			DbconApp.setDbType("Access");
		 }
		DbconDateType=(DbConnection) DbconApp.clone();
		DbconNowDate=(DbConnection) DbconApp.clone();
		AppStatus.getInstance().setStatus("db", "程序应用数据库:" + DbconApp.getDbName().replace(AppConfig.SerialVersionUID, ""));
	}

	// public static void main(String[] args) throws Exception {
	// new AppCon();
	// Connection con = JDBCManager.getConnection(Dbcon);
	// System.out.println(Sql.QueryForMax(con,
	// "select max(Id) from rdp_settings", new Object[0]));
	// }
}
