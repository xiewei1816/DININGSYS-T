package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;


/**
 * 物品寄存实体
 * @author xiewei
 *
 */
@SuppressWarnings("serial")
public class DgGoodsConsign extends BasePojo{
	
    private Integer id;
    
    private String isDel; //删除状态 0默认- 未删除 1-已删除
    
    /* 客户信息 */
    private String clientName; //客户名称
    
    private String clientPhone; //客户电话
    
    private String clientSeat; //客位
    
    /* 物品信息 */
    private String goodsType; //物品寄存种类
    
    private String goodsCode; //物品编号
    
    private String goodsName; //物品名称
    
    private Integer goodsNumber; //数量
        
    private String goodsSpecification; //规格
    
    private String goodsColor; //颜色
    
    private String goodsExpirationDate; //保质截止日期
    
    private String goodsExplain; //说明
    
    /* 寄存信息 */
    private String gcFlag; //寄存状态
    
    private String gcPos; //寄存操作POS
    
    private String gcOperator;//寄存操作员
    
    private String gcStartTime; //寄存时间
    
    private String gcEndTime; //寄存截止时间
    
    private String gcAddress; //寄存位置
    
    /* 取走信息 */
    private Date qzTime; //取走时间
    
    private String qzPos; //取走操作POS
    
    private String qzOperator; //取走操作员
    
    /* 处理信息 */
    private String clWay; //处理方式
    
    private String clPos; //处理操作POS
    
    private String clOperator; //处理操作员
    
    private String clExplain; //处理说明

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public String getClientSeat() {
		return clientSeat;
	}

	public void setClientSeat(String clientSeat) {
		this.clientSeat = clientSeat;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getGoodsSpecification() {
		return goodsSpecification;
	}

	public void setGoodsSpecification(String goodsSpecification) {
		this.goodsSpecification = goodsSpecification;
	}

	public String getGoodsColor() {
		return goodsColor;
	}

	public void setGoodsColor(String goodsColor) {
		this.goodsColor = goodsColor;
	}

	public String getGoodsExpirationDate() {
		return goodsExpirationDate;
	}

	public void setGoodsExpirationDate(String goodsExpirationDate) {
		this.goodsExpirationDate = goodsExpirationDate;
	}

	public String getGoodsExplain() {
		return goodsExplain;
	}

	public void setGoodsExplain(String goodsExplain) {
		this.goodsExplain = goodsExplain;
	}

	public String getGcFlag() {
		return gcFlag;
	}

	public void setGcFlag(String gcFlag) {
		this.gcFlag = gcFlag;
	}

	public String getGcPos() {
		return gcPos;
	}

	public void setGcPos(String gcPos) {
		this.gcPos = gcPos;
	}

	public String getGcOperator() {
		return gcOperator;
	}

	public void setGcOperator(String gcOperator) {
		this.gcOperator = gcOperator;
	}

	public String getGcStartTime() {
		return gcStartTime;
	}

	public void setGcStartTime(String gcStartTime) {
		this.gcStartTime = gcStartTime;
	}

	public String getGcEndTime() {
		return gcEndTime;
	}

	public void setGcEndTime(String gcEndTime) {
		this.gcEndTime = gcEndTime;
	}

	public String getGcAddress() {
		return gcAddress;
	}

	public void setGcAddress(String gcAddress) {
		this.gcAddress = gcAddress;
	}
	
	public String getQzTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(qzTime != null)
            return simpleDateFormat.format(qzTime);
        else return null;
	}

	public void setQzTime(Date qzTime) {
		this.qzTime = qzTime;
	}

	public String getQzPos() {
		return qzPos;
	}

	public void setQzPos(String qzPos) {
		this.qzPos = qzPos;
	}

	public String getQzOperator() {
		return qzOperator;
	}

	public void setQzOperator(String qzOperator) {
		this.qzOperator = qzOperator;
	}

	public String getClWay() {
		return clWay;
	}

	public void setClWay(String clWay) {
		this.clWay = clWay;
	}

	public String getClPos() {
		return clPos;
	}

	public void setClPos(String clPos) {
		this.clPos = clPos;
	}

	public String getClOperator() {
		return clOperator;
	}

	public void setClOperator(String clOperator) {
		this.clOperator = clOperator;
	}

	public String getClExplain() {
		return clExplain;
	}

	public void setClExplain(String clExplain) {
		this.clExplain = clExplain;
	}
    
}