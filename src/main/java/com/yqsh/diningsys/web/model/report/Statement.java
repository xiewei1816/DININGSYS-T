package com.yqsh.diningsys.web.model.report;

import java.util.List;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 结账单查询
* @author xiewei
* @version 创建时间：2017年8月10日 上午11:28:03
 */
public class Statement extends BasePojo{
	
	/**
	 * 数据字段
	 */
	// line-1
	private String dowId;
	private String cwNum;
	private String owNum;
	private String clearingTime;
	private String payInfo;
	private String firstTime;
	private String seatName;
	private String peopleCount;
	// line-2
	private String clearingState;
	private String bisName;
	private String pxjgh;
	private String consumptionAmount;
	private String discountCosts;
	//line-3
	private String discountInfo;
	private String authorizedPersonName;
	private String zeroAmount;
	private String fixedDiscount;
	private String paidAmount;
	private String amountsReceivable;
	private String changeAmount;
	//line-4
	private String posName;
	private String operatorName;
	
	/**
	 * 查询字段
	 */
	private String startTime;
	private String endTime;
	private Integer consumerArea;
	private Integer bis;
	private Integer pos;
	private String clearingNum;
	private Boolean flag;
	private List<String> tableSuffixList;
	public String getDowId() {
		return dowId;
	}
	public void setDowId(String dowId) {
		this.dowId = dowId;
	}
	public String getCwNum() {
		return cwNum;
	}
	public void setCwNum(String cwNum) {
		this.cwNum = cwNum;
	}
	public String getOwNum() {
		return owNum;
	}
	public void setOwNum(String owNum) {
		this.owNum = owNum;
	}
	public String getClearingTime() {
		return clearingTime;
	}
	public void setClearingTime(String clearingTime) {
		this.clearingTime = clearingTime;
	}
	public String getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	public String getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}
	public String getSeatName() {
		return seatName;
	}
	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}
	public String getPeopleCount() {
		return peopleCount;
	}
	public void setPeopleCount(String peopleCount) {
		this.peopleCount = peopleCount;
	}
	public String getClearingState() {
		return clearingState;
	}
	public void setClearingState(String clearingState) {
		this.clearingState = clearingState;
	}
	public String getBisName() {
		return bisName;
	}
	public void setBisName(String bisName) {
		this.bisName = bisName;
	}
	public String getPxjgh() {
		return pxjgh;
	}
	public void setPxjgh(String pxjgh) {
		this.pxjgh = pxjgh;
	}
	public String getConsumptionAmount() {
		return consumptionAmount;
	}
	public void setConsumptionAmount(String consumptionAmount) {
		this.consumptionAmount = consumptionAmount;
	}
	public String getDiscountCosts() {
		return discountCosts;
	}
	public void setDiscountCosts(String discountCosts) {
		this.discountCosts = discountCosts;
	}
	public String getDiscountInfo() {
		return discountInfo;
	}
	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}
	public String getAuthorizedPersonName() {
		return authorizedPersonName;
	}
	public void setAuthorizedPersonName(String authorizedPersonName) {
		this.authorizedPersonName = authorizedPersonName;
	}
	public String getZeroAmount() {
		return zeroAmount;
	}
	public void setZeroAmount(String zeroAmount) {
		this.zeroAmount = zeroAmount;
	}
	public String getFixedDiscount() {
		return fixedDiscount;
	}
	public void setFixedDiscount(String fixedDiscount) {
		this.fixedDiscount = fixedDiscount;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getAmountsReceivable() {
		return amountsReceivable;
	}
	public void setAmountsReceivable(String amountsReceivable) {
		this.amountsReceivable = amountsReceivable;
	}
	public String getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(String changeAmount) {
		this.changeAmount = changeAmount;
	}
	public String getPosName() {
		return posName;
	}
	public void setPosName(String posName) {
		this.posName = posName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getConsumerArea() {
		return consumerArea;
	}
	public void setConsumerArea(Integer consumerArea) {
		this.consumerArea = consumerArea;
	}
	public Integer getBis() {
		return bis;
	}
	public void setBis(Integer bis) {
		this.bis = bis;
	}
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
		this.pos = pos;
	}
	public String getClearingNum() {
		return clearingNum;
	}
	public void setClearingNum(String clearingNum) {
		this.clearingNum = clearingNum;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public List<String> getTableSuffixList() {
		return tableSuffixList;
	}
	public void setTableSuffixList(List<String> tableSuffixList) {
		this.tableSuffixList = tableSuffixList;
	}

}
