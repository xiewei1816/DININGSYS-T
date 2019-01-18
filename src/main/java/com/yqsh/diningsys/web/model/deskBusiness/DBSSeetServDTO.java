package com.yqsh.diningsys.web.model.deskBusiness;

/**
 * 客座设置dto
 * Created by mrren on 2016/11/15.
 */
public class DBSSeetServDTO {


    private String isCanModifyServerMan;            //加单，退单允许修改点单员
    private String isManualInputOrderNum;                          //加单，退单必须输入手工单号
    private String defaultWaiter;                                  //加单时默认点单员
    private String isUnsubscribeReasonNeed;                        //退单时必须填写退单原因
    private String isUseGiftOrderReason;                           //使用赠单原因
    private String isUseGiftOrderReasonNeed;                       //必须使用赠单原因
    private String isRetainRoomReserve;                            //内部留房允许开台，预定
    private String isNewOrderNeedForegift;                         //开单使用押金
    private String isReserveForegiftToDeposit;                     //预定时订金转押金
    private String isAutoShowBusinessInfo;                         //是否鼠标点击客位显示营业信息
    private String isAutoInsertDeskLabelToSettlementRemarks;       //结算时是否将前台客位标签自动插入到结算备注
    private String staffCardAuthorizType;                          //员工卡授权类型
    private String isAllowManualInputAuthorizNumber;               //允许手工输入授权卡号
    private String isGetPosNumberByIp;                             //通过ip地址获取pos号
    private String isOnlyAllowModifyIntradayData;                  //只允许修改当日盘点数量
    private String isChangeSeatOrServerCorrespond;                 //更改客位或服务员，使用服务员服务客位中对应的客位和服务员
    private String isNormalMealMustOptionsNumber;                  //是否需要填写修改常规套餐必选项的数量

    public String getIsCanModifyServerMan() {
        return isCanModifyServerMan;
    }

    public void setIsCanModifyServerMan(String isCanModifyServerMan) {
        this.isCanModifyServerMan = isCanModifyServerMan;
    }

    public String getIsManualInputOrderNum() {
        return isManualInputOrderNum;
    }

    public void setIsManualInputOrderNum(String isManualInputOrderNum) {
        this.isManualInputOrderNum = isManualInputOrderNum;
    }

    public String getIsUnsubscribeReasonNeed() {
        return isUnsubscribeReasonNeed;
    }

    public void setIsUnsubscribeReasonNeed(String isUnsubscribeReasonNeed) {
        this.isUnsubscribeReasonNeed = isUnsubscribeReasonNeed;
    }

    public String getIsUseGiftOrderReason() {
        return isUseGiftOrderReason;
    }

    public void setIsUseGiftOrderReason(String isUseGiftOrderReason) {
        this.isUseGiftOrderReason = isUseGiftOrderReason;
    }

    public String getIsUseGiftOrderReasonNeed() {
        return isUseGiftOrderReasonNeed;
    }

    public void setIsUseGiftOrderReasonNeed(String isUseGiftOrderReasonNeed) {
        this.isUseGiftOrderReasonNeed = isUseGiftOrderReasonNeed;
    }

    public String getIsRetainRoomReserve() {
        return isRetainRoomReserve;
    }

    public void setIsRetainRoomReserve(String isRetainRoomReserve) {
        this.isRetainRoomReserve = isRetainRoomReserve;
    }

    public String getIsNewOrderNeedForegift() {
        return isNewOrderNeedForegift;
    }

    public void setIsNewOrderNeedForegift(String isNewOrderNeedForegift) {
        this.isNewOrderNeedForegift = isNewOrderNeedForegift;
    }

    public String getIsReserveForegiftToDeposit() {
        return isReserveForegiftToDeposit;
    }

    public void setIsReserveForegiftToDeposit(String isReserveForegiftToDeposit) {
        this.isReserveForegiftToDeposit = isReserveForegiftToDeposit;
    }

    public String getIsAutoShowBusinessInfo() {
        return isAutoShowBusinessInfo;
    }

    public void setIsAutoShowBusinessInfo(String isAutoShowBusinessInfo) {
        this.isAutoShowBusinessInfo = isAutoShowBusinessInfo;
    }

    public String getIsAutoInsertDeskLabelToSettlementRemarks() {
        return isAutoInsertDeskLabelToSettlementRemarks;
    }

    public void setIsAutoInsertDeskLabelToSettlementRemarks(String isAutoInsertDeskLabelToSettlementRemarks) {
        this.isAutoInsertDeskLabelToSettlementRemarks = isAutoInsertDeskLabelToSettlementRemarks;
    }

    public String getStaffCardAuthorizType() {
        return staffCardAuthorizType;
    }

    public void setStaffCardAuthorizType(String staffCardAuthorizType) {
        this.staffCardAuthorizType = staffCardAuthorizType;
    }

    public String getIsAllowManualInputAuthorizNumber() {
        return isAllowManualInputAuthorizNumber;
    }

    public void setIsAllowManualInputAuthorizNumber(String isAllowManualInputAuthorizNumber) {
        this.isAllowManualInputAuthorizNumber = isAllowManualInputAuthorizNumber;
    }

    public String getIsGetPosNumberByIp() {
        return isGetPosNumberByIp;
    }

    public void setIsGetPosNumberByIp(String isGetPosNumberByIp) {
        this.isGetPosNumberByIp = isGetPosNumberByIp;
    }

    public String getIsOnlyAllowModifyIntradayData() {
        return isOnlyAllowModifyIntradayData;
    }

    public void setIsOnlyAllowModifyIntradayData(String isOnlyAllowModifyIntradayData) {
        this.isOnlyAllowModifyIntradayData = isOnlyAllowModifyIntradayData;
    }

    public String getIsChangeSeatOrServerCorrespond() {
        return isChangeSeatOrServerCorrespond;
    }

    public void setIsChangeSeatOrServerCorrespond(String isChangeSeatOrServerCorrespond) {
        this.isChangeSeatOrServerCorrespond = isChangeSeatOrServerCorrespond;
    }

    public String getIsNormalMealMustOptionsNumber() {
        return isNormalMealMustOptionsNumber;
    }

    public void setIsNormalMealMustOptionsNumber(String isNormalMealMustOptionsNumber) {
        this.isNormalMealMustOptionsNumber = isNormalMealMustOptionsNumber;
    }

    public String getDefaultWaiter() {
        return defaultWaiter;
    }

    public void setDefaultWaiter(String defaultWaiter) {
        this.defaultWaiter = defaultWaiter;
    }
}
