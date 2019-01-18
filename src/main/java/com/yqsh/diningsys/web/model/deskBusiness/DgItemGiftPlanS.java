package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemGiftPlanS extends BasePojo implements Serializable{
    private Integer id;

    private Integer itemId; //品项id

    private String itemCode; //品项代码

    private Integer giftAcount; //赠送数量

    private Integer enable; //启用

    private Integer parentId; //父表id
    
    
    private String name; //查询字段 品项名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public Integer getGiftAcount() {
        return giftAcount;
    }

    public void setGiftAcount(Integer giftAcount) {
        this.giftAcount = giftAcount;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}