package com.yqsh.diningsys.web.service.archive;

import java.util.List;

import com.yqsh.diningsys.web.model.archive.DgSettlementWayType;

public interface DgSettlementWayTypeService {
	com.yqsh.diningsys.core.util.Page<DgSettlementWayType> getPageList(DgSettlementWayType dgSettlementWayType);
	
	int insertOrUpd(DgSettlementWayType dgSettlementWayType);
	
	DgSettlementWayType getDgSettlementWayTypeByID(DgSettlementWayType dgSettlementWayType);
	
	int deleteByIds(DgSettlementWayType dgSettlementWayType);
	
	List<DgSettlementWayType> getAllList(DgSettlementWayType dgSettlementWayType);
}
