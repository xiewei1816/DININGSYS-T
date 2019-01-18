package com.yqsh.diningsys.web.service.report.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.model.report.ItemFileSell;
import com.yqsh.diningsys.web.model.report.Payway;

import org.apache.ibatis.annotations.Param;
import com.yqsh.diningsys.web.util.TableQueryUtil;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.report.ReportPayWayMapper;
import com.yqsh.diningsys.web.service.report.ReportPayWayService;
import com.yqsh.diningsys.web.util.TableQueryUtil;
import org.springframework.util.StringUtils;

@Service
public class ReportPayWayServiceImpl implements ReportPayWayService {
	
	@Resource
	private ReportPayWayMapper reportPayWayMapper;

	@Override
	public Page<Payway> getPaywayPageList(Payway payway) {
		Integer totle = reportPayWayMapper.countPaywayListByPage(payway);
		List<Payway> list = reportPayWayMapper.selectPaywayListByPage(payway);
		return PageUtil.getPage(totle, payway.getPage(),list, payway.getRows());
	}
	
	@Override
	public List<Map<String, Object>> selectPayWayReportList(String cwName,String startTime,String endTime) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("cwName", cwName);
		params.put("startTime", startTime);
		params.put("endTime", endTime);

		List<String> strings = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);

		params.put("tableSuffixList", strings);

		return reportPayWayMapper.selectPayWayReportList(params);
	}

	/**
	 * 相加
	 * @param v1
	 * @param v2
	 * @return
	 */
    public static double add(double v1, double v2){  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.add(b2).doubleValue();  
    }

	@Override
	public Map<String, Object> selectBusinessStatisticsDateSufl(
			String searchDate) {
		// TODO Auto-generated method stub
		String tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(searchDate);
		if(StringUtils.isEmpty(tableDate)){
            return null;
        }
		return reportPayWayMapper.selectBusinessStatisticsDateSufl(searchDate,tableDate);
	}

	@Override
	public List<Map<String, Object>> selectBusinessStatisticsDateSk(String searchDate) {
		// TODO Auto-generated method stub
		String tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(searchDate);
		if(StringUtils.isEmpty(tableDate)){
            return null;
        }
		return reportPayWayMapper.selectBusinessStatisticsDateSk(searchDate,tableDate);
	}

	@Override
	public List<Map<String, Object>> selectBusinessStatisticsDateRj(
			String searchDate) {
		// TODO Auto-generated method stub
		String tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(searchDate);
		if(StringUtils.isEmpty(tableDate)){
            return null;
        }
		return reportPayWayMapper.selectBusinessStatisticsDateRj(searchDate,tableDate);
	}

	@Override
	public Map<String, Object> selectBusinessStatisticsMonth(String searchDate) {
		// TODO Auto-generated method stub
		String tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(searchDate);
		if(StringUtils.isEmpty(tableDate)){
            return null;
        }
		return reportPayWayMapper.selectBusinessStatisticsMonth(searchDate,tableDate);
	}

	@Override
	public Map<String, Object> selectBusinessStatisticsWeek(String sStartDate,
			String sEndDate, String bStartDate, String bEndDate) {
		// TODO Auto-generated method stub
		Map org = new HashMap();
		org.put("bStartDate",bStartDate);
		org.put("bEndDate",bEndDate);
		org.put("sStartDate",sStartDate);
		org.put("sEndDate",sEndDate);
		
		List<String> sTableDateList = TableQueryUtil.tableNameUtilWithMonthRange(sStartDate, sEndDate);
		List<String> bTableDateList = TableQueryUtil.tableNameUtilWithMonthRange(bStartDate, bEndDate);
		if(sTableDateList.size() == 0 || bTableDateList.size() == 0){
            return null;
        }
		org.put("sTableDateList", sTableDateList);
		org.put("bTableDateList", bTableDateList);
		return reportPayWayMapper.selectBusinessStatisticsWeek(org);
	}

	@Override
	public List<Map<String, Object>> selectRegionalOpenBill(String searchDate,String tbBis) {
		// TODO Auto-generated method stub
		Map org = new HashMap();
		org.put("searchDate",searchDate);
		org.put("tbBis",tbBis);
		return reportPayWayMapper.selectRegionalOpenBill(org);
	}

	@Override
	public List<Map<String, Object>> selectRegionalOpenAnalysisBill(
			String searchDate, String tbBis) {
		// TODO Auto-generated method stub
		Map org = new HashMap();
		org.put("searchDate",searchDate);
		org.put("tbBis",tbBis);
		return reportPayWayMapper.selectRegionalOpenAnalysisBill(org);
	}

	@Override
	public List<DgSettlementWay> selectAllPayWay() {
		return reportPayWayMapper.selectAllPayWay();
	}

}