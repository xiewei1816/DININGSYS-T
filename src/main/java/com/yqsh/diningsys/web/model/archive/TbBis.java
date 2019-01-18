package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 营业市别管理
 *
 * @author xiewei
 * @version 创建时间：2016年10月10日 下午2:41:27
 */
public class TbBis extends BasePojo {

    private Integer id;

    private String bisName;    //名称

    private String bisTime;   //开始时间

    private String bisOrganization;   //所属机构

    private String isDel;  //是否删除 0-未删除 1-已删除

    private Date createTime;  //创建时间

    //属性新增
    private String beginTime;

    private String endTime;

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

    public String getBisName() {
        return bisName;
    }

    public void setBisName(String bisName) {
        this.bisName = bisName;
    }

    public String getBisTime() {
        return bisTime;
    }

    public void setBisTime(String bisTime) {
        this.bisTime = bisTime;
    }

    public String getBisOrganization() {
        return bisOrganization;
    }

    public void setBisOrganization(String bisOrganization) {
        this.bisOrganization = bisOrganization;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getCreateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (createTime != null)
            return simpleDateFormat.format(createTime);
        else
            return null;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}