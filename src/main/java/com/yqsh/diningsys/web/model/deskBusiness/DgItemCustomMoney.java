package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemCustomMoney extends BasePojo implements Serializable{
    private Integer id;

    private String itemCode; //品项代码

    private Integer customMoneyId; //自定义金额id

    private Integer itemId; //品项id

    /**
     * 
     * 查询字段
     */
    private String name; //品项名称
    private Double sPrice; //标准价格
    private Integer tc; //是否套餐
    private String bName; //大类id
    private String sName; //小类id
    private String customMoneyName;//自定义金额名称
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

    public Integer getCustomMoneyId() {
        return customMoneyId;
    }

    public void setCustomMoneyId(Integer customMoneyId) {
        this.customMoneyId = customMoneyId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

	public Integer getTc() {
		return tc;
	}

	public void setTc(Integer tc) {
		this.tc = tc;
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

	public String getCustomMoneyName() {
		return customMoneyName;
	}

	public void setCustomMoneyName(String customMoneyName) {
		this.customMoneyName = customMoneyName;
	}
    
    
}