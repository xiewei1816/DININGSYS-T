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
import com.yqsh.diningsys.web.model.archive.TbRfct;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.archive.TbRfctService;

/**
 * 退菜原因类型控制器
* @author xiewei
* @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/archive/rfct")
public class TbRfctController extends BaseController{

    @Autowired
    private TbRfctService tbRfctService;
    @Autowired
    private TbOrgService tbOrgService;

    @RequestMapping("/index")
    public ModelAndView index(Model model,TbOrg tbOrg,TbRfct tbRfct){
    	model.addAttribute("listOrg",tbOrgService.getAllList(tbOrg)); //查询机构组织数据
    	//查询退菜原因类型类型数据
    	tbRfct.setIsDel("0");
    	model.addAttribute("listRfct",tbRfctService.getAllList(tbRfct)); 
        ModelAndView modelAndView = new ModelAndView("archive/rfct/rfct-index");
        return modelAndView;
    }
    
    /**
     * 跳转至回收站
     * @param model
     * @return
     */
    @RequestMapping("/trash")
    public ModelAndView trash(Model model){
        ModelAndView modelAndView = new ModelAndView("archive/rfct/rfct-trash");
        return modelAndView;
    }
    
    /**
     * 分页查询退菜原因类型类型信息
     * @param request
     * @param response
     * @param tbRfct
     * @return
     */
    @RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,TbRfct tbRfct) {
    	com.yqsh.diningsys.core.util.Page<TbRfct> pagebean = null;
		try {
			pagebean = tbRfctService.getPageList(tbRfct);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(tbRfct.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
    
    /**
     * 通过退菜原因类型类型ID查询退菜原因类型类型信息
     * @param request
     * @param response
     * @param tbRfct
     * @return
     */
    @RequestMapping(value = "/getRfctById")
   	@ResponseBody
   	public Object getRfcById(HttpServletRequest request,HttpServletResponse response,TbRfct tbRfct) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			tbRfct.setIsDel("0");//查询未被删除的退菜原因类型类型信息
   			TbRfct rfct = tbRfctService.getRfctById(tbRfct);
   			result.put("rfct", rfct);
   			result.put("success", "OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "error");
   		}
   		return result;
   	}
    
    /**
     * 新增、修改退菜原因类型类型信息保存
     * @param request
     * @param response
     * @param tbRfct
     * @return
     */
    @RequestMapping(value = "/saveRfct")
	@ResponseBody
	public Object saveRfc(HttpServletRequest request,HttpServletResponse response,TbRfct tbRfct) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			tbRfctService.insertOrUpdRfct(tbRfct);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}

    /**
     * 退菜原因类型类型信息回收站
     * @param request
     * @param response
     * @param tbRfct
     * @return
     */
    @RequestMapping(value = "/deleteRfctTrash")
   	@ResponseBody
	public Object deleteRfcTrash(HttpServletRequest request,HttpServletResponse response,TbRfct tbRfct) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbRfct.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbRfct.setIds(list);
   			tbRfctService.deleteTrash(tbRfct);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 还原回收站退菜原因类型类型信息
     * @param request
     * @param response
     * @param tbRfct
     * @return
     */
    @RequestMapping(value = "/replyRfct")
   	@ResponseBody
	public Object replyRfct(HttpServletRequest request,HttpServletResponse response,TbRfct tbRfct) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbRfct.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbRfct.setIds(list);
   			tbRfctService.replyRfct(tbRfct);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 删除退菜原因类型类型信息
     * @param request
     * @param response
     * @param tbRfct
     * @return
     */
    @RequestMapping(value = "/deleteRfct")
   	@ResponseBody
   	public Object deleteRfc(HttpServletRequest request,
   			HttpServletResponse response,TbRfct tbRfct) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbRfct.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbRfct.setIds(list);
   			tbRfctService.deleteByIds(tbRfct);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 导出退菜原因类型类型信息Excel
     * @param request
     * @param response
     * @param model
     * @param tbRfct
     * @return
     */
    @RequestMapping(value="exportXls")
	public ModelAndView exportXls(HttpServletRequest request,HttpServletResponse response,Model model,TbRfct tbRfct){
		List<TbRfct> RfctList = tbRfctService.getAllList(tbRfct);
		model.addAttribute("rfctList", RfctList);
		ModelAndView modelAndView = new ModelAndView("archive/rfct/exportXls");
		return modelAndView;
	}
}
