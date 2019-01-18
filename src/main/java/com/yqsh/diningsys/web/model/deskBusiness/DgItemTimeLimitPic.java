package com.yqsh.diningsys.web.model.deskBusiness;

public class DgItemTimeLimitPic {
    private Integer id;

    private Integer pId;

    private String picNames;
    
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getPicNames() {
        return picNames;
    }

    public void setPicNames(String picNames) {
        this.picNames = picNames == null ? null : picNames.trim();
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}  
    
}