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
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePriceS;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.deskBusiness.DgForMealTimePriceSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgForMealTimePriceService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemForWeekService;
@Controller
@RequestMapping("/mealTime")
public class ProMealTimeController extends BaseController{
	@Autowired
	private DgForMealTimePriceService dgForMealTimePriceService;
	
	@Autowired
	private DgConsumptionAreaService dgConsumptionAreaService;

	@Autowired
	private TbBisService tbBisService;
	
	@Autowired
	private DgForMealTimePriceSService dgForMealTimePriceSService;
	
	@Autowired
	private DgItemForWeekService dgItemForWeekService;
	
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
    	TbBis bis = new TbBis();
    	bis.setIsDel("0");
		List<TbBis> items = tbBisService.getAllList(bis);
		request.setAttribute("meal", items);
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/meal_time");
    	return model;
	}
	
	@RequestMapping(value = "/getTableItem")
	@ResponseBody
	public Object getTableItem(HttpServletRequest request,HttpServletResponse response)
	{
    	TbBis bis = new TbBis();
    	bis.setIsDel("0");
		Map<String,Object> ret = new HashMap<String, Object>();
		List<TbBis> items = tbBisService.getAllList(bis);
		ret.put("success",true);
		ret.put("items",items);
		return ret;
	}
	
	
	@RequestMapping("/getAllData")
    @ResponseBody
    public Object getAllData(DgForMealTimePrice dgPlacePrice, Object dgImportantActivityDiscountService) {
		List<Map<String,Object>>  ret = new ArrayList<Map<String,Object>>();
		List<DgForMealTimePrice> list = dgForMealTimePriceService.getAllData(dgPlacePrice);
		for(DgForMealTimePrice l:list)
		{
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("id",l.getId());
			m.put("itemId",l.getItemId());
			m.put("name",l.getName());
			m.put("itemCode",l.getItemCode());
			m.put("sPrice",l.getsPrice());
			List<DgForMealTimePriceS> childList = dgForMealTimePriceSService.selectByMealPriceId(l.getId());
			for(DgForMealTimePriceS s:childList)
			{
				m.put("d"+s.getMealTimeId(),s.getPrice());
			}
			ret.add(m);
		}
        return ret;
    }
	
	 /**
     * @return
     */
    @RequestMapping(value = "/toAddPlanDish")
    public ModelAndView toAddPlanDish(HttpServletRequest request,HttpServletResponse response)
    {    	
		ModelAndView model = new ModelAndView("deskBusiness/product_item/meal_time_add");
    	return model;
    }
    
    
    /**
     * 
     * 获取具体打折项下的品项
     */
    @RequestMapping(value = "/getMealTimeAddDish")
	@ResponseBody
    public Object getMealTimeAddDish(HttpServletRequest request,HttpServletResponse response)
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
		    			m.put("id",item.getId());
		    			m.put("name",item.getName());
		    			m.put("itemId",item.getId());
		    			m.put("itemCode",item.getNum());
		    			m.put("sPrice",item.getStandardPrice());
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
    
    
    @RequestMapping("/saveData")
    @ResponseBody
    public ResultInfo saveData(String data){
    	int relust=dgForMealTimePriceService.saveForMealTimePrice(data);
    	if(relust>0){
    		return returnSuccess();
    	}
    	return returnFail();
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
    	dgForMealTimePriceSService.deleteIds(ids);
    	dgForMealTimePriceService.deleteIds(ids);
    	ret.put("success", true);
    	return ret;
    }
}
