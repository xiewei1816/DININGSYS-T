package com.yqsh.diningsys.web.model.archive;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 费用项目实体
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:28:03
 */
public class TbFyxm extends BasePojo{

	private Integer id;

    private String fyxmNum;    //编号

    private String fyxmName;    //名称（费用项目）
    
    private String fyxmType;    //费用类型
    
    private String fyxmOrganization;    //所属组织机构
     
    private String isDel;  //是否回收

    private Date createTime;  //创建时间
    
    private Date updateTime;  //修改时间

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

	public String getFyxmNum() {
		return fyxmNum;
	}

	public void setFyxmNum(String fyxmNum) {
		this.fyxmNum = fyxmNum;
	}

	public String getFyxmName() {
		return fyxmName;
	}

	public void setFyxmName(String fyxmName) {
		this.fyxmName = fyxmName;
	}

	public String getFyxmType() {
		return fyxmType;
	}

	public void setFyxmType(String fyxmType) {
		this.fyxmType = fyxmType;
	}

	public String getFyxmOrganization() {
		return fyxmOrganization;
	}

	public void setFyxmOrganization(String fyxmOrganization) {
		this.fyxmOrganization = fyxmOrganization;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
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

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

	
}