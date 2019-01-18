package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemDiscountProgrammeSMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountProgrammeSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountProgrammeService;
@Service
public class DgItemDiscountProgrammeSServiceImpl extends GenericServiceImpl<DgItemDiscountProgrammeS,Integer> implements DgItemDiscountProgrammeSService{

	@Resource
	private DgItemDiscountProgrammeSMapper dgItemDiscountProgrammeSMapper;
	@Override
	public GenericDao<DgItemDiscountProgrammeS, Integer> getDao() {
		
		return dgItemDiscountProgrammeSMapper;
	}
	@Override
	public List<DgItemDiscountProgrammeS> selectItemByPid(Integer id) {
		// TODO Auto-generated method stub
		return dgItemDiscountProgrammeSMapper.selectItemByPid(id);
	}
	@Override
	public List<DgItemFile> selectItemByAdd(String id) {
		List ids = new ArrayList();
	    Collections.addAll(ids,id.split(","));
		return dgItemDiscountProgrammeSMapper.selectItemByAdd(ids);
	}
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemDiscountProgrammeSMapper.deleteIds(ids);
	}
	@Override
	public List<DgItemFileType> selectItemTypeByAdd(String id) {
		List ids = new ArrayList();
	    Collections.addAll(ids,id.split(","));
		return dgItemDiscountProgrammeSMapper.selectItemTypeByAdd(ids);
	}
	@Override
	public List<DgItemDiscountProgrammeS> selectItemTypeByPid(Integer id) {
		// TODO Auto-generated method stub
		return dgItemDiscountProgrammeSMapper.selectItemTypeByPid(id);
	}
	@Override
	public int deleteByItemId(Map<String, Object> id) {
		// TODO Auto-generated method stub
		return dgItemDiscountProgrammeSMapper.deleteByItemId(id);
	}
//	@Override
//	public Map<String, Object> selectByPIdAndItemIdType1(Integer src) {
//		// TODO Auto-generated method stub
//		return dgItemDiscountProgrammeSMapper.selectByPIdAndItemIdType1(src);
//	}
//	@Override
//	public Map<String, Object> selectByPIdAndItemIdType2(Integer src) {
//		// TODO Auto-generated method stub
//		return dgItemDiscountProgrammeSMapper.selectByPIdAndItemIdType2(src);
//	}
	@Override
	public void insertChilds(List<DgItemDiscountProgrammeS> lists) {
		// TODO Auto-generated method stub
		dgItemDiscountProgrammeSMapper.insertChilds(lists);
	}

}