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
import com.yqsh.diningsys.web.dao.deskBusiness.DgPromotionItemMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgPromotionItem;
import com.yqsh.diningsys.web.service.deskBusiness.DgPromotionItemService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class DgPromotionItemServiceImpl extends GenericServiceImpl<DgPromotionItem,Integer> implements DgPromotionItemService{
	@Resource
	private DgPromotionItemMapper dgPromotionItemMapper;
	@Override
	public GenericDao<DgPromotionItem, Integer> getDao() {
		return dgPromotionItemMapper;
	}
	@Override
	public List<DgPromotionItem> getAllData(DgPromotionItem record) {
		return dgPromotionItemMapper.getAllData(record);
	}
	@Override
	public List<Integer> getAllItemId() {
		return dgPromotionItemMapper.getAllItemId();
	}
	@Override
	public List<DgItemFile> selectItemByAdd(List<Integer> ids) {
		return dgPromotionItemMapper.selectItemByAdd(ids);
	}
	@Override
	public int deleteAll() {
		return dgPromotionItemMapper.deleteAll();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgPromotionItemMapper.deleteIds(ids);
	}
	@Override
	public void deleteNotIn(List<Integer> ids) {
		dgPromotionItemMapper.deleteNotIn(ids);
	}
	@Override
	public int deleteByItemId(int id) {
		return dgPromotionItemMapper.deleteByItemId(id);
	}
	@Override
	public DgPromotionItem selectByItemId(Integer id) {
		return dgPromotionItemMapper.selectByItemId(id);
	}
	@Override
	public int insertChilds(List<DgPromotionItem> record) {
		return dgPromotionItemMapper.insertChilds(record);
	}
	@Override
	@Transactional
	public int savePromItem(String data) {
		int relust=0;
		if(StringUtils.isNotBlank(data)){
			JSONArray json = JSONArray.fromObject(data);
	    	if(json.size()>0){
	    		for(int i=0;i<json.size();i++){
	    			JSONObject job = json.getJSONObject(i);
	    			DgPromotionItem item = new DgPromotionItem();
//	    			item.setId(Integer.parseInt((String)job.get("itemId")));
	    			item.setItemId(Integer.parseInt((String)job.get("itemId")));
	    			item.setItemCode((String)job.get("itemCode"));
	    			if(!StringUtils.isEmpty((String)job.get("proPrice"))){
	    				item.setProPrice(Double.valueOf((String)job.get("proPrice")));
	    			}else{
	    				item.setProPrice(null);
	    			}
	    			if(!StringUtils.isEmpty((String)job.get("maxCount"))){
	    				item.setMaxCount(Integer.parseInt((String)job.get("maxCount")));
	    			}else{
	    				item.setMaxCount(0);
	    			}
	    			dgPromotionItemMapper.deleteByItemId(item.getItemId());
	    			dgPromotionItemMapper.insert(item);
	    		}
	    		relust=1;
	    	}
		}
		return relust;
	
	}
	@Override
	@Transactional
	public void synPromItem(List<DgPromotionItem> listPromItem) {
		dgPromotionItemMapper.delPhy();
		if(listPromItem!=null&&listPromItem.size()>0){
			dgPromotionItemMapper.insertBatch(listPromItem);
		}
	}

}
