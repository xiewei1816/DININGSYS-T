package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgImportantActivityDiscount extends BasePojo implements Serializable{
    private Integer id;

    private Integer organId; //组织id

    private String name; //方案名称

    private Date startTime; //开始时间

    private Date endTime; //结束时间

    private Integer dustbin; //是否回收站

    private Integer enable; //是否启用

    private Integer discount; //打折百分比
    
    
    private String organName; //组织名称

    private String uuidKey;
    
    private String shopKey;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganId() {
        return organId;
    }

    public void setOrganId(Integer organId) {
        this.organId = organId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStartTime() {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(startTime != null)
            return simpleDateFormat.format(startTime);
        else 
        	return null;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(endTime != null)
            return simpleDateFormat.format(endTime);
        else 
        	return null;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getDustbin() {
        return dustbin;
    }

    public void setDustbin(Integer dustbin) {
        this.dustbin = dustbin;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getUuidKey() {
		return uuidKey;
	}

	public void setUuidKey(String uuidKey) {
		this.uuidKey = uuidKey;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

    
    
}