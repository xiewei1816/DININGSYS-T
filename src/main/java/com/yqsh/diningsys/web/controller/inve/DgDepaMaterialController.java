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
import com.yqsh.diningsys.web.dao.archive.TbDepMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.TbDep;
import com.yqsh.diningsys.web.model.inve.DgDepaMaterial;
import com.yqsh.diningsys.web.model.inve.DgInveItems;
import com.yqsh.diningsys.web.model.inve.DgItemType;
import com.yqsh.diningsys.web.model.inve.DgWarehouse;
import com.yqsh.diningsys.web.service.inve.DgDepaMaterialService;
import com.yqsh.diningsys.web.service.inve.DgInveItemsService;
import com.yqsh.diningsys.web.service.inve.DgItemTypeService;
import com.yqsh.diningsys.web.service.inve.DgWarehouseService;

import net.sf.json.JSONArray;

/**
 * 部门用料控制层
 * @author jianglei
 * 日期 2016年10月25日 上午10:14:04
 *
 */
@Controller
@RequestMapping(value="/inve/depaMate/")
public class DgDepaMaterialController {
	@Autowired
	private DgDepaMaterialService depaMaterialService;
    @Autowired
	private DgWarehouseService warehouseService;
	@Autowired
	private TbDepMapper depMapper;
	@Autowired
	private DgInveItemsService inveItemsService;
	@Autowired
	private DgItemTypeService itemTypeService;
	/**
	 * 到部门用料管理页面
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:07:48
	 * @return
	 */
	@RequestMapping("index")
    public ModelAndView index(ModelMap model){
        ModelAndView modelAndView = new ModelAndView("inve/depaMate/dgdepaMateIndex");
        DgWarehouse ware=new DgWarehouse();
        ware.setState(DgWarehouse.STATE_NORMAL);
        //查询正常的仓库数据
        List<DgWarehouse> listWare=warehouseService.findListData(ware);
        model.put("listWare", listWare);
        TbDep dep=new TbDep();
        dep.setIsDel("0"); //0表示未删除
        //部门
        List<TbDep> listDep=depMapper.getAllList(dep);
        model.put("listDep", listDep);
        DgInveItems items=new DgInveItems();
        items.setState(DgInveItems.STATE_NORMAL);
        //物品
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
	public Object getPageList(DgDepaMaterial depaMate) {
    	Page<DgDepaMaterial> pagebean = null;
			pagebean = depaMaterialService.getPageList(depaMate);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(depaMate.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		return pagebean;
	}
	/**
	 * 部门用料管理
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:58:36
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "depaMateSave")
	@ResponseBody
	public Object depaMateSave(HttpServletRequest request, HttpServletResponse response,DgDepaMaterial depaMate,String jsonArr) {
		Map<String, String> result = new HashMap<String, String>();
		SysUser user=(SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
		try{
			if (null == depaMate.getId() || "".equals(depaMate.getId().trim())) {
				if(null!=user&&null!=user.getId())depaMate.setOperUser(user.getId().toString());
				result=depaMaterialService.depaMeteInsert(depaMate, jsonArr);
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
		public Object getProcById(HttpServletRequest request,HttpServletResponse response,DgDepaMaterial depaMate) {
			Map<String,Object> result = new HashMap<String,Object>();
			try{
				DgDepaMaterial ware= depaMaterialService.get(depaMate.getId());
				result.put("supplier", ware);
				result.put("success", "OK");
			}catch(Exception e){
				e.printStackTrace();
				result.put("success", "error");
			}
			return result;
		}
}
