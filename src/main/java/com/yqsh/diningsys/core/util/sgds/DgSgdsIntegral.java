package com.yqsh.diningsys.core.util.sgds;

import java.io.Serializable;

/**
 * 蜀国大师会员积分信息类
 * @author xiewei
 * 日期 2017年9月26日 下午14:08:25
 *
 */
@SuppressWarnings("serial")
public class DgSgdsIntegral implements Serializable{

	/**
	 * 系统级别参数
	 */
	private String accessToken;     //用户的令牌，通过授权获取
	private String timestamp;       //时间戳(Unix timestamp) ,格式：1468920420
	private String sign;            //签名字符串
	private String method;          //API接口名称 - User.AddIntegralByMobile
	private String version;         //接口版本号(1.0版本传入1即可)
	
	/**
	 * 应用级别参数
	 * Token.GetAccessToken
	 */
	private String appid;           //用户唯一凭证
	/**
	 * 应用级别参数
	 * User.AddIntegralByMobile
	 */
	private String mobile;          //会员的手机号
	private String serialNumber;    //第三方系统唯一消费流水号
	private String amount;          //消费金额
	
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

}
