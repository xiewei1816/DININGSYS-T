package com.yqsh.diningsys.web.controller.pay;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.pay.DgMerchants;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.pay.DgMerchantsService;
@Controller
@RequestMapping(value="/pay/merch/")
public class DgMerchantsController {
	@Autowired
	private DgMerchantsService merchantsService;
	@Autowired
	private TbOrgService orgService;
	
	/**
	 * 到采购管理页面
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:07:48
	 * @return
	 */
	@RequestMapping("index")
    public String index(ModelMap model){
		model.put("listOrg", orgService.selectAllTbOrg());
        return "/pay/merch/query";
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
	public Object getPageList(DgMerchants merch) {
    	Page<DgMerchants> pagebean = null;
			pagebean = merchantsService.getPageList(merch);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(merch.getPage());
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
	@RequestMapping(value = "save")
	@ResponseBody
	public Object saveItemType(HttpServletRequest request, DgMerchants merch) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "false");
		SysUser user = (SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
		try {
				if (StringUtils.isBlank(merch.getNickName())) {
						result.put("error", "请输入名称!");
						return result;
				}
				if (StringUtils.isBlank(merch.getId())) {
					merch.setId(UUID.randomUUID().toString());
					if (null != user && null != user.getId())merch.setOperUser(user.getId().toString());
					merch.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
					merchantsService.insert(merch);
				} else {
					if (null != user && null != user.getId())merch.setOperUser(user.getId().toString());
					merch.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
					merchantsService.update(merch);
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
	 @RequestMapping(value = "getModel")
		@ResponseBody
		public Object getItemTypeById(HttpServletRequest request,HttpServletResponse response,DgMerchants merch) {
			Map<String,Object> result = new HashMap<String,Object>();
			try{
				DgMerchants dmerch= merchantsService.get(merch.getId());
				result.put("obj", dmerch);
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
	  @RequestMapping(value = "delete")
	  @ResponseBody
	  public Object deleteItemType(DgMerchants merch) {
		  Map<String,Object> result = new HashMap<String,Object>();
		  try{
			  	List<String> listIds = new ArrayList<String>();
				for(String str : merch.getEditIds().split(",")){
					listIds.add(str);
				}
				merchantsService.delBatch(listIds);
			  result.put("success","OK");
		  }catch(Exception e){
			  e.printStackTrace();
			  result.put("success", "false");
		  }
		  return result;
	  }
}
