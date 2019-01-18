package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemProDepS extends BasePojo implements Serializable{
    private Integer id;

    private Integer placeId;//区域id

    private Integer proDepId; //主表id

    private Integer itemId; //品项id

    private Integer depId; //出品部门id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getProDepId() {
        return proDepId;
    }

    public void setProDepId(Integer proDepId) {
        this.proDepId = proDepId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }
}