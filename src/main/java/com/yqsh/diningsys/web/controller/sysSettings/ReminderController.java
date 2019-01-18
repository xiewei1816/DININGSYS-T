package com.yqsh.diningsys.web.controller.sysSettings;

import java.util.List;
import java.util.Map;

import org.aspectj.apache.bcel.generic.IINC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.service.deskBusiness.DgImportantActivityDiscountService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountProgrammeService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemGiftPlanService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemMemberDiscountService;

@Controller
@RequestMapping("/reminder")
public class ReminderController extends BaseController {
	@Autowired
	private DgItemDiscountProgrammeService dgItemDiscountProgrammeService;
	
	@Autowired
	private DgItemGiftPlanService dgItemGiftPlanService;
	
	@Autowired
	private DgItemMemberDiscountService dgItemMemberDiscountService;
	
	@Autowired
	private DgImportantActivityDiscountService dgImportantActivityDiscountService;

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("sysSettings/reminder/reminder");
		return model;
	}

	@RequestMapping(value = "/itemDiscountPan")
	@ResponseBody
	public Object itemDiscountPan() {
		List<Map<String, Object>> reminder = dgItemDiscountProgrammeService
				.reminder();
		for (Map<String, Object> r : reminder) {
			Long timeDiff = (Long) r.get("timeDiff");
			if(timeDiff != null){
				if (timeDiff < 0) {
					r.put("reminder", r.get("name") + "品项打折方案已逾期!");
				} else {
					r.put("reminder", r.get("name") + "品项打折方案还有" + timeDiff
							+ "天已逾期!");
				}
			}else{
				return null;
			}
		}
		return reminder;
	}
	
	@RequestMapping(value = "/memberDiscountPan")
	@ResponseBody
	public Object memberDiscountPan() {
		List<Map<String, Object>> reminder = dgItemMemberDiscountService
				.reminder();
		for (Map<String, Object> r : reminder) {
			Long timeDiff = (Long) r.get("timeDiff");
			if(timeDiff != null){
				if (timeDiff < 0) {
					r.put("reminder", r.get("name") + "品项打折方案已逾期!");
				} else {
					r.put("reminder", r.get("name") + "品项打折方案还有" + timeDiff
							+ "天已逾期!");
				}
			}else{
				return null;
			}
		}
		return reminder;
	}
	
	@RequestMapping(value = "/giftDiscountPan")
	@ResponseBody
	public Object giftDiscountPan() {
		List<Map<String, Object>> reminder = dgItemGiftPlanService
				.reminder();
		for (Map<String, Object> r : reminder) {
			Long timeDiff = (Long) r.get("timeDiff");
			if(timeDiff != null){
				if (timeDiff < 0) {
					r.put("reminder", r.get("name") + "品项打折方案已逾期!");
				} else {
					r.put("reminder", r.get("name") + "品项打折方案还有" + timeDiff
							+ "天已逾期!");
				}
			}else{
				return null;
			}
		}
		return reminder;
	}
	
	@RequestMapping(value = "/importtantActiDiscountPan")
	@ResponseBody
	public Object importtantActiDiscountPan() {
		List<Map<String, Object>> reminder = dgImportantActivityDiscountService
				.reminder();
		for (Map<String, Object> r : reminder) {
			Long timeDiff = (Long) r.get("timeDiff");
			if(timeDiff != null){
				if (timeDiff < 0) {
					r.put("reminder", r.get("name") + "品项打折方案已逾期!");
				} else {
					r.put("reminder", r.get("name") + "品项打折方案还有" + timeDiff
							+ "天已逾期!");
				}
			}else{
				return null;
			}
		}
		return reminder;
	}
	
}
