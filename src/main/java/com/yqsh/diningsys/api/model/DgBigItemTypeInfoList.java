package com.yqsh.diningsys.api.model;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;

import java.util.List;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2017-01-04 16:53
 */
public class DgBigItemTypeInfoList {

    private String bigItemTypeName;

    private Double itemCostsSum;

    private List<DgOwConsumerDetails> allItems;

    public String getBigItemTypeName() {
        return bigItemTypeName;
    }

    public void setBigItemTypeName(String bigItemTypeName) {
        this.bigItemTypeName = bigItemTypeName;
    }

    public Double getItemCostsSum() {
        return itemCostsSum;
    }

    public void setItemCostsSum(Double itemCostsSum) {
        this.itemCostsSum = itemCostsSum;
    }

    public List<DgOwConsumerDetails> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<DgOwConsumerDetails> allItems) {
        this.allItems = allItems;
    }
}
