package com.yqsh.diningsys.web.controller.archive;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.archive.TbDep;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.archive.TbDepService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;

/**
 * 部门管理控制器
* @author xiewei
* @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/archive/dep")
public class TbDepController extends BaseController{

    @Autowired
    private TbDepService tbDepService;
    @Autowired
    private TbOrgService tbOrgService;
    @Autowired
    private DgPublicCode0Service dgPublicCode0Service;

    private Set<String> childList = new HashSet<String>();
    private Set<String> parentList = new HashSet<String>();
    
    @RequestMapping("/index")
    public ModelAndView index(Model model,TbOrg tbOrg,TbDep tbDep){
    	//查询机构组织数据
    	model.addAttribute("listOrg",tbOrgService.getAllList(tbOrg));
    	//查询部门数据
    	tbDep.setIsDel("0");
    	model.addAttribute("listDep",tbDepService.getAllList(tbDep));
        ModelAndView modelAndView = new ModelAndView("archive/dep/dep-index");
        return modelAndView;
    }
    
    /**
     * 跳转至回收站
     * @param model
     * @return
     */
    @RequestMapping("/trash")
    public ModelAndView trash(Model model){
        ModelAndView modelAndView = new ModelAndView("archive/dep/dep-trash");
        return modelAndView;
    }
    
	@RequestMapping("/toDepFormEdit")
	public String toGiftFormEdit(Model model, Integer id, String depDepartment,
			String depDepartmentName) throws UnsupportedEncodingException {
		//查询字典数据
    	model.addAttribute("list",dgPublicCode0Service.getAllDpcToPage()); 
		if (id != null) { // 修改
			//查询修改对象
			TbDep set_dep = new TbDep();
			set_dep.setId(id);
			TbDep dep = tbDepService.getDepById(set_dep);
			model.addAttribute("dep", dep);
			//查询修改对象对应“父级代码和父级名称”
			depDepartment = dep.getDepDepartment();
			set_dep.setId(Integer.parseInt(depDepartment));
			TbDep get_dep = tbDepService.getDepDepartmentNameById(set_dep);
			if(get_dep != null)
				depDepartmentName = get_dep.getDepCode() + "-" + get_dep.getDepName();
			else
				depDepartmentName = "顶级部门";
			
			//组织机构初始数据
			if(null!=dep.getDepOrganization()){
				TbOrg set_org = new TbOrg();
				set_org.setId(Integer.parseInt(dep.getDepOrganization()));
				TbOrg get_org = tbOrgService.getOrgByID(set_org);
				if(null!=get_org){
					String depOrganizationName = get_org.getOrgName();
					model.addAttribute("depOrganizationName", depOrganizationName);
				}else{
					//如果为空查询所有组织机构
					model.addAttribute("listOrg",tbOrgService.getAllList(new TbOrg()));	
				}
				
			}
			model.addAttribute("depDepartmentName", depDepartmentName);
			
		} else { // 新增
			String depCode = null;
			String depOrganization = null;
			String depOrganizationName = null;
			
			TbDep set_dep = new TbDep();
			set_dep.setDepDepartment(depDepartment);
			TbDep get_dep = tbDepService.getLastDepCode(set_dep);
			if (get_dep != null) {
				String lastCode = get_dep.getDepCode();
				depCode = getCodeByLastCode(lastCode);
			} else {
				depCode = "01"; // 初始化代码
			}
			//组织机构初始数据			
			TbDep setdep = new TbDep();
			setdep.setId(Integer.parseInt(depDepartment));
			TbDep getdep = tbDepService.getDepById(setdep);
			if(null!=getdep){
				depOrganization = getdep.getDepOrganization();
			}
			TbOrg get_org=null;
			if(null!=depOrganization){
				TbOrg set_org = new TbOrg();
				set_org.setId(Integer.parseInt(depOrganization));
				get_org = tbOrgService.getOrgByID(set_org);
			}
			
			
			
			if(get_org != null){
				depOrganizationName = get_org.getOrgName();
			}else{
				//如果为空查询所有组织机构
				model.addAttribute("listOrg",tbOrgService.getAllList(new TbOrg()));				
			}
			//父级部门名称转码
//			depDepartmentName = new String(depDepartmentName.getBytes("ISO-8859-1"),
//					"UTF-8");
			
			TbDep dep = new TbDep();
			dep.setDepCode(depCode);
			dep.setDepDepartment(depDepartment);
			dep.setDepOrganization(depOrganization);
			model.addAttribute("dep", dep);
			model.addAttribute("depDepartmentName", depDepartmentName);
			model.addAttribute("depOrganizationName", depOrganizationName);
		} 
		return "archive/dep/dep-edit";
	}
    
	/**
	 * 通过上级代码查询最后一条记录获取代码进行叠加算法
	 * @param tbDep
	 * @return
	 */
    @RequestMapping(value = "/getLastDepCode")
	public String getLastDepCode(TbDep tbDep) {
		String depCode = null;
		try {
			TbDep get_dep = tbDepService.getLastDepCode(tbDep);
			// 如果该节点下有子节点，则查询最大代码进行算法叠加
			if (get_dep != null) {
				String lastCode = get_dep.getDepCode();
				depCode = getCodeByLastCode(lastCode);
			} else {
				depCode = "01"; // 初始化代码
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depCode;
	}
    
	/**
	 * 编号叠加算法
	 * @param lastCode
	 * @return
	 */
	public String getCodeByLastCode(String lastCode) {
		int lastCodeNo = Integer.parseInt(lastCode);
		int codeNo = lastCodeNo + 1;
		String code = codeNo + ""; // 编号
		String no = ""; // 填充“0”
		for (int i = 0; i < 2 - code.length(); i++) {
			no += "0";
		}
		return no + code;
	}
	
    /**
     * 分页查询部门信息
     * @param request
     * @param response
     * @param tbDep
     * @return
     */
    @RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,TbDep tbDep) {
    	com.yqsh.diningsys.core.util.Page<TbDep> pagebean = null;
		try {
			pagebean = tbDepService.getPageList(tbDep);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(tbDep.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
    
    /**
     * 通过部门ID查询部门信息
     * @param request
     * @param response
     * @param tbdep
     * @return
     */
    @RequestMapping(value = "/getDepById")
   	@ResponseBody
   	public Object getDepById(HttpServletRequest request,HttpServletResponse response,TbDep tbDep) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			tbDep.setIsDel("0");//查询未被删除的部门信息
   			TbDep dep = tbDepService.getDepById(tbDep);
   			result.put("dep", dep);
   			result.put("success", "OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "error");
   		}
   		return result;
   	}
    
    /**
     * 新增、修改部门信息保存
     * @param request
     * @param response
     * @param tbDep
     * @return
     */
    @RequestMapping(value = "/saveDep")
	@ResponseBody
	public Object saveDep(HttpServletRequest request,HttpServletResponse response,TbDep tbDep) {
    	@SuppressWarnings("unused")
		String type = tbDep.getUseType();
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			tbDepService.insertOrUpdDep(tbDep);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}

    /**
     * 部门信息回收站
     * @param request
     * @param response
     * @param tbDep
     * @return
     */
    @RequestMapping(value = "/deleteDepTrash")
   	@ResponseBody
	public Object deleteDepTrash(HttpServletRequest request,HttpServletResponse response,TbDep tbDep) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String id : tbDep.getEditIds().split(",")){
   			//如果该回收对象有相对的子节点，则回收该对象时回收该对象子节点
				List<String> ls = getAllSmallDpc(id);
				if(ls != null && ls.size() > 0){
					for (int i = 0; i < ls.size(); i++) {
						list.add(ls.get(i));
					}
				}
				list.add(id);
   			}
   			tbDep.setIds(list);
   			tbDepService.deleteTrash(tbDep);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}finally{
   			childList.clear();
   		}
   		return result;
   	}
    
    /**
     * 还原回收站部门信息
     * @param request
     * @param response
     * @param tbDep
     * @return
     */
    @RequestMapping(value = "/replyDep")
   	@ResponseBody
	public Object replyDep(HttpServletRequest request,HttpServletResponse response,TbDep tbDep) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String id : tbDep.getEditIds().split(",")){
   				//如果该还原对象有相对的子节点，则还原该对象时还原该对象子节点
				List<String> cls = getAllSmallDpc(id);
				if(cls != null && cls.size() > 0){
					for (int i = 0; i < cls.size(); i++) {
						list.add(cls.get(i));
					}
				}
				//如果该还原对象有删除状态的父节点，则还原该对象时还原该对象父节点
				List<String> rls = getAllParentDpc(id);
				if(rls != null && rls.size() > 0){
					for (int i = 0; i < rls.size(); i++) {
						list.add(rls.get(i));
					}
				}
   				list.add(id);
   			}
   			tbDep.setIds(list);
   			tbDepService.replyDep(tbDep);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}finally{
   			childList.clear();
   			parentList.clear();
   		}
   		return result;
   	}
    
    /**
     * 删除部门信息
     * @param request
     * @param response
     * @param tbDep
     * @return
     */
    @RequestMapping(value = "/deleteDep")
   	@ResponseBody
   	public Object deleteDep(HttpServletRequest request,
   			HttpServletResponse response,TbDep tbDep) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String id : tbDep.getEditIds().split(",")){
   				//如果该删除对象有相对的子节点，则删除该对象时删除该对象子节点
				List<String> ls = getAllSmallDpc(id);
				if(ls != null && ls.size() > 0){
					for (int i = 0; i < ls.size(); i++) {
						list.add(ls.get(i));
					}
				}
   				list.add(id);
   			}
   			tbDep.setIds(list);
   			tbDepService.deleteByIds(tbDep);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}finally{
   			childList.clear();
   		}
   		return result;
   	}
    
	/**
	 * 根据父节点id查询是否有子节点，如果有返回所有子节点list
	 * @param pId
	 * @return
	 */
	public List<String> getAllSmallDpc(String pId){
		List<String> listIds = new ArrayList<String>();
		TbDep set_dep = new TbDep();
		set_dep.setDepDepartment(pId);
		List<TbDep> get_dep = tbDepService.getAllList(set_dep);
		if(get_dep != null){
			for (int i = 0; i < get_dep.size(); i++) {
				String id = get_dep.get(i).getId()+"";
				childList.add(id);
				getAllSmallDpc(id);
			}
		}
		Iterator<String> it = childList.iterator();  
		while (it.hasNext()) {  
		  String sid = it.next();  
		  listIds.add(sid);
		} 
		return listIds;	
	}
	
	/**
	 * 根据子节点id查询是否有父节点，如果有返回父节点id
	 * @param id
	 * @return
	 */
	public List<String> getAllParentDpc(String id){
		List<String> listIds = new ArrayList<String>();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("iDelFlg", "1");
		List<TbDep> get_dep = tbDepService.selectAllDep(params);
		if(get_dep != null && get_dep.size() > 0){
			String pId = get_dep.get(0).getDepDepartment()+"";
			if(!("1").equals(pId)){
				parentList.add(pId);
				getAllParentDpc(pId);
			}
		}
		Iterator<String> it = parentList.iterator();  
		while (it.hasNext()) {  
		  String sid = it.next();  
		  listIds.add(sid);
		} 
		return listIds;	
	}
    
    /**
     * 导出部门信息Excel
     * @param request
     * @param response
     * @param model
     * @param tbDep
     * @return
     */
    @RequestMapping(value="exportXls")
	public ModelAndView exportXls(Model model,TbDep tbDep){
		List<TbDep> depList = tbDepService.getAllList(tbDep);
		for (int i = 0; i < depList.size(); i++) {
			TbDep set_dep = depList.get(i);
			String depDepartment = set_dep.getDepDepartment();
			if(depDepartment == null){
				String defaultDepDepartment = "顶级部门";
				set_dep.setDepDepartment(defaultDepDepartment);
			}
		}
		model.addAttribute("depList", depList);
		ModelAndView modelAndView = new ModelAndView("archive/dep/exportXls");
		return modelAndView;
	}
    
    /**
     * 获取所有部门信息
     * @param flag
     * @return
     */
    @RequestMapping("/selectAllDep")
    @ResponseBody
    public List<TbDep> selectAllDep(String id,String isDel,String depDepartment){
    	Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("isDel", isDel);
		params.put("depDepartment", depDepartment);
        List<TbDep> tbDeps = tbDepService.selectAllDep(params);

		//出初始化树结构根目录
        TbDep tbDep = new TbDep();
        tbDep.setId(0);
        tbDep.setDepName("顶级部门");
        tbDeps.add(0, tbDep);
        
        for (int i = 0; i < tbDeps.size(); i++) {
        	TbDep dpc = tbDeps.get(i);
			if(dpc.getDepCode() != null){
				dpc.setDepName(dpc.getDepCode() + "-" + dpc.getDepName());
			}
		}
        return tbDeps;
    }
    
    /**
     * 部门管理-添加选择组织机构判断是否已经选择、判断名称是否已经存在、判断速记码是都已经存在
     * @param dep
     * @return
     */
    @RequestMapping("/checkParamIsHaved")
    @ResponseBody
    public Object checkParamIsHaved(TbDep dep){
    	Map<String,Object> result = new HashMap<String, Object>();
    	Integer depOrganizationCount = 0;
    	Integer depNameCount = 0;
    	Integer depSjmCount = 0;
    	
    	//判断组织机构是否已经选择
    	if(dep.getDepOrganization() != null){
    		TbDep depOrganization = new TbDep();
    		depOrganization.setDepOrganization(dep.getDepOrganization());
    		List<TbDep> depOrganizations = tbDepService.getAllList(depOrganization);
    		depOrganizationCount = depOrganizations.size();
    	}
    	//判断名称是否已经存在
    	if(dep.getDepName() != null){
    		TbDep depName = new TbDep();
    		depName.setDepName(dep.getDepName());
    		List<TbDep> depNames = tbDepService.getAllList(depName);
    		if(depNames.size() == 0){
    			depNameCount = 0;
    		}
    		if(depNames.size() == 1 && depNames.get(0).getId().equals(dep.getId())){
    			depNameCount = 0;
    		}else{
    			depNameCount = depNames.size();
    		}
    		if(depNames.size() > 1){
    			depNameCount = depNames.size();
    		}
    	}
    	
    	//判断速记码是都已经存在
    	if(dep.getDepSjm() != null){
    		TbDep depSjm = new TbDep();
    		depSjm.setDepSjm(dep.getDepSjm());
    		List<TbDep> depSjms = tbDepService.getAllList(depSjm);
    		if(depSjms.size() == 0){
    			depSjmCount = 0;
    		}
    		if(depSjms.size() == 1 && depSjms.get(0).getId().equals(dep.getId())){
    			depSjmCount = 0;
    		}else{
    			depSjmCount = depSjms.size();
    		}
    		if(depSjms.size() > 1){
    			depSjmCount = depSjms.size();
    		}
    	}
        result.put("depOrganizationCount", depOrganizationCount);
        result.put("depNameCount", depNameCount);
        result.put("depSjmCount", depSjmCount);
        
        return result;
    }
    
    /**
     * 根据部门depId获取部门信息
     * @param depId
     * @return
     */
    @RequestMapping("/selectSmallDep")
    @ResponseBody
    public List<TbDep> selectSmallDep(Integer id,String isDel,Integer depId){
    	Map<String,Object> params = new HashMap<String, Object>();
    	params.put("id", id);
    	params.put("isDel", isDel);
    	params.put("depId", depId);
    	
    	//根据depId值查询父级
    	TbDep set_dep = new TbDep();
    	set_dep.setId(depId);
    	TbDep get_dep = tbDepService.getDepById(set_dep);
    	List<TbDep> deps = tbDepService.selectSmallDep(params);
    	for (int i = 0; i < deps.size(); i++) {
    		TbDep dep = deps.get(i);
    		dep.setDepDepartment(get_dep.getDepCode() + "-" + dep.getDepDepartment());
		}
        return deps;
    }

}
