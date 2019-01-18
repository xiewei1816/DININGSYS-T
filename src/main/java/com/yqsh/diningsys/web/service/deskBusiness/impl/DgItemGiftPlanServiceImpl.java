package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemGiftPlanMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemGiftPlanSMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlan;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlanS;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemGiftPlanService;
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DgItemGiftPlanServiceImpl extends GenericServiceImpl<DgItemGiftPlan,Integer> implements DgItemGiftPlanService{
	@Resource
	private DgItemGiftPlanMapper dgItemGiftPlanMapper;
	@Autowired
	private DgItemGiftPlanSMapper dgItemGiftPlansMapper;
	
	@Override
	public GenericDao<DgItemGiftPlan, Integer> getDao() {
		return dgItemGiftPlanMapper;
	}
	@Override
	public int insertBackId(DgItemGiftPlan record) {
		return dgItemGiftPlanMapper.insertBackId(record);
	}
	@Override
	public Page<DgItemGiftPlan> getPageGiftPlan(
			DgItemGiftPlan dgItemGiftPlan) {
		Integer totle = dgItemGiftPlanMapper.countAllData(dgItemGiftPlan);
	    List<DgItemGiftPlan> list = dgItemGiftPlanMapper.getAllData(dgItemGiftPlan);
	    return PageUtil.getPage(totle, dgItemGiftPlan.getPage(),list, dgItemGiftPlan.getRows());
	}
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemGiftPlanMapper.deleteIds(ids);
	}
	@Override
	public DgItemGiftPlan getDataByPrimaryKey(Integer id) {
		return dgItemGiftPlanMapper.getDataByPrimaryKey(id);
	}
	@Override
	public int deleteByItemId(int id) {
		return dgItemGiftPlanMapper.deleteByItemId(id);
	}
	@Override
	public List<DgItemGiftPlan> selectAll() {
		return dgItemGiftPlanMapper.selectAll();
	}
	@Override
	public void trash(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemGiftPlanMapper.trash(ids);
	}
	@Override
	public void restore(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemGiftPlanMapper.restore(ids);
	}
	@Override
	public List<Map<String, Object>> reminder() {
		return dgItemGiftPlanMapper.reminder();
	}
	@Override
	public int seleNameCode(DgItemGiftPlan src) {
		return dgItemGiftPlanMapper.seleNameCode(src);
	}
	@Override
	@Transactional
	public void synItemGiftPlan(List<DgItemGiftPlan> listGiftPan, List<DgItemGiftPlanS> listGiftPans) {
		dgItemGiftPlanMapper.delPhy();
		dgItemGiftPlansMapper.delPhy();
		if(null!=listGiftPan&&listGiftPan.size()>0){
			dgItemGiftPlanMapper.insertBatch(listGiftPan);
		}
		if(null!=listGiftPans&&listGiftPans.size()>0){
			dgItemGiftPlansMapper.insertBatch(listGiftPans);
		}
		
	}
}