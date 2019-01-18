package com.yqsh.diningsys.web.controller.permissions;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRole;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.base.SysMenuService;
import com.yqsh.diningsys.web.service.base.SysRoleService;
import com.yqsh.diningsys.web.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-28 13:16
 */
@RequestMapping("/permission/sysPermissions")
@Controller
public class SysPermissionController extends BaseController{

    @Autowired
    private TbOrgService tbOrgService;

    @Autowired
    private UserService userService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/index")
    public String index(Model model){
        return "permissions/sys_per/sys_per_index";
    }

    @RequestMapping("/userRoleIndex/{id}")
    public String userRoleIndex(Model model, @PathVariable Integer id){
        SysUser sysUser = userService.selectByPrimaryKey(id);
        Map param = new HashMap<>();
        param.put("id",sysUser.getId());
        List<SysRole> sysRoles = sysRoleService.selectRoleByUserId(param);

        model.addAttribute("sysRoles",sysRoles);
        model.addAttribute("sysUser",sysUser);
        return "permissions/sys_per/sys_user_role";
    }

    /**
     * 权限新增或者修改
     * @param model
     * @param
     * @return
     */
    @RequestMapping("/roleUserIndex")
    public String roleUserIndex(Model model,Integer id){
        List<SysUser> sysUsers;
        if (id != null) {
            SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
            sysUsers = userService.selectUsersByRoleId(id);
            model.addAttribute("sysRole",sysRole);
        }else{
           sysUsers = userService.selectAllUser();
        }
        model.addAttribute("sysUsers",sysUsers);
        return "permissions/sys_per/sys_role_user_edit";
    }

    @RequestMapping("/userRoleEdit")
    @ResponseBody
    public ResultInfo userRoleEdit(SysUser sysUser,String roleIds){
        try {
            userService.editUser(sysUser,roleIds);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/roleUserEdit")
    @ResponseBody
    public ResultInfo roleUserEdit(SysRole sysRole,String userIds){
        try {
            sysRoleService.editRoleUser(sysRole,userIds);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/getSysTree")
    @ResponseBody
    public List<PermissonTree> getSysTree(){
        TbOrg tbOrg = tbOrgService.getOwnOrg();

        List<PermissonTree> permissonTreeList = new ArrayList<>();
        permissonTreeList.add(new PermissonTree(1,0,"权限和用户"));
        permissonTreeList.add(new PermissonTree(2,1,"用户"));
        permissonTreeList.add(new PermissonTree(3,2,tbOrg.getOrgName()));
        permissonTreeList.add(new PermissonTree(4,1,"权限"));
        permissonTreeList.add(new PermissonTree(5,4,tbOrg.getOrgName()));

        return permissonTreeList;
    }

    @RequestMapping("/getSysPerUserData")
    @ResponseBody
    public List<SysUser> getSysPerUserData(){
        return userService.getAllList(null);
    }

    @RequestMapping("/getSysPerRoleData")
    @ResponseBody
    public List<SysRole> getSysPerRoleData(){
        return sysRoleService.selectAllRole();
    }

    @RequestMapping("/deleteUser/{id}")
    @ResponseBody
    public ResultInfo deleteUser(@PathVariable Integer id){
        try {
            userService.deleteUser(id);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/deleteRole/{id}")
    @ResponseBody
    public ResultInfo deleteRole(@PathVariable Integer id){
        try {
            sysRoleService.deleteByPrimaryKey(id);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/rolePermisson/{id}")
    public String rolePermisson(@PathVariable Integer id,Model model){
        SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
        model.addAttribute("sysRole",sysRole);
        return "permissions/sys_per/sys_role_permission";
    }


    @RequestMapping("/getRoleMenus/{id}")
    @ResponseBody
    public List<SysMenu> getRoleMenus(@PathVariable Integer id){
        return sysRoleService.selectRoleMenu(id);
    }

    @RequestMapping("/editRoleMenu")
    @ResponseBody
    public ResultInfo editRoleMenu(Integer roleId,String QXCheckNodesID){
        try {
            sysRoleService.insertRoleMenu(roleId,QXCheckNodesID);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }


    @RequestMapping("/getSysMenuTree")
    @ResponseBody
    public List<SysMenu> getSysMenuTree(){
        return sysMenuService.selectBaseMenu();
    }


    @RequestMapping("/userPwdPage/{id}")
    public String userPwdPage(@PathVariable Integer id,Model model){
        model.addAttribute("id",id);
        return "permissions/sys_per/sys_user_pwd";
    }

    @RequestMapping("/updateUserPwd")
    @ResponseBody
    public ResultInfo updateUserPwd(Integer id,String newPwd){
        try {
            userService.updatePwd(newPwd,id);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    private class PermissonTree{

        private int id;

        private int pId;

        private String name;

        public PermissonTree(int id, int pId, String name) {
            this.id = id;
            this.pId = pId;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getpId() {
            return pId;
        }

        public void setpId(int pId) {
            this.pId = pId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
