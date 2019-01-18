package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
import com.yqsh.diningsys.web.model.deskBusiness.DgSpeciallyBusiness;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.archive.PublicCodeService;
import com.yqsh.diningsys.web.service.deskBusiness.DgSpeciallyBusinessService;

@Controller
@RequestMapping(value = "/speciallyBusiness")
public class DgSpeciallyBusinessController extends BaseController {
	@Autowired
    private DgSpeciallyBusinessService speciallyBusinessService;
	
	@Autowired
    private PublicCodeService publicCodeService;
	
	@Autowired
	private DgPublicCode0Service dgPublicCode0Service;
	
	@RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("deskBusiness/speciallyBusiness/speciallyBusiness-index");
        return modelAndView;
    }
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgSpeciallyBusiness speciallyBusiness) {
		
    	com.yqsh.diningsys.core.util.Page<DgSpeciallyBusiness> pagebean = null;
		try {
			pagebean = speciallyBusinessService.getPageList(speciallyBusiness);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(speciallyBusiness.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	@RequestMapping(value = "/getAllList")
	@ResponseBody
	public Object getAllList(HttpServletRequest request,HttpServletResponse response
							,DgSpeciallyBusiness speciallyBusiness) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<DgSpeciallyBusiness> list = speciallyBusinessService.getAllList(speciallyBusiness);
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
							,DgSpeciallyBusiness speciallyBusiness) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		DgPublicCode tycode = new DgPublicCode();
        tycode.setCparent("33");
		List<DgPublicCode> areaList = publicCodeService.findListData(tycode);
		for(DgPublicCode area : areaList){
			Map<String,Object> treedom = new HashMap<String,Object>();
			treedom.put("id", area.getCgpcid());
			treedom.put("pid", area.getCparent());
			treedom.put("name", area.getCname());
			result.add(treedom);
		}
		return result;
	}
	
	@RequestMapping(value = "/getSpeciallyBusinessByID")
	@ResponseBody
	public Object getspeciallyBusinessByID(HttpServletRequest request,HttpServletResponse response,DgSpeciallyBusiness speciallyBusiness) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			DgSpeciallyBusiness DgSpeciallyBusiness = speciallyBusinessService.getDgSpeciallyBusinessByID(speciallyBusiness);
			result.put("speciallyBusiness", DgSpeciallyBusiness);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
    
    @RequestMapping(value = "/saveSpeciallyBusiness")
	@ResponseBody
	public Object savespeciallyBusiness(HttpServletRequest request,
			HttpServletResponse response,DgSpeciallyBusiness speciallyBusiness) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			speciallyBusiness.setCreateUserid(getCurrentUser().getId() + "");
			speciallyBusinessService.insertOrUpd(speciallyBusiness);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}
    
    @RequestMapping(value = "/deleteSpeciallyBusiness")
	@ResponseBody
	public Object deletespeciallyBusiness(HttpServletRequest request,
			HttpServletResponse response,DgSpeciallyBusiness speciallyBusiness) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<String> list = new ArrayList<String>();
			for(String str : speciallyBusiness.getEditIds().split(",")){
				list.add(str);
			}
			speciallyBusiness.setIds(list);
			speciallyBusinessService.deleteByIds(speciallyBusiness);
			result.put("success","OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "false");
		}
		return result;
	}
    
    @RequestMapping(value = "/checkSpeciallyBusinessByName")
	@ResponseBody
	public Object checkspeciallyBusinessByName(HttpServletRequest request,
			HttpServletResponse response,DgSpeciallyBusiness speciallyBusiness) {
		Map<String,Object> result = new HashMap<String,Object>();
		int stat = 0;
		List<DgSpeciallyBusiness> checkList = speciallyBusinessService.getAllList(speciallyBusiness);
		if(speciallyBusiness.getId() != null && speciallyBusiness.getId() > 0){
			stat = checkList != null && checkList.size() != 0 ?
			checkList.get(0).getId() != speciallyBusiness.getId() ? 2 : 1 : 1;
		}else{
			stat = checkList != null && checkList.size() != 0 ? 2 : 1;
		}
		result.put("state", stat);
		return result;
	}
    
    
    /**
     * 
     * 左侧树菜单
     * @return
     */
    @RequestMapping("/getTreeNodes")
    @ResponseBody
    public  List<DgProductPhaseLeftmenu> getTreeNodes()
    {
    	String id = getRequest().getParameter("id");
    	String childCount = getRequest().getParameter("childCount");
    	List<DgProductPhaseLeftmenu> menu = new ArrayList<DgProductPhaseLeftmenu>();
    	
    	//获取根节点
    	if(StringUtils.isEmpty(id))
    	{
    		DgProductPhaseLeftmenu m = new DgProductPhaseLeftmenu();
    		m.setId(32);
    		m.setName("特约商户");
    		m.setChildCount(1); //大于0即可
    		m.setParentId(0);
    		menu.add(m);
    	}
    	else if(id.equals("32"))
    	{
    			Map<String,Object> map = new HashMap<String,Object>();
    			map.put("cParent",32);
    			map.put("iDelFlg",0);
    			List<DgPublicCode0> areas = dgPublicCode0Service.selectSmallDpc(map);
    			
    			for(DgPublicCode0 a:areas)
    			{
    				DgProductPhaseLeftmenu m = new DgProductPhaseLeftmenu();
    	    		m.setId(a.getId());
    	    		m.setName(a.getcName());
    	    		m.setChildCount(1); //大于0即可
    	    		m.setParentId(-1);
    	    		menu.add(m);
    			}
    	}
    	return menu;
    }
    /**
     * 
     * 增加/删除后刷新select选项框
     * @return
     */
    @RequestMapping("/getTYPbc")
    @ResponseBody
    public  List<DgPublicCode0> getTYPbc()
    {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cParent",32);
		map.put("iDelFlg",0);
		List<DgPublicCode0> pbc = dgPublicCode0Service.selectSmallDpc(map);
    	return pbc;
    }
    
}
