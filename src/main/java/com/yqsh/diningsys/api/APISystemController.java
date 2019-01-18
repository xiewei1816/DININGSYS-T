package com.yqsh.diningsys.api;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.service.base.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 系统
 *
 * @author zhshuo create on 2016-12-26 10:46
 */
@RequestMapping("/yqshapi/system")
@Controller
public class APISystemController extends ApiBaseController{

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/modifyPassWord")
    @ApiOperation(value = "修改密码", httpMethod = "POST", notes = "修改密码")
    public Object modifyPassWord(@ApiParam(required = true, name = "userCode", value = "需要修改密码的用户编号") @RequestParam(value = "userCode", required = true)String userCode,
                                 @ApiParam(required = true, name = "oPwd", value = "旧密码") @RequestParam(value = "oPwd", required = true)String oPwd,
                                 @ApiParam(required = true, name = "newPwd", value = "新密码") @RequestParam(value = "newPwd", required = true)String newPwd){
        try {
            SysUser sysUser = userService.selectByUsername(userCode);

            if(sysUser == null){
                return getResult(APIEnumDefine.S003);
            }

            if(!DigestUtils.sha256Hex(oPwd).equals(sysUser.getPassword())){
                return getResult(APIEnumDefine.S008);
            }

            userService.updatePwd(DigestUtils.sha256Hex(newPwd),sysUser.getId());
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            getExceptionResult();
        }
        return getFailResult();
    }

    @ResponseBody
    @RequestMapping("/safeLoginOut")
    @ApiOperation(value = "安全退出", httpMethod = "POST", notes = "安全退出")
    public Object safeLoginOut(@ApiParam(required = true, name = "userCode", value = "前台需要退出的用户") @RequestParam(value = "userCode", required = true)String userCode){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            List<Map> users  = (List<Map>) session.getAttribute(SystemConstants.SESSION_CLIENT_USER);
            if(users.size() < 1){
                return getResult(APIEnumDefine.S009);
            }

            Iterator<Map> iterator = users.iterator();
            while(iterator.hasNext()){
                Map next = iterator.next();
                SysUser user = (SysUser)next.get("user");
                if(user.getEmpCode().equals(userCode)){
                    iterator.remove();
                }
            }

            session.setAttribute(SystemConstants.SESSION_CLIENT_USER,users);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

}
