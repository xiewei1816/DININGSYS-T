package com.yqsh.diningsys.web.dao.report;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.model.report.Payway;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportPayWayMapper {
	
	Integer countPaywayListByPage(Payway payway);

	List<Payway> selectPaywayListByPage(Payway payway);

	List<Map<String, Object>> selectPayWayReportList(Map<String, Object> params);
	
	Map<String,Object> selectBusinessStatisticsDateSufl(@Param("searchDate")String searchDate, @Param("tableDate")String tableDate);
	
	List<Map<String,Object>> selectBusinessStatisticsDateSk(@Param("searchDate")String searchDate, @Param("tableDate")String tableDate);
	
	List<Map<String,Object>> selectBusinessStatisticsDateRj(@Param("searchDate")String searchDate, @Param("tableDate")String tableDate);
	
	Map<String,Object> selectBusinessStatisticsMonth(@Param("searchDate")String searchDate, @Param("tableDate")String tableDate);
	
	Map<String,Object> selectBusinessStatisticsWeek(Map org);
	
	List<Map<String,Object>> selectRegionalOpenBill(Map org);
		
	List<Map<String,Object>> selectRegionalOpenAnalysisBill(Map org);

    List<DgSettlementWay> selectAllPayWay();

}
