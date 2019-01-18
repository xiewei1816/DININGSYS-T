package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 押金实体
 * @author xiewei
 *
 */
public class DgCashPledge{

    private Integer id;

    private String owNum; //开单编号

    private String cpType; //押金类型 - 登记押金;退押金

    private String cpCurrency; //币种 - 结算方式（人民币等）

    private Double cpMoney; //金额

    private Double converMoney; //换算金额

    private Date regTime; //登记时间

    private String refInfo; //参考信息

    private String remark; //备注

    private Integer printNumber; //打印次数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpType() {
		return cpType;
	}

	public String getOwNum() {
		return owNum;
	}

	public void setOwNum(String owNum) {
		this.owNum = owNum;
	}

	public void setCpType(String cpType) {
		this.cpType = cpType;
	}

	public String getCpCurrency() {
		return cpCurrency;
	}

	public void setCpCurrency(String cpCurrency) {
		this.cpCurrency = cpCurrency;
	}

	public Double getCpMoney() {
		return cpMoney;
	}

	public void setCpMoney(Double cpMoney) {
		this.cpMoney = cpMoney;
	}

	public Double getConverMoney() {
		return converMoney;
	}

	public void setConverMoney(Double converMoney) {
		this.converMoney = converMoney;
	}

	public String getRegTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(regTime != null)
            return simpleDateFormat.format(regTime);
        else return null;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getRefInfo() {
		return refInfo;
	}

	public void setRefInfo(String refInfo) {
		this.refInfo = refInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPrintNumber() {
		return printNumber;
	}

	public void setPrintNumber(Integer printNumber) {
		this.printNumber = printNumber;
	}

}