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
import com.yqsh.diningsys.web.dao.deskBusiness.DgForMealTimePriceMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgForMealTimePriceSMapper;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePriceS;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.deskBusiness.DgForMealTimePriceService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class DgForMealTimePriceServiceImpl extends GenericServiceImpl<DgForMealTimePrice,Integer> implements DgForMealTimePriceService{
	@Resource
	private DgForMealTimePriceMapper dgForMealTimePriceMapper;
	@Autowired
	private DgForMealTimePriceSMapper dgForMealPriceMapper;
	@Autowired
	private TbBisService tbBisService;
	
	@Override
	public GenericDao<DgForMealTimePrice, Integer> getDao() {
		return dgForMealTimePriceMapper;
	}

	@Override
	public List<DgForMealTimePrice> getAllData(DgForMealTimePrice record) {
		return dgForMealTimePriceMapper.getAllData(record);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgForMealTimePriceMapper.deleteIds(ids);
	}
	@Override
	public int deleteAll() {
		return dgForMealTimePriceMapper.deleteAll();
	}
	@Override
	public int insertBackId(DgForMealTimePrice record) {
		return dgForMealTimePriceMapper.insertBackId(record);
	}

	@Override
	public int deleteByItemId(int id) {
		return dgForMealTimePriceMapper.deleteByItemId(id);
	}

	@Override
	@Transactional
	public void syngetForMealTime(List<DgForMealTimePrice> listDfmtp, List<DgForMealTimePriceS> listDfmtps) {
		dgForMealTimePriceMapper.delPhy();
		dgForMealPriceMapper.delPhy();
		if(null!=listDfmtp&&listDfmtp.size()>0){
			dgForMealTimePriceMapper.insertBatch(listDfmtp);
		}
		if(null!=listDfmtps&&listDfmtps.size()>0){
			dgForMealPriceMapper.insertBatch(listDfmtps);
		}
	}

	@Override
	public int saveForMealTimePrice(String data) {
		int relust=0;
		if(org.apache.commons.lang.StringUtils.isNotBlank(data)){
			JSONArray json = JSONArray.fromObject(data);
	    	TbBis bis = new TbBis();
	    	bis.setIsDel("0");
			List<TbBis> items = tbBisService.getAllList(bis);
	    	if(null!=json&&json.size()>0){
	    		for(int i=0;i<json.size();i++){
	    			JSONObject job = json.getJSONObject(i);
	    			DgForMealTimePrice item = new DgForMealTimePrice();
	    			item.setId(Integer.parseInt((String)job.get("itemId")));
	    			item.setItemId(Integer.parseInt((String)job.get("itemId")));
	    			item.setItemCode((String)job.get("itemCode"));
	    			dgForMealTimePriceMapper.deleteByPrimaryKey(item.getId());
	    			dgForMealTimePriceMapper.insertBackId(item);
	    			dgForMealPriceMapper.deleteByItemId(item.getItemId());
	    			for(TbBis d:items){
	    				DgForMealTimePriceS s = new DgForMealTimePriceS();
	    				s.setMealTimeId(d.getId());
	    				s.setMealTimePriceId(item.getId());
	    				s.setItemId(item.getItemId());
		    			if(!StringUtils.isEmpty(job.get("d"+d.getId()))){
		    				s.setPrice(Double.valueOf(((String)job.get("d"+d.getId()))));
		    			}else{
		    				s.setPrice(null);
		    			}
		    			dgForMealPriceMapper.insert(s);
	    			}
	    		}
	    		relust=1;
	    	}
		}
    	return relust;
	}
}