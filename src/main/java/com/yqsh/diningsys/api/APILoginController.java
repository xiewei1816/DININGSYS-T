/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yqsh.diningsys.api;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.core.util.TreeUtil;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.service.api.APIMainService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.base.SysMenuService;
import com.yqsh.diningsys.web.service.base.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 登陆相关接口
 * @author yqsh-zc
 */
@RequestMapping("/yqshapi/login")
@Controller
public class APILoginController extends ApiBaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private DgPosService posService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private APIMainService apiMainService;

    /**
     * 获取用户姓名<br>
     * 主要用于客户端登陆的时候输入工号，获取对应用户的姓名
     * @param empCode 工号 必须
     * @return 返回对应姓名
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    @ApiOperation(value = "获取用户信息", httpMethod = "POST", notes = "传入参数获取用户信息")
    public Object getUserInfo(HttpServletRequest request,
                        @ApiParam(required = true, name = "empCode", value = "用户名") @RequestParam(value = "empCode", required = true) String empCode)
    {
        SysUser user =  userService.selectByUsername(empCode);
        if(user == null){
            return getResult(APIEnumDefine.S003);
        }
        //前台不需要太多数据，只需要用户名
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name",user.getEmpName());
        return getSuccessResult(map);
    }

    /**
     * 用户登陆接口<br>
     * 成功返回用户数据和权限数据<br>
     * 客户端每次登入传入本地的token值，客户端正常关闭，token为空，非正常关闭token非空<br>
     * token为空，直接创建新的token，token非空，判断该token是否过期，过期创建新token<br>
     * 可以传入备用金,不传默认为0--服务员开始工作的时候一般会自带备用金用于找零.
     * 最后一个结班信息为未结，且登录人不为同一个，拒绝登录
     * 最后一个结班为已结，添加新的结班基本信息
     * @param empCode 工号 必须
     * @param password 密码 必须
     * @param pettyCash 备用金 非必须
     * @return 用户数据和权限数据
     */
    @RequestMapping("/login")
    @ResponseBody
    @ApiOperation(value = "用户登陆", httpMethod = "POST", notes = "成功返回用户数据和权限数据")
    public Object login(HttpServletRequest httpServletRequest,
                        @ApiParam(required = true, name = "empCode", value = "用户名") @RequestParam(value = "empCode", required = true) String empCode,
                        @ApiParam(required = true, name = "password", value = "密码") @RequestParam(value = "password", required = true) String password,
                        @ApiParam(required = false, name = "pettyCash", value = "备用金") @RequestParam(value = "pettyCash", required = false) Double pettyCash,
                        @ApiParam(required = true, name = "posInfo", value = "POS编号以及名称") @RequestParam(value = "posInfo", required = true) String posInfo)
    {
        try{
            SysUser user =  userService.selectByUsername(empCode);
            if(user == null){
                return getResult(APIEnumDefine.S003);
            }

            //前台不需要太多数据，只需要用户名
            if(!user.getPassword().toUpperCase().equals(DigestUtils.sha256Hex(password).toUpperCase())){
                return getResult(APIEnumDefine.S004);
            }
            DgPos dgPos = posService.selectPosByPosId(Integer.parseInt(posInfo));

            if(dgPos.getCanJb() == 1){//只有当该登录POS能结班时
                //传入POSID 获取该POS最后一条接班流水
                Map lastInfo =  apiMainService.selectLastOpenClassInfo(dgPos.getId());
                Map lastUserInfo =  apiMainService.selectLoginUserLast(empCode);
                if(lastInfo != null && lastInfo.containsKey("login_user")){
                    String lastLoginUser = lastInfo.get("login_user").toString(),
                            lastLoginUserNaem = lastInfo.get("login_user_name").toString(),
                            lastLoginPOS = lastInfo.get("login_pos").toString(),
                            lastOpenClassState = lastInfo.get("open_class_state").toString();
                    if(!lastLoginUser.equalsIgnoreCase(empCode) && lastOpenClassState.equals("0")){ //最后一个结班信息为未结，且登录人不为同一个，拒绝登录，
                        Map body = new HashMap();
                        body.put("lastUser",lastLoginUser+"-"+lastLoginUserNaem);
                        body.put("lastPOS",lastLoginPOS);
                        return getResult(APIEnumDefine.S006,body);
                    }else if(lastOpenClassState.equals("1")){//最后一个结班为已结，无论登录人是否为同一个都添加新的结班基本信息
                        apiMainService.updateOpenClass(empCode,user.getEmpName(),dgPos.getId(),pettyCash,new Date());
                    }
                }else{
                    if(lastUserInfo != null && lastUserInfo.containsKey("login_user")){
                        Integer lastPos = Integer.parseInt(lastUserInfo.get("login_pos").toString());
                        if(lastUserInfo.get("open_class_state").toString().equals("0")){
                            Map body = new HashMap();
                            body.put("lastUser",lastUserInfo+"-"+lastUserInfo.get("login_user_name").toString());
                            body.put("lastPOS",lastPos);
                            return getResult(APIEnumDefine.S011,body);
                        }else{
                            apiMainService.updateOpenClass(empCode,user.getEmpName(),dgPos.getId(),pettyCash,new Date());
                        }
                    }else{
                        apiMainService.updateOpenClass(empCode,user.getEmpName(),dgPos.getId(),pettyCash,new Date());
                    }
                }
            }

            Map<String,Object> map = new HashMap<>();
            map.put("user",user);
            List<SysMenu> sysMenus = sysMenuService.selectReceptionMenu(user.getEmpDuties());
            TreeUtil treeUtil = new TreeUtil(sysMenus);
            SysMenu sysMenu = treeUtil.generateTreeNode(394);
            map.put("menus",sysMenu.getChild());

            List<Map> users = new ArrayList<>();
            Map clientUser = new HashMap();
            clientUser.put("user",user);
            clientUser.put("lastConnectTime",System.currentTimeMillis());
            users.add(clientUser);
            httpServletRequest.getSession().setAttribute(SystemConstants.SESSION_CLIENT_USER,users);

            return getSuccessResult(map);
        }catch(Exception e){
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /**
     *
     * @param empCode
     * @param pwd
     * @return
     */
    @ResponseBody
    @RequestMapping("/judgeUser")
    public Object judgeUser(String empCode,String pwd){
        Map<String,Object> map = new HashMap<>();
        SysUser user =  userService.selectByUsername(empCode);
        if(user == null){
            return getResult(APIEnumDefine.S003);
        }

        //前台不需要太多数据，只需要用户名
        if(!user.getPassword().toUpperCase().equals(DigestUtils.sha256Hex(pwd).toUpperCase())){
            return getResult(APIEnumDefine.S004);
        }
        map.put("user",user);
        return getSuccessResult(map);
    }

    /**
     * 前台授权验证
     * @param authorizationCode 授权码
     * @param function_code 功能code
     * @return
     */
    @ResponseBody
    @RequestMapping("/judgePermission")
    @ApiOperation(value = "前台授权验证", httpMethod = "POST", notes = "前台授权验证")
    public Object judgePermission(String authorizationCode,String function_code){
        try {
            SysUser user = userService.selectUserByAuthCode(authorizationCode);
            if(user == null){
                return getResult(APIEnumDefine.S990);
            }

            SysMenu sysMenu = sysMenuService.selectMenuByMenuCode(function_code);

            SysRoleMenu sysRoleMenu = userService.selectMenuByUserZwAndMenuId(user.getEmpDuties(), sysMenu.getId());
            if(sysRoleMenu == null){
                return getResult(APIEnumDefine.S989);
            }
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getExceptionResult();
    }

    /**
     * 获取POS终端数据<br>
     * 成功返回POS列表,如果前台选择了其中一个POS，如果IP地址不为000.000.000.000，那么客户端要做IP验证
     * @return POS终端数据
     */
    @RequestMapping("/getPos")
    @ResponseBody
    @ApiOperation(value = "获取POS终端数据", httpMethod = "POST", notes = "成功返回POS列表,如果前台选择了其中一个POS，如果IP地址不为000.000.000.000，那么客户端要做IP验证")
    public Object getPos(HttpServletRequest request)
    {
        List<Map<String, Object>> allPos = posService.getAllPosList();
        return getSuccessResult(allPos);
    }


}
