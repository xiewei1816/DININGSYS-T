package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysMenuMapper {
    int deleteByPrimaryKey(Map params);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> selectAllMenu();

    List<SysMenu> selectAllMenuWithOutSelf(Integer id);

    int modifyMenuState(Map para);

    Integer selectNextOrderByMenuId(Integer id);

    //TODO 用户登录相关
    /*获取登录用户功能permission*/
    List<SysMenu> selectLoginUserFunPermission(Map params);

    List<SysMenu> selectLoginUserDutiesPermission(Map params);

    /*获取登录用户菜单permission*/
    List<SysMenu> selectLoginUserMenuPermission(Map param);

    /*获取父级菜单下的子菜单，用来删除*/
    List<Integer> selectDeleteIdByParentId(Integer id);

    /*删除菜单之前判断是否存在角色分配了该菜单*/
    List<SysRoleMenu> selectRoleByMenuId(Integer id);

    /*餐饮使用相关*/
    List<SysMenu> selectBaseMenu();

    List<SysMenu> getSysSecMenuTree(Integer id);

    List<SysMenu> getSysOpeMenuTree(Integer id);

    List<SysMenu> selectSysPerLevelOneData();

    List<SysMenu> getAllBakcgroudMenu(Map param);

    List<SysMenu> getAllDeskMenu(Map param);

    List<SysMenu> getUserMenu(Map param);

    /**
     * 根据职务code与菜单ID，确认该职务是否包含该权限
     * @param param
     * @return
     */
    SysRoleMenu selectDutiesMenu(Map param);

    /**
     * 根据菜单根节点获取下面的所有菜单ID
     * @param param
     * @return
     */
    String getMenuByRootId(Map param);

    /**
     * 获取前台所有菜单的ID，不包括按钮
     * @param param
     * @return
     */
    String getClienMenuByRootId(Map param);

    /**
     * 根据菜单根节点获取下面的所有按钮ID，不包括菜单
     * @param param
     * @return
     */
    String getBtnsByRootId(Map param);

    void delDutiesMenu(Map param);

    void insertDutiesMenu(Map param);

    List<SysMenu> selectUserReceptionMenu(Map param);

    SysRoleMenu selectCheckPermission(Map<String,Object> param);

    SysMenu selectMenuByMenuCode(@Param("menuCode") String function_code);
}