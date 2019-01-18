package com.yqsh.diningsys.web.controller.archive;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgGiftForm;
import com.yqsh.diningsys.web.model.archive.DgGiftFormReason;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.service.archive.DgGiftFormService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 赠单原因and赠单原因类型  controller
 *
 * @author zhshuo create on 2016-10-08 17:00
 */
@Controller
@RequestMapping("/archive/giftForm")
public class GiftFormController extends BaseController {

    @Autowired
    private DgGiftFormService dgGiftFormService;

    @RequestMapping("/index")
    public String index(Model model) {
        List<DgGiftFormReason> allGiftFormReason = dgGiftFormService.getAllGiftFormReason();
        model.addAttribute("allGiftFormReason",allGiftFormReason);
        return "archive/giftForm/gift_form_index";
    }

    @RequestMapping("/reasonIndex")
    public String reasonIndex() {
        return "archive/giftForm/gift_form_reason_index";
    }

    @RequestMapping("/toGiftFormEdit")
    public String toGiftFormEdit(Integer id, Model model) {
        if (id != null) {
            DgGiftForm dgGiftForm = dgGiftFormService.selectByPrimaryKey(id);
            model.addAttribute("dgGiftForm", dgGiftForm);
        }else{
            model.addAttribute("nextCode", dgGiftFormService.selectNextCode());
        }
        List<DgGiftFormReason> allGiftFormReason = dgGiftFormService.getAllGiftFormReason();
        model.addAttribute("allGiftFormReason",allGiftFormReason);
        return "archive/giftForm/gift_form_edit";
    }

    @RequestMapping("/toGiftFormReasonEdit")
    public String toGiftFormReasonEdit(Integer id, Model model) {
        if (id != null) {
            DgGiftFormReason dgGiftFormReason = dgGiftFormService.selectGiftFormReasonById(id);
            model.addAttribute("dgGiftFormReason", dgGiftFormReason);
        }else{
            model.addAttribute("nextCode", dgGiftFormService.selectReasonNextCode());
        }
        return "archive/giftForm/gift_form_reason_edit";
    }


    @RequestMapping("/getAllData")
    @ResponseBody
    public Page<DgGiftForm> getAllData(DgGiftForm dgGiftForm) {
        Page<DgGiftForm> pagebean = null;
        try {
            pagebean = dgGiftFormService.getAllGiftForm(dgGiftForm);
            pagebean.setPage(dgGiftForm.getPage());
            pagebean = (Page<DgGiftForm>) pageResult(pagebean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    @RequestMapping("/getAllReasonData")
    @ResponseBody
    public Page<DgGiftFormReason> getAllData(DgGiftFormReason dgGiftFormReason) {
        Page<DgGiftFormReason> pagebean = null;
        try {
            pagebean = dgGiftFormService.getAllGiftFormReason(dgGiftFormReason);
            pagebean.setPage(dgGiftFormReason.getPage());
            pagebean = (Page<DgGiftFormReason>) pageResult(pagebean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    @RequestMapping("/addGiftForm")
    @ResponseBody
    public Object addGiftForm(DgGiftForm dgGiftForm) {
    	Map<String,Object> result = new HashMap<String,Object>();
        try {
        	List<DgGiftForm> list = dgGiftFormService.getAllList(dgGiftForm);
        	if(list.size() == 0){
        		result.put("success",true);
        		dgGiftFormService.addDgGiftForm(dgGiftForm);
        	}else{
        		result.put("msg","名称已存在！");
        	}
        } catch (Exception e) {
        	result.put("msg","保存失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/addGiftFormReason")
    @ResponseBody
    public Object addGiftFormReason(DgGiftFormReason dgGiftFormReason) {
    	Map<String,Object> result = new HashMap<String,Object>();
        try {
        	List<DgGiftFormReason> list = dgGiftFormService.getAllReasonList(dgGiftFormReason);
        	if(list.size() == 0){
        		result.put("success",true);
        		dgGiftFormService.addDgGiftFormReason(dgGiftFormReason);
        	}else{
        		result.put("msg","名称已存在！");
        	}
        } catch (Exception e) {
        	result.put("msg","保存失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/editGiftForm")
    @ResponseBody
    public Object editGiftForm(DgGiftForm dgGiftForm) {
    	Map<String,Object> result = new HashMap<String,Object>();
        try {
        	List<DgGiftForm> list = dgGiftFormService.getAllList(dgGiftForm);
        	if(list.size() == 0 || (list.size() == 1 && list.get(0).getId() == dgGiftForm.getId()) ){
        		result.put("success",true);
        		dgGiftFormService.updateDgGiftForm(dgGiftForm);
        	}else{
        		result.put("msg","名称已存在！");
        	}
        } catch (Exception e) {
        	result.put("msg","保存失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/editGiftFormReason")
    @ResponseBody
    public Object editGiftFormReason(DgGiftFormReason dgGiftFormReason) {
    	Map<String,Object> result = new HashMap<String,Object>();
        try {
        	List<DgGiftFormReason> list = dgGiftFormService.getAllReasonList(dgGiftFormReason);
        	if(list.size() == 0 || (list.size() == 1 && list.get(0).getId() == dgGiftFormReason.getId()) ){
        		result.put("success",true);
        		dgGiftFormService.updateDgGiftFormReason(dgGiftFormReason);
        	}else{
        		result.put("msg","名称已存在！");
        	}
        } catch (Exception e) {
        	result.put("msg","保存失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/deleteGiftForm")
    @ResponseBody
    public ResultInfo deleteGiftForm(String ids) {
        try {
            dgGiftFormService.deleteDgGiftForm(ids);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/deleteGiftFormReason")
    @ResponseBody
    public ResultInfo deleteGiftFormReason(String ids) {
        try {
            dgGiftFormService.deleteDgGiftFormReason(ids);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

}
