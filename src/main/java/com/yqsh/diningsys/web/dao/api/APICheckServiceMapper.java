package com.yqsh.diningsys.web.dao.api;

import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.model.deskBusiness.DgOwLockinfo;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 账单服务
 *
 * @author zhshuo create on 2016-12-06 10:10
 */
@Repository
public interface APICheckServiceMapper {

    /**
     * 检测客座号流水下是否有品项
     * @param param
     * @return
     */
    List<Map> selectSeatHasItem(Map param);

    /**
     * 获取该营业流水下面的所有品项<br>
     * 只包括服务类型自增品项、加单
     * @param param
     * @return
     */
    List<DgOwConsumerDetails> selectAllItemDataAddItem(Map param);

    /**
     * 获取该营业流水下面的所有品项<br>
     * 只包括服务类型自增品项、加单、赠单
     * @param param
     * @return
     */
    List<DgOwConsumerDetails> selectOpenWaterClearingWithService(Map<String, String> param);

    List<DgOwConsumerDetails> selectClearingItemFileInfosWithService(Map<String, String> param);

    /**
     * 获取该营业流水下面所有的品项<br>
     * 只包括服务类型为退单的数据
     * @param param
     * @return
     */
    List<DgOwConsumerDetails> selectAllItemDataReducedItem(Map param);

    List<DgOwConsumerDetails> selectClearingItemFileInfosTD(Map<String, String> param);

    /**
     * 获取该营业流水下面的所有品项，包括套餐下的具体品项<br>
     * 只包括服务类型自增品项、加单
     * @param param
     * @return
     */
    List<DgOwConsumerDetails> selectAllItemDataAddItemTC(Map param);

    /**
     * 获取该营业流水下面所有的品项，包括套餐下的具体品项<br>
     * 只包括服务类型为退单的数据
     * @param param
     * @return
     */
    List<DgOwConsumerDetails> selectAllItemDataReducedItemTC(Map param);

    /**
     * 根据客座号查询营业流水信息
     * @param param
     * @return
     */
    List<Map> selectOpenWaterBySeatId(Map param);

    /**
     * 根据营业流水查询营业流水信息
     * @param param
     * @return
     */
    Map selectOpenWaterByOwNUm(Map param);

    /**
     * 根据待转账的营业流水检测该营业流水下是否存在转账流水
     * @param param
     * @return
     */
    List<DgOpenWater> selectOpenWaterByTransferNum(Map param);

    /**
     * 根据团队号码获取除本身营业流水以外的团队成员
     * @param param
     * @return
     */
    List<Map> selectTeamMembersByTeamCode(Map param);

    /**
     * 营业流水进行转账操作
     * @param param
     */
    Integer openWaterTransfer(Map param);

    /**
     * 修改营业流水信息
     * @param param
     * @return
     */
    Integer modifyOpenWaterTeamInfo(Map param);

    /**
     * 当修改之后的品项数据为0，删除该服务单下的具体品项数据
     * @param splitOldItemNumber
     * @return
     */
    Integer delItemDetail(Map splitOldItemNumber);

    /**
     * 根据拆分品项的数据，创建先的服务单品项详细信息
     * @return
     */
    Integer createSplitItemData(DgOwConsumerDetails dgOwConsumerDetails);

    /**
     * 创建退单的具体品项信息
     * @param dgOwConsumerDetails
     * @return
     */
    Integer insertChargeback(DgOwConsumerDetails dgOwConsumerDetails);

    /**
     * 根据客位，获取该客位有效的已结流水
     * @param id
     * @return
     */
    List<Map> selectSeatClosedWater(Integer id);

    /**
     * 挂S账
     * @param param
     * @return
     */
    Integer hangingSBill(Map param);

    /**
     * 更改客位状态
     * @param param
     * @return
     */
    Integer setSeatState(Map param);

    /**
     * 查看所有的S账
     * @return
     */
    List<DgOpenWater> selectAllSBill();

    /**
     * 撤销S帐
     * @return
     */
    Integer modifyCancelSBill(Map param);

    /**
     * 根据座位查询待关账的营业流水
     * @param params
     * @return
     */
	List<Map<String, Object>> selectCloseBillOpenWater(Map<String,Object> params);

