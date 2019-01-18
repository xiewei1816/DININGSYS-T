package com.yqsh.diningsys.web.dao.data_upload;

import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.model.SysDatauploadLog;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017-04-26 13:45
 *
 * @author zhshuo
 */
@Repository
public interface BusinessDataUploadMapper {

    List<DgOpenWater> selectOpenWaterByCwId(@Param("list") List<DgReceptionClearingWater> dgReceptionClearingWaters);

    Integer insertBusinessDataUploadLog(SysDatauploadLog sysDatauploadLog);

    LinkedList<DgReceptionClearingWater> selectClearingWater(Map<String, Object> map);

    String selectLastUploadTime(@Param("type") Integer type);

    List<DgOwServiceForm> selectServiceFormByOwId(@Param("list") List<DgOpenWater> dgOpenWaters);

    List<DgOwConsumerDetails> selectOwConsumerDetails(@Param("list") List<DgOwServiceForm> dgOwServiceForms);

    List<DgCashPledge> selectCashPledge(@Param("list") List<DgOpenWater> dgOpenWaters);

    List<DgOwReceipt> selectDgOwRecepits(@Param("list") List<DgReceptionClearingWater> dgReceptionClearingWaters);

    Integer selectCountClearingWater(@Param("startTime") String time);

    List<DgOwClearingway> selectDgOwClearingWay(@Param("list") LinkedList<DgReceptionClearingWater> dgReceptionClearingWaters);

    LinkedList<DgReceptionClearingWater> selectFwjsCwId(Map<String, Object> map);

    LinkedList<DgOpenWater> selectSBillOpenWater(@Param("startTime") String startTime);

    LinkedList<SysAuthorizationLog> selectAuthCodeLogData(@Param("startTime") String authCodeLastTime);

    List<DgOwDiscount> selectDiscountInfoByCwId(@Param("list") LinkedList<DgReceptionClearingWater> dgReceptionClearingWaters);
}
