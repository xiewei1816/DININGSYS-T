package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemTypeDiscountQuery extends BasePojo implements Serializable{
    private Integer id;

    private String dgNum;

    private Integer dgId;

    private Integer discount;
    
    /*
     * 
     * 小类名称，关联获得
     */
    private String dgName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDgNum() {
        return dgNum;
    }

    public void setDgNum(String dgNum) {
        this.dgNum = dgNum == null ? null : dgNum.trim();
    }

    public Integer getDgId() {
        return dgId;
    }

    public void setDgId(Integer dgId) {
        this.dgId = dgId;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

	public String getDgName() {
		return dgName;
	}

	public void setDgName(String dgName) {
		this.dgName = dgName;
	}


    
}