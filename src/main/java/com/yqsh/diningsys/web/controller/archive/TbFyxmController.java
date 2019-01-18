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
import com.yqsh.diningsys.web.model.archive.TbFylx;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.archive.TbFyxm;
import com.yqsh.diningsys.web.service.archive.TbFylxService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.archive.TbFyxmService;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;

/**
 * 费用项目控制器
* @author xiewei
* @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/archive/fyxm")
public class TbFyxmController extends BaseController{
	
	@Autowired
	private TbFyxmService tbFyxmService;
    @Autowired
    private TbFylxService tbFylxService;
    @Autowired
    private TbOrgService tbOrgService;
    @Autowired
    private AutoSeqService seqService;
    @RequestMapping("/index")
    public ModelAndView index(Model model,TbOrg tbOrg,TbFylx tbFylx){
    	//查询机构组织数据
    	model.addAttribute("listOrg",tbOrgService.getAllList(tbOrg)); 
    	//查询费用项目类型数据
    	tbFylx.setIsDel("0");
    	model.addAttribute("listFylx",tbFylxService.getAllList(tbFylx)); 
        ModelAndView modelAndView = new ModelAndView("archive/fyxm/fyxm-index");
        return modelAndView;
    }

    /**
     * 跳转至回收站
     * @param model
     * @return
     */
    @RequestMapping("/trash")
    public ModelAndView trash(Model model){
        ModelAndView modelAndView = new ModelAndView("archive/fyxm/fyxm-trash");
        return modelAndView;
    }
    
    /**
     * 分页查询费用项目信息
     * @param request
     * @param response
     * @param tbFyxm
     * @return
     */
    @RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,TbFyxm tbFyxm) {
    	com.yqsh.diningsys.core.util.Page<TbFyxm> pagebean = null;
		try {
			pagebean = tbFyxmService.getPageList(tbFyxm);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(tbFyxm.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
    
    /**
     * 通过费用项目ID查询费用项目信息
     * @param request
     * @param response
     * @param tbFyxm
     * @return
     */
    @RequestMapping(value = "/getFyxmById")
   	@ResponseBody
   	public Object getFyxmById(HttpServletRequest request,HttpServletResponse response,TbFyxm tbFyxm) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			tbFyxm.setIsDel("0");//查询未被删除的费用项目类型信息
   			TbFyxm fyxm = tbFyxmService.getFyxmById(tbFyxm);
   			result.put("fyxm", fyxm);
   			result.put("success", "OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "error");
   		}
   		return result;
   	}
    
    /**
     * 新增、修改费用项目信息保存
     * @param request
     * @param response
     * @param tbFyxm
     * @return
     */
    @RequestMapping(value = "/saveFyxm")
	@ResponseBody
	public Object saveFyxm(HttpServletRequest request,HttpServletResponse response,TbFyxm tbFyxm) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
                        TbFyxm dbFyxm = tbFyxmService.getFyxmByNum(tbFyxm.getFyxmNum());
                        if(dbFyxm != null){
                            if(dbFyxm.getId() != null && dbFyxm.getId() == tbFyxm.getId()){
                                tbFyxmService.insertOrUpdFyxm(tbFyxm);
                                seqService.setUsedSeq("fyxm", tbFyxm.getFyxmNum());
                                result.put("success", "OK");
                            }else{
                                result.put("success", "false");
                                result.put("error", "编号重复");
                            }
                        }else{
                            tbFyxmService.insertOrUpdFyxm(tbFyxm);
                            seqService.setUsedSeq("fyxm", tbFyxm.getFyxmNum());
                            result.put("success", "OK");
                        }
		}catch(Exception e){
                        e.printStackTrace();
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}

    /**
     * 费用项目信息回收站
     * @param request
     * @param response
     * @param tbFyxm
     * @return
     */
    @RequestMapping(value = "/deleteFyxmTrash")
   	@ResponseBody
	public Object deleteFyxmrash(HttpServletRequest request,HttpServletResponse response,TbFyxm tbFyxm) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbFyxm.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbFyxm.setIds(list);
   			tbFyxmService.deleteTrash(tbFyxm);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 还原回收站费用项目信息
     * @param request
     * @param response
     * @param tbFyxm
     * @return
     */
    @RequestMapping(value = "/replyFyxm")
   	@ResponseBody
	public Object replyFyxm(HttpServletRequest request,HttpServletResponse response,TbFyxm tbFyxm) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbFyxm.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbFyxm.setIds(list);
   			tbFyxmService.replyFyxm(tbFyxm);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 删除费用项目信息
     * @param request
     * @param response
     * @param tbFyxm
     * @return
     */
    @RequestMapping(value = "/deleteFyxm")
   	@ResponseBody
   	public Object deleteFyxm(HttpServletRequest request,
   			HttpServletResponse response,TbFyxm tbFyxm) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			List<String> list = new ArrayList<String>();
   			for(String str : tbFyxm.getEditIds().split(",")){
   				list.add(str);
   			}
   			tbFyxm.setIds(list);
   			tbFyxmService.deleteByIds(tbFyxm);
   			result.put("success","OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "false");
   		}
   		return result;
   	}
    
    /**
     * 导出费用项目信息Excel
     * @param request
     * @param response
     * @param model
     * @param tbFyxm
     * @return
     */
    @RequestMapping(value="exportXls")
	public ModelAndView exportXls(HttpServletRequest request,HttpServletResponse response,Model model,TbFyxm tbFyxm){
		List<TbFyxm> fyxmList = tbFyxmService.getAllList(tbFyxm);
		model.addAttribute("fyxmList", fyxmList);
		ModelAndView modelAndView = new ModelAndView("archive/fyxm/exportXls");
		return modelAndView;
	}
}
