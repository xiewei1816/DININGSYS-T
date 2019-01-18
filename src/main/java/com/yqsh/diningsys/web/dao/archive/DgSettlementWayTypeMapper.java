package com.yqsh.diningsys.web.dao.archive;

import java.util.List;

import com.yqsh.diningsys.web.model.archive.DgSettlementWayType;

public interface DgSettlementWayTypeMapper {
	
	List<DgSettlementWayType> getListByPage(DgSettlementWayType dgSettlementWayType);
	
    Integer countListByPage(DgSettlementWayType dgSettlementWayType);
    
    DgSettlementWayType getSettlementWayTypeByID(DgSettlementWayType dgSettlementWayType);
    
	int newInsert(DgSettlementWayType dgSettlementWayType);
	
	int update(DgSettlementWayType dgSettlementWayType);
	
	int delete(DgSettlementWayType dgSettlementWayType);
	
	List<DgSettlementWayType> getAllList(DgSettlementWayType dgSettlementWayType);
}
