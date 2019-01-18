package com.yqsh.diningsys.web.model.sysSettings;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

public class SysLogQuery extends BasePojo implements Serializable{
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
    
    
    /**
     * 
     * 查询其它表返回
     */
    private String query_time;//查询时间
    private String operName;//操作元姓名
    private String query_type; //查询类型
    private String query_time_type;//查询时间类型
    private String query_content;//查询值
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

	public String getQuery_time() {
		return query_time;
	}

	public void setQuery_time(String query_time) {
		this.query_time = query_time;
	}

	public Integer getOperId() {
		return operId;
	}

	public void setOperId(Integer operId) {
		this.operId = operId;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public String getQuery_content() {
		return query_content;
	}

	public void setQuery_content(String query_content) {
		this.query_content = query_content;
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

	public String getQuery_time_type() {
		return query_time_type;
	}

	public void setQuery_time_type(String query_time_type) {
		this.query_time_type = query_time_type;
	}
	
	
     
}