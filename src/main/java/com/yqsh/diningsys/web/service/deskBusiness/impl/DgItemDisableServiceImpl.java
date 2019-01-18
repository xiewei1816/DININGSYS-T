package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemDisableMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDisable;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDisableService;
import com.yqsh.diningsys.web.service.deskBusiness.DgJointDeleteService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class DgItemDisableServiceImpl extends GenericServiceImpl<DgItemDisable,Integer> implements DgItemDisableService{
	@Resource
	private DgItemDisableMapper dgItemDisableMapper;
	@Autowired
	private DgJointDeleteService dgJointDeleteService;
	
	
	@Override
	public GenericDao<DgItemDisable, Integer> getDao() {
		return dgItemDisableMapper;
	}
	@Override
	public List<DgItemDisable> getAllData(DgItemDisable record) {
		return dgItemDisableMapper.getAllData(record);
	}
	@Override
	public List<Integer> getAllItemId() {
		return dgItemDisableMapper.getAllItemId();
	}
	@Override
	public List<DgItemDisable> selectItemByAdd(List<Integer> ids) {
		return dgItemDisableMapper.selectItemByAdd(ids);
	}
	@Override
	public int deleteAll() {
		return dgItemDisableMapper.deleteAll();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemDisableMapper.deleteIds(ids);
	}
	@Override
	public void deleteNotIn(List<Integer> ids) {
		dgItemDisableMapper.deleteNotIn(ids);
	}
	@Override
	public DgItemDisable seleByItemId(Integer id) {
		return dgItemDisableMapper.seleByItemId(id);
	}
	@Override
	public int insertChilds(List<DgItemDisable> record) {
		return dgItemDisableMapper.insertChilds(record);
	}
	@Override
	@Transactional
	public int saveItemDisable(String data) {
		int relust=0;
		if(StringUtils.isNotBlank(data)){
			JSONArray json = JSONArray.fromObject(data);
	    	if(json.size()>0){
	    		List<DgItemDisable> list = new ArrayList<DgItemDisable>();
	    		for(int i=0;i<json.size();i++){
	    			JSONObject job = json.getJSONObject(i);
	    			DgItemDisable item = new DgItemDisable();
	    			item.setItemId(Integer.parseInt((String)job.get("itemId")));
	    			item.setItemCode((String)job.get("itemCode"));
	    			item.setUuidKey(UUID.randomUUID().toString());
	    			list.add(item);
	    			dgJointDeleteService.deleteJointItem(Integer.parseInt((String)job.get("itemId")));
	    			dgItemDisableMapper.deleteByItemKey(item.getItemId());
	    		}
	    		dgItemDisableMapper.insertChilds(list);
				relust=1;
	    	}
		}
		return relust;
	}
	@Override
	public int deleteByItemKey(Integer itemId) {
		return dgItemDisableMapper.deleteByItemKey(itemId);
	}
	@Override
	@Transactional
	public void synItemDisable(List<DgItemDisable> listItemDisable) {
		dgItemDisableMapper.delPhy();
		if(null!=listItemDisable&&listItemDisable.size()>0){
			dgItemDisableMapper.insertBatch(listItemDisable);
		}
	}

}