package com.yqsh.diningsys.web.service.base;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRole;

import java.util.List;
import java.util.Map;

/**
 * sysRole Service
 *
 * @author zhshuo create on 2016-07-26 17:59
 */
public interface SysRoleService extends GenericService<SysRole,Integer> {
    /*角色后台权限菜单以及功能*/

    List<SysRole> selectByConAndPage(Page<SysRole> page, Map params);


    /**
     * 获取该角色的权限
     * @param id
     * @return
     */
    List<SysMenu> selectRoleMenu(Integer id);

    /**
     * 多项删除
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(List ids);

    /**
     * 多项lock
     * @param params
     * @return
     */
    int lockByPrimaryKeys(Map params);

    /**
     * 每次权限操作都会清空该角色权限 在一次性插入
     * @param roelMenuId
     * @return
     */
    void insertRoleMenu(Integer roleId,String roelMenuId);

    List<SysRole> selectRoleByUserId(Map params);

    List<SysRole> selectRoleByLoginUserId(Map params);

    List<SysRole> selectAllRole();

    /**
     * 2016年11月2日10:16:57 餐饮系统
     * @param sysRole
     * @param userIds
     */
    void editRoleUser (SysRole sysRole,String userIds);

}
