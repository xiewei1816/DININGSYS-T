package com.yqsh.diningsys.web.model.report;

import java.util.List;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 品项销售明细-明细/汇总
* @author xiewei
* @version 创建时间：2017年8月07日 上午9:28:03
 */
public class ItemFileSell  extends BasePojo{

	/**
	 * 数据字段
	 */
	private String serviceType;
	private String num;
	private String name;
	private String unit;
	private String pxdl;
	private String pxxl;
	private String itemFinalPrice;
	private String itemFileNumber;
	private String subtotal;
	private String serviceTime;
	private String isTc;
	private String pos;
	private String userName;
	private String seatName;
	private String owNum;
	private String serviceNum;
	/**
	 * 汇总
	 */
	private String number; 
	
	/**
	 * 查询字段
	 */
	private String searchDataType; // 1-明细 2-汇总
	private String startTime;
	private String endTime;
	private String itemFileType;
	private String bis;
	private String itemFileName;
	private List<String> tableSuffixList;
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPxdl() {
		return pxdl;
	}
	public void setPxdl(String pxdl) {
		this.pxdl = pxdl;
	}
	public String getPxxl() {
		return pxxl;
	}
	public void setPxxl(String pxxl) {
		this.pxxl = pxxl;
	}
	public String getItemFinalPrice() {
		return itemFinalPrice;
	}
	public void setItemFinalPrice(String itemFinalPrice) {
		this.itemFinalPrice = itemFinalPrice;
	}
	public String getItemFileNumber() {
		return itemFileNumber;
	}
	public void setItemFileNumber(String itemFileNumber) {
		this.itemFileNumber = itemFileNumber;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getIsTc() {
		return isTc;
	}
	public void setIsTc(String isTc) {
		this.isTc = isTc;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSeatName() {
		return seatName;
	}
	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}
	public String getOwNum() {
		return owNum;
	}
	public void setOwNum(String owNum) {
		this.owNum = owNum;
	}
	public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
	
	/**
	 * 汇总字段
	 * @return
	 */
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	/**
	 * 查询字段
	 * @return
	 */
	public String getSearchDataType() {
		return searchDataType;
	}
	public void setSearchDataType(String searchDataType) {
		this.searchDataType = searchDataType;
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
	public String getItemFileType() {
		return itemFileType;
	}
	public void setItemFileType(String itemFileType) {
		this.itemFileType = itemFileType;
	}
	public String getBis() {
		return bis;
	}
	public void setBis(String bis) {
		this.bis = bis;
	}
	public String getItemFileName() {
		return itemFileName;
	}
	public void setItemFileName(String itemFileName) {
		this.itemFileName = itemFileName;
	}
	public List<String> getTableSuffixList() {
		return tableSuffixList;
	}
	public void setTableSuffixList(List<String> tableSuffixList) {
		this.tableSuffixList = tableSuffixList;
	}
}
