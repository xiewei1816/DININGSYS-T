package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemMemberDiscountS extends BasePojo implements Serializable{
    private Integer id;

    private Integer itemId; //品项id

    private Integer enable; //启用

    private Double price; //方案价格

    private Integer consistent; //价格是否一致
    
    private Integer pId; //父表id
    
    /**
     * 
     * 查询字段
     */
    private String name;//品项名称
    private String itemCode;//品项编码
    private Integer tc;//是否是套餐
    private Double sPrice;//标准价格
    

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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getConsistent() {
        return consistent;
    }

    public void setConsistent(Integer consistent) {
        this.consistent = consistent;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getTc() {
		return tc;
	}

	public void setTc(Integer tc) {
		this.tc = tc;
	}

	public Double getsPrice() {
		return sPrice;
	}

	public void setsPrice(Double sPrice) {
		this.sPrice = sPrice;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
    
    
}