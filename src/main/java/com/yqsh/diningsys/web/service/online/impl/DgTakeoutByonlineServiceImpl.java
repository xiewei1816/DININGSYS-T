package com.yqsh.diningsys.web.service.online.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.web.dao.online.DgTakeoutByonlineMapper;
import com.yqsh.diningsys.web.model.online.DgTakeoutByonline;
import com.yqsh.diningsys.web.service.online.DgTakeoutByonlineService;

@Service
public class DgTakeoutByonlineServiceImpl implements DgTakeoutByonlineService{

	@Autowired
	private DgTakeoutByonlineMapper dgTakeoutByonlineMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return dgTakeoutByonlineMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DgTakeoutByonline record) {
		// TODO Auto-generated method stub
		return dgTakeoutByonlineMapper.insert(record);
	}

	@Override
	public int insertSelective(DgTakeoutByonline record) {
		// TODO Auto-generated method stub
		return dgTakeoutByonlineMapper.insertSelective(record);
	}

	@Override
	public DgTakeoutByonline selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return dgTakeoutByonlineMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DgTakeoutByonline record) {
		// TODO Auto-generated method stub
		return dgTakeoutByonlineMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(DgTakeoutByonline record) {
		// TODO Auto-generated method stub
		return dgTakeoutByonlineMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(DgTakeoutByonline record) {
		// TODO Auto-generated method stub
		return dgTakeoutByonlineMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insertOrEdit_takeout_online(List<Map<String,Object>>  date) {
		// TODO Auto-generated method stub
		return dgTakeoutByonlineMapper.insertOrEdit_takeout_online(date);
	}

	@Override
	public List<DgTakeoutByonline> selectTop20() {
		// TODO Auto-generated method stub
		return dgTakeoutByonlineMapper.selectTop20();
	}

	@Override
	public int updateIds(String date) {
		// TODO Auto-generated method stub
	    List ids = new ArrayList();
        Collections.addAll(ids,date.split(","));
		return dgTakeoutByonlineMapper.updateIds(ids);
	}

}
