package com.yqsh.diningsys.web.controller.sysSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;
import com.yqsh.diningsys.web.model.archive.DgReserveSeatidsList;
import com.yqsh.diningsys.web.model.archive.DgServing;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.model.sysSettings.SysBackupDatabase;
import com.yqsh.diningsys.web.model.sysSettings.SysLogQuery;
import com.yqsh.diningsys.web.service.sysSettings.DgUrlSettingService;
import com.yqsh.diningsys.web.service.sysSettings.SysBackupDataBaseService;

@Controller
@RequestMapping("/urlset")
public class UrlSetController extends BaseController {
	@Autowired
	private DgUrlSettingService dgUrlSettingService;

	@RequestMapping(value = "/index")
	public ModelAndView getAllLog() {
		ModelAndView model = new ModelAndView("sysSettings/urlset/index");
		return model;
	}

	@RequestMapping(value = "/toAdd")
	public ModelAndView toBackup(HttpServletRequest request) {
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			request.setAttribute("item",
					dgUrlSettingService.selectByPrimaryKey(Integer.valueOf(id)));
		}
		ModelAndView model = new ModelAndView("sysSettings/urlset/url_add");
		return model;
	}

	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,
			HttpServletResponse response, DgUrlSetting dgUrlSetting) {
		com.yqsh.diningsys.core.util.Page<DgUrlSetting> pagebean = null;
		try {

			pagebean = dgUrlSettingService.getListByPage(dgUrlSetting);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(dgUrlSetting.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}

	@RequestMapping(value = "/insertOrUpdate")
	@ResponseBody
	public Object insertOrUpdate(HttpServletRequest request,
			HttpServletResponse response, DgReserveManager dgReserveManager,
			DgUrlSetting dgUrlSetting) {
		try {
			int count = dgUrlSettingService.insertOrUpdate(dgUrlSetting);
			if (count < 1) {
				return returnFail("已存在相同的编码或名称");
			} else {
				//加入缓存
				CacheUtil.updateURLInCache(dgUrlSettingService.selectAllData());
				return returnSuccess();
			}
		} catch (Exception e) {
			return returnFail();
		}
	}

}
