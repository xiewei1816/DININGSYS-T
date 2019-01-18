package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.deskBusiness.DgNonMember;
import com.yqsh.diningsys.web.model.deskBusiness.DgNonMemberCredit;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.deskBusiness.DgNonMemberCreditService;
import com.yqsh.diningsys.web.service.deskBusiness.DgNonMemberService;

@Controller
@RequestMapping(value = "/nonMemberCredit")
public class DgNonMemberCreditController extends BaseController {
	@Autowired
    private DgNonMemberCreditService nonMemberCreditService;
	
	@Autowired
    private DgNonMemberService dgNonMemberService;
	
	@Autowired
    private UserService userService;
	
	@RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("deskBusiness/nonMemberCredit/nonMemberCredit-index");
        modelAndView.addObject("emps", userService.getAllList(new SysUser()));
        modelAndView.addObject("members", dgNonMemberService.getAllList(new DgNonMember()));
        return modelAndView;
    }
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgNonMemberCredit nonMemberCredit) {
		
    	com.yqsh.diningsys.core.util.Page<DgNonMemberCredit> pagebean = null;
		try {
			pagebean = nonMemberCreditService.getPageList(nonMemberCredit);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(nonMemberCredit.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	@RequestMapping(value = "/getAllList")
	@ResponseBody
	public Object getAllList(HttpServletRequest request,HttpServletResponse response
							,DgNonMemberCredit nonMemberCredit) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<DgNonMemberCredit> list = nonMemberCreditService.getAllList(nonMemberCredit);
			result.put("list", list);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
	
	@RequestMapping(value = "/getTreeList")
	@ResponseBody
	public Object getTreeList(HttpServletRequest request,HttpServletResponse response
							,DgNonMemberCredit nonMemberCredit) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<DgNonMember> areaList = dgNonMemberService.getAllList(new DgNonMember());
		for(DgNonMember area : areaList){
			Map<String,Object> treedom = new HashMap<String,Object>();
			treedom.put("id", area.getId());
			treedom.put("pid", 0);
			treedom.put("name", area.getName());
			result.add(treedom);
		}
		return result;
	}
	
	@RequestMapping(value = "/getNonMemberCreditByID")
	@ResponseBody
	public Object getnonMemberCreditByID(HttpServletRequest request,HttpServletResponse response,DgNonMemberCredit nonMemberCredit) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			DgNonMemberCredit DgNonMemberCredit = nonMemberCreditService.getDgNonMemberCreditByID(nonMemberCredit);
			result.put("nonMemberCredit", DgNonMemberCredit);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
    
    @RequestMapping(value = "/saveNonMemberCredit")
	@ResponseBody
	public Object savenonMemberCredit(HttpServletRequest request,
			HttpServletResponse response,DgNonMemberCredit nonMemberCredit) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			nonMemberCredit.setCreateUserid(getCurrentUser().getId() + "");
			nonMemberCreditService.insertOrUpd(nonMemberCredit);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}
    
    @RequestMapping(value = "/deleteNonMemberCredit")
	@ResponseBody
	public Object deletenonMemberCredit(HttpServletRequest request,
			HttpServletResponse response,DgNonMemberCredit nonMemberCredit) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<String> list = new ArrayList<String>();
			for(String str : nonMemberCredit.getEditIds().split(",")){
				list.add(str);
			}
			nonMemberCredit.setIds(list);
			nonMemberCreditService.deleteByIds(nonMemberCredit);
			result.put("success","OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "false");
		}
		return result;
	}
    
    @RequestMapping(value = "/checkNonMemberCreditByName")
	@ResponseBody
	public Object checknonMemberCreditByName(HttpServletRequest request,
			HttpServletResponse response,DgNonMemberCredit nonMemberCredit) {
		Map<String,Object> result = new HashMap<String,Object>();
		int stat = 0;
		List<DgNonMemberCredit> checkList = nonMemberCreditService.getAllList(nonMemberCredit);
		if(nonMemberCredit.getId() != null && nonMemberCredit.getId() > 0){
			stat = checkList != null && checkList.size() != 0 ?
			checkList.get(0).getId() != nonMemberCredit.getId() ? 2 : 1 : 1;
		}else{
			stat = checkList != null && checkList.size() != 0 ? 2 : 1;
		}
		result.put("state", stat);
		return result;
	}
}
