package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.deskBusiness.DgRecommendItem;
import com.yqsh.diningsys.web.service.deskBusiness.DgRecommendItemService;

@Controller
@RequestMapping("/RecommedItem")
public class RecommedItemController extends BaseController{
	
	@Autowired
	private DgRecommendItemService dgRecommendItemService;
	
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/recommend_dish");
    	return model;
	}
	
	
	@RequestMapping("/getAllData")
    @ResponseBody
    public Object getAllData(DgRecommendItem record, Object dgImportantActivityDiscountService) {
		List<DgRecommendItem> ret = dgRecommendItemService.selectAll(record);
        return ret;
    }
	
	/**
     * 
     * 获取具体打折项下的品项
     */
    @RequestMapping(value = "/getPanItemByAdd")
	@ResponseBody
    public Object getPanItemByAdd(HttpServletRequest request,HttpServletResponse response)
    {
    	Map<String,Object> ret = new HashMap<String, Object>();
    	String id = request.getParameter("chooseTrIds");
    	if(!StringUtils.isEmpty(id))
    	{
	    		List<DgRecommendItem> list = dgRecommendItemService.selectItemByAdd(id);	    		
	    		for(DgRecommendItem item:list)
	    		{
	    			item.setId(item.getItemId());
	    		}
	    		ret.put("success", true);
	    		ret.put("list",list);
	    		return ret;
    	}
		ret.put("success", true);
		ret.put("list",null);
    	return ret;
    }
    
    /**
     * @return
     */
    @RequestMapping(value = "/toAddItem")
    public ModelAndView toAddPlan(HttpServletRequest request,HttpServletResponse response)
    {
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/un_disable_add");
    	return model;
    }
	
    
    /**
     * @return
     */
    @RequestMapping(value = "/saveData")
    @ResponseBody
    public Object saveData(String data) {
    	int relust=dgRecommendItemService.insertMore(data);
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
    	dgRecommendItemService.deleteIds(ids);
    	ret.put("success", true);
    	return ret;
    }
    
    @RequestMapping("/exportXls")
	public ModelAndView exportXls(HttpServletRequest request,HttpServletResponse response,Model model){
    	List<DgRecommendItem> ret = dgRecommendItemService.selectAll(null);
		model.addAttribute("recommend", ret);
		ModelAndView modelAndView = new ModelAndView("deskBusiness/product_item/recommend_Xls");
		return modelAndView;
	}
}