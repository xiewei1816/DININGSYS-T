package com.yqsh.diningsys.web.service.businessMan.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.businessMan.DgAreaManagerMapper;
import com.yqsh.diningsys.web.model.businessMan.DgAreaManager;
import com.yqsh.diningsys.web.service.businessMan.DgAreaManagerService;
@Service
public class DgAreaManagerServiceImpl extends GenericServiceImpl<DgAreaManager,Integer> implements DgAreaManagerService{
	@Resource
	private DgAreaManagerMapper dgAreaManagerMapper;
	@Override
	public GenericDao<DgAreaManager, Integer> getDao() {
		return dgAreaManagerMapper;
	}
	@Override
	public int selectCountByAreaId(Integer id) {
		// TODO Auto-generated method stub
		return dgAreaManagerMapper.selectCountByAreaId(id);
	}
	@Override
	public DgAreaManager selectByAreaId(Integer id) {
		// TODO Auto-generated method stub
		return dgAreaManagerMapper.selectByAreaId(id);
	}
	@Override
	public int insertBackId(DgAreaManager record) {
		// TODO Auto-generated method stub
		return dgAreaManagerMapper.insertBackId(record);
	}
	@Override
	public List<DgAreaManager> selectAll() {
		// TODO Auto-generated method stub
		return dgAreaManagerMapper.selectAll();
	}
	@Override
	public int getAllCount() {
		// TODO Auto-generated method stub
		return dgAreaManagerMapper.getAllCount();
	}

}
