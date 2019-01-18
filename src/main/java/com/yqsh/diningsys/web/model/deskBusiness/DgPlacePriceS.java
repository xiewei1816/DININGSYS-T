package com.yqsh.diningsys.web.model.deskBusiness;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgPlacePriceS extends BasePojo{
    private Integer id;

    private Integer placeId; //区域id

    private Integer placePriceId; //区域价格对应id

    private Integer itemId; //品项id

    private Double price; //区域品项价格

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

    public Integer getPlacePriceId() {
        return placePriceId;
    }

    public void setPlacePriceId(Integer placePriceId) {
        this.placePriceId = placePriceId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}