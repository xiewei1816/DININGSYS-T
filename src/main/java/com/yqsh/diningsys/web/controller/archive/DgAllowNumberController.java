package com.yqsh.diningsys.web.controller.archive;

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
import com.yqsh.diningsys.web.model.archive.DgAllowNumber;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.service.archive.DgAllowNumberService;

@Controller
@RequestMapping("/dgAllowNumber")
public class DgAllowNumberController extends BaseController {
	@Autowired
	private DgAllowNumberService dgAllowNumberService;
	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("archive/allowNumber/allowNumber-index");
		return model;
	}
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,
			HttpServletResponse response, DgAllowNumber DgAllowNumber) {
		com.yqsh.diningsys.core.util.Page<DgAllowNumber> pagebean = null;
		try {
			pagebean = dgAllowNumberService.getListByPage(DgAllowNumber);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(DgAllowNumber.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}

	@RequestMapping(value = "/addOrUpdate")
	public ModelAndView addOrUpdate() {
		String id = getRequest().getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			getRequest().setAttribute("allowNumber",dgAllowNumberService.selectById(Integer.valueOf(id)));

		}
		ModelAndView model = new ModelAndView("archive/allowNumber/allowNumber-add");
		return model;
	}

	/**
	 * 新增、修改公共代码信息保存
	 * 
	 * @return
	 */
	@RequestMapping(value = "/insertOrUpdate")
	@ResponseBody
	public Object insertOrUpdate(DgAllowNumber DgAllowNumber) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			return dgAllowNumberService.insertOrUpdate(DgAllowNumber);
		} catch (Exception e) {
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/selectById")
	@ResponseBody
	public Object selectById(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			DgAllowNumber dan = dgAllowNumberService.selectById(id);
			result.put("dan", dan);
   			result.put("success", true);
		} catch (Exception e) {
			result.put("success",false);
			result.put("error", e.getMessage());
		}
		return result;
	}

	@RequestMapping("/delAllowNumber")
	@ResponseBody
	public ResultInfo delAllowNumber(String ids) {
		try {
			dgAllowNumberService.deleteById(ids);
		} catch (Exception e) {
			e.printStackTrace();
			returnFail();
		}
		return returnSuccess();
	}

}