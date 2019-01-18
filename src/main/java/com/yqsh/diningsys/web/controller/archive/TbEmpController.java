package com.yqsh.diningsys.web.controller.archive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.service.base.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.archive.TbDep;
import com.yqsh.diningsys.web.model.archive.TbEmp;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.archive.TbDepService;
import com.yqsh.diningsys.web.service.archive.TbEmpService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;

/**
 * 员工管理控制器
* @author xiewei
* @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/archive/emp")
public class TbEmpController extends BaseController{

	// TODO: 2016-10-28  员工档案表使用sys_user，结构改动，页面未改动

    @Autowired
    private UserService userService;
    @Autowired
    private TbOrgService tbOrgService;
    @Autowired
    private TbDepService tbDepService;
    @Autowired
    private DgPublicCode0Service dgPublicCode0Service;
    
    @RequestMapping("/index")
    public ModelAndView index(Model model,TbOrg tbOrg,TbDep tbDep){
    	//查询机构组织数据
    	model.addAttribute("listOrg",tbOrgService.getAllList(tbOrg)); 
    	//查询字典数据
    	model.addAttribute("list",dgPublicCode0Service.getAllDpcToPage());

    	model.addAttribute("postData",userService.getAllPostData());

        ModelAndView modelAndView = new ModelAndView("archive/emp/emp-index");
        return modelAndView;
    }
    
    /**
     * 跳转至回收站
     * @param model
     * @return
     */
    @RequestMapping("/trash")
    public ModelAndView trash(Model model){
        ModelAndView modelAndView = new ModelAndView("archive/emp/emp-trash");
        return modelAndView;
    }
    
    /**
     * 分页查询员工信息
     * @param request
     * @param response
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,SysUser sysUser) {
    	com.yqsh.diningsys.core.util.Page<SysUser> pagebean = null;
		try {
			pagebean = userService.getPageList(sysUser);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(sysUser.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
    
    /**
     * 通过员工ID查询员工信息
     * @param request
     * @param response
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/getEmpById")
   	@ResponseBody
   	public Object getEmpById(HttpServletRequest request,HttpServletResponse response,SysUser sysUser) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
			sysUser.setIsDel("0");//查询未被删除的员工信息
			SysUser emp = userService.getEmpById(sysUser);
			//查询部门信息
			String depId = emp.getEmpDepartment();
			TbDep set_dep = new TbDep();
			set_dep.setId(Integer.parseInt(depId));
			TbDep dep = tbDepService.getDepById(set_dep);
			//添加到返回结果
   			result.put("emp", emp);
   			result.put("dep", dep);
   			result.put("success", "OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "error");
   		}
   		return result;
   	}
    
    /**
     * 新增、修改员工信息保存
     * @param request
     * @param response
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/saveEmp")
	@ResponseBody
	public Object saveEmp(HttpServletRequest request,HttpServletResponse response,SysUser sysUser) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			
			SysUser uCode = userService.chekUserCode(sysUser.getEmpCode());
			
			if(sysUser.getId() != null)
			{
				//如果系统中存在修改的code，判断是否为当前数据
				if(uCode != null && sysUser.getEmpCode().equals(uCode.getEmpCode()) && sysUser.getId() == uCode.getId()){
					SysUser u = userService.getEmpById(sysUser);
					if("admin".equals(u.getEmpCode())){
						result.put("success", false);
						result.put("error", "系统管理员admin不能修改!");
						return result;
					}
					
					if("yxe_water".equals(u.getEmpCode())){
						result.put("success", false);
						result.put("error", "易小二服务员不能修改!");
						return result;
					}
				}else{
					String msg = "此code："+uCode.getEmpCode()+"已存在!";
					result.put("success", false);
					result.put("error", msg);
					return result;
				}
			}else{
				if(uCode != null){
					String msg = "此code："+uCode.getEmpCode()+"已存在!";
					result.put("success", false);
					result.put("error", msg);
					return result;
				}
			}
			
			userService.insertOrUpdEmp(sysUser);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}

	/**
	 * 查询最后一条记录获取代码进行叠加算法
	 * @return
	 */
    @RequestMapping(value = "/getLastEmpCode")
	@ResponseBody
	public Object getLastEmpCode() {
    	Map<String,Object> result = new HashMap<String,Object>();
		String empCode = null;
		try {
			SysUser user = userService.getLastEmpCode();
			// 如果该节点下有子节点，则查询最大代码进行算法叠加
			if (user != null) {
				String lastCode = user.getEmpCode();
				empCode = getCodeByLastCode(lastCode);
			} else {
				empCode = "001"; // 初始化代码
			}
			user.setEmpCode(empCode);
			result.put("user", user);
			result.put("success", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}

	/**
	 * 编号叠加算法
	 * 
	 * @param lastCode
	 * @return
	 */
	public String getCodeByLastCode(String lastCode) {
		int lastCodeNo = Integer.parseInt(lastCode);
		int codeNo = lastCodeNo + 1;
		String code = codeNo + ""; // 编号
		String no = ""; // 填充“0”
		for (int i = 0; i < 3 - code.length(); i++) {
			no += "0";
		}
		return no + code;
	}
    
    /**
     * 员工信息回收站
     * @param request
     * @param response
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/deleteEmpTrash")
   	@ResponseBody
	public Object deleteEmpTrash(HttpServletRequest request,HttpServletResponse response,SysUser sysUser) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : sysUser.getEditIds().split(",")){
   				
   				SysUser setUser = new SysUser();
   				setUser.setId(Integer.parseInt(str));
   				SysUser u = userService.getEmpById(setUser);
   				if("yxe_water".equals(u.getEmpCode())){
   					result.put("success", false);
   					result.put("error", "[yxe_water易小二]不能删除!");
   					return result;
   				}
   				if("admin".equals(u.getEmpCode()))
   				{
   					result.put("success", false);
   					result.put("error", "[admin管理员不能删除!");
   					return result;
   				}
   				
   				list.add(str);
   			}
			sysUser.setIds(list);
   			userService.deleteTrash(sysUser);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 还原回收站员工信息
     * @param request
     * @param response
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/replyEmp")
   	@ResponseBody
	public Object replyEmp(HttpServletRequest request,HttpServletResponse response,SysUser sysUser) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : sysUser.getEditIds().split(",")){
   				list.add(str);
   			}
			sysUser.setIds(list);
   			userService.replyEmp(sysUser);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 删除员工信息
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/deleteEmp")
   	@ResponseBody
   	public Object deleteEmp(SysUser sysUser) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : sysUser.getEditIds().split(",")){
   				list.add(str);
   			}
			sysUser.setIds(list);
   			userService.deleteByIds(sysUser);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 导出员工信息Excel
     * @param request
     * @param response
     * @param model
     * @param sysUser
     * @return
     */
    @RequestMapping(value="exportXls")
	public ModelAndView exportXls(HttpServletRequest request,HttpServletResponse response,Model model,SysUser sysUser){
		List<SysUser> empList = userService.getAllList(sysUser);
		model.addAttribute("empList", empList);
		ModelAndView modelAndView = new ModelAndView("archive/emp/exportXls");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("/resetPwd")
	public ResultInfo resetPwd(Integer id){
		try {
			List ids = new ArrayList();
			ids.add(id);
			userService.modifyUserPassword(ids);
			return returnSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnFail();
	}

	@RequestMapping("/getAllPostData")
	@ResponseBody
	public List<DgPublicCode0> getAllPostData(){
		return userService.getAllPostData();
	}

}