	/**
	 * 判断当前营业流水状态
	 * @param params
	 * @return
	 */
	Map<String, Object> checkCloseBillOpenWater(Map<String, Object> params);

	/**
	 * 判断当前营业流水押金状态
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> checkCashPledgeOpenWater(Map<String, Object> params);

	/**
	 * 设置流水号为关账状态
	 * @param params
	 * @return
	 */
	int closeBillForOpenWater(Map<String, Object> params);

	/**
	 * 设置客位为空闲状态
	 * @param params
	 * @return
	 */
	int closeBillForSeat(Map<String, Object> params);

    /**
     * 删除没有用的服务数据
     * @param id
     */
	void delUnUseServieData(Integer id);

    /**
     * 根据服务ID和品项ID获取品项信息
     * @return
     */
	DgOwConsumerDetails selectDataByServiceIdAndOwId(Map param);

    /**
     * 根据服务ID和品项ID获取品项的制作方法数据
     * @param param
     * @return
     */
	List<DgOwDetailsOther> selectDetailOtherInfo(Map param);

    /**
     * 批量插入品项的其他信息
     * @param param
     * @return
     */
	void insertMultiOtherInfo(Map param);

    /**
     * 根据品项ID和服务ID查询出该品项所属的营业流水信息
     * @param param
     */
	 DgOpenWater selectOpenWaterByItemAndServiceId(Map param);

    /**
     * 设置品项为赠送品项
     * @param param
     */
	 void setDetailFree(DgOwConsumerDetails param);

    /**
     * 取消设置品项为赠送品项
     * @param param
     */
	 void cancelDetailFree(DgOwConsumerDetails param);

    /**
     * 根据营业流水获取该营业流水下的所有品项信息<br>
     * 包含了向该客位转账的营业流水数据<br>
     * @param map
     */
    List<DgOpenWater> selectAllOpenWaterNum(Map<String, Object> map);

    /**
     * 查询出条件营业流水的团队成员以及向条件营业流水转账的集合数据
     * @param openWater
     */
    List<DgOpenWater> selectAllOpenWaterByOwNum(Map openWater);

    /**
     * 获取所有的支付方式
     * @return
     * @param list
     */
    List<DgSettlementWay> selectAllPayWay(@Param("list") List<String> list);

    /**
     * 查询今天是否是重要活动
     */
    List<Map> selectIsZyhd();

    /**
     * 获取营业流水下的有效品项
     * @param param
     * @return
     */
    List<DgOwConsumerDetails> selectFreeItemDataAddItem(Map param);

    /**
     * 营业流水会员绑定
     * @param map
     */
    void modifyBingdingMember(Map<String, Object> map);

    /**
     * 营业流水锁定信息
     * @param param
     * @return
     */
    DgOwLockinfo selectOpenWaterLock(Map param);

    /**
     * 循环修改营业流水的状态为结算锁定/解锁
     * @param maps
     */
    void modifyOpenWaterLock(Map<String, Object> maps);

    /**
     * 插入锁定的日志，埋单、手工锁定、结算锁定
     * @param maps
     */
    void insertLockLog(Map<String, Object> maps);

    /**
     * 查询出所有允许打折的品项数据
     */
    List<DgItemFile> selectAllCanDiscountItemData();

    /**
     * 埋单的营业流水再次埋单，修改埋单的数据
     * @param param
     */
    void updateAdvancePayClearingWaterDataById(Map<String,Object> param);


    /**
     * 获取所有的押金信息
     * @param dgOpenWaters
     */
    List<DgCashPledge> selectAllDepositByOwNums(@Param("dgOpenWaters") List<DgOpenWater> dgOpenWaters);

    /**
     * 查询用户的登录班次信息
     * @param userCode
     */
    Map selectUserLoginInfo(Map<String,Object> params);
    
    /**
     * 查询前班数据
     * @param loginTime
     * @return
     */
	Map selectLastUserLoginInfo(@Param("loginTime") String loginTime);

    /**
     * 结班的时查询查询的结算方式数据
     */
    DgOwClearingway selectFrequency(Map<String,Object> param);

    /**
     * 插入优惠信息
     * @param map
     */
    void insertDiscountData(Map<String, Object> map);

