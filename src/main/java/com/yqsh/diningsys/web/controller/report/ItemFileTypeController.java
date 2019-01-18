package com.yqsh.diningsys.web.controller.report;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.service.report.BackReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created on 2017-02-09 16:13
 * 品项类别销售
 * @author zhshuo
 */
@Controller
@RequestMapping("/report/itemFileType")
public class ItemFileTypeController extends BaseController{

    @Autowired
    private BackReportService backReportService;

    @RequestMapping("/index")
    public String index(Model model){
        return "report/itemFileTypeReport/itemFileType_index";
    }

    @ResponseBody
    @RequestMapping("/dataSearch_index")
    public List<Map> dataSearch_index(String startTime,String endTime){
        try {
            //2017年8月15日17:10:35 修改店内的品项内别销售为查询已结账单数据
            return backReportService.queryItemFileTypeIndex(startTime,endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @ResponseBody
    @RequestMapping("/dataSearch_small")
    public List<Map> dataSearch_small(String bigNum,String startTime,String endTime){
        try {
            if(StringUtils.isEmpty(bigNum)) return null;
            return backReportService.queryItemFileTypeSmall(bigNum,startTime,endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/dataSearch_item")
    public List<Map> dataSearch_item(String smallNum,String startTime,String endTime){
        try {
            if(StringUtils.isEmpty(smallNum)) return null;
            return backReportService.queryItemFileTypeItem(smallNum,startTime,endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/dataSearch_openWaters")
    public List<Map> dataSearch_openWaters(String itemFileNum,String startTime,String endTime){
        try {
            if(StringUtils.isEmpty(itemFileNum)) return null;
            return backReportService.queryItemFileTypeOpenWaters(itemFileNum,startTime,endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/dataSearchDetailsIndex/{serviceId}/{clearingTime}")
    public String dataSearchDetailsIndex(@PathVariable Integer serviceId,@PathVariable String clearingTime,Model model){
        try {
            List<Integer> inList = backReportService.dataSearchDetailsIndex(serviceId,clearingTime);
            model.addAttribute("tableDate",clearingTime);
            model.addAttribute("inList",inList);
            return "report/itemFileTypeReport/itemFileTypeDetail_index";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "500";
    }

	@ResponseBody
    @RequestMapping("/dataSearch_details_next/{serviceId}/{tableDate}")
    public Map dataSearch_details_next(@PathVariable Integer serviceId,@PathVariable String tableDate){
        try {
            Map map = backReportService.dataSearchDetailsNext(serviceId,tableDate);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 数据导出
     * @param model
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "exportXls0")
    public ModelAndView exportXls0(Model model,String startTime,String endTime) {
    	List<Map> itemFileTypeList = backReportService.queryItemFileTypeIndex(startTime,endTime);
        model.addAttribute("itemFileTypeList", itemFileTypeList);
        ModelAndView modelAndView = new ModelAndView("report/itemFileTypeReport/exportXls");
        return modelAndView;
    }
    @RequestMapping(value = "exportXls1")
    public ModelAndView exportXls1(Model model,String bigNum,String startTime,String endTime) {
    	if(StringUtils.isEmpty(bigNum)) return null;
    	List<Map> itemFileTypeSmallList = backReportService.queryItemFileTypeSmall(bigNum,startTime,endTime);
        model.addAttribute("itemFileTypeSmallList", itemFileTypeSmallList);
        ModelAndView modelAndView = new ModelAndView("report/itemFileTypeReport/exportXls1");
        return modelAndView;
    }
    @RequestMapping(value = "exportXls2")
    public ModelAndView exportXls2(Model model,String smallNum, String startTime,String endTime) {
    	if(StringUtils.isEmpty(smallNum)) return null;
    	List<Map> itemFileTypeItemList = backReportService.queryItemFileTypeItem(smallNum,startTime,endTime);
        model.addAttribute("itemFileTypeItemList", itemFileTypeItemList);
        ModelAndView modelAndView = new ModelAndView("report/itemFileTypeReport/exportXls2");
        return modelAndView;
    }
    @RequestMapping(value = "exportXls3")
    public ModelAndView exportXls3(Model model,String itemFileNum, String startTime,String endTime) {
    	if(StringUtils.isEmpty(itemFileNum)) return null;
    	List<Map> itemFileTypeOpenWaterList = backReportService.queryItemFileTypeOpenWaters(itemFileNum,startTime,endTime);
        model.addAttribute("itemFileTypeOpenWaterList", itemFileTypeOpenWaterList);
        ModelAndView modelAndView = new ModelAndView("report/itemFileTypeReport/exportXls3");
        return modelAndView;
    }

}
