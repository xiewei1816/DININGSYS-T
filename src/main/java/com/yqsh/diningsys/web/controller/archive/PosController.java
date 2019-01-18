package com.yqsh.diningsys.web.controller.archive;

import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
import com.yqsh.diningsys.web.model.archive.TbDep;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;

@Controller
@RequestMapping(value = "/pos")
public class PosController extends BaseController {
	
	@Autowired
    private DgPosService posService;
	
	@Autowired
    private DgConsumptionAreaService consumptionAreaService;
        
        @Autowired
        private AutoSeqService seqService;
        @Autowired
        private TbOrgService tbOrgService;
        
	@RequestMapping("/index")
    public ModelAndView index(Model model,TbOrg tbOrg){
    	//查询机构组织数据
    	model.addAttribute("listOrg",tbOrgService.getAllList(tbOrg));
        ModelAndView modelAndView = new ModelAndView("archive/pos/pos-index");
        modelAndView.addObject("areas", consumptionAreaService.getAllList(new DgConsumptionArea()));
        //获取自动编号
        String currentNum = seqService.getSeq("pos", 3, 0, "", 0, "");
        modelAndView.addObject("number",currentNum);
        return modelAndView;
    }

    /**
     * 跳转至回收站
     * @param model
     * @return
     */
    @RequestMapping("/trash")
    public ModelAndView trash(Model model){
        ModelAndView modelAndView = new ModelAndView("archive/pos/pos-trash");
        return modelAndView;
    }
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgPos pos) {
    	com.yqsh.diningsys.core.util.Page<DgPos> pagebean = null;
		try {
			pagebean = posService.getPageList(pos);
			for(DgPos dgPos : pagebean.getRows()){
				DgConsumptionArea are = new DgConsumptionArea();
				are.setEditIds(dgPos.getConsumerAreas());
				List<DgConsumptionArea> areas = consumptionAreaService.getAllList(are);
				String name = "";
				for(DgConsumptionArea aname : areas){
					name += name.equals("") ? aname.getName() : "," + aname.getName();
				}
				dgPos.setConsumerAreas(name);
			}
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(pos.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	@RequestMapping(value = "/getAllList")
	@ResponseBody
	public Object getAllList(HttpServletRequest request,HttpServletResponse response
							,DgPos pos) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<DgPos> list = posService.getAllList(pos);
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
							,DgPos pos) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<DgConsumptionArea> areaList = consumptionAreaService.getAllList(new DgConsumptionArea());
		for(DgConsumptionArea area : areaList){
			Map<String,Object> treedom = new HashMap<String,Object>();
			treedom.put("id", area.getId());
			treedom.put("pid", 0);
			treedom.put("name", area.getName());
			result.add(treedom);
		}
		return result;
	}
	
	@RequestMapping(value = "/getPosByID")
	@ResponseBody
	public Object getPosByID(HttpServletRequest request,HttpServletResponse response,DgPos pos) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			DgPos dgPos = posService.getPosByID(pos);
			//查询组织机构信息
			String orgId = dgPos.getOrganization();
			TbOrg set_org = new TbOrg();
			set_org.setId(Integer.parseInt(orgId));
			TbOrg org = tbOrgService.getOrgByID(set_org);
			
			result.put("org", org);
			result.put("pos", dgPos);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
    
    @RequestMapping(value = "/savePos")
	@ResponseBody
	public Object savePos(HttpServletRequest request,
			HttpServletResponse response,DgPos pos) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			if(pos.getId() != null)
			{
	   			List<String> list = new ArrayList<String>();
	   			list.add(""+pos.getId());
	   			pos.setIds(list);
	   			if(posService.checkHavaYxePos(pos) > 0)
	   			{
	   	   			result.put("success", false);
	   	   			result.put("error", "易小二pos不能修改!");
	   	   			return result;
	   			}
			}
			
			
            String zjf = pos.getMnemonic();
            if(StringUtil.isBlank(zjf)){
                pos.setMnemonic(YQSHTranslate.getPYIndexStr(pos.getName(), true));
            }
			pos.setCreateUserid(getCurrentUser().getId() + "");
			posService.insertOrUpd(pos);
                        seqService.setUsedSeq("pos", pos.getNumber());
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}
    
    @RequestMapping(value = "/deletePosTrash")
   	@ResponseBody
	public Object deletePosTrash(HttpServletRequest request,HttpServletResponse response,DgPos pos) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : pos.getEditIds().split(",")){
   				list.add(str);
   			}
   			pos.setIds(list);
   			if(posService.checkHavaYxePos(pos) > 0)
   			{
   	   			result.put("success", false);
   	   			result.put("error", "易小二pos不能修改!");
   	   			return result;
   			}
			posService.deleteTrash(pos);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    @RequestMapping(value = "/replyPos")
   	@ResponseBody
	public Object replyPos(HttpServletRequest request,HttpServletResponse response,DgPos pos) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : pos.getEditIds().split(",")){
   				list.add(str);
   			}
			pos.setIds(list);
			posService.replyPos(pos);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    @RequestMapping(value = "/deletePos")
	@ResponseBody
	public Object deletePos(HttpServletRequest request,
			HttpServletResponse response,DgPos pos) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<String> list = new ArrayList<String>();
			for(String str : pos.getEditIds().split(",")){
				list.add(str);
			}
			pos.setIds(list);
			posService.deleteByIds(pos);
			result.put("success","OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "false");
		}
		return result;
	}
    
    @RequestMapping(value = "/checkPosByName")
	@ResponseBody
	public Object checkPosByName(HttpServletRequest request,
			HttpServletResponse response,DgPos pos) {
		Map<String,Object> result = new HashMap<String,Object>();
		int stat = 0;
		List<DgPos> checkList = posService.getAllList(pos);
		if(pos.getId() != null && pos.getId() > 0){
			stat = checkList != null && checkList.size() != 0 ?
			checkList.get(0).getId() != pos.getId() ? 2 : 1 : 1;
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
    		m.setId(-1);
    		m.setName("消费区域");
    		m.setChildCount(1); //大于0即可
    		m.setParentId(0);
    		menu.add(m);
    	}
    	else if(id.equals("-1"))
    	{
    			Map<String,Object> map = new HashMap<String,Object>();
    			map.put("cParent",32);
    			map.put("iDelFlg",0);
    			List<DgConsumptionArea> areaList = consumptionAreaService.getAllList(new DgConsumptionArea());
    			
    			for(DgConsumptionArea a:areaList)
    			{
    				DgProductPhaseLeftmenu m = new DgProductPhaseLeftmenu();
    	    		m.setId(a.getId());
    	    		m.setName(a.getName());
    	    		m.setChildCount(1); //大于0即可
    	    		m.setParentId(-1);
    	    		menu.add(m);
    			}
    	}
    	return menu;
    }
}
