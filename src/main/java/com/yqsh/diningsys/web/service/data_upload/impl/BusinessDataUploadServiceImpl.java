package com.yqsh.diningsys.web.service.data_upload.impl;

import com.alibaba.fastjson.JSON;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.dao.data_upload.BusinessDataUploadMapper;
import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.model.SysDatauploadLog;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;
import com.yqsh.diningsys.web.service.data_upload.BusinessDataUploadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created on 2017-04-26 13:41
 *
 * @author zhshuo
 */
@Service
public class BusinessDataUploadServiceImpl implements BusinessDataUploadService {

    @Resource
    private BusinessDataUploadMapper businessDataUploadMapper;

    @Override
    public Map<String,Object> selectClearingWater(Integer i, Integer startLimit, Integer endLimit, String startTime) {
        Map<String,Object> map = new HashMap<>();
        map.put("startLimit",startLimit);
        map.put("endLimit",endLimit);
        map.put("startTime",startTime);

        String shopKey = CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY");

        List<DgOpenWater> dgOpenWaters = Collections.emptyList();
        List<DgOwServiceForm> dgOwServiceForms = Collections.emptyList();
        List<DgOwConsumerDetails> dgOwConsumerDetails = Collections.emptyList();
        List<DgCashPledge> dgCashPledges = Collections.emptyList();
        List<DgOwClearingway> dgOwClearingways = Collections.emptyList();
        List<DgOwReceipt> dgOwReceipts = Collections.emptyList();
        //List<DgOwDiscount> dgOwDiscounts = Collections.emptyList();

        LinkedList<DgReceptionClearingWater> dgReceptionClearingWaters = businessDataUploadMapper.selectClearingWater(map);

        if(dgReceptionClearingWaters.size() > 0){
            dgOwClearingways = businessDataUploadMapper.selectDgOwClearingWay(dgReceptionClearingWaters);
            dgOwReceipts = businessDataUploadMapper.selectDgOwRecepits(dgReceptionClearingWaters);
            dgOpenWaters = businessDataUploadMapper.selectOpenWaterByCwId(dgReceptionClearingWaters);
            //dgOwDiscounts = businessDataUploadMapper.selectDiscountInfoByCwId(dgReceptionClearingWaters);
        }

        if(dgOpenWaters.size() > 0){
            dgOwServiceForms = businessDataUploadMapper.selectServiceFormByOwId(dgOpenWaters);
            dgCashPledges = businessDataUploadMapper.selectCashPledge(dgOpenWaters);
        }

        if(dgOwServiceForms.size() > 0){
            dgOwConsumerDetails = businessDataUploadMapper.selectOwConsumerDetails(dgOwServiceForms);
        }

        Map<String,Object> resMap = new HashMap<>();
        resMap.put("dgReceptionClearingWaters", JSON.toJSONString(dgReceptionClearingWaters));
        resMap.put("dgOpenWaters",JSON.toJSONString(dgOpenWaters));
        resMap.put("dgOwServiceForms",JSON.toJSONString(dgOwServiceForms));
        resMap.put("dgOwConsumerDetail",JSON.toJSONString(dgOwConsumerDetails));
        resMap.put("dgCashPledges",JSON.toJSONString(dgCashPledges));
        resMap.put("dgOwReceipts",JSON.toJSONString(dgOwReceipts));
        resMap.put("dgOwClearingways",JSON.toJSONString(dgOwClearingways));
        //resMap.put("dgOwDiscounts",JSON.toJSONString(dgOwDiscounts));
        resMap.put("shopKey",shopKey);
        resMap.put("uploadFlag","1");
        resMap.put("lastTime",dgReceptionClearingWaters.getLast().getClearingTime());
        resMap.put("dataSize",dgReceptionClearingWaters.size());
        return resMap;
    }

    @Override
    public LinkedList<DgOpenWater> selectSBillOpenWater(String startTime) {
        return businessDataUploadMapper.selectSBillOpenWater(startTime);
    }

    @Override
    public LinkedList<DgReceptionClearingWater> selectReturnSettle(String startTime) {
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        return businessDataUploadMapper.selectFwjsCwId(map);
    }

    @Override
    public LinkedList<SysAuthorizationLog> selectAuthCodeLogData(String authCodeLastTime) {
        return businessDataUploadMapper.selectAuthCodeLogData(authCodeLastTime);
    }

    @Override
    public String selectLastUploadTime(Integer type) {
        return businessDataUploadMapper.selectLastUploadTime(type);
    }

    @Override
    public Integer insertBusinessDataUploadLog(SysDatauploadLog sysDatauploadLog) {
        return businessDataUploadMapper.insertBusinessDataUploadLog(sysDatauploadLog);
    }

    @Override
    public Integer selectCountClearingWater(Integer uploadSize,String lastTime) {
        Integer count = businessDataUploadMapper.selectCountClearingWater(lastTime);

        BigDecimal cb = new BigDecimal(count);

        BigDecimal bigDecimal = new BigDecimal(uploadSize);

        return (int) Math.ceil(cb.divide(bigDecimal, 2, BigDecimal.ROUND_UP).doubleValue());
    }

}
