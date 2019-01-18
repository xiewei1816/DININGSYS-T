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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.TbDep;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemFromCook;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDep;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDepS;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgItemFileService;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
import com.yqsh.diningsys.web.service.archive.TbDepService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemProDepSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemProDepService;
@Controller
@RequestMapping("/DgItemFromCook")
public class DgItemFromCookController extends BaseController{
	@Autowired
	private DgItemFileService dgItemFileService;
	@Autowired
	private DgItemDiscountService dgItemDiscountService;
	@Autowired
	private UserService userService;
	@Autowired
	private DgItemFileTypeService dgItemFileTypeService;
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
		List<SysUser> cookers = userService.getAllCooker();
		request.setAttribute("cookers", cookers);
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/item_from_cook");
    	return model;
	}
	
	@RequestMapping(value = "/getCookers")
	@ResponseBody
	public Object getCookers(HttpServletRequest request,HttpServletResponse response)
	{
		Map<String,Object> ret = new HashMap<String, Object>();
		List<SysUser> cookers = userService.getAllCooker();
		ret.put("cookers", cookers);
		ret.put("success",true);
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
		if(!StringUtils.isEmpty(id) && !id.equals("-1"))
		{
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
		List<DgItemFile> list = dgItemFileService.getAllItemCooker(query);
        return list;
    }
	
	@RequestMapping("/updateData")
    @ResponseBody
    public ResultInfo updateData(HttpServletRequest request,HttpServletResponse response){
    	String data = request.getParameter("data");
    	JSONArray json = JSONArray.fromObject(data);
    	if(json.size()>0)
    	{
    		List<DgItemFile> list = new ArrayList<DgItemFile>();
    		for(int i=0;i<json.size();i++)
    		{
    				JSONObject job = json.getJSONObject(i);
    				DgItemFile s = new DgItemFile();
    				s.setId(Integer.parseInt((String)job.get("id")));
    				s.setCs(Integer.parseInt((String)job.get("cookId")));
    				list.add(s);
    		}
    		dgItemFileService.updateCsList(list);
    	}
    		
    	return returnSuccess();
    }
}