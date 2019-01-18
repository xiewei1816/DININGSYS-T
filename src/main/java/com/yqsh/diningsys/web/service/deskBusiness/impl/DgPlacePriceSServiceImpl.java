package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgPlacePriceSMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;
import com.yqsh.diningsys.web.service.deskBusiness.DgPlacePriceSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgPlacePriceService;
@Service
public class DgPlacePriceSServiceImpl extends GenericServiceImpl<DgPlacePriceS,Integer> implements DgPlacePriceSService{
	@Resource
	private DgPlacePriceSMapper dgPlacePriceSMapper;
	@Override
	public GenericDao<DgPlacePriceS, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgPlacePriceSMapper;
	}
	@Override
	public List<DgPlacePriceS> selectByPlacePriceId(Integer id) {
		// TODO Auto-generated method stub
		return dgPlacePriceSMapper.selectByPlacePriceId(id);
	}
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgPlacePriceSMapper.deleteIds(ids);
	}
	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return dgPlacePriceSMapper.deleteAll();
	}
	@Override
	public int deleteByItemId(int id) {
		// TODO Auto-generated method stub
		return dgPlacePriceSMapper.deleteByItemId(id);
	}
	@Override
	public DgPlacePriceS selectByItemIdAndPriceId(Map src) {
		// TODO Auto-generated method stub
		return dgPlacePriceSMapper.selectByItemIdAndPriceId(src);
	}
	@Override
	public int insertChilds(List<DgPlacePriceS> record) {
		// TODO Auto-generated method stub
		return dgPlacePriceSMapper.insertChilds(record);
	}
}
