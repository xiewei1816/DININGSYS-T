package com.yqsh.diningsys.web.model.print;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgPrintManagerS extends BasePojo implements Serializable{
    private Integer id;

    private Integer pId;

    private Integer itemId;

    private String name;
    
    private String num;
    
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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	} 
}