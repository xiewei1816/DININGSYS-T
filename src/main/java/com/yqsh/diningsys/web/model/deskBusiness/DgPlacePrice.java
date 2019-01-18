package com.yqsh.diningsys.web.model.deskBusiness;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgPlacePrice extends BasePojo{
    private Integer id;

    private Integer itemId; //品项id

    private String itemCode; //品项代码

    
    
    /**
     * 
     * 
     * 查询字段
     */
    private String name; //品项名称
    private Double sPrice; //标准价格
    
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getsPrice() {
		return sPrice;
	}

	public void setsPrice(Double sPrice) {
		this.sPrice = sPrice;
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