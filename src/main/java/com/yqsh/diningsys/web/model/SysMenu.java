package com.yqsh.diningsys.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysMenu implements Serializable{

    private static final long serialVersionUID = 4480305356507483457L;

    private Integer id;

    private Integer parentId;

    private String parentName;

    private String menuName;

    private String menuUrl;

    private String menuCode;

    private String menuState;

    private Integer menuOrder;

    private String menuIcon;

    private Integer menuType;

    /**
     * 区分功能权限和普通菜单
     */
    private Map<String,String> font;

    /**
     * 设置是否选中
     */
    private String checked;

    /**
     * 前台客户端是否启用该菜单的标志，值0|1
     */
    private Integer permissionFlag;

    private List<SysMenu> child = new ArrayList<SysMenu>();

    private List<SysMenu> backgroundChild = new ArrayList<>();

    /**
     * 前台客户端确定菜单下面的按钮集合
     */
    private List<SysMenu> clientButtons = new ArrayList<>();

    public Integer getPermissionFlag() {
        return permissionFlag;
    }

    public void setPermissionFlag(Integer permissionFlag) {
        this.permissionFlag = permissionFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuState() {
        return menuState;
    }

    public void setMenuState(String menuState) {
        this.menuState = menuState == null ? null : menuState.trim();
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Map<String, String> getFont() {
        if(menuType.equals(2)){
            Map<String,String> color = new HashMap<>();
            color.put("color","blue");
            setFont(color);
        }
        return font;
    }

    public void setFont(Map<String, String> font) {
        this.font = font;
    }

    public Boolean getChecked() {
        return checked != null;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public List<SysMenu> getChild() {
        return child;
    }

    public List<SysMenu> getReceptionMenu() {
        for(SysMenu sysMenu:child){
            if(sysMenu.getBackgroundChild() != null)sysMenu.setBackgroundChild(null);
        }
        return child;
    }

    /**
     * 获取后台菜单
     * @return
     */
    public List<SysMenu> getBackgroundChild(){
        List<SysMenu> reChild = new ArrayList<>();
        for(SysMenu sysMenu:child){
            if(!sysMenu.getParentId().equals(167)){
                reChild.add(sysMenu);
            }
        }
        return reChild;
    }

    public void setBackgroundChild(List<SysMenu> backgroundChild) {
        this.backgroundChild = backgroundChild;
    }

    public void setChild(List<SysMenu> child) {
        this.child = child;
    }

    public List<SysMenu> getClientButtons() {
        return clientButtons;
    }

    public void setClientButtons(List<SysMenu> clientButtons) {
        this.clientButtons = clientButtons;
    }
}