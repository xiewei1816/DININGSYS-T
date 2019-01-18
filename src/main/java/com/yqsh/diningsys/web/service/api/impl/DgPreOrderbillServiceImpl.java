package com.yqsh.diningsys.web.service.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.api.DgPreOrderbillMapper;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;
import com.yqsh.diningsys.web.service.api.DgPreOrderbillService;

@Service
public class DgPreOrderbillServiceImpl extends GenericServiceImpl<DgPreOrderbill,String> implements DgPreOrderbillService{
	
	@Resource
	private DgPreOrderbillMapper dgPreOrderbillMapper;
	
	@Override
	public Integer updateItemCount(DgPreOrderbill dgPreOrderbill) {
		// TODO Auto-generated method stub
		return dgPreOrderbillMapper.updateItemCount(dgPreOrderbill);
	}

	@Override
	public List<DgPreOrderbill> selectByWaterId(Integer id) {
		// TODO Auto-generated method stub
		return dgPreOrderbillMapper.selectByWaterId(id);
	}


	@Override
	public Integer insertBatch(List<DgPreOrderbill> dgPreOrderbills) {
		// TODO Auto-generated method stub
		return dgPreOrderbillMapper.insertBatch(dgPreOrderbills);
	}

	@Override
	public List<DgPreOrderbill> selectByPreNum(String owNum) {
		// TODO Auto-generated method stub
		return dgPreOrderbillMapper.selectByPreNum(owNum);
	}

	@Override
	public Integer deleteByWaterId(Integer waterId) {
		// TODO Auto-generated method stub
		return dgPreOrderbillMapper.deleteByWaterId(waterId);
	}

	@Override
	public GenericDao<DgPreOrderbill, String> getDao() {
		// TODO Auto-generated method stub
		return dgPreOrderbillMapper;
	}

}
