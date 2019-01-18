package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemForMealTimeS extends BasePojo implements Serializable{
    private Integer id;

    private Integer itemId;

    private String itemCode;

    private Double afternoon;

    private Double dinner;

    
    /**
     * 
     * 
     * 查询项
     */
    private String name; //品项名称
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public Double getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(Double afternoon) {
        this.afternoon = afternoon;
    }

    public Double getDinner() {
        return dinner;
    }

    public void setDinner(Double dinner) {
        this.dinner = dinner;
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
    
    
}