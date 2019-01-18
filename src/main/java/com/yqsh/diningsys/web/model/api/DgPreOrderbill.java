package com.yqsh.diningsys.web.model.api;

public class DgPreOrderbill {
    private String id;

    private Integer itemFileId;

    private Integer itemFileCount;

    private String owNum;

    private Integer waterId;
    
    private String firstNum;

    //查询字段
    private String itemNum; //品项编码
     
    private String itemName; //品项名称
    
    private String unit;//规格
    
    private double price;//标准价格
    
    private Integer pxxlId;//品项小类id
    
    private Integer thisCount = 0; //本次数量
    
    private Integer compareIde = 0; //对比本次数量  0大于本次数量  1等于本次数量  2小于本次数量
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getItemFileId() {
        return itemFileId;
    }

    public void setItemFileId(Integer itemFileId) {
        this.itemFileId = itemFileId;
    }

    public Integer getItemFileCount() {
        return itemFileCount;
    }

    public void setItemFileCount(Integer itemFileCount) {
        this.itemFileCount = itemFileCount;
    }

    public String getOwNum() {
        return owNum;
    }

    public void setOwNum(String owNum) {
        this.owNum = owNum == null ? null : owNum.trim();
    }

    public Integer getWaterId() {
        return waterId;
    }

    public void setWaterId(Integer waterId) {
        this.waterId = waterId;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getFirstNum() {
		return firstNum;
	}

	public void setFirstNum(String firstNum) {
		this.firstNum = firstNum;
	}

	public Integer getThisCount() {
		return thisCount;
	}

	public void setThisCount(Integer thisCount) {
		this.thisCount = thisCount;
	}

	public Integer getCompareIde() {
		return compareIde;
	}

	public void setCompareIde(Integer compareIde) {
		this.compareIde = compareIde;
	}
    
	
}