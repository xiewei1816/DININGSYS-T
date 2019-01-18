package com.yqsh.diningsys.web.model.report;

import com.yqsh.diningsys.core.util.BasePojo;

import java.util.List;

/**
 * 营销员销售信息
* @author xiewei
* @version 创建时间：2018年8月18日
 */
public class Sales extends BasePojo{

	/**
	 * 数据字段
	 */
	private String itemFileName;
	private String total;
	private String marketingStaffName;
	private String number;
	private String standardPrice;
	private String discountMoney;
	private String ppxlId;
	private List<String> ppxlIds;

	public String getItemFileName() {
		return itemFileName;
	}

	public void setItemFileName(String itemFileName) {
		this.itemFileName = itemFileName;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getMarketingStaffName() {
		return marketingStaffName;
	}

	public void setMarketingStaffName(String marketingStaffName) {
		this.marketingStaffName = marketingStaffName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}

	public String getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(String discountMoney) {
		this.discountMoney = discountMoney;
	}

	public String getPpxlId() {
		return ppxlId;
	}

	public void setPpxlId(String ppxlId) {
		this.ppxlId = ppxlId;
	}

	public List<String> getPpxlIds() {
		return ppxlIds;
	}

	public void setPpxlIds(List<String> ppxlIds) {
		this.ppxlIds = ppxlIds;
	}

	/**
	 * 查询字段
	 */
	private String marketingStaff; //营销员ID
	private String startTime;
	private String endTime;
	private List<String> tableSuffixList;

	public String getMarketingStaff() {
		return marketingStaff;
	}
	public void setMarketingStaff(String marketingStaff) {
		this.marketingStaff = marketingStaff;
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
	public List<String> getTableSuffixList() {
		return tableSuffixList;
	}
	public void setTableSuffixList(List<String> tableSuffixList) {
		this.tableSuffixList = tableSuffixList;
	}
	
}
