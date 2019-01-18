package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
import com.yqsh.diningsys.web.model.sysSettings.SysLog;

public interface DgProductPhaseLeftmenuService extends GenericService<DgProductPhaseLeftmenu,Integer>{
	
	List<DgProductPhaseLeftmenu> selectByParentPrimaryKey(Integer parentId);
}
