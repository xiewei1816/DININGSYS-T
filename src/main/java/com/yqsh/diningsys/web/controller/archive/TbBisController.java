package com.yqsh.diningsys.web.controller.archive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;

/**
 * 营业市别管理控制器
* @author xiewei
* @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/archive/bis")
public class TbBisController extends BaseController{

    @Autowired
    private TbBisService tbBisService;
    @Autowired
    private TbOrgService tbOrgService;
    
    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("archive/bis/bis-index");
        return modelAndView;
    }

    @RequestMapping("/toEdit")
    public String toEdit(Model model,Integer id){
		model.addAttribute("listOrg",tbOrgService.getAllList(null)); //查询机构组织数据
    	if(id != null){
    		TbBis tbBis = new TbBis();
    		tbBis.setId(id);
			TbBis bis = tbBisService.getBisByID(tbBis);
			model.addAttribute("bis",bis);
		}
    	return "archive/bis/bis-edit";
	}

    /**
     * 跳转至回收站
     * @param model
     * @return
     */
    @RequestMapping("/trash")
    public ModelAndView trash(Model model){
        ModelAndView modelAndView = new ModelAndView("archive/bis/bis-trash");
        return modelAndView;
    }
    
    /**
     * 分页查询营业市别信息
     * @param request
     * @param response
     * @param tbBis
     * @return
     */
    @RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,TbBis tbBis) {
    	com.yqsh.diningsys.core.util.Page<TbBis> pagebean = null;
		try {
			pagebean = tbBisService.getPageList(tbBis);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(tbBis.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
    
    /**
     * 通过营业市别ID查询营业市别信息
     * @param request
     * @param response
     * @param tbBis
     * @return
     */
    @RequestMapping(value = "/getBisById")
   	@ResponseBody
   	public Object getBisById(HttpServletRequest request,HttpServletResponse response,TbBis tbBis) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			tbBis.setIsDel("0");//查询未被删除的营业市别信息
   			TbBis bis = tbBisService.getBisByID(tbBis);
   			result.put("bis", bis);
   			result.put("success", "OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "error");
   		}
   		return result;
   	}
    
    /**
     * 新增、修改营业市别信息保存
     * @param request
     * @param response
     * @param tbBis
     * @return
     */
    @RequestMapping(value = "/saveBis")
	@ResponseBody
	public Object saveBis(TbBis tbBis) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			TbBis tbBis1 = tbBisService.selectHasSameTimeAndOrg(tbBis);
			if(tbBis1 != null && tbBis.getId() != tbBis1.getId()){
				result.put("success", "false");
				result.put("error", "该组织机构下已经存在相同时间数据！");
				return result;
			}

			TbBis tbBis2 = tbBisService.selectHasSameNameAndOrg(tbBis);
			if(tbBis2 != null && tbBis.getId() != tbBis2.getId()){
				result.put("success", "false");
				result.put("error", "该组织机构下已经存在相同名字数据！");
				return result;
			}
			tbBisService.insertOrUpdBis(tbBis);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}

    /**
     * 营业市别信息回收站
     * @param request
     * @param response
     * @param tbBis
     * @return
     */
    @RequestMapping(value = "/deleteBisTrash")
   	@ResponseBody
	public Object deleteBisTrash(HttpServletRequest request,HttpServletResponse response,TbBis tbBis) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbBis.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbBis.setIds(list);
   			tbBisService.deleteTrash(tbBis);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 还原回收站营业市别信息
     * @param request
     * @param response
     * @param tbBis
     * @return
     */
    @RequestMapping(value = "/replyBis")
   	@ResponseBody
	public Object replyBis(HttpServletRequest request,HttpServletResponse response,TbBis tbBis) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbBis.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbBis.setIds(list);
   			tbBisService.replyBis(tbBis);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 删除营业市别信息
     * @param request
     * @param response
     * @param tbBis
     * @return
     */
    @RequestMapping(value = "/deleteBis")
   	@ResponseBody
   	public Object deleteBis(HttpServletRequest request,
   			HttpServletResponse response,TbBis tbBis) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbBis.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbBis.setIds(list);
   			tbBisService.deleteByIds(tbBis);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 导出营业市别信息Excel
     * @param request
     * @param response
     * @param model
     * @param tbBis
     * @return
     */
    @RequestMapping(value="exportXls")
	public ModelAndView exportXls(HttpServletRequest request,HttpServletResponse response,Model model,TbBis tbBis){
		List<TbBis> bisList = tbBisService.getAllList(tbBis);
		model.addAttribute("bisList", bisList);
		ModelAndView modelAndView = new ModelAndView("archive/bis/exportXls");
		return modelAndView;
	}
}