    /**
     * 获取用户名称
     * @param userCode
     * @return
     */
    String selectUserName(@Param("userCode") String userCode);

    /**
     * 查询会员挂账数据
     * @param param
     */
    List<Map> selectMembersCredit(Map<String,Object> param);

    /**
     * 品项报告
     * @param param
     * @return
     */
    List<DgOwConsumerDetails> selectItemReport(Map<String, Object> param);

    /**
     * 结班报表开单人数以及开台总数
     * @param map
     * @return
     */
    Map selectBilingData(Map<String, Object> map);

    /**
     * 结班报表未结台数以及未结金额
     * @param map
     * @return
     */
    Map selectOpenData(Map<String, Object> map);

    /**
     * 查询未退押金数据
     * @param map
     * @return
     */
    Map selectWTDepositData(Map<String, Object> map);

    /**
     * 查询已结台数以及已结人数
     * @param map
     */
    Map selectCloseData(Map<String, Object> map);

    /**
     * 查询本班赠单金额
     * @param map
     */
    Map selectFreeData(Map<String, Object> map);

    /**
     * 本班退单金额
     * @param map
     */
    Map selectBackData(Map<String, Object> map);

    /**
     * 本班S账金额
     * @param map
     * @return
     */
	Map selectSbillData(Map<String, Object> map);
	
    /**
     * 小类报告
     * @param map
     */
    List<Map> selectSmallTypeReport(Map<String, Object> map);

    /**
     * 大类报告
     * @param map
     * @return
     */
    List<Map> selectBigTypeReport(Map<String, Object> map);

    /**
     * 获取消费金额、抹零金额等信息
     * @param map
     */
    Map selectClearingWaterMoney(Map<String, Object> map);

    /**
     * 获取包房费、最低消费补齐、服务费等
     * @param map
     * @return
     */
    List<Map> selectOpenWaterMoney(Map<String, Object> map);

    /**
     * 结班操作
     * @param map
     */
    void modifyOpenClassWater(Map<String, Object> map);
    
    /**
     * 用于平板查询最后一条流水(初始化/埋单/手工锁定 状态)
     * @param param
     * @return
     */
    Map selectOpenWaterBySeatIdLastOne(Map param);

    Map selectDepostByOwNum(@Param("owNum") String owNum);

    DgOpenWater selectOpenWaterByowNum(@Param("openWaterNumber") String openWaterNumber);

    /**
     * 批量修改营业流水客用单打印次数回调
     * @param id
     */
    void modifyKydPrintAjax(Integer id);

    /**
     * 批量修改营业流水预结单打印次数回调
     * @param id
     */
    void modifyYjdPrintAjax(Integer id);

    /**
     * 修改结账单打印次数回调
     * @param clearingWaterId
     */
    void modifyJzdPrintAjax(@Param("id") Integer clearingWaterId);


    /**
     * 查询结算的营业流水是否已经被结算
     * @param dgOpenWaters
     * @return
     */
    List<DgOpenWater> selectAllOpenWaterIsClosed(@Param("dgOpenWaters") List<DgOpenWater> dgOpenWaters);

    DgReceptionClearingWater selectClearingWaterByNum(@Param("clearingWater") String clearingWater);

    List<DgOpenWater> selectopenWatersByClearingWaterId(@Param("id") Integer id);

    /**
     * 续单
     * @param id
     */
    void modifyContinuedCheck(@Param("id") Integer id);

    /**
     * 续单操作，修改客座
     * @param seatId
     */
    void modifyContinuedSeatState(@Param("seatId") Integer seatId);

    /**
     * 续单操作，修改结算流水的状态4
     * @param id
     */
    void modifyContinuedClearingState(@Param("id") Integer id);

    List<DgOpenWater> selectAllDepositByStringOwNums(@Param("list") List<String> list);

    DgConsumerSeat checkSeatState(@Param("transferOwNum") String transferOwNum);

    List<DgCashPledge> selectAllDepositByOwNum(@Param("owNum") String owNum);

    /**
     * 更新优惠金额
     * @param dgOpenWater
     * @return
     */
	int updateDiscountCostsByPrimaryKey(DgOpenWater dgOpenWater);

	Map selectLastUserLoginInfo(Object object);

    DgOpenWater selectTargetOpenWater(@Param("targetOwNum") String targetOwNum);

