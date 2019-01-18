package com.yqsh.diningsys.web.model.pay;

import java.math.BigDecimal;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 支付流水
 * @author jianglei
 * 日期 2017年1月10日 上午9:35:08
 *
 */
@SuppressWarnings("serial")
public class DgPayWater extends BasePojo{
	/**
	 * 支付状态：0，表示成功
	 */
	public static final String PAYSTATE_OK="0";
	/**
	 * 支付状态:1，表示失败
	 */
	public static final String PAYSTATE_FAIL="1";
	private String id;              //系统编号
	private String outTradeNo;      //商户订单号
	private String threeTradeNo;    //第三方订单号
	private BigDecimal payMoney;    //支付金额
	private String tradeDate;       //交易日期
	private String payType;         //充值类型：如支付宝(ZFB)、微信(WX)、银联等
	private String payState;        //第三方充值状态
	private String payPeopleInfo;   //充值人信息：如支付宝充值的手机号，微信openid等相关信息
	private String bankCard;         //银行卡号
	private String bankCardType;     //银行卡类型，如：借记卡，信用卡
	private String merchId;          //商户系统编号
	private String orderNo;          //店内流水号
	private String orgId;            //店面id
	private String remark;           //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getThreeTradeNo() {
		return threeTradeNo;
	}
	public void setThreeTradeNo(String threeTradeNo) {
		this.threeTradeNo = threeTradeNo;
	}
	public BigDecimal getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getPayPeopleInfo() {
		return payPeopleInfo;
	}
	public void setPayPeopleInfo(String payPeopleInfo) {
		this.payPeopleInfo = payPeopleInfo;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
