package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-24 上午9:39
 */
public class DgReceipt {

    private Integer id;

    private Integer receiptAmount;

    private Integer receiptDenomination;

    private String receiptNum;

    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
