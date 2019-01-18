package com.yqsh.diningsys.web.service.report;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.report.Payway;
import com.yqsh.diningsys.web.model.report.Statement;

/**
 * Created on 2017-02-08 14:07
 *
 * @author zhshuo
 */
public interface BackReportService {

    List<Map> queryNumberOfMealsBy24Hour(String beginTime,String endTime);

    List<Map> queryItemFileTypeIndex(String beginTime,String endTime);

    List<Map> queryItemFileTypeSmall(String bigNum,String startTime,String endTime);

    List<Map> queryItemFileTypeItem(String smallNum,String startTime,String endTime);

    List<Map> queryItemFileTypeOpenWaters(String itemFileNum,String startTime,String endTime);

    List<Integer> dataSearchDetailsIndex(Integer serviceId,String clearingTime);

    List<Integer> selectServiceDataByOwnum(String openWater, String startTime, String endTime);

    Map dataSearchDetailsNext(Integer serviceId,String tableDate);

    Map dataSearchDetailsNext_new(Integer serviceId,String startTime,String endTime);

    com.yqsh.diningsys.core.util.Page<Payway> getStatementPageList(Statement statement);
    
    List<Map> statementQuery(String startTime, String endTime, Integer consumerArea, Integer bis, Integer pos, String clearingNum);

    Map openDayReportDataSearch(String startTime, String endTime, String orgCode, Integer bis, Integer pos, Integer timeType, Integer moneyType, Integer area);

    List<Map> itemFileDataSaleStatistical(String startTime, String endTime, Integer pos, Integer area,Integer timeType);

}
