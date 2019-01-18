package com.yqsh.diningsys.web.controller.inve;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.inve.DgDiscPoint;
import com.yqsh.diningsys.web.model.inve.DgInveItems;
import com.yqsh.diningsys.web.model.inve.DgItemType;
import com.yqsh.diningsys.web.model.inve.DgWarehouse;
import com.yqsh.diningsys.web.service.inve.DgDiscPointService;
import com.yqsh.diningsys.web.service.inve.DgInveItemsService;
import com.yqsh.diningsys.web.service.inve.DgItemTypeService;
import com.yqsh.diningsys.web.service.inve.DgWarehouseService;

import net.sf.json.JSONArray;

/**
 * 盘点控制层
 * @author jianglei
 * 日期 2016年10月26日 下午12:34:49
 *
 */
@Controller
@RequestMapping(value="/inve/discPoint/")
public class DgDiscPointController extends BaseController{
	@Autowired
	private DgDiscPointService discPointService;
	@Autowired
	private DgWarehouseService warehouseService;
	@Autowired
	private DgInveItemsService inveItemsService;
	@Autowired
	private DgItemTypeService itemTypeService;
	/**
	 * 到盘点管理页面
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:07:48
	 * @return
	 */
	@RequestMapping("index")
    public ModelAndView index(ModelMap model){
        ModelAndView modelAndView = new ModelAndView("inve/disc/dgDiscIndex");
      //查询正常的仓库数据
        DgWarehouse ware=new DgWarehouse();
        ware.setState(DgWarehouse.STATE_NORMAL);
        List<DgWarehouse> listWare=warehouseService.findListData(ware);
        model.put("listWare", listWare);
      //物品
        DgInveItems items=new DgInveItems();
        items.setState(DgInveItems.STATE_NORMAL);
        List<DgInveItems> listItems=inveItemsService.findListData(items);
        JSONArray jsonArr=JSONArray.fromObject(listItems);
        model.addAttribute("listItems", jsonArr);
      //物品类型
        DgItemType itemType=new DgItemType();
        itemType.setState(DgItemType.STATE_NORMAL);
        //根据条件查询正常的物品类型
        List<DgItemType> listItemType=itemTypeService.listItemType(itemType);
        model.put("listItemType", listItemType);
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
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgDiscPoint disc) {
    	Page<DgDiscPoint> pagebean = null;
			pagebean = discPointService.getPageList(disc);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(disc.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		return pagebean;
	}
	/**
	 * 盘点数据保存
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:58:36
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "saveDisc")
	@ResponseBody
	public Object saveDisc(HttpServletRequest request, HttpServletResponse response,DgDiscPoint disc,String jsonArr) {
		Map<String, String> result = new HashMap<String, String>();
		SysUser user=(SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
		try{
			if (null == disc.getId() || "".equals(disc.getId().trim())) {
				if(null!=user&&null!=user.getId())disc.setOperUser(user.getId().toString());
				result=discPointService.saveDiscPoint(disc, jsonArr);
			}
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
			e.printStackTrace();
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
	 @RequestMapping(value = "getDepaMateById")
		@ResponseBody
		public Object getProcById(HttpServletRequest request,HttpServletResponse response,DgDiscPoint disc) {
			Map<String,Object> result = new HashMap<String,Object>();
			try{
				DgDiscPoint ware= discPointService.get(disc.getId());
				result.put("supplier", ware);
				result.put("success", "OK");
			}catch(Exception e){
				e.printStackTrace();
				result.put("success", "error");
			}
			return result;
		}
}
