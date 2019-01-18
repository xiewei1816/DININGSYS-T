package com.yqsh.diningsys.web.model.businessMan;

import java.io.Serializable;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgSeatChargingScheme extends BasePojo implements Serializable{
    private Integer id;

    private String code; //方案编码

    private String name; //方案名称
 
    private Integer type; //方案类型

    private Integer typeMinVal; //不足多少分钟，按多少分钟补齐

    private Integer typeHourVal; //尾时段不足多少分钟不计费

    private Double amountUpLim; //收费金额上限

    private Integer tLongLowLim; //收费时长下限

    private Integer qySsdsf; //启用首时段收费

    private Integer ssdsfMin; //多少分钟

    private Double ssdsfMoney; //多少钱

    private Integer trash; //回收站
    
    private Date time; //最新修改时间

    private String uuidKey;

    private String shopKey;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTypeMinVal() {
        return typeMinVal;
    }

    public void setTypeMinVal(Integer typeMinVal) {
        this.typeMinVal = typeMinVal;
    }

    public Integer getTypeHourVal() {
        return typeHourVal;
    }

    public void setTypeHourVal(Integer typeHourVal) {
        this.typeHourVal = typeHourVal;
    }

    public Double getAmountUpLim() {
        return amountUpLim;
    }

    public void setAmountUpLim(Double amountUpLim) {
        this.amountUpLim = amountUpLim;
    }

    public Integer gettLongLowLim() {
        return tLongLowLim;
    }

    public void settLongLowLim(Integer tLongLowLim) {
        this.tLongLowLim = tLongLowLim;
    }

    public Integer getQySsdsf() {
        return qySsdsf;
    }

    public void setQySsdsf(Integer qySsdsf) {
        this.qySsdsf = qySsdsf;
    }

    public Integer getSsdsfMin() {
        return ssdsfMin;
    }

    public void setSsdsfMin(Integer ssdsfMin) {
        this.ssdsfMin = ssdsfMin;
    }

    public Double getSsdsfMoney() {
        return ssdsfMoney;
    }

    public void setSsdsfMoney(Double ssdsfMoney) {
        this.ssdsfMoney = ssdsfMoney;
    }

    public Integer getTrash() {
        return trash;
    }

    public void setTrash(Integer trash) {
        this.trash = trash;
    }

	public String getTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time != null)
            return simpleDateFormat.format(time);
        else 
        	return null;
	}

	public void setTime(java.util.Date date) {
		this.time = date;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}
    
    
}