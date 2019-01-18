package com.yqsh.diningsys.web.model.businessMan;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 费用登记实体
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:28:03
 */
public class TbFydj extends BasePojo{

	private Integer id;
	
	private String fydjName;    //费用名称

    private String fydjType;    //费用类型
    
    private Double fydjExpend;    //支出费用
    
    private Double fydjEarning;    //收入费用
    
    private String fydjTime;    //费用发生时间
    
    private String fydjOrganization;    //所属组织机构
     
    private String fydjAbstract;  //摘要

    private Date createTime;  //创建时间
    
    /**
     * 查询条件
     */
    private String startTime;
    private String endTime;

    private String uuidKey;

    private String shopKey;
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

	public String getFydjName() {
		return fydjName;
	}

	public void setFydjName(String fydjName) {
		this.fydjName = fydjName;
	}

	public String getFydjType() {
		return fydjType;
	}

	public void setFydjType(String fydjType) {
		this.fydjType = fydjType;
	}

	public Double getFydjExpend() {
		return fydjExpend;
	}

	public void setFydjExpend(Double fydjExpend) {
		this.fydjExpend = fydjExpend;
	}

	public Double getFydjEarning() {
		return fydjEarning;
	}

	public void setFydjEarning(Double fydjEarning) {
		this.fydjEarning = fydjEarning;
	}

	public String getFydjTime() {
		int len = fydjTime.length();
		String str = fydjTime.substring(len-2,len);
		if(str.equals(".0")){
			fydjTime = fydjTime.substring(0,len-2);
		}
		return fydjTime;
	}

	public void setFydjTime(String fydjTime) {
		this.fydjTime = fydjTime;
	}

	public String getFydjOrganization() {
		return fydjOrganization;
	}

	public void setFydjOrganization(String fydjOrganization) {
		this.fydjOrganization = fydjOrganization;
	}

	public String getFydjAbstract() {
		return fydjAbstract;
	}

	public void setFydjAbstract(String fydjAbstract) {
		this.fydjAbstract = fydjAbstract;
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
	
	/**
	 * 查询条件
	 */
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}
    
	
}