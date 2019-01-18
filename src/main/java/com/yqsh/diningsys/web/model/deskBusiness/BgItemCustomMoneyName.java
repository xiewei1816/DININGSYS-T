package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class BgItemCustomMoneyName extends BasePojo implements Serializable{
    private Integer id;

    private String customMoneyName; //自定义金额名称

    private Integer itemCode; //自定义金额代码

    private String uuidKey;
    private String shopKey;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomMoneyName() {
        return customMoneyName;
    }

    public void setCustomMoneyName(String customMoneyName) {
        this.customMoneyName = customMoneyName == null ? null : customMoneyName.trim();
    }

    public Integer getItemCode() {
        return itemCode;
    }

    public void setItemCode(Integer itemCode) {
        this.itemCode = itemCode;
    }

	public String getUuidKey() {
		return uuidKey;
	}

	public void setUuidKey(String uuidKey) {
		this.uuidKey = uuidKey;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}
    
    
}