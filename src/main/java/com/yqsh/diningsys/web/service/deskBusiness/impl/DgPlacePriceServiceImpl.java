package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgPlacePriceMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgPlacePriceSMapper;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.deskBusiness.DgPlacePriceService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class DgPlacePriceServiceImpl extends GenericServiceImpl<DgPlacePrice,Integer> implements DgPlacePriceService{
	@Resource
	private DgPlacePriceMapper dgPlacePriceMapper;
	@Autowired
	private DgPlacePriceSMapper dgPlacePriceSMapper;
	@Autowired
	private DgConsumptionAreaService dgConsumptionAreaService;
	
	
	@Override
	public GenericDao<DgPlacePrice, Integer> getDao() {
		return dgPlacePriceMapper;
	}
	@Override
	public List<DgPlacePrice> getAllData(DgPlacePrice record) {
		return dgPlacePriceMapper.getAllData(record);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgPlacePriceMapper.deleteIds(ids);
	}
	@Override
	public int deleteAll() {
		return dgPlacePriceMapper.deleteAll();
	}
	@Override
	public int insertBackId(DgPlacePrice record) {
		return dgPlacePriceMapper.insertBackId(record);
	}
	@Override
	public int deleteByItemId(int id) {
		return dgPlacePriceMapper.deleteByItemId(id);
	}
	@Override
	public int insertChilds(List<DgPlacePrice> record) {
		return dgPlacePriceMapper.insertChilds(record);
	}
	@Override
	@Transactional
	public void synPlacePrice(List<DgPlacePrice> listPlacPric, List<DgPlacePriceS> listPlacPrics) {
		dgPlacePriceMapper.delPhy();
		dgPlacePriceSMapper.delPhy();
		if(null!=listPlacPric&&listPlacPric.size()>0){
			dgPlacePriceMapper.insertBatch(listPlacPric);
		}
		if(null!=listPlacPrics&&listPlacPrics.size()>0){
			dgPlacePriceSMapper.insertBatch(listPlacPrics);
		}
	}
	@Override
	@Transactional
	public int savePlacePrice(String data) {
		int relust=0;
		if(org.apache.commons.lang.StringUtils.isNotBlank(data)){
			JSONArray json = JSONArray.fromObject(data);
			List<DgConsumptionArea> items = dgConsumptionAreaService.getAllList(null);
	    	if(json.size()>0){
	    		for(int i=0;i<json.size();i++){
	    			JSONObject job = json.getJSONObject(i);
	    			DgPlacePrice item = new DgPlacePrice();
	    			item.setItemId(Integer.parseInt((String)job.get("itemId")));
	    			item.setItemCode((String)job.get("itemCode"));
	    			dgPlacePriceMapper.deleteByItemId(item.getItemId());
	    			dgPlacePriceMapper.insertBackId(item);
	    			dgPlacePriceSMapper.deleteByItemId(item.getItemId());
	    			for(DgConsumptionArea d:items){
	    				DgPlacePriceS s = new DgPlacePriceS();
	    				s.setPlacePriceId(item.getId());
	    				s.setPlaceId(d.getId());
	    				s.setItemId(item.getItemId());
		    			if(!StringUtils.isEmpty(job.get("d"+d.getId()))){
		    				s.setPrice(Double.valueOf(((String)job.get("d"+d.getId()))));
		    			}else{
		    				s.setPrice(null);
		    			}
		    			dgPlacePriceSMapper.insert(s);
	    			}
	    		}
	    		relust=1;
	    	}
		}
		return relust;
	}

}
