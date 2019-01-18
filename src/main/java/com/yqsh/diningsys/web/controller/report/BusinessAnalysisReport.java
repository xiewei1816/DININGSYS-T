package com.yqsh.diningsys.web.controller.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.domain.Data;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.report.BusinessAnalysisService;

/**
 * Created on 2017-05-02 
 * 营业分析
 * @author hs
 */
@Controller
@RequestMapping("/report/businessAnalysisReport")
public class BusinessAnalysisReport extends BaseController{
	
	@Autowired
	private TbBisService tbBisService;
	
	@Autowired
	private BusinessAnalysisService businessAnalysisService;
	@RequestMapping("/index")
	public String index(Model model){
	    return "report/businessAnalysis/businessAnalysisReport_index";
	}
	
	@RequestMapping("/yysrfxbb")
	public String yysrfxbb(Model model){
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss");
		model.addAttribute("date", DateFormat.format(new Date()));
		model.addAttribute("time", TimeFormat.format(new Date()));
	    return "report/businessAnalysis/yysrfxbb";
	}
	
	@RequestMapping("/getTreeNodes")
	@ResponseBody
	public List<DgProductPhaseLeftmenu> getTreeNodes() {
		String id = getRequest().getParameter("id");
		List<DgProductPhaseLeftmenu> menu = new ArrayList<DgProductPhaseLeftmenu>();
	
		// 获取根节点
		if (StringUtils.isEmpty(id)) {
			DgProductPhaseLeftmenu m = new DgProductPhaseLeftmenu();
			m.setId(1);
			m.setName("营业分析");
			m.setChildCount(1);
			m.setParentId(0);
			menu.add(m);
		} else {
			menu.add(initTreeNode(2,"营业收入分析报表","/DININGSYS/report/businessAnalysisReport/yysrfxbb"));
			menu.add(initTreeNode(3,"营业统计日报","/DININGSYS/report/businessStatistics/date"));
			menu.add(initTreeNode(4,"营业统计周报","/DININGSYS/report/businessStatistics/week"));
			menu.add(initTreeNode(5,"营业统计月报","/DININGSYS/report/businessStatistics/month"));
		}
		return menu;
	}
	
	@ResponseBody
    @RequestMapping("/yysrfxbbData")
    public Object yysrfxbbData(String searchDate){
    	try {
    		Map ret = new HashMap();
    		List<Map<String, Object>> zb = new ArrayList<Map<String,Object>>();
    		Map<String, Object> by = businessAnalysisService.selectBusinessAnalysisBy(searchDate);
    		Map<String, Object> br = businessAnalysisService.selectBusinessAnalysisBr(searchDate);
    		Map<String, Object> zt = businessAnalysisService.selectBusinessAnalysisZt(searchDate);
    		List<Map<String, Object>> bis = businessAnalysisService.selectBusinessAnalysisSb(searchDate);
    		if(!StringUtils.isEmpty(bis)){
                zb.addAll(bis);
            }
    		if(!StringUtils.isEmpty(br)){
                zb.add(br);
            }
    		if(!StringUtils.isEmpty(zt)){
                zb.add(zt);
            }
    		if(!StringUtils.isEmpty(by)){
                zb.add(by);
            }
    		ret.put("zb",zb);
    		return ret;
    	} catch (Exception e) {
            e.printStackTrace();
        }
       return null;
   }
	
	private DgProductPhaseLeftmenu initTreeNode(Integer id,String name,String url){
		DgProductPhaseLeftmenu item = new DgProductPhaseLeftmenu();
		item.setId(id);
		item.setName(name);
		item.setChildCount(0);
		item.setParentId(1);
		item.setMurl(url);
		return item;
	}
	
    @RequestMapping("/yysrfxbbData_export")
    public String yysrfxbbData_export(String searchDate){
    	try {
    		Map ret = new HashMap();
    		List<Map<String, Object>> zb = new ArrayList<Map<String,Object>>();
    		Map<String, Object> by = businessAnalysisService.selectBusinessAnalysisBy(searchDate);
    		Map<String, Object> br = businessAnalysisService.selectBusinessAnalysisBr(searchDate);
    		Map<String, Object> zt = businessAnalysisService.selectBusinessAnalysisZt(searchDate);
    		List<Map<String, Object>> bis = businessAnalysisService.selectBusinessAnalysisSb(searchDate);
    		zb.addAll(bis);
    		zb.add(br);
    		zb.add(zt);
    		zb.add(by);
    		ret.put("zb",zb);
    		getRequest().setAttribute("data",ret);
    	} catch (Exception e) {
             e.printStackTrace();
        }
        return "report/businessAnalysis/yysrfxbb_Xls";
   }
}