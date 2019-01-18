package com.yqsh.diningsys.web.model.pay;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 商户管理实体
 * @author jianglei
 * 日期 2017年1月10日 下午1:16:06
 *
 */
@SuppressWarnings("serial")
public class DgMerchants extends BasePojo{
	private String id;             //系统编号
	private String nickName;       //名称
	private String orgId;      //所属公司id
	private String remark;         //备注
	
	private String wxAppId;        //公众号应用id
	private String wxAppSecret;    //应用密匙
	private String wxApiSecretKey; //API密钥(服务商或普通商户)
	private String wxMchId;        //微信商户号
	
	//支付宝相关
	private String zfbPid;                //合作者身份标识
	private String zfbAppid;              //支付宝应用id
	private String zfbPrivateKey;        //商户私钥且转PKCS8格式
	private String zfbPublicKey;         //商户公钥 
	private String zfbAlipayPublicKey;  //支付宝公钥

	//2018-12-10
	private String threePartyPayment; // 三方支付
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWxAppId() {
		return wxAppId;
	}
	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}
	public String getWxAppSecret() {
		return wxAppSecret;
	}
	public void setWxAppSecret(String wxAppSecret) {
		this.wxAppSecret = wxAppSecret;
	}
	public String getWxApiSecretKey() {
		return wxApiSecretKey;
	}
	public void setWxApiSecretKey(String wxApiSecretKey) {
		this.wxApiSecretKey = wxApiSecretKey;
	}
	public String getWxMchId() {
		return wxMchId;
	}
	public void setWxMchId(String wxMchId) {
		this.wxMchId = wxMchId;
	}
	public String getZfbPid() {
		return zfbPid;
	}
	public void setZfbPid(String zfbPid) {
		this.zfbPid = zfbPid;
	}
	public String getZfbAppid() {
		return zfbAppid;
	}
	public void setZfbAppid(String zfbAppid) {
		this.zfbAppid = zfbAppid;
	}
	public String getZfbPrivateKey() {
		return zfbPrivateKey;
	}
	public void setZfbPrivateKey(String zfbPrivateKey) {
		this.zfbPrivateKey = zfbPrivateKey;
	}
	public String getZfbPublicKey() {
		return zfbPublicKey;
	}
	public void setZfbPublicKey(String zfbPublicKey) {
		this.zfbPublicKey = zfbPublicKey;
	}
	public String getZfbAlipayPublicKey() {
		return zfbAlipayPublicKey;
	}
	public void setZfbAlipayPublicKey(String zfbAlipayPublicKey) {
		this.zfbAlipayPublicKey = zfbAlipayPublicKey;
	}

	public String getThreePartyPayment() {
		return threePartyPayment;
	}

	public void setThreePartyPayment(String threePartyPayment) {
		this.threePartyPayment = threePartyPayment;
	}
}
