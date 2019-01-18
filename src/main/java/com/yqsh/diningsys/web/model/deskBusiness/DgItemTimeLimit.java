package com.yqsh.diningsys.web.model.deskBusiness;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemTimeLimit extends BasePojo{
    private Integer id;

    private Integer itemId;

    private Double saleLimit;

    private Double surplusLimit;

    private String startTime;

    private String endTime;

    private Double price;
    
    private Integer pId;
    
    /**
     * 查询字段
     */
    private String itemCode;
    private String name;
    private Double bzPrice;
    
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

    public Double getSaleLimit() {
        return saleLimit;
    }

    public void setSaleLimit(Double saleLimit) {
        this.saleLimit = saleLimit;
    }

    public Double getSurplusLimit() {
        return surplusLimit;
    }

    public void setSurplusLimit(Double surplusLimit) {
        this.surplusLimit = surplusLimit;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBzPrice() {
		return bzPrice;
	}

	public void setBzPrice(Double bzPrice) {
		this.bzPrice = bzPrice;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
    
    
}