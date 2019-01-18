package com.yqsh.diningsys.web.dao.api;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryPrintMapper {

	Map<String, Object> selectTeamSeatMembersList(Map<String, Object> params);
	
	Map<String, Object> selectGuestList(Map<String, Object> params);

	List<Map<String, Object>> selectGuestItemFileList(Map<String, Object> params);
	
	Map<String, Object> selectCheckDocSeatList(Map<String, Object> params);

	List<Map<String, Object>> selectCheckDocList(Map<String, Object> params);

	List<Map<String, Object>> selectCheckItemFileList(Map<String, Object> params);

	List<Map<String, Object>> selectTeamMembersList();

	List<Map<String, Object>> selectTeamBySeatNameList(Map<String, Object> params);

	List<Map<String, Object>> selectTeamList(Map<String, Object> params);

	int updWaterJoinTeamNotes(Map<String, Object> params);

	List<Map<String, Object>> selectClearingList(Map<String, Object> params);

	Map<String, Object> selectClearingBaseList(Map<String, Object> params);

	List<Map<String, Object>> selectOpenWaterBaseList(Map<String, Object> params);

	List<Map<String, Object>> selectClearingWayBaseList(
			Map<String, Object> params);
	
	List<Map<String, Object>> selectReceiptFileList(Map<String, Object> params);

	List<Map<String, Object>> selectDocListOpenWater(Map<String, Object> params);

	Map<String, Object> selectSeatAreaList(Map<String, Object> params);

	Map<String, Object> getTeamMembersList(Map<String, Object> params);

	int updRepealWaterSeatState(Map<String, Object> params);

	int updRepealWaterManyTeam(Map<String, Object> params);

	int updRepealWaterSeatAmount(Map<String, Object> zparams);

	List<Map<String, Object>> getNoBalenceDataList();

	List<Map<String, Object>> selectIntoOrOutOpenWaterList(
			Map<String, Object> params);

	List<Map<String, Object>> selectIntoOrOutItemFileList(
			Map<String, Object> params);


    void updateOldSeatState(@Param("oldSeatId") Integer oldSeatId);

	void updateOpenWaterNonTeam(@Param("owNum") String owNum);

}
