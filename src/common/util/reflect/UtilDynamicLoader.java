package common.util.reflect;

import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.net.URL;

public class UtilDynamicLoader extends URLClassLoader {

	private static UtilDynamicLoader loader = null;

	private UtilDynamicLoader() {
		super(new URL[0], UtilDynamicLoader.class.getClassLoader());
	}

	/**
	 * 返回新的jar loader
	 * 
	 * @return
	 */
	public static UtilDynamicLoader getInstance() {
		if (loader == null)
			loader = new UtilDynamicLoader();
		return loader;
	}

	// 加载jar路径
	public void addURL(String vurl) throws MalformedURLException {
		StringBuilder url = new StringBuilder("jar:file:///"
				+ System.getProperty("user.dir") + "/" + vurl + "!/");
		this.addURL(new URL(url.toString()));
	}
}
