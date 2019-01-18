package com.yqsh.diningsys.web.controller.report;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.report.ItemFileSell;
import com.yqsh.diningsys.web.service.archive.DgItemFileService;
import com.yqsh.diningsys.web.service.archive.TbBisService;
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
 * Created on 2017-02-06 10:28
 * 品项销售
 * @author zhshuo
 */
@Controller
@RequestMapping("/report/itemFileSell")
public class ItemFileSellController extends BaseController{

    @Autowired
    private TbBisService tbBisService;

    @Autowired
    private DgItemFileService dgItemFileService;

    @RequestMapping("/index")
    public String index(Model model)
    {
        List<TbBis> tbBiss = tbBisService.selectAllBis();
        model.addAttribute("TbBis",tbBiss);
        return "report/itemFileSell/itemFileSell_index";
    }

    @ResponseBody
    @RequestMapping("/dataSearch")
    public Object dataSearch(HttpServletRequest request,HttpServletResponse response,ItemFileSell itemFileSell){
    	com.yqsh.diningsys.core.util.Page<ItemFileSell> pagebean = null;
    	String startTime = itemFileSell.getStartTime();
    	String endTime = itemFileSell.getEndTime();
    	List<String> strings = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
    	itemFileSell.setTableSuffixList(strings);
        if(strings == null ||strings.size() < 1){
            pagebean = new Page<>();
            pagebean.setTotal(0);
            pagebean.setPage(1);
            pagebean.setRecords(0);
            return pagebean;
        }
    	try {
    		String dataType = itemFileSell.getSearchDataType(); //1-明细 2-汇总
    		if(dataType.equals("1")){
    			pagebean = dgItemFileService.getItemFileSellDetailsPageList(itemFileSell);
    			pagebean.setTotal(pagebean.getContext().getPageCount());
    			pagebean.setPage(itemFileSell.getPage());
    			pagebean.setRecords(pagebean.getContext().getTotal());
    		}else if(dataType.equals("2")){
    			pagebean = dgItemFileService.getItemFileSellSummaryPageList(itemFileSell);
    			pagebean.setTotal(pagebean.getContext().getPageCount());
    			pagebean.setPage(itemFileSell.getPage());
    			pagebean.setRecords(pagebean.getContext().getTotal());
    		}else{
    			return null;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
    }
    
    @RequestMapping(value = "detailExportXls")
    public ModelAndView detailExportXls(Model model,String startTime, String endTime,Integer itemFileType, Integer searchDataType,Integer bis,String itemFileName,String serviceType) {
    	List<Map> itemFileSellDetailList = dgItemFileService.selectItemFileSellDetails(startTime, endTime, itemFileType, searchDataType, bis, itemFileName,serviceType);
    	//更新服务方式的对应值
    	for (int i = 0; i < itemFileSellDetailList.size(); i++) {
			Map map = itemFileSellDetailList.get(i);
			if(map != null){
				Integer serviceType1 = Integer.parseInt(map.get("service_type")+"");
				String service_Type = changeServiceType(serviceType1);
				map.put("service_type", service_Type);
			}
		}
        model.addAttribute("itemFileSellDetailList", itemFileSellDetailList);
        ModelAndView modelAndView = new ModelAndView("report/itemFileSell/detailExportXls");
        return modelAndView;
    }
    
    @RequestMapping(value = "summaryExportXls")
    public ModelAndView summaryExportXls(Model model,String startTime, String endTime,Integer itemFileType, Integer searchDataType,Integer bis,String itemFileName,String serviceType) {
    	List<Map> itemFileSellSummaryList = dgItemFileService.selectItemFileSellDetails(startTime, endTime, itemFileType, searchDataType, bis, itemFileName,serviceType);
        model.addAttribute("itemFileSellSummaryList", itemFileSellSummaryList);
        ModelAndView modelAndView = new ModelAndView("report/itemFileSell/summaryExportXls");
        return modelAndView;
    }
    
    /**
     * 更新服务方式的对应值
     * @param serviceType 服务类型
     * @return
     */
    public String changeServiceType(Integer serviceType){
    	if(serviceType == 1 || serviceType == 6){
            return "自增品项";
        }else if(serviceType == 2){
            return "加单";
        }else if(serviceType == 3){
            return "赠单";
        }else if(serviceType == 4){
            return "退单";
        }else if(serviceType == 5){
            return "自减品项";
        }else{
            return "其他";
        }
    }

}
