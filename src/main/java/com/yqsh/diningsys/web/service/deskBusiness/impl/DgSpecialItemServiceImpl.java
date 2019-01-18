package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgSpecialItemMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgSpecialItem;
import com.yqsh.diningsys.web.service.deskBusiness.DgSpecialItemService;
@Service
public class DgSpecialItemServiceImpl extends GenericServiceImpl<DgSpecialItem,Integer> implements DgSpecialItemService{
	@Resource
	private DgSpecialItemMapper dgSpecialItemMapper;
	@Override
	public GenericDao<DgSpecialItem, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgSpecialItemMapper;
	}
	@Override
	public List<DgSpecialItem> getAll() {
		// TODO Auto-generated method stub
		return dgSpecialItemMapper.getAll();
	}
	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return dgSpecialItemMapper.deleteAll();
	}
	@Override
	public int deleteByItemId(int id) {
		// TODO Auto-generated method stub
		return dgSpecialItemMapper.deleteByItemId(id);
	}
	@Override
	public DgSpecialItem seleByItemId(int id) {
		// TODO Auto-generated method stub
		return dgSpecialItemMapper.seleByItemId(id);
	}

}