package com.yqsh.diningsys.web.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.report.ReportPayWayService;
/**
 * Created on 2017-02-07 13:09
 * 区域开台人均
 * @author heshuai
 */
@Controller
@RequestMapping("/report/regionalOpenBillReport")
public class RegionalOpenBillReport extends BaseController{
	
	@Autowired
	private ReportPayWayService reportPayWayService;
	
    @Autowired
    private TbBisService tbBisService;
    
	@RequestMapping("/index")
	public String index(Model model){
		
        List<TbBis> tbBiss = tbBisService.selectAllBis();
        model.addAttribute("TbBis",tbBiss);
	    return "report/regionalOpenBillReport/regionalOpenBillReport_index";
	}
	
	@RequestMapping("/analysisIndex")
	public String analysisIndex(Model model){
		
        List<TbBis> tbBiss = tbBisService.selectAllBis();
        model.addAttribute("TbBis",tbBiss);
	    return "report/regionalOpenBillReport/regionalOpenBillReportAnalysis_index";
	}
	
	@ResponseBody
    @RequestMapping("/getData")
    public Object getData(String searchDate,String bis){
    	try {
            List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
    		List<Map<String,Object>> skl =  reportPayWayService.selectRegionalOpenBill(searchDate,bis);
    		for(Map<String,Object> s:skl){
    			Map rItem = new HashMap();
    			rItem.put("areaName",s.get("areaName"));
    			rItem.put("seatCount",s.get("seatCount"));
    			rItem.put("type","当日");
    			rItem.put("waterCount",s.get("jropenCount") == null ?0 :s.get("jropenCount"));
    			rItem.put("peopleCount",s.get("jrpeopleCount") == null ?0 :s.get("jrpeopleCount"));
    			rItem.put("moneyCount",s.get("jrsummoney") == null ?0 :s.get("jrsummoney"));
    			rItem.put("sj",s.get("jrsj") == null ?0 :s.get("jrsj"));
    			rItem.put("itemCount",s.get("jritemcount") == null ?0 :s.get("jritemcount"));
    			rItem.put("ktl",s.get("jrktl") == null ?0 :s.get("jrktl"));
    			ret.add(rItem);
    			
    			Map yItem = new HashMap();
    			yItem.put("areaName",s.get("areaName"));
    			yItem.put("seatCount",s.get("seatCount"));
    			yItem.put("type","月累计");
    			yItem.put("waterCount",s.get("byopenCount") == null ?0 :s.get("byopenCount"));
    			yItem.put("peopleCount",s.get("bypeopleCount") == null ?0 :s.get("bypeopleCount"));
    			yItem.put("moneyCount",s.get("bysummoney") == null ?0 :s.get("bysummoney"));
    			yItem.put("sj",s.get("bysj") == null ?0 :s.get("bysj"));
    			yItem.put("itemCount",s.get("byitemcount") == null ?0 :s.get("byitemcount"));
    			yItem.put("ktl",s.get("byktl") == null ?0 :s.get("byktl"));
    			ret.add(yItem);
    			
    			Map rjItem = new HashMap();
    			rjItem.put("areaName",s.get("areaName"));
    			rjItem.put("seatCount",s.get("seatCount"));
    			rjItem.put("type","日均");
    			rjItem.put("waterCount",s.get("rjopenCount") == null ?0 :s.get("rjopenCount"));
    			rjItem.put("peopleCount",s.get("rjpeopleCount") == null ?0 :s.get("rjpeopleCount"));
    			rjItem.put("moneyCount",s.get("rjsummoney") == null ?0 :s.get("rjsummoney"));
    			rjItem.put("sj","");
    			rjItem.put("itemCount",s.get("rjitemcount") == null ?0 :s.get("rjitemcount"));
    			double rjktl = 0.0;
    			String rjktls;
    			if(Double.valueOf(s.get("byopenCount").toString()) > 0){
    				Integer byOpenCount = Integer.valueOf(s.get("byopenCount").toString());
    				Integer seatCount = Integer.valueOf(s.get("seatCount").toString());
    				rjktl  = MathExtend.divide(byOpenCount,seatCount*60,2)*100;
    			}
    			rjItem.put("ktl",rjktl);
    			ret.add(rjItem);
    		}
    		return ret;
    	} catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
	
	@SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping("/getAnalysisData")
    public Object getAnalysisData(String searchDate,String bis){
    	try {
            List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
    		List<Map<String,Object>> skl =  reportPayWayService.selectRegionalOpenAnalysisBill(searchDate,bis);
    		for(Map<String,Object> s:skl){
    			Map rItem = new HashMap();
    			rItem.put("areaName",s.get("areaName")); //区域名称
    			rItem.put("seatCount",s.get("seatCount")); //客位数量
    			rItem.put("bisName",s.get("bisName")); //市别
    			rItem.put("jrwaterCount",s.get("jropenCount") == null ?0 :s.get("jropenCount"));//本日开台数
    			rItem.put("jrpeopleCount",s.get("jrpeopleCount") == null ?0 :s.get("jrpeopleCount")); //今日客流数
    			rItem.put("jrmoneyCount",s.get("jrsummoney") == null ?0 :s.get("jrsummoney")); //今日营业额
    			rItem.put("jrzjxf",s.get("jrzjxf") == null ?0 :s.get("jrzjxf")); //今日桌均消费
    			rItem.put("jritemCount",s.get("jritemcount") == null ?0 :s.get("jritemcount")); //今日菜品数量
    			rItem.put("bysj",s.get("bysj") == null ?0 :s.get("bysj")); //本月人均
    			rItem.put("bywaterCount",s.get("byopenCount") == null ?0 :s.get("byopenCount")); //本月开台数
    			rItem.put("bypeopleCount",s.get("bypeopleCount") == null ?0 :s.get("bypeopleCount")); //月客流数
    			rItem.put("bymoneyCount",s.get("bysummoney") == null ?0 :s.get("bysummoney")); //月营业额
    			rItem.put("byzjxf",s.get("byzjxf") == null ?0 :s.get("byzjxf")); //本月桌均消费
    			rItem.put("byitemCount",s.get("byitemcount") == null ?0 :s.get("byitemcount")); //本月菜品数量
    			rItem.put("jrsj",s.get("jrsj") == null ?0 :s.get("jrsj")); //今日人均
    			ret.add(rItem);
    		}
    		return ret;
    	} catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
}