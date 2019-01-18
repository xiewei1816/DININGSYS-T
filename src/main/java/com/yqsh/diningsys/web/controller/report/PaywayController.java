package com.yqsh.diningsys.web.controller.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yqsh.diningsys.core.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.report.ItemFileSell;
import com.yqsh.diningsys.web.model.report.Payway;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.report.ReportPayWayService;
import com.yqsh.diningsys.web.util.TableQueryUtil;

/**
 * 结算方式信息统计报表
 *
 * @author xiewei
 * @version 创建时间：2017年1月25日 上午10:34:12
 */
@Controller
@RequestMapping(value = "/report/payway")
public class PaywayController extends BaseController {

    @Autowired
    private ReportPayWayService reportPayWayService;
    @Autowired
    private DgPublicCode0Service dgPublicCode0Service;
    
    @RequestMapping("/index")
    public ModelAndView index(Model model) {
    	//查询字典数据
//    	model.addAttribute("list",dgPublicCode0Service.getAllDpcToPage());
    	model.addAttribute("list",reportPayWayService.selectAllPayWay());
        ModelAndView modelAndView = new ModelAndView("report/payway/payway-index");
        return modelAndView;
    }
    
    /**
     * 查询结算方式信息
     * @param cwName 结算方式
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
	@RequestMapping("/selectPayWayReportList")
	@ResponseBody
	public Object selectPayWayReportList(HttpServletRequest request,HttpServletResponse response,Payway payway) {
		com.yqsh.diningsys.core.util.Page<Payway> pagebean = null;
    	String startTime = payway.getStartTime();
    	String endTime = payway.getEndTime();
    	List<String> strings = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
    	payway.setTableSuffixList(strings);
		if(strings.size() < 1) {
			pagebean = new Page<>();
			pagebean.setTotal(0);
			pagebean.setPage(1);
			pagebean.setRecords(0);
			return pagebean;
		}
    	try {
			pagebean = reportPayWayService.getPaywayPageList(payway);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(payway.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	
    /**
     * 导出结算方式信息Excel
     * @param cwName 结算方式
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @RequestMapping(value = "exportXls")
    public ModelAndView exportXls(Model model,String cwName,String startTime,String endTime) {
    	List<Map<String,Object>> payWayList = reportPayWayService.selectPayWayReportList(cwName,startTime,endTime);
        model.addAttribute("payWayList", payWayList);
        ModelAndView modelAndView = new ModelAndView("report/payway/exportXls");
        return modelAndView;
    }

}
