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
import org.springframework.cache.annotation.Cacheable;
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
import com.yqsh.diningsys.web.model.archive.TbDep;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDep;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDepS;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
import com.yqsh.diningsys.web.service.archive.TbDepService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemProDepSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemProDepService;
@Controller
@RequestMapping("/DgItemProDepartment")
public class DgItemProDepartmentController extends BaseController{
	@Autowired
	private DgConsumptionAreaService dgConsumptionAreaService;
	@Autowired
	private TbDepService tbDepService;
	
	@Autowired
	private DgItemProDepService dgItemProDepService;
	@Autowired
	private DgItemProDepSService dgItemProDepSService;
	@Autowired
	private DgItemDiscountService dgItemDiscountService;
	
	@Autowired
	private DgItemFileTypeService dgItemFileTypeService;
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
		List<DgConsumptionArea> areas = dgConsumptionAreaService.getAllList(null);
		request.setAttribute("areas", areas);
		List<TbDep> deps = tbDepService.getAllList(null);
		request.setAttribute("deps", deps);
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/item_production_department");
    	return model;
	}
	
	@RequestMapping(value = "/getTableItem")
	@ResponseBody
	public Object getTableItem(HttpServletRequest request,HttpServletResponse response)
	{
		Map<String,Object> ret = new HashMap<String, Object>();
		List<DgConsumptionArea> items = dgConsumptionAreaService.getAllList(null);
		List<TbDep> deps = tbDepService.getAllList(null);
		ret.put("deps", deps);
		ret.put("success",true);
		ret.put("items",items);
		return ret;
	}
	
	@RequestMapping("/getAllData")
    @ResponseBody  
    public Object getAllData(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object>  query = new HashMap<String,Object>();
    	List<DgItemFile> items= null;
		String id = request.getParameter("id");//点击树返回id
		String name = request.getParameter("name");//查询名称
		String itemCode = request.getParameter("itemCode");//查询代码
		query.put("name", name);
		query.put("itemCode",itemCode);
		if(id == null) //第一次加载,判断是否存在数据
		{
			items = dgItemDiscountService.selectAllItemFile(); //获取所有品项
			List<DgConsumptionArea> areas = dgConsumptionAreaService.getAllList(null); //获取所有区域
			/**
	    	 * 
	    	 * 先遍历查询没有的数据
	    	 */
	    	for(DgItemFile item : items) //遍历是哟有品项
	    	{
	    		int count = dgItemProDepService.getCountByItemId(item.getId()); //查询没有的品项
	    		if(count == 0) //没有这个品项才进入下一步
	    		{
	    			DgItemProDep parentItem = new DgItemProDep();
	    			parentItem.setItemId(item.getId());
	    			parentItem.setItemCode(item.getNum());
	    			dgItemProDepService.insertBackId(parentItem);
	    			for(DgConsumptionArea area:areas)
	    			{
	    				DgItemProDepS childItem = new DgItemProDepS();
	    				childItem.setPlaceId(area.getId());
	    				childItem.setProDepId(parentItem.getId());
	    				childItem.setItemId(item.getId());
	    				childItem.setDepId(0); //初始化为0,为未知树
	    				dgItemProDepSService.insertSelective(childItem);//插入数据
	    			}
	    		}
	    		else //有这个品项就查询品项下面有没这个区域
	    		{
					DgItemProDep parentItem = dgItemProDepService.selectByItemId(item.getId());
	    			for(DgConsumptionArea area:areas)
	    			{
	    				Map<String,Object> obj = new HashMap<String, Object>();
	    				obj.put("area_id",area.getId());
	    				obj.put("id",parentItem.getId());
	    				int areaCount = dgItemProDepSService.selectByEarchId(obj);
	    				if(areaCount == 0) //没有包含这个地区,新的就增加
	    				{
    	    				DgItemProDepS childItem = new DgItemProDepS();
    	    				childItem.setPlaceId(area.getId());
    	    				childItem.setProDepId(parentItem.getId());
    	    				childItem.setItemId(item.getId());
    	    				childItem.setDepId(0); //初始化为0,为未知树
    	    				dgItemProDepSService.insertSelective(childItem);//插入数据
	    				}
	    			}
	    		}
	    	}	
		}
		else{ //id不为空,树查询
	    	DgItemFileType type= dgItemFileTypeService.selectByPrimaryKey(Integer.parseInt(id));
    		if(type.getpId() == 0) //大类情况
    		{
    			query.put("bId", type.getId()); //大类id
    		}
    		else //小类情况
    		{
    			query.put("sId", type.getId()); //小类id
    		}
		}
		List<Map<String,Object>>  ret = new ArrayList<Map<String,Object>>();
		List<DgItemProDep> list = dgItemProDepService.getAllData(query);
		for(DgItemProDep l:list)
		{
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("id",l.getId());
			m.put("itemId",l.getItemId());
			m.put("name",l.getName());
			m.put("itemCode",l.getItemCode());
			List<DgItemProDepS> childList = dgItemProDepSService.selectByPlaceParentId(l.getId());
			for(DgItemProDepS s:childList)
			{
				m.put("d"+s.getPlaceId(),"d"+s.getPlaceId()+":"+s.getDepId());
			}
			ret.add(m);
		}
        return ret;
    }
	
	@RequestMapping("/saveData")
    @ResponseBody
    public ResultInfo saveData(HttpServletRequest request,HttpServletResponse response){
    	String data = request.getParameter("data");
    	JSONArray json = JSONArray.fromObject(data);
    	dgItemProDepSService.deleteAll(); //先删除所有
		List<DgConsumptionArea> items = dgConsumptionAreaService.getAllList(null);
    	if(json.size()>0)
    	{
    		List<DgItemProDepS>  list = new ArrayList<DgItemProDepS>();
    		for(int i=0;i<json.size();i++)
    		{
    			JSONObject job = json.getJSONObject(i);
    			for(DgConsumptionArea d:items)
    			{
    				DgItemProDepS s = new DgItemProDepS();
    				s.setPlaceId(d.getId());
    				s.setProDepId(Integer.parseInt((String)job.get("id")));
    				s.setItemId(Integer.parseInt((String)job.get("itemId")));
    				s.setDepId(Integer.parseInt((String)job.get("d"+d.getId())));
    				list.add(s);
    			}
    		}
			dgItemProDepSService.insertChilds(list);
    	}
    		
    	return returnSuccess();
    }
}