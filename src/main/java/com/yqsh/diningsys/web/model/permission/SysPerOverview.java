package com.yqsh.diningsys.web.model.permission;

public class SysPerOverview {
    private Integer id;

    private String zwCode;

    private Integer qxsfysz;

    private Integer state = 0;

    private Integer freeType;

    private Integer chargebackType;

    private Integer variablePriceType;

    private String freeMaxPrice;

    private String freeTotalMoney;

    private Integer freeTemporary;

    private Integer chargebackTemporary;

    private String uuidKey;

    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZwCode() {
        return zwCode;
    }

    public void setZwCode(String zwCode) {
        this.zwCode = zwCode;
    }

    public Integer getQxsfysz() {
        return qxsfysz;
    }

    public void setQxsfysz(Integer qxsfysz) {
        this.qxsfysz = qxsfysz;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFreeType() {
        return freeType;
    }

    public void setFreeType(Integer freeType) {
        this.freeType = freeType;
    }

    public Integer getChargebackType() {
        return chargebackType;
    }

    public void setChargebackType(Integer chargebackType) {
        this.chargebackType = chargebackType;
    }

    public Integer getVariablePriceType() {
        return variablePriceType;
    }

    public void setVariablePriceType(Integer variablePriceType) {
        this.variablePriceType = variablePriceType;
    }

    public String getFreeMaxPrice() {
        return freeMaxPrice;
    }

    public void setFreeMaxPrice(String freeMaxPrice) {
        this.freeMaxPrice = freeMaxPrice;
    }

    public String getFreeTotalMoney() {
        return freeTotalMoney;
    }

    public void setFreeTotalMoney(String freeTotalMoney) {
        this.freeTotalMoney = freeTotalMoney;
    }

    public Integer getFreeTemporary() {
        return freeTemporary;
    }

    public void setFreeTemporary(Integer freeTemporary) {
        this.freeTemporary = freeTemporary;
    }

    public Integer getChargebackTemporary() {
        return chargebackTemporary;
    }

    public void setChargebackTemporary(Integer chargebackTemporary) {
        this.chargebackTemporary = chargebackTemporary;
    }
}