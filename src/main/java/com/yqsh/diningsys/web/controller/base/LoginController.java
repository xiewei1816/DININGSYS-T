package com.yqsh.diningsys.web.controller.base;

import java.util.List;




import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.core.util.TreeUtil;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysSetting;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.service.base.SystemSettingService;
import com.yqsh.diningsys.web.service.base.UserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yqsh.diningsys.web.service.base.SysMenuService;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-07-20 10:09
 */
@Controller
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SystemSettingService systemSettingService;
    /**
     * 用户登录
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(SysUser sysUser, Model model, HttpServletRequest request) {
        try {
            Subject subject = SecurityUtils.getSubject();
            // 已登陆则 跳到首页
            if (subject.isAuthenticated()) {
                return "redirect:/DININGSYS/index";
            }
            // 身份验证
            subject.login(new UsernamePasswordToken(sysUser.getEmpCode(), sysUser.getPassword()));
            // 验证成功在Session中保存用户信息
            final SysUser authUserInfo = userService.selectByUsername(sysUser.getEmpCode());
            SysSetting setting = new SysSetting();
            setting.setSettingCode("single");
            setting = systemSettingService.selectSystemByCode(setting);
            request.getSession().setAttribute("single", setting.getSettingValue());
            request.getSession().setAttribute(SystemConstants.SESSION_USER, authUserInfo);
            TreeUtil treeUtil = new TreeUtil(sysMenuService.selectLoginUserMenuPermission(authUserInfo.getEmpDuties()));
            SysMenu treeMenu = treeUtil.generateTreeNode(2);
            List<SysMenu> loginUserMenus = treeMenu.getChild();
            request.getSession().setAttribute(SystemConstants.SESSION_USER_MENUS,loginUserMenus);
        } catch (AuthenticationException e){
            e.printStackTrace();
            // 身份验证失败
            if(e.getMessage().equalsIgnoreCase("该用户已被锁定，请联系管理员！"))
                model.addAttribute("error", "该用户已被锁定，请联系管理员！");
            else
                model.addAttribute("error", "用户名或密码错误 ！");
            return "login";
        }
        return "redirect:/DININGSYS/index";
    }

    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("userInfo");
        // 登出操作
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
