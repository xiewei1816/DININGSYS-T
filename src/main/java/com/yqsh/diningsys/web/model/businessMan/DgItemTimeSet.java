package com.yqsh.diningsys.web.model.businessMan;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 品项显示时间设置
 * 此表记录品项刷新频率、品项统计起始日期
 * @author xiewei
 *
 */
public class DgItemTimeSet extends BasePojo{

	private Integer id;

    private Integer refreshHz;    //刷新频率
    
    private String startDate;  //品项统计起始日期

    private Date createTime;  //创建时间
 
    private Date updateTime;    //修改时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRefreshHz() {
		return refreshHz;
	}

	public void setRefreshHz(Integer refreshHz) {
		this.refreshHz = refreshHz;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getCreateTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(createTime != null)
            return simpleDateFormat.format(createTime);
        else return null;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(updateTime != null)
            return simpleDateFormat.format(updateTime);
        else return null;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}