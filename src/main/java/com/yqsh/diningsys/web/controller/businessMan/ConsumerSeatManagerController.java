package com.yqsh.diningsys.web.controller.businessMan;

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
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.businessMan.DgAreaLimitItem;
import com.yqsh.diningsys.web.model.businessMan.DgAreaManager;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingScheme;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingSchemeS;
import com.yqsh.diningsys.web.model.businessMan.DgSeatItem;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.businessMan.DgAreaLimitItemService;
import com.yqsh.diningsys.web.service.businessMan.DgAreaManagerService;
import com.yqsh.diningsys.web.service.businessMan.DgConsumSeatManagerService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatChargingSchemeSService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatChargingSchemeService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatItemService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatManagerService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemForWeekService;

@Controller
@RequestMapping(value = "/consumerSeatManager")
public class ConsumerSeatManagerController extends BaseController {

	@Autowired
	private DgConsumptionAreaService consumptionAreaService;

	@Autowired
	private DgConsumerSeatService consumerSeatService;

	@Autowired
	private DgAreaLimitItemService dgAreaLimitItemService;

	@Autowired
	private DgAreaManagerService dgAreaManagerService;

	@Autowired
	private DgSeatManagerService dgSeatManagerService;

	@Autowired
	private DgSeatItemService dgSeatItemService;

	@Autowired
	private DgItemForWeekService dgItemForWeekService;

	@Autowired
	private DgPublicCode0Service dgPublicCode0Service; // 数据字典
														// selectSmallDpc查询字段

	@Autowired
	private DgSeatChargingSchemeService dgSeatChargingSchemeService;

	@Autowired
	private DgSeatChargingSchemeSService dgSeatChargingSSchemeService;

	@Autowired
	private DgConsumSeatManagerService dgConsumSeatManagerService;

	@RequestMapping("/index")
	public ModelAndView index() {
		int count = dgAreaManagerService.getAllCount();
		int seatCount = dgSeatManagerService.getAllCount();
		getRequest().setAttribute("count", count);
		getRequest().setAttribute("seatCount", seatCount);
		ModelAndView modelAndView = new ModelAndView(
				"bussinessMan/consumerSeat/index");
		return modelAndView;
	}

