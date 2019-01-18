//package com.yqsh.diningsys.web.controller.base;
//
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
//import com.yqsh.diningsys.web.model.SysRole;
//import com.yqsh.diningsys.web.service.base.UserService;
//
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.apache.shiro.authz.annotation.RequiresRoles;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.yqsh.diningsys.web.model.ResultInfo;
//import com.yqsh.diningsys.web.model.SysUser;
//import com.yqsh.diningsys.web.security.PermissionSign;
//import com.yqsh.diningsys.web.security.RoleSign;
//import com.yqsh.diningsys.web.service.base.SysRoleService;
//
///**
// * 用户控制器
// *
// * @author zhshuo
// **/
//@Controller
//@RequestMapping(value = "/user")
//public class UserController extends BaseController{
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private SysRoleService sysRoleService;
//
//    @RequestMapping("/index")
//    public ModelAndView index(){
//        ModelAndView modelAndView = new ModelAndView("system/user/user-new-index");
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/getPageList")
//	@ResponseBody
//	public Object getPageList(HttpServletRequest request,HttpServletResponse response,SysUser sysUser) {
//
//    	com.yqsh.diningsys.core.util.Page<SysUser> pagebean = null;
//		try {
//			pagebean = userService.getPageList(sysUser);
//			pagebean.setTotal(pagebean.getContext().getPageCount());
//			pagebean.setPage(sysUser.getPage());
//			pagebean.setRecords(pagebean.getContext().getTotal());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return pagebean;
//	}
//
//    @RequestMapping(value = "/getUserById")
//	@ResponseBody
//	public Object getUserById(HttpServletRequest request,HttpServletResponse response,SysUser sysUser) {
//		Map<String,Object> result = new HashMap<String,Object>();
//		try{
//			SysUser user = userService.getUserByID(sysUser);
//			result.put("user", user);
//			result.put("success", "OK");
//		}catch(Exception e){
//			e.printStackTrace();
//			result.put("success", "error");
//		}
//		return result;
//	}
//
//    @RequestMapping(value = "/saveUser")
//	@ResponseBody
//	public Object saveUser(HttpServletRequest request,
//			HttpServletResponse response,SysUser sysUser) {
//		Map<String,Object> result = new HashMap<String,Object>();
//		try{
//			userService.insertOrUpdUser(sysUser);
//			result.put("success", "OK");
//		}catch(Exception e){
//			result.put("success", "false");
//			result.put("error", e.getMessage());
//		}
//		return result;
//	}
//
//    @RequestMapping(value = "/deleteUser")
//	@ResponseBody
//	public Object deleteUser(HttpServletRequest request,
//			HttpServletResponse response,SysUser sysUser) {
//		Map<String,Object> result = new HashMap<String,Object>();
//		try{
//			List<String> list = new ArrayList<String>();
//			for(String str : sysUser.getEditIds().split(",")){
//				list.add(str);
//			}
//			sysUser.setIds(list);
//			userService.deleteByIds(sysUser);
//			result.put("success","OK");
//		}catch(Exception e){
//			e.printStackTrace();
//			result.put("success", "false");
//		}
//		return result;
//	}
//
//    @RequestMapping(value = "/checkUser")
//	@ResponseBody
//	public Object checkUser(HttpServletRequest request,
//			HttpServletResponse response,SysUser sysUser) {
//		Map<String,Object> result = new HashMap<String,Object>();
//		int stat = 0;
//		List<SysUser> checkList = userService.getAllList(sysUser);
//		if(sysUser.getId() != null && sysUser.getId() > 0){
//			stat = checkList != null && checkList.size() != 0 ?
//			checkList.get(0).getId() != sysUser.getId() ? 2 : 1 : 1;
//		}else{
//			stat = checkList != null && checkList.size() != 0 ? 2 : 1;
//		}
//		result.put("state", stat);
//		return result;
//	}
//
//    @RequestMapping("/addPage")
//    public ModelAndView addPage(){
//        ModelAndView modelAndView = new ModelAndView("system/user/user_add");
//        List<SysRole> sysRoles = sysRoleService.selectRoleByUserId(null);
//        modelAndView.addObject("sysRoles",sysRoles);
//        return modelAndView;
//    }
//
//    @RequestMapping("/showUserRoleEdit/{id}")
//    public ModelAndView showUserRoleEdit(@PathVariable Integer id){
//        // TODO: 2016-07-28  用户删除 需删除sys_user_role表中数据  角色删除 和菜单删除都需要进行删除关联操作
//        ModelAndView modelAndView = new ModelAndView("system/user/user_role_table");
//        List<SysRole> sysRoles = null;
//        try {
//            Map params = new HashMap();
//            params.put("id",id);
//            sysRoles = sysRoleService.selectRoleByUserId(params);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        modelAndView.addObject("sysRoles",sysRoles);
//        modelAndView.addObject("useId",id);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/userRoleEdit",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo userRoleEdit(Integer userId,String roleIds){
//        try {
//            userService.updateUserRole(userId,roleIds);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//
//    @RequestMapping("/getData")
//    @ResponseBody
//    public Page<SysUser> getUserByCon(HttpServletRequest request){
//        String[] orderColumns = {"","id","username","nickname","state","create_time","email"};
////        try {
////            Page<SysUser> page = null;
////            page = this.getPageParams(page,request,orderColumns);
////            userService.selectByConAndPage(page,page.getPageParam());
////            return page;
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return null;
//    }
//
//    @RequestMapping("/showUserInfo/{id}")
//    public ModelAndView showUserInfo(@PathVariable Integer id){
//        ModelAndView modelAndView = new ModelAndView("system/user/user_info");
//        SysUser sysUser = userService.selectByPrimaryKey(id);
//        modelAndView.addObject("sysUser",sysUser);
//        return modelAndView;
//    }
//
//    @RequestMapping("/showUserEdit/{id}")
//    public ModelAndView showUserEdit(@PathVariable Integer id){
//        ModelAndView modelAndView = new ModelAndView("system/user/user_edit");
//        SysUser sysUser = userService.selectByPrimaryKey(id);
//        modelAndView.addObject("sysUser",sysUser);
//
//        return modelAndView;
//    }
//
//
//
//    /**
//     * 用户修改
//     * @param sysUser
//     * @return
//     */
//    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo editUser(SysUser sysUser){
//        try {
//            userService.modifyUserByPrimaryKey(sysUser);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    /**
//     * 用户新增
//     * @param sysUser
//     * @return
//     */
//    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo insertUser(SysUser sysUser){
//        // TODO: 2016-07-26  用户新增时的密码 系统设置
//        try {
//            userService.insertUser(sysUser);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    /**
//     * 删除用户
//     * @param userIDs
//     * @return
//     */
//    @RequestMapping(value = "/delUsers", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo delUsers(String userIDs){
//        try {
//            List ids = new ArrayList();
//            Collections.addAll(ids,userIDs.split(","));
//            userService.delUserByPrimaryKey(ids);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    /**
//     * 冻结用户
//     * @param userIDs
//     * @return
//     */
//    @RequestMapping(value = "/lockUser", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo lockUser(String userIDs,String state){
//        try {
//            List ids = new ArrayList();
//            Collections.addAll(ids,userIDs.split(","));
//            Map params = new HashMap();
//            params.put("ids",ids);
//            params.put("state",state);
//            userService.modifyUserState(params);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    /**
//     * 密码初始化
//     * @param userIDs
//     * @return
//     */
//    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo resetPassword(String userIDs){
//        // TODO: 2016-07-26  用户密码初始化 初始化密码 系统设置
//        try {
//            List ids = new ArrayList();
//            Collections.addAll(ids,userIDs.split(","));
//            userService.modifyUserPassword(ids);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//
//
//
//
//    /**
//     * 基于角色 标识的权限控制案例
//     */
//    @RequestMapping(value = "/admin")
//    @ResponseBody
//    @RequiresRoles(value = RoleSign.ADMIN)
//    public String admin() {
//        return "拥有admin角色,能访问";
//    }
//
//    /**
//     * 基于权限标识的权限控制案例
//     */
//    @RequestMapping(value = "/create")
//    @ResponseBody
//    @RequiresPermissions(value = PermissionSign.USER_CREATE)
//    public String create() {
//        return "拥有user:create权限,能访问";
//    }
//
//
//
//}
