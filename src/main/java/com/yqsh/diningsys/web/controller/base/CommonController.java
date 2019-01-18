package com.yqsh.diningsys.web.controller.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.yqsh.diningsys.web.model.DgCommonsVariable;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.service.base.CommonService;
import com.yqsh.diningsys.web.service.base.DgCommonsVariableService;
import com.yqsh.diningsys.web.service.base.SystemSettingService;
import com.yqsh.diningsys.web.service.base.UserService;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysUser;

/**
 * 公共视图控制器
 **/
@Controller
public class CommonController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemSettingService systemSettingService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private DgCommonsVariableService dgCommonsVariableService;

    /**
     * 首页
     *
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request, Model model) {
        List<SysMenu> userMenus = (List<SysMenu>) request.getSession().getAttribute(SystemConstants.SESSION_USER_MENUS);
        model.addAttribute("userMenus", userMenus);
        model.addAttribute("userInfo", getCurrentUser());

        model.addAttribute("systemName", systemSettingService.selectSystemName());

        return "new-index";
    }

    @RequestMapping("lockScreen")
    public String lockScreen(HttpServletRequest request) {
        return "lock_screen";
    }

    @RequestMapping("/checkUserNameExist")
    @ResponseBody
    public ResultInfo checkUserNameExist(String name, String type, String loginId) {
        try {
            SysUser sysUser = null;
            if (type == null) {
                sysUser = userService.selectByUsername(name);
            } else {
                Map params = new HashMap();
                params.put("name", name);
                params.put("loginId", loginId);
                sysUser = userService.selectByUsernameWithOutSelf(params);
            }
            if (sysUser != null)
                return returnSuccess("exist");
            else
                return returnSuccess("notExist");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/wel")
    public String wel() {
        return "wel";
    }


    @RequestMapping(value = "summernoteFileUpload", method = RequestMethod.POST)
    @ResponseBody
    public String summernoteFileUpload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String filePath = commonService.insertMultiVlues(getRequest(), multipartFile, "summernoteFile");
        jsonObject.put("path", "upload/" + filePath);
        return jsonObject.toString();
    }

    @RequestMapping(value = "updateCommonsValueByCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateCommonsValueByCode(DgCommonsVariable dgCommonsVariable) {
        try{
            dgCommonsVariableService.updateValueByCode(dgCommonsVariable);
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnFail();
    }

    /**
     * 新增中文转换拼音首字母
     * @param chinese
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "chiness2ShortPinyin",method = RequestMethod.POST,produces="text/plain;charset=utf-8")
    public String chiness2ShortPinyin(String chinese ){
        try {
            if(!StringUtils.isEmpty(chinese)){
                String shortPinyin = PinyinHelper.getShortPinyin(chinese);
                return shortPinyin;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}