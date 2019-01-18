package com.yqsh.diningsys.web.service.sysSettings.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.sysSettings.SysLogMapper;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.sysSettings.SysLog;
import com.yqsh.diningsys.web.model.sysSettings.SysLogQuery;
import com.yqsh.diningsys.web.service.sysSettings.SysLogService;
@Service
public class SysLogServiceImpl extends GenericServiceImpl<SysLog,Integer> implements SysLogService{
    @Resource
    private SysLogMapper sysLogMapper;

	@Override
	public GenericDao<SysLog, Integer> getDao() {
		// TODO Auto-generated method stub
		return sysLogMapper;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(SysLog record) {
		// TODO Auto-generated method stub
		return sysLogMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public  List<Map<String,Object>> selectYearByType(int type) {
		// TODO Auto-generated method stub
		return sysLogMapper.selectYearByType(type);
	}

	@Override
	public List<Map<String, Object>> selectMonthByTypeAndYear(
			Map<String, Object> src) {
		// TODO Auto-generated method stub
		return sysLogMapper.selectMonthByTypeAndYear(src);
	}

	@Override
	public List<Map<String, Object>> selectDayByTypeAndYearAndMonth(
			Map<String, Object> src) {
		// TODO Auto-generated method stub
		return sysLogMapper.selectDayByTypeAndYearAndMonth(src);
	}

	@Override
	public Page<SysLogQuery> getListByPage(SysLogQuery page) {
		Integer totle = sysLogMapper.countListByPage(page);
		List<SysLogQuery> list = sysLogMapper.getListByPage(page);
		return PageUtil.getPage(totle, page.getPage(),list, page.getRows());
	}

	@Override
	public int countListByPage(SysLogQuery page) {
		// TODO Auto-generated method stub
		return sysLogMapper.countListByPage(page);
	}
		
}
