package com.yqsh.diningsys.web.service.api;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 前台结算的相关service方法
 *
 * @author zhshuo create on 2016-12-30 14:42
 */
public interface PaySettlementService {

    /**
     * 快速结算
     * @param userCode
     * @param owNums
     * @param clearingWayData
     * @param payType
     * @param amountsReceivable
     * @param pos
     * @param zeroMoney
     * @param fixedDiscount
     * @param jsNum
     */
    Map modifyFastSettlement(String userCode, List<DgOpenWater> owNums, String clearingWayData,
                             Integer payType, Double amountsReceivable, Integer pos, Double zeroMoney,
                             Double fixedDiscount, String clearingNotes, String statementLabel, String invoicingData,
                             Double generalProportions, Double singleProportions, Double paidMoney, Double changeAmount,
                             Boolean isWechatPay, Boolean isAliPay, String payAuthCode, String payBody, String jsNum,Integer isGz,  String couponData,String isNotice,HttpServletRequest request, HttpServletResponse response);

    /**
     * 插入结算流水数据
     */
    Integer insertClearingWaterData(Map<String, Object> param);

    /**
     * 埋单时插入结算流水数据
     * @param param
     * @return
     */
    Integer insertAdvancePayClearingWaterData(Map<String, Object> param);

    /**
     * 批量插入更新结算的时候品项的最终价格
     */
    void modifyItemFile(DgOwConsumerDetails dgOwConsumerDetails,Integer payType,Integer owId,Double generalProportions,Double singleProportions);

    /**
     * 插入结算支付方式数据
     */
    void insertClearWayData(Integer clearingId,List<Map<String,Object>> clearingWayDataList);

    /**
     * 修改营业流水（转账流水）的状态为已结/埋单
     */
    void modifyOpenWaterStateAndCwId(Map<String, Object> param);

    /**
     * 更新客座状态
     * @param id
     * @param operaType 操作类型，1（结算），2（埋单）
     */
    void modifySeatState(Integer id,Integer operaType);

    /**
     * 埋单操作
     * @param userCode
     * @param dgOpenWaters
     * @param payType
     * @param pos
     * @param fixedDiscount
     * @param invoicingData
     * @return
     */
    Map modifyAdvancePay(String userCode, List<DgOpenWater> dgOpenWaters, Integer payType, Integer pos,String clearingWayData,
                         Double fixedDiscount, String invoicingData,Integer generalProportions,Integer singleProportions,Integer clearingId);

    /**
     * 发票数据插入
     * @param invoiceData
     */
    void insertInvoice(Integer clearingId,String invoiceData);

    /**
     * 修改营业流水以及修改营业流水下面的具体品项的结算价格
     * @param dgOpenWaters
     * @param clearingId
     * @param payType
     * @param operaType 操作类型1（买单），2（埋单）
     */
    void modifyOpenWaterAndDetails(List<DgOpenWater> dgOpenWaters, Integer clearingId, Integer payType,
                                   Integer operaType,String userCode,Integer pos,Double generalProportions,Double singleProportions);

    /**
     * 埋单时候，修改营业流水的埋单信息以及插入埋单的记录
     * @param map
     */
    void modifyOpenWaterAdvancePayInfo(Map<String, Object> map);

    /**
     * 插入营业流水埋单锁定信息/结算
     * @param param
     */
    void insertLockInfo(Map<String,Object> param);

    /**
     * 撤销埋单
     * @param dgOpenWaterNum
     */
    void cancelAdvancePay(List<DgOpenWater> dgOpenWaterNum,DgOpenWater dgOpenWater);

    /**
     * 打印营业流水客用单信息
     * @param dgOpenWaters
     */
    List<DgOpenWater> printGuestBillingData(List<DgOpenWater> dgOpenWaters);

    /**
     * 获取埋单时候的埋单的信息
     * @param clearingId
     */
    Map<String, Object> selectAllAdvancePayInfo(Integer clearingId);

    /**
     * 根据结算流水ID，查询结算流水的详细信息
     * @param clearing_water_id
     */
    DgReceptionClearingWater selectClearingWaterById(int clearing_water_id);
}
