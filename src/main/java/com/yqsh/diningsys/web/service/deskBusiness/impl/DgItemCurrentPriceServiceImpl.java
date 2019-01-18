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
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemCurrentPriceMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCurrentPrice;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemCurrentPriceService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class DgItemCurrentPriceServiceImpl extends GenericServiceImpl<DgItemCurrentPrice,Integer> implements DgItemCurrentPriceService{
	@Resource
	private DgItemCurrentPriceMapper dgItemCurrentPriceMapper;
	@Override
	public List<DgItemCurrentPrice> getAllData(DgItemCurrentPrice record) {
		return dgItemCurrentPriceMapper.getAllData(record);
	}

	@Override
	public List<Integer> getAllItemId() {
		return dgItemCurrentPriceMapper.getAllItemId();
	}

	@Override
	public List<DgItemFile> selectItemByAdd(List<Integer> ids) {
		return dgItemCurrentPriceMapper.selectItemByAdd(ids);
	}

	@Override
	public int deleteAll() {
		return dgItemCurrentPriceMapper.deleteAll();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemCurrentPriceMapper.deleteIds(ids);
	}

	@Override
	public void deleteNotIn(List<Integer> ids) {
		dgItemCurrentPriceMapper.deleteNotIn(ids);
	}

	@Override
	public GenericDao<DgItemCurrentPrice, Integer> getDao() {
		return dgItemCurrentPriceMapper;
	}

	@Override
	public int deleteByItemId(int id) {
		return dgItemCurrentPriceMapper.deleteByItemId(id);
	}

	@Override
	public DgItemCurrentPrice selectByItemId(Integer id) {
		return dgItemCurrentPriceMapper.selectByItemId(id);
	}

	@Override
	public int insertChilds(List<DgItemCurrentPrice> record) {
		return dgItemCurrentPriceMapper.insertChilds(record);
	}

	@Override
	@Transactional
	public int saveItemCurrPric(String data) {
		int relust=0;
		if(org.apache.commons.lang.StringUtils.isNotBlank(data)){
			JSONArray json = JSONArray.fromObject(data);
	    	if(json.size()>0){
	    		List<DgItemCurrentPrice> list = new ArrayList<DgItemCurrentPrice>();
	    		for(int i=0;i<json.size();i++){
	    			JSONObject job = json.getJSONObject(i);
	    			DgItemCurrentPrice item = new DgItemCurrentPrice();
	    			//item.setId(Integer.parseInt((String)job.get("itemId")));
	    			item.setItemId(Integer.parseInt((String)job.get("itemId")));
	    			item.setItemCode((String)job.get("itemCode"));
	    			
	    			if(!StringUtils.isEmpty(job.get("currentPrice"))){
	    				item.setCurrentPrice(Double.valueOf((String)job.get("currentPrice")));
	    			}else{
	    				item.setCurrentPrice(null);
	    			}
	    			list.add(item);
	    			dgItemCurrentPriceMapper.deleteByItemId(item.getItemId());
	    		}
	    		dgItemCurrentPriceMapper.insertChilds(list);
	    		relust=1;
	    	}
		}
		return relust;
	}

	@Override
	@Transactional
	public void synItemCurrentPrice(List<DgItemCurrentPrice> listItemCurrPrice) {
		dgItemCurrentPriceMapper.delPhy();
		if(listItemCurrPrice!=null&&listItemCurrPrice.size()>0){
			dgItemCurrentPriceMapper.insertBatch(listItemCurrPrice);
		}
	}

}
