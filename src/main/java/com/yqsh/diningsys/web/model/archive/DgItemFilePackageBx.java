package com.yqsh.diningsys.web.model.archive;

/**
 * 品项档案套餐必选品项
 */
public class DgItemFilePackageBx {
    private Integer id;

    private Integer packageId;

    private Integer itemFileId;

    private Integer itemAmount;

    private Double itemPrice;

    private Double itemAmountPrice;

    private Double itemAddprice;

    private Integer addpriceNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getItemFileId() {
        return itemFileId;
    }

    public void setItemFileId(Integer itemFileId) {
        this.itemFileId = itemFileId;
    }

    public Integer getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Integer itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Double getItemAmountPrice() {
        return itemAmountPrice;
    }

    public void setItemAmountPrice(Double itemAmountPrice) {
        this.itemAmountPrice = itemAmountPrice;
    }

    public Double getItemAddprice() {
        return itemAddprice;
    }

    public void setItemAddprice(Double itemAddprice) {
        this.itemAddprice = itemAddprice;
    }

    public Integer getAddpriceNum() {
        return addpriceNum;
    }

    public void setAddpriceNum(Integer addpriceNum) {
        this.addpriceNum = addpriceNum;
    }
}