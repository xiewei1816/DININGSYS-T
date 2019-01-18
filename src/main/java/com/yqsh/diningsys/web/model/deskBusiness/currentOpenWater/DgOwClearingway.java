package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

public class DgOwClearingway{
    private Integer cwId;

    private Double settlementAmount;

    private Double conversionAmount;

    private String notes;

    private String cwCode;

    private Double nonZeroAmount;

    private Double foreignPay;

    private String consId;
    /**
     * 结算方式名称
     */
    private String seName;

    private String nrowId;

    private String cwName;
    
    /**
     * 结算方式计入收入、不计入收入比例
     */
    private Double actualIncomeRatio;
    private Double notActualIncomeRatio;
    
    public String getCwName() {
        return cwName;
    }

    public void setCwName(String cwName) {
        this.cwName = cwName;
    }

    public Integer getCwId() {
        return cwId;
    }

    public void setCwId(Integer cwId) {
        this.cwId = cwId;
    }

    public Double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public Double getConversionAmount() {
        return conversionAmount;
    }

    public void setConversionAmount(Double conversionAmount) {
        this.conversionAmount = conversionAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getCwCode() {
        return cwCode;
    }

    public void setCwCode(String cwCode) {
        this.cwCode = cwCode;
    }

    public Double getNonZeroAmount() {
        return nonZeroAmount;
    }

    public void setNonZeroAmount(Double nonZeroAmount) {
        this.nonZeroAmount = nonZeroAmount;
    }

    public Double getForeignPay() {
        return foreignPay;
    }

    public void setForeignPay(Double foreignPay) {
        this.foreignPay = foreignPay;
    }

    public String getSeName() {
        return seName;
    }

    public void setSeName(String seName) {
        this.seName = seName;
    }

    public String getNrowId() {
        return nrowId;
    }

    public void setNrowId(String nrowId) {
        this.nrowId = nrowId;
    }

	public String getConsId() {
		return consId;
	}

	public void setConsId(String consId) {
		this.consId = consId;
	}
    
	public Double getActualIncomeRatio() {
		return actualIncomeRatio;
	}

	public void setActualIncomeRatio(Double actualIncomeRatio) {
		this.actualIncomeRatio = actualIncomeRatio;
	}

	public Double getNotActualIncomeRatio() {
		return notActualIncomeRatio;
	}

	public void setNotActualIncomeRatio(Double notActualIncomeRatio) {
		this.notActualIncomeRatio = notActualIncomeRatio;
	}

}