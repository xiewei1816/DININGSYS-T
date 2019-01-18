package com.yqsh.diningsys.web.service.api;

import com.yqsh.diningsys.api.model.VariablePrice;
import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.model.deskBusiness.DBSBillServDTO;
import com.yqsh.diningsys.web.model.deskBusiness.DgOwLockinfo;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;

import java.util.List;
import java.util.Map;

/**
 * 接口账单服务
 *
 * @author zhshuo create on 2016-12-06 10:07
 */
public interface APICheckService {

    /**
     * 查询该营业流水下的有效品项，不包括赠送的品项，按照品项汇总
     * @param owNum
     * @return
     */
    List<Map> selectSeatHasItem(String owNum);

    /**
     * 查询该营业流水下的有效品项，不包括赠送的品项，按照服务单汇总
     * @param owNum
     * @return
     */
    List<DgOwConsumerDetails> selectOpenWaterWithService(String owNum);

    /**
     * 查询该营业流水下的有效品项，包括赠送的品项，按照服务单汇总
     * @param owNum
     * @return
     */
    List<DgOwConsumerDetails> selectOpenWaterClearingWithService(String owNum,Integer isCategory);

    /**
     * 查询该营业流水下所有的赠送品项，按服务单汇总
     * @param owNum
     * @return
     */
    List<DgOwConsumerDetails> selectOpenWaterFreeWithService(String owNum);

    /**
     * 查询该营业流水下的有效品项，不包括赠送的品项，按照服务单汇总，包括套餐下的具体品项
     * @param owNum
     * @return
     */
    List<DgOwConsumerDetails> selectOpenWaterWithServiceTC(String owNum);

    /**
     * 根据客座号获取该客座的当前所有其营业流水信息
     * @return
     */
    List<Map> selectOpenWaterBySeatId(String seatId);

    /**
     * 检测该营业流水的状态
     * @return
     */
     Map selectOpenWaterByOwNum(String owNum);

     DgOpenWater selectOpenWaterObjByOwNum(String owNum);

    /**
     * 检测需要转账的营业流水是否已经有转账流水
     * @return
     */
    List<DgOpenWater> selectOpenWaterByTransferNum(String owNum);

    /**
     * 根据团队号码获取除本身营业流水以外的团队成员
     * @param teamCode
     * @return
     */
    List<Map> selectTeamMembersByTeamCode(String teamCode,String owNum);

    /**
     * 进行转账操作
     * @param transferOwNum
     * @param targetOwNum
     * @param opFlag
     * @param transferWaters
     */
    void updateOpenWaterTransfer(String userCode,String transferOwNum,String targetOwNum,Integer opFlag,List<DgOpenWater> transferWaters);
    
    /**
     * 根据客户端的拆账数据创建营业流水
     * @param userCode 用户
     * @param dgOwConsumerDetailss 该营业流水下面所有能进行拆账的流水
     * @param owNum 营业流水ID
     * @param openPos POS
     * @param token TOKEN
     * @param splitOpenWaterInfos 拆账的数据
     */
    void createOpenWaterWithSplit(String userCode,List<DgOwConsumerDetails> dgOwConsumerDetailss,String owNum,String openPos,String token,List<List<VariablePrice>> splitOpenWaterInfos);

    /**
     * 根据客户端的拆分品项数据进行营业流水拆分品项操作
     * @param owNum
     * @param token
     * @param splitOpenWaterInfo 拆分品项的数据
     */
    void editServiceItemData(String owNum,String token,List<Map> splitOpenWaterInfo);

    /**
     * 根据客位检测该客位是否存在有效的接单流水数据
     * @param seatId 客位ID
     * @return List<Map>
     */
    List<Map> selectSeatClosedWater(Integer seatId);

    /**
     * 续单操作
     * @param clearingCode 需要续单的结算流水号码
     * @return
     */
    void modifyContinuedCheck(String clearingCode);

    /**
     * 挂S账操作
     * @param owNum
     * @return
     */
    void modifyHangingSBill(String userCode,String owNum);

    /**
     * 查看所有的S账
     * @return
     */
    List<DgOpenWater> selectAllSBill();

    /**
     * 撤销S帐
     * @param owNum
     * @return
     */
    void modifyCancelSBill(String  userCode,String owNum);

