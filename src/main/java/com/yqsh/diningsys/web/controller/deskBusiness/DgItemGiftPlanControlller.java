package com.yqsh.diningsys.web.controller.deskBusiness;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlan;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlanS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscountS;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemForWeekService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemGiftPlanSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemGiftPlanService;
@Controller
@RequestMapping("/DgItemGiftPlan")
public class DgItemGiftPlanControlller extends BaseController{
	@Autowired
	private DgItemGiftPlanService dgItemGiftPlanService;
	@Autowired
	private DgItemGiftPlanSService dgItemGiftPlanSService;
	
	@Autowired
	private DgItemForWeekService dgItemForWeekService;
	
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/gift_item_plan");
    	return model;
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse responsem)
	{
		String id = request.getParameter("id");
    	if(!StringUtils.isEmpty(id))
    	{
    		DgItemGiftPlan gift = dgItemGiftPlanService.getDataByPrimaryKey(Integer.parseInt(id));
    		request.setAttribute("gift", gift);
    	}
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/gift_item_plan_add");
    	return model;
	}
	
	
	@RequestMapping(value = "/addHostItem")
	public ModelAndView addHostItem(HttpServletRequest request,HttpServletResponse responsem)
	{
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/gift_item_plan_host_item_add");
    	return model;
	}
	
    @RequestMapping("/getAllData")
    @ResponseBody
    public Page<DgItemGiftPlan> getAllData(DgItemGiftPlan dgItemGiftPlan) {
        Page<DgItemGiftPlan> pagebean = null;
        try {
            pagebean = dgItemGiftPlanService.getPageGiftPlan(dgItemGiftPlan);
            pagebean.setPage(dgItemGiftPlan.getPage());
            pagebean = (Page<DgItemGiftPlan>) pageResult(pagebean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }
	/**
     * 
     * 增加打折品项
     */
    @RequestMapping(value = "/getItemGiftAddDish")
	@ResponseBody
    public Object getItemGiftAddDish(HttpServletRequest request,HttpServletResponse response)
    {
    	Map<String,Object> ret = new HashMap<String, Object>();
    	String chooseTrIds = request.getParameter("chooseTrIds");
    	if(!StringUtils.isEmpty(chooseTrIds))
    	{
    			List<Map<String,Object>>  listRet = new ArrayList<Map<String,Object>>();
	    		List<Integer> ids = new ArrayList<Integer>();
	    		
	    		String str[] = chooseTrIds.split(",");  
	    		for(int i=0;i<str.length;i++){  
	    		    ids.add(Integer.parseInt(str[i]));
	    		}
		        if(ids.size()>0)
		        {
			        List<DgItemFile> list2 = dgItemForWeekService.selectItemByAdd(ids);
		    		for(DgItemFile item:list2)
		    		{		    			
		    			Map<String,Object> m = new HashMap<String, Object>();
		    			m.put("id",-item.getId()); //为负数,防止
		    			m.put("name",item.getName());
		    			m.put("itemId",item.getId());
		    			m.put("itemCode",item.getNum());
		    			m.put("enable",1); //启用设置为1
		    			m.put("giftAcount",1); //方案价格设置为标准价格
		    			listRet.add(m);
		    		}
		    		ret.put("success", true);
		    		ret.put("list",listRet);
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
    
    
    
    /**
     * 
     * 获取子表信息(品项表)
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getAllImportChild")
    @ResponseBody
    public Object getAllImportChild(HttpServletRequest request,HttpServletResponse response) {
    	String id = request.getParameter("id");
    	List<DgItemGiftPlanS>  ret = null;
    	if(!StringUtils.isEmpty(id)) //查询id
    	{
    		ret = dgItemGiftPlanSService.seleByPid(Integer.parseInt(id));
    	}
    	return ret;
    }
    
    
    
    /**
     * 
     * 功能:保存主表信息
     */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response,DgItemGiftPlan dim)
	{
    	Map<String,Object> ret = new HashMap<String, Object>();
    	
    	
    	int count = dgItemGiftPlanService.seleNameCode(dim);
		if (count > 0) {
			ret.put("success", false);
			ret.put("msg", "存在同名的重要活动打折方案!");
			return ret;
		}
		
    	if(!StringUtils.isEmpty(dim.getId())) //更新
    	{
    		if(StringUtils.isEmpty(dim.getEnable()))
    		{
    			dim.setEnable(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek1()))
    		{
    			dim.setWeek1(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek2()))
    		{
    			dim.setWeek2(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek3()))
    		{
    			dim.setWeek3(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek4()))
    		{
    			dim.setWeek4(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek5()))
    		{
    			dim.setWeek5(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek6()))
    		{
    			dim.setWeek6(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek7()))
    		{
    			dim.setWeek7(0);
    		}
    		dgItemGiftPlanService.updateByPrimaryKeySelective(dim);
    		dgItemGiftPlanSService.deleteIds(""+dim.getId()); //删除子表信息
    	}
    	else //插入
    	{
    		if(StringUtils.isEmpty(dim.getEnable()))
    		{
    			dim.setEnable(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek1()))
    		{
    			dim.setWeek1(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek2()))
    		{
    			dim.setWeek2(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek3()))
    		{
    			dim.setWeek3(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek4()))
    		{
    			dim.setWeek4(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek5()))
    		{
    			dim.setWeek5(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek6()))
    		{
    			dim.setWeek6(0);
    		}
    		if(StringUtils.isEmpty(dim.getWeek7()))
    		{
    			dim.setWeek7(0);
    		}
    		dim.setRecycle(0); //初始化不进入回收站
    		dgItemGiftPlanService.insertBackId(dim);
    	}
    	
    	ret.put("success", true);
    	ret.put("id",dim.getId());
    	return ret;
	}
	
	
	/**
     * 功能:保存附表信息
     */
    @RequestMapping(value="/saveChild")
    @ResponseBody
    public Object saveChild(HttpServletRequest request,HttpServletResponse response)
    {
    	Map<String,Object> ret = new HashMap<String, Object>();
        String id = request.getParameter("id");//方案id
        String childContent = request.getParameter("childContent");
    	JSONArray json = JSONArray.fromObject(childContent);
    	if(json.size()>0)
    	{
    		List<DgItemGiftPlanS> list = new ArrayList<DgItemGiftPlanS>();
    		for(int i=0;i<json.size();i++)
    		{
    			DgItemGiftPlanS item = new DgItemGiftPlanS();
    			JSONObject job = json.getJSONObject(i);
    			item.setParentId(Integer.parseInt(id)); //主表id
    			item.setItemId(Integer.parseInt((String)job.get("itemId")));
    			item.setItemCode((String)job.get("itemCode"));
    			item.setGiftAcount(Integer.valueOf((String)job.get("giftAcount")));
    			item.setEnable(Integer.parseInt((String)job.get("enable")));
    			list.add(item);
    		}
    		dgItemGiftPlanSService.insertChilds(list);
    	}
    	ret.put("success", true);
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
//    	dgItemGiftPlanSService.deleteIds(ids);
//    	dgItemGiftPlanService.deleteIds(ids);
    	dgItemGiftPlanService.trash(ids);
    	ret.put("success", true);
    	return ret;
    }
    
    
    /**
     * 
     * 批量删除
     * @param ids
     * @return
     */
    
    @RequestMapping("/delete")
    @ResponseBody
    public  Object delete(String ids){
    	Map<String,Object> ret = new HashMap<String, Object>();
    	dgItemGiftPlanSService.deleteIds(ids);
    	dgItemGiftPlanService.deleteIds(ids);
    	ret.put("success", true);
    	return ret;
    }
    
    /**
     * 
     * 批量还原
     * @param ids
     * @return
     */
    
    @RequestMapping("/restore")
    @ResponseBody
    public  Object restore(String ids){
    	Map<String,Object> ret = new HashMap<String, Object>();
    	dgItemGiftPlanService.restore(ids);
    	ret.put("success", true);
    	return ret;
    }
    
    @RequestMapping("/trash")
	public ModelAndView trash(HttpServletRequest request,HttpServletResponse response,Model model){
		ModelAndView modelAndView = new ModelAndView("deskBusiness/product_item/dg_gift_pan_trash");
		return modelAndView;
	}
    
    
    @RequestMapping("/exportXls")
	public ModelAndView exportXls(HttpServletRequest request,HttpServletResponse response,Model model){
		List<DgItemGiftPlan> gift =dgItemGiftPlanService.selectAll();
		model.addAttribute("gift", gift);
		ModelAndView modelAndView = new ModelAndView("deskBusiness/product_item/gift_Xls");
		return modelAndView;
	}
    
    /**
	 * form表单提交 Date类型数据绑定
	 * 日期格式化
	 * @param binder
	 * @see [类、类#方法、类#成员]
	 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	} 
    
}