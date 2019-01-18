package com.yqsh.diningsys.web.controller.inve;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.inve.DgItemType;
import com.yqsh.diningsys.web.service.inve.DgItemTypeService;

/**
 * 物品类型控制层
 * @author jianglei
 * 日期 2016年10月19日 上午9:07:50
 *
 */
@Controller
@RequestMapping(value="/inve/itemType/")
public class DgItemTypeController {
	@Autowired
	private DgItemTypeService itemTypeService;
	/**
	 * 到物品类型管理页面
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:07:48
	 * @return
	 */
	@RequestMapping("index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("inve/itemType/dgItemTypeIndex");
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
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgItemType itemType) {
    	Page<DgItemType> pagebean = null;
			pagebean = itemTypeService.getPageList(itemType);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(itemType.getPage());
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
	@RequestMapping(value = "saveItemType")
	@ResponseBody
	public Object saveItemType(HttpServletRequest request, HttpServletResponse response, DgItemType itemType) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "false");
		SysUser user = (SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
		try {
			synchronized (itemType) {
				// 根据编码获取信息
				List<DgItemType> ity = itemTypeService.listItemType(itemType);
				if (null != itemType && StringUtils.isBlank(itemType.getId()) && null != ity && ity.size() > 0) {
					result.put("error", "编码重复，请重新输入!");
					return result;
				}
				if (null != itemType && StringUtils.isNotBlank(itemType.getId()) && null != ity && ity.size() > 0) {
					DgItemType itype = itemTypeService.get(itemType.getId());
					if (!itype.getItemTypeNo().equals(ity.get(0).getItemTypeNo())) {
						result.put("error", "编码重复，请重新输入!");
						return result;
					}
				}
				if (StringUtils.isBlank(itemType.getId())) {
					itemType.setId(UUID.randomUUID().toString());
					if (null != user && null != user.getId())
						itemType.setOperUser(user.getId().toString());
					itemType.setState(DgItemType.STATE_NORMAL);
					itemType.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
					itemTypeService.insert(itemType);
				} else {
					itemTypeService.update(itemType);
				}
			}

			result.put("success", "OK");
		} catch (Exception e) {
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
	 @RequestMapping(value = "getItemTypeById")
		@ResponseBody
		public Object getItemTypeById(HttpServletRequest request,HttpServletResponse response,DgItemType itemType) {
			Map<String,Object> result = new HashMap<String,Object>();
			try{
				DgItemType ware= itemTypeService.get(itemType.getId());
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
	  @RequestMapping(value = "deleteItemType")
	  @ResponseBody
	  public Object deleteItemType(HttpServletRequest request,
			 HttpServletResponse response,DgItemType itemType) {
		  Map<String,Object> result = new HashMap<String,Object>();
		  try{
			  	List<String> listIds = new ArrayList<String>();
				for(String str : itemType.getEditIds().split(",")){
					listIds.add(str);
				}
				itemTypeService.delete(listIds,DgItemType.STATE_DEL);
			  result.put("success","OK");
		  }catch(Exception e){
			  e.printStackTrace();
			  result.put("success", "false");
		  }
		  return result;
	  }
}
