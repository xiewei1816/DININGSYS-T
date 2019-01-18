package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgForMealTimePriceS extends BasePojo implements Serializable{
    private Integer id;

    private Integer mealTimeId; //市别id

    private Integer itemId; //品项id

    private Integer mealTimePriceId; //市别价格id

    private Double price;//品项市别价格

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMealTimeId() {
        return mealTimeId;
    }

    public void setMealTimeId(Integer mealTimeId) {
        this.mealTimeId = mealTimeId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getMealTimePriceId() {
        return mealTimePriceId;
    }

    public void setMealTimePriceId(Integer mealTimePriceId) {
        this.mealTimePriceId = mealTimePriceId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}