package com.yqsh.diningsys.web.controller.archive;

import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;

@Controller
@RequestMapping(value = "/consumptionArea")
public class ConsumptionAreaController extends BaseController {
	
	@Autowired
    private DgConsumptionAreaService consumptionAreaService;

	@Autowired
	private DgConsumerSeatService consumptionArea;
	
	@RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("archive/consumptionArea/consumptionArea-index");
        return modelAndView;
    }
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgConsumptionArea consumptionArea) {
		
    	com.yqsh.diningsys.core.util.Page<DgConsumptionArea> pagebean = null;
		try {
			pagebean = consumptionAreaService.getPageList(consumptionArea);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(consumptionArea.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	@RequestMapping(value = "/getAllList")
	@ResponseBody
	public Object getAllList(HttpServletRequest request,HttpServletResponse response
							,DgConsumptionArea consumptionArea) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<DgConsumptionArea> list = consumptionAreaService.getAllList(consumptionArea);
			result.put("list", list);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
	
	@RequestMapping(value = "/getConsumptionAreaByID")
	@ResponseBody
	public Object getConsumptionAreaByID(HttpServletRequest request,HttpServletResponse response,DgConsumptionArea consumptionArea) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			DgConsumptionArea gconsumptionArea = consumptionAreaService.getConsumptionAreaByID(consumptionArea);
			result.put("consumptionArea", gconsumptionArea);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
    
    @RequestMapping(value = "/saveConsumptionArea")
	@ResponseBody
	public Object saveConsumptionArea(HttpServletRequest request,
			HttpServletResponse response,DgConsumptionArea consumptionArea) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
                        String zjf = consumptionArea.getMnemonic();
                        if(StringUtil.isBlank(zjf)){
                            consumptionArea.setMnemonic(YQSHTranslate.getPYIndexStr(consumptionArea.getName(), true));
                        }
			consumptionArea.setCreateUserid(getCurrentUser().getId() + "");
			consumptionAreaService.insertOrUpd(consumptionArea);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}
    
    @RequestMapping(value = "/deleteConsumptionArea")
	@ResponseBody
	public ResultInfo deleteConsumptionArea(Integer id) {
		try{
            List<DgConsumerSeat> dgConsumerSeats = consumptionArea.selectDataByAreaId(id);
            if(dgConsumerSeats.size() > 0){
                return returnFail("请先删除该区域下的客位信息");
            }
            consumptionAreaService.deleteById(id);
			return returnSuccess();
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnFail();
	}
    
    @RequestMapping(value = "/checkConsumptionAreaByName")
	@ResponseBody
	public Object checkConsumptionAreaByName(HttpServletRequest request,
			HttpServletResponse response,DgConsumptionArea consumptionArea) {
		Map<String,Object> result = new HashMap<String,Object>();
		int stat = 0;
		List<DgConsumptionArea> checkList = consumptionAreaService.getAllList(consumptionArea);
		if(consumptionArea.getId() != null && consumptionArea.getId() > 0){
			stat = checkList != null && checkList.size() != 0 ?
			checkList.get(0).getId() != consumptionArea.getId() ? 2 : 1 : 1;
		}else{
			stat = checkList != null && checkList.size() != 0 ? 2 : 1;
		}
		result.put("state", stat);
		return result;
	}
}