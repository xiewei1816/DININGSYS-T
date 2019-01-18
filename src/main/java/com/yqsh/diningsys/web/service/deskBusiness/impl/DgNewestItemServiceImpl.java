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
import com.yqsh.diningsys.web.dao.deskBusiness.DgNewestItemMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgNewestItem;
import com.yqsh.diningsys.web.service.deskBusiness.DgNewestItemService;
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DgNewestItemServiceImpl extends GenericServiceImpl<DgNewestItem,Integer> implements DgNewestItemService{
	@Resource
	private DgNewestItemMapper dgNewestItemMapper;
	@Override
	public GenericDao<DgNewestItem, Integer> getDao() {
		return dgNewestItemMapper;
	}
	@Override
	public List<DgNewestItem> selectAll(DgNewestItem record) {
		return dgNewestItemMapper.selectAll(record);
	}
	
	@Override
	public List<DgNewestItem> selectItemByAdd(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
		return dgNewestItemMapper.selectItemByAdd(ids);
	}
	@Override
	public int insertMore(String s) {
		int relust=0;
		if(StringUtils.isNotBlank(s)){
			List<DgNewestItem> ids = new ArrayList();
			String[] item_ids = s.split(",");
			for(int i=0;i<item_ids.length;i++){
				DgNewestItem item = new DgNewestItem();
				item.setItemId(Integer.parseInt(item_ids[i]));
				ids.add(item);
				dgNewestItemMapper.deleteByItemId(item.getItemId());
			}
			relust=dgNewestItemMapper.insertMore(ids);
		}
		return relust;
	}
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgNewestItemMapper.deleteIds(ids);
		
	}
	@Override
	public void deleteAll() {
		dgNewestItemMapper.deleteAll();
	}
	@Override
	public int deleteByItemId(int id) {
		return dgNewestItemMapper.deleteByItemId(id);
	}
	@Override
	@Transactional
	public void synNewestItem(List<DgNewestItem> listNewestItem) {
		dgNewestItemMapper.delPhy();
		if(null!=listNewestItem&&listNewestItem.size()>0){
			dgNewestItemMapper.insertBatch(listNewestItem);
		}
	}
}
