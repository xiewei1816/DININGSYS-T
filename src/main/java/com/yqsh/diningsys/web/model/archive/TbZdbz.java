package com.yqsh.diningsys.web.model.archive;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;


/**
 * 整单备注实体
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:28:03
 */
public class TbZdbz extends BasePojo{

	private Integer id;

    private String zdbzNum;    //编号

    private String orderRemark;    //整单备注
 
    private String zdbzOrganization;    //所属组织机构
     
    private String isDel;  //是否回收

    private Date createTime;  //创建时间

	private String shopKey;

	private String uuidKey;

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

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

	public String getZdbzNum() {
		return zdbzNum;
	}

	public void setZdbzNum(String zdbzNum) {
		this.zdbzNum = zdbzNum;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getZdbzOrganization() {
		return zdbzOrganization;
	}

	public void setZdbzOrganization(String zdbzOrganization) {
		this.zdbzOrganization = zdbzOrganization;
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

}