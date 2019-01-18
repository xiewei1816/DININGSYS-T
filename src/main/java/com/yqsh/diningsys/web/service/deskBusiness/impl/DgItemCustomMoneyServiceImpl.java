package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;










import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemCustomMoneyMapper;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCustomMoney;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemCustomMoneyService;

@Service
public class DgItemCustomMoneyServiceImpl extends GenericServiceImpl<DgItemCustomMoney,Integer> implements DgItemCustomMoneyService{
	@Resource
	private DgItemCustomMoneyMapper dgItemCustomMoneyMapper;
	@Override
	public GenericDao<DgItemCustomMoney, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgItemCustomMoneyMapper;
	}
	@Override
	public List<DgItemCustomMoney> getAllByCustomNameId(int id) {
		// TODO Auto-generated method stub
		return dgItemCustomMoneyMapper.getAllByCustomNameId(id);
	}
	@Override
	public int deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    return dgItemCustomMoneyMapper.deleteIds(ids);
	}
	@Override
	public int deleteByItemIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    return dgItemCustomMoneyMapper.deleteByItemIds(ids);
	}
	@Override
	public List<DgItemCustomMoney> selectAllByItemIds(List<Integer> ids) {
	    return dgItemCustomMoneyMapper.selectAllByItemIds(ids);
	}
	@Override
	public int deleteByCustomId(Integer id) {
		// TODO Auto-generated method stub
		return dgItemCustomMoneyMapper.deleteByCustomId(id);
	}
	@Override
	public int deleteByItemId(int id) {
		// TODO Auto-generated method stub
		return dgItemCustomMoneyMapper.deleteByItemId(id);
	}
	@Override
	public void insertChilds(List<DgItemCustomMoney> src) {
		// TODO Auto-generated method stub
		dgItemCustomMoneyMapper.insertChilds(src);
	}

}
