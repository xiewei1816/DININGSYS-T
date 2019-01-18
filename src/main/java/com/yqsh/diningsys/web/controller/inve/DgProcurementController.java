package com.yqsh.diningsys.web.controller.inve;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.inve.*;
import com.yqsh.diningsys.web.service.inve.*;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购管理控制层
 *
 * @author jianglei
 * 日期 2016年10月21日 下午3:02:03
 */
@Controller
@RequestMapping(value = "/inve/proc/")
public class DgProcurementController extends BaseController{
    @Autowired
    private DgProcurementService procurementService;
    @Autowired
    private DgWarehouseService warehouseService;
    @Autowired
    private DgSupplierService supplierService;
    @Autowired
    private DgInveItemsService inveItemsService;
    @Autowired
    private DgItemTypeService itemTypeService;

    /**
     * 到采购管理页面
     *
     * @return
     * @author jianglei
     * 日期 2016年10月17日 下午1:07:48
     */
    @RequestMapping("index")
    public ModelAndView index(ModelMap model) {
        ModelAndView modelAndView = new ModelAndView("inve/procurement/dgProcurementIndex");
        DgWarehouse ware = new DgWarehouse();
        ware.setState(DgWarehouse.STATE_NORMAL);
        //查询正常的仓库数据
        List<DgWarehouse> listWare = warehouseService.findListData(ware);
        model.put("listWare", listWare);
        DgSupplier supplier = new DgSupplier();
        supplier.setState(DgSupplier.STATE_NORMAL);
        //供应商
        List<DgSupplier> listSupperlier = supplierService.findListData(supplier);
        model.put("listSupplier", listSupperlier);
        DgInveItems items = new DgInveItems();
        items.setState(DgInveItems.STATE_NORMAL);
        //物品
        List<DgInveItems> listItems = inveItemsService.findListData(items);
        JSONArray jsonArr = JSONArray.fromObject(listItems);
        model.addAttribute("listItems", jsonArr);
        //物品类型
        DgItemType itemType = new DgItemType();
        itemType.setState(DgItemType.STATE_NORMAL);
        //根据条件查询正常的物品类型
        List<DgItemType> listItemType = itemTypeService.listItemType(itemType);
        model.put("listItemType", listItemType);
        return modelAndView;
    }

    /**
     * 分页带参查询信息
     *
     * @param request
     * @param response
     * @param supplier
     * @return
     * @author jianglei
     * 日期 2016年10月17日 下午1:08:08
     */
    @RequestMapping(value = "getPageList")
    @ResponseBody
    public Object getPageList(DgProcurement proc) {
        Page<DgProcurement> pagebean = null;
        pagebean = procurementService.getPageList(proc);
        pagebean.setTotal(pagebean.getContext().getPageCount());
        pagebean.setPage(proc.getPage());
        pagebean.setRecords(pagebean.getContext().getTotal());
        return pagebean;
    }

    /**
     * 采购入库
     *
     * @param request
     * @param response
     * @param sysUser
     * @return
     * @author jianglei
     * 日期 2016年10月17日 下午1:58:36
     */
    @RequestMapping(value = "procSave")
    @ResponseBody
    public Object procSave(HttpServletRequest request, DgProcurement proc, String jsonArr) {
        Map<String, String> result = new HashMap<String, String>();
        SysUser user = (SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
        try {
            if (null == proc.getId() || "".equals(proc.getId().trim())) {
                if (null != user && null != user.getId()) proc.setOperUser(user.getId().toString());
                result = procurementService.insertProc(proc, jsonArr);
            }
        } catch (Exception e) {
            result.put("success", "false");
            result.put("error", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 快速入库save
     */
    @RequestMapping(value = "fastStorage")
    @ResponseBody
    public ResultInfo fastStorage(DgProcurement proc) {
        try {
            SysUser currentUser = getCurrentUser();
            if(StringUtil.isEmpty(proc.getId())){
                proc.setOperUser(currentUser.getId().toString());
            }
            procurementService.insertFastStorage(proc);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    /**
     * 根据id获取信息
     *
     * @param request
     * @param response
     * @param supplier
     * @return
     * @author jianglei
     * 日期 2016年10月17日 下午2:10:29
     */
    @RequestMapping(value = "getProcById")
    @ResponseBody
    public Object getProcById(DgProcurement proc) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            DgProcurement ware = procurementService.get(proc.getId());
            result.put("supplier", ware);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "error");
        }
        return result;
    }

    /**
     * 返回物品信息
     *
     * @return
     * @author jianglei
     * 日期 2016年10月31日 下午3:34:23
     */
    @RequestMapping(value = "getListItems")
    @ResponseBody
    public List<DgInveItems> getListItems(DgInveItems item) {
        item.setState(DgInveItems.STATE_NORMAL);
        List<DgInveItems> listItem = inveItemsService.findListData(item);
        return listItem;
    }

    @RequestMapping("fastStorageIndex")
    public String fastStorageIndex(Model model){
        DgWarehouse ware = new DgWarehouse();
        ware.setState(DgWarehouse.STATE_NORMAL);
        List<DgWarehouse> listWare = warehouseService.findListData(ware);
        model.addAttribute("listWare", listWare);
        DgSupplier supplier = new DgSupplier();
        supplier.setState(DgSupplier.STATE_NORMAL);
        List<DgSupplier> listSupperlier = supplierService.findListData(supplier);
        model.addAttribute("listSupplier", listSupperlier);
        DgInveItems items = new DgInveItems();
        items.setState(DgInveItems.STATE_NORMAL);
        return "inve/procurement/fastStorage";
    }

    @ResponseBody
    @RequestMapping("selectInveItemByCode/{code}")
    public DgInveItems selectInveItemByCode(@PathVariable String code){
        return inveItemsService.selectInveItemByCode(code);
    }

}
