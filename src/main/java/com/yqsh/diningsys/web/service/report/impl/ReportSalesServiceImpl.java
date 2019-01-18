package com.yqsh.diningsys.web.service.report.impl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.report.ReportSalesMapper;
import com.yqsh.diningsys.web.model.report.Sales;
import com.yqsh.diningsys.web.service.report.ReportSalesService;
import com.yqsh.diningsys.web.util.TableQueryUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportSalesServiceImpl implements ReportSalesService {
	
	@Resource
	private ReportSalesMapper reportSalesMapper;

	@Override
	public Page<Sales> getSalesPageList(Sales sales) {
		Integer total = reportSalesMapper.countSalesListByPage(sales);
		List<Sales> list = reportSalesMapper.selectSalesListByPage(sales);
		return PageUtil.getPage(total, sales.getPage(),list, sales.getRows());
	}
	
	@Override
	public List<Map<String, Object>> selectSalesReportList(Sales sales) {
		return reportSalesMapper.selectSalesReportList(sales);
	}

}