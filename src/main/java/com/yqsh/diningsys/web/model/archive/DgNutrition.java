package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 营养信息
 */
public class DgNutrition extends BasePojo{
    private Integer id;

    private String code;

    private String name;

    private Double bzsrl;

    private Integer unit;

    private String zjf;

    private Date createTime;

    private Date updateTime;

    private String uuidKey;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getBzsrl() {
        return bzsrl;
    }

    public void setBzsrl(Double bzsrl) {
        this.bzsrl = bzsrl;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getZjf() {
        return zjf;
    }

    public void setZjf(String zjf) {
        this.zjf = zjf == null ? null : zjf.trim();
    }

    public String getCreateTime() {
        return createTime==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime==null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(updateTime);
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}