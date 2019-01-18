package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgWeekDiscountMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgWeekDiscount;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemTypeDiscountService;
import com.yqsh.diningsys.web.service.deskBusiness.DgWeekDiscountService;
@Service
public class DgWeekDiscountServiceImpl extends GenericServiceImpl<DgWeekDiscount,Integer> implements DgWeekDiscountService{

	@Resource
	private DgWeekDiscountMapper dgWeekDiscountMapper;
	
	@Override
	public GenericDao<DgWeekDiscount, Integer> getDao() {
		return dgWeekDiscountMapper;
	}

	@Override
	public List<DgWeekDiscount> selectAll() {
		return dgWeekDiscountMapper.selectAll();
	}

	@Override
	public int updataByDelete(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
		return dgWeekDiscountMapper.updataByDelete(ids);
	}

	@Override
	public DgWeekDiscount selectByName(String name) {
		// TODO Auto-generated method stub
		return dgWeekDiscountMapper.selectByName(name);
	}

}
