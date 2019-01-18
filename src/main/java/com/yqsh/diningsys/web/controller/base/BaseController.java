package com.yqsh.diningsys.web.controller.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.util.BasePojo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;

/**
 * baseController
 *
 * @author zhshuo create on 2016-07-21 13:54
 */
public class BaseController {


    /**
     * 成功
     * @param successMsg
     * @return
     */
    public ResultInfo returnSuccess(String successMsg){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccessMsg(successMsg);
        return resultInfo;
    }

    /**
     * 成功
     * @return
     */
    public ResultInfo returnSuccess(){
        return new ResultInfo();
    }
    
    /**
     * 失败
     * @param errorMsg
     * @return
     */
    public ResultInfo returnFail(String errorMsg){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(false);
        resultInfo.setErrorMsg(errorMsg);
        return resultInfo;
    }

    /**
     * 失败
     * @return
     */
    public ResultInfo returnFail(){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setSuccess(false);
        return resultInfo;
    }


    public com.yqsh.diningsys.core.util.Page<? extends BasePojo> pageResult(com.yqsh.diningsys.core.util.Page<? extends BasePojo> pagebean){
        pagebean.setTotal(pagebean.getContext().getPageCount());
        pagebean.setRecords(pagebean.getContext().getTotal());
        return pagebean;
    }

    /**
     * 数组转换为List
     * @param arr
     * @return
     */
    public List arrayToList(String arr){
        if(StringUtils.isEmpty(arr))return null;
        List list = new ArrayList();
        Collections.addAll(list,arr.split(","));
        return list;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return request;
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public SysUser getCurrentUser(){
       return (SysUser)getRequest().getSession().getAttribute(SystemConstants.SESSION_USER);
    }

    public String List2String(List<String> list){
        if(list != null && list.size() > 0){
            String res = "";
            for(String string:list){
                res += string+",";
            }
            res = res.substring(0,res.lastIndexOf(","));
            return res;
        }
        return null;
    }

}
