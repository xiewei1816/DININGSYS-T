package com.yqsh.diningsys.web.model.deskBusiness;

/**
 * 打印设置dto
 * Created by mrren on 2016/11/15.
 */
public class DBSPrinterSettingDTO {
    private String settlementPrintControll;                    //结算打印控制
    private String title;
    private String mobile;
    private String address;
    private String slogan;
    private String isNoPrintParallelPortAddOrder;              //是否允许打印并口加单
    private String isNoPrintParallelPortChargeback;            //是否允许打印并口退单
    private String isNoPrintParallelPortGiftOrder;             //是否允许打印并口赠单
    private String isPrintBelongSetmealInOneMealOneOrder;    //一菜一单打印明细是，是否打印所属套餐
    private String isPrintBelongSetmealInSetmealMessage;     //打印套餐明细时同时打印所属套餐
    private String isEnablePromptBeforePrintCustomeTicketAndCheckBill;          //是否启用提示当打印客用单和买单结账之前，启用提示功能
    private String promptMessage;                               //提示信息
    private String isReprintServiceFlowInBillWindow;            //是否允许重新打印已经打印过的服务流水，在核对单据窗口

    public String getSettlementPrintControll() {
        return settlementPrintControll;
    }

    public void setSettlementPrintControll(String settlementPrintControll) {
        this.settlementPrintControll = settlementPrintControll;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getIsNoPrintParallelPortAddOrder() {
        return isNoPrintParallelPortAddOrder;
    }

    public void setIsNoPrintParallelPortAddOrder(String isNoPrintParallelPortAddOrder) {
        this.isNoPrintParallelPortAddOrder = isNoPrintParallelPortAddOrder;
    }

    public String getIsNoPrintParallelPortChargeback() {
        return isNoPrintParallelPortChargeback;
    }

    public void setIsNoPrintParallelPortChargeback(String isNoPrintParallelPortChargeback) {
        this.isNoPrintParallelPortChargeback = isNoPrintParallelPortChargeback;
    }

    public String getIsNoPrintParallelPortGiftOrder() {
        return isNoPrintParallelPortGiftOrder;
    }

    public void setIsNoPrintParallelPortGiftOrder(String isNoPrintParallelPortGiftOrder) {
        this.isNoPrintParallelPortGiftOrder = isNoPrintParallelPortGiftOrder;
    }

    public String getIsPrintBelongSetmealInOneMealOneOrder() {
        return isPrintBelongSetmealInOneMealOneOrder;
    }

    public void setIsPrintBelongSetmealInOneMealOneOrder(String isPrintBelongSetmealInOneMealOneOrder) {
        this.isPrintBelongSetmealInOneMealOneOrder = isPrintBelongSetmealInOneMealOneOrder;
    }

    public String getIsPrintBelongSetmealInSetmealMessage() {
        return isPrintBelongSetmealInSetmealMessage;
    }

    public void setIsPrintBelongSetmealInSetmealMessage(String isPrintBelongSetmealInSetmealMessage) {
        this.isPrintBelongSetmealInSetmealMessage = isPrintBelongSetmealInSetmealMessage;
    }

    public String getIsEnablePromptBeforePrintCustomeTicketAndCheckBill() {
        return isEnablePromptBeforePrintCustomeTicketAndCheckBill;
    }

    public void setIsEnablePromptBeforePrintCustomeTicketAndCheckBill(String isEnablePromptBeforePrintCustomeTicketAndCheckBill) {
        this.isEnablePromptBeforePrintCustomeTicketAndCheckBill = isEnablePromptBeforePrintCustomeTicketAndCheckBill;
    }

    public String getIsReprintServiceFlowInBillWindow() {
        return isReprintServiceFlowInBillWindow;
    }

    public void setIsReprintServiceFlowInBillWindow(String isReprintServiceFlowInBillWindow) {
        this.isReprintServiceFlowInBillWindow = isReprintServiceFlowInBillWindow;
    }

    public String getPromptMessage() {
        return promptMessage;
    }

    public void setPromptMessage(String promptMessage) {
        this.promptMessage = promptMessage;
    }
}
