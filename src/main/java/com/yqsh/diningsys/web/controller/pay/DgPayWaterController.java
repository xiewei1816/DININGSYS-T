package com.yqsh.diningsys.web.controller.pay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yqsh.diningsys.core.util.Constants;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.pay.DgConstants;
import com.yqsh.diningsys.core.util.pay.DgPayUtil;
import com.yqsh.diningsys.web.model.pay.DgPayWater;
import com.yqsh.diningsys.web.service.pay.DgPayWaterService;

/**
 * 充值流水
 * @author jianglei
 * 日期 2017年1月12日 上午10:15:50
 *
 */
@Controller
@RequestMapping(value="/pay/water/")
public class DgPayWaterController {
	
	@Autowired
	private DgPayWaterService dgPayWaterService;
	
	
	
	/**
	 * 到数据显示列表
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:07:48
	 * @return
	 */
	@RequestMapping("index")
    public String index(ModelMap model){
        return "/pay/payWater/query";
    }
	/**
	 * 分页带参查询信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:08:08
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = "getPageList")
	@ResponseBody
	public Object getPageList(DgPayWater water) {
    	Page<DgPayWater> pagebean = null;
			pagebean = dgPayWaterService.getPageList(water);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(water.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		return pagebean;
	}
	/**
	 * 导出报表
	 * @author jianglei
	 * 日期 2017年1月16日 上午10:43:25
	 * @param water
	 * @param model
	 * @return
	 */
	@RequestMapping(value="exportXls")
	public String  exportXls(DgPayWater water,ModelMap model){
		List<DgPayWater> listObj=dgPayWaterService.findAllObj(water);
		model.addAttribute("listObj", listObj);
		return "pay/payWater/exportXls";
	}
	/**
	 * 单条对账
	 * @author jianglei
	 * 日期 2017年1月16日 上午11:31:45
	 * @param water
	 * @return
	 */
	@RequestMapping(value="payCheck",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String payCheck(String id,HttpServletRequest request,HttpServletResponse response){
		String msg=DgConstants.RES_MEG;
		if(StringUtils.isBlank(id)){
			msg="参数有误!";
			return msg;
		}
		DgPayWater water=dgPayWaterService.get(id);
		if(null!=water&&StringUtils.isNotBlank(water.getId())){
			if(Constants.PAY_TYPE_WX.equals(water.getPayType())){
				msg=DgPayUtil.wxCheck(water, request, response);
			}else if(Constants.PAY_TYPE_ZFB.equals(water.getPayType())){
				msg=DgPayUtil.aliPayCheck(water);
			}
		}else{
			msg="数据有误!";
		}
		return msg;
	}
}
