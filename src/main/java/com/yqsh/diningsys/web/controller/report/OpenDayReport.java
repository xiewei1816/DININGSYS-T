package com.yqsh.diningsys.web.controller.report;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.report.BackReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created on 2017-03-20 9:35
 * 日营业报表
 * @author zhshuo
 */
@Controller
@RequestMapping("/report/openDay")
public class OpenDayReport extends BaseController{

    @Autowired
    private BackReportService backReportService;

    @Autowired
    private TbOrgService tbOrgService;

    @Autowired
    private TbBisService tbBisService;

    @Autowired
    private DgPosService dgPosService;

    @Autowired
    private DgConsumptionAreaService dgConsumptionAreaService;

    @RequestMapping("/index")
    public String index(Model model){
        List<TbOrg> tbOrgs = tbOrgService.selectAllTbOrg();
        model.addAttribute("org",tbOrgs);

        List<TbBis> tbBiss = tbBisService.selectAllBis();
        model.addAttribute("bis",tbBiss);

        List<Map<String, Object>> allPosList = dgPosService.getAllPosList();
        model.addAttribute("allPosList",allPosList);

        List<DgConsumptionArea> allArea = dgConsumptionAreaService.selectAllArea();
        model.addAttribute("allArea",allArea);

        return "report/openDay/openDay_index";
    }

    @RequestMapping("/body")
    public String content(String startTime, String endTime, String orgCode, Integer bis,
                          Integer pos, Integer timeType, Integer moneyType, Integer area, Model model){
        try {
            Map map = backReportService.openDayReportDataSearch(startTime, endTime, orgCode, bis,
                    pos, timeType, moneyType, area);

            model.addAttribute("attrs",map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "report/openDay/openDay_content";
    }
    
    @RequestMapping("/itemFileDataSaleStatistical")
    @ResponseBody
    public Object itemFileDataSaleStatistical(String startTime, String endTime,
                              Integer pos,Integer area,Integer timeType){
        try{
        	List<Map> maps = backReportService.itemFileDataSaleStatistical(startTime, endTime,
                    pos, area, timeType);
            return maps;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
	@RequestMapping("/exportXls")
	public Object selectPayWayReportList(String startTime, String endTime, String orgCode, Integer bis,
            Integer pos, Integer timeType, Integer moneyType, Integer area, Model model) {
		 try {
	             Map map = backReportService.openDayReportDataSearch(startTime, endTime, orgCode, bis,
	                    pos, timeType, moneyType, area);

	             model.addAttribute("attrs",map);

                 //品项销售统计
                 List<Map> itemFileDataSaleStatisticalList = backReportService.itemFileDataSaleStatistical(startTime, endTime, pos, area, timeType);
                 model.addAttribute("itemFileDataSaleStatisticalList",itemFileDataSaleStatisticalList);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "report/openDay/exportXls";
	}
}
