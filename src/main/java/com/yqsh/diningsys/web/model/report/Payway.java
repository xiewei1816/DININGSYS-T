package com.yqsh.diningsys.web.model.report;

import java.util.List;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 结算方式信息
* @author xiewei
* @version 创建时间：2017年8月09日 上午9:28:03
 */
public class Payway extends BasePojo{

	/**
	 * 数据字段
	 */
	private String cwNum;
	private String cName;
	private String cwType;
	private String amountsReceivable;
	private String paidAmount;
	private String changeAmount;
	private String clearingState;
	private String clearingTime;
	private String clearingNotes;
	
	/**
	 * 查询字段
	 */
	private String startTime;
	private String endTime;
	private String cwName;
	private List<String> tableSuffixList;
	public String getCwNum() {
		return cwNum;
	}
	public void setCwNum(String cwNum) {
		this.cwNum = cwNum;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getCwType() {
		return cwType;
	}
	public void setCwType(String cwType) {
		this.cwType = cwType;
	}
	public String getAmountsReceivable() {
		return amountsReceivable;
	}
	public void setAmountsReceivable(String amountsReceivable) {
		this.amountsReceivable = amountsReceivable;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(String changeAmount) {
		this.changeAmount = changeAmount;
	}
	public String getClearingState() {
		return clearingState;
	}
	public void setClearingState(String clearingState) {
		this.clearingState = clearingState;
	}
	public String getClearingTime() {
		return clearingTime;
	}
	public void setClearingTime(String clearingTime) {
		this.clearingTime = clearingTime;
	}
	public String getClearingNotes() {
		return clearingNotes;
	}
	public void setClearingNotes(String clearingNotes) {
		this.clearingNotes = clearingNotes;
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
	public String getCwName() {
		return cwName;
	}
	public void setCwName(String cwName) {
		this.cwName = cwName;
	}
	public List<String> getTableSuffixList() {
		return tableSuffixList;
	}
	public void setTableSuffixList(List<String> tableSuffixList) {
		this.tableSuffixList = tableSuffixList;
	}
	
}
