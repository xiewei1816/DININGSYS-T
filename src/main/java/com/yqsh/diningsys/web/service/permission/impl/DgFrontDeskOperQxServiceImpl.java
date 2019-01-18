package com.yqsh.diningsys.web.service.permission.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.permission.DgFrontDeskOperQxMapper;
import com.yqsh.diningsys.web.model.permission.DgFrontDeskOperQx;
import com.yqsh.diningsys.web.model.permission.DgFrontDeskOperQxArray;
import com.yqsh.diningsys.web.service.permission.DgFrontDeskOperQxService;
@Service
public class DgFrontDeskOperQxServiceImpl extends GenericServiceImpl<DgFrontDeskOperQx,Integer> implements DgFrontDeskOperQxService{
	@Resource
	private DgFrontDeskOperQxMapper dgFrontDeskOperQxMapper;
	@Override
	public GenericDao<DgFrontDeskOperQx, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgFrontDeskOperQxMapper;
	}
	@Override
	public int updateByPrimaryKeyInIds(String ids) {
		// TODO Auto-generated method stub
		 List<String> id = new ArrayList();
	     Collections.addAll(id,ids.split(","));
		return dgFrontDeskOperQxMapper.updateByPrimaryKeyInIds(id);
	}
	@Override
	public int updateByPrimaryKeyNotInIds(String ids) {
		// TODO Auto-generated method stub
		 List<String> id = new ArrayList();
	     Collections.addAll(id,ids.split(","));
		return dgFrontDeskOperQxMapper.updateByPrimaryKeyNotInIds(id);
	}
	@Override
	public List<DgFrontDeskOperQx> seleAll() {
		// TODO Auto-generated method stub
		return dgFrontDeskOperQxMapper.seleAll();
	}
	@Override
	public int saveFrontDeskQx(DgFrontDeskOperQxArray qxs) {
		// TODO Auto-generated method stub
		 List<String> ids = new ArrayList();
		for(DgFrontDeskOperQx q:qxs.getDeskQx())
		{
			if(q.getVal() != null)
			{
				ids.add(q.getCode());
			}
		}
		if(ids.size()>0)
		{
			dgFrontDeskOperQxMapper.updateByPrimaryKeyInIds(ids);
			dgFrontDeskOperQxMapper.updateByPrimaryKeyNotInIds(ids);
		}
		else
		{
			dgFrontDeskOperQxMapper.updateAllNoQx();
		}
		return 0;
	}
}