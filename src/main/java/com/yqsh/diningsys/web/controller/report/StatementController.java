package com.yqsh.diningsys.web.controller.report;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.report.Payway;
import com.yqsh.diningsys.web.model.report.Statement;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.report.BackReportService;
import com.yqsh.diningsys.web.util.TableQueryUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 2017-02-07 13:09
 * 结账单查询
 * @author zhshuo
 */
@Controller
@RequestMapping("/report/statement")
public class StatementController extends BaseController{

    @Autowired
    private TbBisService tbBisService;

    @Autowired
    private DgPosService dgPosService;

    @Autowired
    private DgConsumptionAreaService dgConsumptionAreaService;

    @Autowired
    private BackReportService backReportService;


    @RequestMapping("/index")
    public String index(Model model)
    {
        List<TbBis> tbBiss = tbBisService.selectAllBis();
        model.addAttribute("TbBis",tbBiss);

        List<Map<String, Object>> allPosList = dgPosService.getAllPosList();
        model.addAttribute("allPosList",allPosList);

        List<DgConsumptionArea> dgConsumptionAreas = dgConsumptionAreaService.selectAllArea();
        model.addAttribute("allConsumerArea",dgConsumptionAreas);

        return "report/statement/checkout_index";
    }

    @ResponseBody
    @RequestMapping("/dataSearch")
    public Object dataSearch(HttpServletRequest request,HttpServletResponse response,Statement statement){
    	com.yqsh.diningsys.core.util.Page<Payway> pagebean = null;
    	String startTime = statement.getStartTime();
    	String endTime = statement.getEndTime();
    	List<String> strings = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
    	statement.setTableSuffixList(strings);
    	if(strings.size() < 1){
            pagebean = new Page<>();
            pagebean.setTotal(0);
            pagebean.setPage(1);
            pagebean.setRecords(0);
            return pagebean;
        }
    	Boolean flag = TableQueryUtil.openDayReportCheck(startTime, endTime);
    	statement.setFlag(flag);
    	try {
    		pagebean = backReportService.getStatementPageList(statement);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(statement.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }
    
    @RequestMapping(value = "exportXls")
    public ModelAndView exportXls(Model model,String startTime, String endTime,Integer consumerArea,Integer bis, Integer pos,String clearingNum) {
    	List<Map> statementList = backReportService.statementQuery(startTime,endTime,consumerArea,bis,pos, clearingNum);
        model.addAttribute("statementList", statementList);
        ModelAndView modelAndView = new ModelAndView("report/statement/exportXls");
        return modelAndView;
    }


}
