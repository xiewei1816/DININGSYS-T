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
import com.yqsh.diningsys.web.dao.deskBusiness.DgRecommendItemMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgRecommendItem;
import com.yqsh.diningsys.web.service.deskBusiness.DgRecommendItemService;
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DgRecommendItemServiceImpl extends GenericServiceImpl<DgRecommendItem,Integer> implements DgRecommendItemService{
	@Resource
	private DgRecommendItemMapper dgRecommendItemMapper;
	@Override
	public GenericDao<DgRecommendItem, Integer> getDao() {
		return dgRecommendItemMapper;
	}
	@Override
	public List<DgRecommendItem> selectAll(DgRecommendItem record) {
		return dgRecommendItemMapper.selectAll(record);
	}
	@Override
	public List<DgRecommendItem> selectItemByAdd(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
		return dgRecommendItemMapper.selectItemByAdd(ids);
	}
	@Override
	@Transactional
	public int insertMore(String s) {
		int relust=0;
		if(StringUtils.isNotBlank(s)){
			List<DgRecommendItem> ids = new ArrayList<DgRecommendItem>();
			String[] item_ids = s.split(",");
			for(int i=0;i<item_ids.length;i++){
				DgRecommendItem item = new DgRecommendItem();
				item.setItemId(Integer.parseInt(item_ids[i]));
				ids.add(item);
				dgRecommendItemMapper.deleteByItemId(item.getItemId());
			}
			relust=dgRecommendItemMapper.insertMore(ids);
		}
		return relust;
	}
	
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
		dgRecommendItemMapper.deleteIds(ids);
		
	}
	@Override
	public void deleteAll() {
		dgRecommendItemMapper.deleteAll();
	}
	@Override
	public int deleteByItemId(int id) {
		return dgRecommendItemMapper.deleteByItemId(id);
	}
	@Override
	@Transactional
	public void synRecommendItem(List<DgRecommendItem> listRecommendItem) {
		dgRecommendItemMapper.delPhy();
		if(null!=listRecommendItem&&listRecommendItem.size()>0){
			dgRecommendItemMapper.insertBatch(listRecommendItem);
		}
	}

}
