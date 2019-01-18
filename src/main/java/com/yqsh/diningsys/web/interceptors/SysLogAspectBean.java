package com.yqsh.diningsys.web.interceptors;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgGiftForm;
import com.yqsh.diningsys.web.model.archive.DgGiftFormReason;
import com.yqsh.diningsys.web.model.sysSettings.SysLog;
import com.yqsh.diningsys.web.service.sysSettings.SysLogService;

@Aspect
public class SysLogAspectBean {
	/**
	 * 自动注入request,spring 3+
	 */
	@Autowired
	private  HttpServletRequest request;
	@Resource
	private SysLogService sysLogService;
	/**
	 * 
	 * 公共代码日志
	 * @param _jp
	 * @throws Throwable
	 */
	@After("execution (* com.yqsh.diningsys.web.controller.archive.DgPublicCode0Controller.save*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.DgPublicCode0Controller.edit*(..))")
    public void writeLogInfoPublicCodeSave(JoinPoint _jp) throws Throwable{
		Object[] args = _jp.getArgs();
		for(int i=0;i<args.length;i++)
		{
			if(args[0] instanceof  ShiroHttpServletRequest)
			{
				ShiroHttpServletRequest  request = (ShiroHttpServletRequest)args[0];
				SysUser user =(SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
				sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","公共代码增加或修改操作","",""));
				break;
			}
		}
    } 
	
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.DgPublicCode0Controller.delete*(..))")  
    public void writeLogInfoPublicCodeDelete(JoinPoint _jp) throws Throwable{  
		Object[] args = _jp.getArgs();
		for(int i=0;i<args.length;i++)
		{
			if(args[0] instanceof  ShiroHttpServletRequest)
			{
				ShiroHttpServletRequest  request = (ShiroHttpServletRequest)args[0];
				SysUser user =(SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
				sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","公共代码删除操作","",""));
				break;
			}
		}
    }
	 
	/**
	 * 
	 * 员工管理
	 * @param _jp
	 * @throws Throwable
	 */
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbEmpController.save*(..))")  
    public void writeLogInfoSaveTbEmp(JoinPoint _jp) throws Throwable{  
		Object[] args = _jp.getArgs();
		for(int i=0;i<args.length;i++)
		{
			if(args[0] instanceof  ShiroHttpServletRequest)
			{
				ShiroHttpServletRequest  request = (ShiroHttpServletRequest)args[0];
				SysUser user =(SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
				sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","员工信息增加或修改操作","",""));
				break;
			}
		}
    }  
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbEmpController.dele*(..))")  
    public void writeLogInfoSaveTbEmpDelete(JoinPoint _jp) throws Throwable{  
		Object[] args = _jp.getArgs();
		for(int i=0;i<args.length;i++)
		{
			if(args[0] instanceof  ShiroHttpServletRequest)
			{
				ShiroHttpServletRequest  request = (ShiroHttpServletRequest)args[0];
				SysUser user =(SysUser) request.getSession().getAttribute(SystemConstants.SESSION_USER);
				sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","员工信息删除操作","",""));
				break;
			}
		}
    } 
	/**
	 * 
	 * 上菜状态
	 * @param _jp
	 * @throws Throwable
	 */
	@After("execution (* com.yqsh.diningsys.web.controller.archive.ServingController.add*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.ServingController.edit*(..))")  
    public void writeLogInfoServingControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","上菜状态增加或修改操作","",""));
    } 
	@After("execution (* com.yqsh.diningsys.web.controller.archive.ServingController.del*(..))")  
    public void writeLogInfoServingControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","上菜状态删除操作","",""));
    } 
	
	/**
	 * 
	 * 赠单原因
	 * @param _jp
	 * @throws Throwable
	 */
	@After("execution (* com.yqsh.diningsys.web.controller.archive.GiftFormController.add*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.GiftFormController.edit*(..))")  
    public void writeLogInfoGiftFormControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		Object[] args = _jp.getArgs();
		for(int i=0;i<args.length;i++)
		{
			if(args[0] instanceof  DgGiftForm)
			{
				sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","赠单原因增加或修改操作","",""));
				break;
			}
			if(args[0] instanceof  DgGiftFormReason)
			{
				sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","赠单原因类型增加或修改操作","",""));
				break;
			}
		}
		
		
    } 
	@After("execution (* com.yqsh.diningsys.web.controller.archive.ServingController.del*(..))")  
    public void writeLogInfoGiftFormControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		Object[] args = _jp.getArgs();
		for(int i=0;i<args.length;i++)
		{
			if(args[0] instanceof  DgGiftForm)
			{
				sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","赠单原因删除操作","",""));
				break;
			}
			if(args[0] instanceof  DgGiftFormReason)
			{
				sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","赠单原因类型删除操作","",""));
				break;
			}
		}
    }
	
	/**
	 * 
	 * 部门管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbDepController.save*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.TbDepController.edit*(..))")  
    public void writeLogInfoTbDepControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","部门管理增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbDepController.del*(..))")  
    public void writeLogInfoTbDepControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","部门管理删除操作","",""));
    }
	
	
	/**
	 * 
	 * 制作方式
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.DgProMethodsController.add*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.DgProMethodsController.update*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.DgProMethodsController.edit*(..))")  
    public void writeLogInfoDgProMethodsControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","制作方式增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.DgProMethodsController.del*(..))")  
    public void writeLogInfoDgProMethodsControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","制作方式删除操作","",""));
    }
	
	
	
	/**
	 * 
	 * 组织机构
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbOrgController.save*(..))")  
    public void writeLogInfoTTbOrgControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","组织机构增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbDepController.del*(..))")  
    public void writeLogInfoTbOrgControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","组织机构删除操作","",""));
    }
	
	
	/**
	 * 
	 * 营业市别
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbBisController.save*(..))")  
    public void writeLogInfoTbBisControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","营业市别增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbBisController.del*(..))")  
    public void writeLogInfoTbBisControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","营业市别删除操作","",""));
    }
	
	
	/**
	 * 
	 * 品项档案
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.DgItemFileController.toFileType*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.DgItemFileController.toItemFileE*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.DgItemFileController.addItemFil*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.DgItemFileController.edit*(..))")  
    public void writeLogInfoDgItemFileControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项档案增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.DgItemFileController.del*(..))")  
    public void writeLogInfoDgItemFileControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项档案删除操作","",""));
    }
	
	
	
	/**
	 * 
	 * 退菜原因
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbRfcController.save*(..))  or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.TbRfctController.save*(..))")  
    public void writeLogInfoTbRfcControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","退菜原因增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbRfcController.del*(..))  or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.TbRfctController.del*(..))")  
    public void writeLogInfoTbRfcControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","退菜原因删除操作","",""));
    }
	
	/**
	 * 
	 * 整单备注
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbZdbzController.save*(..))")  
    public void writeLogInfoTbZdbzControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","整单备注增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbZdbzController.del*(..))")  
    public void writeLogInfoTbZdbzControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","整单备注删除操作","",""));
    }
	
	
	/**
	 * 
	 * 费用项目
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbFyxmController.save*(..))  or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.TbFylxController.save*(..))")  
    public void writeLogInfoTbFyxmControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","费用项目增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.TbFyxmController.del*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.TbFylxController.del*(..))")  
    public void writeLogInfoTbFyxmControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","费用项目删除操作","",""));
    }
	
	
	/**
	 * 
	 * 消费区域和客位
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.ConsumerSeatController.save*(..))")  
    public void writeLogInfoConsumerSeatControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","消费区域和客位增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.ConsumerSeatController.del*(..))")  
    public void writeLogInfoConsumerSeatControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","消费区域和客位删除操作","",""));
    }
	
	/**
	 * 
	 * Pos档案
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.PosController.save*(..))")  
    public void writeLogInfoPosControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","Pos档案增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.PosController.del*(..))")  
    public void writeLogInfoPosControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","Pos档案删除操作","",""));
    }
	
	
	/**
	 * 
	 * 结算方式
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.DgSettlementWayController.save*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.DgSettlementWayTypeController.save*(..))")  
    public void writeLogInfoDgSettlementWayControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","结算方式增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.archive.DgSettlementWayController.del*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.archive.DgSettlementWayTypeController.del*(..))")  
    public void writeLogInfoDgSettlementWayControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","结算方式删除操作","",""));
    }
	
	
	/**
	 * 
	 * 密码修改
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.sysSettings.SystemConfig.updatePass*(..))")  
    public void writeLogInfoSystemConfigUpdate(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","密码修改操作","",""));
    } 
	
	
	
	/**
	 * 
	 * 数据库备份
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.sysSettings.SystemBackupController.add*(..))")  
    public void writeLogInfoSystemBackupControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","数据库备份操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.sysSettings.SystemBackupController.del*(..))")  
    public void writeLogInfoSystemBackupControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","数据库备份数据删除操作","",""));
    }
	
	
	
	/**
	 * 
	 * 系统使用权限
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.permissions.SysPermissionController.roleUserE*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.permissions.SysPermissionController.edit*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.permissions.SysPermissionController.update*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.permissions.SysPermissionController.userRoleE*(..))")  
    public void writeLogInfoSysPermissionControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","系统使用权限修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.permissions.SysPermissionController.del*(..))")  
    public void writeLogInfoSysPermissionControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","系统使用权限删除操作","",""));
    }
	
	
	
	/**
	 * 
	 * 供应商管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgSupplierController.save*(..))")  
    public void writeLogInfoDgSupplierControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","供应商增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgSupplierController.del*(..))")  
    public void writeLogInfoDgSupplierControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","供应商删除操作","",""));
    }
	
	/**
	 * 
	 * 仓库管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgWarehouseController.save*(..))")  
    public void writeLogInfoDgWarehouseControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","仓库管理增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgWarehouseController.del*(..))")  
    public void writeLogInfoDgWarehouseControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","仓库管理删除操作","",""));
    }
	
	
	/**
	 * 
	 * 物品类型
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgItemTypeController.save*(..))")  
    public void writeLogInfoDgItemTypeControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","物品类型增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgItemTypeController.del*(..))")  
    public void writeLogInfoDgItemTypeControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","物品类型删除操作","",""));
    }
	
	
	
	/**
	 * 
	 * 物品管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgInveItemsController.save*(..))")  
    public void writeLogInfoDgInveItemsControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","物品管理增加或修改操作","",""));
    } 
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgInveItemsController.del*(..))")  
    public void writeLogInfoDgDgInveItemsControllerDelete(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","物品管理删除操作","",""));
    }
	
	
//	
//	/**
//	 * 
//	 * 库存管理
//	 * @param _jp
//	 * @throws Throwable
//	 */
//	
//	@After("execution (* com.yqsh.diningsys.web.controller.*.DgInventoryController.save*(..))")  
//    public void writeLogInfoDgInventoryControllerAdd(JoinPoint _jp) throws Throwable{  
//		HttpSession session=request.getSession(true);
//		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
//		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","库存管理增加或修改操作","",""));
//    } 
//	
//	@After("execution (* com.yqsh.diningsys.web.controller.*.DgInventoryController.del*(..))")  
//    public void writeLogInfoDgInventoryControllerDelete(JoinPoint _jp) throws Throwable{  
//		HttpSession session=request.getSession(true);
//		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
//		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","库存管理删除操作","",""));
//    }
	
	
	/**
	 * 
	 * 采购管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgProcurementController.procSav*(..))")  
    public void writeLogInfoDgProcurementControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","采购管理增加或修改操作","",""));
    } 
	
	
	/**
	 * 
	 * 调拨管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgTransfersController.save*(..))")  
    public void writeLogInfoDgTransfersControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","调拨管理增加或修改操作","",""));
    } 
	
	/**
	 * 
	 * 盘点管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgDiscPointController.save*(..))")  
    public void writeLogInfoDgDiscPointControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","盘点管理增加或修改操作","",""));
    } 
	
	
	/**
	 * 
	 * 部门物料
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After("execution (* com.yqsh.diningsys.web.controller.inve.DgDepaMaterialController.depaMateS*(..))")  
    public void writeLogInfoDgDepaMaterialControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","采购管理增加或修改操作","",""));
    } 
	/**
	 * 
	 * 业务使用权限
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.permissions.BusinessPermissionController.edit*(..)) or execution(*"
			+ " com.yqsh.diningsys.web.controller.permissions.BusinessPermissionController.save*(..))")  
    public void writeLogInfoBusinessPermissionControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","业务使用权限修改操作","",""));
    } 
	
	
	
	/**
	 * 
	 * 费用登记
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.businessMan.TbFydjController.save*(..))")  
    public void writeLogInfoTbFydjControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","费用登记增加或修改操作","",""));
    } 
	
	@After(" execution(* com.yqsh.diningsys.web.controller.businessMan.TbFydjController.del*(..))")  
    public void writeLogInfoTbFydjControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","费用登记删除操作","",""));
    } 
	
	
	
	/**
	 * 
	 * 消费品项显示设置
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.businessMan.DgItemShowRankController.save*(..))")  
    public void writeLogInfoDgItemShowRankControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","消费品项显示设置修改操作","",""));
    } 
	
	
	/**
	 * 
	 * 非会员挂账管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgNonMemberController.save*(..))")  
    public void writeLogInfoDgNonMemberControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","非会员挂账管理增加或修改操作","",""));
    }
	
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgNonMemberController.del*(..))")  
    public void writeLogInfoDgNonMemberControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","非会员挂账管理删除操作","",""));
    }
	
	
	
	
	/**
	 * 
	 * 特约商户管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgSpeciallyBusinessController.save*(..))")  
    public void writeLogInfoDgSpeciallyBusinessControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","特约商户管理增加或修改操作","",""));
    }
	
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgSpeciallyBusinessController.del*(..))")  
    public void writeLogInfoDgSpeciallyBusinessControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","特约商户管理删除操作","",""));
    }
	
	
	/**
	 * 
	 * 服务员服务客位
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ServiceClassController.save*(..))")  
    public void writeLogInfoServiceClassControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","服务员服务客位增加或修改操作","",""));
    }
	
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ServiceClassController.del*(..))")  
    public void writeLogInfoServiceClassControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","服务员服务客位删除操作","",""));
    }
	
	
	/**
	 * 
	 * 消费区域与客位管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.businessMan.ConsumerSeatManagerController.save*(..))")  
    public void writeLogInfoConsumerSeatManagerControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","消费区域与客位管理增加或修改操作","",""));
    }
	
	
	@After(" execution(* com.yqsh.diningsys.web.controller.businessMan.ConsumerSeatManagerController.del*(..))")  
    public void writeLogInfoConsumerSeatManagerControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","包房费用方案删除操作","",""));
    }
	
	
	/**
	 * 
	 * 品项管理
	 * @param _jp
	 * @throws Throwable
	 */
	
	/**
	 * 品项打折权限
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProDiscountController.update*(..))")  
    public void writeLogInfoProDiscountControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项打折权限修改操作","",""));
    }
	
	/*
	 * 品项打折方案
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProDiscountPanController.toSavePlan(..))")  
    public void writeLogInfoProDiscountPanControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项打折方案增加或修改操作","",""));
    }
	
	/*
	 * 品项打折方案星期
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProDiscountPanController.saveWeek*(..))")  
    public void writeLogInfoProDiscountPanControllerSaveWeek(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","一周内设置了不同品项打折方案","",""));
    }
	
	/*
	 * 品项打折方案
	 */
	
	@After("execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProDiscountPanController.del(..))")  
    public void writeLogInfoProDiscountPanControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项打折方案删除操作","",""));
    }
	
	
	/*
	 * 重要活动打折增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProImportantActivityController.save(..))")  
    public void writeLogInfoProImportantActivityControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","重要活动打折增加或修改操作","",""));
    }
	
	/*
	 *  重要活动打折删除操作
	 */
	
	@After("execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProImportantActivityController.del*(..))")  
    public void writeLogInfoProImportantActivityControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","重要活动打折删除操作","",""));
    }
	
	
	/*
	 * 品项会员方案增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgMemberDiscountController.save(..))")  
    public void writeLogInfoDgMemberDiscountControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项会员方案增加修改操作","",""));
    }
	
	/*
	 * 品项会员方案发布
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgMemberDiscountController.publish(..))")  
    public void writeLogInfoDgMemberDiscountControllerPublish(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项会员方案发布操作","",""));
    }
	
	/*
	 *  品项会员方案删除操作
	 */
	
	@After("execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgMemberDiscountController.del*(..))")  
    public void writeLogInfoDgMemberDiscountControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项会员方案删除操作","",""));
    }
	
	/*
	 * 一菜多价按市别增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProMealTimeController.save*(..))")  
    public void writeLogInfoProMealTimeControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","一菜多价按市别增加修改操作","",""));
    }
	
	/*
	 * 一菜多按市别删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProMealTimeController.del*(..))")  
    public void writeLogInfoProMealTimeControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","一菜多按市别删除操作","",""));
    }
	
	
	/*
	 * 一菜多价按消费区域增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProPlaceTimeController.save*(..))")  
    public void writeLogInfoProPlaceTimeControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","一菜多价按消费区域增加修改操作","",""));
    }
	
	/*
	 * 一菜多价按消费区域删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProPlaceTimeController.del*(..))")  
    public void writeLogInfoProPlaceTimeControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","一菜多价按消费区域删除操作","",""));
    }
	
	/*
	 * 一菜多价按星期增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProWeekTimeController.save*(..))")  
    public void writeLogInfoProWeekTimeControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","一菜多价按星期增加修改操作","",""));
    }
	
	/*
	 * 一菜多价按星期删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProWeekTimeController.del*(..))")  
    public void writeLogInfoProWeekTimeControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","一菜多价按星期删除操作","",""));
    }
	
	/*
	 * 促销品项增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgPromotionController.save*(..))")  
    public void writeLogInfoDgPromotionControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","促销品项增加修改操作","",""));
    }
	
	/*
	 * 促销品项删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgPromotionController.del*(..))")  
    public void writeLogInfoDgPromotionControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","促销品项删除操作","",""));
    }
	
	/*
	 * 时价品项增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgCurrentController.save*(..))")  
    public void writeLogInfoDgCurrentControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","时价品项增加修改操作","",""));
    }
	
	/*
	 * 时价品项删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgCurrentController.del*(..))")  
    public void writeLogInfoDgCurrentControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","时价品项删除操作","",""));
    }
	
	/*
	 * 阶梯品项增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProLadderPriceController.save*(..))")  
    public void writeLogInfoProLadderPriceControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","阶梯品项增加修改操作","",""));
    }
	
	/*
	 * 阶梯品项删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProLadderPriceController.del*(..))")  
    public void writeLogInfoProLadderPriceControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","阶梯品项删除操作","",""));
    }
	
	/*
	 * 价格优先级修改操作
	 */
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProPricePriorityController.updata(..)) or "
			+ "execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProPricePriorityController.mo(..))")  
    public void writeLogInfoProPricePriorityControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","价格优先级修改操作","",""));
    }
	
	
	
	/*
	 * 品项沽清增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProItemOutofStockController.save*(..))")  
    public void writeLogInfoProItemOutofStockControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项沽清增加修改操作","",""));
    }
	
	/*
	 * 品项沽清删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.ProItemOutofStockController.del*(..))")  
    public void writeLogInfoProItemOutofStockControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项沽清删除操作","",""));
    }
	
	
	/*
	 * 品项停用增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgItemSaleLimitController.save*(..))")  
    public void writeLogInfoDgItemSaleLimitControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项停用增加修改操作","",""));
    }
	
	/*
	 * 品项停用删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgItemSaleLimitController.del*(..))")  
    public void writeLogInfoDgItemSaleLimitControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项停用删除操作","",""));
    }
	
	/*
	 * 推荐品项增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.RecommedItemController.save*(..))")  
    public void writeLogInfoRecommedItemControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","推荐品项增加修改操作","",""));
    }
	
	/*
	 * 推荐品项删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.RecommedItemController.del*(..))")  
    public void writeLogInfoRecommedItemControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","推荐品项删除操作","",""));
    }
	
	
	/*
	 * 最新菜品增加修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.NewestItemController.save*(..))")  
    public void writeLogInfoNewestItemControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","最新菜品增加修改操作","",""));
    }
	
	/*
	 * 最新菜品删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.NewestItemController.del*(..))")  
    public void writeLogInfoNewestItemControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","最新菜品删除操作","",""));
    }
	
	
	/*
	 *	品项出品部门修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgItemProDepartmentController.save*(..))")  
    public void writeLogInfoDgItemProDepartmentControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项出品部门修改操作","",""));
    }
	
	/*
	 *	特殊品项修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgSpecialItemController.save(..))")  
    public void writeLogInfoDgSpecialItemControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","特殊品项修改操作","",""));
    }
	
	/*
	 *	品项出品厨师修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgItemFromCookController.update*(..))")  
    public void writeLogInfoDgItemFromCookControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","品项出品厨师修改操作","",""));
    }
	
	
	
	/*
	 * 自定义金额修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgCustomMoneyController.save*(..))")  
    public void writeLogInfoDgCustomMoneyControllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","自定义金额修改操作","",""));
    }
	
	/*
	 * 自定义金额删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgCustomMoneyController.del*(..))")  
    public void writeLogInfoDgCustomMoneyControllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","自定义金额删除操作","",""));
    }
	
	
	/*
	 * 赠送品项促销方案修改操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgItemGiftPlanControlller.save*(..))")  
    public void writeLogInfoDgItemGiftPlanControlllerAdd(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统","赠送品项促销方案增加或修改操作","",""));
    }
	
	/*
	 * 赠送品项促销方案删除操作
	 */
	
	@After(" execution(* com.yqsh.diningsys.web.controller.deskBusiness.DgItemGiftPlanControlller.del*(..))")  
    public void writeLogInfoDgItemGiftPlanControlllerDel(JoinPoint _jp) throws Throwable{  
		HttpSession session=request.getSession(true);
		SysUser user =(SysUser) session.getAttribute(SystemConstants.SESSION_USER);
		sysLogService.insertSelective(new SysLog(1,user.getId(),new Date(),"后台系统"," 赠送品项促销方案删除操作","",""));
    }
	
	
}