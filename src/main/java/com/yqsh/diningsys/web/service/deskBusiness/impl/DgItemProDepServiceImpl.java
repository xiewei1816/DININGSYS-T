package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemProDepMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDep;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemProDepService;
@Service
public class DgItemProDepServiceImpl extends GenericServiceImpl<DgItemProDep,Integer> implements DgItemProDepService{
	@Resource
	private DgItemProDepMapper dgItemProDepMapper;
	@Override
	public GenericDao<DgItemProDep, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgItemProDepMapper;
	}
	@Override
	public List<DgItemProDep> getAllData(Map<String,Object>  record) {
		// TODO Auto-generated method stub
		return dgItemProDepMapper.getAllData(record);
	}
	@Override
	public void deleteIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemProDepMapper.deleteIds(ids);
	}
	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return dgItemProDepMapper.deleteAll();
	}
	@Override
	public int insertBackId(DgItemProDep record) {
		// TODO Auto-generated method stub
		return dgItemProDepMapper.insertBackId(record);
	}
	@Override
	public int getCountByItemId(Integer id) {
		// TODO Auto-generated method stub
		return dgItemProDepMapper.getCountByItemId(id);
	}
	@Override
	public DgItemProDep selectByItemId(Integer id) {
		// TODO Auto-generated method stub
		return dgItemProDepMapper.selectByItemId(id);
	}
	@Override
	public int deleteByItemId(int id) {
		// TODO Auto-generated method stub
		return dgItemProDepMapper.deleteByItemId(id);
	}

}