package com.yqsh.diningsys.web.controller.deskBusiness;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yqsh.diningsys.api.util.OkHttpUtils;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantAcitivityDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantActivityDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgNewestItem;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemForWeekService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemMemberDiscountSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemMemberDiscountService;

/**
 * 
 * 会员打折方案
 * 
 * @author heshuai
 *
 */
@Controller
@RequestMapping("/DgMemberDiscount")
public class DgMemberDiscountController extends BaseController {
	@Autowired
	private DgItemMemberDiscountService dgItemMemberDiscountService;
	@Autowired
	private DgItemMemberDiscountSService dgItemMemberDiscountSService;

	@Autowired
	private DgItemForWeekService dgItemForWeekService;

	@Autowired
	private TbOrgService tbOrgService;

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/member_discount");
		return model;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse responsem) {
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/member_discount_add");
		try {
			String id = request.getParameter("id");
			if (!StringUtils.isEmpty(id)) {
				DgItemMemberDiscount member = dgItemMemberDiscountService
						.selectByPrimaryKey(Integer.parseInt(id));
				request.setAttribute("member", member);
			}
			List<TbOrg> orgs = tbOrgService.getAllList(null);
			request.setAttribute("orgs", orgs);
			String memberList = OkHttpUtils.getMemberLevel(CacheUtil.getURLInCache("encDog"));
			Gson gson = new Gson();
			Map data = gson.fromJson(memberList, new TypeToken<Map>() {
			}.getType());
			if (data.get("msgCode").equals("ok")) {
				@SuppressWarnings("unchecked")
				List<Map> body = (List<Map>) data.get("body");
				request.setAttribute("level", body);
			}
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return model;
		}
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/toAddPlanDish")
	public ModelAndView toAddPlanDish(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/mem_dis_item_add");
		return model;
	}

	@RequestMapping("/getAllData")
	@ResponseBody
	public Page<DgItemMemberDiscount> getAllData(
			DgItemMemberDiscount dgMDiscount) {
		Page<DgItemMemberDiscount> pagebean = null;
		try {
			pagebean = dgItemMemberDiscountService
					.getAllMemberDishcount(dgMDiscount);
			pagebean.setPage(dgMDiscount.getPage());
			pagebean = (Page<DgItemMemberDiscount>) pageResult(pagebean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}

	/**
	 * 
	 * 功能:保存主表信息
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(HttpServletRequest request,
			HttpServletResponse response, DgItemMemberDiscount dim) {
		Map<String, Object> result = new HashMap<String, Object>();
		int count = dgItemMemberDiscountService.seleNameCode(dim);
		if (count > 0) {
			result.put("success", false);
			result.put("msg", "存在同名的方案!");
			return result;
		}
		try {
			int id = dgItemMemberDiscountService
					.insertP(request, response, dim);
			result.put("success", true);
			result.put("id", id);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "服务器内部错误");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 功能:保存附表信息
	 */
	@RequestMapping(value = "/saveChild")
	@ResponseBody
	public Object saveChild(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			dgItemMemberDiscountService.insertChild(request, response);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "服务器内部错误");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * 获取子表信息(品项表)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getAllImportChild")
	@ResponseBody
	public Object getAllImportChild(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		List<DgItemMemberDiscountS> ret = null;
		if (!StringUtils.isEmpty(id)) // 查询id
		{
			ret = dgItemMemberDiscountSService.seleByPid(Integer.parseInt(id));
		}
		return ret;
	}

	/**
	 * 
	 * 获取子表信息,组织机构表
	 */
/*	@RequestMapping("/getOrgChild")
	@ResponseBody
	public Object getOrgChild(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		String id = request.getParameter("id");// 是否新建
		if (StringUtils.isEmpty(id)) {
			List<TbOrg> orgs = tbOrgService.getAllList(null);
			for (TbOrg org : orgs) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("name", org.getOrgName());
				item.put("id", org.getId());
				item.put("enable", 0);// 是否启用设置为0
				ret.add(item);
			}
		} else // 存在id的情况,解析字符串json
		{
			List<TbOrg> orgs = tbOrgService.getAllList(null);
			DgItemMemberDiscount mem = dgItemMemberDiscountService
					.selectByPrimaryKey(Integer.parseInt(id));
			JSONArray obj = JSONArray.fromObject(mem.getAffiliation());
			if (obj.size() > 0) {
				for (int i = 0; i < obj.size(); i++) {
					JSONObject o = obj.getJSONObject(i);
					for (TbOrg org : orgs) {
						if (((String) o.get("id")).equals("" + org.getId())) {
							Map<String, Object> item = new HashMap<String, Object>();
							item.put("id", (String) o.get("id"));
							item.put("name", (String) o.get("name"));
							item.put("enable", (String) o.get("enable"));
							ret.add(item);
							break;
						}
					}
				}
			}
			for (TbOrg org : orgs) {
				boolean hava = false;
				for (int i = 0; i < ret.size(); i++) {
					if (((String) ret.get(i).get("id"))
							.equals("" + org.getId())) {
						hava = true;
						break;
					}
				}
				if (hava == false) {
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("name", org.getOrgName());
					item.put("id", org.getId());
					item.put("enable", 0);// 是否启用设置为0
					ret.add(item);
				}
			}

		}
		return ret;
	}*/

	/**
	 * 
	 * 增加打折品项
	 */
	@RequestMapping(value = "/getMealTimeAddDish")
	@ResponseBody
	public Object getMealTimeAddDish(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String chooseTrIds = request.getParameter("chooseTrIds");
		if (!StringUtils.isEmpty(chooseTrIds)) {
			List<Map<String, Object>> listRet = new ArrayList<Map<String, Object>>();
			List<Integer> ids = new ArrayList<Integer>();

			String str[] = chooseTrIds.split(",");
			for (int i = 0; i < str.length; i++) {
				ids.add(Integer.parseInt(str[i]));
			}
			if (ids.size() > 0) {
				List<DgItemFile> list2 = dgItemForWeekService
						.selectItemByAdd(ids);
				for (DgItemFile item : list2) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("id", -item.getId()); // 为负数,防止
					m.put("name", item.getName());
					m.put("itemId", item.getId());
					m.put("itemCode", item.getNum());
					m.put("tc", item.getIsTc());
					m.put("sPrice", item.getStandardPrice());
					m.put("enable", 1); // 启用设置为1
					m.put("price", item.getStandardPrice()); // 方案价格设置为标准价格
					m.put("consistent", 1); // 价格是否一致设置为1
					listRet.add(m);
				}
				ret.put("success", true);
				ret.put("list", listRet);
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

	/**
	 * 
	 * 批量 发布
	 * 
	 * @param ids
	 * @return
	 */

	@RequestMapping("/publish")
	@ResponseBody
	public Object publish(String id) {
		Map<String, Object> ret = new HashMap<String, Object>();
		dgItemMemberDiscountService.publish(Integer.valueOf(id));
		ret.put("success", true);
		return ret;
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
		// dgItemMemberDiscountService.deleteIds(ids);
		// dgItemMemberDiscountSService.deleteIds(ids);
		dgItemMemberDiscountService.trash(ids);
		ret.put("success", true);
		return ret;
	}

	/**
	 * 
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(String ids) {
		Map<String, Object> ret = new HashMap<String, Object>();
		dgItemMemberDiscountSService.deleteIds(ids);
		dgItemMemberDiscountService.deleteIds(ids);
		ret.put("success", true);
		return ret;
	}

	/**
	 * 
	 * 批量还原
	 * 
	 * @param ids
	 * @return
	 */

	@RequestMapping("/restore")
	@ResponseBody
	public Object restore(String ids) {
		Map<String, Object> ret = new HashMap<String, Object>();
		dgItemMemberDiscountService.restore(ids);
		ret.put("success", true);
		return ret;
	}

	@RequestMapping("/trash")
	public ModelAndView trash(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<TbOrg> orgs = tbOrgService.getAllList(null);
		request.setAttribute("orgs", orgs);
		ModelAndView modelAndView = new ModelAndView(
				"deskBusiness/product_item/member_discount_trash");
		return modelAndView;
	}

	@RequestMapping("/exportXls")
	public ModelAndView exportXls(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<DgItemMemberDiscount> ret = dgItemMemberDiscountService.seleAll();
		model.addAttribute("member", ret);
		ModelAndView modelAndView = new ModelAndView(
				"deskBusiness/product_item/member_Xls");
		return modelAndView;
	}

	/**
	 * form表单提交 Date类型数据绑定 日期格式化
	 * 
	 * @param binder
	 * @see [类、类#方法、类#成员]
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
}