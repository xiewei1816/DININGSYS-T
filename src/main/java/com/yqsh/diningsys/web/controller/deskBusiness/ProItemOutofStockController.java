package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.ArrayList;
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
import com.yqsh.diningsys.web.model.deskBusiness.DgItemOutofStock;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemOutofStockService;

/*
 * 
 * 品项沽清
 */
@Controller
@RequestMapping("/ProItemOutofStock")
public class ProItemOutofStockController extends BaseController {
	@Autowired
	private DgItemOutofStockService dgItemOutofStockService;

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/item_outof_stock");
		return model;
	}

	@RequestMapping("/getAllData")
	@ResponseBody
	public Object getAllData(DgItemOutofStock dgItemPriceLadder,
			Object dgImportantActivityDiscountService) {
		if (dgItemPriceLadder.getmType() == null) // 第一次进来时候,初始化为1
		{
			dgItemPriceLadder.setmType(1);
		}
		List<DgItemOutofStock> ret = dgItemOutofStockService
				.getAllData(dgItemPriceLadder);
		return ret;
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

	/**
	 * 
	 * 获取具体打折项下的品项
	 */
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
				List<DgItemOutofStock> list2 = dgItemOutofStockService
						.selectItemByAdd(ids);
				for (DgItemOutofStock item : list2) {
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
		int relust=dgItemOutofStockService.saveItemOutofStock(data);
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
		dgItemOutofStockService.deleteIds(ids);
		ret.put("success", true);
		return ret;
	}
}