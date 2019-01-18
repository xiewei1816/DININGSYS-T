package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemPricePriorityMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPricePriority;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemPricePriorityService;
@Service
public class DgItemPricePriorityServiceImpl extends GenericServiceImpl<DgItemPricePriority,Integer> implements DgItemPricePriorityService{
	@Resource
	private DgItemPricePriorityMapper dgItemPricePriorityMapper;
	@Override
	public GenericDao<DgItemPricePriority, Integer> getDao() {
		return dgItemPricePriorityMapper;
	}
	@Override
	public List<DgItemPricePriority> getAll() {
		return dgItemPricePriorityMapper.getAll();
	}
	@Override
	public int updateAll() {
		return dgItemPricePriorityMapper.updateAll();
	}
	@Override
	public int synItemPricPrio(List<DgItemPricePriority> listItemPricPrio) {
		int relust=0;
		dgItemPricePriorityMapper.delPhy();
		if(null!=listItemPricPrio&&listItemPricPrio.size()>0){
			dgItemPricePriorityMapper.insertBatch(listItemPricPrio);
		}
		relust=1;
		return relust;
	}

}