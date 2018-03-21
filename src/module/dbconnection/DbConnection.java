package module.dbconnection;
import common.util.log.UtilLog;


//数据库连接信息Bean
public class DbConnection implements Cloneable {
	// 数据库连接名key
	private String dbName;
	// 数据库连接类型
	private String dbType;
	// 数据库连接类
	private String dbClassName;
	// 数据库连接
	private String dbCon;
	// 用户名
	private String dbUser;
	// 密码
	private String dbPassword;

	
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			UtilLog.logError("复制对象错误:", e);
		}
		return o;
	};
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbClassName() {
		return dbClassName;
	}

	public void setDbClassName(String dbClassName) {
		this.dbClassName = dbClassName;
	}

	public String getDbCon() {
		return dbCon;
	}

	public void setDbCon(String dbCon) {
		this.dbCon = dbCon;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
}
