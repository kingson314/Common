package common.myBatis;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import common.util.log.UtilLog;

public class Session {
	private final static String sqlMapConfig = System.getProperty("user.dir") + "/xml/sqlMapConfig.xml";
	private static ConcurrentHashMap<String, SqlSessionFactory> mapSqlSessionFactory = new ConcurrentHashMap<String, SqlSessionFactory>();

	// public static synchronized Object getDataDao(String dbName, Class<?> c) {
	// SqlSession sqlSession = getSqlSessionFactory(dbName).openSession();
	//
	// Object dataDaoMapper = sqlSession.getMapper(c);
	// return dataDaoMapper;
	// }
	// 更加数据库连接名称获取Session
	public static synchronized SqlSessionFactory getSqlSessionFactory(String dbName) {
		SqlSessionFactory sqlSessionFactory = mapSqlSessionFactory.get(dbName);
		if (sqlSessionFactory != null)
			return sqlSessionFactory;

		InputStream instream = null;
		BufferedInputStream bufInStream = null;
		Reader reader = null;
		try {
			instream = new FileInputStream(sqlMapConfig);
			// System.out.println(sqlMapConfig);
			bufInStream = new BufferedInputStream(instream);
			reader = new InputStreamReader(bufInStream);
			// reader = Resources.getResourceAsReader(sqlMapConfig);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, dbName);
			mapSqlSessionFactory.put(dbName, sqlSessionFactory);
		} catch (Exception e) {
			e.printStackTrace();
			UtilLog.logError("[CommonFun]从数据库环境配置得到数据库连接错误", e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return sqlSessionFactory;
	}
	// public static void main(String[] args) {
	// Session.getSqlSessionFactory("otc");
	// }
}
