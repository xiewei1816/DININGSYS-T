package com.yqsh.diningsys.web.service.base.impl;

import com.yqsh.diningsys.web.dao.SysMenuMapper;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.service.base.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 这里用一句话来说明该类的作用
 *
 * @author zhshuo create on 2016-07-21 13:17
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getAllMenus() {
        return sysMenuMapper.selectAllMenu();
    }

    @Override
    public SysMenu getMenuById(Integer id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addMenu(SysMenu sysMenu) {
        return sysMenuMapper.insertSelective(sysMenu);
    }

    @Override
    public int editMenu(SysMenu sysMenu) {
        return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
    }

    @Override
    public int delMenu(Integer id) {
        List<Integer> ids = selectMenuDeleteIdByParentId(id);
        Map params = new HashMap();
        params.put("id",id);
        params.put("ids",ids.size()==0?null:ids);
        return sysMenuMapper.deleteByPrimaryKey(params);
    }

    @Override
    public List<SysMenu> selectAllMenuWithOutSelf(Integer id) {
        return sysMenuMapper.selectAllMenuWithOutSelf(id);
    }

    @Override
    public int modifyMenuState(Integer id, String state) {
        Map para = new HashMap();
        para.put("id",id);
        para.put("state",state);
        return sysMenuMapper.modifyMenuState(para);
    }

    @Override
    public List<SysMenu> selectLoginUserDutiesPermission(Map param) {
        return sysMenuMapper.selectLoginUserDutiesPermission(param);
    }

    @Override
    public Integer selectNextOrderByMenuId(Integer id) {
        return sysMenuMapper.selectNextOrderByMenuId(id);
    }

    @Override
    public List<SysMenu> selectLoginUserMenuPermission(String empDuties) {
        Map param = new HashMap();
        param.put("empDuties",empDuties);
        param.put("rootId",2);
        List<Integer> backMenuId = StringIdsTOList(sysMenuMapper.getMenuByRootId(param));
        backMenuId.remove(0);
        param.put("list",backMenuId);
        List<SysMenu> sysMenuList = sysMenuMapper.selectLoginUserMenuPermission(param);
        return sysMenuList;
    }

    @Override
    public List<Integer> selectMenuDeleteIdByParentId(Integer id) {
        return sysMenuMapper.selectDeleteIdByParentId(id);
    }

    @Override
    public List<SysRoleMenu> selectRoleByMenuId(Integer id) {
        return sysMenuMapper.selectRoleByMenuId(id);
    }

    @Override
    public List<SysMenu> selectBaseMenu() {
        return sysMenuMapper.selectBaseMenu();
    }

    @Override
    public List<SysMenu> getAllBakcgroudMenu(String empDuties) {
        Map param = new HashMap();
        param.put("empDuties",empDuties);
        //查询出所有后台模块的菜单的ID
        param.put("rootId",2);
        String allIds = sysMenuMapper.getMenuByRootId(param);
        List<Integer> integers = StringIdsTOList(allIds);
        param.put("list",integers);
        List<SysMenu> allBakcgroudMenu = sysMenuMapper.getAllBakcgroudMenu(param);
        List<SysMenu> userMenus = sysMenuMapper.getUserMenu(param);
        for(SysMenu sysMenu:allBakcgroudMenu){
            for(SysMenu userMenu:userMenus){
                if(sysMenu !=null && userMenu != null && sysMenu.getId().equals(userMenu.getId())){
                    sysMenu.setChecked("checked");
                }
            }
        }
        return allBakcgroudMenu;
    }

    @Override
    public List<SysMenu> getAllDeskMenu(String empDuties) {
        Map param = new HashMap();
        param.put("empDuties",empDuties);
        //查询出所有前台模块菜单的ID
        param.put("rootId",394);
        String allIds = sysMenuMapper.getMenuByRootId(param);
        List<Integer> integers = StringIdsTOList(allIds);
        param.put("list",integers);
        List<SysMenu> allDeskMenu = sysMenuMapper.getAllDeskMenu(param);
        List<SysMenu> userMenus = sysMenuMapper.getUserMenu(param);
        for(SysMenu sysMenu:allDeskMenu){
            for(SysMenu userMenu:userMenus){
                if(sysMenu !=null && userMenu!= null && sysMenu.getId().equals(userMenu.getId())){
                    sysMenu.setChecked("checked");
                }
            }
        }
        return allDeskMenu;
    }

    @Override
    public List<SysMenu> selectReceptionMenu(String dutiesCode) {
        Map param = new HashMap();
        param.put("empDuties",dutiesCode);
        //查询出所有前台模块菜单的ID
        param.put("rootId",394);
        String allIds = sysMenuMapper.getClienMenuByRootId(param);
        List<Integer> integers = StringIdsTOList(allIds);
        param.put("list",integers);
        List<SysMenu> sysMenus = sysMenuMapper.selectUserReceptionMenu(param);
        for(SysMenu sysMenu:sysMenus){
            param.put("empDuties",dutiesCode);
            param.put("rootId",sysMenu.getId());
            List btnIds = StringIdsTOList(sysMenuMapper.getBtnsByRootId(param));
            btnIds = integerListRemove(btnIds,sysMenu.getId());
            if(btnIds != null && btnIds.size() > 1){
                param.put("list",btnIds);
                sysMenu.setClientButtons(sysMenuMapper.selectUserReceptionMenu(param));
            }
        }
        return sysMenus;
    }

    @Override
    public SysRoleMenu selectCheckPermission(String dutiesCode, Integer menuId) {
        Map<String,Object> map = new HashMap<>();
        map.put("dutiesCode",dutiesCode);
        map.put("menuId",menuId);
        return sysMenuMapper.selectCheckPermission(map);
    }

    @Override
    public SysMenu selectMenuByMenuCode(String function_code) {
        return sysMenuMapper.selectMenuByMenuCode(function_code);
    }

    @Override
    public void editDutiesMenu(String dutiesCode, Integer menuId, Boolean check) {
        Map param = new HashMap();
        param.put("dutiesCode",dutiesCode);
        param.put("menuId",menuId);

        /* param.put("rootId",menuId);
        List<Integer> menuIds = StringIdsTOList(sysMenuMapper.getMenuByRootId(param));
        param.put("list",menuIds);*/

//        SysRoleMenu sysRoleMenu = sysMenuMapper.selectDutiesMenu(param);
        if(!check){
            sysMenuMapper.delDutiesMenu(param);
        }else if(check){
            sysMenuMapper.insertDutiesMenu(param);
        }
    }

    private List StringIdsTOList(String ids) {
        if(StringUtils.isEmpty(ids))return null;
        List list = new ArrayList();
        Collections.addAll(list,ids.split(","));
        list.remove(0);
        return list;
    }

    private List<Integer> integerListRemove(List list,Integer removeVal){
        List<Integer> resList = new ArrayList();
        for(int i = 0; i < list.size();i++){
            Integer val = Integer.parseInt(list.get(i).toString());
            if(!val.equals(removeVal)){
                resList.add(val);
            }
        }
        return resList;
    }
}
