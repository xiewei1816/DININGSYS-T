
package com.yqsh.diningsys.web.service.businessMan.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatChargingSchemeSMapper;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingSchemeS;
import com.yqsh.diningsys.web.service.businessMan.DgSeatChargingSchemeSService;
@Service
public class DgSeatChargingSchemeSServiceImpl extends GenericServiceImpl<DgSeatChargingSchemeS,Integer> implements  DgSeatChargingSchemeSService{
	@Autowired
	private DgSeatChargingSchemeSMapper dgSeatChargingSchemeSMapper;
	@Override
	public GenericDao<DgSeatChargingSchemeS, Integer> getDao() {
		return dgSeatChargingSchemeSMapper;
	}

	@Override
	public List<DgSeatChargingSchemeS> seleByPid(Integer id) {
		// TODO Auto-generated method stub
		return dgSeatChargingSchemeSMapper.seleByPid(id);
	}

	@Override
	public void deleteIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgSeatChargingSchemeSMapper.deleteIds(ids);
	}

	@Override
	public void deleteAllByPid(Integer id) {
		// TODO Auto-generated method stub
		dgSeatChargingSchemeSMapper.deleteAllByPid(id);
	}

	@Override
	public List<DgSeatChargingSchemeS> seleByPidNoSd(Integer id) {
		// TODO Auto-generated method stub
		return dgSeatChargingSchemeSMapper.seleByPidNoSd(id);
	}

}