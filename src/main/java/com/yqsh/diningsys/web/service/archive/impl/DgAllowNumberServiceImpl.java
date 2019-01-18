package com.yqsh.diningsys.web.service.archive.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgAllowNumberMapper;
import com.yqsh.diningsys.web.model.archive.DgAllowNumber;
import com.yqsh.diningsys.web.service.archive.DgAllowNumberService;

@Service
public class DgAllowNumberServiceImpl extends
		GenericServiceImpl<DgAllowNumber, Integer> implements
		DgAllowNumberService {
	@Resource
	private DgAllowNumberMapper dgAllowNumberMapper;

	@Override
	public Page<DgAllowNumber> getListByPage(DgAllowNumber page) {
		// TODO Auto-generated method stub
		Integer totle = dgAllowNumberMapper.getCount(page);
		List<DgAllowNumber> list =  dgAllowNumberMapper
				.getListByPage(page);
		return PageUtil.getPage(totle, page.getPage(), list, page.getRows());
	}

	@Override
	public int deleteById(String ids) {
		// TODO Auto-generated method stub
        List id = new ArrayList();
        Collections.addAll(id,ids.split(","));
		return dgAllowNumberMapper.deleteById(id);
	}

	@Override
	public int updateById(DgAllowNumber record) {
		// TODO Auto-generated method stub
		return dgAllowNumberMapper.updateById(record);
	}

	@Override
	public Map insertOrUpdate(DgAllowNumber org) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		if (org.getId() == null) {
			int count = dgAllowNumberMapper.getCount(org);
			if (count > 0) {
				ret.put("success", false);
				ret.put("msg", "已存在此名称!");
			} else {
				dgAllowNumberMapper.insert(org);
				ret.put("success", true);
			}
		} else {
			dgAllowNumberMapper.updateById(org);
			ret.put("success", true);
		}
		return ret;
	}

	@Override
	public DgAllowNumber selectById(Integer id) {
		// TODO Auto-generated method stub
		return dgAllowNumberMapper.selectById(id);
	}

	@Override
	public GenericDao<DgAllowNumber, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgAllowNumberMapper;
	}

	@Override
	public List<DgAllowNumber> getAllList() {
		// TODO Auto-generated method stub
		return dgAllowNumberMapper.getAllList();
	}

}