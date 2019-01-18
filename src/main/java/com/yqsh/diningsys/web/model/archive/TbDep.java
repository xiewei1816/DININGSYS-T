package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 部门管理
 *
 * @author xiewei
 * @version 创建时间：2016年10月10日 下午2:41:27
 */
public class TbDep extends BasePojo {

    private Integer id;

    private String depCode;    //部门编号

    private String depName;    //部门名称

    private String depSjm;     //速记码 

    private String depDepartment;    //所属部门

    private String useType;  //使用类型

    private String depOrganization;  //所属机构

    private String isDel;  //是否删除 0-未删除 1-已删除

    private Date createTime; //创建时间

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

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepSjm() {
        return depSjm;
    }

    public void setDepSjm(String depSjm) {
        this.depSjm = depSjm;
    }

    public String getDepDepartment() {
        return depDepartment;
    }

    public void setDepDepartment(String depDepartment) {
        this.depDepartment = depDepartment;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getDepOrganization() {
        return depOrganization;
    }

    public void setDepOrganization(String depOrganization) {
        this.depOrganization = depOrganization;
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

}