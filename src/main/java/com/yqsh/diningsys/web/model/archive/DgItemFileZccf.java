package com.yqsh.diningsys.web.model.archive;

public class DgItemFileZccf {
    private Integer id;

    private String inveItemId;

    private Integer itemId;

    private Double counter;

    /**
     * 查询字段
     * @return
     */
    private String inveItemCode;//成分编号
    private String unit;//单位
    private String name;//成分名称
    private String bName;//成分所属类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInveItemId() {
        return inveItemId;
    }

    public void setInveItemId(String inveItemId) {
        this.inveItemId = inveItemId == null ? null : inveItemId.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getCounter() {
        return counter;
    }

    public void setCounter(Double counter) {
        this.counter = counter;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getInveItemCode() {
		return inveItemCode;
	}

	public void setInveItemCode(String inveItemCode) {
		this.inveItemCode = inveItemCode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
    
    
}