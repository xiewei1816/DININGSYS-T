package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DgReceptionClearingWater{
    private Integer id;

    private String cwNum;

    private Double consumptionAmount;

    private Double zeroAmount;

    private Double fixedDiscount;

    private Double amountsReceivable;

    private Double paidAmount;

    private Double changeAmount;

    private Date clearingTime;

    private Integer clearingBis;

    private String clearingOperator;

    private String  clearingPos;

    private Integer printCont;

    private Integer invoicing;

    private Integer zeroSettlement;

    private Integer retroDocuments;

    private String statementLabel;

    private Double couponAmount;

    private String clearingNotes;

    private String clearingMember;

    private Integer clearingState;


    private String bisName;

    private String clearingOperatorName;

    private String clearingPosName;

    private String posName;

    private Integer shiftState;

    private Integer jbId;

    private Double generalProportions;

    private Double singleProportions;

    private Integer payType;

    private String shopKey;

    private String returnSettleTime;

    private String seatName;

    private Integer openId;

    private String owNum;
    
    private Double beforeDiscountsAmount;
    
    private Double totalDiscountAmount;

    public Integer getOpenId() {
        return openId;
    }

    public void setOpenId(Integer openId) {
        this.openId = openId;
    }

    public String getOwNum() {
        return owNum;
    }

    public void setOwNum(String owNum) {
        this.owNum = owNum;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getReturnSettleTime() {
        return returnSettleTime;
    }

    public void setReturnSettleTime(String returnSettleTime) {
        this.returnSettleTime = returnSettleTime;
    }

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCwNum() {
        return cwNum;
    }

    public void setCwNum(String cwNum) {
        this.cwNum = cwNum == null ? null : cwNum.trim();
    }

    public Double getConsumptionAmount() {
        return consumptionAmount;
    }

    public void setConsumptionAmount(Double consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
    }

    public Double getZeroAmount() {
        return zeroAmount;
    }

    public void setZeroAmount(Double zeroAmount) {
        this.zeroAmount = zeroAmount;
    }

    public Double getFixedDiscount() {
        return fixedDiscount;
    }

    public void setFixedDiscount(Double fixedDiscount) {
        this.fixedDiscount = fixedDiscount;
    }

    public Double getAmountsReceivable() {
        return amountsReceivable;
    }

    public void setAmountsReceivable(Double amountsReceivable) {
        this.amountsReceivable = amountsReceivable;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getClearingTime() {
        return clearingTime!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(clearingTime):null;
    }

    public void setClearingTime(Date clearingTime) {
        this.clearingTime = clearingTime;
    }

    public Integer getClearingBis() {
        return clearingBis;
    }

    public void setClearingBis(Integer clearingBis) {
        this.clearingBis = clearingBis;
    }

    public String getClearingOperator() {
        return clearingOperator;
    }

    public void setClearingOperator(String clearingOperator) {
        this.clearingOperator = clearingOperator;
    }

    public String getClearingPos() {
        return clearingPos;
    }

    public void setClearingPos(String clearingPos) {
        this.clearingPos = clearingPos;
    }

    public Integer getPrintCont() {
        return printCont;
    }

    public void setPrintCont(Integer printCont) {
        this.printCont = printCont;
    }

    public Integer getInvoicing() {
        return invoicing;
    }

    public void setInvoicing(Integer invoicing) {
        this.invoicing = invoicing;
    }

    public Integer getZeroSettlement() {
        return zeroSettlement;
    }

    public void setZeroSettlement(Integer zeroSettlement) {
        this.zeroSettlement = zeroSettlement;
    }

    public Integer getRetroDocuments() {
        return retroDocuments;
    }

    public void setRetroDocuments(Integer retroDocuments) {
        this.retroDocuments = retroDocuments;
    }

    public String getStatementLabel() {
        return statementLabel;
    }

    public void setStatementLabel(String statementLabel) {
        this.statementLabel = statementLabel == null ? null : statementLabel.trim();
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getClearingNotes() {
        return clearingNotes;
    }

    public void setClearingNotes(String clearingNotes) {
        this.clearingNotes = clearingNotes == null ? null : clearingNotes.trim();
    }

    public String getClearingMember() {
        return clearingMember;
    }

    public void setClearingMember(String clearingMember) {
        this.clearingMember = clearingMember;
    }

    public Integer getClearingState() {
        return clearingState;
    }

    public void setClearingState(Integer clearingState) {
        this.clearingState = clearingState;
    }

    public String getBisName() {
        return bisName;
    }

    public void setBisName(String bisName) {
        this.bisName = bisName;
    }

    public String getClearingOperatorName() {
        return clearingOperatorName;
    }

    public void setClearingOperatorName(String clearingOperatorName) {
        this.clearingOperatorName = clearingOperatorName;
    }

    public String getClearingPosName() {
        return clearingPosName;
    }

    public void setClearingPosName(String clearingPosName) {
        this.clearingPosName = clearingPosName;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public Integer getShiftState() {
        return shiftState;
    }

    public void setShiftState(Integer shiftState) {
        this.shiftState = shiftState;
    }

    public Integer getJbId() {
        return jbId;
    }

    public void setJbId(Integer jbId) {
        this.jbId = jbId;
    }

    public Double getGeneralProportions() {
        return generalProportions;
    }

    public void setGeneralProportions(Double generalProportions) {
        this.generalProportions = generalProportions;
    }

    public Double getSingleProportions() {
        return singleProportions;
    }

    public void setSingleProportions(Double singleProportions) {
        this.singleProportions = singleProportions;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

	public Double getBeforeDiscountsAmount() {
		return beforeDiscountsAmount;
	}

	public void setBeforeDiscountsAmount(Double beforeDiscountsAmount) {
		this.beforeDiscountsAmount = beforeDiscountsAmount;
	}

	public Double getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(Double totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

    
}