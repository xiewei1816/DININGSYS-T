package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.List;





import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgGateItemAllowCustomMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgGateItemAllowCustom;
import com.yqsh.diningsys.web.service.deskBusiness.DgGateItemAllowCustomService;
@Service
public class DgGateItemAllowCustomServiceImpl extends GenericServiceImpl<DgGateItemAllowCustom,Integer> implements DgGateItemAllowCustomService{
	@Resource
	private DgGateItemAllowCustomMapper dgGateItemAllowCustomMapper;
	@Override
	public GenericDao<DgGateItemAllowCustom, Integer> getDao() {
		return dgGateItemAllowCustomMapper;
	}
	@Override
	public int selectByGateItemId(int id) {
		// TODO Auto-generated method stub
		return dgGateItemAllowCustomMapper.selectByGateItemId(id);
	}
	@Override
	public List<DgGateItemAllowCustom> getAll() {
		// TODO Auto-generated method stub
		return dgGateItemAllowCustomMapper.getAll();
	}
	@Override
	public int deleteByGateItemId(int id) {
		// TODO Auto-generated method stub
		return dgGateItemAllowCustomMapper.deleteByGateItemId(id);
	}
	@Override
	public int deleteByGateId(int id) {
		// TODO Auto-generated method stub
		return dgGateItemAllowCustomMapper.deleteByGateId(id);
	}

}