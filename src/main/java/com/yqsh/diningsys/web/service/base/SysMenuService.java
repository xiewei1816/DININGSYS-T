package com.yqsh.diningsys.web.service.base;

import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRoleMenu;

import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明该类的作用
 *
 * @author zhshuo create on 2016-07-21 13:15
 */
 public interface SysMenuService {

     List<SysMenu> getAllMenus();

     List<SysMenu> selectAllMenuWithOutSelf(Integer id);

     SysMenu getMenuById(Integer id);

     int addMenu(SysMenu sysMenu);

     int editMenu(SysMenu sysMenu);

     int delMenu(Integer id);

     int modifyMenuState(Integer id,String type);

     Integer selectNextOrderByMenuId(Integer id);

    /**
     * 2016年11月29日11:40:06
     * 根据用户的职务code，获取该职务的系统使用权限，包括菜单以及按钮
     * @param param
     * @return
     */
     List<SysMenu> selectLoginUserDutiesPermission(Map param);

    /*获取登录用户菜单permission*/
     List<SysMenu> selectLoginUserMenuPermission(String empDuties);

     List<Integer> selectMenuDeleteIdByParentId(Integer id);

     List<SysRoleMenu> selectRoleByMenuId(Integer id);

    /*餐饮系统权限使用相关*/

    /**
     * 获取第一级菜单
     * @return
     */
    List<SysMenu> selectBaseMenu();

    /**
     * 获取所有后台菜单
     * @return
     */
    List<SysMenu> getAllBakcgroudMenu(String empDuties);

    /**
     * 获取所有前台菜单
     * @return
     */
    List<SysMenu> getAllDeskMenu(String empDuties);

    /**
     * 点击后台或者前台模块树的实时操作
     * @param dutiesCode
     * @param menuId
     * @param check
     * @return
     */
    void editDutiesMenu(String dutiesCode,Integer menuId,Boolean check);

    /**
     *
     * @param dutiesCode
     * @return
     */
    List<SysMenu> selectReceptionMenu(String dutiesCode);

    SysRoleMenu selectCheckPermission(String dutiesCode, Integer menuId);

    SysMenu selectMenuByMenuCode(String function_code);
}
