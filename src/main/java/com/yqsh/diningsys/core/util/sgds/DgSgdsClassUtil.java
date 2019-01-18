package com.yqsh.diningsys.core.util.sgds;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yqsh.diningsys.core.util.pay.MyX509TrustManager;

import net.sf.json.JSONObject;
/**
 * 工具类
 * @author xiewei
 * 日期 2017年9月27日 上午14:21:26
 *
 */
public class DgSgdsClassUtil {
	private static Log log = LogFactory.getLog(DgSgdsClassUtil.class);

	/**
	 * 发起https请求或者http请求并获取json数组集合
	 * 
	 * @author xiewei 2017年9月27日 上午14:21:26
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	public static JSONObject httpRequestArray(String requestUrl, String requestMethod, String outputStr) {
		String str = null;
		if (StringUtils.isNotBlank(requestUrl)) {
			if (requestUrl.contains("https://")) {
				str = publicHttpRequest(requestUrl, requestMethod, outputStr);
			} else {
				str = httpPublicRequest(requestUrl, requestMethod, outputStr);
			}
		}
		JSONObject jsonArrObject = null;
		if (StringUtils.isNotBlank(str)) {
			jsonArrObject = JSONObject.fromObject(str);
		}
		return jsonArrObject;
	}
	
	/**
	 * 提取发起请求公共部分
	 * 
	 * @author xiewei 2017年9月27日 上午14:21:26
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	private static String publicHttpRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		String msgStr = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("TLSv1");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setRequestProperty("content-type", "text/html");
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("utf-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			msgStr = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("===publicHttpRequest method===", e);
		}
		return msgStr;
	}

	/**
	 * 发起http请求公共部分
	 * 
	 * @author xiewei 2017年9月27日 上午14:21:26
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	private static String httpPublicRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		String msgStr = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setRequestProperty("content-type", "text/html");
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("utf-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			msgStr = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("===httpPublicRequest method===", e);
		}
		return msgStr;
	}

	
}
