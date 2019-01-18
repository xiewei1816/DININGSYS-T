package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgForMealTimePriceSMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePriceS;
import com.yqsh.diningsys.web.service.deskBusiness.DgForMealTimePriceSService;
@Service
public class DgForMealTimePriceSServiceImpl extends GenericServiceImpl<DgForMealTimePriceS,Integer> implements DgForMealTimePriceSService{
	@Resource
	private DgForMealTimePriceSMapper dgForMealTimePriceSMapper;
	
	@Override
	public GenericDao<DgForMealTimePriceS, Integer> getDao() {
		return dgForMealTimePriceSMapper;
	}

	@Override
	public List<DgForMealTimePriceS> selectByMealPriceId(Integer id) {
		return dgForMealTimePriceSMapper.selectByMealPriceId(id);
	}
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgForMealTimePriceSMapper.deleteIds(ids);
	}
	@Override
	public int deleteAll() {
		return dgForMealTimePriceSMapper.deleteAll();
	}

	@Override
	public int deleteByItemId(int id) {
		return dgForMealTimePriceSMapper.deleteByItemId(id);
	}

	@Override
	public DgForMealTimePriceS selectByItemIdAndMealtime(Map src) {
		return dgForMealTimePriceSMapper.selectByItemIdAndMealtime(src);
	}
}
