package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemDiscountProgramme extends BasePojo implements Serializable{
    private Integer id;

    private Integer recyclebin; //回收站

    private String code; //方案编码

    private String name; //方案名称

    private String type; //方案类型 1/按品项 2/按小类

    private Integer allowFDis; //是否允许前台打折

    private Integer disable; //是否停用

    private Integer dateLimit; //是否开启日期限制

    private Date startTime; //开始时间

    private Date endTime; //结束时间

    private Integer discount; //统一折扣比例

    private Integer useStoreType; 

    private String useStore;
    
    
    private Date time; //修改事件
    
    private String uuidKey;
    
    private String shopKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecyclebin() {
        return recyclebin;
    }

    public void setRecyclebin(Integer recyclebin) {
        this.recyclebin = recyclebin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getAllowFDis() {
        return allowFDis;
    }

    public void setAllowFDis(Integer allowFDis) {
        this.allowFDis = allowFDis;
    }

    public Integer getDisable() {
        return disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    public Integer getDateLimit() {
        return dateLimit;
    }

    public void setDateLimit(Integer dateLimit) {
        this.dateLimit = dateLimit;
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getUseStoreType() {
        return useStoreType;
    }

    public void setUseStoreType(Integer useStoreType) {
        this.useStoreType = useStoreType;
    }

    public String getUseStore() {
        return useStore;
    }

    public void setUseStore(String useStore) {
        this.useStore = useStore == null ? null : useStore.trim();
    }

	public String  getTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time != null)
            return simpleDateFormat.format(time);
        else 
        	return null;
	}

	public void setTime(Date time) {
		this.time = time;
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