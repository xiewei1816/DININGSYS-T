package com.yqsh.diningsys.web.controller.sysSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgServing;
import com.yqsh.diningsys.web.model.sysSettings.SysBackupDatabase;
import com.yqsh.diningsys.web.model.sysSettings.SysLogQuery;
import com.yqsh.diningsys.web.service.sysSettings.DgUrlSettingService;
import com.yqsh.diningsys.web.service.sysSettings.SysBackupDataBaseService;

@Controller
@RequestMapping("/sysbackup")
public class SystemBackupController extends BaseController{
	@Autowired
	private SysBackupDataBaseService sysBackupDataBaseService;
	@Autowired
	private DgUrlSettingService dgUrlSettingService;
    @RequestMapping(value = "/index")
	public ModelAndView getAllLog()
	{
    	ModelAndView model = new ModelAndView("sysSettings/backup/index");
    	return model;
	}
    
    /**
     * 增加备份文件
     * @return
     */
    @RequestMapping(value = "/toBackup")
    public ModelAndView toBackup()
    {
    	ModelAndView model = new ModelAndView("sysSettings/backup/backup_add");
    	return model;
    }
    
    
    @RequestMapping(value = "/doBackup",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addData(HttpServletRequest request,HttpServletResponse response,SysBackupDatabase sysBackupDatabase){
    	
    	InputStream is=SystemBackupController.class.getResourceAsStream("/application.properties");  
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String connection = p.getProperty("jdbc.url");
		String conSplit1[] = connection.split("\\//");
		String ip = conSplit1[1].split(":")[0];
		String port = conSplit1[1].split(":")[1].split("\\/")[0];
		String conSplit2 = conSplit1[1].split("\\/")[1];
		String dataBaseName = conSplit2.split("[?]")[0];
				
		String username = p.getProperty("jdbc.username");
		String password = p.getProperty("jdbc.password");
		String mysqlbin = dgUrlSettingService.selectByCode("mysql.bin").getCode();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd-HH-mm-ss");
		String fileName = format.format(new Date())+".sql";
//		String path = request.getServletContext()
//				.getRealPath("/backup");
		String path = "D:/backup";
		File fpath = new File(path);
		if (!fpath.exists()) {
			fpath.mkdirs();
		}
		
		
		boolean ret = backup(path+"/"+fileName,dataBaseName,ip,port,mysqlbin,username,password);
		
		if(ret)
		{
			SysUser user = (SysUser)request.getSession().getAttribute(SystemConstants.SESSION_USER);
			sysBackupDatabase.setBackupFileName(fileName);
			sysBackupDatabase.setPath(path);
			sysBackupDatabase.setTime(new Date());
			sysBackupDatabase.setBackupName(user.getEmpName());
	        try {        	
	        	sysBackupDataBaseService.insert(sysBackupDatabase);
	            return returnSuccess();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return returnSuccess();
		}
		else
		{
			return returnFail();
		}
    }
    
   
    
    @RequestMapping("/delData")
    @ResponseBody
    public ResultInfo delData(String ids){
        try {
        	
        	List<SysBackupDatabase> backups = sysBackupDataBaseService.selectFromIds(ids);
        	if(backups!=null && backups.size() >0)
        	{
	        	for(int i=0;i<backups.size();i++)
	        	{
		        	File file = new File(backups.get(i).getPath()+"/"+backups.get(i).getBackupFileName());  
		        	    // 路径为文件且不为空则进行删除  
		        	    if (file.isFile() && file.exists()) {  
		        	        file.delete();  
		        	}  
	        	}
        	}
        	sysBackupDataBaseService.deleteData(ids);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
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
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,SysBackupDatabase sysBackup) {
    	com.yqsh.diningsys.core.util.Page<SysBackupDatabase> pagebean = null;
		try {
			
			pagebean = sysBackupDataBaseService.getListByPage(sysBackup);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(sysBackup.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
    
    
    
    @RequestMapping(value = "/test")
    @ResponseBody
    public Object  test(HttpServletRequest request,HttpServletResponse response)
    {
		InputStream is=SystemBackupController.class.getResourceAsStream("/application.properties");  
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String connection = p.getProperty("jdbc.url");
		String conSplit1[] = connection.split("\\//");
		String ip = conSplit1[1].split(":")[0];
		String port = conSplit1[1].split(":")[1].split("\\/")[0];
		String conSplit2 = conSplit1[1].split("\\/")[1];
		String dataBaseName = conSplit2.split("[?]")[0];
				
		String username = p.getProperty("jdbc.username");
		String password = p.getProperty("jdbc.password");
		String mysqlbin = p.getProperty("mysql.bin");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd-HH-mm-ss");
		String fileName = format.format(new Date())+".sql";
		String path = request.getServletContext()
				.getRealPath("/backup");
		File fpath = new File(path);
		if (!fpath.exists()) {
			fpath.mkdirs();
		}
		
		
		boolean ret = backup(path+"/"+fileName,dataBaseName,ip,port,mysqlbin,username,password);
		
		if(ret)
		{
			 return returnSuccess();
		}
		else
		{
			 return returnFail();
		}
    }
    
    /** 
     * 备份数据库，如果指定路径的文件不存在会自动生成 
     *  
     * @param dest 
     *            备份文件的路径 
     * @param dbname 
     *            要备份的数据库 
     * @throws InterruptedException 
     */  
    public boolean backup(String exportPath, String dbname,String host,String port, 
		String mysqlBinPath,String username,String password) {  
        Process proc; 
        String shellStr = "";  
        shellStr = /*mysqlBinPath + */" mysqldump -h " + host  
                + " -P " + port + " -u " + username + " -p" //★第二个-p后面不能有空格，否则将被认为是数据库的名称  
                + password + " --result-file=" + exportPath  
                + " --default-character-set=utf8 " + dbname;  
        System.out.print("##############" + shellStr);  
        try {  
        	Runtime runtime = Runtime.getRuntime();  
        	proc = runtime.exec(shellStr);//这里简单一点异常我就直接往上抛  
            int tag = proc.waitFor();// 等待进程终止  
            return tag == 0;
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } finally {  
            
        } 
        return false;
    }
}
