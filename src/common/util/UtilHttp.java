package common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import common.util.http.SSLClient;
import common.util.string.UtilString;
import net.sf.json.JSONObject;


@SuppressWarnings("deprecation")
public class UtilHttp {


    private final static String DEFAULT_ENCODE = "UTF-8";

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static JSONObject get(String url) throws Exception {
		JSONObject jsonObject = null;
		// DefaultHttpClient client = new DefaultHttpClient();
		SSLClient client = new SSLClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String result = EntityUtils.toString(entity, DEFAULT_ENCODE);
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}

    /**
	 * delete请求
	 *
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static String delete(String url) throws Exception {
		SSLClient client = new SSLClient();
        HttpDelete httpDelete = new HttpDelete(url);
        HttpResponse response = client.execute(httpDelete);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, DEFAULT_ENCODE);
	}

    /**
	 * post请求
	 *
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String postByJson(String url,Map<String, Object> params) throws Exception {
        return postByJson(url,params,DEFAULT_ENCODE);
	}

    /**
	 * post请求
	 *
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static String postByJson(String url,Map<String, Object> params,String encode) throws Exception {
        SSLClient client = new SSLClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject obj = new JSONObject();
        if(StringUtils.isEmpty(encode)){
            encode = DEFAULT_ENCODE;
        }
        if(params!=null){
            for(Map.Entry<String, Object>entry:params.entrySet()){
                obj.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        httpPost.setEntity(new StringEntity(obj.toString(),"application/json", encode));
        HttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, encode);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static JSONObject post(String url, Map<String, Object> params, String encode) throws Exception {
		// DefaultHttpClient client = new DefaultHttpClient();
		SSLClient client = new SSLClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encode));
		HttpResponse response = client.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		return JSONObject.fromObject(result);
	}

    /**
     * POST请求
     *
     * @param url
     * @param headers
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static JSONObject post(String url, Map<String, Object> params, String encode, Map<String, String> headers)
            throws Exception {
        return postTimeOut(url,params,encode,headers,null);
    }

    public static JSONObject postTimeOutByDefault(String url, Map<String, Object> params, String encode, Map<String, String> headers)
            throws Exception {
        HttpParams httpParams = new BasicHttpParams();
        //设置连接超时时间
        //设置请求超时2秒钟 根据业务调整
        Integer CONNECTION_TIMEOUT = 2 * 1000;
        //设置等待数据超时时间2秒钟 根据业务调整
        Integer SO_TIMEOUT = 2 * 1000;
        //定义了当从ClientConnectionManager中检索ManagedClientConnection实例时使用的毫秒级的超时时间
        //这个参数期望得到一个java.lang.Long类型的值。如果这个参数没有被设置，默认等于CONNECTION_TIMEOUT，因此一定要设置
        Long CONN_MANAGER_TIMEOUT = 500L; //该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大 ()
        httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
        httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
        httpParams.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);
        return postTimeOut(url,params,encode,headers,httpParams);
    }

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static JSONObject postTimeOut(String url, Map<String, Object> params, String encode, Map<String, String> headers,HttpParams httpParams)
			throws Exception {
		// DefaultHttpClient client = new DefaultHttpClient();
		SSLClient client = new SSLClient();
        if (httpParams != null){
            client.setParams(httpParams);
        }
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
		}
		// set headers
		// 默认提交application/x-www-form-urlencoded
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		if (headers != null) {
			Set<String> keys = headers.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				httpPost.setHeader(key, headers.get(key));
			}
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encode));
		HttpResponse response = client.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		return JSONObject.fromObject(result);
	}

	/**
	 * 包含数据类型
	 * @param url
	 * @param params
	 * @param encode
	 * @param headers
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static JSONObject post(String url,Map<String,Object>params, String encode,Map<String,String>headers,String dataType) throws Exception{
//		DefaultHttpClient client = new DefaultHttpClient();  
		SSLClient client = new SSLClient();
        HttpPost httpPost = new HttpPost(url);
        //set post data
        if("json".equals(dataType)){
            //set headers
            //默认提交application/x-www-form-urlencoded
        	httpPost.setHeader("Content-Type", "application/json");
        	JSONObject obj = new JSONObject();
        	if(params!=null){
    	        for(Map.Entry<String, Object>entry:params.entrySet()){
    	        	obj.put(entry.getKey(), String.valueOf(entry.getValue()));  
    	        }
            }
//        	System.out.println(obj.toString());
        	httpPost.setEntity(new StringEntity(obj.toString(), encode));
        } else {
            //set headers
            //默认提交application/x-www-form-urlencoded
        	httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
            if(params!=null){
    	        for(Map.Entry<String, Object>entry:params.entrySet()){
    	        	 nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));  
    	        }
            }
        	httpPost.setEntity(new UrlEncodedFormEntity(nvps, encode));
        }
        if (headers != null) {
			Set<String> keys = headers.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				httpPost.setHeader(key, headers.get(key));
			}
		}
        HttpResponse response = client.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		return JSONObject.fromObject(result);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject post(String url, Map<String, Object> params) throws Exception {
		return post(url, params, "UTF-8");
	}

	/**
	 * POST请求，只提交formdata，可以设置头部
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject post(String url, Map<String, Object> params, Map<String, String> headers)
			throws Exception {
		return post(url, params, "UTF-8", headers);
	}

	/**
	 * POST请求,可以提交json，可以设置头部
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	public static JSONObject post(String url, Map<String, Object> params, Map<String, String> headers, String dataType)
			throws Exception {
		return post(url, params, "UTF-8", headers, dataType);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param paramStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static JSONObject postStr(String url, String paramStr) throws ParseException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		httpost.setEntity(new StringEntity(paramStr, "UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		return JSONObject.fromObject(result);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
//	public static JSONObject postFile(String url, MultipartFile file) throws Exception {
//		JSONObject jsonObject = null;
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		try {
//			String fileName = file.getOriginalFilename();
//			HttpPost httpPost = new HttpPost();
//			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//			builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
//			builder.addTextBody("filename", fileName);// 类似浏览器表单提交，对应input的name和value
//			HttpEntity entity = builder.build();
//			httpPost.setEntity(entity);
//			HttpResponse response = httpClient.execute(httpPost);// 执行提交
//			HttpEntity responseEntity = response.getEntity();
//			if (responseEntity != null) {
//				String result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
//				jsonObject = JSONObject.fromObject(result);
//			}
//		} finally {
//			try {
//				httpClient.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return jsonObject;
//	}

	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String> rsMap = new HashMap<String, String>();
		Map<String, String[]> map = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String value = "";
			String[] valueObj = entry.getValue();
			String[] valueArr = (String[]) valueObj;
			for (int i = 0; i < valueArr.length; i++) {
				if ("".equals(value)) {
					value = valueArr[i];
				} else {
					value = value + "," + valueArr[i];
				}
			}
			String key = UtilString.isNil(entry.getKey());
			if (key.endsWith("[]")) {
				key = key.replace("[]", "");
			}
			rsMap.put(key, value);
		}
		return rsMap;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getMapByXml(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for (Element e : list) {
			// Runtime.log(new
			// String(e.getText().getBytes("gbk"),"utf-8")+"/"+new
			// String(e.getText().getBytes("utf-8"),"gbk"));
			// Runtime.log(new
			// String(e.getData().toString().getBytes("gbk"),"utf-8")+"/"+new
			// String(e.getData().toString().getBytes("utf-8"),"gbk"));
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}

	 /**
     * 保存Cookies
     * @param response
     * servlet请求
     * @param value
     */
    public static void setCookie(HttpServletResponse response, String name, String value,String domain, String path,int second) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(second);
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
    }
    
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    public static Map<String, Cookie> getCookie(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
    
    /**
     * 根据名字获取cookie
     * @param request
     * @param name
     * @return 
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = getCookie(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    
    public static  String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.indexOf(",") != -1) {
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
    
    
    /**
     * ascii码升序,MD5消息摘要
     * 从map集合转换为ascii码升序集合
     *
     * @param map    请求(an timestamp randomNum)
     * @param key 私有秘钥
     * @return
     */
    public static String sign(Map<String, String> map, String key) {
    Map<String, String> sortMap = new TreeMap<>();
      for (Map.Entry<String, String> entry : map.entrySet()) {
        if (!UtilString.isBlank(entry.getValue())) {
          sortMap.put(entry.getKey(), entry.getValue());
        }
      }
      StringBuilder sb = new StringBuilder();
      for (Map.Entry<String, String> keyValue : sortMap.entrySet()) {
        sb.append(keyValue.getKey()).append("=").append(keyValue.getValue()).append("&");
      }
      sb.append("key=").append(key);
      return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }

    
    @SuppressWarnings("resource")
	public static String download(String url, String filepath) {  
        try {  
            HttpClient client = new DefaultHttpClient();  
            HttpGet httpget = new HttpGet(url);  
            HttpResponse response = client.execute(httpget);  
            HttpEntity entity = response.getEntity();  
            InputStream is = entity.getContent();  
            File file = new File(filepath);  
            file.getParentFile().mkdirs();  
            FileOutputStream fileout = new FileOutputStream(file);  
            /** 
             * 根据实际运行效果 设置缓冲区大小 
             */  
            byte[] buffer=new byte[10 * 1024];  
            int ch = 0;  
            while ((ch = is.read(buffer)) != -1) {  
                fileout.write(buffer,0,ch);  
            }  
            is.close();  
            fileout.flush();  
            fileout.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
    
	public static void main(String[] args) throws Exception {
		System.out.println(new Date().getTime());
		
//		Map<String,String> mapSign=new HashMap<String,String>();
//		mapSign.put("an", "CN201721398837.1");
//		mapSign.put("timestamp", String.valueOf(new Date().getTime()));
//		mapSign.put("randomNum", UtilMath.getRandom(6));
//		String sign=sign(mapSign,"90cac82f-7858-4cd2-866d-78efc7f8b158");
//		String url = "https://www.incopat.com/detail/hxjzdInterface.json";
//		try {
//			JSONObject obj = post(url, UtilCollection.getMap("sign",sign,"an",mapSign.get("an"),"timestamp",mapSign.get("timestamp"),"randomNum",mapSign.get("randomNum")));
//			System.out.println(obj.toString());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		String url="http://bulletin.cebpubservice.com/project//2019-08/noticeFile/Z3701000077004506001/24185e8eaf634702aa5f5f3b02a1212f.swf";
		download(url, "/Volumes/Docs/956.swf");
	}

}
