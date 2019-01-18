package com.yqsh.diningsys.web.service.businessMan.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatItemMapper;
import com.yqsh.diningsys.web.model.businessMan.DgSeatItem;
import com.yqsh.diningsys.web.service.businessMan.DgSeatItemService;
@Service
public class DgSeatItemServiceImpl extends GenericServiceImpl<DgSeatItem,Integer> implements DgSeatItemService{
	@Resource
	private DgSeatItemMapper dgSeatItemMapper;
	@Override
	public GenericDao<DgSeatItem, Integer> getDao() {
		return dgSeatItemMapper;
	}
	@Override
	public List<DgSeatItem> getBySeatId(Integer id) {
		// TODO Auto-generated method stub
		return dgSeatItemMapper.getBySeatId(id);
	}
	@Override
	public int deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    return dgSeatItemMapper.deleteIds(ids);
	}
	@Override
	public int deleteBySeatId(Integer id) {
		// TODO Auto-generated method stub
		return dgSeatItemMapper.deleteBySeatId(id);
	}
}