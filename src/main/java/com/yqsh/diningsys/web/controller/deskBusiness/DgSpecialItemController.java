package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.deskBusiness.DgSpecialItem;
import com.yqsh.diningsys.web.service.deskBusiness.DgSpecialItemService;
/**
 * 特殊品项
 * @author heshuai
 *
 */
@Controller
@RequestMapping("/DgSpecialItem")
public class DgSpecialItemController  extends BaseController{
	@Autowired
	private DgSpecialItemService dgSpecialItemService;
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
		List<DgSpecialItem>  allItems = dgSpecialItemService.getAll();
		if(allItems.size() > 0) //最大里面最多只有两条数据
		{
			Map<String,Object> ret = new HashMap<String, Object>();
			for(DgSpecialItem item : allItems)
			{
				if(item.getType() == 1)
				{
					ret.put("serviceItemId",item.getItemId());
					ret.put("serviceItemCode",item.getItemCode());
					ret.put("serviceItemName",item.getName());
				}
				else
				{
					ret.put("minConsumptionItemId",item.getItemId());
					ret.put("minConsumptionItemCode",item.getItemCode());
					ret.put("minConsumptionItemName",item.getName());
					ret.put("minConsumeType",item.getMinConsumeType());
				}
			}
			request.setAttribute("item", ret);
		}
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/special_item");
    	return model;
	}
	
	@RequestMapping(value = "/addHostItem")
	public ModelAndView addHostItem(HttpServletRequest request,HttpServletResponse responsem)
	{
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/gift_item_plan_host_item_add");
    	return model;
	}
	
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse responsem)
	{
		Map<String,Object> ret = new HashMap<String, Object>();
		String data = request.getParameter("data");
		JSONObject obj = JSONObject.fromObject(data);
		String serviceItemId  = (String)obj.get("serviceItemId");
		String minConsumptionItemId  = (String)obj.get("minConsumptionItemId");
		dgSpecialItemService.deleteAll(); //先删除所有数据
		if(serviceItemId.length() > 0)
		{
			DgSpecialItem item = new DgSpecialItem();
			item.setItemId(Integer.parseInt(serviceItemId));
			item.setItemCode((String)obj.get("serviceItemCode"));
			item.setType(1);
			dgSpecialItemService.insertSelective(item);
		}
		
		
		if(minConsumptionItemId.length() > 0)
		{
			DgSpecialItem item = new DgSpecialItem();
			item.setItemId(Integer.parseInt(minConsumptionItemId));
			item.setItemCode((String)obj.get("minConsumptionItemCode"));
			item.setType(2);
			item.setMinConsumeType(Integer.parseInt((String)obj.get("minConsumeType")));
			dgSpecialItemService.insertSelective(item);
		}
		
		ret.put("success", true);
		return ret;
	}
}