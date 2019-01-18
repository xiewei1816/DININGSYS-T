package com.yqsh.diningsys.core.util.pay;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.yqsh.diningsys.web.model.pay.Dg_WxUnifiedOrderInfo;
import com.yqsh.diningsys.web.model.pay.MicropayInfo;

/*
 '微信支付服务器签名支付请求请求类
 '============================================================================
 'api说明：
 'init(app_id, app_secret, partner_key, app_key);
 '初始化函数，默认给一些参数赋值，如cmdno,date等。
 'setKey(key_)'设置商户密钥
 'getLasterrCode(),获取最后错误号
 'GetToken();获取Token
 'getTokenReal();Token过期后实时获取Token
 'createMd5Sign(signParams);生成Md5签名
 'genPackage(packageParams);获取package包
 'createSHA1Sign(signParams);创建签名SHA1
 'sendPrepay(packageParams);提交预支付
 'getDebugInfo(),获取debug信息
 '============================================================================
 '*/
public class RequestHandler {
	/** Token获取网关地址地址 */
	@SuppressWarnings("unused")
	private String tokenUrl;
	/** 预支付网关url地址 */
	private String gateUrl;
	/** 查询支付通知网关URL */
	@SuppressWarnings("unused")
	private String notifyUrl;
	/** 商户参数 */
	@SuppressWarnings("unused")
	private String appid;
	@SuppressWarnings("unused")
	private String appkey;
	@SuppressWarnings("unused")
	private String partnerkey;
	@SuppressWarnings("unused")
	private String appsecret;
	private String key;
	/** 请求的参数 */
	@SuppressWarnings("rawtypes")
	private SortedMap parameters;
	/** Token */
	@SuppressWarnings("unused")
	private String Token;
	private String charset;
	/** debug信息 */
	private String debugInfo;
	private String last_errcode;

	private HttpServletRequest request;

	private HttpServletResponse response;
	//无参构造函数
	public RequestHandler() {
		
	}
	/**
	 * 初始构造函数。
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public RequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		this.last_errcode = "0";
		this.request = request;
		this.response = response;
		this.charset = "UTF-8";
		this.parameters = new TreeMap();
		// 验证notify支付订单网关
		notifyUrl = "https://gw.tenpay.com/gateway/simpleverifynotifyid.xml";
		
	}

	/**
	 * 初始化函数。
	 */
	public void init(String app_id, String app_secret,	String partner_key) {
		this.last_errcode = "0";
		this.Token = "token_";
		this.debugInfo = "";
		this.appid = app_id;
		this.partnerkey = partner_key;
		this.appsecret = app_secret;
		this.key = partner_key;
	}

	public void init() {
	}

	/**
	 * 获取最后错误号
	 */
	public String getLasterrCode() {
		return last_errcode;
	}

	/**
	 *获取入口地址,不包含参数值
	 */
	public String getGateUrl() {
		return gateUrl;
	}

	/**
	 * 获取参数值
	 * 
	 * @param parameter
	 *            参数名称
	 * @return String
	 */
	public String getParameter(String parameter) {
		String s = (String) this.parameters.get(parameter);
		return (null == s) ? "" : s;
	}

	
	 //设置密钥
	
	public void setKey(String key) {
		this.partnerkey = key;
	}
	//设置微信密钥
	public void  setAppKey(String key){
		this.appkey = key;
	}
	
	// 特殊字符处理
	public String UrlEncode(String src) throws UnsupportedEncodingException {
		return URLEncoder.encode(src, this.charset).replace("+", "%20");
	}

	// 获取package的签名包
	@SuppressWarnings("rawtypes")
	public String genPackage(SortedMap<String, String> packageParams)
			throws UnsupportedEncodingException {
		String sign = createSign(packageParams);

		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + UrlEncode(v) + "&");
		}

		// 去掉最后一个&
		String packageValue = sb.append("sign=" + sign).toString();
