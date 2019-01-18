package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemMemberDiscount extends BasePojo implements Serializable{
    private Integer id;

    private String name; //名称

    private Integer enable; //启用

    private String explains; //说明

    private Date startDate; //开始日期

    private Date endDate; //结束日期

    private String dayStartTime; //每天开始时间

    private String dayEndTime; //每天结束时间

    private String week; //每周启用限定  json

    private Integer useAllShop;  //0/1 0为指定店1为所有店

    private String affiliation; //所属机构(1/2/3/4)模式

    private Integer publish; //是否发布

    private Date updateTime; //更新时间

    private Integer recycleBin; //回收站
    
    private String levelId;//会员等级id
    
    
    private String searchDate;//查询日期
    private String searchTime;//查询时间段

    private String uuidKey;
    
    private String shopKey;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }


    public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public String getStartDate() {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(startDate != null)
            return simpleDateFormat.format(startDate);
        else 
        	return null;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(endDate != null)
            return simpleDateFormat.format(endDate);
        else 
        	return null;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDayStartTime() {
        return dayStartTime;
    }

    public void setDayStartTime(String dayStartTime) {
        this.dayStartTime = dayStartTime == null ? null : dayStartTime.trim();
    }

    public String getDayEndTime() {
        return dayEndTime;
    }

    public void setDayEndTime(String dayEndTime) {
        this.dayEndTime = dayEndTime == null ? null : dayEndTime.trim();
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public Integer getUseAllShop() {
        return useAllShop;
    }

    public void setUseAllShop(Integer useAllShop) {
        this.useAllShop = useAllShop;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation == null ? null : affiliation.trim();
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public String getUpdateTime() {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(updateTime != null)
            return simpleDateFormat.format(updateTime);
        else 
        	return null;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRecycleBin() {
        return recycleBin;
    }

    public void setRecycleBin(Integer recycleBin) {
        this.recycleBin = recycleBin;
    }

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}

	public String getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
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