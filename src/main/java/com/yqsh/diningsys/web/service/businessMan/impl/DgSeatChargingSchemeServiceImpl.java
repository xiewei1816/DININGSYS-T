package com.yqsh.diningsys.web.service.businessMan.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatChargingSchemeMapper;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingScheme;
import com.yqsh.diningsys.web.service.businessMan.DgSeatChargingSchemeService;
@Service
public class DgSeatChargingSchemeServiceImpl extends GenericServiceImpl<DgSeatChargingScheme,Integer> implements  DgSeatChargingSchemeService{
	@Resource
	private DgSeatChargingSchemeMapper dgSeatChargingSchemeMapper;
	@Override
	public GenericDao<DgSeatChargingScheme, Integer> getDao() {
		return dgSeatChargingSchemeMapper;
	}
	@Override
	public int insertBackId(DgSeatChargingScheme record) {
		// TODO Auto-generated method stub
		return dgSeatChargingSchemeMapper.insertBackId(record);
	}
	@Override
	public Page<DgSeatChargingScheme> getPage(
			DgSeatChargingScheme dgSeatChargingScheme) {
		Integer totle = dgSeatChargingSchemeMapper.countAllData(dgSeatChargingScheme);
	    List<DgSeatChargingScheme> list = dgSeatChargingSchemeMapper.getAllData(dgSeatChargingScheme);
	    return PageUtil.getPage(totle, dgSeatChargingScheme.getPage(),list, dgSeatChargingScheme.getRows());
	}
	@Override
	public void deleteIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgSeatChargingSchemeMapper.deleteIds(ids);
	}
	@Override
	public List<DgSeatChargingScheme> seleAll() {
		// TODO Auto-generated method stub
		return dgSeatChargingSchemeMapper.seleAll();
	}
	@Override
	public void trash(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgSeatChargingSchemeMapper.trash(ids);
		
	}
	@Override
	public void restore(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgSeatChargingSchemeMapper.restore(ids);
	}
	
	

}
