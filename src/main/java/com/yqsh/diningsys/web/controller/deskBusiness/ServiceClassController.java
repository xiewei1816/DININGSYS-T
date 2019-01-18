package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yqsh.diningsys.web.model.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.deskBusiness.ServiceClass;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.deskBusiness.ServiceClassService;

@Controller
@RequestMapping(value = "/serviceClass")
public class ServiceClassController extends BaseController {
	
	@Autowired
    private ServiceClassService serviceClassService;
	
	@Autowired
    private TbBisService tbBisService;
	
	@Autowired
    private DgConsumerSeatService consumerSeatService;
	
	@Autowired
    private UserService userService;
	
	@RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("deskBusiness/serviceClass/serviceClass-index");
        modelAndView.addObject("bises", tbBisService.getAllList(null));
        modelAndView.addObject("seats", consumerSeatService.getAllList(null));
        modelAndView.addObject("emps", userService.getAllList(null));
        return modelAndView;
    }

    @RequestMapping("/serviceClassEdit")
    public String  serviceClassEdit(Model model){
		model.addAttribute("bises", tbBisService.getAllList(null));
		model.addAttribute("emps", userService.getAllList(null));
		return "deskBusiness/serviceClass/serviceClass-edit";
	}

	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,ServiceClass serviceClass) {
		
    	com.yqsh.diningsys.core.util.Page<ServiceClass> pagebean = null;
		try {
			pagebean = serviceClassService.getPageList(serviceClass);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(serviceClass.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	@RequestMapping(value = "/getAllList")
	@ResponseBody
	public Object getAllList(HttpServletRequest request,HttpServletResponse response
							,ServiceClass serviceClass) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<ServiceClass> list = serviceClassService.getAllList(serviceClass);
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
							,ServiceClass serviceClass) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<TbBis> areaList = tbBisService.getAllList(new TbBis());
		List<DgConsumerSeat> seatList = consumerSeatService.getAllList(new DgConsumerSeat());
		for(TbBis area : areaList){
			Map<String,Object> treedom = new HashMap<String,Object>();
			treedom.put("id", area.getId() + "-bis");
			treedom.put("pid", 0);
			treedom.put("name", area.getBisName());
			result.add(treedom);
			for(DgConsumerSeat seat : seatList){
				Map<String,Object> tree = new HashMap<String,Object>();
				tree.put("id", seat.getId() + "-seat");
				tree.put("pid", area.getId() + "-bis");
				tree.put("name", seat.getName());
				result.add(tree);
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/getServiceClassByID")
	@ResponseBody
	public Object getserviceClassByID(HttpServletRequest request,HttpServletResponse response,ServiceClass serviceClass) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			ServiceClass ServiceClass = serviceClassService.getServiceClassByID(serviceClass);
			result.put("serviceClass", ServiceClass);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
    
    @RequestMapping(value = "/saveServiceClass")
	@ResponseBody
	public ResultInfo saveserviceClass(ServiceClass serviceClass) {
		try{
			serviceClass.setCreateUserid(getCurrentUser().getId() + "");
			serviceClassService.insertOrUpd(serviceClass);
			return returnSuccess();
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnFail();
	}
    
    @RequestMapping(value = "/saveServiceClassIds")
   	@ResponseBody
   	public ResultInfo saveServiceClassIds(ServiceClass serviceClass) {
   		try{
   			serviceClass.setCreateUserid(getCurrentUser().getId() + "");
   			serviceClassService.insertBatch(serviceClass);
   			return returnSuccess();
   		}catch(Exception e){
			e.printStackTrace();
   		}
   		return returnFail();
   	}
    
    @RequestMapping(value = "/deleteServiceClass")
	@ResponseBody
	public Object deleteserviceClass(ServiceClass serviceClass) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<String> list = new ArrayList<String>();
			for(String str : serviceClass.getEditIds().split(",")){
				list.add(str);
			}
			serviceClass.setIds(list);
			serviceClassService.deleteByIds(serviceClass);
			result.put("success","OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "false");
		}
		return result;
	}
}
