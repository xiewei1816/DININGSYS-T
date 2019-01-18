package com.yqsh.diningsys.web.service.businessMan.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.businessMan.DgAreaLimitItemMapper;
import com.yqsh.diningsys.web.model.businessMan.DgAreaLimitItem;
import com.yqsh.diningsys.web.service.businessMan.DgAreaLimitItemService;
@Service
public class DgAreaLimitItemServiceImpl extends GenericServiceImpl<DgAreaLimitItem,Integer> implements DgAreaLimitItemService{
	@Resource
	private DgAreaLimitItemMapper dgAreaLimitItemMapper;
	@Override
	public GenericDao<DgAreaLimitItem, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgAreaLimitItemMapper;
	}
	@Override
	public List<DgAreaLimitItem> getByAreaId(Integer id) {
		// TODO Auto-generated method stub
		return dgAreaLimitItemMapper.getByAreaId(id);
	}
	@Override
	public int deleteIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    return dgAreaLimitItemMapper.deleteIds(ids);
	}
	@Override
	public int deleteByAreaId(Integer id) {
		// TODO Auto-generated method stub
		return dgAreaLimitItemMapper.deleteByAreaId(id);
	}

}
