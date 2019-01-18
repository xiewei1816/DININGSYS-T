package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgGateItemAllowCustom extends BasePojo implements Serializable{
    private Integer id;

    private Integer smallGateId; //小类id

    private Integer allowCustom;//是否允许打折

    
    
    private String name; //小类名称
    private String uuidKey;
    private String shopKey;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSmallGateId() {
        return smallGateId;
    }

    public void setSmallGateId(Integer smallGateId) {
        this.smallGateId = smallGateId;
    }

    public Integer getAllowCustom() {
        return allowCustom;
    }

    public void setAllowCustom(Integer allowCustom) {
        this.allowCustom = allowCustom;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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