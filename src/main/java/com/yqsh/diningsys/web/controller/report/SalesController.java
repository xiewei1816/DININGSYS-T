package com.yqsh.diningsys.web.controller.report;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.report.Sales;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.report.ReportSalesService;
import com.yqsh.diningsys.web.util.TableQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 营销员销售信息统计报表
 *
 * @author xiewei
 * @version 创建时间：2018年12月18日
 */
@Controller
@RequestMapping(value = "/report/sales")
public class SalesController extends BaseController {

    @Autowired
	private ReportSalesService reportSalesService;

	@Autowired
	private UserService userService;

	@Autowired
	private DgItemFileTypeService dgItemFileTypeService;


    @RequestMapping("/index")
    public ModelAndView index(Model model) {
    	SysUser user = new SysUser();
    	user.setIsDel("0");
		model.addAttribute("list",userService.getAllList(user));
		model.addAttribute("itemFileBigTypes",dgItemFileTypeService.selectAllItemFileBigType());
		model.addAttribute("itemFileSmallTypes",dgItemFileTypeService.selectAllItemFileSmallType());
        ModelAndView modelAndView = new ModelAndView("report/sales/sales-index");
        return modelAndView;
    }

    /**
     * 查询营销员销售信息
     * @param sales
     * @return
     */
	@RequestMapping("/selectSalesReportList")
	@ResponseBody
	public Object selectSalesReportList(Sales sales) {
		Page<Sales> pagebean = null;
    	String startTime = sales.getStartTime();
    	String endTime = sales.getEndTime();
    	List<String> strings = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
		sales.setTableSuffixList(strings);
		if(strings.size() < 1) {
			pagebean = new Page<>();
			pagebean.setTotal(0);
			pagebean.setPage(1);
			pagebean.setRecords(0);
			return pagebean;
		}
    	try {
    		String ppxlId = sales.getPpxlId();
    		if(ppxlId!=null&&!ppxlId.equals("null")){
				List<String> list = new ArrayList<String>();
				for(String str : ppxlId.split(",")){
					list.add(str);
				}
				sales.setPpxlIds(list);
			}else{
				List<DgItemFileType> dgItemFileTypes = dgItemFileTypeService.selectAllItemFileSmallType();
				List<String> list = new ArrayList<String>();
				for(DgItemFileType dgItemFileType : dgItemFileTypes){
					list.add(dgItemFileType.getId().toString());
				}
				sales.setPpxlIds(list);
			}
			pagebean = reportSalesService.getSalesPageList(sales);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(sales.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	
    /**
     * 导出Excel
     * @param sales
     * @return
     */
    @RequestMapping(value = "exportXls")
    public ModelAndView exportXls(Model model,Sales sales) {
		String startTime = sales.getStartTime();
		String endTime = sales.getEndTime();
		List<String> strings = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
		sales.setTableSuffixList(strings);
		if(strings.size() < 1) {
			return null;
		}
		String ppxlId = sales.getPpxlId();
		if(ppxlId!=null&&!ppxlId.equals("null")){
			List<String> list = new ArrayList<String>();
			for(String str : ppxlId.split(",")){
				list.add(str);
			}
			sales.setPpxlIds(list);
		}else{
			List<DgItemFileType> dgItemFileTypes = dgItemFileTypeService.selectAllItemFileSmallType();
			List<String> list = new ArrayList<String>();
			for(DgItemFileType dgItemFileType : dgItemFileTypes){
				list.add(dgItemFileType.getId().toString());
			}
			sales.setPpxlIds(list);
		}
    	List<Map<String,Object>> salesList = reportSalesService.selectSalesReportList(sales);
        model.addAttribute("salesList", salesList);
        ModelAndView modelAndView = new ModelAndView("report/sales/exportXls");
        return modelAndView;
    }

}
