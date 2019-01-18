package com.yqsh.diningsys.web.model.archive;

import java.util.List;

public class DgFlavor {
    private Integer id;

    private String number;

    private String zjf;

    private String name;

    private Integer px;

    private Integer parentid;
    
    private String createtime;
    
    private String isonly;

    private List<DgFlavor> child;

    private String uuidKey;

    private String shopKey;
    
    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getZjf() {
        return zjf;
    }

    public void setZjf(String zjf) {
        this.zjf = zjf == null ? null : zjf.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPx() {
        return px;
    }

    public void setPx(Integer px) {
        this.px = px;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getIsonly() {
        return isonly;
    }

    public void setIsonly(String isonly) {
        this.isonly = isonly;
    }

	public List<DgFlavor> getChild() {
		return child;
	}

	public void setChild(List<DgFlavor> child) {
		this.child = child;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}
    
    
}