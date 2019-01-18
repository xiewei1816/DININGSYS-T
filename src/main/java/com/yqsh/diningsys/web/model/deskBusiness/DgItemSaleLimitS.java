package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;
import java.math.BigDecimal;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemSaleLimitS extends BasePojo implements Serializable{
    private Integer id;

    private Integer itemId; //品项id

    private String itemCode; //品项编码

    private Double saleCount; //今日可销售数量

    private Double reservationCount; //今日预定数量

    private Double frontSaleCount; //今日前台可销售数量

    private Integer limitId; //限量id 父表id

    
    /**
     * 查询字段
     */
    private String name; //品项名称
    private int tc; //套餐
    private Double sPrice; //标准价格
    private String bName;//大类名称
    private String sName;//小类名称
    private double useCount;//使用数量
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

    public Double getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Double saleCount) {
        this.saleCount = saleCount;
    }

    public Double getReservationCount() {
        return reservationCount;
    }

    public void setReservationCount(Double reservationCount) {
        this.reservationCount = reservationCount;
    }

    public Double getFrontSaleCount() {
        return frontSaleCount;
    }

    public void setFrontSaleCount(Double frontSaleCount) {
        this.frontSaleCount = frontSaleCount;
    }

    public Integer getLimitId() {
        return limitId;
    }

    public void setLimitId(Integer limitId) {
        this.limitId = limitId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTc() {
		return tc;
	}

	public void setTc(int tc) {
		this.tc = tc;
	}

	public Double getsPrice() {
		return sPrice;
	}

	public void setsPrice(Double sPrice) {
		this.sPrice = sPrice;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public double getUseCount() {
		return useCount;
	}

	public void setUseCount(double useCount) {
		this.useCount = useCount;
	}
    
	
}