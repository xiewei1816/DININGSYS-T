package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 制作方法类型
 */
public class DgProMethodsType extends BasePojo{
    private Integer id;

    private String pmtname;

    private Integer pmtorder;

    private Integer organid;

    private Date createTime;

    private Integer delFlag;

    private Integer parentId = 0;

    private String uuidKey;
    
    private String shopKey;
    
    private List<DgProMethods> methods;

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

    public String getPmtname() {
        return pmtname;
    }

    public void setPmtname(String pmtname) {
        this.pmtname = pmtname == null ? null : pmtname.trim();
    }

    public Integer getPmtorder() {
        return pmtorder;
    }

    public void setPmtorder(Integer pmtorder) {
        this.pmtorder = pmtorder;
    }

    public Integer getOrganid() {
        return organid;
    }

    public void setOrganid(Integer organid) {
        this.organid = organid;
    }

    public String getCreateTime() {
        return createTime!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime):null;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

	public List<DgProMethods> getMethods() {
		return methods;
	}

	public void setMethods(List<DgProMethods> methods) {
		this.methods = methods;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}
    
	
    
}