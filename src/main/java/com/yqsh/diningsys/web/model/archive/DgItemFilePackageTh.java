package com.yqsh.diningsys.web.model.archive;

/**
 * 品项档案套餐必选品项的替换品项
 */
public class DgItemFilePackageTh {
    private Integer id;

    private Integer packageId;

    private Integer bxItemFileId;

    private Integer itemFileId;

    private Integer itemAmout;

    private Double itemReplaceprice;

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

    public Integer getBxItemFileId() {
        return bxItemFileId;
    }

    public void setBxItemFileId(Integer bxItemFileId) {
        this.bxItemFileId = bxItemFileId;
    }

    public Integer getItemFileId() {
        return itemFileId;
    }

    public void setItemFileId(Integer itemFileId) {
        this.itemFileId = itemFileId;
    }

    public Integer getItemAmout() {
        return itemAmout;
    }

    public void setItemAmout(Integer itemAmout) {
        this.itemAmout = itemAmout;
    }

    public Double getItemReplaceprice() {
        return itemReplaceprice;
    }

    public void setItemReplaceprice(Double itemReplaceprice) {
        this.itemReplaceprice = itemReplaceprice;
    }

    public Integer getAddpriceNum() {
        return addpriceNum;
    }

    public void setAddpriceNum(Integer addpriceNum) {
        this.addpriceNum = addpriceNum;
    }
}