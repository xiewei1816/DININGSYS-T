package com.yqsh.diningsys.web.dao.api;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgCashPledge;

@Repository
public interface DgCashPledgeMapper {

	List<DgCashPledge> selectCashPledge(DgCashPledge dcp);

	int regMoney(DgCashPledge dcp);
	
	int updateCashPledge(DgCashPledge dcp);

	int updetePrintNumber(DgCashPledge dcp);


}
