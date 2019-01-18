package com.yqsh.diningsys.web.model.api;

public class DgPreOrderbillColor {
    private String id;

    private String owNum;

    private Integer colorItemId;

    private Integer waterId;

    private Integer colorItemCount;

    
    //查询字段
    private String itemNum; //品项编码
    
    private String itemName; //品项名称
    
    private String unit;//规格
    
    private double price;//标准价格
    
    private Integer pxxlId;//品项小类id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOwNum() {
        return owNum;
    }

    public void setOwNum(String owNum) {
        this.owNum = owNum == null ? null : owNum.trim();
    }

    public Integer getColorItemId() {
        return colorItemId;
    }

    public void setColorItemId(Integer colorItemId) {
        this.colorItemId = colorItemId;
    }

    public Integer getWaterId() {
        return waterId;
    }

    public void setWaterId(Integer waterId) {
        this.waterId = waterId;
    }

    public Integer getColorItemCount() {
        return colorItemCount;
    }

    public void setColorItemCount(Integer colorItemCount) {
        this.colorItemCount = colorItemCount;
    }
	
	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getPxxlId() {
		return pxxlId;
	}

	public void setPxxlId(Integer pxxlId) {
		this.pxxlId = pxxlId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
    
    
}