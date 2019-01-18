package com.yqsh.diningsys.web.controller.inve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.inve.DgSupplier;
import com.yqsh.diningsys.web.service.inve.DgSupplierService;
/**
 * 供应商控制层
 * @author jianglei
 * 日期 2016年10月17日 上午10:05:27
 *
 */
@Controller
@RequestMapping(value="/inve/dgSupplier/")
public class DgSupplierController extends BaseController{
	@Autowired
	private DgSupplierService supplierService;
	
	
	/**
	 * 到供应商管理页面
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:07:48
	 * @return
	 */
	@RequestMapping("index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("inve/supplier/dgSupplierIndex");
        return modelAndView;
    }
	/**
	 * 分页带参查询供应商信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:08:08
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = "getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgSupplier supplier) {
    	Page<DgSupplier> pagebean = null;
			pagebean = supplierService.getPageList(supplier);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(supplier.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		return pagebean;
	}
	/**
	 * 保存供应商
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:58:36
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "saveSupplier")
	@ResponseBody
	public Object saveSupplier(HttpServletRequest request, HttpServletResponse response, DgSupplier supplier) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysUser user=(SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
		try{
			if (null == supplier.getId() || "".equals(supplier.getId().trim())) {
				if(null!=user&&null!=user.getId())supplier.setOperUser(user.getId().toString());
				supplierService.insert(supplier);
			} else {
				supplierService.update(supplier);
			}
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}
	/**
	 * 根据id获取供应商信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:10:29
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 */
	 @RequestMapping(value = "getSupplierById")
		@ResponseBody
		public Object getSupplierById(HttpServletRequest request,HttpServletResponse response,DgSupplier supplier) {
			Map<String,Object> result = new HashMap<String,Object>();
			try{
				DgSupplier sup= supplierService.get(supplier.getId());
				result.put("supplier", sup);
				result.put("success", "OK");
			}catch(Exception e){
				e.printStackTrace();
				result.put("success", "error");
			}
			return result;
		}
	 /**
	  * 根据id上次供应商信息
	  * @author jianglei
	  * 日期 2016年10月17日 下午2:13:00
	  * @param request
	  * @param response
	  * @param supplier
	  * @return
	  */
	  @RequestMapping(value = "deleteSupplier")
	  @ResponseBody
	  public Object deleteSupplier(HttpServletRequest request,
			 HttpServletResponse response,DgSupplier supplier) {
		  Map<String,Object> result = new HashMap<String,Object>();
		  try{
			  	List<String> listIds = new ArrayList<String>();
				for(String str : supplier.getEditIds().split(",")){
					listIds.add(str);
				}
			  supplierService.delete(listIds,DgSupplier.STATE_DEL);
			  result.put("success","OK");
		  }catch(Exception e){
			  e.printStackTrace();
			  result.put("success", "false");
		  }
		  return result;
	  }
}