    void updateOpenWaterTransfer(Map param);

    DgOpenWater selectTransferOtherOwNum(@Param("seatId") Integer seatId);

    void updateSeatIdle(@Param("seatId") Integer seatId);

    void updateOpenWatetSeatAmount(Map<String,Object> param);

    void updateOpenWatetTeamState(@Param("targetOwNum") String targetOwNum);

    DgOpenWater selectOtherFirstTeamOwNum(DgOpenWater transferOwNum);

    void updateTeamMainSeatByTeamMembers(Map<String, Object> map);

    void updateOpenWaterTransferTeamMember(Map<String, Object> map);

    DgReceptionClearingWater selectClearingWaterById(@Param("clearingWaterId") Integer clearingWaterId);

    List<DgOpenWater> selectOpenWaterByCwId(@Param("clearingWaterId") Integer clearingWaterId);

    List<DgOwClearingway> selectClearingWayByCwId(@Param("clearingWaterId") Integer clearingWaterId);

    DgOpenWater selectOpenWaterObjByOwNum(@Param("owNum") String owNum);

    List<DgOpenWater> selectOpenWaterObjBySeatIdAndTeamNum(Map<String,Object> param);

    List<DgOwConsumerDetails> selectBackBillDetailInfoByAddBillInfo(@Param("list") List<DgOwConsumerDetails> itemFileInfos);

    List<DgOpenWater> selectOpenwaterByTeamNum(@Param("teamMembers") String teamMembers);

    List<DgReceptionClearingWater> selectClearingWaterByTime(Map<String, Object> map);

    Integer selectWaterBySeatId(@Param("seatId") Integer seatId);

    List<DgOpenWater> selectINGOpenWaters();

    DgOwDiscount selectYhxx(@Param("clearingWaterId") int clearingWaterId);

    void updateOpenWaterSeat(Map<String, Object> map);

    void modifyOpenWaterSeat(Map param);

    void selectopenwaterBySeat(@Param("seatId") Integer seatId);

    List<DgOpenWater> selectOpenWaterBySeatIdAndTeamNum(Map param);

    void updateDetailInfo(DgOwConsumerDetails dgOwConsumerDetails1);

    void insertFreeDetailInfo(DgOwConsumerDetails dgOwConsumerDetails);

    void updateOpenWaterMainSeat(Map<String, Object> map);

    List<DgOpenWater> selectTransferOpenWaterByOwNum(@Param("owNum") String owNum);

    List<Map> selectClosedWater(Map<String, Object> map);

    List<DgOwConsumerDetails> selectOpenWaterClearing(@Param("owNum") String owNum);

    List<DgOpenWater> lockPayingWaters(@Param("list") List<DgOpenWater> owNums);

    List<DgOwConsumerDetails> selectClearingAllItemFileInfos(Map<String, String> param);

    List<DgOwConsumerDetails> selectAllItemDataByOwNum(Map<String, String> param);
    
    
    List<DgOpenWater> selectOpenWaterByTeamNum(Map param);

    void modifyOpenWaterTeamMainSeat(Map param);

    DgReceptionClearingWater selectClearingWaterById_new(Map<String,Object> param);

    List<DgOwClearingway> selectClearingWayByCwId_new(Map<String, Object> map);

    List<DgOpenWater> selectOpenWaterByCwId_new(Map<String, Object> map);

    List<DgOwConsumerDetails> selectClearingAllItemFileInfos_new(Map<String, Object> param);

    DgSettlementWay selectSettleWayInfoById(@Param("wayId") Integer wayId);
    
    List<DgItemFile> selectWxAllCanDiscountItemData(@Param("lists")List<String> list);

    Map selectOpenClassWaterWithId(Integer id);

    List<Map> selectOpenClassWaterHistory(Map<String, Object> map);

    int selectOpenClassWaterHistoryCount(Map<String, Object> map);

    List<DgOwConsumerPackageDetails> selectPackageBxInfo(@Param("id") String id);

    int reductionPriceForAdd(DgOpenWater dgOpenWaters);

    int reductionPriceForGift(DgOpenWater dgOpenWaters);

    void updateWaterSubtotal(@Param("subtotal") Double subtotal,@Param("id") int id);
}