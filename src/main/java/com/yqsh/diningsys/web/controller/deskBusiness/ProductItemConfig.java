package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
import com.yqsh.diningsys.web.model.sysSettings.SysLog;
import com.yqsh.diningsys.web.model.sysSettings.SysLogQuery;
import com.yqsh.diningsys.web.model.sysSettings.SysLogTree;
import com.yqsh.diningsys.web.service.base.SysRoleService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.deskBusiness.DgProductPhaseLeftmenuService;
import com.yqsh.diningsys.web.service.sysSettings.SysLogService;
@Controller
@RequestMapping("/productItemconfig")
public class ProductItemConfig extends BaseController{
    @Autowired
    private DgProductPhaseLeftmenuService dgProductPhaseLeftmenuService;
    
    
    @RequestMapping(value = "/index")
	public ModelAndView getAllLog()
	{
    	ModelAndView model = new ModelAndView("deskBusiness/product_item/index");
    	return model;
	}
    
    
    /**
     * 
     * 左侧树菜单
     * @return
     */
    @RequestMapping("/getTreeNodes")
    @ResponseBody
    public  List<DgProductPhaseLeftmenu> getTreeNodes()
    {
    	String id = getRequest().getParameter("id");
    	List<DgProductPhaseLeftmenu> menu = new ArrayList<DgProductPhaseLeftmenu>();
    	
    	//获取根节点
    	if(StringUtils.isEmpty(id))
    	{
    		DgProductPhaseLeftmenu m = dgProductPhaseLeftmenuService.selectByPrimaryKey(1);
    		menu.add(m);
    	}
    	else
    	{
    		int nId = Integer.parseInt(id);
    		String childCount = getRequest().getParameter("childCount");
    		int nChildCount = Integer.parseInt(childCount);
    		if(nChildCount > 0)
    		{
    			List<DgProductPhaseLeftmenu> m = dgProductPhaseLeftmenuService.selectByParentPrimaryKey(nId);
    			menu = m;
    		}
    	}
    		
    	return menu;
    }
    
}