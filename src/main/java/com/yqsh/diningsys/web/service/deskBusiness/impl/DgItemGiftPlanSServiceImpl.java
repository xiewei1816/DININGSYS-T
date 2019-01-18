package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemGiftPlanSMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlanS;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemGiftPlanSService;
@Service
public class DgItemGiftPlanSServiceImpl extends GenericServiceImpl<DgItemGiftPlanS,Integer> implements DgItemGiftPlanSService{
	@Resource
	private DgItemGiftPlanSMapper DgItemGiftPlanSMapper;
	@Override
	public GenericDao<DgItemGiftPlanS, Integer> getDao() {
		return DgItemGiftPlanSMapper;
	}
	@Override
	public List<DgItemGiftPlanS> seleByPid(Integer id) {
		// TODO Auto-generated method stub
		return DgItemGiftPlanSMapper.seleByPid(id);
	}
	@Override
	public void deleteIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    DgItemGiftPlanSMapper.deleteIds(ids);
	}
	@Override
	public int deleteByItemId(int id) {
		// TODO Auto-generated method stub
		return DgItemGiftPlanSMapper.deleteByItemId(id);
	}
	@Override
	public void insertChilds(List<DgItemGiftPlanS> src) {
		// TODO Auto-generated method stub
		DgItemGiftPlanSMapper.insertChilds(src);
	}

}