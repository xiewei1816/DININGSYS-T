package com.yqsh.diningsys.web.controller.report;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.service.base.SysAuthorizationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2017-06-06 11:28
 * 授权码日志
 * @author zhshuo
 */
@Controller
@RequestMapping("/report/sysAuthCode")
public class SysAuthCodeController extends BaseController{

    @Autowired
    private SysAuthorizationLogService sysAuthorizationLogService;

    @RequestMapping("/index")
    public String index(){
        return "report/sysAuthCode/index";
    }

    @RequestMapping(value = "/getPageList")
    @ResponseBody
    public Page<SysAuthorizationLog> getAllData(SysAuthorizationLog sysAuthorizationLog){
        Page<SysAuthorizationLog> pagebean = null;
        try {
            pagebean = sysAuthorizationLogService.getAllData(sysAuthorizationLog);
            pagebean.setPage(sysAuthorizationLog.getPage());
            pagebean = (Page<SysAuthorizationLog>)pageResult(pagebean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

}