//		System.out.println("UrlEncode后 packageValue=" + packageValue);
		return packageValue;
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	@SuppressWarnings("rawtypes")
	public String createSign(SortedMap<String, String> packageParams) {
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
		sb.append("key=" + this.getKey());
		String sign = MD5Util.MD5Encode(sb.toString(), this.charset)
				.toUpperCase();
		return sign;

	}
	/**
	 * 创建package签名
	 */
	@SuppressWarnings("rawtypes")
	public boolean createMd5Sign(String signParams) {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		// 算出摘要
		String enc = TenpayUtil.getCharacterEncoding(this.request,
				this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();

		String tenpaySign = this.getParameter("sign").toLowerCase();

		// debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign + " tenpaySign:"
				+ tenpaySign);

		return tenpaySign.equals(sign);
	}

	/**
	 * 组装需要签名的数据
	 * @author jianglei
	 * 2016年7月14日 下午5:46:56
	 * @param orderInfo
	 * @return
	 */
	public SortedMap<String,String> returnCreateSign(Dg_WxUnifiedOrderInfo orderInfo){
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		if(orderInfo!=null){
			if(StringUtils.isNotBlank(orderInfo.getAppid())){
				packageParams.put("appid", orderInfo.getAppid());
			}
			if(StringUtils.isNotBlank(orderInfo.getMch_id())){
				packageParams.put("mch_id", orderInfo.getMch_id());
			}
			if(StringUtils.isNotBlank(orderInfo.getSub_appid())){
				packageParams.put("sub_appid", orderInfo.getSub_appid());
			}
			if(StringUtils.isNotBlank(orderInfo.getSub_mch_id())){
				packageParams.put("sub_mch_id", orderInfo.getSub_mch_id());
			}
			if(StringUtils.isNotBlank(orderInfo.getDevice_info())){
				packageParams.put("device_info", orderInfo.getDevice_info());
			}
			if(StringUtils.isNotBlank(orderInfo.getNonce_str())){
				packageParams.put("nonce_str", orderInfo.getNonce_str());
			}
			if(StringUtils.isNotBlank(orderInfo.getBody())){
				packageParams.put("body", orderInfo.getBody());
			}
			if(StringUtils.isNotBlank(orderInfo.getDetail())){
				packageParams.put("detail", orderInfo.getDetail());
			}
			if(StringUtils.isNotBlank(orderInfo.getAttach())){
				packageParams.put("attach", orderInfo.getAttach());
			}
			if(StringUtils.isNotBlank(orderInfo.getOut_trade_no())){
				packageParams.put("out_trade_no", orderInfo.getOut_trade_no());
			}
			if(StringUtils.isNotBlank(orderInfo.getFee_type())){
				packageParams.put("fee_type", orderInfo.getFee_type());
			}
			if(StringUtils.isNotBlank(orderInfo.getTotal_fee())){
				packageParams.put("total_fee", orderInfo.getTotal_fee());
			}
			if(StringUtils.isNotBlank(orderInfo.getSpbill_create_ip())){
				packageParams.put("spbill_create_ip", orderInfo.getSpbill_create_ip());
			}
			if(StringUtils.isNotBlank(orderInfo.getTime_start())){
				packageParams.put("time_start", orderInfo.getTime_start());
			}
			if(StringUtils.isNotBlank(orderInfo.getTime_expire())){
				packageParams.put("time_expire", orderInfo.getTime_expire());
			}
			if(StringUtils.isNotBlank(orderInfo.getGoods_tag())){
				packageParams.put("goods_tag", orderInfo.getGoods_tag());
			}
			if(StringUtils.isNotBlank(orderInfo.getNotify_url())){
				packageParams.put("notify_url", orderInfo.getNotify_url());
			}
			if(StringUtils.isNotBlank(orderInfo.getTrade_type())){
				packageParams.put("trade_type", orderInfo.getTrade_type());
			}
			if(StringUtils.isNotBlank(orderInfo.getProduct_id())){
				packageParams.put("product_id", orderInfo.getProduct_id());
			}
			if(StringUtils.isNotBlank(orderInfo.getLimit_pay())){
				packageParams.put("limit_pay", orderInfo.getLimit_pay());
			}
			if(StringUtils.isNotBlank(orderInfo.getOpenid())){
				packageParams.put("openid", orderInfo.getOpenid());
			}
			if(StringUtils.isNotBlank(orderInfo.getSub_openid())){
				packageParams.put("sub_openid", orderInfo.getSub_openid());
			}
			if(StringUtils.isNotBlank(orderInfo.getAuth_code())){
				packageParams.put("auth_code", orderInfo.getAuth_code());
			}
			if(StringUtils.isNotBlank(orderInfo.getBill_type())){
				packageParams.put("bill_type", orderInfo.getBill_type());
			}
			if(StringUtils.isNotBlank(orderInfo.getBill_date())){
				packageParams.put("bill_date", orderInfo.getBill_date());
			}
			if(StringUtils.isNotBlank(orderInfo.getRefund_fee())){
				packageParams.put("refund_fee", orderInfo.getRefund_fee());
			}
			if(StringUtils.isNotBlank(orderInfo.getOut_refund_no())){
				packageParams.put("out_refund_no", orderInfo.getOut_refund_no());
			}
			if(StringUtils.isNotBlank(orderInfo.getOp_user_id())){
				packageParams.put("op_user_id", orderInfo.getOp_user_id());
			}
		}
		return packageParams;
	}
	
	
	/**
	 * 组装需要签名的数据
	 * @author hs
	 * 2016年7月14日 下午5:46:56
	 * @param orderInfo
	 * @return
	 */
	public SortedMap<String,String> returnCreateSign(MicropayInfo orderInfo){
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		if(orderInfo!=null){
			if(StringUtils.isNotBlank(orderInfo.getMch_id())){
				packageParams.put("mch_id", orderInfo.getMch_id());
			}
			if(StringUtils.isNotBlank(orderInfo.getDevice_info())){
				packageParams.put("device_info", orderInfo.getDevice_info());
			}
			if(StringUtils.isNotBlank(orderInfo.getNonce_str())){
				packageParams.put("nonce_str", orderInfo.getNonce_str());
			}
			if(StringUtils.isNotBlank(orderInfo.getBody())){
				packageParams.put("body", orderInfo.getBody());
			}
			if(StringUtils.isNotBlank(orderInfo.getDetail())){
				packageParams.put("detail", orderInfo.getDetail());
			}
			if(StringUtils.isNotBlank(orderInfo.getAttach())){
				packageParams.put("attach", orderInfo.getAttach());
			}
			if(StringUtils.isNotBlank(orderInfo.getOut_trade_no())){
				packageParams.put("out_trade_no", orderInfo.getOut_trade_no());
			}
			if(StringUtils.isNotBlank(orderInfo.getFee_type())){
				packageParams.put("fee_type", orderInfo.getFee_type());
			}
			if(StringUtils.isNotBlank(orderInfo.getTotal_fee())){
				packageParams.put("total_fee", orderInfo.getTotal_fee());
			}
			if(StringUtils.isNotBlank(orderInfo.getSpbill_create_ip())){
				packageParams.put("spbill_create_ip", orderInfo.getSpbill_create_ip());
			}
			if(StringUtils.isNotBlank(orderInfo.getGoods_tag())){
				packageParams.put("goods_tag", orderInfo.getGoods_tag());
			}
			if(StringUtils.isNotBlank(orderInfo.getLimit_pay())){
				packageParams.put("limit_pay", orderInfo.getLimit_pay());
			}
			if(StringUtils.isNotBlank(orderInfo.getAuth_code())){
				packageParams.put("auth_code", orderInfo.getAuth_code());
			}
			if(StringUtils.isNotBlank(orderInfo.getNotify_url())){
				packageParams.put("notify_url", orderInfo.getNotify_url());
			}
		}
		return packageParams;
	}

    //输出XML
	   @SuppressWarnings("rawtypes")
	public String parseXML() {
		   StringBuffer sb = new StringBuffer();
	       sb.append("<xml>");
	       Set es = this.parameters.entrySet();
	       Iterator it = es.iterator();
	       while(it.hasNext()) {
				Map.Entry entry = (Map.Entry)it.next();
				String k = (String)entry.getKey();
				String v = (String)entry.getValue();
				if(null != v && !"".equals(v) && !"appkey".equals(k)) {
					
					sb.append("<" + k +">" + getParameter(k) + "</" + k + ">\n");
				}
			}
	       sb.append("</xml>");
			return sb.toString();
		}

	/**
	 * 设置debug信息
	 */
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}
	public String getDebugInfo() {
		return debugInfo;
	}
	public String getKey() {
		return key;
	}

}
