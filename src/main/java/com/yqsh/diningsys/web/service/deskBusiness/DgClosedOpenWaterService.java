package com.yqsh.diningsys.web.service.deskBusiness;

import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceipt;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;

import java.util.List;
import java.util.Map;

/**
 * 已结账单service
 *
 * @author zhshuo create on 2016-11-23 下午2:50
 */
public interface DgClosedOpenWaterService {

    /**
     * 获取所有的已结账单
     * @param date
     * @return
     */
    List<Map> selectClosedOpenWater(String date,String clientSeat,String bis,String expArea,String pos,String choiceCode,String code);

    /**
     * 获取已结账单的具体信息
     * @param id
     * @param time
     * @return
     */
    DgReceptionClearingWater selectCwInfoById(Integer id, String time);

    /**
     * 获取某已结账单下的所有营业流水
     * @param id
     * @param time
     * @return
     */
    List<DgOpenWater> selectOwInfoByCwId(Integer id, String time);

    /**
     * 获取所有的发票信息
     * @return
     */
    List<DgReceipt> selectAllReceipt();

    /**
     * 补开发票
     * @param tableJsonData
     * @param cwId
     * @param time
     */
    void editCwInvoice(String tableJsonData, Integer cwId, String time);

    /**
     * 常用结算
     * @return
     */
    List<DgPublicCode> selectAllCommonWay();

    /**
     * 其他结算
     * @return
     */
    List<DgPublicCode> selectAllOtherWay();

    /**
     * 付款修正
     */
    void updateClearingWay(String tableJsonData,Integer cwId,Double ss,Double zl);

    /**
     * 返位结算
     * @param authCode
     * @param dgOpenWaters
     * @param clearingWaterId
     */
    String modifySettlement(SysUser authUser, String deskUserCode, String authCode, List<DgOpenWater> dgOpenWaters, DgReceptionClearingWater dgReceptionClearingWater);

    /**
     * 根据结算流水ID获取所又营业流水
     * @param clearingWaterId
     * @return
     */
    List<DgOpenWater> selectOpenWaterbyCwId(Integer clearingWaterId);

    /**
     *
     * @param dgOpenWaters
     */
    List<DgConsumerSeat> selectSeatInfoBySeatIds(List<DgOpenWater> dgOpenWaters);
}
