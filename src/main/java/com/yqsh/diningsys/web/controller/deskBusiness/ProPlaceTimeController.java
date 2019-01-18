package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantActivityDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemForMealTimeS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemForWeek;
import com.yqsh.diningsys.web.model.deskBusiness.DgNewestItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;
import com.yqsh.diningsys.web.model.deskBusiness.DgWeekDiscount;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemForWeekService;
import com.yqsh.diningsys.web.service.deskBusiness.DgNewestItemService;
import com.yqsh.diningsys.web.service.deskBusiness.DgPlacePriceSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgPlacePriceService;
@Controller
@RequestMapping("/placeTime")
public class ProPlaceTimeController extends BaseController{
	@Autowired
	private DgPlacePriceSService dgPlacePriceSService;
	
	@Autowired
	private DgConsumptionAreaService dgConsumptionAreaService;

	@Autowired
	private DgPlacePriceService dgPlacePriceService;
	
	@Autowired
	private DgItemForWeekService dgItemForWeekService;
	
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
		List<DgConsumptionArea> areas = dgConsumptionAreaService.getAllList(null);
		request.setAttribute("areas", areas);
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/place_time");
    	return model;
	}
	
	@RequestMapping(value = "/getTableItem")
	@ResponseBody
	public Object getTableItem(HttpServletRequest request,HttpServletResponse response)
	{
		Map<String,Object> ret = new HashMap<String, Object>();
		List<DgConsumptionArea> items = dgConsumptionAreaService.getAllList(null);
		ret.put("success",true);
		ret.put("items",items);
		return ret;
	}
	
	
	@RequestMapping("/getAllData")
    @ResponseBody
    public Object getAllData(DgPlacePrice dgPlacePrice, Object dgImportantActivityDiscountService) {
		List<Map<String,Object>>  ret = new ArrayList<Map<String,Object>>();
		List<DgPlacePrice> list = dgPlacePriceService.getAllData(dgPlacePrice);
		for(DgPlacePrice l:list)
		{
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("id",l.getId());
			m.put("itemId",l.getItemId());
			m.put("name",l.getName());
			m.put("itemCode",l.getItemCode());
			m.put("sPrice",l.getsPrice());
			List<DgPlacePriceS> childList = dgPlacePriceSService.selectByPlacePriceId(l.getId());
			for(DgPlacePriceS s:childList)
			{
				m.put("d"+s.getPlaceId(),s.getPrice());
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
    	int relust=dgPlacePriceService.savePlacePrice(data);
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
    	dgPlacePriceSService.deleteIds(ids);
    	dgPlacePriceService.deleteIds(ids);
    	ret.put("success", true);
    	return ret;
    }
}
