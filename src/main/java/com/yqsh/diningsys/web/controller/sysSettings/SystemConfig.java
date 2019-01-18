package com.yqsh.diningsys.web.controller.sysSettings;

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
import com.yqsh.diningsys.web.model.sysSettings.SysLog;
import com.yqsh.diningsys.web.model.sysSettings.SysLogQuery;
import com.yqsh.diningsys.web.model.sysSettings.SysLogTree;
import com.yqsh.diningsys.web.service.base.SysRoleService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.sysSettings.SysLogService;
@Controller
@RequestMapping("/sysconfig")
public class SystemConfig extends BaseController{
    @Autowired
    private SysLogService sysLogService;
    
    @Autowired
    private UserService userService;
    
    
    @RequestMapping(value = "/log")
	public ModelAndView getAllLog()
	{
    	ModelAndView model = new ModelAndView("sysSettings/log/log");
    	return model;
	}
    
    @RequestMapping(value = "/updateIndex")
	public ModelAndView updateIndex()
	{
    	ModelAndView model = new ModelAndView("sysSettings/update/update_password");
    	return model;
	}
      
    protected Object getStoredPassword(UsernamePasswordToken token) {  
    	Object stored = token.getPassword();
        if (stored instanceof char[]) {  
            stored = String.valueOf((char[]) stored);  
        }  
        return stored;  
    }  
    
    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public Object updatePassword(HttpServletRequest request,HttpServletResponse response)
    {
    	Map<String,Object> result = new HashMap<String,Object>();
    	SysUser user;
    	String old_password = request.getParameter("old_password");
    	String new1_password = request.getParameter("new1_password");
    	user = (SysUser)request.getSession().getAttribute(SystemConstants.SESSION_USER);
        if(old_password.equals(user.getPassword())){
            userService.updatePwd(new1_password, user.getId());
            result.put("success", true);
        }else{
        	result.put("success", false);
        }
    	return result;
    }
    /**
     * 
     * 插入日志测试
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/insertSysLog")
    @ResponseBody
    public Object insertSysLog(HttpServletRequest request,HttpServletResponse response)
    {
    	SysLog log = new SysLog();
    	log.setOperId(1);
    	log.setContent("开台101");
    	log.setWind("窗口1");
    	log.setTime(new Date());
    	log.setType(1);
    	log.setOpenWater("123456897");
    	log.setSettlementWater("895454589544");
    	Map<String,Object> res = new HashMap<String, Object>();
    	sysLogService.insert(log);
    	res.put("success", "success");
    	return res; 	
    }
    /**
     * 
     * @param request
     * @param response
     * @param SysLog
     * @return
     */
    @RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,SysLogQuery sysLog) {
    	com.yqsh.diningsys.core.util.Page<SysLogQuery> pagebean = null;
		try {
			pagebean = sysLogService.getListByPage(sysLog);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(sysLog.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
    
    @RequestMapping("/getTreeNodes")
    @ResponseBody
    public  List<SysLogTree> getTreeNodes(String id,String year,String month,String day) {
    	String mtype = getRequest().getParameter("ctype");
		List<SysLogTree> root = new ArrayList<SysLogTree>();
    	if(id.equals("0"))
    	{
	    	SysLogTree one = new SysLogTree();
	    	one.setId("2");
	    	one.setpId("0");
	    	one.setName("后台管理");
	    	one.setCounter(1);
	    	one.setCType(1);
	    	root.add(one);
	    	SysLogTree two = new SysLogTree();
	    	two.setId("3");
	    	two.setpId("0");
	    	two.setName("前台营业");
	    	two.setCounter(1);
	    	two.setCType(2);
	    	root.add(two);
	    	SysLogTree three = new SysLogTree();
	    	three.setId("4");
	    	three.setpId("0");
	    	three.setName("前台质询");
	    	three.setCounter(1);
	    	three.setCType(3);
	    	root.add(three);
	    	SysLogTree four = new SysLogTree();
	    	four.setId("5");
	    	four.setpId("0");
	    	four.setName("厨房划菜");
	    	four.setCounter(1);
	    	four.setCType(4);
	    	root.add(four);
	    	SysLogTree five = new SysLogTree();
	    	five.setId("6");
	    	five.setpId("0");
	    	five.setName("数据管理");
	    	five.setCounter(1);
	    	five.setCType(5);
	    	root.add(five);
	    	SysLogTree six = new SysLogTree();
	    	six.setId("7");
	    	six.setpId("0");
	    	six.setName("厨师配菜");
	    	six.setCounter(1);
	    	six.setCType(6);
	    	root.add(six);
        }
    	else
    	{
    		int ctype = Integer.parseInt(mtype);
    		//获取年份
	    	if(year==null || year.length()==0)
	    	{
	    		List<Map<String,Object>> log = sysLogService.selectYearByType(ctype);
	    		for(Map<String,Object> m :log)
	    		{
	    			SysLogTree s = new SysLogTree();
	    	    	s.setId(id+String.valueOf(m.get("time"))); //父id+year 设置成id
	    	    	s.setpId(id);
	    	    	s.setName(String.valueOf(m.get("time")));
	    	    	s.setCounter(1);
	    	    	s.setCType(ctype);
	    	    	s.setYear(String.valueOf(m.get("time")));
	    	    	root.add(s);
	    		}
	    	}
    		//获取年份
	    	if(year!=null && year.length()>0 && (month == null || month.length() == 0))
	    	{
	        	Map<String,Object> src = new HashMap<String, Object>();
	        	src.put("type", ctype);
	        	src.put("year", year);
	    		List<Map<String,Object>> log = sysLogService.selectMonthByTypeAndYear(src);
	    		for(Map<String,Object> m :log)
	    		{
	    			SysLogTree s = new SysLogTree();
	    	    	s.setId(id+String.valueOf(m.get("year"))+String.valueOf(m.get("month"))); //父id+year 设置成id
	    	    	s.setpId(id);
	    	    	s.setName(String.valueOf(m.get("month")));
	    	    	s.setCounter(1);
	    	    	s.setCType(ctype);
	    	    	s.setYear(String.valueOf(m.get("year")));
	    	    	s.setMonth(String.valueOf(m.get("month")));
	    	    	root.add(s);
	    		}
	    	}
	    	else if(year!=null && year.length()>0 && 
	    			month != null && month.length() > 0 &&
	    			(day == null || day.length() == 0 ))
	    	{
	    		Map<String,Object> src = new HashMap<String, Object>();
	        	src.put("type", ctype);
	        	src.put("year", year);
	        	src.put("month", month);
	    		List<Map<String,Object>> log = sysLogService.selectDayByTypeAndYearAndMonth(src);
	    		for(Map<String,Object> m :log)
	    		{
	    			SysLogTree s = new SysLogTree();
	    	    	s.setId(id+String.valueOf(m.get("year"))+String.valueOf(m.get("month"))+String.valueOf(m.get("day"))); //父id+year 设置成id
	    	    	s.setpId(id);
	    	    	s.setName(String.valueOf(m.get("day")));
	    	    	s.setCounter(1);
	    	    	s.setCType(ctype);
	    	    	s.setYear(String.valueOf(m.get("year")));
	    	    	s.setMonth(String.valueOf(m.get("month")));
	    	    	s.setDay(String.valueOf(m.get("day")));
	    	    	root.add(s);
	    		}
	    	}
	    	else if(year!=null && year.length()>0 && 
	    			month != null && month.length() > 0 &&
	    			day != null && day.length() > 0 )
	    	{
	    		SysLogTree s = new SysLogTree();
    	    	s.setId(id+"/content"); //父id+year 设置成id
    	    	s.setpId(id);
    	    	s.setName("日志");
    	    	s.setCounter(0);
    	    	s.setCType(ctype);
    	    	s.setYear(year);
    	    	s.setMonth(month);
    	    	s.setDay(day);
    	    	root.add(s);
	    	}
	    	
    	}	
    	return root;
    }
    
}