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

import com.mysql.fabric.xmlrpc.base.Array;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgGiftForm;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantAcitivityDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantActivityDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.deskBusiness.DgNewestItem;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.deskBusiness.DgImportantAcitivityDiscountSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgImportantActivityDiscountService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountProgrammeService;
@Controller
@RequestMapping("/ProImportant")
public class ProImportantActivityController extends BaseController{

	@Autowired
	private DgImportantActivityDiscountService dgImportantActivityDiscountService;
	
	@Autowired
	private DgImportantAcitivityDiscountSService dgImportantAcitivityDiscountSService;
	
	@Autowired
	private DgItemDiscountProgrammeService dgItemDiscountProgrammeService;
	@Autowired
	private TbOrgService tbOrgService;
	
	
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)
	{
		List<TbOrg> orgs = tbOrgService.getAllList(null);
		request.setAttribute("orgs", orgs);
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/important_activitie_discount");
    	return model;
	}
	
	
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse responsem)
	{
		String id = request.getParameter("id");
    	if(!StringUtils.isEmpty(id))
    	{
    		DgImportantActivityDiscount disP = dgImportantActivityDiscountService.selectByPrimaryKey(Integer.parseInt(id));
    		request.setAttribute("disP", disP);
    	}
    	
		List<TbOrg> orgs = tbOrgService.getAllList(null);
		request.setAttribute("orgs", orgs);
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/important_activitie_discount_add");
    	return model;
	}
	
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response,DgImportantActivityDiscount dis)
	{
    	Map<String,Object> ret = new HashMap<String, Object>();
    	
    	
		int count = dgImportantActivityDiscountService.seleNameCode(dis);
		if (count > 0) {
			ret.put("success", false);
			ret.put("msg", "存在同名的重要活动打折方案!");
			return ret;
		}
		
    	if(!StringUtils.isEmpty(dis.getId())) //更新
    	{
    		dgImportantActivityDiscountService.updateByPrimaryKeySelective(dis);
    		dgImportantAcitivityDiscountSService.deleteIds(""+dis.getId());
    	}
    	else
    	{
    		dis.setDustbin(0);
    		dgImportantActivityDiscountService.insertBackId(dis);
    	}
    	
    	if(dis.getEnable() == 1) //启用就关闭其它
		{
			dgImportantActivityDiscountService.updateDisable(dis.getId());
		}
    	ret.put("success", true);
    	ret.put("id",dis.getId());
    	return ret;
	}
	
	
	
    /**
     * 保存方案附表
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
    		for(int i=0;i<json.size();i++)
    		{
    			DgImportantAcitivityDiscountS item = new DgImportantAcitivityDiscountS();
    			JSONObject job = json.getJSONObject(i);
    			item.setpId(Integer.parseInt(id));
    			item.setGateId(Integer.parseInt((String)job.get("code")));
    			item.setDiscount(Integer.parseInt((String)job.get("discount")));
    			dgImportantAcitivityDiscountSService.insert(item);
    		}
    	}
    	ret.put("success", true);
    	return ret;
    }
    
	
    @RequestMapping("/getAllData")
    @ResponseBody
    public Page<DgImportantActivityDiscount> getAllData(DgImportantActivityDiscount dgGiftForm) {
        Page<DgImportantActivityDiscount> pagebean = null;
        try {
            pagebean = dgImportantActivityDiscountService.getAllImportantActivity(dgGiftForm);
            pagebean.setPage(dgGiftForm.getPage());
            pagebean = (Page<DgImportantActivityDiscount>) pageResult(pagebean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }
    
    
    
    
    @RequestMapping("/getAllImportChild")
    @ResponseBody
    public Object getAllImportChild(HttpServletRequest request,HttpServletResponse response) {
    	String id = request.getParameter("id");
    	List<DgImportantAcitivityDiscountS>  ret = null;
    	if(!StringUtils.isEmpty(id)) //查询id
    	{
    		List<DgItemFileType> list= dgItemDiscountProgrammeService.selectAllItemFileType("");
    		ret = dgImportantAcitivityDiscountSService.seleByPid(Integer.parseInt(id));
    		for(DgItemFileType l:list) //遍历查询新增小类
    		{
    			boolean havaGate = false;
    			for(DgImportantAcitivityDiscountS s : ret)
    			{
    				if(s.getGateId() == l.getId())
    				{
    					havaGate = true;
    					break;
    				}  		
    			}
    			if(havaGate == false)
    			{
    				DgImportantAcitivityDiscountS d = new DgImportantAcitivityDiscountS();
        			d.setId(-l.getId());
        			d.setpId(l.getId());
        			d.setGateId(l.getId());
        			d.setDiscount(100);
        			d.setCode(l.getNum());
        			d.setName(l.getName());
        			ret.add(d);
    			}
    		}
    	}
    	else
    	{
    		List<DgItemFileType> list= dgItemDiscountProgrammeService.selectAllItemFileType("");
			ret = new ArrayList<DgImportantAcitivityDiscountS>();
    		for(DgItemFileType l:list)
    		{
    			DgImportantAcitivityDiscountS d = new DgImportantAcitivityDiscountS();
    			d.setId(-l.getId());
    			d.setpId(l.getId());
    			d.setGateId(l.getId());
    			d.setDiscount(100);
    			d.setCode(l.getNum());
    			d.setName(l.getName());
    			ret.add(d);
    		}
    	}
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
    	dgImportantActivityDiscountService.trash(ids);
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
    	dgImportantAcitivityDiscountSService.deleteIds(ids);
    	dgImportantActivityDiscountService.deleteIds(ids);
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
    	dgImportantActivityDiscountService.restore(ids);
    	ret.put("success", true);
    	return ret;
    }
    
    @RequestMapping("/trash")
	public ModelAndView trash(HttpServletRequest request,HttpServletResponse response,Model model){
		List<TbOrg> orgs = tbOrgService.getAllList(null);
		request.setAttribute("orgs", orgs);
		ModelAndView modelAndView = new ModelAndView("deskBusiness/product_item/important_activitie_trash");
		return modelAndView;
	}
    
    
    @RequestMapping("/exportXls")
	public ModelAndView exportXls(HttpServletRequest request,HttpServletResponse response,Model model){
    	List<DgImportantActivityDiscount> ret = dgImportantActivityDiscountService.seleAll();
		model.addAttribute("activity", ret);
		ModelAndView modelAndView = new ModelAndView("deskBusiness/product_item/important_act_Xls");
		return modelAndView;
	}
    /**
	 * form表单提交 Date类型数据绑定
	 * <功能详细描述>
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