package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 制作方法
 */
public class DgProMethods extends BasePojo{
    private Integer id;

    private String pmcode;

    private String pmname;

    private String pmmnemonic;

    private Integer pmtid;

    private Integer orderno;

    private Integer dept;

    private Integer collectProMoney;

    private Double proMoney;

    private Integer collectProMoneybynum = 0;

    private Integer canUpdate;

    private String description;

    private Integer organid;

    private Date createTime;

    private Integer delflag;

    private Integer isSpecialMethods;

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

    public String getPmcode() {
        return pmcode;
    }

    public void setPmcode(String pmcode) {
        this.pmcode = pmcode == null ? null : pmcode.trim();
    }

    public String getPmname() {
        return pmname;
    }

    public void setPmname(String pmname) {
        this.pmname = pmname == null ? null : pmname.trim();
    }

    public String getPmmnemonic() {
        return pmmnemonic;
    }

    public void setPmmnemonic(String pmmnemonic) {
        this.pmmnemonic = pmmnemonic == null ? null : pmmnemonic.trim();
    }

    public Integer getPmtid() {
        return pmtid;
    }

    public void setPmtid(Integer pmtid) {
        this.pmtid = pmtid;
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public Integer getDept() {
        return dept;
    }

    public void setDept(Integer dept) {
        this.dept = dept;
    }

    public Integer getCollectProMoney() {
        return collectProMoney;
    }

    public void setCollectProMoney(Integer collectProMoney) {
        this.collectProMoney = collectProMoney;
    }

    public Double getProMoney() {
        return proMoney;
    }

    public void setProMoney(Double proMoney) {
        this.proMoney = proMoney;
    }

    public Integer getCollectProMoneybynum() {
        return collectProMoneybynum;
    }

    public void setCollectProMoneybynum(Integer collectProMoneybynum) {
        this.collectProMoneybynum = collectProMoneybynum;
    }

    public Integer getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Integer canUpdate) {
        this.canUpdate = canUpdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Integer getIsSpecialMethods() {
        return isSpecialMethods;
    }

    public void setIsSpecialMethods(Integer isSpecialMethods) {
        this.isSpecialMethods = isSpecialMethods;
    }

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}
    
    
}