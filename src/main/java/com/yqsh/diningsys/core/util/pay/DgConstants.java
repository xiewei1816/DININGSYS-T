package com.yqsh.diningsys.core.util.pay;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;


public class DgConstants {

	private static Log log = LogFactory.getLog(DgConstants.class);
	/**
	 * 店内付款方式:0表示会员卡付款
	 */
	public static String STORE_PAYMENT_TYPE_ZERO="0";
	/**
	 * 店内付款方式:1表示客户挂账
	 */
	public static String STORE_PAYMENT_TYPE_ONE="1";
	/**
	 * 店内付款方式:2表示优惠券消费
	 */
	public static String STORE_PAYMENT_TYPE_TWO="2";
	/**
	 * 充值类型:会员卡支付：vip
	 */
	public static String PAY_TYPE_VIP="vip";
	/**
	 * 充值类型:微信：wx
	 */
	public static String PAY_TYPE_WX="wx";
	/**
	 * 充值类型:支付宝:zfb
	 */
	public static String PAY_TYPE_ZFB="zfb";
	/**
	 * 充值类型:银联卡:ylk
	 */
	public static String PAY_TYPE_YLK="ylk";
	/**
	 * 充值类型：现金:xj
	 */
	public static String PAY_TYPE_XJ="xj";
	/**
	 * 充值类型：赠送:zs
	 */
	public static String PAY_TYPE_ZS="zs";
	/**
	 * 类型：优惠券:yhq
	 */
	public static String PAY_TYPE_YHQ="yhq";
	
	/**
	 * 支付宝应用网关
	 */
	public static String ZFB_MCLOUDAPI_URL="http://mcloudmonitor.com/gateway.do";
	/**
	 * 支付宝:支付宝网关名,沙箱地址
	 */
//	public static String ZFB_ALIPAYDEV_URL="https://openapi.alipaydev.com/gateway.do";
	/**
	 * 支付宝:支付宝网关名,正式地址
	 */
	public static String ZFB_ALIPAY_URL="https://openapi.alipay.com/gateway.do";
	/**
	 * 微信：商户微信退款接口
	 */
	public static String WX_REFUND_URL="https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	/**
	 * 微信支付:下载对账单
	 */
	public static String DOWNLOADBILL_URL="https://api.mch.weixin.qq.com/pay/downloadbill";
	/**
	 * 微信支付：撤销订单
	 */
	public static String REVERSE_URL="https://api.mch.weixin.qq.com/secapi/pay/reverse";
	/**
	 * 微信支付：订单查询
	 */
	public static String ORDERQUERY_URL="https://api.mch.weixin.qq.com/pay/orderquery";
	/**
	 * 微信支付:刷卡支付API
	 */
	public static String MICROPAY_URL="https://api.mch.weixin.qq.com/pay/micropay";
	/**
	 * 微信支付：统一下单地址
	 */
	public static String UNIFIEDORDER_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
	/**
	 * ULINE 微信快捷支付
	 */
	public static String ULINE_WX_MICROPAY_URL = "/wechat/orders/micropay";
	
	/**
	 * ULINE 支付宝快捷支付
	 */
	public static String ULINE_ZFB_MICROPAY_URL = "/alipay/orders/micropay";
	
