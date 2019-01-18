package com.yqsh.diningsys.web.controller.businessMan;
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
import com.yqsh.diningsys.web.model.archive.TbFylx;
import com.yqsh.diningsys.web.model.archive.TbFyxm;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.businessMan.TbFydj;
import com.yqsh.diningsys.web.service.archive.TbFylxService;
import com.yqsh.diningsys.web.service.archive.TbFyxmService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.businessMan.TbFydjService;

/**
 * 费用登记控制器
* @author xiewei
* @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/businessMan/fydj")
public class TbFydjController extends BaseController{

    @Autowired
    private TbFydjService tbFydjService;
    @Autowired
    private TbOrgService tbOrgService;
    @Autowired
    private TbFyxmService tbFyxmService;
    @Autowired
    private TbFylxService tbFylxService;

    @RequestMapping("/index")
    public ModelAndView index(Model model,TbOrg tbOrg,TbFydj tbFydj,TbFyxm tbFyxm){
    	model.addAttribute("listOrg",tbOrgService.getAllList(tbOrg)); //查询机构组织数据
    	model.addAttribute("listFydj",tbFydjService.getAllList(tbFydj)); //查询费用登记类型数据
    	tbFyxm.setIsDel("0");
    	model.addAttribute("listFyxm",tbFyxmService.getAllList(tbFyxm)); //查询费用项目数据
        ModelAndView modelAndView = new ModelAndView("bussinessMan/fydj/fydj-index");
        return modelAndView;
    }
    
    /**
     * 分页查询费用登记信息
     * @param request
     * @param response
     * @param tbFydj
     * @return
     */
    @RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,TbFydj tbFydj) {
    	com.yqsh.diningsys.core.util.Page<TbFydj> pagebean = null;
		try {
			pagebean = tbFydjService.getPageList(tbFydj);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(tbFydj.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
    
    /**
     * 通过费用登记ID查询费用登记信息
     * @param request
     * @param response
     * @param tbFydj
     * @return
     */
    @RequestMapping(value = "/getFydjById")
   	@ResponseBody
   	public Object getFydjById(HttpServletRequest request,HttpServletResponse response,TbFydj tbFydj) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			TbFydj fydj = tbFydjService.getFydjById(tbFydj);
   			result.put("fydj", fydj);
   			result.put("success", "OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "error");
   		}
   		return result;
   	}
    
    /**
     * 新增、修改费用登记信息保存
     * @param request
     * @param response
     * @param tbFydj
     * @return
     */
    @RequestMapping(value = "/saveFydj")
	@ResponseBody
	public Object saveFydj(TbFydj tbFydj,TbFyxm tbFyxm,TbFylx tbFylx) {
		Map<String,Object> result = new HashMap<String,Object>();
		String fydjType = "" ;  //初始化登记类型
		//根据费用项目ID查询费用类型ID
		tbFyxm.setId(Integer.parseInt(tbFydj.getFydjName()));
		TbFyxm fyxm = tbFyxmService.getFyxmById(tbFyxm);
		
		if(fyxm != null){
			//根据费用类型ID查询费用类型
			tbFylx.setId(Integer.parseInt(fyxm.getFyxmType()));
			TbFylx fylx = tbFylxService.getFylxById(tbFylx);
			fydjType = fylx.getfylxName();
		}
		try{
			tbFydj.setFydjType(fydjType);
			tbFydjService.insertOrUpdFydj(tbFydj);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}

    /**
     * 费用登记信息回收站
     * @param request
     * @param response
     * @param tbFydj
     * @return
     */
    @RequestMapping(value = "/deleteFydjTrash")
   	@ResponseBody
	public Object deleteFydjrash(HttpServletRequest request,HttpServletResponse response,TbFydj tbFydj) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbFydj.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbFydj.setIds(list);
   			tbFydjService.deleteTrash(tbFydj);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 还原回收站费用登记信息
     * @param request
     * @param response
     * @param tbFydj
     * @return
     */
    @RequestMapping(value = "/replyFydj")
   	@ResponseBody
	public Object replyFydj(HttpServletRequest request,HttpServletResponse response,TbFydj tbFydj) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbFydj.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbFydj.setIds(list);
   			tbFydjService.replyFydj(tbFydj);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 删除费用登记信息
     * @param request
     * @param response
     * @param tbFydj
     * @return
     */
    @RequestMapping(value = "/deleteFydj")
   	@ResponseBody
   	public Object deleteFydj(HttpServletRequest request,
   			HttpServletResponse response,TbFydj tbFydj) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbFydj.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbFydj.setIds(list);
   			tbFydjService.deleteByIds(tbFydj);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 导出费用登记信息Excel
     * @param request
     * @param response
     * @param model
     * @param tbFydj
     * @return
     */
    @RequestMapping(value="exportXls")
	public ModelAndView exportXls(HttpServletRequest request,HttpServletResponse response,Model model,TbFydj tbFydj){
		List<TbFydj> fydjList = tbFydjService.getAllList(tbFydj);
		model.addAttribute("fydjList", fydjList);
		ModelAndView modelAndView = new ModelAndView("bussinessMan/fydj/exportXls");
		return modelAndView;
	}
}
