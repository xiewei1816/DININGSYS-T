package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemProDepSMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDepS;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemProDepSService;
@Service
public class DgItemProDepSServiceImpl extends GenericServiceImpl<DgItemProDepS,Integer> implements DgItemProDepSService{
	@Resource
	private DgItemProDepSMapper  DgItemProDepSMapper;
	@Override
	public GenericDao<DgItemProDepS, Integer> getDao() {
		// TODO Auto-generated method stub
		return DgItemProDepSMapper;
	}
	@Override
	public List<DgItemProDepS> selectByPlaceParentId(Integer id) {
		// TODO Auto-generated method stub
		return DgItemProDepSMapper.selectByPlaceParentId(id);
	}
	@Override
	public void deleteIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    DgItemProDepSMapper.deleteIds(ids);
	}
	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return DgItemProDepSMapper.deleteAll();
	}
	@Override
	public int selectByEarchId(Map<String, Object> obj) {
		// TODO Auto-generated method stub
		return DgItemProDepSMapper.selectByEarchId(obj);
	}
	@Override
	public int deleteByItemId(int id) {
		// TODO Auto-generated method stub
		return DgItemProDepSMapper.deleteByItemId(id);
	}
	@Override
	public int insertChilds(List<DgItemProDepS> record) {
		// TODO Auto-generated method stub
		return DgItemProDepSMapper.insertChilds(record);
	}


}
