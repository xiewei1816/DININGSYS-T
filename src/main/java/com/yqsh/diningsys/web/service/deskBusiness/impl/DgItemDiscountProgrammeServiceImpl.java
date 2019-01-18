package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemDiscountProgrammeMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemDiscountProgrammeSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemForWeekMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgWeekDiscountMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantActivityDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemForWeek;
import com.yqsh.diningsys.web.model.deskBusiness.DgWeekDiscount;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountProgrammeService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountService;
@Service
public class DgItemDiscountProgrammeServiceImpl extends GenericServiceImpl<DgItemDiscountProgramme,Integer> implements DgItemDiscountProgrammeService{
	@Resource
	private DgItemDiscountProgrammeMapper dgItemDiscountProgrammeMapper;
	@Autowired
	private DgWeekDiscountMapper dgWeekDiscountMapper;
	@Autowired
	private DgItemDiscountProgrammeSMapper dgItemDiscProsMapper;
	
	
	@Override
	public GenericDao<DgItemDiscountProgramme, Integer> getDao() {
		return dgItemDiscountProgrammeMapper;
	}

	@Override
	public List<DgItemFile> selectAllItemFile(String s,String disable,String yxdz) {
		Map org = new HashMap();
	    List ids = new ArrayList();
        Collections.addAll(ids,s.split(","));
	    org.put("ids",ids);
	    org.put("disable",disable);
	    org.put("yxdz",yxdz);
        return dgItemDiscountProgrammeMapper.selectAllItemFile(org);
	}

	@Override
	public List<DgItemFile> selectSmallItemFile(String s, int ppxl_id,String disable,String yxdz) {
		
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	        
		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("ppxl_id", ppxl_id);
		res.put("ids",ids);
		res.put("disable",disable);
		res.put("yxdz",yxdz);
		return dgItemDiscountProgrammeMapper.selectSmallItemFile(res);
	}

	@Override
	public List<DgItemFile> selectBigItemFile(String s, int ppdl_id,String disable,String yxdz) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("ppdl_id", ppdl_id);
		res.put("ids",ids);
		res.put("disable",disable);
		res.put("yxdz",yxdz);
		return dgItemDiscountProgrammeMapper.selectBigItemFile(res);
	}

	@Override
	public List<DgItemDiscountProgramme> selectAllProgrammes() {
		// TODO Auto-generated method stub
		return dgItemDiscountProgrammeMapper.selectAllProgrammes();
	}

	@Override
	public int insertBackId(DgItemDiscountProgramme record) {
		// TODO Auto-generated method stub
		return dgItemDiscountProgrammeMapper.insertBackId(record);
	}

	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemDiscountProgrammeMapper.deleteIds(ids);
	}

	@Override
	public List<DgItemFile> selectHaveItem(String s,String disable,String yxdz) {
		Map orgs = new HashMap();
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    orgs.put("list",ids);
	    orgs.put("disable",disable);
	    orgs.put("yxdz",yxdz);
		return dgItemDiscountProgrammeMapper.selectHaveItem(orgs);
	}

	@Override
	public List<DgItemFileType> selectAllItemFileType(String s) {
	    List ids = new ArrayList();
        Collections.addAll(ids,s.split(","));
        return dgItemDiscountProgrammeMapper.selectAllItemFileType(ids);
	}

	@Override
	public List<DgItemFileType> selectSmallItemFileType(String s,
			int parent_id) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	        
		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("parent_id", parent_id);
		res.put("ids",ids);
		return dgItemDiscountProgrammeMapper.selectSmallItemFileType(res);
	}


	@Override
	public List<DgItemFileType> selectHaveItemType(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
		return dgItemDiscountProgrammeMapper.selectHaveItemType(ids);
	}

	@Override
	public List<DgItemFile> selectAllDgItemFile() {
		return dgItemDiscountProgrammeMapper.selectAllDgItemFile();
	}

	@Override
	public List<DgItemFile> selectSmallDgItemFile(Integer id) {
		return dgItemDiscountProgrammeMapper.selectSmallDgItemFile(id);
	}

	@Override
	public List<DgItemFile> selectBigDgItemFile(Integer id) {
		return dgItemDiscountProgrammeMapper.selectBigDgItemFile(id);
	}

	@Override
	public List<DgItemFile> searchVague(String id,String search,String s,String disable,String yxdz) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    HashMap<String,Object> ret = new HashMap<String, Object>();
		ret.put("search",search);
		ret.put("ids",ids);
		ret.put("pxPid" ,id);
		ret.put("disable",disable);
		ret.put("yxdz",yxdz);
		return dgItemDiscountProgrammeMapper.searchVague(ret);
	}

	@Override
	public List<DgItemFile> search(String s) {
		return dgItemDiscountProgrammeMapper.search(s);
	}

	@Override
	public void trash(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
		dgItemDiscountProgrammeMapper.trash(ids);
	}

	@Override
	public void restore(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemDiscountProgrammeMapper.restore(ids);
	}

	@Override
	public Page<DgItemDiscountProgramme> getPage(
			DgItemDiscountProgramme dgItemDiscountProgramme) {
	    Integer totle = dgItemDiscountProgrammeMapper.countAllData(dgItemDiscountProgramme);
        List<DgItemDiscountProgramme> list = dgItemDiscountProgrammeMapper.getAllData(dgItemDiscountProgramme);
        return PageUtil.getPage(totle, dgItemDiscountProgramme.getPage(),list, dgItemDiscountProgramme.getRows());
	}

	@Override
	public List<Map<String, Object>> reminder() {
		return dgItemDiscountProgrammeMapper.reminder();
	}

	@Override
	public int seleNameCode(DgItemDiscountProgramme src) {
		// TODO Auto-generated method stub
		return dgItemDiscountProgrammeMapper.seleNameCode(src);
	}

	@Override
	@Transactional
	public void synItemDiscPro(List<DgItemDiscountProgramme> listItemDiscPro,
			List<DgItemDiscountProgrammeS> listItemDiscPros, List<DgWeekDiscount> listWeekDisc) {
		dgItemDiscountProgrammeMapper.delPhy();
		dgItemDiscProsMapper.delPhy();
		dgWeekDiscountMapper.delPhy();
		if(null!=listItemDiscPro&&listItemDiscPro.size()>0){
			dgItemDiscountProgrammeMapper.insertBatch(listItemDiscPro);
		}
		if(null!=listItemDiscPros&&listItemDiscPros.size()>0){
			dgItemDiscProsMapper.insertBatch(listItemDiscPros);
		}
		if(null!=listWeekDisc&&listWeekDisc.size()>0){
			dgWeekDiscountMapper.insertBatch(listWeekDisc);
		}
	}
}