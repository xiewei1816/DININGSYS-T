package com.yqsh.diningsys.web.dao.report;

import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwClearingway;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.report.Payway;
import com.yqsh.diningsys.web.model.report.Statement;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created on 2017-02-08 14:09
 *
 * @author zhshuo
 */
@Repository
public interface BackReportMapper {

    List<Map> countNumberOfMealsBy24Hour(Map<String,Object> param);

    Integer countNumberOfMeals(Map<String,Object> param);

    List<Map> queryItemFileTypeIndex(Map<String, Object> param);

    List<Map> queryItemFileTypeSmall(Map<String, Object> param);

    List<Map> queryItemFileTypeItem(Map<String, Object> param);

    List<Map> queryItemFileTypeOpenWaters(Map<String, Object> param);

    List<Integer> selectCountServiceNumByServiceWater(@Param("serviceId") Integer serviceId,@Param("tableDate") String tableDate);

    Map selectServiceDetailByServiceId(@Param("serviceId") Integer serviceId,@Param("tableDate") String tableDate);

	Map selectServiceDetailByServiceId_new(Map<String,Object> param);

    List<DgOwConsumerDetails> dataSearchDetailsNext(@Param("serviceId") Integer serviceId,@Param("tableDate") String tableDate);

    List<DgOwConsumerDetails> dataSearchDetailsNext_new(Map<String,Object> param);

	Integer countStatementListByPage(Statement statement);

	List<Payway> selectStatementListByPage(Statement statement);
	
    List<Map> statementQuery(Map<String, Object> map);

    /**
     * 营业总额查询
     * @param map
     */
    Map openDayOpeningAmount(Map<String, Object> map);

    /**
     * 账单以及客位信息
     * @param map
     * @return
     */
    Map openDayBillInfo(Map<String, Object> map);

    /**
     * 结算方式
     * @param map
     * @return
     */
    DgOwClearingway openDayClearingWay(Map<String, Object> map);

    /**
     * 登记押金以及已退押金查询
     * @param map
     * @return
     */
    Map openDayCashPledgeQuery(Map<String, Object> map);

    /**
     * 特殊品项统计
     * @param map
     * @return
     */
    Map openDaySpecialItem(Map<String, Object> map);

    /**
     * 消费区域包房费
     * @param map
     * @return
     */
    List<Map> openDaySpeedAreaRoomCosts(Map<String, Object> map);

    /**
     * 品项销售统计
     * @param map
     * @return
     */
    List<Map> openDayItemSaleInfo(Map<String, Object> map);

    /**
     * 就餐人数统计
     * @param map
     * @return
     */
    List<Map> openDayNumberOfMeals(Map<String, Object> map);

    /**
     * 品项类别现金销售统计
     * @param map
     * @return
     */
    List<Map> openDayItemSaleRMBInfo(Map<String, Object> map);

    List<Integer> selectCountServiceNumByOpenWater(Map<String, Object> param);

    List<Integer> selectOpenWaterId(Map<String, Object> param);

    List<Integer> selectServiceId(Map<String,Object> param);

	List<Map> itemFileDataSaleStatistical(Map<String, Object> map);
    
}
