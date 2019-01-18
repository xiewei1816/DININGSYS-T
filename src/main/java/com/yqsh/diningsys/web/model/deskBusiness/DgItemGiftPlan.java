package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemGiftPlan extends BasePojo implements Serializable{
    private Integer id;
    
    private String name; //方案名称

    private Integer recycle; //回收站

    private Integer itemId;//品项id

    private String itemCode;//品项代码

    private Date startDate; //开始日期

    private Date endDate; //结束日期

    private String startTime; //开始时间(每天)

    private Integer enable; //是否启用

    private String endTime; //结束时间(每天)

    private Integer week1;//周一是否启用

    private Integer week2;//周二是否启用

    private Integer week3;//周三是否启用

    private Integer week4;//周四是否启用

    private Integer week5;//周五是否启用

    private Integer week6;//周六是否启用

    private Integer week7;//周日是否启用

    private String explains; //说明

    private Integer giftAmount; //每次赠送数量

    private Integer giftFrequencyLimit; //赠送次数限定

    
    private String itemName; //查询字段品项名称
    
    private String uuidKey;
    
    private String shopKey;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecycle() {
        return recycle;
    }

    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getWeek1() {
        return week1;
    }

    public void setWeek1(Integer week1) {
        this.week1 = week1;
    }

    public Integer getWeek2() {
        return week2;
    }

    public void setWeek2(Integer week2) {
        this.week2 = week2;
    }

    public Integer getWeek3() {
        return week3;
    }

    public void setWeek3(Integer week3) {
        this.week3 = week3;
    }

    public Integer getWeek4() {
        return week4;
    }

    public void setWeek4(Integer week4) {
        this.week4 = week4;
    }

    public Integer getWeek5() {
        return week5;
    }

    public void setWeek5(Integer week5) {
        this.week5 = week5;
    }

    public Integer getWeek6() {
        return week6;
    }

    public void setWeek6(Integer week6) {
        this.week6 = week6;
    }

    public Integer getWeek7() {
        return week7;
    }

    public void setWeek7(Integer week7) {
        this.week7 = week7;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains == null ? null : explains.trim();
    }

    public Integer getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(Integer giftAmount) {
        this.giftAmount = giftAmount;
    }

    public Integer getGiftFrequencyLimit() {
        return giftFrequencyLimit;
    }

    public void setGiftFrequencyLimit(Integer giftFrequencyLimit) {
        this.giftFrequencyLimit = giftFrequencyLimit;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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