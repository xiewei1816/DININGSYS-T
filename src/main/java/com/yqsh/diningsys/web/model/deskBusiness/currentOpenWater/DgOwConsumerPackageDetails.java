package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

import java.math.BigDecimal;

/**
 * @author zhangshuo
 * @desc
 */
public class DgOwConsumerPackageDetails {

    private String packageId;

    private Integer itemFileId;

    private String itemFileName;

    private Double itemFileNumber;

    private int type;

    private BigDecimal subtotal;

    private Integer settlementStatus;

    public String getItemFileName() {
        return itemFileName;
    }

    public DgOwConsumerPackageDetails setItemFileName(String itemFileName) {
        this.itemFileName = itemFileName;
        return this;
    }

    public String getPackageId() {
        return packageId;
    }

    public DgOwConsumerPackageDetails setPackageId(String packageId) {
        this.packageId = packageId;
        return this;
    }

    public Integer getItemFileId() {
        return itemFileId;
    }

    public DgOwConsumerPackageDetails setItemFileId(Integer itemFileId) {
        this.itemFileId = itemFileId;
        return this;
    }

    public Double getItemFileNumber() {
        return itemFileNumber;
    }

    public DgOwConsumerPackageDetails setItemFileNumber(Double itemFileNumber) {
        this.itemFileNumber = itemFileNumber;
        return this;
    }

    public Integer getSettlementStatus() {
        return settlementStatus;
    }

    public DgOwConsumerPackageDetails setSettlementStatus(Integer settlementStatus) {
        this.settlementStatus = settlementStatus;
        return this;
    }

    public int getType() {
        return type;
    }

    public DgOwConsumerPackageDetails setType(int type) {
        this.type = type;
        return this;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public DgOwConsumerPackageDetails setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        return this;
    }
}