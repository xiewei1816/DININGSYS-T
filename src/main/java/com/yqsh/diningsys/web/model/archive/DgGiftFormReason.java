package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DgGiftFormReason extends BasePojo{
    private Integer id;

    private String gfcode;

    private String gfrtype;

    private Date createTime;

    private Date updateTime;

    private String shopKey;

    private String uuidKey;

    private List<DgGiftForm> dgGiftForms;

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGfcode() {
        return gfcode;
    }

    public void setGfcode(String gfcode) {
        this.gfcode = gfcode == null ? null : gfcode.trim();
    }

    public String getGfrtype() {
        return gfrtype;
    }

    public void setGfrtype(String gfrtype) {
        this.gfrtype = gfrtype == null ? null : gfrtype.trim();
    }

    public String  getCreateTime() {
        return (createTime != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime):null;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return (updateTime != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(updateTime):null;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<DgGiftForm> getDgGiftForms() {
        return dgGiftForms;
    }

    public void setDgGiftForms(List<DgGiftForm> dgGiftForms) {
        this.dgGiftForms = dgGiftForms;
    }

    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }
}