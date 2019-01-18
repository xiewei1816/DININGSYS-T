package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitSMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemSaleLimitSService;
@Service
public class DgItemSaleLimitSServiceImpl extends GenericServiceImpl<DgItemSaleLimitS,Integer> implements DgItemSaleLimitSService{
	@Resource
	private DgItemSaleLimitSMapper dgItemSaleLimitSMapper;
	@Override
	public GenericDao<DgItemSaleLimitS, Integer> getDao() {
		return dgItemSaleLimitSMapper;
	}
	@Override
	public List<DgItemSaleLimitS> getAllData(Map<String,Object> record) {
		return dgItemSaleLimitSMapper.getAllData(record);
	}
	@Override
	public List<Integer> getAllItemId() {
		return dgItemSaleLimitSMapper.getAllItemId();
	}
	@Override
	public List<DgItemSaleLimitS> selectItemByAdd(List<Integer> ids) {
		return dgItemSaleLimitSMapper.selectItemByAdd(ids);
	}
	@Override
	public int deleteAll(Integer id) {
		return dgItemSaleLimitSMapper.deleteAll(id);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemSaleLimitSMapper.deleteIds(ids);
	}
	@Override
	public void deleteNotIn(List<Integer> ids) {
		dgItemSaleLimitSMapper.deleteNotIn(ids);
	}
	@Override
	public int deleteByItemId(int id) {
		return dgItemSaleLimitSMapper.deleteByItemId(id);
	}
	@Override
	public int updateCount(DgItemSaleLimitS useCount) {
		return dgItemSaleLimitSMapper.updateCount(useCount);
	}
	@Override
	public int insertChilds(List<DgItemSaleLimitS> record) {
		return dgItemSaleLimitSMapper.insertChilds(record);
	}
	@Override
	public int deleteParams(Integer itemId, Integer limitId) {
		return dgItemSaleLimitSMapper.deleteParams(itemId, limitId);
	}

}