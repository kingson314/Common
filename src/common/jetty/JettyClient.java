package common.jetty;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class JettyClient {
	private HttpClient httpClient;
	private String ip;
	private int port;
	private String servletDo;
	private String url;

	public JettyClient(String ip, int port, String servletDo) {
		httpClient = new HttpClient();
		this.ip = ip;
		this.port = port;
		this.servletDo = servletDo;
		this.url = "http://" + this.ip + ":" + this.port + "/" + this.servletDo
				+ ".do";
	}

	// 执行各种参数的post方法
	public String call(Map<?, ?> param) throws HttpException, IOException {
		PostMethod post = getPostMethod(this.url, param);
		int rs = httpClient.executeMethod(post);
		String result = "";
		if (rs == 200) {
			result = post.getResponseBodyAsString();
		}
		return result;
	}

	public String call(NameValuePair param) throws HttpException, IOException {

		PostMethod post = getPostMethod(this.url, param);
		int rs = httpClient.executeMethod(post);
		String result = "";
		if (rs == 200) {
			result = post.getResponseBodyAsString();
		}
		return result;
	}

	public String call(NameValuePair[] param) throws HttpException, IOException {
		PostMethod post = getPostMethod(this.url, param);
		int rs = httpClient.executeMethod(post);
		String result = "";
		if (rs == 200) {
			result = post.getResponseBodyAsString();
		}
		return result;
	}

	public String call(String key, String value) throws HttpException,
			IOException {
		PostMethod post = getPostMethod(this.url, key, value);
		int rs = httpClient.executeMethod(post);
		String result = "";
		if (rs == 200) {
			result = post.getResponseBodyAsString();
		}
		return result;
	}

	public String call() throws HttpException, IOException {
		PostMethod post = new PostMethod(url);
		int rs = httpClient.executeMethod(post);
		String result = "";
		if (rs == 200) {
			result = post.getResponseBodyAsString();
		}
		return result;
	}

	// 创建不同参数的post方法
	
	@SuppressWarnings("deprecation")
	private PostMethod getPostMethod(String url, Map<?, ?> map) {
		PostMethod post = new PostMethod(url);
		if (map != null) {
			Iterator<?> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry<?, ?> entry = (Entry<?, ?>) it.next();
				post.addParameter(URLEncoder.encode(entry.getKey().toString()),
						URLEncoder.encode(entry.getValue().toString()));
			}
		}
		return post;
	}

	@SuppressWarnings("deprecation")
	private PostMethod getPostMethod(String url, NameValuePair nvp) {
		PostMethod post = new PostMethod(url);
		nvp.setName(URLEncoder.encode(nvp.getName()));
		nvp.setValue(URLEncoder.encode(nvp.getValue()));
		post.addParameter(nvp);
		return post;
	}

	@SuppressWarnings("deprecation")
	private PostMethod getPostMethod(String url, NameValuePair[] nvp) {
		PostMethod post = new PostMethod(url);
		for (int i = 0; i < nvp.length; i++) {
			nvp[i].setName(URLEncoder.encode(nvp[i].getName()));
			nvp[i].setValue(URLEncoder.encode(nvp[i].getValue()));
		}
		post.addParameters(nvp);
		return post;
	}

	@SuppressWarnings("deprecation")
	private PostMethod getPostMethod(String url, String key, String value) {
		PostMethod post = new PostMethod(url);
		post.addParameter(URLEncoder.encode(key), URLEncoder.encode(value));
		return post;
	}

}
