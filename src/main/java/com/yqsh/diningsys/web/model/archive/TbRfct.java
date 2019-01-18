package com.yqsh.diningsys.web.model.archive;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.yqsh.diningsys.core.util.BasePojo;


/**
 * 退菜原因类型实体
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:28:03
 */
public class TbRfct extends BasePojo{

	private Integer id;

    private String rfctCode;    //编号

    private String rfctName;    //名称

    private String rfctOrganization;    //所属组织机构
     
    private String isDel;  //是否回收
    
    private String isDefaultFlag;  //默认设置

    private Date createTime;  //创建时间
    
    private Date updateTime; //更新时间

    private String uuidKey;
    
    private List<TbRfc>  tbRfcs;
    
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

	public String getRfctCode() {
		return rfctCode;
	}

	public void setRfctCode(String rfctCode) {
		this.rfctCode = rfctCode;
	}

	public String getRfctName() {
		return rfctName;
	}

	public void setRfctName(String rfctName) {
		this.rfctName = rfctName;
	}

	public String getRfctOrganization() {
		return rfctOrganization;
	}

	public void setRfctOrganization(String rfctOrganization) {
		this.rfctOrganization = rfctOrganization;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getIsDefaultFlag() {
		return isDefaultFlag;
	}

	public void setIsDefaultFlag(String isDefaultFlag) {
		this.isDefaultFlag = isDefaultFlag;
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

	public List<TbRfc> getTbRfcs() {
		return tbRfcs;
	}

	public void setTbRfcs(List<TbRfc> tbRfcs) {
		this.tbRfcs = tbRfcs;
	}

	
}