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
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.archive.TbFylx;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.archive.TbFylxService;

/**
 * 费用类型控制器
* @author xiewei
* @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/archive/fylx")
public class TbFylxController extends BaseController{

    @Autowired
    private TbFylxService tbFylxService;
    @Autowired
    private TbOrgService tbOrgService;

    @RequestMapping("/index")
    public ModelAndView index(Model model,TbOrg tbOrg,TbFylx tbFylx){
    	model.addAttribute("listOrg",tbOrgService.getAllList(tbOrg)); //查询机构组织数据
    	tbFylx.setIsDel("0");
    	model.addAttribute("listFylx",tbFylxService.getAllList(tbFylx)); //查询费用类型类型数据
        ModelAndView modelAndView = new ModelAndView("archive/fylx/fylx-index");
        return modelAndView;
    }
    
    /**
     * 跳转至回收站
     * @param model
     * @return
     */
    @RequestMapping("/trash")
    public ModelAndView trash(Model model){
        ModelAndView modelAndView = new ModelAndView("archive/fylx/fylx-trash");
        return modelAndView;
    }
    
    /**
     * 分页查询费用类型信息
     * @param request
     * @param response
     * @param tbFylx
     * @return
     */
    @RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,TbFylx tbFylx) {
    	com.yqsh.diningsys.core.util.Page<TbFylx> pagebean = null;
		try {
			pagebean = tbFylxService.getPageList(tbFylx);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(tbFylx.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
    
    /**
     * 通过费用类型ID查询费用类型信息
     * @param request
     * @param response
     * @param tbFylx
     * @return
     */
    @RequestMapping(value = "/getFylxById")
   	@ResponseBody
   	public Object getFylxById(HttpServletRequest request,HttpServletResponse response,TbFylx tbFylx) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			tbFylx.setIsDel("0");//查询未被删除的费用类型类型信息
   			TbFylx fylx = tbFylxService.getFylxById(tbFylx);
   			result.put("fylx", fylx);
   			result.put("success", "OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "error");
   		}
   		return result;
   	}
    
    /**
     * 新增、修改费用类型信息保存
     * @param request
     * @param response
     * @param tbFylx
     * @return
     */
    @RequestMapping(value = "/saveFylx")
	@ResponseBody
	public Object saveFylx(HttpServletRequest request,HttpServletResponse response,TbFylx tbFylx) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			tbFylxService.insertOrUpdFylx(tbFylx);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}

    /**
     * 费用类型信息回收站
     * @param request
     * @param response
     * @param tbFylx
     * @return
     */
    @RequestMapping(value = "/deleteFylxTrash")
   	@ResponseBody
	public Object deleteFylxTrash(HttpServletRequest request,HttpServletResponse response,TbFylx tbFylx) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbFylx.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbFylx.setIds(list);
   			tbFylxService.deleteTrash(tbFylx);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 还原回收站费用类型信息
     * @param request
     * @param response
     * @param tbFylx
     * @return
     */
    @RequestMapping(value = "/replyFylx")
   	@ResponseBody
	public Object replyFylx(HttpServletRequest request,HttpServletResponse response,TbFylx tbFylx) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbFylx.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbFylx.setIds(list);
   			tbFylxService.replyFylx(tbFylx);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 删除费用类型信息
     * @param request
     * @param response
     * @param tbFylx
     * @return
     */
    @RequestMapping(value = "/deleteFylx")
   	@ResponseBody
   	public Object deleteFylx(HttpServletRequest request,
   			HttpServletResponse response,TbFylx tbFylx) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbFylx.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbFylx.setIds(list);
   			tbFylxService.deleteByIds(tbFylx);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 导出费用类型信息Excel
     * @param request
     * @param response
     * @param model
     * @param tbFylx
     * @return
     */
    @RequestMapping(value="exportXls")
	public ModelAndView exportXls(HttpServletRequest request,HttpServletResponse response,Model model,TbFylx tbFylx){
		List<TbFylx> fylxList = tbFylxService.getAllList(tbFylx);
		model.addAttribute("fylxList", fylxList);
		ModelAndView modelAndView = new ModelAndView("archive/fylx/exportXls");
		return modelAndView;
	}
}
