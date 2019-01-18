package com.yqsh.diningsys.web.model;

import java.io.Serializable;

/**
 * role  menu  temp  table
 */
public class SysRoleMenu implements Serializable{
    private static final long serialVersionUID = -6206893147341914700L;
    private String roleCode;

    private Integer menuId;

    private String uuidKey;

    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}