	/**
	 * ULINE微信支付：订单查询
	 */
	public static String ULINE_ORDERQUERY_URL="/wechat/orders/query";
	/**
	 * 返回状态:200,表示成功
	 */
	public static String RES_MEG="200";
	/**
	 * 报文类型：查询  ==》如查询一卡通信息或其他
	 */
	public static String NOTICE_TYPE_SELECT_INFO="YP0001";
	/**
	 * 报文类型：充值通知  
	 */
	public static String NOTICE_TYPE_PAY_NOTICE="YP0002";
	/**
	 * 报文类型：退款
	 */
	public static String NOTICE_TYPE_REFUND="YP0003";
	/**
	 * 支付方式：扫码支付:qrCode
	 */
	public static String QR_CODE="qrCode";
	/**
	 * 支付方式:刷卡支付:slotCard
	 */
	public static String SLOT_CARD="slotCard";
	/**
	 * 微信支付类型:公众号支付 JSAPI
	 */
	public static String WX_PAY_TYPE_JSAPI="JSAPI";
	/**
	 * 微信支付类型：原生扫码支付
	 */
	public static String WX_PAY_TYPE_NATIVE="NATIVE";
	/**
	 * 微信支付类型:app支付
	 */
	public static String WX_PAY_TYPE_APP="APP";
	/**
	 * 微信支付类:刷卡支付
	 */
	public static String WX_PAY_TYPE_MICROPAY="MICROPAY";
	/**
	 * 正则表达：大小写字符或数字
	 */
	public static String REGULAR_LETTER_NUM="^[a-zA-Z0-9]+$";
	/**
	 * 正则表达式数字
	 */
	public static String REGULAR_NUM="^[0-9]+$";
	/**
	 * 非零正整数据
	 */
	public static String REG_NZ_NUM="^[1-9]\\d*$";
	/**
	 * 有1-2位小数的正实数
	 */
	public static String REGULAR_ARIT_NUM="^[0-9]+(.[0-9]{1,2})?$";
	/**
	 * 正则表达式 手机
	 */
	public static final String REGULAR_MODILE_PHONE = "^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(16[0-9]))\\d{8}$";
	/**
	 * 正则表达式 邮箱
	 */
	public static final String REGULAR_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	/**
	 * 微信支付账单类型:ALL表示返回当日所有订单信息，默认值
	 */
	public static String BILL_TYPE_ALL="ALL";
	/**
	 * 微信支付账单类型:返回当日成功支付的订单
	 */
	public static String BILL_TYPE_SUCCESS="SUCCESS";
	/**
	 * 微信支付账单类型:返回当日退款订单
	 */
	public static String BILL_TYPE_REFUND="REFUND";
	/**
	 * 支付宝:当面付最大查询次数
	 */
	public static int MAX_QUERY_RETRY=5;
	/**
	 * 支付宝:查询间隔（毫秒）
	 */
	public static long QUERY_DURATION=5000;
	/**
	 * 支付宝:当面付最大撤销次数
	 */
	public static int MAX_CANCEL_RETRY=3;
	/**
	 * 支付宝:撤销间隔（毫秒）
	 */
	public static long CANCEL_DURATION=2000;
	/**
	 * 支付宝:交易保障线程第一次调度延迟
	 */
	public static long HEARTBEAT_DELAY=5;
	/**
	 * 支付宝:交易保障线程调度间隔（秒）
	 */
	public static long HEARTBEAT_DURATION=900;
	
	/**
	 * 获取 base.properties文件中对应key的值
	 * @author jianglei
	 * 2016年7月14日 下午1:48:15
	 * @param key
	 * @return
	 */
	public static String getBasePropertiesValue(String key){
		File cfgFile;
		Properties pros = new Properties();
		try {
			cfgFile = ResourceUtils.getFile("classpath:config/base.properties");
			pros.load(new FileInputStream(cfgFile));
		} catch (IOException e) {
			log.info("===getBasePropertiesValue method===", e);
		}
	    String value=pros.getProperty(key).trim();
	  return value;
	}

	/**
	 * 获取 application.properties文件中对应key的值
	 * @author jianglei
	 * 2016年7月14日 下午1:48:15
	 * @param key
	 * @return
	 */
	public static String getApplicationPropertiesValue(String key){
		File cfgFile;
		Properties pros = new Properties();
		try {
			cfgFile = ResourceUtils.getFile("classpath:application.properties");
			pros.load(new FileInputStream(cfgFile));
		} catch (IOException e) {
			log.info("===getApplicationPropertiesValue method===", e);
		}
	    String value=pros.getProperty(key).trim();
	  return value;
	}
	/**
	* 获取ip地址
	* @param request
	* @return
	*/
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")||ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					log.info("===getIpAddr method===", e);
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

}
