package com.yqsh.diningsys.web.controller.print;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.dao.archive.DgItemFileTypeMapper;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.model.print.DgPrintManager;
import com.yqsh.diningsys.web.model.print.DgPrintManagerS;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountProgrammeService;
import com.yqsh.diningsys.web.service.print.DgPrintDataService;
import com.yqsh.diningsys.web.service.print.DgPrintManagerService;

@Controller
@RequestMapping(value = "/PrintManger")
public class PrintMangerController extends BaseController {

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private DgConsumptionAreaService dgConsumptionAreaService;

	@Autowired
	private DgPrintManagerService dgPrintManagerService;

	@Autowired
	private DgItemFileTypeService dgItemFileTypeService;

	@Autowired
	private DgItemDiscountProgrammeService dgItemDiscountProgrammeService;

	@Autowired
	private DgPrintDataService dgPrintDataService;

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("print/print_manger");
		return modelAndView;
	}

	/**
	 * 
	 * 获取所有打印机设置
	 */
	@RequestMapping(value = "/getAllPrint")
	@ResponseBody
	public Object getAllPrint(HttpServletRequest request,
			HttpServletResponse response, DgPrintManager p) {

		Page<DgPrintManager> pagebean = null;
		try {
			pagebean = dgPrintManagerService.getPage(p);
			pagebean.setPage(p.getPage());
			pagebean = (Page<DgPrintManager>) pageResult(pagebean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}

	@RequestMapping(value = "/toAddPrint")
	public ModelAndView toAddPrint(HttpServletRequest request,
			HttpServletResponse response) {
		Map body = new HashMap();
		body.put("code", 51001);
		body.put("explain", "请求打印机列表");
		body.put("username", "admin");
		body.put("requesttime", df.format(new Date()));
		Gson gson = new Gson();
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			DgPrintManager PMan = dgPrintManagerService
					.selectByPrimaryKey(Integer.valueOf(id));
			request.setAttribute("disP", PMan);
		} else {
			try {
				List<Map> printList = dgPrintDataService.getPrintUrl(
						gson.toJson(body));
				request.setAttribute("print", printList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<DgConsumptionArea> areas = dgConsumptionAreaService
				.getAllList(null);
		request.setAttribute("areas", areas);
		ModelAndView model = new ModelAndView("print/print_add");
		return model;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/toAddPrintDish")
	public ModelAndView toAddPrintDish(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		if (type.equals("1")) {
			ModelAndView model = new ModelAndView("print/print_dish");
			return model;
		} else {
			ModelAndView model = new ModelAndView("print/print_gate");
			return model;
		}
	}

	/**
	 * 
	 * 获取具体打折项下的品项
	 */
	@RequestMapping(value = "/getPrintItemByAdd")
	@ResponseBody
	public Object getPrintItemByAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String id = request.getParameter("chooseTrIds");
		String type = request.getParameter("type");
		if (!StringUtils.isEmpty(id)) {

			if (type.equals("1")) {
				List<DgItemFile> list = dgPrintManagerService
						.selectItemByAdd(id);

				List<DgPrintManagerS> list2 = new ArrayList<DgPrintManagerS>();
				for (DgItemFile item : list) {
					DgPrintManagerS item1 = new DgPrintManagerS();
					item1.setId(-item.getId());
					item1.setpId(item.getId());
					item1.setNum(item.getNum());
					item1.setItemId(item.getId());
					item1.setName(item.getName());
					list2.add(item1);
				}
				ret.put("success", true);
				ret.put("list", list2);
				return ret;
			} else {
				List<DgItemFileType> list = dgPrintManagerService
						.selectItemTypeByAdd(id);
				List<DgPrintManagerS> list2 = new ArrayList<DgPrintManagerS>();

				for (DgItemFileType item : list) {
					DgPrintManagerS item1 = new DgPrintManagerS();
					item1.setId(-item.getId());
					item1.setNum(item.getNum());
					item1.setItemId(item.getId());
					item1.setName(item.getName());
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
	 * 查询品项
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAllItem")
	@ResponseBody
	public Object getAllItem(HttpServletRequest request,
			HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String id = request.getParameter("id");
		List<DgItemFile> item = null;
		DgItemFileType type = null;
		String search = request.getParameter("search");

		if (StringUtils.isEmpty(search)) // 未查询情况
		{
			if (id == null || id.equals("-1")) {
				item = dgPrintManagerService.selectAllItemFile(ids);
			} else {
				type = dgItemFileTypeService.selectByPrimaryKey(Integer
						.parseInt(id));
				if (type.getpId() == 0) {
					item = dgPrintManagerService.selectBigItemFile(ids,
							Integer.parseInt(id));
				} else {
					item = dgPrintManagerService.selectSmallItemFile(ids,
							Integer.parseInt(id));
				}
			}
		} else {
			item = dgPrintManagerService.searchVague(search, ids);
		}
		return item;
	}

	@RequestMapping(value = "/getHaveItem")
	@ResponseBody
	public Object getHaveDish(HttpServletRequest request,
			HttpServletResponse response) {

		String ids = request.getParameter("ids");
		if (StringUtils.isEmpty(ids)) {
			return null;
		} else {
			List<DgItemFile> item = dgPrintManagerService.selectHaveItem(ids);
			return item;
		}
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
			item = dgPrintManagerService.selectAllItemFileType(ids);
		} else {
			type = dgItemFileTypeService.selectByPrimaryKey(Integer
					.parseInt(id));
			if (type.getpId() == 0) // 有子元素
			{
				item = dgPrintManagerService.selectSmallItemFileType(ids,
						Integer.parseInt(id));
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

	@RequestMapping(value = "/getHaveGate")
	@ResponseBody
	public Object getHaveGate(HttpServletRequest request,
			HttpServletResponse response) {

		String ids = request.getParameter("ids");
		if (StringUtils.isEmpty(ids)) {
			return null;
		} else {
			List<DgItemFileType> item = dgPrintManagerService
					.selectHaveItemType(ids);
			return item;
		}
	}

	/**
	 * 查询小类
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAllItemType")
	@ResponseBody
	public Object getAllItemType(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String search = request.getParameter("search");
		List<DgItemFile> item = null;
		DgItemFileType type = null;

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

	/**
	 * 保存方案主表
	 */
	@RequestMapping(value = "/toSavePrint")
	@ResponseBody
	public Object toSavePrint(HttpServletRequest request,
			HttpServletResponse response, DgPrintManager print) {
		Map<String, Object> ret = new HashMap<String, Object>();
		if (StringUtils.isEmpty(print.getTrash())) {
			print.setTrash(0);
		}

		if (!StringUtils.isEmpty(print.getId())) // 更新
		{
			dgPrintManagerService.updateByPrimaryKeySelective(print);
			dgPrintManagerService.deleteChildByPid("" + print.getId());
		} else {
			dgPrintManagerService.insertBackId(print);
		}
		ret.put("success", true);
		ret.put("id", print.getId());
		return ret;
	}

	/**
	 * 保存方案附表
	 */
	@RequestMapping(value = "/toSavePrintNext")
	@ResponseBody
	public Object toSavePrintNext(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String id = request.getParameter("id");// 方案id
		String childContent = request.getParameter("childContent");
		JSONArray json = JSONArray.fromObject(childContent);
		List<DgPrintManagerS> list = new ArrayList<DgPrintManagerS>();
		if (json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				DgPrintManagerS item = new DgPrintManagerS();
				JSONObject job = json.getJSONObject(i);
				item.setItemId(Integer.parseInt((String) job.get("itemId")));
				item.setpId(Integer.parseInt(id));
				list.add(item);
			}
		}
		dgPrintManagerService.insertChilds(list);
		ret.put("success", true);
		return ret;
	}

	/**
	 * 
	 * 获取具体打折项下的品项
	 */
	@RequestMapping(value = "/getPrintItem")
	@ResponseBody
	public Object getPrintItem(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if (!StringUtils.isEmpty(id)) {
			DgPrintManager p = dgPrintManagerService.selectByPrimaryKey(Integer
					.parseInt(id));
			List<DgPrintManagerS> ret = null;
			if (p.getType() == 1 && type.equals("1")) {
				ret = dgPrintManagerService.selectItemByPid(Integer
						.parseInt(id));
			} else if (p.getType() == 2 && type.equals("2")) {
				ret = dgPrintManagerService.selectItemTypeByPid(Integer
						.parseInt(id));
			}
			return ret;
		}
		return null;
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
		dgPrintManagerService.deleteIds(ids);
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
		dgPrintManagerService.restore(ids);
		ret.put("success", true);
		return ret;
	}

	/**
	 * 
	 * 批量进入回收站
	 * 
	 * @param ids
	 * @return
	 */

	@RequestMapping("/trash")
	@ResponseBody
	public Object trash(String ids) {
		Map<String, Object> ret = new HashMap<String, Object>();
		dgPrintManagerService.trash(ids);
		ret.put("success", true);
		return ret;
	}

	@RequestMapping("/to_trash")
	public ModelAndView to_trash(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ModelAndView modelAndView = new ModelAndView("print/print_trash");
		return modelAndView;
	}
}