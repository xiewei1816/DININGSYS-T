package com.yqsh.diningsys.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.web.model.api.SysBusinessLog;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;
import com.yqsh.diningsys.web.service.api.APILogService;
import com.yqsh.diningsys.web.service.api.ReserveManagerService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
@Controller
@RequestMapping(value = "/yqshapi/log")
public class APILogController extends ApiBaseController{
	@Autowired
	private APILogService apiLogService;
	@Autowired
	private DgConsumerSeatService dgConsumerSeatService;
	/**
	 * 前台日志管理
	 * @param request
	 * @return
	 */
	@RequestMapping("/getLogManager")
	public ModelAndView getLogManager(
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView("api/log/log_index");
		model.addObject("seats", dgConsumerSeatService.getAllList(new DgConsumerSeat()));
    	return model;
	}
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(SysBusinessLog sysBusinessLog) {
		sysBusinessLog.setTable_end(getTableEnd(sysBusinessLog));
		com.yqsh.diningsys.core.util.Page<SysBusinessLog> pagebean = null;
		try {
			pagebean = apiLogService.getListByPage(sysBusinessLog);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(sysBusinessLog.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	
	
	private String getTableEnd(SysBusinessLog log){
		String searchTime = log.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat table_format = new SimpleDateFormat("yyyy_MM");
		try {
			Date seatDate = format.parse(searchTime);
			return table_format.format(seatDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    /**
	 * form表单提交 Date类型数据绑定
	 * <功能详细描述>
	 * @param binder
	 * @see [类、类#方法、类#成员]
	 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}
}
