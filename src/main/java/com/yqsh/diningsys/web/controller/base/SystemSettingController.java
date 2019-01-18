package com.yqsh.diningsys.web.controller.base;

import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysSetting;
import com.yqsh.diningsys.web.service.base.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-08-19 13:57
 */
@RequestMapping("/systemSetting")
@Controller
public class SystemSettingController extends BaseController{

    @Autowired
    private SystemSettingService systemSettingService;

    @RequestMapping("/index")
    public String index(Model model){
        List<SysSetting> maps = systemSettingService.selectSettingData();
        model.addAttribute("data",maps);
        return "system/setting/setting_index";
    }

    @RequestMapping("/updateData")
    @ResponseBody
    public ResultInfo updateData(HttpServletRequest request){
        try {
            systemSettingService.insertMultiSetting(request.getParameterMap());
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

}
