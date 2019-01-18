package com.yqsh.diningsys.web.model.sysSettings;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

public class SysLog extends BasePojo implements Serializable{
    private Integer id;

    private Integer type;
    
    /**
     * 数据库为oper_id
     * 转为Java字段下划线变大写
     */
    private Integer operId;

    private Date time;

    private String wind;

    private String content;
    
    private String openWater;
    
    private String settlementWater;
    public SysLog()
    {
    	
    }
    public SysLog(Integer type,Integer operId,Date time,String wind,String content,String openWater,String settlementWater)
    {
    	this.type = type;
    	this.operId = operId;
    	this.time = time;
    	this.wind = wind;
    	this.content = content;
    	this.openWater = openWater;
    	this.settlementWater = settlementWater;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time != null)
            return simpleDateFormat.format(time);
        else 
        	return null;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind == null ? null : wind.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public Integer getOperId() {
		return operId;
	}

	public void setOperId(Integer operId) {
		this.operId = operId;
	}

	public String getOpenWater() {
		return openWater;
	}

	public void setOpenWater(String openWater) {
		this.openWater = openWater;
	}

	public String getSettlementWater() {
		return settlementWater;
	}

	public void setSettlementWater(String settlementWater) {
		this.settlementWater = settlementWater;
	}
  
	
     
}