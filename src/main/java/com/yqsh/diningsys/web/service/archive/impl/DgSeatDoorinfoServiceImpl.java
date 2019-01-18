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
import com.yqsh.diningsys.web.dao.archive.DgSeatDoorinfoMapper;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgSeatDoorinfo;
import com.yqsh.diningsys.web.service.archive.DgSeatDoorinfoService;

@Service
public class DgSeatDoorinfoServiceImpl extends
		GenericServiceImpl<DgSeatDoorinfo, Integer> implements
		DgSeatDoorinfoService {
	@Resource
	private DgSeatDoorinfoMapper dgSeatDoorinfoMapper;

	@Override
	public Page<DgSeatDoorinfo> getListByPage(DgSeatDoorinfo page) {
		// TODO Auto-generated method stub
		Integer totle = dgSeatDoorinfoMapper.getCount(page);
		List<DgSeatDoorinfo> list =  dgSeatDoorinfoMapper
				.getListByPage(page);
		return PageUtil.getPage(totle, page.getPage(), list, page.getRows());
	}

	@Override
	public int deleteByMac(String ids) {
		// TODO Auto-generated method stub
        List id = new ArrayList();
        Collections.addAll(id,ids.split(","));
		return dgSeatDoorinfoMapper.deleteByMac(id);
	}

	@Override
	public int updateByMac(DgSeatDoorinfo record) {
		// TODO Auto-generated method stub
		return dgSeatDoorinfoMapper.updateByMac(record);
	}

	@Override
	public GenericDao<DgSeatDoorinfo, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgSeatDoorinfoMapper;
	}

	@Override
	public List<DgConsumerSeat> getSeatNotInDoorInfo() {
		// TODO Auto-generated method stub
		return dgSeatDoorinfoMapper.getSeatNotInDoorInfo();
	}

	@Override
	public Map insertOrUpdate(DgSeatDoorinfo org) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		if (org.getId() == null) {
			int count = dgSeatDoorinfoMapper.getCount(org);
			if (count > 0) {
				ret.put("success", false);
				ret.put("msg", "已存在此mac地址!");
			} else {
				dgSeatDoorinfoMapper.insert(org);
				ret.put("success", true);
			}
		} else {
			dgSeatDoorinfoMapper.updateByMac(org);
			ret.put("success", true);
		}
		return ret;
	}

	@Override
	public DgSeatDoorinfo selectById(Integer id) {
		// TODO Auto-generated method stub
		return dgSeatDoorinfoMapper.selectById(id);
	}

	@Override
	public List<DgConsumerSeat> getAllSeat() {
		// TODO Auto-generated method stub
		return dgSeatDoorinfoMapper.getAllSeat();
	}

}