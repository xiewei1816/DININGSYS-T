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
import com.yqsh.diningsys.web.dao.deskBusiness.DgImportantAcitivityDiscountSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgImportantActivityDiscountMapper;
import com.yqsh.diningsys.web.model.archive.DgGiftForm;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantAcitivityDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantActivityDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.service.deskBusiness.DgImportantActivityDiscountService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountProgrammeService;
@Service
public class DgImportantActivityDiscountServiceImpl extends GenericServiceImpl<DgImportantActivityDiscount,Integer> implements  DgImportantActivityDiscountService{
	@Resource
	private DgImportantActivityDiscountMapper dgImportantActivityDiscountMapper;
	@Autowired
	private DgImportantAcitivityDiscountSMapper dgImporActiDiscsMapper;
	
	
	@Override
	public GenericDao<DgImportantActivityDiscount, Integer> getDao() {
		return dgImportantActivityDiscountMapper;
	}

	@Override
	public Page<DgImportantActivityDiscount> getAllImportantActivity(
		DgImportantActivityDiscount dgImportantActivityDiscount) {
	    Integer totle = dgImportantActivityDiscountMapper.countAllData(dgImportantActivityDiscount);
        List<DgImportantActivityDiscount> list = dgImportantActivityDiscountMapper.getAllData(dgImportantActivityDiscount);
        return PageUtil.getPage(totle, dgImportantActivityDiscount.getPage(),list, dgImportantActivityDiscount.getRows());
	}

	@Override
	public int insertBackId(DgImportantActivityDiscount record) {
		return dgImportantActivityDiscountMapper.insertBackId(record);
	}
	
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgImportantActivityDiscountMapper.deleteIds(ids);
	}

	@Override
	public List<DgImportantActivityDiscount> seleAll() {
		return dgImportantActivityDiscountMapper.seleAll();
	}

	@Override
	public void trash(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgImportantActivityDiscountMapper.trash(ids);		
	}

	@Override
	public void restore(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgImportantActivityDiscountMapper.restore(ids);	
		
	}

	@Override
	public List<Map<String, Object>> reminder() {
		return dgImportantActivityDiscountMapper.reminder();
	}

	@Override
	public void updateDisable(Integer id) {
		dgImportantActivityDiscountMapper.updateDisable(id);
	}

	@Override
	public int seleNameCode(DgImportantActivityDiscount src) {
		// TODO Auto-generated method stub
		return dgImportantActivityDiscountMapper.seleNameCode(src);
	}

	@Override
	@Transactional
	public void synProImportant(List<DgImportantActivityDiscount> listDiad,
			List<DgImportantAcitivityDiscountS> listDiads) {
		dgImportantActivityDiscountMapper.delPhy();
		dgImporActiDiscsMapper.delPhy();
		if(null!=listDiad&&listDiad.size()>0){
			dgImportantActivityDiscountMapper.insertBatch(listDiad);
		}
		if(null!=listDiads&&listDiads.size()>0){
			dgImporActiDiscsMapper.insertBatch(listDiads);
		}
	}
}