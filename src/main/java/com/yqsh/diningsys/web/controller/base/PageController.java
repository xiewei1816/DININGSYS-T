package com.yqsh.diningsys.web.controller.base;

import com.yqsh.diningsys.web.service.base.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 视图控制器,返回jsp视图给前端
 * 
 **/
@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private SystemSettingService systemSettingService;

    /**
     * 登录页
     */
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("systemName", systemSettingService.selectSystemName());
        return "login";
    }

    /**
     * dashboard页
     */
    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }


    /**
     * 404页
     */
    @RequestMapping("/404")
    public String error404() {
        return "404";
    }

    /**
     * 401页
     */
    @RequestMapping("/401")
    public String error401() {
        return "401";
    }

    /**
     * 500页
     */
    @RequestMapping("/500")
    public String error500() {
        return "500";
    }
}