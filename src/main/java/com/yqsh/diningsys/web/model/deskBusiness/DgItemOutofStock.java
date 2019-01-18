package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemOutofStock extends BasePojo implements Serializable{
    private Integer id;

    private String itemCode; //品项代码

    private Integer itemId; //品项id

    private Integer mType; //类别(不指定/按照当前市别/按照营业日)
    
    private Integer mBis;//当前市别
    
    private Date date;//沽清日期

    private String uuidKey;
    
    /**
     * 查询字段
     */
    private String name; //品项日期
    private int tc; //套餐
    private Double sPrice; //标准价格 
    private String bName;//大类名称
    private String sName;//小类名称
    private String unit;//单位
    private String sm;//说明
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

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
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

	public int getTc() {
		return tc;
	}

	public void setTc(int tc) {
		this.tc = tc;
	}

	public Integer getmBis() {
		return mBis;
	}

	public void setmBis(Integer mBis) {
		this.mBis = mBis;
	}

	public String getDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(date != null)
            return simpleDateFormat.format(date);
        else 
        	return null;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	public String getUuidKey() {
		return uuidKey;
	}

	public void setUuidKey(String uuidKey) {
		this.uuidKey = uuidKey;
	}
    
	
    
}