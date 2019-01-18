package com.yqsh.diningsys.web.model.archive;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * 费用类型实体
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:28:03
 */
public class TbFylx extends BasePojo{

	private Integer id;

    private String fylxNum;    //编号

    private String fylxName;    //名称（费用项目）
        
    private String fylxOrganization;    //所属组织机构
     
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

	public String getfylxNum() {
		return fylxNum;
	}

	public void setfylxNum(String fylxNum) {
		this.fylxNum = fylxNum;
	}

	public String getfylxName() {
		return fylxName;
	}

	public void setfylxName(String fylxName) {
		this.fylxName = fylxName;
	}

	public String getfylxOrganization() {
		return fylxOrganization;
	}

	public void setfylxOrganization(String fylxOrganization) {
		this.fylxOrganization = fylxOrganization;
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