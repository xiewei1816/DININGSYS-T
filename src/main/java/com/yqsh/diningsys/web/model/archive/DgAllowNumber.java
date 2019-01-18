package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;

@SuppressWarnings("serial")
public class DgAllowNumber extends BasePojo{
	
	private Integer id;
	
    private String name;

    private Integer minAllowNumber;

    private String maxAllowNumber;

    
    private String uuidKey;
    
	public String getUuidKey() {
		return uuidKey;
	}

	public void setUuidKey(String uuidKey) {
		this.uuidKey = uuidKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMinAllowNumber() {
		return minAllowNumber;
	}

	public void setMinAllowNumber(Integer minAllowNumber) {
		this.minAllowNumber = minAllowNumber;
	}

	public String getMaxAllowNumber() {
		return maxAllowNumber;
	}

	public void setMaxAllowNumber(String maxAllowNumber) {
		this.maxAllowNumber = maxAllowNumber;
	}
    
}