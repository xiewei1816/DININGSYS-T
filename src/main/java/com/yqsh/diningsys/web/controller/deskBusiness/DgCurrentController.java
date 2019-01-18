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
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCurrentPrice;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemCurrentPriceService;
@Controller
@RequestMapping("/DgCurrent")
public class DgCurrentController extends BaseController{
	@Autowired
	private DgItemCurrentPriceService dgItemCurrentPriceService;
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/current_item");
    	return model;
	}
	
	
	@RequestMapping("/getAllData")
    @ResponseBody
    public Object getAllData(DgItemCurrentPrice dgItemCurrentPrice, Object dgImportantActivityDiscountService) {
		List<DgItemCurrentPrice> ret = dgItemCurrentPriceService.getAllData(dgItemCurrentPrice);
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
    			List<DgItemCurrentPrice> listRet = new ArrayList<DgItemCurrentPrice>();
	    		List<Integer> ids = new ArrayList<Integer>();
	    		
	    		String str[] = chooseTrIds.split(",");  
	    		for(int i=0;i<str.length;i++){  
	    		    ids.add(Integer.parseInt(str[i]));
	    		}
		        if(ids.size()>0)
		        {
			        List<DgItemFile> list2 = dgItemCurrentPriceService.selectItemByAdd(ids);
		    		for(DgItemFile item:list2)
		    		{
		    			DgItemCurrentPrice s = new DgItemCurrentPrice();
		    			s.setId(item.getId());
		    			s.setItemId(item.getId());
		    			s.setItemCode(item.getNum());
		    			s.setName(item.getName());
		    			s.setsPrice(item.getStandardPrice());
		    			s.setCurrentPrice(item.getStandardPrice()); //初始化为标准价格
		    			listRet.add(s);
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
    	int relust=dgItemCurrentPriceService.saveItemCurrPric(data);
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
    	dgItemCurrentPriceService.deleteIds(ids);
    	ret.put("success", true);
    	return ret;
    }
}