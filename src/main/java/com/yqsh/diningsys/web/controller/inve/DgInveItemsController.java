package com.yqsh.diningsys.web.controller.inve;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yqsh.diningsys.web.model.inve.DgWarehouse;
import com.yqsh.diningsys.web.service.inve.DgWarehouseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PinYinUtil;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.inve.DgInveItems;
import com.yqsh.diningsys.web.model.inve.DgItemType;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.inve.DgInveItemsService;
import com.yqsh.diningsys.web.service.inve.DgItemTypeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 物品管理控制层
 * @author jianglei
 * 日期 2016年10月19日 上午10:50:11
 *
 */
@Controller
@RequestMapping(value="/inve/items/")
public class DgInveItemsController {
	@Autowired
	private DgInveItemsService inveItemsService;
	@Autowired
	private DgItemTypeService itemTypeService;
//	@Autowired
//	private PublicCodeService publicCodeService;
    @Autowired
    private DgPublicCode0Service dgPublicCode0Service;
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
        ModelAndView modelAndView = new ModelAndView("inve/inveItems/dgInveItemsIndex");
        DgItemType itemType=new DgItemType();
        itemType.setState(DgItemType.STATE_NORMAL);
        //根据条件查询正常的物品类型
        List<DgItemType> listItemType=itemTypeService.listItemType(itemType);
        model.put("listItemType", listItemType);
		DgWarehouse h = new DgWarehouse();
		h.setState("0");
		List<DgWarehouse> wareHouses = warehouseService.findListData(h);
		model.put("houses",wareHouses);
//        //根据相关条件查询公共代码中的物品单位
//        DgPublicCode publicCode=new DgPublicCode();
//        publicCode.setCparent(DgInveItems.UNIT_PUBLIC_CODE_PARENT);
//        List<DgPublicCode> listCode=publicCodeService.findListData(publicCode);
//        model.put("listCode", listCode);
    	//查询字典数据
    	model.addAttribute("list",dgPublicCode0Service.getAllDpcToPage()); 
        return modelAndView;
    }
	/**
	 * 分页带参查询信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:08:08
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgInveItems items) {
    	Page<DgInveItems> pagebean = null;
			pagebean = inveItemsService.getPageList(items);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(items.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		return pagebean;
	}
	/**
	 * 保存
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:58:36
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveInveItems")
	@ResponseBody
	public Object saveInveItems(HttpServletRequest request, HttpServletResponse response,DgInveItems items) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "false");
		SysUser user=(SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
		try{
			if(null!=items&&StringUtils.isNotBlank(items.getItemName()))
				items.setSpellCode(PinYinUtil.getFirstSpellUpperCase(items.getItemName()));
			synchronized(items){
				items.setState(DgItemType.STATE_NORMAL);
				DgInveItems dii=new DgInveItems();
				dii.setItemNo(items.getItemNo());
				List<DgInveItems> listItems=inveItemsService.findListData(dii);
				if(null!=items&&StringUtils.isBlank(items.getId())&&null!=listItems&&listItems.size()>0){
					result.put("error", "编码已存在，请重新输入");
					return result;
				}
				if(null!=items&&StringUtils.isNotBlank(items.getId())&&null!=listItems&&listItems.size()>0){
					DgInveItems inveItems=inveItemsService.get(items.getId());
					if(!inveItems.getItemNo().equals(listItems.get(0).getItemNo())){
						result.put("error", "编码已存在，请重新输入");
						return result;
					}
				}
				if (StringUtils.isBlank(items.getId())) {
					items.setId(UUID.randomUUID().toString());
					if(null!=user&&null!=user.getId())items.setOperUser(user.getId().toString());
					items.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
					inveItemsService.insert(items);
				} else {
						inveItemsService.update(items);
				}
				result.put("success", "OK");
			}
		}catch(Exception e){
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
	 * @return
	 */
	 @RequestMapping(value = "getInveItemsById")
		@ResponseBody
		public Object getInveItemsById(HttpServletRequest request,HttpServletResponse response,DgInveItems items) {
			Map<String,Object> result = new HashMap<String,Object>();
			try{
				DgInveItems ware= inveItemsService.get(items.getId());
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
	  @RequestMapping(value = "deleteInveItems")
	  @ResponseBody
	  public Map<String,Object> deleteInveItems(HttpServletRequest request,
			 HttpServletResponse response,String objJson) {
		  List<Map<String,String>> listMap=new ArrayList<Map<String,String>>();
		  JSONArray jsonArr=JSONArray.fromObject(objJson);
		  for (Object obj : jsonArr) {
			  JSONObject json=JSONObject.fromObject(obj);
			  Map<String,String> mapStr=new HashMap<String,String>();
				mapStr.put("id",json.getString("id"));
				mapStr.put("itemName","$"+json.getString("itemName")+"$");
				listMap.add(mapStr);
		  }
		 Map<String,Object> result = new HashMap<String,Object>();
		  try{
			  inveItemsService.delete(listMap);
			  result.put("success","OK");
		  }catch(Exception e){
			  e.printStackTrace();
			  result.put("success", "false");
			  result.put("error", e.getMessage());
		  }
		  return result;
	  }
}
