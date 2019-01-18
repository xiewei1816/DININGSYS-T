package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;








import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;
import com.yqsh.diningsys.web.model.deskBusiness.DgGateItemAllowCustom;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCustomMoney;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
import com.yqsh.diningsys.web.service.deskBusiness.BgItemCustomMoneyNameService;
import com.yqsh.diningsys.web.service.deskBusiness.DgGateItemAllowCustomService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemCustomMoneyService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDisableService;
/**
 * 
 * 自定义金额
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/DgCustomMoney")
public class DgCustomMoneyController extends BaseController{
	
	@Autowired
	private DgGateItemAllowCustomService dgGateItemAllowCustomService;
	@Autowired
	private DgItemCustomMoneyService dgItemCustomMoneyService;
	
	@Autowired
	private BgItemCustomMoneyNameService dgItemCustomMoneyNameService;
	
	@Autowired
	private DgItemFileTypeService dgItemFileTypeService;
	 
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
		
		int nameCount = dgItemCustomMoneyNameService.getCount();
		if(nameCount != 0) //有就获取 
		{
			List<BgItemCustomMoneyName> names = dgItemCustomMoneyNameService.getAll();
			request.setAttribute("names", names);
		}
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/custom_money");
    	return model;
	}
	/**
	 * 
	 * 获取小类
	 */
	
	@RequestMapping(value = "/gate")
	@ResponseBody
	public Object gate(HttpServletRequest request,HttpServletResponse response)
	{
		List<DgItemFileType> allGateTypes = dgItemFileTypeService.selectAllItemFileSmallType();
		for(DgItemFileType type: allGateTypes)
		{
			int hava = dgGateItemAllowCustomService.selectByGateItemId(type.getId());
			if(hava == 0) //若是没有,就创建
			{
				DgGateItemAllowCustom record = new DgGateItemAllowCustom();
				record.setSmallGateId(type.getId());
				record.setAllowCustom(1); //初始化1,允许
				dgGateItemAllowCustomService.insertSelective(record);
			}
		}
		List<DgGateItemAllowCustom> all = dgGateItemAllowCustomService.getAll();
    	return all;
	}
	
	/**
	 * 
	 * 获取具体品项列表
	 */
	@RequestMapping(value = "/getItem")
	@ResponseBody
	public Object getItem(HttpServletRequest request,HttpServletResponse response)
	{
		String type = request.getParameter("type");
		List<DgItemCustomMoney> items = dgItemCustomMoneyService.getAllByCustomNameId(Integer.parseInt(type));
		
		return items;
	}
	
	 /**
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add(HttpServletRequest request,HttpServletResponse response)
    {    	
		ModelAndView model = new ModelAndView("deskBusiness/product_item/custom_money_add");
    	return model;
    }
    
    /**
     * 
     * 获取具体打折项下的品项
     */
    @RequestMapping(value = "/toAddItem")
	@ResponseBody
    public Object toAddItem(HttpServletRequest request,HttpServletResponse response)
    {
    	Map<String,Object> ret = new HashMap<String, Object>();
    	String chooseTrIds = request.getParameter("chooseTrIds");
    	if(!StringUtils.isEmpty(chooseTrIds))
    	{
	    		List<Integer> ids = new ArrayList<Integer>();
	    		
	    		String str[] = chooseTrIds.split(",");  
	    		for(int i=0;i<str.length;i++){  
	    		    ids.add(Integer.parseInt(str[i]));
	    		}
		        if(ids.size()>0)
		        {
			        List<DgItemCustomMoney> list2 = dgItemCustomMoneyService.selectAllByItemIds(ids);
		    		for(DgItemCustomMoney item:list2)
		    		{		    			
		    			item.setId(-item.getItemId());
		    		}
		    		ret.put("success", true);
		    		ret.put("list",list2);
		        }
		        else
		        {
		        	ret.put("success", true);
		    		ret.put("list",null);
		        }
	    		return ret;
    	}
		ret.put("success", true);
		ret.put("list",null);
    	return ret;
    }
    
    
    @RequestMapping("/saveCustom")
    @ResponseBody
    public Object saveCustom(HttpServletRequest request,HttpServletResponse response)
    {
    	Map<String,Object> ret =new HashMap<String,Object>();
    	String gate = request.getParameter("gate");
    	String dish = request.getParameter("dish");
    	String type = request.getParameter("type");
    	String nameContent = request.getParameter("nameContent");
    	
    	/**
    	 * 更新小类
    	 */
    	JSONArray gateJson = JSONArray.fromObject(gate);
    	if(gateJson.size() >0)
    	{
    		for(int i=0;i<gateJson.size();i++)
    		{
    			JSONObject object = gateJson.getJSONObject(i);
    			DgGateItemAllowCustom gateItem = new DgGateItemAllowCustom();
    			gateItem.setId(Integer.parseInt((String)object.get("id")));
    			gateItem.setAllowCustom(Integer.parseInt((String)object.get("allowCustom")));
    			
    			dgGateItemAllowCustomService.updateByPrimaryKeySelective(gateItem);//更新小类
    		}
    	}
    	
    	//只保存名字不为空的
    	JSONArray nameJson = JSONArray.fromObject(nameContent);
    	if(nameJson.size() >0)
    	{
    		for(int i=0;i<nameJson.size();i++)
    		{
    			JSONObject object = nameJson.getJSONObject(i);
    			int itemCode = Integer.parseInt((String)object.get("index"));
    			int count = dgItemCustomMoneyNameService.getCountByItemCode(itemCode);
    			String customName = (String)object.get("customName");
    			if(count == 0) //插入
    			{
    				BgItemCustomMoneyName  name  = new BgItemCustomMoneyName();
    				name.setCustomMoneyName(customName);
    				name.setItemCode(itemCode);
    				dgItemCustomMoneyNameService.insertSelective(name);
    			}
    			else//更新 
    			{
    				BgItemCustomMoneyName  name  = new BgItemCustomMoneyName();
    				name.setCustomMoneyName(customName);
    				name.setItemCode(itemCode);
        			dgItemCustomMoneyNameService.updateByItemCode(name);
    			}
    		}
    	}
    	
    	//存在名字时,才保存(最后保存)
    	if(!type.equals("0"))
    	{
	    	/**
	    	 * 先删除对应的所有数据
	    	 */
	    	dgItemCustomMoneyService.deleteByCustomId(Integer.parseInt(type));
	    	JSONArray dishJson = JSONArray.fromObject(dish);
	    	if(dishJson.size() > 0 )
	    	{
	    		List<DgItemCustomMoney> list = new ArrayList<DgItemCustomMoney>();
	    		String itemIds="";
	    		for(int i=0;i<dishJson.size();i++)
	    		{
	    			JSONObject object = dishJson.getJSONObject(i);
	    			DgItemCustomMoney dishItem = new DgItemCustomMoney();
	    			dishItem.setItemId(Integer.parseInt((String)object.get("itemId")));
	    			dishItem.setItemCode((String)object.get("itemCode"));
	    			dishItem.setCustomMoneyId(Integer.parseInt(type));
	    			itemIds += (String)object.get("itemId");
	        		list.add(dishItem);
	    		}
	    		itemIds = itemIds.substring(0, itemIds.length()-1);
	    		dgItemCustomMoneyService.deleteByItemIds(itemIds); //先删除数据库中存在的菜品id
    			dgItemCustomMoneyService.insertChilds(list);//依次插入
	    	}
    	}
    	
    	List<BgItemCustomMoneyName> names = dgItemCustomMoneyNameService.getAll();
		request.setAttribute("names", names);
		ret.put("names",names);
    	ret.put("success",true);
    	return ret;
    }
    
    /**
     * 
     * 批量删除
     * @param ids
     * @return
     */
    
    @RequestMapping("/delData")
    @ResponseBody
    public  Object delData(String ids){
    	Map<String,Object> ret = new HashMap<String, Object>();
    	dgItemCustomMoneyService.deleteIds(ids);
    	ret.put("success", true);
    	return ret;
    }
 
    @RequestMapping("/exportXls")
	public ModelAndView exportXls(HttpServletRequest request,HttpServletResponse response,Model model){
    	String type = request.getParameter("type");
		List<DgItemCustomMoney> items = dgItemCustomMoneyService.getAllByCustomNameId(Integer.parseInt(type));
		model.addAttribute("custom", items);
		ModelAndView modelAndView = new ModelAndView("deskBusiness/product_item/custom_Xls");
		return modelAndView;
	}
}