    /**
     * 结班操作
     * @param userCode
     * @param loginPos
     * @return
     */
    Object selectOpenClassInfo(String userCode, Integer loginPos,Integer type);

    /**
     * 查询结班报表
     * @return
     */
    Map selectOpenClassReport(String userCode, Integer loginPos);

    /**
     * 根据座位查询待关账的营业流水
     * @param seatId
     * @return
     */
	List<Map<String, Object>> selectCloseBillOpenWater(Integer seatId);

	/**
	 * 判断当前营业流水状态
	 * @param owNum
	 * @return
	 */
	Map<String, Object> checkCloseBillOpenWater(String owNum);

	/**
	 * 判断当前营业流水押金状态
	 * @param owNum
	 * @return
	 */
	List<Map<String, Object>> checkCashPledgeOpenWater(String cpType,String owNum);

	/**
	 * 设置流水号为关账状态
	 * @param owState
	 * @param owNum
	 * @return
	 */
	int closeBillForOpenWater(String userCode,Integer owState,String owNum);

	/**
	 * 设置客位为空闲状态
	 * @param seatState
	 * @param seatId
	 */
	int closeBillForSeat(Integer seatState,String seatId);

    /**
     * 根据营业流水获取该营业流水下的所有品项信息,包含了向该客位转账的营业流水数据
     * @param owNum
     */
    Map selectAllDetails(String owNum);

    /**
     * 根据前台传入的营业流水获取到相关的团队成员以及向该营业流水转账的数据集合
     * @param owNum
     * @return
     */
    List<DgOpenWater> selectAllOpenWaterByOwNum(String owNum);

    /**
     * 获取所有的支付方式
     * @return
     * @param userCode
     */
    List<DgSettlementWay> selectAllPayWay(String userCode);

    /**
     *  得到品项的重要活动价格/品项打折价格/会员价格
     * @return
     */
    void getOpenWaterTotalPrice(DgOwConsumerDetails dgOwConsumerDetails, String hyLevelId, Integer orgId, Double generalProportions, Double singleProportions);

    /**
     * 单独得到会员的价格信息
     * @param dgOwConsumerDetails
     * @param orgId
     */
    void getOpenWaterHyPrice(DgOwConsumerDetails dgOwConsumerDetails, String hyLevelId,Integer orgId);

    /**
     * 获取该营业流水下面的其他费用，服务费，包房费，最低消费等
     * @param dbsBillServDTO
     * @param dgOpenWater
     * @param type hy-会员 px-品项打折 hd-活动
     */
    Double getOtherCost(DBSBillServDTO dbsBillServDTO, DgOpenWater dgOpenWater, String type);

    /**
     * 查询今天是否存在重要活动<br>
     */
    List<Map> selectIsZyhd();

    /**
     * 营业流水绑定会员
     * @param owNum
     * @param crId
     */
    void modifyBingdingMember(String owNum, String crId);

    /**
     * 根据营业流水json数组，判断里面的所有营业流水是否存在有效品项
     * @param owNums
     */
    Integer modifyCheckOwNumHasItem(String owNums);

    Integer modifyCheckOwNumHasItemWithOutJson(String owNum);

    /**
     * 设置品项锁定操作
     * @param owNums
     * @param userCode
     * @param type
     * @param pos
     */
    void modifyOpenWaterLock(String owNums,String userCode,Integer type,Integer pos);


    void modifyOpenWaterLock(List<DgOpenWater> dgOpenWaters, String userCode, Integer type, Integer pos);

    /**
     * 锁定信息
     * @param owNum
     * @return
     */
    DgOwLockinfo selectOpenWaterLock(String owNum);

    /**
     * 后台计算常规优惠比例以及全单比例
     * @param openWaterData
     * @param proportion
     * @param type
     */
    List<DgOpenWater> modifyPercentageDiscount(String openWaterData, Double proportion, Integer type);
    
    
    /**
     * 后台计算常规优惠比例以及全单比例(新)
     * @param openWaterData
     * @param proportion
     * @param type
     */
    List<DgOpenWater> newModifyPercentageDiscount(List<DgOpenWater> openWaters, DBSBillServDTO dbsBillServDTO,Double proportion, Integer type);

    /**
     * 获取该品项的制作费用信息
     * @param itemFileId
     * @param serviceId
     * @return
     */
    List<DgOwDetailsOther> selectDetailOtherInfo(Integer itemFileId, Integer serviceId);

