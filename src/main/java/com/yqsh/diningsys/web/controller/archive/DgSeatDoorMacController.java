package com.yqsh.diningsys.web.controller.archive;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.archive.DgSeatDoorinfo;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgSeatDoorinfoService;

@Controller
@RequestMapping("/dgSeatDoorMac")
public class DgSeatDoorMacController extends BaseController {
	@Autowired
	private DgSeatDoorinfoService dgSeatDoorinfoService;
	@Autowired
	private DgConsumerSeatService dgConsumerSeatService;
	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("archive/doormac/doormac");
		return model;
	}

	@RequestMapping(value = "/addOrUpdate")
	public ModelAndView addOrUpdate() {
		String id = getRequest().getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			getRequest().setAttribute("item",
					dgSeatDoorinfoService.selectById(Integer.valueOf(id)));
			getRequest().setAttribute("itemSeats",
					dgSeatDoorinfoService.getSeatNotInDoorInfo());
		}
		else {
			
			getRequest().setAttribute("itemSeats",
					dgSeatDoorinfoService.getSeatNotInDoorInfo());
		}
		ModelAndView model = new ModelAndView("archive/doormac/doormac_add");
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
			HttpServletResponse response, DgSeatDoorinfo dgSeatDoorinfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			return dgSeatDoorinfoService.insertOrUpdate(dgSeatDoorinfo);
		} catch (Exception e) {
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,
			HttpServletResponse response, DgSeatDoorinfo dgSeatDoorinfo) {
		com.yqsh.diningsys.core.util.Page<DgSeatDoorinfo> pagebean = null;
		try {
			pagebean = dgSeatDoorinfoService.getListByPage(dgSeatDoorinfo);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(dgSeatDoorinfo.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}

	@RequestMapping("/delData")
	@ResponseBody
	public ResultInfo delData(String ids) {
		try {
			dgSeatDoorinfoService.deleteByMac(ids);
		} catch (Exception e) {
			e.printStackTrace();
			returnFail();
		}
		return returnSuccess();
	}

}