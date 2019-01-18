package com.yqsh.diningsys.web.service.deskBusiness;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;

import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-14 下午1:50
 */
public interface DgCurrentOpenWaterService extends GenericService<DgOpenWater,Integer>{

    /**
     * 获取当前客位信息
     * @return
     */
    List<DgConsumerSeat> selectCurrentSeatInfo(Integer org, Integer place, Integer bis, Integer seatState);

    /**
     * 获取当前条件下所有客座能容纳的所有人数以及上座人数
     * @param place
     * @param bis
     * @param seatState
     * @return
     */
    Map selectAllSeatPeople(Integer place,Integer bis,Integer seatState);

    /**
     * 获取当前条件下的客位统计信息
     * @param org
     * @param place
     * @param bis
     * @return
     */
    Map selectCurrentSeatCount(Integer org,Integer place,Integer bis);

    /**
     * 获取当前条件下的结算方式信息
     * @param org
     * @param place
     * @param bis
     * @return
     */
    List<Map> selectCurrentPayInfo(Integer org,Integer place,Integer bis);

    /**
     * 获取当前市别条件下的账单信息
     * @param bis
     * @param bis
     * @return
     */
    Map selectCurrentBisClassInfo(Integer org, Integer bis);

    /**
     * 获取今日预定总数、今日执行总数
     * @param place
     * @return
     */
    Map selecttodayReservationInfo(Integer place);

    /**
     * 获取所选市别预定，所选市别执行，条件包含客座区域以及市别
     * @param place
     * @param bis
     * @return
     */
    Map selectPlaceBisRes(Integer place,Integer bis);

    /**
     * 获取当前客位的营业流水信息
     * @param id
     * @return
     */
    DgOpenWater selectOpenWaterInfoById(Integer id);

    /**
     * 根据流水号ID，获取该流水号下的所有品项
     * @param id 流水号ID
     * @return
     */
    List<Map> selectOpenWaterItemFileByOwId(Integer id);

    /**
     * 检测该客位是否有已结账单
     * @param id
     * @return
     */
    DgReceptionClearingWater selectSeatHasClosedCheck(Integer id);

    List<DgOpenWater> getSeatOpenWater(Integer id);

    /**
     * 根据营业ID获取具体的消费信息
     * @param id
     * @return
     */
    List<DgOwConsumerDetails> getOpenWaterConDeInfo(Integer id);

    /**
     * 根据结算流水获取结算方式、优惠信息以及发票信息
     * @param cwId
     * @return
     */
    Map getOpenWaterOtherInfo(Integer cwId);

    Map getOpenWaterOtherInfo_new(Integer cwId,String time);

    /**
     * 根据结算流水单独获取结算方式信息
     * @param id
     * @return
     */
    List<DgOwClearingway> getClearingWayByCwId(Integer id);

    /**
     * 根据结算流水单独获取发票信息
     * @param id
     * @return
     */
    List<DgOwReceipt> getReceiptInfoByCwId(Integer id);

    List<DgOwReceipt> getReceiptInfoByCwId_new(Integer id,String time);

    /**
     * 编辑结算备注与结账单标注
     * @param clearingNotes
     * @param statementLabel
     * @param cwId
     */
    void editClearingNotes(String clearingNotes,Integer statementLabel,Integer cwId);

    Map getNotesAndLabel(Integer id);

    /*前台接口使用*/
    Map selectCurrentSeatCountByPosArea(String areas);

    /**
     * 根据桌位ID查询营业流水信息
     * @param seatId
     */
    List<DgOpenWater> selectOpenWaterBySeatId(Integer seatId);

    DgOpenWater selectOpenWaterByOwNum(String owNum);

    List<DgOwConsumerDetails> getOpenWaterConDeInfo_new(Integer owId,String time);
}
