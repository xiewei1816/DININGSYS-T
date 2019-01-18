package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgImportantAcitivityDiscountS extends BasePojo implements Serializable{
    private Integer id;

    private Integer gateId; //小类id

    private Integer discount; //打折百分比
    
    private Integer pId; //父表id

    
    private String code;//品项代码
    private String name;//品项名称
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGateId() {
        return gateId;
    }

    public void setGateId(Integer gateId) {
        this.gateId = gateId;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}