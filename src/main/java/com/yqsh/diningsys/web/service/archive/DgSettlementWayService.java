package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgSettlementWay;

public interface DgSettlementWayService {
	com.yqsh.diningsys.core.util.Page<DgSettlementWay> getPageList(DgSettlementWay dgSettlementWay);
	
	int insertOrUpd(DgSettlementWay dgSettlementWay);
	
	DgSettlementWay getDgSettlementWayByID(DgSettlementWay dgSettlementWay);
	
	int deleteByIds(DgSettlementWay dgSettlementWay);
	
	List<DgSettlementWay> getAllList(DgSettlementWay dgSettlementWay);

    void upateWayItemSet(Integer wayId, String ids);

    List<DgSettlementWay> getSettleMentWayRankList();

	void addSettlementWayRank(Map<String, Object> map);

	void updateSettlementWayRank(Map<String, Object> params);

    Map<String, Object> selSettlementWayRank(Map<String, Object> map);

    DgSettlementWay selectDataById(Integer id);

	int updateSettlementWayRankMoveUp(Integer id);

	int updateSettlementWayRankMoveDown(Integer id);

	List<String> selectSettlementWayRankMoveTopper(Integer id);

	void updateSettlementWayRankMoveTopper(List<String> ids);

}
