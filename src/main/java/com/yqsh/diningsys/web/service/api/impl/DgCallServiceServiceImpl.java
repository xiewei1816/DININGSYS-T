package com.yqsh.diningsys.web.service.api.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.web.dao.api.DgCallServiceMapper;
import com.yqsh.diningsys.web.model.api.DgCallService;
import com.yqsh.diningsys.web.service.api.DgCallServiceService;

@Service("dgCallServiceServiceImpl")
public class DgCallServiceServiceImpl implements DgCallServiceService{
	@Resource
	private DgCallServiceMapper dgCallServiceMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DgCallService record) {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.insert(record);
	}

	@Override
	public int insertSelective(DgCallService record) {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.insertSelective(record);
	}

	@Override
	public DgCallService selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DgCallService record) {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(DgCallService record) {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<DgCallService> selectTop5(Map orgs) {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.selectTop5(orgs);
	}

	@Override
	public List<DgCallService> selectTopZccf5() {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.selectTopZccf5();
	}

	@Override
	public int deleteIds(List ids) {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.deleteIds(ids);
	}

	@Override
	public Integer selectOnlineCount() {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.selectOnlineCount();
	}

	@Override
	public DgCallService getFirstItem() {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.getFirstItem();
	}

	@Override
	public int selectCxptCount() {
		return dgCallServiceMapper.selectCxptCount();
	}

	@Override
	public List<DgCallService> selectCxptTop5() {
		return dgCallServiceMapper.selectCxptTop5();
	}

	@Override
	public DgCallService getFirstCxptItem() {
		return dgCallServiceMapper.getFirstCxptItem();
	}
}
