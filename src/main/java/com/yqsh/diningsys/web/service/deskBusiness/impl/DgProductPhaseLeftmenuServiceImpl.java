package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgProductPhaseLeftmenuMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
import com.yqsh.diningsys.web.model.sysSettings.SysLog;
import com.yqsh.diningsys.web.service.deskBusiness.DgProductPhaseLeftmenuService;
import com.yqsh.diningsys.web.service.sysSettings.SysLogService;
@Service
public class DgProductPhaseLeftmenuServiceImpl extends GenericServiceImpl<DgProductPhaseLeftmenu,Integer> implements DgProductPhaseLeftmenuService{
	@Resource
	private DgProductPhaseLeftmenuMapper dgProductPhaseLeftmenuMapper;
	@Override
	public GenericDao<DgProductPhaseLeftmenu, Integer> getDao() {
		return dgProductPhaseLeftmenuMapper;
	}
	@Override
	public List<DgProductPhaseLeftmenu> selectByParentPrimaryKey(Integer parentId) {
		// TODO Auto-generated method stub
		return dgProductPhaseLeftmenuMapper.selectByParentPrimaryKey(parentId);
	}

}
