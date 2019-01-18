package com.yqsh.diningsys.web.model.archive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DgItemFileType {
    private Integer id;

    private Integer itemFileTypeId;

    private String num;

    private String name;

    private String zjf;

    private String pp;

    private String description;

    private Integer qyzzfflbxd;

    private String qyzzfflbids;

    private Date updateTime;

    private Date createTime;

    private Integer pId;

    private Integer defaultSczt;

    private Integer operator;

    private Integer delFlag;

    private String couponCode;

    private Integer zdxdsl;
    
    private Integer yxe = 0;

    private String uuidKey;
    
    //查询小类
    private List<DgItemFileType> smallItemTypes;

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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getZjf() {
        return zjf;
    }

    public void setZjf(String zjf) {
        this.zjf = zjf == null ? null : zjf.trim();
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getQyzzfflbxd() {
        return qyzzfflbxd;
    }

    public void setQyzzfflbxd(Integer qyzzfflbxd) {
        this.qyzzfflbxd = qyzzfflbxd;
    }

    public String getQyzzfflbids() {
        return qyzzfflbids;
    }

    public void setQyzzfflbids(String qyzzfflbids) {
        this.qyzzfflbids = qyzzfflbids == null ? null : qyzzfflbids.trim();
    }

    public String  getUpdateTime() {
        return updateTime != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(updateTime):null;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String  getCreateTime() {
        return createTime != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime):null;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDefaultSczt() {
        return defaultSczt;
    }

    public void setDefaultSczt(Integer defaultSczt) {
        this.defaultSczt = defaultSczt;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getZdxdsl() {
        return zdxdsl;
    }

    public void setZdxdsl(Integer zdxdsl) {
        this.zdxdsl = zdxdsl;
    }

    public Integer getItemFileTypeId() {
        return itemFileTypeId;
    }

    public void setItemFileTypeId(Integer itemFileTypeId) {
        this.itemFileTypeId = itemFileTypeId;
    }

	public List<DgItemFileType> getSmallItemTypes() {
		return smallItemTypes;
	}

	public void setSmallItemTypes(List<DgItemFileType> smallItemTypes) {
		this.smallItemTypes = smallItemTypes;
	}

	public Integer getYxe() {
		return yxe;
	}

	public void setYxe(Integer yxe) {
		this.yxe = yxe;
	}

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}