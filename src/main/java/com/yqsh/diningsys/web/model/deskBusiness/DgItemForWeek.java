package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;
import java.math.BigDecimal;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemForWeek extends BasePojo implements Serializable{
    private Integer id;

    private String itemCode; //品项代码

    private Integer itemId; //品项id

    private Double x1; //星期一价格

    private Double x2; //星期二价格

    private Double x3; //星期三价格

    private Double x4; //星期四价格

    private Double x5; //星期五价格

    private Double x6; //星期六价格

    private Double x7; //星期日价格
    
    /**
     * 
     * 
     * 查询项
     */
    private String name; //品项名称
    private Double sPrice;//标准价格

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

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    public Double getX3() {
        return x3;
    }

    public void setX3(Double x3) {
        this.x3 = x3;
    }

    public Double getX4() {
        return x4;
    }

    public void setX4(Double x4) {
        this.x4 = x4;
    }

    public Double getX5() {
        return x5;
    }

    public void setX5(Double x5) {
        this.x5 = x5;
    }

    public Double getX6() {
        return x6;
    }

    public void setX6(Double x6) {
        this.x6 = x6;
    }

    public Double getX7() {
        return x7;
    }

    public void setX7(Double x7) {
        this.x7 = x7;
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