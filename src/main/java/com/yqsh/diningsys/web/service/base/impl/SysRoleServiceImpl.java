package com.yqsh.diningsys.web.service.base.impl;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.SysRoleMapper;
import com.yqsh.diningsys.web.dao.SysUserMapper;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRole;
import com.yqsh.diningsys.web.service.base.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * sysroleServiceimpl
 *
 * @author zhshuo create on 2016-07-27 9:38
 */
@Service
public class SysRoleServiceImpl extends GenericServiceImpl<SysRole,Integer> implements SysRoleService{

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public GenericDao<SysRole, Integer> getDao() {
        return sysRoleMapper;
    }

    @Override
    public List<SysRole> selectByConAndPage(Page<SysRole> page, Map params) {
        return sysRoleMapper.selectByConAndPage(page,params);
    }

    @Override
    public List<SysMenu> selectRoleMenu(Integer id) {
        return sysRoleMapper.selectRoleMenu(id);
    }

    @Override
    public int deleteByPrimaryKeys(List ids) {
        sysRoleMapper.deleteRoleMenuByRoleIds(ids);
        return sysRoleMapper.deleteByPrimaryKeys(ids);
    }

    @Override
    public int lockByPrimaryKeys(Map params) {
        return sysRoleMapper.lockByPrimaryKeys(params);
    }

    @Override
    public void insertRoleMenu(Integer roleId,String roleMenuId) {
        sysRoleMapper.deleteRoleMenuByRoleId(roleId);
        if(!StringUtils.isEmpty(roleMenuId)){
            List<Map> list = new ArrayList<>();
            for(String menuId:roleMenuId.split(",")){
                Map param = new HashMap();
                param.put("roleId",roleId);
                param.put("menuId",Integer.parseInt(menuId));
                list.add(param);
            }
            sysRoleMapper.insertRoleMenu(list);
        }

    }

    @Override
    public List<SysRole> selectRoleByUserId(Map params) {
        return sysRoleMapper.selectRoleByUserId(params);
    }

    @Override
    public List<SysRole> selectRoleByLoginUserId(Map params) {
        return sysRoleMapper.selectRoleByLoginUserId(params);
    }

    @Override
    public List<SysRole> selectAllRole() {
        return sysRoleMapper.selectAllRole();
    }

    @Override
    public void editRoleUser(SysRole sysRole, String userIds) {
        if(sysRole.getId() != null){
            sysRoleMapper.updateByPrimaryKeySelective(sysRole);
            //先清除该角色下面的所有用户
            sysRoleMapper.deleteUserRoleByRoleId(sysRole.getId());
        }else{
            sysRole.setTheShop(1);
            sysRole.setState("1");
            sysRole.setCreateTime(new Date());
            sysRoleMapper.insertSelective(sysRole);
        }
        List<Map> sysRoles = new ArrayList<>();
        if(!StringUtils.isEmpty(userIds) ){
            for(String userId:userIds.split(",")){
                Map map = new HashMap();
                map.put("userId",Integer.parseInt(userId));
                map.put("roleId",sysRole.getId());
                sysRoles.add(map);
            }
            sysRoleMapper.insertRoleUser(sysRoles);
        }
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        sysRoleMapper.deleteUserRoleByRoleId(id);
        sysRoleMapper.deleteRoleMenuByRoleId(id);
        return sysRoleMapper.deleteByPrimaryKey(id);
    }
}
