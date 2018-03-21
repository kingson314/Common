package common.myBatis;

import java.util.Properties;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import common.util.security.UtilCrypt;

//mybatis数据库加密
public class EncryptPoolDs extends PooledDataSourceFactory {

	@Override
	public void setProperties(Properties prop) {
		String password = prop.getProperty("password");
		// System.out.println(password);
		String psw = UtilCrypt.getInstance().decryptAES(password, UtilCrypt.key);
		prop.setProperty("password", psw);
		super.setProperties(prop);
		// System.out.println(prop.get("password"));
	}
}
