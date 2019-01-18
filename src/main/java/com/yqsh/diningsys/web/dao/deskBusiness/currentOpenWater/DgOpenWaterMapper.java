package com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwClearingway;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwReceipt;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceipt;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;

@Repository
public interface DgOpenWaterMapper extends GenericDao<DgOpenWater,Integer>{
    int deleteByPrimaryKey(Map param);

    int insert(DgOpenWater record);

    int insertSelective(DgOpenWater record);

    DgOpenWater selectByPrimaryKey(Map param);

    DgOpenWater selectByOpenWaterByNum(String num);

    int updateByPrimaryKeySelective(DgOpenWater record);

    int updateByPrimaryKey(DgOpenWater record);

    int updateByPrimaryOpenWaterNum(DgOpenWater record);

    List<DgOpenWater> selectCurrentSeatInfo(Map param);

    Map selectAllSeatPeople(Map param);

    List<Map> selectCurrentSeatCount(Map param);

    List<Map> selectCurrentPayInfo(Map param);

    Map selectCurrentBisClassInfo(Map param);

    Map selecttodayReservationInfo(Map param);

    Map selectPlaceBisRes(Map param);

    DgOpenWater selectOpenWaterInfoById(Map param);

    List<Map> selectOpenWaterItemFileByOwId(Map param);

    DgReceptionClearingWater selectSeatHasClosedCheck(Map param);

    List<DgOpenWater> getSeatOpenWater(Map param);

    /**
     * 根据结算流水ID获取结算方式信息
     * @param param
     * @return
     */
    List<DgOwClearingway> selectSettlementInfoByCwId(Map param);

    /**
     * 根据营业流水ID获取消费明细
     * @param param
     * @return
     */
    List<DgOwConsumerDetails> selectConsumerDetailsByOwId(Map param);

    /**
     * 根据结算流水ID获取优惠信息
     * @param param
     * @return
     */
    List<DgOwDiscount> selectDiscountInfoByCwId(Map param);

    /**
     * 根据结算流水ID获取发票信息
     * @param param
     * @return
     */
    List<DgOwReceipt> selectReceiptInfoByCwId(Map param);

    List<DgOwReceipt> selectReceiptInfoByCwId_new(Map param);

    void editClearingNotes(Map param);

    Map getNotesAndLabel(Map param);

    //未结账单相关

    List<DgOpenWater> selectCurrentOpenWater(Map param);

    //已结账单相关
    List<Map> selectClosedOpenWaterByDate(Map param);

    DgReceptionClearingWater selectCwInfoById(Map param);

    List<DgOpenWater> selectOwInfoByCwId(Map param);

    List<DgReceipt> selectAllReceipt();

    void deleteInvoiceByCwId(Map param);

    void insertInvoice(Map param);

    List<DgPublicCode> selectAllCommonWay();

    void updateCwMoney(Map param);

    void deleteClearingWayByCwId(Map param);

    void insertClearingWay(Map param);

    /*前台接口相关*/

    /**
     * 根据前台POS号，查询出该pos的区域的所有客位的统计信息按照客位状态
     * @param param
     * @return
     */
    List<Map> selectCurrentSeatCountByPostAreaState(Map param);

    /**
     * 根据前台POS号，查询出该pos的区域的所有客位的统计信息按照客位所属区域
     * @param param
     * @return
     */
    List<Map> selectCurrentSeatCountByPostArea(Map param);
    /**
     * 根据状态监测是否有开台
     * @author jianglei
     * 日期 2016年12月22日 下午2:59:22
     * @return
     */
    int isExists();
    
    
    /**
     * 根据id组合获取流水组合
     */
    List<DgOpenWater> selectByIds(List ids);
    
    /**
     * 批量更新
     */
    int updateWaterList(List<DgOpenWater> src);

    /**
     * 根据结算ID，获取结算流水下的所有营业流水
     * @param clearingWaterId
     */
    List<DgOpenWater> selectOpenWaterbyCwId(@Param("clearingWaterId") Integer clearingWaterId);

    /**
     * 获取客座的信息
     * @param dgOpenWaters
     */
    List<DgConsumerSeat> selectSeatInfoBySeatIds(@Param("dgOpenWaters") List<DgOpenWater> dgOpenWaters);

    /**
     * 对营业流水状态进行初始化
     * @param dgOpenWaters
     */
    void resetOpenWaterState(@Param("dgOpenWaters") List<DgOpenWater> dgOpenWaters);

    /**
     * 根据营业流水获取所有的营业流水品项
     * @param dgOpenWaters
     */
    List<DgOwConsumerDetails> selectAllItemByOpenWaters(@Param("dgOpenWaters") List<DgOpenWater> dgOpenWaters);

    /**
     * 初始化品项明细表中的结算价格为null
     * @param dgOwConsumerDetailss
     */
    void resetItemFinalPayMoney(DgOwConsumerDetails dgOwConsumerDetailss);

    /**
     *删除优惠券品项
     * @param dgOwConsumerDetailss
     */
    void deleteOwConsumerDetail(DgOwConsumerDetails dgOwConsumerDetailss);

    /**
     * 修改结算流水的状态为返位结算
     * @param clearingWaterId
     */
    void updateClearingWaterState(@Param("clearingWaterId") Integer clearingWaterId);

    /**
     * 获取空闲的客座
     * @param dgOpenWaters
     */
    List<DgConsumerSeat> selectOpenWaterFreeSeat(@Param("dgOpenWaters") List<DgOpenWater> dgOpenWaters);

    /**
     * 修改客座为占用
     * @param dgConsumerSeats
     */
    void updateSeatBusy(@Param("dgConsumerSeats") List<DgConsumerSeat> dgConsumerSeats);

    /**
     * 查询所有的客座
     * @param param
     */
    List<DgConsumerSeat> selectAllSeat(Map param);

    /**
     * 查询出当前的所有正常状态/埋单/的营业流水
     * @param param
     */
    List<DgOpenWater> selectSeatCurrentOpenWater(Map param);

    /**
     * 根据桌位ID获取营业流水
     * @param seatId
     * @return
     */
    List<DgOpenWater> selectOpenWaterBySeatId(@Param("seatId") Integer seatId);

    /**
     * 当前营业情况未结的营业流水
     * @param param
     * @return
     */
    List<DgOpenWater> selectCurrentOpeningWater(Map param);
    //获取时间段之内的数据
    List<Map<String,Object>> getOWByDate(Map param);

    List<Map<String,Object>> getPCountAndCNum(Map param);

    void insertFwjsLog(@Param("id") Integer clearingWaterId);

    List<DgOwClearingway> selectSettlementInfoByCwId_new(Map param);

    List<DgOwConsumerDetails> selectConsumerDetailsByOwId_new(Map param);
}