package com.yqsh.diningsys.web.service.archive.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgSettlementWayTypeMapper;
import com.yqsh.diningsys.web.model.archive.DgSettlementWayType;
import com.yqsh.diningsys.web.service.archive.DgSettlementWayTypeService;

@Service
public class DgSettlementWayTypeServiceImpl implements DgSettlementWayTypeService {
	
	@Resource
	private DgSettlementWayTypeMapper settlementWayTypeMapper;
	
	@Override
	public Page<DgSettlementWayType> getPageList(
			DgSettlementWayType dgSettlementWayType) {
		Integer totle = settlementWayTypeMapper.countListByPage(dgSettlementWayType);
		List<DgSettlementWayType> list = settlementWayTypeMapper.getListByPage(dgSettlementWayType);
		return PageUtil.getPage(totle, dgSettlementWayType.getPage(),list, dgSettlementWayType.getRows());
	}

	@Override
	public int insertOrUpd(DgSettlementWayType dgSettlementWayType) {
		int result = 0;
		if(dgSettlementWayType.getId() != null && dgSettlementWayType.getId() > 0){
			result = settlementWayTypeMapper.update(dgSettlementWayType);
		}else{
			dgSettlementWayType.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = settlementWayTypeMapper.newInsert(dgSettlementWayType);
		}
		return result;
	}

	@Override
	public DgSettlementWayType getDgSettlementWayTypeByID(
			DgSettlementWayType dgSettlementWayType) {
		return settlementWayTypeMapper.getSettlementWayTypeByID(dgSettlementWayType);
	}

	@Override
	public int deleteByIds(DgSettlementWayType dgSettlementWayType) {
		return settlementWayTypeMapper.delete(dgSettlementWayType);
	}

	@Override
	public List<DgSettlementWayType> getAllList(
			DgSettlementWayType dgSettlementWayType) {
		return settlementWayTypeMapper.getAllList(dgSettlementWayType);
	}

}
