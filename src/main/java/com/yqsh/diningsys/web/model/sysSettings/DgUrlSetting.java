package com.yqsh.diningsys.web.model.sysSettings;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;


public class DgUrlSetting extends BasePojo implements Serializable{

    private static final long serialVersionUID = 3687314210541648542L;

    private Integer id;
	
    private String code;

    private String value;
    
    private String name;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}