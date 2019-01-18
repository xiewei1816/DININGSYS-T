package com.yqsh.diningsys.web.dao.api;

import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwReceipt;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-12-30
 */
@Repository
public interface PaySettlementMapper {

    /**
     * 结算流水插入
     * 结算流水插入
     * @param param
     */
    void insertClearingWater(Map param);

    /**
     * 埋单时插入的结算流水数据
     * @param param
     */
    void insertAdvacePayClearingWater(Map param);

    /**
     * 更新加单品项的最终结算价格
     * @param map
     */
    void modifyItemFileAdd(Map<String, Object> map);

    /**
     * 更新减单品项的最终结算价格
     * @param map
     */
    void modifyItemFileSub(Map<String, Object> map);

    /**
     * 插入支付方式数据
     * @param param
     */
    void insertClearingWayData(Map param);

    /**
     * 修改营业流水的结算/埋单状态
     * @param param
     */
    void modifyOpenWaterState(Map param);

    /**
     * 更新客座状态
     * @param id
     */
    void modifySeatState(Map<String, Object> id);

    void modifySeatStateForMD(Map<String, Object> id);

    /**
     * 查询客位的营业流水
     * @param id
     */
    List<Map> selectSeatOpenWater(Integer id);

    /**
     * 插入发票数据
     * @param map
     */
    void insertInvoicing(Map<String, Object> map);

    /**
     * 埋单时对营业流水的埋单数据进行保存
     * @param map
     */
    void modifyOpenWaterAdvancePayInfo(Map<String, Object> map);

    /**
     * 撤销埋单，重置前台埋单时的相关数据
     * @param map
     */
    void modifyOpenWAterCancelAdvancePay(Map<String, Object> map);

    /**
     * 查询结算流水ID下的所有结算流水
     * @param map
     */
    List<DgOpenWater> selectOpenWaterByCwId(Map<String, Object> map);

    /**
     * 将结算流水的数据设置为作废
     * @param map
     */
    void modifyClearingWaterState(Map<String, Object> map);

    /**
     * 打折修改
     * @param map
     */
    void modifyItemFileDiscount(Map<String, Object> map);
    
    
    /**
     * 根据客座获取该客座下的当前所有非转账的埋单营业流水
     * @param map
     * @return
     */
    List<Map> selectAllPayOpenWaterBySeatId(Map<String, Object> map);

    /**
     * 获取所有的品项大类数据
     */
    List<DgItemFileType> selectAllItemBigType();

    /**
     * 删除埋单时的支付信息
     */
    void deletePayWayInfoByCwId(@Param("id") Integer id);

    /**
     * 清除埋单时可能存入的比例信息以及定额优惠信息
     * @param cwId
     */
    void updateProportionByCwId(@Param("id") Integer cwId);

    /**
     * 清除埋单时可能存入的开发票信息
     * @param cwId
     */
    void deleteInvoicingByCwId( @Param("id")Integer cwId);

    /**
     * 根据结算ID获取结算信息
     * @param clearingId
     */
    DgReceptionClearingWater selectClearingWaterById(@Param("id") Integer clearingId);

    /**
     * 根据结算ID获取所有支付信息
     * @param clearingId
     */
    List<Map> selectPayWayByCwId(@Param("id") Integer clearingId);

    /**
     * 根据结算ID获取所有发票信息
     * @param clearingId
     */
    List<DgOwReceipt> selectInvoicingByCwId(@Param("id") Integer clearingId);

    /**
     * 插入单条支付方式数据
     * @param param
     */
    void insertPayWay(Map<String,Object> param);

    DgReceptionClearingWater selectClearingWaterByCwNum(@Param("jsNum") String jsNum);

    void delTrashClearingWater(Map<String, Object> map);

    List<Integer> selectDistinctSeatId(List<DgOpenWater> dgOpenWaterNum);
}
