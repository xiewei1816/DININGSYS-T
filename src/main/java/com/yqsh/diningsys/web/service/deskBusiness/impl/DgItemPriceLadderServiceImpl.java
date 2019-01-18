package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemPriceLadderMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPriceLadder;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemPriceLadderService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class DgItemPriceLadderServiceImpl extends GenericServiceImpl<DgItemPriceLadder,Integer> implements DgItemPriceLadderService{
	@Resource
	private DgItemPriceLadderMapper dgItemPriceLadderMapper;
	@Override
	public GenericDao<DgItemPriceLadder, Integer> getDao() {
		return dgItemPriceLadderMapper;
	}
	@Override
	public List<DgItemPriceLadder> getAllData(DgItemPriceLadder record) {
		return dgItemPriceLadderMapper.getAllData(record);
	}
	@Override
	public List<Integer> getAllItemId() {
		return dgItemPriceLadderMapper.getAllItemId();
	}
	@Override
	public List<DgItemFile> selectItemByAdd(List<Integer> ids) {
		return dgItemPriceLadderMapper.selectItemByAdd(ids);
	}
	@Override
	public int deleteAll() {
		return dgItemPriceLadderMapper.deleteAll();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemPriceLadderMapper.deleteIds(ids);
	}
	@Override
	public void deleteNotIn(List<Integer> ids) {
		dgItemPriceLadderMapper.deleteNotIn(ids);
	}
	@Override
	public int deleteByItemId(int id) {
		return dgItemPriceLadderMapper.deleteByItemId(id);
	}
	@Override
	public DgItemPriceLadder selectByItemId(Integer id) {
		return dgItemPriceLadderMapper.selectByItemId(id);
	}
	@Override
	public int insertChilds(List<DgItemPriceLadder> record) {
		return dgItemPriceLadderMapper.insertChilds(record);
	}
	@Override
	@Transactional
	public int saveItemPriceLadder(String data) {
		int relsut=0;
		if(org.apache.commons.lang.StringUtils.isNotBlank(data)){
			JSONArray json = JSONArray.fromObject(data);
	    	if(json.size()>0){
	    		List<DgItemPriceLadder> list = new ArrayList<DgItemPriceLadder>();
	    		for(int i=0;i<json.size();i++){
	    			JSONObject job = json.getJSONObject(i);
	    			DgItemPriceLadder item = new DgItemPriceLadder();
	    			item.setItemId(Integer.parseInt((String)job.get("itemId")));
	    			item.setItemCode((String)job.get("itemCode"));
	    			if(StringUtils.isEmpty(job.get("ladder1"))){
	    				item.setLadder1(null);
	    			}else{
	        			item.setLadder1(Double.parseDouble((String)job.get("ladder1")));	
	    			}
	    			if(StringUtils.isEmpty(job.get("ladder2"))){
	    				item.setLadder2(null);
	    			}else{
	        			item.setLadder2(Double.parseDouble((String)job.get("ladder2")));	
	    			}
	    			list.add(item);
	    			dgItemPriceLadderMapper.deleteByItemId(item.getItemId());
	    		}
	    		dgItemPriceLadderMapper.insertChilds(list);
	    		relsut=1;
	    	}
		}
    	return relsut;
	}
	@Override
	public void synItemPriceLadder(List<DgItemPriceLadder> listItemPriceLadder) {
		dgItemPriceLadderMapper.delPhy();
		if(null!=listItemPriceLadder&&listItemPriceLadder.size()>0){
			dgItemPriceLadderMapper.insertBatch(listItemPriceLadder);
		}
	}
}