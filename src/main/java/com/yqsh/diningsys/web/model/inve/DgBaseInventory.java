package com.yqsh.diningsys.web.model.inve;

import com.yqsh.diningsys.core.util.BasePojo;

import java.math.BigDecimal;

/**
 * 库存相关基类
 *
 * @author jianglei
 * 日期 2016年10月19日 下午4:16:36
 */
@SuppressWarnings("serial")
public class DgBaseInventory extends BasePojo {
    private String wareID;          //仓库编号
    private String serialNumber;    //流水号
    private String supplierId;      //供应商编号
    private String sinceNumber;     //自编号
    private String dateTime;        //日期
    private String itemId;          //物品编号
    private String itemName;        //物品名称
    private String unit;            //物品单位
    private BigDecimal origPrice;   //原价
    private BigDecimal presPrice;   //现价
    private BigDecimal number;             //数量
    private BigDecimal sumAmount;   //总金额

    private String inCode; //2017年11月7日17:02:45  原助记符标记 修改为部门领料是否减少库存标记

    public String getInCode() {
        return inCode;
    }

    public void setInCode(String inCode) {
        this.inCode = inCode;
    }

    public DgBaseInventory() {
        super();
    }

    public String getWareID() {
        return wareID;
    }

    public void setWareID(String wareID) {
        this.wareID = wareID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSinceNumber() {
        return sinceNumber;
    }

    public void setSinceNumber(String sinceNumber) {
        this.sinceNumber = sinceNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getOrigPrice() {
        return origPrice;
    }

    public void setOrigPrice(BigDecimal origPrice) {
        this.origPrice = origPrice;
    }

    public BigDecimal getPresPrice() {
        return presPrice;
    }

    public void setPresPrice(BigDecimal presPrice) {
        this.presPrice = presPrice;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }
}