    /**
     * 计算优惠券的价格
     * @param map1 优惠券列表
     * @param subtract 会员应收金额
     * @param dgOpenWaters 具体的品项信息
     */
    List<Map> modifyCouponMoney(List<Map> map1, Double subtract, List<DgOpenWater> dgOpenWaters);

    /**
     * 计算优惠券的价格
     * @param map1 优惠券列表
     * @param subtract 会员应收金额
     * @param dgOpenWater 具体的品项信息
     */
    List<Map> modifyCouponMoney(List<Map> map1, Double subtract, DgOpenWater dgOpenWater);

    /**
     * 结班操作
     * @param userCode
     * @param loginPos
     */
    void openClassModify(String userCode, Integer loginPos);

    /**
     * 检测营业流水是否存在押金
     * @param s
     * @return
     */
    Integer modifyCheckOwNumHasDeposit(String s);

    /**
     * 前台打印客用单的回调函数，修改客用单打印次数
     * @param openWaterIds
     */
    void modifyKydPrintAjax(String openWaterIds);

    /**
     * 前台打印预结单的回调函数，修改预结单打印次数
     * @param openWaterIds
     */
    void modifyYjdPrintAjax(String openWaterIds);

    /**
     * 前台打印结账单的回调函数，修改结账单打印次数
     * @param clearingWaterId
     */
    void modifyJzdPrintAjax(Integer clearingWaterId);
    
    Map modifyOnlineYDValidate(String id,String validateCode);

    List<DgOpenWater> selectAllOpenWaterIsClosed(List<DgOpenWater> dgOpenWaters);

    /**
     * 获取单个营业流水信息
     * @param owNum
     * @return
     */
    DgOpenWater selectSingleOpenWaterByOwNum(String owNum);

    DgReceptionClearingWater selectClearingWaterById(Integer clearingWaterId);

    List<DgOpenWater> selectOpenWaterByCwId(Integer clearingWaterId);

    List<DgOwClearingway> selectClearingWayByCwId(Integer clearingWaterId);

    List<DgOwConsumerDetails> selectClearingItemFileInfos(String owNum,Integer isCategory);

    List<DgOpenWater> selectOpenWaterObjBySeatIdAndTeamNum(Map<String,Object> param);

    List<DgOwConsumerDetails> selectBackBillDetailInfoByAddBillInfo(List<DgOwConsumerDetails> itemFileInfos);

    List<DgReceptionClearingWater> selectClearingWaterByTime(String queryTime);

    List<DgOpenWater> selectINGOpenWaters();

    DgOwDiscount selectYhxx(int clearingWaterId);

    List<DgOpenWater> selectTransferOpenWaterByOwNum(String owNum);

    List<Map> selectClosedWater(String beginTime,String endTime, Integer sortType);

    List<DgOwConsumerDetails> selectOpenWaterClearing(String owNum);

    DgReceptionClearingWater selectClearingWaterById_new(Integer clearingWaterId, Boolean check,String suffix);

    List<DgOwClearingway> selectClearingWayByCwId_new(Integer clearingWaterId, Boolean check,String suffix);

    List<DgOpenWater> selectOpenWaterByCwId_new(Integer clearingWaterId, Boolean check,String suffix);

    List<DgOwConsumerDetails> selectClearingItemFileInfos_new(String owNum,Boolean check, String suffix,Integer isCategory);

    DgSettlementWay selectSettleWayInfoById(Integer wayId);

    Double wxGetItemFileTypeTotal(List<DgOpenWater> dgOpenWaters,Integer purpose);

    Map getHyPayVailCode(String mobile);
    
    //卡券百分比打折
    List<DgOpenWater> wxModifyPercentageDiscount(List<DgOpenWater> dgOpenWaters,Double minimum, Double Denomination, Integer purpose, Integer type,Map couponInfo);


    //2018年6月22日 结班流水历史记录查询
    Map selectOpenClassWaterHistory(int pageSize, int pageNum, String time, String userName, int posId);

    Map selectOpenClassReportWithId(Integer id);

    Object selectOpenClassInfo(Map loginInfo, int i);

    void reductionPrice(String dgOpenWaters);

    void updateOpenWaterSubtotal(DgOpenWater dow);
}
