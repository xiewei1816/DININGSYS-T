package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysRoleMapper extends GenericDao<SysRole,Integer>{

    /*角色后台权限菜单以及功能*/
    List<SysRole> selectByConAndPage(Page<SysRole> sysRolePage,Map params);

    int deleteByPrimaryKeys(List ids);

    int lockByPrimaryKeys(Map params);

    /**
     * 删除sys_role_menu
     * @param id
     * @return
     */
    int deleteRoleMenuByRoleId(Integer id);

    /**
     * 删除sys_role_menu
     * @param ids
     * @return
     */
    int deleteRoleMenuByRoleIds(List ids);

    /**
     * 插入权限
     * @param list
     * @return
     */
    int insertRoleMenu(List list);

    /**
     *
     * @param id
     * @return
     */
    List<SysMenu> selectRoleMenu(Integer id);

    List<SysRole> selectRoleByUserId(Map params);

    List<SysRole> selectRoleByLoginUserId(Map params);

    List<SysRole> selectAllRole();

    void deleteUserRoleByRoleId(Integer id);

    void deleteUserRoleByUserId(Integer id);

    void insertRoleUser(List list);

    /**
     * 通过用户ID获取业务使用权限常规
     * @param userid
     * @return
     */
    Map<String, Object> getPerBusinessByUserId(Map userid);

}