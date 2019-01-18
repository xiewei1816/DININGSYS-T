package com.yqsh.diningsys.api;

import java.io.UnsupportedEncodingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.alibaba.fastjson.JSONObject;
import com.yqsh.catering.web.mq.DepositOrderMessage;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.api.DgCallService;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;
import com.yqsh.diningsys.web.model.archive.DgReserveSeatidsList;
import com.yqsh.diningsys.web.service.api.ReserveManagerService;

@Controller
@RequestMapping(value = "/yqshapi/yd")
public class APIReserveManagerController extends BaseController{
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	private SimpleDateFormat rqFormat = new SimpleDateFormat("yyyy-MM-dd");  
	@Autowired
	private ReserveManagerService reserveManagerService;
	/**
	 * 预定管理
	 * @param request
	 * @return
	 */
	@RequestMapping("/getYdManager")
	public ModelAndView getYdManager(
			HttpServletRequest request) {
		String posId = request.getParameter("posId");
		request.setAttribute("posId", posId);
		ModelAndView model = new ModelAndView("api/yd/yd");
    	return model;
	}
	
	/**
	 * 预定管理
	 * @param request
	 * @return
	 */
	@RequestMapping("/getYdTrashManager")
	public ModelAndView getYdTrashManager(
			HttpServletRequest request) {
		String posId = request.getParameter("posId");
		request.setAttribute("posId", posId);
		ModelAndView model = new ModelAndView("api/yd/yd_del");
    	return model;
	}
	
