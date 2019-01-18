package com.yqsh.diningsys.web.controller.inve;

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

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;
import com.yqsh.diningsys.web.model.inve.DgInveItems;
import com.yqsh.diningsys.web.model.inve.DgInventory;
import com.yqsh.diningsys.web.model.inve.DgItemType;
import com.yqsh.diningsys.web.model.inve.DgWarehouse;
import com.yqsh.diningsys.web.service.inve.DgInventoryService;
import com.yqsh.diningsys.web.service.inve.DgWarehouseService;

/**
 * 仓库管理控制层
 * @author jianglei
 * 日期 2016年10月19日 下午5:12:47
 *
 */
@Controller
@RequestMapping(value="/inve/inventory/")
public class DgInventoryController {
	@Autowired
	private DgInventoryService inventoryService;
	@Autowired
	private DgWarehouseService warehouseService;
	/**
	 * 到物品管理页面
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:07:48
	 * @return
	 */
	@RequestMapping("index")
    public ModelAndView index(ModelMap model){
        ModelAndView modelAndView = new ModelAndView("inve/invetory/dgInventoryIndex");
      //查询正常的仓库数据
        DgWarehouse ware=new DgWarehouse();
        ware.setState(DgWarehouse.STATE_NORMAL);
        List<DgWarehouse> listWare=warehouseService.findListData(ware);
        model.put("listWare", listWare);
        DgItemType itemType=new DgItemType();
        itemType.setState(DgItemType.STATE_NORMAL);
        //根据条件查询正常的物品类型
       // List<DgItemType> listItemType=itemTypeService.listItemType(itemType);
     //   model.put("listItemType", listItemType);
        //根据相关条件查询公共代码中的物品单位
        DgPublicCode publicCode=new DgPublicCode();
        publicCode.setCparent(DgInveItems.UNIT_PUBLIC_CODE_PARENT);
      //  List<DgPublicCode> listCode=publicCodeService.findListData(publicCode);
      //  model.put("listCode", listCode);
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
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgInventory inventory) {
    	Page<DgInventory> pagebean = null;
			pagebean = inventoryService.getPageList(inventory);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(inventory.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		return pagebean;
	}
	/**
	 * 导出
	 * @author jianglei
	 * 日期 2016年10月31日 上午11:22:46
	 * @param model
	 * @param inventory
	 * @return
	 */
	@RequestMapping(value="exportXls")
	public String exportXls(ModelMap model,DgInventory inventory){
		List<DgInventory> listInve=inventoryService.exportXls(inventory);
		model.put("inve", listInve);
		return "inve/invetory/exportXls";
	}
	/**
	 * 导出
	 * @author jianglei
	 * 日期 2016年10月31日 上午11:22:46
	 * @param model
	 * @param inventory
	 * @return
	 */
	@RequestMapping(value="printDocu")
	public String printDocu(ModelMap model,DgInventory inventory){
		List<DgInventory> listInve=inventoryService.exportXls(inventory);
		model.put("inve", listInve);
		return "inve/invetory/printDocu";
	}
	/**
	 * 根据仓库或物品类型获取仓库所属物品
	 * @author jianglei
	 * 日期 2016年11月4日 上午9:53:45
	 * @return
	 */
	@RequestMapping(value="ajaxInveItems")
	@ResponseBody
	public List<Map<String,Object>> ajaxInveItems(DgInventory inve,String itemTypeId){
		List<Map<String,Object>> listInve=inventoryService.ajaxInveItems(inve, itemTypeId);
		return listInve;
	}
}
