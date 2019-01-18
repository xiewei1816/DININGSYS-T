package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemFromCook extends BasePojo implements Serializable{
    private Integer id;

    private Integer itemId; //品项id

    private String itemCode; //品项代码

    private Integer cookId; //厨师id

    
    /**
     * 
     * 查询字段
     * @return
     */
    private String name; //品项名称
    private String cookName; //厨师名
    
    private String uuidKey;
    private String shopKey;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public Integer getCookId() {
        return cookId;
    }

    public void setCookId(Integer cookId) {
        this.cookId = cookId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCookName() {
		return cookName;
	}

	public void setCookName(String cookName) {
		this.cookName = cookName;
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