package com.yqsh.diningsys.web.model.businessMan;

import java.math.BigDecimal;

public class DgSeatItem {
    private Integer id;

    private Integer itemId; //品项id

    private String itemCode; //品项代码

    private Double count;//开单数量

    private Integer useOpenCounter; //是否按照开单人数

    private Integer seatId; //客位id

    private String name; //品项名称
    private String unit;//单位
    private String standardPrice;//标准价格
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

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Integer getUseOpenCounter() {
        return useOpenCounter;
    }

    public void setUseOpenCounter(Integer useOpenCounter) {
        this.useOpenCounter = useOpenCounter;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}
    
    
}