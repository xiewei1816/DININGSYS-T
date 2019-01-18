package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import org.springframework.stereotype.Repository;

@Repository
public interface DgSettlementWayMapper {
	
	List<DgSettlementWay> getListByPage(DgSettlementWay dgSettlementWay);
	
    Integer countListByPage(DgSettlementWay dgSettlementWay);
    
    DgSettlementWay getSettlementWayByID(DgSettlementWay dgSettlementWay);
    
	int newInsert(DgSettlementWay dgSettlementWay);
	
	int update(DgSettlementWay dgSettlementWay);
	
	int delete(DgSettlementWay dgSettlementWay);
	
	List<DgSettlementWay> getAllList(DgSettlementWay dgSettlementWay);

    List<DgSettlementWay> getSettleMentWayRankList();

    void addSettlementWayRank(Map<String,Object> map);

    void updateSettlementWayRank(Map<String, Object> params);

    Map<String, Object> selSettlementWayRank(Map<String, Object> map);

    void upateWayItemSet(Map<String, Object> map);

    DgSettlementWay selectDataById(Integer id);
	int updateSettlementWayRankMoveUp(Integer id);

	int updateSettlementWayRankMoveDown(Integer id);

	List<String> selectSettlementWayRankMoveTopper(Integer id);

	void updateSettlementWayRankMoveTopper(List<String> ids);

}
