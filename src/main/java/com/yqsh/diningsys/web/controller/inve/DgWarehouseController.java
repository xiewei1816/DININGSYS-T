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
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.inve.DgSupplier;
import com.yqsh.diningsys.web.model.inve.DgWarehouse;
import com.yqsh.diningsys.web.service.inve.DgWarehouseService;

/**
 * 仓库管理控制层
 * @author jianglei
 * 日期 2016年10月18日 下午2:39:15
 *
 */
@Controller
@RequestMapping(value="/inve/warehouse/")
public class DgWarehouseController {
	@Autowired
	private DgWarehouseService warehouseService;
	
	/**
	 * 到仓库管理页面
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:07:48
	 * @return
	 */
	@RequestMapping("index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("inve/warehouse/dgWarehouseIndex");
        return modelAndView;
    }
	/**
	 * 分页带参查询信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:08:08
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = "getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgWarehouse warehouse) {
    	Page<DgWarehouse> pagebean = null;
			pagebean = warehouseService.getPageList(warehouse);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(warehouse.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		return pagebean;
	}
	/**
	 * 保存
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:58:36
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "saveWarehouse")
	@ResponseBody
	public Object saveWarehouse(HttpServletRequest request, HttpServletResponse response, DgWarehouse warehouse) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysUser user=(SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
		try{
			if (null == warehouse.getId() || "".equals(warehouse.getId().trim())) {
				if(null!=user&&null!=user.getId())warehouse.setOperUser(user.getId().toString());
				warehouseService.insert(warehouse);
			} else {
				warehouseService.update(warehouse);
			}
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:10:29
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 */
	 @RequestMapping(value = "getWarehouseById")
		@ResponseBody
		public Object getWarehouseById(HttpServletRequest request,HttpServletResponse response,DgWarehouse warehouse) {
			Map<String,Object> result = new HashMap<String,Object>();
			try{
				DgWarehouse ware= warehouseService.get(warehouse.getId());
				result.put("supplier", ware);
				result.put("success", "OK");
			}catch(Exception e){
				e.printStackTrace();
				result.put("success", "error");
			}
			return result;
		}
	 /**
	  * 根据id删除
	  * @author jianglei
	  * 日期 2016年10月17日 下午2:13:00
	  * @param request
	  * @param response
	  * @param supplier
	  * @return
	  */
	  @RequestMapping(value = "deleteWarehouse")
	  @ResponseBody
	  public Object deleteWarehouse(HttpServletRequest request,
			 HttpServletResponse response,DgSupplier supplier) {
		  Map<String,Object> result = new HashMap<String,Object>();
		  try{
			  	List<String> listIds = new ArrayList<String>();
				for(String str : supplier.getEditIds().split(",")){
					listIds.add(str);
				}
				warehouseService.delete(listIds,DgWarehouse.STATE_DEL);
			  result.put("success","OK");
		  }catch(Exception e){
			  e.printStackTrace();
			  result.put("success", "false");
		  }
		  return result;
	  }
}
