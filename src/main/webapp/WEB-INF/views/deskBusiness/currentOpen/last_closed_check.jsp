<%--
  翻台账单
  Created by zhshuo.
  Date: 16-11-17
  Time: 上午11:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    p{
        font-weight: bold;
    }
    #leftDivInfo p span{
        font-weight: normal;
        margin-left: 100px;
    }
    #rightDivInfo p span{
        font-weight: normal;
    }
</style>
<script>
    jQuery(document).ready(function () {
        cuOw.lastClosedCheckPageInit();
    })
</script>
<div class="panel panel-default" id="lastClosedCheckPage" style="min-width: 900px">
    <div class="panel-heading">
        <%--<button class="btn btn-default btn-sm" type="button" id="flush">刷新</button>--%>
        <%--<button class="btn btn-default btn-sm" type="button" id="print">打印账单</button>--%>
        <%--<button class="btn btn-default btn-sm" type="button" id="export">导出</button>--%>
        <%--<button class="btn btn-default btn-sm" type="button" id="supplemenCoupons">补赠券</button>--%>
        <button class="btn btn-default btn-sm" type="button" id="editCloseNotes">编辑结算备注</button>
        <button class="btn btn-default btn-sm" type="button" id="cancel">取消</button>
    </div>
    <div class="panel-body" style="padding-top: 0px">
        <div class="tab-pane active" style="min-height: 800px" id="lastClosedCheckPanel">
            <form class="form-horizontal">
                <div class="row" style="border-bottom: 1px solid darkgrey">
                    <div class="col-md-12">
                        <input type="hidden" value="${seatId}" id="seatId">
                        <input type="hidden" value="${dgReceptionClearingWater.id}" id="cwId">
                        <h4><label>结算流水</label><span style="font-weight:bold ">|</span><span>${dgReceptionClearingWater.cwNum}</span></h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4" style="border-right: 2px solid #2d6987" id="leftDivInfo">
                        <p class="form-control-static">消费金额：<span>${dgReceptionClearingWater.consumptionAmount}</span></p>
                        <p class="form-control-static">抹零金额：<span>${dgReceptionClearingWater.zeroAmount}</span></p>
                        <p class="form-control-static">定额金额：<span>${dgReceptionClearingWater.fixedDiscount}</span></p>
                        <p class="form-control-static">应收金额：<span>${dgReceptionClearingWater.amountsReceivable}</span></p>
                        <p class="form-control-static">实收金额：<span>${dgReceptionClearingWater.paidAmount}</span></p>
                        <p class="form-control-static">找零金额：<span>${dgReceptionClearingWater.changeAmount}</span></p>
                    </div>
                    <div class="col-md-8" id="rightDivInfo">
                        <div class="row">
                            <div class="col-md-6">
                                <p class="form-control-static">结算时间：<span>${dgReceptionClearingWater.clearingTime}</span></p>
                            </div>
                            <div class="col-md-6">
                                <p class="form-control-static">结算市别：<span>${dgReceptionClearingWater.bisName}</span></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <p class="form-control-static">结算操作员：<span>${dgReceptionClearingWater.clearingOperatorName}</span></p>
                            </div>
                            <div class="col-md-6">
                                <p class="form-control-static">结算POS：<span>${dgReceptionClearingWater.clearingPosName}</span></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <p class="form-control-static">打印次数：<span>${dgReceptionClearingWater.printCont}</span></p>
                            </div>
                            <div class="col-md-3">
                                <p class="form-control-static">开具发票：<span>${dgReceptionClearingWater.invoicing}</span></p>
                            </div>
                            <div class="col-md-3">
                                <p class="form-control-static">补录单据：<span>${dgReceptionClearingWater.retroDocuments}</span></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <p class="form-control-static">结账单备注：<span>${dgReceptionClearingWater.statementLabel}</span></p>
                            </div>
                            <div class="col-md-6">
                                <p class="form-control-static">赠券金额：<span>${dgReceptionClearingWater.couponAmount}</span></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <textarea class="form-control" readonly style="resize: none" title="结算备注">${dgReceptionClearingWater.clearingNotes}</textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table id="seatLastCheck"></table>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <ul class="nav nav-tabs" role="tablist">
                            <li role="presentation" class="active"><a href="#settlementWayDiv" aria-controls="home" role="tab" data-toggle="tab">结算方式</a></li>
                            <li role="presentation"><a href="#consumerDetailsDiv" aria-controls="profile" role="tab" data-toggle="tab">消费明细</a></li>
                            <li role="presentation"><a href="#classStatementDiv" aria-controls="home" role="tab" data-toggle="tab">优惠信息</a></li>
                            <li role="presentation"><a href="#invoiceInfoDiv" aria-controls="profile" role="tab" data-toggle="tab">发票信息</a></li>
                        </ul>
                        <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active" id="settlementWayDiv" style="min-height: 245px">
                                <table id="settlementWayDivTable"></table>
                            </div>
                            <div role="tabpanel" class="tab-pane" id="consumerDetailsDiv" style="min-height: 245px">
                                <table id="consumerDetailsDivTable"></table>
                            </div>
                            <div role="tabpanel" class="tab-pane" id="classStatementDiv" style="min-height: 245px">
                                <table id="classStatementDivTable"></table>
                            </div>
                            <div role="tabpanel" class="tab-pane" id="invoiceInfoDiv" style="min-height: 245px">
                                <table id="invoiceInfoDivTable"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>