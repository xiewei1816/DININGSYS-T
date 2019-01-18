package com.yqsh.diningsys.web.controller.base;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 个人中心
 *
 * @author zhshuo create on 2016-08-17 16:01
 */
@RequestMapping("/personalCenter")
@Controller
public class PersonalCenterController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("userInfo",getCurrentUser());
        return "system/personalCenter/personal_center_index";
    }

    @RequestMapping(value = "/updateUserBaseInfo",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateUserBaseInfo(SysUser sysUser){
        try {
            sysUser.setId(getCurrentUser().getId());
            userService.updateUserBaseInfo(sysUser);
            getRequest().getSession().setAttribute(SystemConstants.SESSION_USER,userService.selectByPrimaryKey(getCurrentUser().getId()));
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }


    @RequestMapping(value = "/checkOldPwd",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo checkOldPwd(String oldPwd){
        if(getCurrentUser().getPassword().equalsIgnoreCase(oldPwd)){
            return returnSuccess();
        }
        return returnFail();
    }

    @RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updatePwd(String newPwd){
        try {
            userService.updatePwd(newPwd,getCurrentUser().getId());
            getRequest().getSession().setAttribute(SystemConstants.SESSION_USER,userService.selectByPrimaryKey(getCurrentUser().getId()));
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

}