	@RequestMapping("/consumerSeatindex")
	public ModelAndView consumerSeatindex() {
		String id = getRequest().getParameter("id");
		try {
			DgConsumerSeat seatId = new DgConsumerSeat();
			seatId.setId(Integer.parseInt(id));
			DgConsumerSeat seat = consumerSeatService
					.getDgConsumerSeatByID(seatId);
			DgSeatManager manager = dgSeatManagerService.selectBySeatId(Integer
					.parseInt(id));
			List<DgSeatChargingScheme> scheme = dgSeatChargingSchemeService
					.seleAll();
			getRequest().setAttribute("man", manager);
			getRequest().setAttribute("seat", seat);
			getRequest().setAttribute("scheme", scheme);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView(
				"bussinessMan/consumerSeat/consumerSeat");
		return modelAndView;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/toAddItem")
	public ModelAndView toAddItem(HttpServletRequest request,
			HttpServletResponse response) {
		String grid = request.getParameter("grid");
		String yxdz = request.getParameter("yxdz");
		String disable = request.getParameter("disable");
		request.setAttribute("grid", grid);
		request.setAttribute("yxdz", yxdz);
		request.setAttribute("disable", disable);
		ModelAndView model = new ModelAndView(
				"bussinessMan/consumerSeat/dish_add");
		return model;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/toBfPro")
	public ModelAndView toBfPro(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView(
				"bussinessMan/consumerSeat/bf_pro");
		return model;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/toAddBfPro")
	public ModelAndView toAddBfPro(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			DgSeatChargingScheme pro = dgSeatChargingSchemeService
					.selectByPrimaryKey(Integer.parseInt(id));
			request.setAttribute("pro", pro);
		}
		ModelAndView model = new ModelAndView(
				"bussinessMan/consumerSeat/bf_pro_add");
		return model;
	}

	@RequestMapping("/saveConsumerSeatMan")
	@ResponseBody
	public Object saveConsumerSeatMan(HttpServletRequest request,
			HttpServletResponse response, DgSeatManager dgSeatManager) {
		Map<String, Object> map = new HashMap<String, Object>();
		dgSeatManager = initSeatManager(dgSeatManager);
		if (StringUtils.isEmpty(dgSeatManager.getId())) {
			dgSeatManagerService.insertSelective(dgSeatManager);
		} else {
			dgSeatManagerService.updateByPrimaryKeySelective(dgSeatManager);
		}
		map.put("id", dgSeatManager.getId());
		map.put("success", true);
		return map;
	}

	@RequestMapping("/saveConsumerSeatNext")
	@ResponseBody
	public Object saveConsumerSeatNext(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String content = request.getParameter("content");
		String seatId = request.getParameter("seatId");
		try {
			dgConsumSeatManagerService.saveConsumerSeatNext(content, seatId);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("/saveConsumerAreaMan")
	@ResponseBody
	public Object saveConsumerAreaMan(HttpServletRequest request,
			HttpServletResponse response, DgAreaManager dgAreaManager) {
		Map<String, Object> map = new HashMap<String, Object>();
		dgAreaManager = initAreaManager(dgAreaManager);
		if (StringUtils.isEmpty(dgAreaManager.getId())) {
			dgAreaManagerService.insertBackId(dgAreaManager);
		} else {
			dgAreaManagerService.updateByPrimaryKeySelective(dgAreaManager);
		}
		map.put("id", dgAreaManager.getId());
		map.put("success", true);
		return map;
	}

	@RequestMapping("/saveConsumerAreaManNext")
	@ResponseBody
	public Object saveConsumerAreaManNext(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String content = request.getParameter("content");
		String areaId = request.getParameter("areaId");
		try {
			dgConsumSeatManagerService.saveConsumerAreaManNext(content, areaId);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("/getConsumerAreaItem")
	@ResponseBody
	public Object getConsumerAreaItem(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			List<DgAreaLimitItem> items = dgAreaLimitItemService
					.getByAreaId(Integer.parseInt(id));
			return items;
		}
		return null;
	}

	@RequestMapping("/getAllAreaItem")
	@ResponseBody
	public Object getAllAreaItem(HttpServletRequest request,
			HttpServletResponse response) {
		List<DgAreaManager> items = dgAreaManagerService.selectAll();
		return items;
	}

	@RequestMapping("/getAllConsumerSeatItem")
	@ResponseBody
	public Object getAllConsumerSeatItem(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			List<DgSeatManager> items = dgSeatManagerService
					.selectDetailBySeatId(Integer.parseInt(id));
			for (DgSeatManager i : items) {
				i.setFwfType(i.getFwf());
				i.setBffType(i.getBff());
			}
			return items;
		}
		return null;
	}

	@RequestMapping("/getAllAreaConsumerSeatItem")
	@ResponseBody
	public Object getAllAreaConsumerSeatItem(HttpServletRequest request,
			HttpServletResponse response) {
		List<DgSeatManager> items = dgSeatManagerService
				.selectAllDetailBySeatId();
		for (DgSeatManager i : items) {
			i.setFwfType(i.getFwf());
			i.setBffType(i.getBff());
		}
		return items;
	}

	@RequestMapping("/getConsumerSeatItem")
	@ResponseBody
	public Object getConsumerSeatItem(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			List<DgSeatItem> items = dgSeatItemService.getBySeatId(Integer
					.parseInt(id));
			return items;
		}
		return null;
	}

	@RequestMapping("/getConsumerWeekItem")
	@ResponseBody
	public Object getConsumerWeekItem(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			DgSeatManager items = dgSeatManagerService.selectBySeatId(Integer
					.parseInt(id));
			if (items == null) {
				List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < 7; i++) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("id", i + 1);
					if (i + 1 == 7) {
						m.put("week", "星期日");
					} else {
						m.put("week", "星期" + (i + 1));
					}
					m.put("faId", 0);
					m.put("faName", "");
					map.add(m);
				}
				return map;
			} else {
				List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
				if (StringUtils.isEmpty(items.getBffWeekProD())
						|| items.getBffWeekProD().length() < 10) {
					for (int i = 0; i < 7; i++) {
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("id", i + 1);
						if (i + 1 == 7) {
							m.put("week", "星期日");
						} else {
							m.put("week", "星期" + (i + 1));
						}
						m.put("faId", 0);
						m.put("faName", "");
						map.add(m);
					}
				} else {
					JSONArray obj = JSONArray
							.fromObject(items.getBffWeekProD());
					if (obj.size() > 0) {
						for (int i = 0; i < obj.size(); i++) {
							JSONObject o = obj.getJSONObject(i);
							Map<String, Object> m = new HashMap<String, Object>();
							m.put("id", o.getInt("id"));
							m.put("week", o.getString("week"));
							m.put("faId", o.getInt("faId"));
							DgSeatChargingScheme scaChargingScheme = dgSeatChargingSchemeService
									.selectByPrimaryKey(o.getInt("faId"));
							if (scaChargingScheme != null) {
								m.put("faName", scaChargingScheme.getName());
							} else {
								m.put("faName", "");
							}
							map.add(m);

						}
					}
				}
				return map;
			}

		} else {
			List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < 7; i++) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", i + 1);
				if (i + 1 == 7) {
					m.put("week", "星期日");
				} else {
					m.put("week", "星期" + (i + 1));
				}
				m.put("faId", 0);
				m.put("faName", "");
				map.add(m);
			}
			return map;
		}
	}

	@RequestMapping("/consumerConreteAreaindex")
	public ModelAndView conreteAreaIndex() {
		String id = getRequest().getParameter("id");
		DgConsumptionArea areaId = new DgConsumptionArea();
		areaId.setId(Integer.parseInt(id));
		DgConsumptionArea area = consumptionAreaService
				.getConsumptionAreaByID(areaId);
		DgAreaManager manager = dgAreaManagerService.selectByAreaId(Integer
				.parseInt(id));
		int count = dgSeatManagerService.getCountByAreaId(Integer.parseInt(id));
		getRequest().setAttribute("manager", manager);
		getRequest().setAttribute("area", area);
		getRequest().setAttribute("count", count);
		ModelAndView modelAndView = new ModelAndView(
				"bussinessMan/consumerSeat/consumerConcreteArea");
		return modelAndView;
	}

	@RequestMapping("/consumerAreaindex")
	public ModelAndView areaIndex() {
		int count = dgAreaManagerService.getAllCount();
		int seatCount = dgSeatManagerService.getAllCount();
		getRequest().setAttribute("count", count);
		getRequest().setAttribute("seatCount", seatCount);
		ModelAndView modelAndView = new ModelAndView(
				"bussinessMan/consumerSeat/consumerArea");
		return modelAndView;
	}

	/**
	 * 
	 * 增加打折品项
	 */
	@RequestMapping(value = "/getSeatAddItem")
	@ResponseBody
	public Object getSeatAddItem(HttpServletRequest request,
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
					m.put("count", 1);
					m.put("useOpenCounter", 0);
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
	 * 左侧树菜单
	 * 
	 * @return
	 */
	@RequestMapping("/getTreeNodes")
	@ResponseBody
	public List<DgProductPhaseLeftmenu> getTreeNodes() {
		String id = getRequest().getParameter("id");
		String childCount = getRequest().getParameter("childCount");
		List<DgProductPhaseLeftmenu> menu = new ArrayList<DgProductPhaseLeftmenu>();

		// 获取根节点
		if (StringUtils.isEmpty(id)) {
			DgProductPhaseLeftmenu m = new DgProductPhaseLeftmenu();
			m.setId(-1);
			m.setName("消费区域");
			m.setChildCount(1); // 大于0即可
			m.setParentId(0);
			m.setMurl("/DININGSYS/consumerSeatManager/consumerAreaindex");
			menu.add(m);
		} else {
			if (id.equals("-1")) {
				DgConsumptionArea area = new DgConsumptionArea();
				area.setDelFlag(0); // 未删除的
				List<DgConsumptionArea> areas = consumptionAreaService
						.getAllList(area);

				for (DgConsumptionArea a : areas) {
					DgProductPhaseLeftmenu m = new DgProductPhaseLeftmenu();
					m.setId(a.getId());
					m.setName(a.getName());
					m.setChildCount(1); // 大于0即可
					m.setParentId(-1);
					m.setMurl("/DININGSYS/consumerSeatManager/consumerConreteAreaindex");
					menu.add(m);
				}
			} else if (!childCount.equals("0") && !id.equals("-1")) {
				DgConsumerSeat seat = new DgConsumerSeat();
				seat.setConsArea(Integer.parseInt(id));
				List<DgConsumerSeat> seats = consumerSeatService
						.getAllList(seat);
				for (DgConsumerSeat a : seats) {
					DgProductPhaseLeftmenu m = new DgProductPhaseLeftmenu();
					m.setId(a.getId());
					m.setName(a.getName());
					m.setChildCount(0); // 大于0即可
					m.setParentId(-1);
					m.setMurl("/DININGSYS/consumerSeatManager/consumerSeatindex");
					menu.add(m);
				}
			}
		}
		return menu;
	}

	@RequestMapping("/getPageBffPro")
	@ResponseBody
	public Page<DgSeatChargingScheme> getPageBffPro(
			DgSeatChargingScheme dgSeatChargingScheme) {
		Page<DgSeatChargingScheme> pagebean = null;
		try {
			pagebean = dgSeatChargingSchemeService
					.getPage(dgSeatChargingScheme);
			pagebean.setPage(dgSeatChargingScheme.getPage());
			pagebean = (Page<DgSeatChargingScheme>) pageResult(pagebean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}

	@RequestMapping("/getNoSdItem")
	@ResponseBody
	public Object getNoSdItem(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			return null;
		} else {
			DgSeatChargingScheme pro = dgSeatChargingSchemeService
					.selectByPrimaryKey(Integer.parseInt(id));
			if (pro.getType() == 2 || pro.getType() == 3) {
				if (!StringUtils.isEmpty(id)) {
					List<DgSeatChargingSchemeS> items = dgSeatChargingSSchemeService
							.seleByPidNoSd(Integer.parseInt(id));
					return items;
				}
			} else {
				return null;
			}
		}
		return null;
	}

	@RequestMapping("/getSdItem")
	@ResponseBody
	public Object getSdItem(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "时段");
			map.put("iDelFlg", 0);
			List<DgPublicCode0> items = dgPublicCode0Service
					.selectSmallByParentName(map);
			List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
			for (DgPublicCode0 code : items) {
				Map<String, Object> s = new HashMap<String, Object>();
				s.put("id", "s" + code.getId());
				s.put("tLong", null);
				s.put("money", null);
				s.put("discount", null);
				s.put("sd", code.getId());
				s.put("sdName", code.getcName());
				ret.add(s);
			}
			return ret;
		} else {
			DgSeatChargingScheme pro = dgSeatChargingSchemeService
					.selectByPrimaryKey(Integer.parseInt(id));
			if (pro.getType() == 1 || pro.getType() == 4) {
				if (!StringUtils.isEmpty(id)) {
					List<DgSeatChargingSchemeS> items = dgSeatChargingSSchemeService
							.seleByPid(Integer.parseInt(id));
					return items;
				}
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", "时段");
				map.put("iDelFlg", 0);
				List<DgPublicCode0> items = dgPublicCode0Service
						.selectSmallByParentName(map);
				List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
				for (DgPublicCode0 code : items) {
					Map<String, Object> s = new HashMap<String, Object>();
					s.put("id", "s" + code.getId());
					s.put("tLong", null);
					s.put("money", null);
					s.put("discount", null);
					s.put("sd", code.getId());
					s.put("sdName", code.getcName());
					ret.add(s);
				}
				return ret;
			}
		}
		return null;
	}

	/**
	 * 
	 * 功能:保存主表信息
	 */
	@RequestMapping(value = "/saveBffPro")
	@ResponseBody
	public Object saveBffPro(HttpServletRequest request,
			HttpServletResponse response,
			DgSeatChargingScheme dgSeatChargingScheme) {
		Map<String, Object> ret = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(dgSeatChargingScheme.getId())) // 更新
		{
			if (StringUtils.isEmpty(dgSeatChargingScheme.getTrash())) {
				dgSeatChargingScheme.setTrash(0); // 回收站设置为0
			}
			if (StringUtils.isEmpty(dgSeatChargingScheme.getQySsdsf())) {
				dgSeatChargingScheme.setQySsdsf(0); // 回收站设置为0
			}
			dgSeatChargingSchemeService
					.updateByPrimaryKeySelective(dgSeatChargingScheme);

		} else // 插入
		{
			if (StringUtils.isEmpty(dgSeatChargingScheme.getTrash())) {
				dgSeatChargingScheme.setTrash(0); // 回收站设置为0
			}
			if (StringUtils.isEmpty(dgSeatChargingScheme.getQySsdsf())) {
				dgSeatChargingScheme.setQySsdsf(0); // 回收站设置为0
			}
			dgSeatChargingScheme.setTime(new Date());// 设置更新时间为当前时间
			dgSeatChargingSchemeService.insertBackId(dgSeatChargingScheme);
		}

		ret.put("success", true);
		ret.put("id", dgSeatChargingScheme.getId());
		return ret;
	}

	@RequestMapping("/saveBffProNext")
	@ResponseBody
	public Object saveBffProNext(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String childContent = request.getParameter("childContent");
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		try {
			dgConsumSeatManagerService.saveBffProNext(childContent, id, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		dgSeatChargingSchemeService.trash(ids);
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
		dgSeatChargingSSchemeService.deleteIds(ids);
		dgSeatChargingSchemeService.deleteIds(ids);
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
		dgSeatChargingSchemeService.restore(ids);
		ret.put("success", true);
		return ret;
	}

	@RequestMapping("/trash")
	public ModelAndView trash(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ModelAndView modelAndView = new ModelAndView(
				"bussinessMan/consumerSeat/bf_pro_trash");
		return modelAndView;
	}

	@RequestMapping("/exportSeatXls")
	public ModelAndView exportXls(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String sId = request.getParameter("id");
		if (!StringUtils.isEmpty(sId)) {
			DgSeatManager seatManager = dgSeatManagerService
					.selectByPrimaryKey(Integer.valueOf(sId));
			model.addAttribute("item", seatManager);
		}
		ModelAndView modelAndView = new ModelAndView(
				"bussinessMan/consumerSeat/consumerSeatXls");
		return modelAndView;
	}
	
	
	@RequestMapping("/exportConAreaXls")
	public ModelAndView exportConAreaXls(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String areaId = request.getParameter("areaId");
		if (!StringUtils.isEmpty(areaId)) {
			DgAreaManager areaManager = dgAreaManagerService.selectByAreaId(Integer
					.parseInt(areaId));
			List<DgSeatManager> seatManager = dgSeatManagerService
					.selectDetailBySeatId(Integer.valueOf(areaId));
			model.addAttribute("items", seatManager);
			model.addAttribute("area", areaManager);
		} 
		ModelAndView modelAndView = new ModelAndView(
				"bussinessMan/consumerSeat/consumerConcreteAreaXls");
		return modelAndView;
	}
	
	
	@RequestMapping("/exportAreaXls")
	public ModelAndView exportAreaXls(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<DgAreaManager> areaManager = dgAreaManagerService.selectAll();
		List<DgSeatManager> seatManager = dgSeatManagerService
				.selectAllDetailBySeatId();
		model.addAttribute("items", seatManager);
		model.addAttribute("areas", areaManager);
		ModelAndView modelAndView = new ModelAndView(
				"bussinessMan/consumerSeat/consumerAreaXls");
		return modelAndView;
	}

	private DgSeatManager initSeatManager(DgSeatManager seat) {
		if (seat != null) {
			if (seat.getQsscTx() == null) {
				seat.setQsscTx(0);
			}

			if (seat.getBffTiming() == null) {
				seat.setBffTiming(0);
			}
		}
		return seat;
	}

	private DgAreaManager initAreaManager(DgAreaManager area) {
		if (area.getBffDiscount() == null) {
			area.setBffDiscount(0);
		}

		if (area.getCxDiscount() == null) {
			area.setCxDiscount(0);
		}

		if (area.getBjDiscount() == null) {
			area.setBjDiscount(0);
		}

		if (area.getBffDiscount() == null) {
			area.setBffDiscount(0);
		}

		if (area.getFwfDiscount() == null) {
			area.setFwfDiscount(0);
		}

		if (area.getZdxfDiscount() == null) {
			area.setZdxfDiscount(0);
		}

		if (area.getBffFree() == null) {
			area.setBffFree(0);
		}

		if (area.getSjDiscount() == null) {
			area.setSjDiscount(0);
		}
		return area;
	}
}