package com.yqsh.diningsys.web.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author  zhshuo
 * 角色model
 */
public class SysRole implements Serializable{
    private static final long serialVersionUID = -2894804686839574499L;
    private Integer id;

    private String roleName;

    /**
     * 角色签名
     */
    private String roleSign;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private String state;

    /**
     * 用户模块中，该用户是否有该角色
     */
    private String selected;

    private Integer bgroup;

    private Integer theShop;

    private String empOrganizationName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleSign() {
        return roleSign;
    }

    public void setRoleSign(String roleSign) {
        this.roleSign = roleSign == null ? null : roleSign.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCreateTime() {
        if(createTime != null)
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
        return null;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Integer getBgroup() {
        return bgroup;
    }

    public void setBgroup(Integer bgroup) {
        this.bgroup = bgroup;
    }

    public Integer getTheShop() {
        return theShop;
    }

    public void setTheShop(Integer theShop) {
        this.theShop = theShop;
    }

    public String getEmpOrganizationName() {
        return empOrganizationName;
    }

    public void setEmpOrganizationName(String empOrganizationName) {
        this.empOrganizationName = empOrganizationName;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}