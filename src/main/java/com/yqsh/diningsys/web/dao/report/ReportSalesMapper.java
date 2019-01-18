package com.yqsh.diningsys.web.dao.report;
import com.yqsh.diningsys.web.model.report.Sales;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface ReportSalesMapper {

	Integer countSalesListByPage(Sales sales);

	List<Sales> selectSalesListByPage(Sales sales);

	List<Map<String, Object>> selectSalesReportList(Sales sales);

}
