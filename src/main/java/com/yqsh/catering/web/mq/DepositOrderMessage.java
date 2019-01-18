package com.yqsh.catering.web.mq;

import java.io.Serializable;
import java.math.BigDecimal;

import com.yqsh.diningsys.core.util.BasePojo;

public class DepositOrderMessage extends BasePojo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String orderId;// 订单号，回掉时传回
	
	private Integer persons;//预定人数

	private String note;//备注
	
	private String seatType;//座位类别   大厅/包间
	
	private String contactuser;//联系人

	private String contacttel;//联系电话
	
	private BigDecimal preamount;//预定费用

	private String shopid;//店铺ID

	private String shopName;//店铺名

	private String gender;//性别
	
	private String createTime;//订单创建时间  yyyy-MM-dd HH:mm:ss
	
	private String reserveTime;//预定时间   yyyy-MM-dd HH:mm:ss
	
	private int orderType; //0--order  1--cancel

	private String search;
	

	private int sectionID;//0--午餐  ；1--晚餐
  
    private int allowShiftSeatType;// true -- 当选择座位类型没有时，可以预定其它座位类型，
    
    private String state;//状态
  
	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public Integer getPersons() {
		return persons;
	}

	public void setPersons(Integer persons) {
		this.persons = persons;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public String getContactuser() {
		return contactuser;
	}

	public void setContactuser(String contactuser) {
		this.contactuser = contactuser;
	}

	public String getContacttel() {
		return contacttel;
	}

	public void setContacttel(String contacttel) {
		this.contacttel = contacttel;
	}

	public BigDecimal getPreamount() {
		return preamount;
	}

	public void setPreamount(BigDecimal preamount) {
		this.preamount = preamount;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getReserveTime() {
		return reserveTime;
	}


	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}


	public int getOrderType() {
		return orderType;
	}


	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}


	public int getSectionID() {
		return sectionID;
	}


	public void setSectionID(int sectionID) {
		this.sectionID = sectionID;
	}


	public int getAllowShiftSeatType() {
		return allowShiftSeatType;
	}


	public void setAllowShiftSeatType(int allowShiftSeatType) {
		this.allowShiftSeatType = allowShiftSeatType;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}

}