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
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDisable;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDisableService;
@Controller
@RequestMapping("/DgItemDisable")
public class DgItemDisableController extends BaseController{
	@Autowired
	private DgItemDisableService dgItemDisableService;
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/item_disable");
    	return model;
	}
	
	
	@RequestMapping("/getAllData")
    @ResponseBody
    public Object getAllData(DgItemDisable dgItemDisable, Object dgImportantActivityDiscountService) {
		List<DgItemDisable> ret = dgItemDisableService.getAllData(dgItemDisable);
        return ret;
    }
	
	
	 /**
     * @return
     */
    @RequestMapping(value = "/toAddPlanDish")
    public ModelAndView toAddPlanDish(HttpServletRequest request,HttpServletResponse response)
    {    	
		ModelAndView model = new ModelAndView("deskBusiness/product_item/disable_time_add");
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
	    		List<Integer> ids = new ArrayList<Integer>();
	    		
	    		String str[] = chooseTrIds.split(",");  
	    		for(int i=0;i<str.length;i++){  
	    		    ids.add(Integer.parseInt(str[i]));
	    		}
		        if(ids.size()>0)
		        {
			        List<DgItemDisable> list2 = dgItemDisableService.selectItemByAdd(ids);
		    		for(DgItemDisable item:list2)
		    		{
		    			item.setId(-item.getItemId()); //设置成负数,不与本地重复
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
    
    
    @RequestMapping("/saveData")
    @ResponseBody
    public ResultInfo saveData(String data){
    	int relust=dgItemDisableService.saveItemDisable(data);
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
    	dgItemDisableService.deleteIds(ids);
    	ret.put("success", true);
    	return ret;
    }
}