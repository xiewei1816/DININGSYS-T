package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemPriceLadder extends BasePojo implements Serializable{
    private Integer id;

    private String itemCode; //品项代码

    private Integer itemId; //品项编码

    private Double ladder1; //阶梯价格1

    private Double ladder2; //阶梯价格2
    
    
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getLadder1() {
        return ladder1;
    }

    public void setLadder1(Double ladder1) {
        this.ladder1 = ladder1;
    }

    public Double getLadder2() {
        return ladder2;
    }

    public void setLadder2(Double ladder2) {
        this.ladder2 = ladder2;
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