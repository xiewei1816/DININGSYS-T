package com.yqsh.diningsys.web.service.businessMan.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatManagerMapper;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;
import com.yqsh.diningsys.web.service.businessMan.DgSeatManagerService;
@Service
public class DgSeatManagerServiceImpl extends GenericServiceImpl<DgSeatManager,Integer> implements DgSeatManagerService{
	@Resource
	private DgSeatManagerMapper dgSeatManagerMapper;
	@Override
	public GenericDao<DgSeatManager, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgSeatManagerMapper;
	}
	@Override
	public DgSeatManager selectBySeatId(Integer id) {
		// TODO Auto-generated method stub
		return dgSeatManagerMapper.selectBySeatId(id);
	}
	@Override
	public List<DgSeatManager> selectDetailBySeatId(Integer id) {
		// TODO Auto-generated method stub
		return dgSeatManagerMapper.selectDetailBySeatId(id);
	}
	@Override
	public List<DgSeatManager> selectAllDetailBySeatId() {
		// TODO Auto-generated method stub
		return dgSeatManagerMapper.selectAllDetailBySeatId();
	}
	@Override
	public int getAllCount() {
		// TODO Auto-generated method stub
		return dgSeatManagerMapper.getAllCount();
	}
	@Override
	public int getCountByAreaId(Integer id) {
		// TODO Auto-generated method stub
		return dgSeatManagerMapper.getCountByAreaId(id);
	}

}