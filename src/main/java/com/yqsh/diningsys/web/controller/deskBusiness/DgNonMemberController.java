package com.yqsh.diningsys.web.controller.deskBusiness;

import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;
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
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.deskBusiness.DgNonMember;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.deskBusiness.DgNonMemberService;

@Controller
@RequestMapping(value = "/nonMember")
public class DgNonMemberController extends BaseController {
    
    @Autowired
    private DgNonMemberService dgNonMemberService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("deskBusiness/non_member/nonMember-index");
        modelAndView.addObject("emps", userService.getAllList(new SysUser()));
        return modelAndView;
    }
    
    @RequestMapping(value = "/getPageList")
    @ResponseBody
    public Object getPageList(HttpServletRequest request, HttpServletResponse response, DgNonMember nonMember) {
        
        com.yqsh.diningsys.core.util.Page<DgNonMember> pagebean = null;
        try {
            pagebean = dgNonMemberService.getPageList(nonMember);
            pagebean.setTotal(pagebean.getContext().getPageCount());
            pagebean.setPage(nonMember.getPage());
            pagebean.setRecords(pagebean.getContext().getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    @RequestMapping(value = "/getAllList")
    @ResponseBody
    public Object getAllList(HttpServletRequest request, HttpServletResponse response,
             DgNonMember nonMember) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<DgNonMember> list = dgNonMemberService.getAllList(nonMember);
            result.put("list", list);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "error");
        }
        return result;
    }
    
    @RequestMapping(value = "/getNonMemberByID")
    @ResponseBody
    public Object getnonMemberByID(HttpServletRequest request, HttpServletResponse response, DgNonMember nonMember) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            DgNonMember dgnonMember = dgNonMemberService.getDgNonMemberByID(nonMember);
            result.put("nonMember", dgnonMember);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "error");
        }
        return result;
    }
    
    @RequestMapping(value = "/saveNonMember")
    @ResponseBody
    public Object savenonMember(HttpServletRequest request,
            HttpServletResponse response, DgNonMember nonMember) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String zjf = nonMember.getMnemonic();
            if (StringUtil.isBlank(zjf)) {
                nonMember.setMnemonic(YQSHTranslate.getPYIndexStr(nonMember.getName(), true));
            }
            nonMember.setCreateUserid(getCurrentUser().getId() + "");
            dgNonMemberService.insertOrUpd(nonMember);
            result.put("success", "OK");
        } catch (Exception e) {
            result.put("success", "false");
            result.put("error", e.getMessage());
        }
        return result;
    }
    
    @RequestMapping(value = "/deleteNonMember")
    @ResponseBody
    public Object deletenonMember(HttpServletRequest request,
            HttpServletResponse response, DgNonMember nonMember) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : nonMember.getEditIds().split(",")) {
                list.add(str);
            }
            nonMember.setIds(list);
            dgNonMemberService.deleteByIds(nonMember);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }
    
    @RequestMapping(value = "/checkNonMemberByName")
    @ResponseBody
    public Object checknonMemberByName(HttpServletRequest request,
            HttpServletResponse response, DgNonMember nonMember) {
        Map<String, Object> result = new HashMap<String, Object>();
        int stat = 0;
        List<DgNonMember> checkList = dgNonMemberService.getAllList(nonMember);
        if (nonMember.getId() != null && nonMember.getId() > 0) {
            stat = checkList != null && checkList.size() != 0
                    ? checkList.get(0).getId() != nonMember.getId() ? 2 : 1 : 1;
        } else {
            stat = checkList != null && checkList.size() != 0 ? 2 : 1;
        }
        result.put("state", stat);
        return result;
    }
}
