package com.yqsh.diningsys.api.model;

/**
 * 品项变价以及拆账可用的实体
 *
 * @author zhshuo create on 2016-12-16 14:53
 */
public class VariablePrice {

    /**
     * 品项ID
     */
    private Integer itemFileId;

    /**
     * 服务ID
     */
    private Integer serviceId;

    /**
     * 品项数量
     */
    private Double itemFileNum;

    /**
     * 品项价格
     */
    private Double itemFilePrice;

    /**
     * 品项加单时的价格
     */
    private Double initalPrice;


    public Integer getItemFileId() {
        return itemFileId;
    }

    public void setItemFileId(Integer itemFileId) {
        this.itemFileId = itemFileId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Double getItemFileNum() {
        return itemFileNum;
    }

    public void setItemFileNum(Double itemFileNum) {
        this.itemFileNum = itemFileNum;
    }

    public Double getItemFilePrice() {
        return itemFilePrice;
    }

    public void setItemFilePrice(Double itemFilePrice) {
        this.itemFilePrice = itemFilePrice;
    }

    public Double getInitalPrice() {
        return initalPrice;
    }

    public void setInitalPrice(Double initalPrice) {
        this.initalPrice = initalPrice;
    }
}
