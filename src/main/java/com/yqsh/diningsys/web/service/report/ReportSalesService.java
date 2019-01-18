package com.yqsh.diningsys.web.service.report;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.report.Sales;

import java.util.List;
import java.util.Map;

public interface ReportSalesService {

    Page<Sales> getSalesPageList(Sales sales);

	List<Map<String,Object>> selectSalesReportList(Sales sales);

}
