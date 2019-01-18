package com.yqsh.diningsys.web.model.deskBusiness;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgItemSaleLimit extends BasePojo implements Serializable{
    private Integer id;

    private Date today; //日期

    private Integer type; //类型   限量类型(是否减去今日预定数量)

    private String uuidKey;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToday() {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(today != null)
            return simpleDateFormat.format(today);
        else 
        	return null;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public String getUuidKey() {
		return uuidKey;
	}

	public void setUuidKey(String uuidKey) {
		this.uuidKey = uuidKey;
	}
    
    
}