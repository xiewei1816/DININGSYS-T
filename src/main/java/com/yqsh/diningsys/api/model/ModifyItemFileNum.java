package com.yqsh.diningsys.api.model;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-12-18 16:44
 */
public class ModifyItemFileNum {

    /**
     * 修改数量的服务ID
     */
    private Integer serviceId;

    /**
     * 品项ID
     */
    private Integer itemFileId;

    /**
     * 品项数量
     */
    private Double itemFileNum;

    /**
     * 制作费用
     */
    private Double productionCosts;

    /**
     * 品项价格
     */
    private Double itemFilePrice;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getItemFileId() {
        return itemFileId;
    }

    public void setItemFileId(Integer itemFileId) {
        this.itemFileId = itemFileId;
    }

    public Double getItemFileNum() {
        return itemFileNum;
    }

    public void setItemFileNum(Double itemFileNum) {
        this.itemFileNum = itemFileNum;
    }

    public Double getProductionCosts() {
        return productionCosts;
    }

    public void setProductionCosts(Double productionCosts) {
        this.productionCosts = productionCosts;
    }

    public Double getItemFilePrice() {
        return itemFilePrice;
    }

    public void setItemFilePrice(Double itemFilePrice) {
        this.itemFilePrice = itemFilePrice;
    }
}