	/**
	 * 预定管理(线上)
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOnlineYdManager")
	public ModelAndView getOnlineYdManager(
			HttpServletRequest request) {
		String posId = request.getParameter("posId");
		request.setAttribute("posId", posId);
		String onlineReserveUrl = "http://"+CacheUtil.getURLInCache("ONLINE_DOMAIN")+"/app/shop/order/queryDepositOrder/"+CacheUtil.getURLInCache("queuingSYS_ID");
		request.setAttribute("onlineReserveUrl", onlineReserveUrl);
		ModelAndView model = new ModelAndView("api/yd/yd_online");
		reserveManagerService.deleteCallInfo();
    	return model;
	}
	
	
	/**
	 * 预定管理(线上) 拒绝列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOnlineYdRefuseManager")
	public ModelAndView getOnlineYdRefuseManager(
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView("api/yd/yd_online_del");
    	return model;
	}
	
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(DgReserveManager dgReserveManager) {
		com.yqsh.diningsys.core.util.Page<DgReserveManager> pagebean = null;
		try {
			pagebean = reserveManagerService.getListByPage(dgReserveManager);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(dgReserveManager.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	
	
	@RequestMapping("/getSeatPage")
	public ModelAndView getSeatPage(
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView("api/yd/ydSeats");
    	return model;
	}
	
	@RequestMapping("/getOnlineSeatPage")
	public ModelAndView getOnlineSeatPage(
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView("api/yd/ydOnlineSeats");
    	return model;
	}
	
	
	@RequestMapping("/getRefusePage")
	public ModelAndView getRefusePage(
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView("api/yd/yd_refuse");
    	return model;
	}
	
	@RequestMapping(value = "/getSeatList")
	@ResponseBody
	public Object getSeatList(DgReserveManager dg) {
		try {
			dg.setSearch(new String(dg.getSearch().getBytes("iso-8859-1"), "utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return reserveManagerService.getAllSeat(dg);
	}
	
	@RequestMapping(value = "/addOrUpdate")
	public ModelAndView addOrUpdate() {
		String id = getRequest().getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			getRequest().setAttribute("item",
					reserveManagerService.selectById(Integer.valueOf(id)));
			
			getRequest().setAttribute("seats",reserveManagerService.selectYdSeatById(Integer.valueOf(id)));
		}
		ModelAndView model = new ModelAndView("api/yd/yd_edit");
		return model;
	}

	
	@RequestMapping(value = "/addOrUpdateOnline")
	public ModelAndView addOrUpdateOnline() {
		String online = getRequest().getParameter("online");
		String posId = getRequest().getParameter("posId");
		getRequest().setAttribute("posId", posId);
		if(!StringUtils.isEmpty(online)){
			DgCallService dcs = reserveManagerService.selectCallInfo(Integer.valueOf(online));
			DepositOrderMessage dom = JSONObject.parseObject(dcs.getOwNum(), DepositOrderMessage.class);	
			DgReserveManager drm = new DgReserveManager();
			drm.setCrId(dom.getSeatType());
			drm.setName(dom.getContactuser());
			drm.setSex(dom.getGender().equals("1")?1:2);
			drm.setNumber(dom.getPersons());
			drm.setOnlineMsg(Integer.valueOf(online));
			try {
				drm.setTime(dateFormat.parse(dom.getReserveTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getRequest().setAttribute("item", drm);
		}
		ModelAndView model = new ModelAndView("api/yd/yd_edit_online");
		return model;
	}
	
	/**
	 * 新增、修改公共代码信息保存
	 * 
	 * @param request
	 * @param response
	 * @param dpc
	 * @return
	 */
	@RequestMapping(value = "/insertOrUpdate")
	@ResponseBody
	public Object insertOrUpdate(HttpServletRequest request,
			HttpServletResponse response, DgReserveManager dgReserveManager,DgReserveSeatidsList list) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = reserveManagerService.insertOrUpdate(dgReserveManager,list);
		} catch (Exception e) {			
			if(e instanceof RuntimeException){
				result.put("msg", "连接线上平台失败!");	
			} else {
				result.put("msg", e.getMessage());	
			}
		}
		return result;
	}
	
	@RequestMapping("/delData")
	@ResponseBody
	public ResultInfo delData(String ids) {
		try {
			reserveManagerService.deleteByIds(ids);
		} catch (Exception e) {
			e.printStackTrace();
			returnFail();
		}
		return returnSuccess();
	}
	
	/**
	 * 拒绝线上预定信息
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delOnelineData")
	@ResponseBody
	public ResultInfo delOnelineData(String ids,String msg) {
		try {
			reserveManagerService.refuseOnline(ids,msg);
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof RuntimeException){
				returnFail("连接线上平台失败!");	
			} else {
				returnFail(e.getMessage());	
			}
		}
		return returnSuccess();
	}
	
	
	@RequestMapping(value = "/getOnlinePageList")
	@ResponseBody
	public Object getOnlinePageList(DepositOrderMessage depositOrderMessage) {
		com.yqsh.diningsys.core.util.Page<DepositOrderMessage> pagebean = null;
		try {
			pagebean = reserveManagerService.getOnlinePageList(depositOrderMessage);
			if(pagebean != null){
				pagebean.setTotal(pagebean.getContext().getPageCount());
				pagebean.setPage(pagebean.getPage());
				pagebean.setRecords(pagebean.getContext().getTotal());	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	
	
	
	@RequestMapping("/insertOnlineReserve")
	@ResponseBody
	public ResultInfo insertOnlineReserve(DepositOrderMessage dom) {
		try {
			
			reserveManagerService.insertOnlineReserve(dom);
		} catch (Exception e) {
			e.printStackTrace();
			returnFail();
		}
		return returnSuccess();
	}
	
	
	
	/**
	 * 
	 * 新增线上数据
	 * @param request
	 * @param response
	 * @param dpc
	 * @return
	 */
	@RequestMapping(value = "/insertOnline")
	@ResponseBody
	public Object insertOnline(HttpServletRequest request,
			HttpServletResponse response, DgReserveManager dgReserveManager,DgReserveSeatidsList list) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = reserveManagerService.insertOrUpdate(dgReserveManager,list);
		} catch (Exception e) {		
			if(e instanceof RuntimeException){
				result.put("msg", "连接线上平台失败!");	
			} else {
				result.put("msg", e.getMessage());	
			}
		}
		return result;
	}
	
	
    /**
	 * form表单提交 Date类型数据绑定
	 * <功能详细描述>
	 * @param binder
	 * @see [类、类#方法、类#成员]
	 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}

	@RequestMapping("/exportXls")
	public Object selectPayWayReportList(DgReserveManager dgReserveManager, Model model) {
		try {
			List<DgReserveManager> reserveManagerList = reserveManagerService.getDgReserveManagerList(dgReserveManager);
			model.addAttribute("reserveManagerList",reserveManagerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "api/yd/exportXls";
	}
}
