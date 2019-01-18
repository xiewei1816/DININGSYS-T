package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

public class DgOwReceipt{
    private Integer cwId;

    private Integer receiptDenomination;

    private Integer receiptCount;

    private Integer receiptAmount;

    private String receiptNum;

    private String notes;

    private String invoiceRowId;

    public Integer getCwId() {
        return cwId;
    }

    public void setCwId(Integer cwId) {
        this.cwId = cwId;
    }

    public Integer getReceiptCount() {
        return receiptCount;
    }

    public void setReceiptCount(Integer receiptCount) {
        this.receiptCount = receiptCount;
    }

    public Integer getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Integer receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getReceiptNum() {
        return receiptNum;
    }

    public void setReceiptNum(String receiptNum) {
        this.receiptNum = receiptNum;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getReceiptDenomination() {
        return receiptDenomination;
    }

    public void setReceiptDenomination(Integer receiptDenomination) {
        this.receiptDenomination = receiptDenomination;
    }

    public String getInvoiceRowId() {
        return invoiceRowId;
    }

    public void setInvoiceRowId(String invoiceRowId) {
        this.invoiceRowId = invoiceRowId;
    }
}