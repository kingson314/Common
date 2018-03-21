package module.systemparam;

import common.util.log.UtilLog;

//系统参数:系统参数值类，每增加一个系统参数得维护此类
public class SystemParamsValue implements Cloneable {
	// 短信发送数据库链接
	private String DbLink;
	// 报警间隔时间(分钟)
	private String Interval;
	// 存储过程名
	private String Listener_Proc_Name;
	// 电话号码串 此参数在任务中设置，在表中的值无效
	private String Tel;
	// 短信发送服务名称
	private String MessageServerName;
	// 发送时间
	private String SendTime;
	// 回溯能力
	private String TraceId;
	// 是否启动短信报警
	private String AutoSend;
	// 下行截止时间
	private String CloseTime;
	// 是否自动显示手工执行结果
	private String showExecTaskresult;
	// 是否自动刷新调度状态结果
	private String AutoRefreshScheResult;
	// 是否记录行情监控日志
	private String ifLogMonitor;
	// 当任务执行次数达到此数目时，写数据表execLogAbridgement
	private String logAbridgement_count;
	// 高频任务最近日志记录数
	private String logLately_count;
	// 日志级别
	private String logLevel;
	// 日志记录器级别
	private String loggerLevel;
	// 高频任务最近日志记录数
	private String logSche_count;
	// 监控发送邮箱地址
	private String MonitorMailAdress;
	// 仿真模拟 行情回归时间
	private String returnTime;
	// 日期类型
	private String dateType;

	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			UtilLog.logError("复制对象错误:", e);
		}
		return o;
	};

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getMessageServerName() {
		return MessageServerName;
	}

	public void setMessageServerName(String messageServerName) {
		MessageServerName = messageServerName;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getSendTime() {
		return SendTime;
	}

	public void setSendTime(String sendTime) {
		SendTime = sendTime;
	}

	public String getCloseTime() {
		return CloseTime;
	}

	public void setCloseTime(String closeTime) {
		CloseTime = closeTime;
	}

	public String getTraceId() {
		return TraceId;
	}

	public void setTraceId(String traceId) {
		TraceId = traceId;
	}

	public String getAutoSend() {
		return AutoSend;
	}

	public void setAutoSend(String autoSend) {
		AutoSend = autoSend;
	}

	public String getInterval() {
		return Interval;
	}

	public void setInterval(String interval) {
		Interval = interval;
	}

	public String getListener_Proc_Name() {
		return Listener_Proc_Name;
	}

	public void setListener_Proc_Name(String listener_Proc_Name) {
		Listener_Proc_Name = listener_Proc_Name;
	}

	public String getShowExecTaskresult() {
		return showExecTaskresult;
	}

	public void setShowExecTaskresult(String showExecTaskresult) {
		this.showExecTaskresult = showExecTaskresult;
	}

	public String getDbLink() {
		return DbLink;
	}

	public void setDbLink(String dbLink) {
		DbLink = dbLink;
	}

	public String getAutoRefreshScheResult() {
		return AutoRefreshScheResult;
	}

	public void setAutoRefreshScheResult(String autoRefreshScheResult) {
		AutoRefreshScheResult = autoRefreshScheResult;
	}

	public String getIfLogMonitor() {
		return ifLogMonitor;
	}

	public void setIfLogMonitor(String ifLogMonitor) {
		this.ifLogMonitor = ifLogMonitor;
	}

	public String getLogAbridgement_count() {
		return logAbridgement_count;
	}

	public void setLogAbridgement_count(String logAbridgement_count) {
		this.logAbridgement_count = logAbridgement_count;
	}

	public String getLogLately_count() {
		return logLately_count;
	}

	public void setLogLately_count(String logLately_count) {
		this.logLately_count = logLately_count;
	}

	public String getLogSche_count() {
		return logSche_count;
	}

	public void setLogSche_count(String logSche_count) {
		this.logSche_count = logSche_count;
	}

	public String getMonitorMailAdress() {
		return MonitorMailAdress;
	}

	public void setMonitorMailAdress(String monitorMailAdress) {
		MonitorMailAdress = monitorMailAdress;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getLoggerLevel() {
		return loggerLevel;
	}

	public void setLoggerLevel(String loggerLevel) {
		this.loggerLevel = loggerLevel;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
}
