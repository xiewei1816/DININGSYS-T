package com.yqsh.diningsys.web.service.report;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.model.report.Payway;

import org.apache.ibatis.annotations.Param;

/**
 * 结算方式信息统计报表
 * @author xiewei
 *
 */
public interface ReportPayWayService {
	
	/**
	 * 分页查询结算方式信息
	 * @param payway
	 * @return
	 */
    com.yqsh.diningsys.core.util.Page<Payway> getPaywayPageList(Payway payway);
    
	/**
	 * 查询结算方式信息
	 * @param cwName 结算方式
	 * @param startTime 开始时间
     * @param endTime 结束时间
	 * @return
	 */
	List<Map<String,Object>> selectPayWayReportList(String cwName,String startTime,String endTime);

	/**
	 * 营业统计时间分析(按日)
	 * @param searchDate
	 * @return
	 */
	Map<String,Object> selectBusinessStatisticsDateSufl(String searchDate);
	List<Map<String,Object>> selectBusinessStatisticsDateSk(String searchDate);
	List<Map<String,Object>> selectBusinessStatisticsDateRj(String searchDate);
	/**
	 * 营业统计时间分析(按月)
	 * @param searchDate
	 * @return
	 */
	Map<String,Object> selectBusinessStatisticsMonth(String searchDate);
	/**
	 * 营业统计时间分析(按周)
	 * @param searchDate
	 * @return
	 */
	Map<String,Object> selectBusinessStatisticsWeek(String sStartDate,String sEndDate,String bStartDate,String bEndDate);
	/**
	 * 区域开台人均
	 * @param searchDate
	 * @return
	 */
	List<Map<String,Object>> selectRegionalOpenBill(String searchDate,String tbBis);
	
	/**
	 * 区域开台经营分析
	 * @param searchDate
	 * @return
	 */
	List<Map<String,Object>> selectRegionalOpenAnalysisBill(String searchDate,String tbBis);

    List<DgSettlementWay> selectAllPayWay();

}
