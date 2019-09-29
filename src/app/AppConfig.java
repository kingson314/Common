package app;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.DocumentException;

import common.component.ShowMsg;
import common.util.conver.UtilConver;
import common.util.security.UtilCrypt;
import common.util.string.UtilString;
import common.util.xml.UtilXml;
import consts.Const;
import module.dbconnection.DbConnection;

/**
 * @info 程序配置
 * 
 * @author fgq 20120831
 * 
 */
public class AppConfig {
	// 本系统的版本号
	public final static String version = "3.0.0";

	private static AppConfig appConfig;
	private List<Map<String, Object>> listConfig;
	private Map<String, String> mapAppConfig;
	// 防止启动时用到的数据库连接与系统启动后在数据库连接信息中的id相同而在c3p0数据库连接池中的map混乱
	public static final String SerialVersionUID = "_fenggquo_2012_08_17";

	public static AppConfig getInstance() {
		if (appConfig == null)
			appConfig = new AppConfig();
		return appConfig;
	}

	// 构造加载程序配置信息
	private AppConfig() {
		try {
			this.listConfig = getListConfig(Const.XmlAppConfig);
			setMapAppConfig(this.listConfig);
		} catch (Exception e) {
			ShowMsg.showError("获取系统配置错误:" + e.getMessage());
		}
	}

	// 读取配置
	private List<Map<String, Object>> getListConfig(String filePath) throws FileNotFoundException, UnsupportedEncodingException, DocumentException {
		return UtilConver.xmlFileToList(filePath);
	}

	// 是否使用登陆界面
	public boolean isUseAppLog() {
		try {
			return Boolean.valueOf(this.mapAppConfig.get("useAppLogin"));
		} catch (Exception e) {
			return false;
		}
	}

	// 获取本程序的数据库连接
	public DbConnection getAppDbcon(String dbKey) {
		DbConnection dbcon = null;
		try {
			String dbId = "";
			for (int i = 0; i < listConfig.size(); i++) {
				Map<String, Object> map = listConfig.get(i);
				Iterator<?> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Entry<?, ?> entry = (Entry<?, ?>) it.next();
					if (dbKey.equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						dbId = entry.getValue().toString().trim();
						break;
					}
				}
				if (!"".equals(dbId))
					break;
			}

			for (int i = 0; i < listConfig.size(); i++) {
				Map<String, Object> map = listConfig.get(i);
				if (dbId.equalsIgnoreCase(UtilString.isNil(map.get("id")).trim())) {
					dbcon = new DbConnection();
					dbcon.setDbName(map.get("id").toString().trim() + SerialVersionUID);
					dbcon.setDbType(map.get("type").toString().trim());
					dbcon.setDbClassName(map.get("class").toString().trim());
					dbcon.setDbCon(map.get("link").toString().trim());
					dbcon.setDbUser(map.get("username").toString().trim());
					dbcon.setDbPassword(map.get("password").toString().trim());
					// System.out.println(dbcon.getDbName());
					// System.out.println(dbcon.getDbType());
					// System.out.println(dbcon.getDbClassName());
					// System.out.println(dbcon.getDbCon().replace("\n", ""));
					// System.out.println(dbcon.getDbUser());
					// System.out.println(dbcon.getDbPassword());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbcon;
	}

	// 读取mapAppConfig
	public Map<String, String> getMapAppConfig() {
		return mapAppConfig;
	}

	// 加载配置参数到mapAppConfig
	private void setMapAppConfig(List<Map<String, Object>> listConfig) {
		this.mapAppConfig = new HashMap<String, String>();
		try {
			for (int i = 0; i < listConfig.size(); i++) {
				Map<String, Object> map = listConfig.get(i);
				Iterator<?> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Entry<?, ?> entry = (Entry<?, ?>) it.next();
					if ("startJetty".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String startJetty = entry.getValue().toString().trim();
						this.mapAppConfig.put("startJetty", startJetty);
					} else if ("jettyPort".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String jettyPort = entry.getValue().toString().trim();
						this.mapAppConfig.put("jettyPort", jettyPort);
					} else if ("useAppLogin".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String useAppLogin = entry.getValue().toString().trim();
						this.mapAppConfig.put("useAppLogin", useAppLogin);
					} else if ("appUserName".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String userName = entry.getValue().toString().trim();
						this.mapAppConfig.put("appUserName", userName);
					} else if ("appPassword".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String password = entry.getValue().toString().trim();
						this.mapAppConfig.put("appPassword", UtilCrypt.getInstance().decryptAES(password, UtilCrypt.key));
					} else if ("appTitle".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String appTitle = entry.getValue().toString().trim();
						this.mapAppConfig.put("appTitle", appTitle);
					} else if ("appVersion".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String appVersion = entry.getValue().toString().trim();
						this.mapAppConfig.put("appVersion", appVersion);
					} else if ("appPublishDate".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String appPublishDate = entry.getValue().toString().trim();
						this.mapAppConfig.put("appPublishDate", appPublishDate);
					} else if ("appAutoLockTime".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String appAutoLockTime = entry.getValue().toString().trim();
						this.mapAppConfig.put("appAutoLockTime", appAutoLockTime);
					} else if ("appLookAndFeel".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String appLookAndFeel = entry.getValue().toString().trim();
						this.mapAppConfig.put("appLookAndFeel", appLookAndFeel);
					} else if ("appCopyRight".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String appCopyRight = entry.getValue().toString().trim();
						this.mapAppConfig.put("appCopyRight", appCopyRight);
					} else if ("appLockShortKey".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String appLockShortKey = entry.getValue().toString().trim().toUpperCase();
						this.mapAppConfig.put("appLockShortKey", appLockShortKey);
					} else if ("appUnDoShortKey".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String appUnDoShortKey = entry.getValue().toString().trim().toUpperCase();
						this.mapAppConfig.put("appUnDoShortKey", appUnDoShortKey);
					} else if ("appReDoShortKey".equalsIgnoreCase(UtilString.isNil(entry.getKey()).trim())) {
						String appReDoShortKey = entry.getValue().toString().trim().toUpperCase();
						this.mapAppConfig.put("appReDoShortKey", appReDoShortKey);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 更新版本号
	public void updateAppVersion(String version) {
		UtilXml.updateXml(Const.XmlAppConfig, Const.XmlAppConfig, "appVersion", version);
		AppConfig.getInstance().getMapAppConfig().put("appVersion", version);
	}
}
