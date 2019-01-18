package com.yqsh.diningsys.web.model.deskBusiness;

/**
 * 账单设置dto
 * Created by mrren on 2016/11/15.
 */
public class DBSBillServDTO {

    private String isForegiftInSettlement;           //押金参与结算
    private String isNoLimitDiscountScheme;            //品相打折方案 不受 允许打折品项限制
    private String isPrintVoucherToGetInvoice;         //开发票是打印领取发票的凭证
    private String isNoForceInputInvoiceNumberAndMoney; //开具发票是不强制要求输入发票号和金额
    private String isAllowNegativeNumberInQuotaDiscount; //定额优惠允许输入负数
    private String isQuotaDiscountAndNoSmallChangeApportionToItems;     //定额优惠和抹零分摊到品项上
    private String isLimitMemCardConsumeArea;              //是否启用会员卡消费区域限定功能
    private String isQuotaDiscountToItemsAsPossible;       //尽可能把定额优惠分摊到品项上
    private String isUploadConsumeDetailWhenUnpaid;        //会员挂账时向会员系统上传消费明细
    private String isNoServiceChargeNoSettlement;          //是否零服务费是无法结算
    private Double noServiceChargeOverMoney;              //消费满多少不收服务费
    private Double noRoomuseChargeOverMoney;               //消费满多少不收包房费
    private String isDiscountCardNoUsePromotionItems;      //会员打折卡不能对促销方案品项进行二次打折
    private String isServiceChargeNoDiscount;              //服务费不参与优惠
    private String isOnlyOffWork;                          //是否只进行结班操作
    private String isSendSmsOffWork;                       //结班是否发送短信
    private String resaveSmsNumber;                           //接受短信号码
    private String noSmallChangeWay;                        //摸零方法
    private String noSmallChangePlace;                      //摸零位置

    public String getIsForegiftInSettlement() {
        return isForegiftInSettlement;
    }

    public void setIsForegiftInSettlement(String isForegiftInSettlement) {
        this.isForegiftInSettlement = isForegiftInSettlement;
    }

    public String getIsNoLimitDiscountScheme() {
        return isNoLimitDiscountScheme;
    }

    public void setIsNoLimitDiscountScheme(String isNoLimitDiscountScheme) {
        this.isNoLimitDiscountScheme = isNoLimitDiscountScheme;
    }

    public String getIsPrintVoucherToGetInvoice() {
        return isPrintVoucherToGetInvoice;
    }

    public void setIsPrintVoucherToGetInvoice(String isPrintVoucherToGetInvoice) {
        this.isPrintVoucherToGetInvoice = isPrintVoucherToGetInvoice;
    }

    public String getIsNoForceInputInvoiceNumberAndMoney() {
        return isNoForceInputInvoiceNumberAndMoney;
    }

    public void setIsNoForceInputInvoiceNumberAndMoney(String isNoForceInputInvoiceNumberAndMoney) {
        this.isNoForceInputInvoiceNumberAndMoney = isNoForceInputInvoiceNumberAndMoney;
    }

    public String getIsAllowNegativeNumberInQuotaDiscount() {
        return isAllowNegativeNumberInQuotaDiscount;
    }

    public void setIsAllowNegativeNumberInQuotaDiscount(String isAllowNegativeNumberInQuotaDiscount) {
        this.isAllowNegativeNumberInQuotaDiscount = isAllowNegativeNumberInQuotaDiscount;
    }

    public String getIsQuotaDiscountAndNoSmallChangeApportionToItems() {
        return isQuotaDiscountAndNoSmallChangeApportionToItems;
    }

    public void setIsQuotaDiscountAndNoSmallChangeApportionToItems(String isQuotaDiscountAndNoSmallChangeApportionToItems) {
        this.isQuotaDiscountAndNoSmallChangeApportionToItems = isQuotaDiscountAndNoSmallChangeApportionToItems;
    }

    public String getIsLimitMemCardConsumeArea() {
        return isLimitMemCardConsumeArea;
    }

    public void setIsLimitMemCardConsumeArea(String isLimitMemCardConsumeArea) {
        this.isLimitMemCardConsumeArea = isLimitMemCardConsumeArea;
    }

    public String getIsQuotaDiscountToItemsAsPossible() {
        return isQuotaDiscountToItemsAsPossible;
    }

    public void setIsQuotaDiscountToItemsAsPossible(String isQuotaDiscountToItemsAsPossible) {
        this.isQuotaDiscountToItemsAsPossible = isQuotaDiscountToItemsAsPossible;
    }

    public String getIsUploadConsumeDetailWhenUnpaid() {
        return isUploadConsumeDetailWhenUnpaid;
    }

    public void setIsUploadConsumeDetailWhenUnpaid(String isUploadConsumeDetailWhenUnpaid) {
        this.isUploadConsumeDetailWhenUnpaid = isUploadConsumeDetailWhenUnpaid;
    }

    public String getIsNoServiceChargeNoSettlement() {
        return isNoServiceChargeNoSettlement;
    }

    public void setIsNoServiceChargeNoSettlement(String isNoServiceChargeNoSettlement) {
        this.isNoServiceChargeNoSettlement = isNoServiceChargeNoSettlement;
    }

    public Double getNoServiceChargeOverMoney() {
        return noServiceChargeOverMoney;
    }

    public void setNoServiceChargeOverMoney(Double noServiceChargeOverMoney) {
        this.noServiceChargeOverMoney = noServiceChargeOverMoney;
    }

    public Double getNoRoomuseChargeOverMoney() {
        return noRoomuseChargeOverMoney;
    }

    public void setNoRoomuseChargeOverMoney(Double noRoomuseChargeOverMoney) {
        this.noRoomuseChargeOverMoney = noRoomuseChargeOverMoney;
    }

    public String getIsDiscountCardNoUsePromotionItems() {
        return isDiscountCardNoUsePromotionItems;
    }

    public void setIsDiscountCardNoUsePromotionItems(String isDiscountCardNoUsePromotionItems) {
        this.isDiscountCardNoUsePromotionItems = isDiscountCardNoUsePromotionItems;
    }

    public String getIsServiceChargeNoDiscount() {
        return isServiceChargeNoDiscount;
    }

    public void setIsServiceChargeNoDiscount(String isServiceChargeNoDiscount) {
        this.isServiceChargeNoDiscount = isServiceChargeNoDiscount;
    }

    public String getIsOnlyOffWork() {
        return isOnlyOffWork;
    }

    public void setIsOnlyOffWork(String isOnlyOffWork) {
        this.isOnlyOffWork = isOnlyOffWork;
    }

    public String getIsSendSmsOffWork() {
        return isSendSmsOffWork;
    }

    public void setIsSendSmsOffWork(String isSendSmsOffWork) {
        this.isSendSmsOffWork = isSendSmsOffWork;
    }

    public String getResaveSmsNumber() {
        return resaveSmsNumber;
    }

    public void setResaveSmsNumber(String resaveSmsNumber) {
        this.resaveSmsNumber = resaveSmsNumber;
    }

    public String getNoSmallChangeWay() {
        return noSmallChangeWay;
    }

    public void setNoSmallChangeWay(String noSmallChangeWay) {
        this.noSmallChangeWay = noSmallChangeWay;
    }

    public String getNoSmallChangePlace() {
        return noSmallChangePlace;
    }

    public void setNoSmallChangePlace(String noSmallChangePlace) {
        this.noSmallChangePlace = noSmallChangePlace;
    }
}
