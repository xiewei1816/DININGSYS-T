package com.yqsh.diningsys.web.service.sysSettings.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.sysSettings.SysBackupDatabaseMapper;
import com.yqsh.diningsys.web.model.sysSettings.SysBackupDatabase;
import com.yqsh.diningsys.web.model.sysSettings.SysLog;
import com.yqsh.diningsys.web.model.sysSettings.SysLogQuery;
import com.yqsh.diningsys.web.service.sysSettings.SysBackupDataBaseService;
import com.yqsh.diningsys.web.service.sysSettings.SysLogService;
@Service
public class SysBackupDataBaseServiceImpl extends GenericServiceImpl<SysBackupDatabase,Integer> implements SysBackupDataBaseService{

	@Resource
	private SysBackupDatabaseMapper sysBackupDatabaseMapper;
	
	@Override
	public Page<SysBackupDatabase> getListByPage(SysBackupDatabase page) {
		Integer totle = sysBackupDatabaseMapper.countListByPage(page);
		List<SysBackupDatabase> list = sysBackupDatabaseMapper.getListByPage(page);
		return PageUtil.getPage(totle, page.getPage(),list, page.getRows());
	}

	@Override
	public int countListByPage(SysBackupDatabase page) {
		// TODO Auto-generated method stub
		return sysBackupDatabaseMapper.countListByPage(page);
	}

	@Override
	public GenericDao<SysBackupDatabase, Integer> getDao() {
		// TODO Auto-generated method stub
		return sysBackupDatabaseMapper;
	}

	@Override
	public int deleteData(String ids) {
        List id = new ArrayList();
        Collections.addAll(id,ids.split(","));
        return sysBackupDatabaseMapper.deletData(id);
	}

	@Override
	public List<SysBackupDatabase> selectFromIds(String ids) {
		 List id = new ArrayList();
	     Collections.addAll(id,ids.split(","));
		 return sysBackupDatabaseMapper.selectFromIds(id);
	}
}
