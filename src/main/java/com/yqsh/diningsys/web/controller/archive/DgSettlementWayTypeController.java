package com.yqsh.diningsys.web.controller.archive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgSettlementWayType;
import com.yqsh.diningsys.web.service.archive.DgSettlementWayTypeService;

@Controller
@RequestMapping(value = "/settlementWayType")
public class DgSettlementWayTypeController extends BaseController {
	
	@Autowired
    private DgSettlementWayTypeService settlementWayTypeService;
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response
			,DgSettlementWayType settlementWayType) {
		
    	com.yqsh.diningsys.core.util.Page<DgSettlementWayType> pagebean = null;
		try {
			pagebean = settlementWayTypeService.getPageList(settlementWayType);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(settlementWayType.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	
	@RequestMapping(value = "/getAllList")
	@ResponseBody
	public Object getAllList(HttpServletRequest request,HttpServletResponse response
							,DgSettlementWayType settlementWayType) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<DgSettlementWayType> list = settlementWayTypeService.getAllList(settlementWayType);
			result.put("list", list);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
	
	@RequestMapping(value = "/getDgSettlementWayTypeByID")
	@ResponseBody
	public Object getDgSettlementWayTypeByID(HttpServletRequest request,HttpServletResponse response,DgSettlementWayType settlementWayType) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			DgSettlementWayType dgSettlementWayType = settlementWayTypeService.getDgSettlementWayTypeByID(settlementWayType);
			result.put("settlementWayType", dgSettlementWayType);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
    
    @RequestMapping(value = "/saveDgSettlementWayType")
	@ResponseBody
	public Object saveDgSettlementWayType(HttpServletRequest request,
			HttpServletResponse response,DgSettlementWayType settlementWayType) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			settlementWayType.setCreateUserid(getCurrentUser().getId() + "");
			settlementWayTypeService.insertOrUpd(settlementWayType);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}
    
    @RequestMapping(value = "/deleteDgSettlementWayType")
	@ResponseBody
	public Object deleteDgSettlementWayType(HttpServletRequest request,
			HttpServletResponse response,DgSettlementWayType settlementWayType) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<String> list = new ArrayList<String>();
			for(String str : settlementWayType.getEditIds().split(",")){
				list.add(str);
			}
			settlementWayType.setIds(list);
			settlementWayTypeService.deleteByIds(settlementWayType);
			result.put("success","OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "false");
		}
		return result;
	}
    
    @RequestMapping(value = "/checkDgSettlementWayTypeByName")
	@ResponseBody
	public Object checkDgSettlementWayTypeByName(HttpServletRequest request,
			HttpServletResponse response,DgSettlementWayType settlementWayType) {
		Map<String,Object> result = new HashMap<String,Object>();
		int stat = 0;
		List<DgSettlementWayType> checkList = settlementWayTypeService.getAllList(settlementWayType);
		if(settlementWayType.getId() != null && settlementWayType.getId() > 0){
			stat = checkList != null && checkList.size() != 0 ?
			checkList.get(0).getId() != settlementWayType.getId() ? 2 : 1 : 1;
		}else{
			stat = checkList != null && checkList.size() != 0 ? 2 : 1;
		}
		result.put("state", stat);
		return result;
	}
}
