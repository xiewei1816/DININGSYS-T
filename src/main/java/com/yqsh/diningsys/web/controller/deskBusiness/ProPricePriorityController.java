package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPricePriority;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemPricePriorityService;
@Controller
@RequestMapping("/ProPricePriority")
public class ProPricePriorityController extends BaseController{
	@Autowired
	private DgItemPricePriorityService dgItemPricePriorityService;
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
		List<DgItemPricePriority> all = dgItemPricePriorityService.getAll();
		request.setAttribute("list", all);
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/price_priority");
    	return model;
	}
	
	
	@RequestMapping(value = "/updata")
	@ResponseBody
	public Object updata(HttpServletRequest request,HttpServletResponse response)
	{
		Map<String,Object> ret = new HashMap<String, Object>();
		String data = request.getParameter("data");
    	JSONArray json = JSONArray.fromObject(data);
    	if(json.size()>0)
    	{
    		for(int i=0;i<json.size();i++)
    		{
    			JSONObject job = json.getJSONObject(i);
    			DgItemPricePriority item = new DgItemPricePriority();
    			item.setId(Integer.valueOf((String)job.get("id")));
    			item.setnIndex(i+1);
    			item.setEnable(Integer.valueOf((String)job.get("enable")));
    			dgItemPricePriorityService.updateByPrimaryKeySelective(item);
    		}
    	}
    	ret.put("success",true);
    	return ret;
	}
	
	
	@RequestMapping(value = "/mo")
	@ResponseBody
	public Object mo(HttpServletRequest request,HttpServletResponse response)
	{
		Map<String,Object> ret = new HashMap<String, Object>();
		dgItemPricePriorityService.updateAll();
		List<DgItemPricePriority> all = dgItemPricePriorityService.getAll();
		ret.put("list", all);
    	ret.put("success",true);
    	return ret;
	}
}