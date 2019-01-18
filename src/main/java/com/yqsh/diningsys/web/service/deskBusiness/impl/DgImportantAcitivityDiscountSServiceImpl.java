package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgImportantAcitivityDiscountSMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantAcitivityDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.service.deskBusiness.DgImportantAcitivityDiscountSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountProgrammeService;
@Service
public class DgImportantAcitivityDiscountSServiceImpl extends GenericServiceImpl<DgImportantAcitivityDiscountS,Integer> implements DgImportantAcitivityDiscountSService{

	@Resource
	private DgImportantAcitivityDiscountSMapper dgImportantAcitivityDiscountSMapper;
	@Override
	public GenericDao<DgImportantAcitivityDiscountS, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgImportantAcitivityDiscountSMapper;
	}
	@Override
	public List<DgImportantAcitivityDiscountS> seleByPid(Integer id) {
		// TODO Auto-generated method stub
		return dgImportantAcitivityDiscountSMapper.seleByPid(id);
	}
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgImportantAcitivityDiscountSMapper.deleteIds(ids);
	}
	@Override
	public int deleteByGateId(int id) {
		// TODO Auto-generated method stub
		return dgImportantAcitivityDiscountSMapper.deleteByGateId(id);
	}
	@Override
	public Map<String, Object> selectByItemId(Integer id) {
		// TODO Auto-generated method stub
		return dgImportantAcitivityDiscountSMapper.selectByItemId(id);
	}
}
