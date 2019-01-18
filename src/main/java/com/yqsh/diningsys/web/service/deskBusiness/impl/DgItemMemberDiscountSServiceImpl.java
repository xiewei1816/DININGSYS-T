package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemMemberDiscountSMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscountS;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemMemberDiscountSService;
@Service
public class DgItemMemberDiscountSServiceImpl extends GenericServiceImpl<DgItemMemberDiscountS,Integer> implements DgItemMemberDiscountSService{
	@Resource
	private DgItemMemberDiscountSMapper dgItemMemberDiscountSMapper;
	@Override
	public List<DgItemMemberDiscountS> seleByPid(Integer id) {
		// TODO Auto-generated method stub
		return dgItemMemberDiscountSMapper.seleByPid(id);
	}

	@Override
	public void deleteIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemMemberDiscountSMapper.deleteIds(ids);
	}

	@Override
	public GenericDao<DgItemMemberDiscountS, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgItemMemberDiscountSMapper;
	}

	@Override
	public int deleteByItemId(int id) {
		// TODO Auto-generated method stub
		return dgItemMemberDiscountSMapper.deleteByItemId(id);
	}

	@Override
	public void insertChilds(List<DgItemMemberDiscountS> s) {
		// TODO Auto-generated method stub
		dgItemMemberDiscountSMapper.insertChilds(s);
	}

}
