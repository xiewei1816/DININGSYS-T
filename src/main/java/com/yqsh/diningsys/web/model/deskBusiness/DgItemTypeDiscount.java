package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

@SuppressWarnings("serial")
public class DgItemTypeDiscount extends BasePojo implements Serializable{
    private Integer id;

    private String dgNum; //小类num

    private Integer dgId; //小类id

    private Integer discount; //允许打折
    

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



    
}