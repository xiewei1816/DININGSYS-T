package com.yqsh.diningsys.web.service.data_upload;

import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.model.SysDatauploadLog;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017-04-26 13:41
 *
 * @author zhshuo
 */
public interface BusinessDataUploadService {

    Map<String,Object> selectClearingWater(Integer i, Integer startLimit, Integer endLimit, String startTime);

    Integer insertBusinessDataUploadLog(SysDatauploadLog sysDatauploadLog);

    String selectLastUploadTime(Integer type);

    Integer selectCountClearingWater(Integer uploadSize,String lastTime);

    LinkedList<DgOpenWater> selectSBillOpenWater(String startTime);

    LinkedList<DgReceptionClearingWater> selectReturnSettle(String startTime);

    LinkedList<SysAuthorizationLog> selectAuthCodeLogData(String authCodeLastTime);
}
