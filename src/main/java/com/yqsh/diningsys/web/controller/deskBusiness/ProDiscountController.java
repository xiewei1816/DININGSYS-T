package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscountQuery;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
import com.yqsh.diningsys.web.model.sysSettings.SysBackupDatabase;
import com.yqsh.diningsys.web.service.archive.DgItemFileService;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemTypeDiscountService;
import com.yqsh.diningsys.web.service.sysSettings.SysBackupDataBaseService;

@Controller
@RequestMapping("/ProDiscount")
public class ProDiscountController extends BaseController {
	@Autowired
	private SysBackupDataBaseService sysBackupDataBaseService;

	@Autowired
	private DgItemTypeDiscountService dgItemTypeDiscountService;

	@Autowired
	private DgItemFileTypeService dgItemFileTypeService;

	@Autowired
	private DgItemDiscountService dgItemDiscountService;

	@RequestMapping(value = "/allowDiscount")
	public ModelAndView getAllowDiscount() {
		// deskBusiness/product_item/discount/allow_discount
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/allow_discount");
		return model;
	}

	/**
	 * 
	 * 更新打折数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateDiscount")
	@ResponseBody
	public ResultInfo updateDiscount(HttpServletRequest request,
			HttpServletResponse response) {
		/*
		 * 
		 * returnSuccess(); returnFail();
		 */
		String gate_content = request.getParameter("gate");
		String dish_content = request.getParameter("dish");
		List<DgItemTypeDiscount> items = new ArrayList<DgItemTypeDiscount>();
		List<DgItemFile> itemsDiscount = new ArrayList<DgItemFile>();

		JSONArray json = JSONArray.fromObject(gate_content);
		if (json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				DgItemTypeDiscount item = new DgItemTypeDiscount();
				JSONObject job = json.getJSONObject(i);
				item.setId(Integer.parseInt((String) job.get("id")));
				item.setDiscount(Integer.parseInt((String) job.get("discount")));
				items.add(item);
			}
		}

		JSONArray jsonDish = JSONArray.fromObject(dish_content);
		if (jsonDish.size() > 0) {
			for (int i = 0; i < jsonDish.size(); i++) {
				DgItemFile item = new DgItemFile();
				JSONObject job = jsonDish.getJSONObject(i);
				item.setId(Integer.parseInt((String) job.get("id")));
				item.setYxdz(Integer.parseInt((String) job.get("discount")));
				itemsDiscount.add(item);
			}
		}

		dgItemTypeDiscountService.updateBySrcList(items);
		dgItemDiscountService.updateBySrcList(itemsDiscount);
		return returnSuccess();
	}

	/**
	 * 
	 * 获取所以小类打折项
	 */
	@RequestMapping(value = "/getAllItemTypeDiscount")
	@ResponseBody
	public Object getAllItemTypeDiscount(HttpServletRequest request,
			HttpServletResponse response) {

		List<DgItemTypeDiscountQuery> pageBean = null;
//		/*
//		 * 获取所有小类
//		 */
//		List<DgItemFileType> itemTypes = dgItemFileTypeService
//				.selectAllItemFileSmallType();
//		/**
//		 * 
//		 * 先遍历查询没有的数据
//		 */
//		for (DgItemFileType item : itemTypes) {
//			int count = dgItemTypeDiscountService.getCountByItemType(item
//					.getId());
//			if (count == 0) {
//				DgItemTypeDiscount diItem = new DgItemTypeDiscount();
//				diItem.setDgId(item.getId());
//				diItem.setDgNum(item.getNum());
//				diItem.setDiscount(0);
//				dgItemTypeDiscountService.insert(diItem);
//			}
//		}
		/**
		 * 
		 * 最后查询
		 */
		pageBean = dgItemTypeDiscountService.getAll();
		return pageBean;
	}

	/**
	 * 
	 * 获取所以小类打折项
	 */
	@RequestMapping(value = "/getAllItemDiscount")
	@ResponseBody
	public Object getAllItemDiscount(HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter("id");
		List<DgItemFile> pageBean = null;
		DgItemFileType type = null;
		/*
		 * 获取所有小类
		 */
		List<DgItemFile> items = null;

		/**
		 * 
		 * 没有查询条件的情况
		 */
		if (id == null || id.equals("-1")) {
			items = dgItemDiscountService.selectAllItemFile();
		} else {
			type = dgItemFileTypeService.selectByPrimaryKey(Integer
					.parseInt(id));
			if (type.getpId() == 0) // 大类情况
			{
				items = dgItemDiscountService.selectBigItemFile(Integer
						.parseInt(id));
			} else // 小类情况
			{
				items = dgItemDiscountService.selectSmallItemFile(Integer
						.parseInt(id));
			}
		}
		// /**
		// *
		// * 先遍历查询没有的数据
		// */
		// for(DgItemFile item : items)
		// {
		// int count = dgItemDiscountService.getCountByItem(item.getId());
		// if(count == 0)
		// {
		// int gate_count =
		// dgItemTypeDiscountService.getCountByItemType(item.getPpxlId());
		// int discount = 0;
		// if(gate_count>0)
		// {
		// discount =
		// dgItemTypeDiscountService.getDiscountByDgId(item.getPpxlId());
		// }
		// DgItemDiscount diItem = new DgItemDiscount();
		// diItem.setNum(item.getNum());
		// diItem.setDiscount(discount);
		// diItem.setItemId(item.getId());
		// dgItemDiscountService.insert(diItem);
		// }
		// }
		/**
		 * 
		 * 最后查询
		 */

		if (id == null || id.equals("-1")) {
			pageBean = dgItemDiscountService.getAll();
		} else if (type != null) {
			if (type.getpId() == 0) {
				pageBean = dgItemDiscountService.getBigByID(Integer
						.parseInt(id));
			} else {
				pageBean = dgItemDiscountService.getSmallByID(Integer
						.parseInt(id));
			}
		}

		return pageBean;
	}

	/**
	 * 
	 * 左侧树菜单
	 * 
	 * @return
	 */
	@RequestMapping("/getTreeNodes")
	@ResponseBody
	public List<DgItemFileType> getTreeNodes() {
		String id = getRequest().getParameter("id");
		List<DgItemFileType> menu = new ArrayList<DgItemFileType>();

		// 获取根节点
		if (StringUtils.isEmpty(id)) {
			DgItemFileType m = new DgItemFileType();
			m.setId(-1);
			m.setName("品项分类");
			menu.add(m);
		} else {
			if (id.equals("-1")) {
				// 获取父类
				menu = dgItemDiscountService.selectItemSmallByParentId(0);
			} else {
				menu = dgItemDiscountService.selectItemSmallByParentId(Integer
						.parseInt(id));
			}
		}
		return menu;
	}

	@RequestMapping("/exportXls")
	public ModelAndView exportXls(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<DgItemFile> discount = dgItemDiscountService.getAll();
		model.addAttribute("discount", discount);
		ModelAndView modelAndView = new ModelAndView(
				"deskBusiness/product_item/allow_discount_Xls");
		return modelAndView;
	}
}
