package com.yqsh.diningsys.web.controller.deskBusiness;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantActivityDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgWeekDiscount;
import com.yqsh.diningsys.web.model.sysSettings.SysBackupDatabase;
import com.yqsh.diningsys.web.service.archive.DgItemFileService;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountProgrammeSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountProgrammeService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemTypeDiscountService;
import com.yqsh.diningsys.web.service.deskBusiness.DgWeekDiscountService;

@Controller
@RequestMapping("/ProDiscountPan")
public class ProDiscountPanController extends BaseController {
	@Autowired
	private DgItemTypeDiscountService dgItemTypeDiscountService;

	@Autowired
	private DgItemFileTypeService dgItemFileTypeService;

	@Autowired
	private DgItemDiscountProgrammeService dgItemDiscountProgrammeService;

	@Autowired
	private DgItemDiscountProgrammeSService dgItemDiscountProgrammeSService;

	@Autowired
	private DgWeekDiscountService dgWeekDiscountService;

	@Autowired
	private DgItemDiscountService dgItemDiscountService;

	@Autowired
	private DgItemFileService dgItemFileService;

	/**
	 * 增加备份文件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toAddPlan")
	public ModelAndView toAddPlan(HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			DgItemDiscountProgramme disP = dgItemDiscountProgrammeService
					.selectByPrimaryKey(Integer.parseInt(id));
			request.setAttribute("disP", disP);
		}
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/allow_discount_pan_add");
		return model;
	}

	@RequestMapping(value = "/allowDiscountPlan")
	public ModelAndView getAllowDiscountPlan(HttpServletRequest request,
			HttpServletResponse response) {
		List<DgWeekDiscount> allWeekDiscount = dgWeekDiscountService
				.selectAll();
		List<DgItemDiscountProgramme> allPan = dgItemDiscountProgrammeService
				.selectAllProgrammes();
		request.setAttribute("allWeekDiscount", allWeekDiscount);
		request.setAttribute("allPan", allPan);
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/allow_discount_pan");
		return model;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/toAddPlanDish")
	public ModelAndView toAddPlanDish(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		if (type.equals("1")) {
			ModelAndView model = new ModelAndView(
					"deskBusiness/product_item/allow_discount_pan_add_dish");
			return model;
		} else {
			ModelAndView model = new ModelAndView(
					"deskBusiness/product_item/allow_discount_pan_add_gate");
			return model;
		}
	}

	@RequestMapping(value = "/getAllDish")
	@ResponseBody
	public Object getAllDish(HttpServletRequest request,
			HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String id = request.getParameter("id");
		String disable = request.getParameter("disable");
		String yxdz = request.getParameter("yxdz");
		List<DgItemFile> item = null;
		DgItemFileType type = null;
		String search = request.getParameter("search");

		if (StringUtils.isEmpty(search)) // 未查询情况
		{
			/**
			 * 
			 * 没有查询条件的情况
			 */
			if (id == null || id.equals("-1")) {
				item = dgItemDiscountProgrammeService.selectAllItemFile(ids,disable,yxdz);
			} else {
				type = dgItemFileTypeService.selectByPrimaryKey(Integer
						.parseInt(id));
				if (type.getpId() == 0) {
					item = dgItemDiscountProgrammeService.selectBigItemFile(
							ids, Integer.parseInt(id),disable,yxdz);
				} else {
					item = dgItemDiscountProgrammeService.selectSmallItemFile(
							ids, Integer.parseInt(id),disable,yxdz);
				}
			}
		} else {
			item = dgItemDiscountProgrammeService.searchVague(id,search, ids,disable,yxdz);
		}

		return item;
	}

	@RequestMapping(value = "/getAllItem")
	@ResponseBody
	public Object getAllItem(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String search = request.getParameter("search");
		List<DgItemFile> item = null;
		DgItemFileType type = null;

		/**
		 * 
		 * 没有查询条件的情况
		 */
		if (StringUtils.isEmpty(search)) // 未查询情况
		{
			if (id == null || id.equals("-1")) {
				item = dgItemDiscountProgrammeService.selectAllDgItemFile();
			} else {
				type = dgItemFileTypeService.selectByPrimaryKey(Integer
						.parseInt(id));
				if (type.getpId() == 0) {
					item = dgItemDiscountProgrammeService
							.selectBigDgItemFile(Integer.parseInt(id));
				} else {
					item = dgItemDiscountProgrammeService
							.selectSmallDgItemFile(Integer.parseInt(id));
				}
			}
		} else {
			item = dgItemDiscountProgrammeService.search(search);
		}
		return item;
	}

	@RequestMapping(value = "/getAllGate")
	@ResponseBody
	public Object getAllGate(HttpServletRequest request,
			HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String id = request.getParameter("id");
		List<DgItemFileType> item = null;
		DgItemFileType type = null;

		/**
		 * 
		 * 没有查询条件的情况
		 */
		if (id == null || id.equals("-1")) {
			item = dgItemDiscountProgrammeService.selectAllItemFileType(ids);
		} else {
			type = dgItemFileTypeService.selectByPrimaryKey(Integer
					.parseInt(id));
			if (type.getpId() == 0) // 有子元素
			{
				item = dgItemDiscountProgrammeService.selectSmallItemFileType(
						ids, Integer.parseInt(id));
			} else {
				item = new ArrayList<DgItemFileType>();
				List idList = new ArrayList();
				Collections.addAll(idList, ids.split(","));
				if (!idList.contains(String.valueOf(type.getId()))) // 查询是否存在,不存在就加入
				{
					item.add(type);
				}
			}
		}
		return item;
	}

	@RequestMapping(value = "/getHaveDish")
	@ResponseBody
	public Object getHaveDish(HttpServletRequest request,
			HttpServletResponse response) {

		String ids = request.getParameter("ids");
		String disable = request.getParameter("disable");
		String yxdz = request.getParameter("yxdz");
		if (StringUtils.isEmpty(ids)) {
			return null;
		} else {
			List<DgItemFile> item = dgItemDiscountProgrammeService
					.selectHaveItem(ids,disable,yxdz);
			return item;
		}
	}

	@RequestMapping(value = "/getHaveGate")
	@ResponseBody
	public Object getHaveGate(HttpServletRequest request,
			HttpServletResponse response) {

		String ids = request.getParameter("ids");
		if (StringUtils.isEmpty(ids)) {
			return null;
		} else {
			List<DgItemFileType> item = dgItemDiscountProgrammeService
					.selectHaveItemType(ids);
			return item;
		}
	}

	/**
	 * 
	 * 获取所有打折项
	 */
	@RequestMapping(value = "/getAllPan")
	@ResponseBody
	public Object getAllPan(HttpServletRequest request,
			HttpServletResponse response, DgItemDiscountProgramme p) {

		Page<DgItemDiscountProgramme> pagebean = null;
		try {
			pagebean = dgItemDiscountProgrammeService.getPage(p);
			pagebean.setPage(p.getPage());
			pagebean = (Page<DgItemDiscountProgramme>) pageResult(pagebean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;

	}

	/**
	 * 
	 * 获取具体打折项下的品项
	 */
	@RequestMapping(value = "/getPanItem")
	@ResponseBody
	public Object getPanItem(HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if (!StringUtils.isEmpty(id)) {
			DgItemDiscountProgramme p = dgItemDiscountProgrammeService
					.selectByPrimaryKey(Integer.parseInt(id));
			List<DgItemDiscountProgrammeS> ret = null;
			if (p.getType().equals("1") && type.equals("1")) {
				ret = dgItemDiscountProgrammeSService.selectItemByPid(Integer
						.parseInt(id));
			} else if (p.getType().equals("2") && type.equals("2")) {
				ret = dgItemDiscountProgrammeSService
						.selectItemTypeByPid(Integer.parseInt(id));
			}
			return ret;
		}
		return null;
	}

	/**
	 * 
	 * 获取具体打折项下的品项
	 */
	@RequestMapping(value = "/getPanItemByAdd")
	@ResponseBody
	public Object getPanItemByAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String id = request.getParameter("chooseTrIds");
		String type = request.getParameter("type");
		if (!StringUtils.isEmpty(id)) {

			if (type.equals("1")) {
				List<DgItemFile> list = dgItemDiscountProgrammeSService
						.selectItemByAdd(id);

				List<DgItemDiscountProgrammeS> list2 = new ArrayList<DgItemDiscountProgrammeS>();
				for (DgItemFile item : list) {
					DgItemDiscountProgrammeS item1 = new DgItemDiscountProgrammeS();
					item1.setId(-item.getId());
					item1.setpId(item.getId());
					item1.setItemId(item.getId());
					item1.setCode(item.getNum());
					item1.setName(item.getName());
					item1.setDisable(0);// 是否停用
					item1.setTc(item.getIsTc());
					item1.setDiscount(100);// 则口比例设置为100
					list2.add(item1);
				}
				ret.put("success", true);
				ret.put("list", list2);
				return ret;
			} else {
				List<DgItemFileType> list = dgItemDiscountProgrammeSService
						.selectItemTypeByAdd(id);
				List<DgItemDiscountProgrammeS> list2 = new ArrayList<DgItemDiscountProgrammeS>();

				for (DgItemFileType item : list) {
					DgItemDiscountProgrammeS item1 = new DgItemDiscountProgrammeS();
					item1.setId(-item.getId());
					item1.setCode(item.getNum());
					item1.setItemId(item.getId());
					item1.setName(item.getName());
					item1.setDisable(0);// 是否停用
					item1.setDiscount(100);// 则口比例设置为100
					list2.add(item1);
				}
				ret.put("success", true);
				ret.put("list", list2);
				return ret;
			}
		}
		ret.put("success", true);
		ret.put("list", null);
		return ret;
	}

	/**
	 * 保存方案主表
	 */
	@RequestMapping(value = "/toSavePlan")
	@ResponseBody
	public Object toSavePlan(HttpServletRequest request,
			HttpServletResponse response, DgItemDiscountProgramme pan) {
		Map<String, Object> ret = new HashMap<String, Object>();
		int count = dgItemDiscountProgrammeService.seleNameCode(pan);
		if (count > 0) {
			ret.put("success", false);
			ret.put("msg", "存在同名或同编码的打折方案!");
			return ret;
		}
		if (StringUtils.isEmpty(pan.getAllowFDis())) {
			pan.setAllowFDis(0);
		}
		if (StringUtils.isEmpty(pan.getDisable())) {
			pan.setDisable(0);
		}
		if (StringUtils.isEmpty(pan.getDateLimit())) {
			pan.setDateLimit(0);
		}
		pan.setTime(new Date());
		pan.setRecyclebin(0);// 初始化不到回收站

		if (!StringUtils.isEmpty(pan.getId())) // 更新
		{
			dgItemDiscountProgrammeService.updateByPrimaryKeySelective(pan);
			dgItemDiscountProgrammeSService.deleteIds("" + pan.getId());
		} else {
			dgItemDiscountProgrammeService.insertBackId(pan);
		}
		ret.put("success", true);
		ret.put("id", pan.getId());
		ret.put("list", pan);
		return ret;
	}

	/**
	 * 保存方案附表
	 */
	@RequestMapping(value = "/toSavePlanNext")
	@ResponseBody
	public Object toSavePlanNext(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String id = request.getParameter("id");// 方案id
		String childContent = request.getParameter("childContent");
		JSONArray json = JSONArray.fromObject(childContent);
		if (json.size() > 0) {
			List<DgItemDiscountProgrammeS> pros = new ArrayList<DgItemDiscountProgrammeS>();
			for (int i = 0; i < json.size(); i++) {
				DgItemDiscountProgrammeS item = new DgItemDiscountProgrammeS();
				JSONObject job = json.getJSONObject(i);
				item.setItemId(Integer.parseInt((String) job.get("pId")));
				item.setpId(Integer.parseInt(id));
				item.setDiscount(Integer.parseInt((String) job.get("discount")));
				item.setDisable(Integer.parseInt((String) job.get("disable")));
				pros.add(item);
			}
			dgItemDiscountProgrammeSService.insertChilds(pros);
		}
		ret.put("success", true);
		return ret;
	}

	@RequestMapping("/delPanData")
	@ResponseBody
	public Object delPanData(String ids) {
		Map<String, Object> ret = new HashMap<String, Object>();
		// dgItemDiscountProgrammeSService.deleteIds(ids);
		// dgItemDiscountProgrammeService.deleteIds(ids);
		dgItemDiscountProgrammeService.trash(ids);
		dgWeekDiscountService.updataByDelete(ids);

		List<DgItemDiscountProgramme> pro = dgItemDiscountProgrammeService
				.selectAllProgrammes();
		ret.put("success", true);
		ret.put("pro", pro);
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
		dgItemDiscountProgrammeSService.deleteIds(ids);
		dgItemDiscountProgrammeService.deleteIds(ids);
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
		dgItemDiscountProgrammeService.restore(ids);
		List<DgItemDiscountProgramme> pro = dgItemDiscountProgrammeService
				.selectAllProgrammes();
		ret.put("success", true);
		ret.put("pro", pro);
		return ret;
	}

	@RequestMapping("/trash")
	public ModelAndView trash(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ModelAndView modelAndView = new ModelAndView(
				"deskBusiness/product_item/pro_discount_pan_trash");
		return modelAndView;
	}

	@RequestMapping("/saveWeekData")
	@ResponseBody
	public ResultInfo saveWeekData(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		JSONArray json = JSONArray.fromObject(data);
		if (json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i);
				DgWeekDiscount week = new DgWeekDiscount();
				week.setId(Integer.parseInt((String) job.get("id")));
				week.setpId(Integer.parseInt((String) job.get("val")));
				dgWeekDiscountService.updateByPrimaryKeySelective(week);
			}

		}

		return returnSuccess();
	}

	/**
	 * 
	 * 左侧树菜单
	 * 
	 * @return
	 */
	@RequestMapping("/getTreeGateNodes")
	@ResponseBody
	public List<DgItemFileType> getTreeGateNodes() {
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
				return null; // 不再查找下级
			}
		}
		return menu;
	}

	@RequestMapping("/exportXls")
	public ModelAndView exportXls(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<DgItemDiscountProgramme> discountPan = dgItemDiscountProgrammeService
				.selectAllProgrammes();
		model.addAttribute("discountPan", discountPan);
		ModelAndView modelAndView = new ModelAndView(
				"deskBusiness/product_item/allow_discount_pan_Xls");
		return modelAndView;
	}

	/**
	 * form表单提交 Date类型数据绑定 <功能详细描述>
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
