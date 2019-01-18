package com.yqsh.diningsys.core.util.sgds;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.yqsh.diningsys.core.util.pay.MD5Util;

/**
 * 蜀国大师接口请求类
 * @author xiewei
 * 日期 2017年9月27日 下午13:12:54
 */
public class RequestSgdsHandler {
	
	/**
	 * 蜀国大师参数拼接
	 * 规则是:按参数名称a-z排序,遇到空值的参数不参加拼接。
	 */
	@SuppressWarnings("rawtypes")
	public String createSgdsParams(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		return sb.toString();
	}

	/**
	 * 蜀国大师
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名 。
	 */
	@SuppressWarnings("rawtypes")
	public String createSgdsSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v);
			}
		}
		sb.append("secret=" + DgSgdsConstants.appSecret);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8")
				.toLowerCase();
		return sign;
	}

	/**
	 * 蜀国大师会员积分组装参数
	 * @param dgSgdsIntegral
	 * 
	 * @return
	 */
	public SortedMap<String,String> createSgdsParams(DgSgdsIntegral dgSgdsIntegral) {
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		if(dgSgdsIntegral!=null){
			if(StringUtils.isNotBlank(dgSgdsIntegral.getAccessToken())){
				packageParams.put("accessToken", dgSgdsIntegral.getAccessToken());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getTimestamp())){
				packageParams.put("timestamp", dgSgdsIntegral.getTimestamp());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getSign())){
				packageParams.put("sign", dgSgdsIntegral.getSign());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getMethod())){
				packageParams.put("method", dgSgdsIntegral.getMethod());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getVersion())){
				packageParams.put("version", dgSgdsIntegral.getVersion());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getMobile())){
				packageParams.put("mobile", dgSgdsIntegral.getMobile());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getSerialNumber())){
				packageParams.put("SerialNumber", dgSgdsIntegral.getSerialNumber());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getAmount())){
				packageParams.put("amount", dgSgdsIntegral.getAmount());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getAppid())){
				packageParams.put("AppID", dgSgdsIntegral.getAppid());
			}
		}
		return packageParams;
	}

	/**
	 * 蜀国大师获取签名数据 by Token.GetAccessToken
	 * @param dgSgdsIntegral
	 * 
	 * @return
	 */
	public SortedMap<String,String> returnCreateSgdsSignByToken(DgSgdsIntegral dgSgdsIntegral){
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		if(dgSgdsIntegral!=null){
			if(StringUtils.isNotBlank(dgSgdsIntegral.getAccessToken())){
				packageParams.put("access_token", dgSgdsIntegral.getAccessToken());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getTimestamp())){
				packageParams.put("timestamp", dgSgdsIntegral.getTimestamp());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getSign())){
				packageParams.put("sign", dgSgdsIntegral.getSign());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getMethod())){
				packageParams.put("method", dgSgdsIntegral.getMethod());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getVersion())){
				packageParams.put("version", dgSgdsIntegral.getVersion());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getAppid())){
				packageParams.put("appid", dgSgdsIntegral.getAppid());
			}
		}
		return packageParams;
	}
	
	/**
	 * 蜀国大师获取签名数据 by User.AddIntegralByMobile
	 * @param dgSgdsIntegral
	 * 
	 * @return
	 */
	public SortedMap<String,String> returnCreateSgdsSignByUser(DgSgdsIntegral dgSgdsIntegral){
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		if(dgSgdsIntegral!=null){
			if(StringUtils.isNotBlank(dgSgdsIntegral.getAccessToken())){
				packageParams.put("accesstoken", dgSgdsIntegral.getAccessToken());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getTimestamp())){
				packageParams.put("timestamp", dgSgdsIntegral.getTimestamp());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getSign())){
				packageParams.put("sign", dgSgdsIntegral.getSign());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getMethod())){
				packageParams.put("method", dgSgdsIntegral.getMethod());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getVersion())){
				packageParams.put("version", dgSgdsIntegral.getVersion());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getMobile())){
				packageParams.put("mobile", dgSgdsIntegral.getMobile());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getSerialNumber())){
				packageParams.put("serialnumber", dgSgdsIntegral.getSerialNumber());
			}
			if(StringUtils.isNotBlank(dgSgdsIntegral.getAmount())){
				packageParams.put("amount", dgSgdsIntegral.getAmount());
			}
		}
		return packageParams;
	}

}
