//package com.yqsh.diningsys.web.controller.base;
//
//import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
//import com.yqsh.diningsys.web.model.ResultInfo;
//import com.yqsh.diningsys.web.model.SysMenu;
//import com.yqsh.diningsys.web.model.SysRole;
//import com.yqsh.diningsys.web.service.base.SysMenuService;
//import com.yqsh.diningsys.web.service.base.SysRoleService;
//import net.sf.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//
///**
// * 角色模块
// *
// * @author zhshuo create on 2016-07-26 16:59
// */
//@RequestMapping("/role")
//@Controller
//public class RoleController extends BaseController{
//
//    @Autowired
//    private SysRoleService sysRoleService;
//
//    @Autowired
//    private SysMenuService sysMenuService;
//
//    @RequestMapping("/index")
//    public String index(){
//        return "system/role/role_index";
//    }
//
//    @RequestMapping("/sysRoleAddPage")
//    public String sysRoleAddPage(){
//        return "system/role/role_add";
//    }
//
//    @RequestMapping("/getData")
//    @ResponseBody
//    public Page<SysRole> getUserByCon(HttpServletRequest request){
//        String[] orderColumns = {"","role_name","role_sign","description","state","create_time"};
//        try {
//            Page<SysRole> page = null;
////            page = this.getPageParams(page,request,orderColumns);
//            sysRoleService.selectByConAndPage(page,page.getPageParam());
//            return page;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * add  role base info without permission
//     * @return ResultInfo
//     */
//    @RequestMapping(value = "/addRoleBaseInfo",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo addRoleBaseInfo(SysRole sysRole){
//        try {
//            sysRoleService.insertSelective(sysRole);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    @RequestMapping("/showEditRileBaseInfo/{id}")
//    public ModelAndView showEditRoleBaseInfo(@PathVariable Integer id){
//        ModelAndView modelAndView = new ModelAndView("system/role/role_edit");
//        SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
//        modelAndView.addObject("sysRole",sysRole);
//        return modelAndView;
//    }
//
//    @RequestMapping("/editRoleBaseInfo")
//    @ResponseBody
//    public ResultInfo editRoleBaseInfo(SysRole sysRole){
//        try {
//            sysRoleService.updateByPrimaryKeySelective(sysRole);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    @RequestMapping("/delRole")
//    @ResponseBody
//    public ResultInfo delRole(String roleIds){
//        try {
//            sysRoleService.deleteByPrimaryKeys(arrayToList(roleIds.split(",")));
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    @RequestMapping("/lockRole")
//    @ResponseBody
//    public ResultInfo lockRole(String state,Integer id){
//        try {
//            Map params = new HashMap();
//            params.put("id",id);
//            params.put("state",state);
//            sysRoleService.lockByPrimaryKeys(params);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    /**
//     * role permission edit page
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("/showMenuEdit/{id}")
//    public ModelAndView showMenuEdit(@PathVariable Integer id){
//        JSONObject jsonObject = new JSONObject();
//        List<SysMenu> sysMenuList = sysRoleService.selectRoleMenu(id);
//        jsonObject.put("sysMenuList",sysMenuList);
//        ModelAndView modelAndView = new ModelAndView("system/role/role_menu_edit");
//        modelAndView.addObject("sysMenuList",jsonObject.toString());
//        modelAndView.addObject("roleId",id);
//        return modelAndView;
//    }
//
//    /**
//     * edit role permission
//     * delete data by role id
//     * insert new data
//     * @param roleID
//     * @param QXCheckNodesID
//     * @return
//     */
//    @RequestMapping(value = "/editRole", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo editRole(Integer roleID, String QXCheckNodesID){
//        try {
//            String params = "";
//            if(QXCheckNodesID.length()>0){
//                String[] ids = QXCheckNodesID.split(",");
//                for(String id:ids){
//                    params += "("+roleID+","+Integer.parseInt(id)+"),";
//                }
//                if (params.endsWith(","))
//                    params = params.substring(0,params.lastIndexOf(","));
//
//                Map param = new HashMap();
//                param.put("params",params);
//                sysRoleService.insertRoleMenu(roleID,param);
//            }
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//}
