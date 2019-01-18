package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgSpecialItem extends BasePojo implements Serializable{
    private Integer id;

    private Integer itemId; //品项id

    private String itemCode; //品项代码

    private Integer type; //类型(1代表服务费品项/2代表最低消费品项)

    private Integer minConsumeType; //如果为type,对应最低消费补齐方式
    
    
    /**
     * 
     * 查询字段
     */
    private String name; //品项名称

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMinConsumeType() {
        return minConsumeType;
    }

    public void setMinConsumeType(Integer minConsumeType) {
        this.minConsumeType = minConsumeType;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
}