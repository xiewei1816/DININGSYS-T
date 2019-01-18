package com.yqsh.diningsys.web.service.print.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.print.DgPrintManagerMapper;
import com.yqsh.diningsys.web.dao.print.DgPrintManagerSMapper;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.print.DgPrintManager;
import com.yqsh.diningsys.web.model.print.DgPrintManagerS;
import com.yqsh.diningsys.web.service.print.DgPrintManagerService;

@Service
public class DgPrintManagerServiceImpl extends
		GenericServiceImpl<DgPrintManager, Integer> implements
		DgPrintManagerService {
	@Resource
	private DgPrintManagerMapper dgPrintManagerMapper;
	@Resource
	private DgPrintManagerSMapper dgPrintManagerSMapper;

	@Override
	public GenericDao<DgPrintManager, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgPrintManagerMapper;
	}

	@Override
	public Page<DgPrintManager> getPage(DgPrintManager dgPrintManager) {
		// TODO Auto-generated method stub
		Integer totle = dgPrintManagerMapper.countAllData(dgPrintManager);
		List<DgPrintManager> list = dgPrintManagerMapper
				.getAllData(dgPrintManager);
		for (DgPrintManager l : list) {
			if (!StringUtils.isEmpty(l.getAreaIds())) {
				List<DgConsumptionArea> areas = getAreaByIds(l.getAreaIds());
				String areaNames = "";
				for (DgConsumptionArea a : areas) {
					areaNames += a.getName()+",";
				}
				areaNames = areaNames.substring(0, areaNames.length() - 1);
				l.setAreaNames(areaNames);
			} else {
				l.setAreaNames("");
			}
		}
		return PageUtil.getPage(totle, dgPrintManager.getPage(), list,
				dgPrintManager.getRows());
	}

	@Override
	public void trash(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		dgPrintManagerMapper.trash(ids);
	}

	@Override
	public void restore(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		dgPrintManagerMapper.restore(ids);
	}

	@Override
	public void deleteIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		dgPrintManagerMapper.deleteIds(ids);
		dgPrintManagerSMapper.deleteIds(ids);
	}

	@Override
	public int insertBackId(DgPrintManager record) {
		// TODO Auto-generated method stub
		return dgPrintManagerMapper.insertBackId(record);
	}

	@Override
	public List<DgItemFile> selectAllItemFile(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		return dgPrintManagerMapper.selectAllItemFile(ids);
	}

	@Override
	public List<DgItemFile> selectSmallItemFile(String s, int ppxl_id) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));

		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("ppxl_id", ppxl_id);
		res.put("ids", ids);
		return dgPrintManagerMapper.selectSmallItemFile(res);
	}

	@Override
	public List<DgItemFile> selectBigItemFile(String s, int ppdl_id) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("ppdl_id", ppdl_id);
		res.put("ids", ids);
		return dgPrintManagerMapper.selectBigItemFile(res);
	}

	@Override
	public List<DgItemFile> searchVague(String search, String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("search", search);
		ret.put("ids", ids);
		return dgPrintManagerMapper.searchVague(ret);
	}

	@Override
	public void deleteChildByPid(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		dgPrintManagerSMapper.deleteIds(ids);
	}

	@Override
	public void insertChilds(List<DgPrintManagerS> lists) {
		// TODO Auto-generated method stub
		dgPrintManagerSMapper.insertChilds(lists);
	}

	@Override
	public List<DgPrintManagerS> selectItemByPid(Integer id) {
		// TODO Auto-generated method stub
		return dgPrintManagerSMapper.selectItemByPid(id);
	}

	@Override
	public List<DgPrintManagerS> selectItemTypeByPid(Integer id) {
		// TODO Auto-generated method stub
		return dgPrintManagerSMapper.selectItemTypeByPid(id);
	}

	@Override
	public List<DgItemFile> selectItemByAdd(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		return dgPrintManagerSMapper.selectItemByAdd(ids);
	}

	@Override
	public List<DgItemFileType> selectItemTypeByAdd(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		return dgPrintManagerSMapper.selectItemTypeByAdd(ids);
	}

	@Override
	public List<DgItemFile> selectHaveItem(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		return dgPrintManagerMapper.selectHaveItem(ids);
	}

	@Override
	public List<DgConsumptionArea> getAreaByIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		return dgPrintManagerMapper.getAreaByIds(ids);
	}

	@Override
	public List<DgItemFileType> selectAllItemFileType(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		return dgPrintManagerMapper.selectAllItemFileType(ids);
	}

	@Override
	public List<DgItemFileType> selectHaveItemType(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		return dgPrintManagerMapper.selectHaveItemType(ids);
	}

	@Override
	public List<DgItemFileType> selectSmallItemFileType(String s, Integer pId) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	        
		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("parent_id", pId);
		res.put("ids",ids);
		return dgPrintManagerMapper.selectSmallItemFileType(res);
	}

	@Override
	public List<DgPrintManager> selectPrintManagerByItem(Map orgs) {
		// TODO Auto-generated method stub
		return dgPrintManagerSMapper.selectPrintManagerByItem(orgs);
	}

}
