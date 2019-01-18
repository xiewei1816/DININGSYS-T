package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemPricePriority extends BasePojo implements Serializable{
    private Integer id;

    private String code; //价格优先级编码

    private String name; //价格优先级名称

    private Integer nIndex; //价格优先级顺序
 
    private Integer mIndex; //默认顺序

    private Integer enable; //是否使用

    private String uuidKey;
    
    private String shopKey;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getnIndex() {
		return nIndex;
	}

	public void setnIndex(Integer nIndex) {
		this.nIndex = nIndex;
	}

	public Integer getmIndex() {
        return mIndex;
    }

    public void setmIndex(Integer mIndex) {
        this.mIndex = mIndex;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
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