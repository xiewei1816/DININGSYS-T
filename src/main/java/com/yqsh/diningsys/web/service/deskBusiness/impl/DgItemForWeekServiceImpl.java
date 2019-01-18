package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemForWeekMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemForWeek;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemForWeekService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class DgItemForWeekServiceImpl extends GenericServiceImpl<DgItemForWeek,Integer> implements DgItemForWeekService{
	@Resource
	private DgItemForWeekMapper dgItemForWeekMapper;
	
	@Override
	public List<DgItemForWeek> getAllData(DgItemForWeek record) {
		return dgItemForWeekMapper.getAllData(record);
	}

	@Override
	public List<Integer> getAllItemId() {
		return dgItemForWeekMapper.getAllItemId();
	}

	@Override
	public List<DgItemFile> selectItemByAdd(List<Integer> ids) {
		return dgItemForWeekMapper.selectItemByAdd(ids);
	}

	@Override
	public int deleteAll() {
		return dgItemForWeekMapper.deleteAll();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemForWeekMapper.deleteIds(ids);
	}

	@Override
	public void deleteNotIn(List<Integer> ids) {
		dgItemForWeekMapper.deleteNotIn(ids);
	}

	@Override
	public GenericDao<DgItemForWeek, Integer> getDao() {
		return dgItemForWeekMapper;
	}

	@Override
	public int deleteByItemId(int id) {
		return dgItemForWeekMapper.deleteByItemId(id);
	}

	@Override
	public DgItemForWeek selectByItemId(Integer id) {
		return dgItemForWeekMapper.selectByItemId(id);
	}

	@Override
	public int insertChilds(List<DgItemForWeek> record) {
		return dgItemForWeekMapper.insertChilds(record);
	}

	@Override
	@Transactional
	public int saveItemForWeek(String data) {
		int relust=0;
		if(StringUtils.isNotBlank(data)){
			JSONArray json = JSONArray.fromObject(data);
	    	if(json!=null&&json.size()>0){
	    		for(int i=0;i<json.size();i++){
	    			JSONObject job = json.getJSONObject(i);
	    			DgItemForWeek item = new DgItemForWeek();
	    			item.setItemId(Integer.parseInt((String)job.get("itemId")));
	    			item.setItemCode((String)job.get("itemCode"));
	    			if(StringUtils.isEmpty((String)job.get("x1"))){
	    				item.setX1(null);
	    			}else{
	        			item.setX1(Double.parseDouble((String)job.get("x1")));	
	    			}
	    			if(StringUtils.isEmpty((String)job.get("x2"))){
	    				item.setX2(null);
	    			}else{
	        			item.setX2(Double.parseDouble((String)job.get("x2")));	
	    			}if(StringUtils.isEmpty((String)job.get("x3"))){
	    				item.setX3(null);
	    			}else{
	        			item.setX3(Double.parseDouble((String)job.get("x3")));	
	    			}
	    			if(StringUtils.isEmpty((String)job.get("x4"))){
	    				item.setX4(null);
	    			}else{
	        			item.setX4(Double.parseDouble((String)job.get("x4")));	
	    			}
	    			if(StringUtils.isEmpty((String)job.get("x5"))){
	    				item.setX5(null);
	    			}else{
	        			item.setX5(Double.parseDouble((String)job.get("x5")));	
	    			}
	    			if(StringUtils.isEmpty((String)job.get("x6"))){
	    				item.setX6(null);
	    			}else{
	        			item.setX6(Double.parseDouble((String)job.get("x6")));	
	    			}
	    			if(StringUtils.isEmpty((String)job.get("x7"))){
	    				item.setX7(null);
	    			}else{
	        			item.setX7(Double.parseDouble((String)job.get("x7")));	
	    			}
	    			dgItemForWeekMapper.deleteByItemId(item.getItemId());
	    			dgItemForWeekMapper.insert(item);
	    		}
	    		relust=1;
	    	}
		}
		return relust;
	}

	@Override
	@Transactional
	public void syngetItemForWeek(List<DgItemForWeek> listItemForWeek) {
		dgItemForWeekMapper.delPhy();
		if(listItemForWeek!=null&&listItemForWeek.size()>0){
			dgItemForWeekMapper.insertBatch(listItemForWeek);
		}
	}

}