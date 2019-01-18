package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DgGiftForm extends BasePojo{
    private Integer id;

    private String gfcode;

    private String gfname;

    private Integer gfreason;

    private Date createTime;

    private String gfdescription;

    private String gfreasonName;

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

    public String getGfcode() {
        return gfcode;
    }

    public void setGfcode(String gfcode) {
        this.gfcode = gfcode == null ? null : gfcode.trim();
    }

    public String getGfname() {
        return gfname;
    }

    public void setGfname(String gfname) {
        this.gfname = gfname == null ? null : gfname.trim();
    }

    public Integer getGfreason() {
        return gfreason;
    }

    public void setGfreason(Integer gfreason) {
        this.gfreason = gfreason;
    }

    public String getCreateTime() {
        return (createTime != null)?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime):null;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGfdescription() {
        return gfdescription;
    }

    public void setGfdescription(String gfdescription) {
        this.gfdescription = gfdescription == null ? null : gfdescription.trim();
    }

    public String getGfreasonName() {
        return gfreasonName==null?"默认类型":gfreasonName;
    }

    public void setGfreasonName(String gfreasonName) {
        this.gfreasonName = gfreasonName;
    }
}