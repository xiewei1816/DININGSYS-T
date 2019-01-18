package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemSaleLimitSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemSaleLimitService;

@Controller
@RequestMapping("/DgItemSaleLimit")
public class DgItemSaleLimitController extends BaseController {
	@Autowired
	private DgItemSaleLimitService dgItemSaleLimitService;
	@Autowired
	private DgItemSaleLimitSService dgItemSaleLimitSService;

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		DgItemSaleLimit s = dgItemSaleLimitService.selectByDate(new Date()); // 根据事件获取主表id
		if (s == null) {
			request.setAttribute("type", 1);
		} else {
			request.setAttribute("type", s.getType());
		}
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/sales_limited");
		return model;
	}

	@RequestMapping("/getAllData")
	@ResponseBody
	public Object getAllData(HttpServletRequest request,
			HttpServletResponse response) {
		String name = request.getParameter("name");
		String itemCode = request.getParameter("itemCode");
		DgItemSaleLimit s = dgItemSaleLimitService.selectByDate(new Date()); // 根据事件获取主表id
		if (s == null) {
			return null;
		} else {
			Map<String, Object> record = new HashMap<String, Object>();
			record.put("name", name);
			record.put("itemCode", itemCode);
			record.put("limitId", s.getId());
			List<DgItemSaleLimitS> ret = dgItemSaleLimitSService
					.getAllData(record);
			return ret;
		}
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/toAddPlanDish")
	public ModelAndView toAddPlanDish(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/un_disable_add");
		return model;
	}

	@RequestMapping(value = "/getMealTimeAddDish")
	@ResponseBody
	public Object getMealTimeAddDish(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String chooseTrIds = request.getParameter("chooseTrIds");
		if (!StringUtils.isEmpty(chooseTrIds)) {
			List<Integer> ids = new ArrayList<Integer>();

			String str[] = chooseTrIds.split(",");
			for (int i = 0; i < str.length; i++) {
				ids.add(Integer.parseInt(str[i]));
			}
			if (ids.size() > 0) {
				List<DgItemSaleLimitS> list2 = dgItemSaleLimitSService
						.selectItemByAdd(ids);
				for (DgItemSaleLimitS item : list2) {
					item.setId(-item.getItemId()); // 设置成负数,不与本地重复
				}
				ret.put("success", true);
				ret.put("list", list2);
			} else {
				ret.put("success", true);
				ret.put("list", null);
			}
			return ret;
		}
		ret.put("success", true);
		ret.put("list", null);
		return ret;
	}

	@RequestMapping("/saveData")
	@ResponseBody
	public ResultInfo saveData(String data) {
		int relust=dgItemSaleLimitService.saveItemSaleLimit(data);
		if(relust>0){
			return returnSuccess();
		}
		return returnFail();
	}

	/**
	 * 
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */

	@RequestMapping("/delData")
	@ResponseBody
	public Object delData(String ids) {
		Map<String, Object> ret = new HashMap<String, Object>();
		dgItemSaleLimitSService.deleteIds(ids);
		ret.put("success", true);
		return ret;
	}
}