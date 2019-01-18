<%--
  Created by zhshuo.
  Date: 16-11-22
  Time: 下午3:35
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"></jsp:include>
<script src="app/js/DININGSYS/deskBusiness/closedBills/closedBills.js"></script>
<script>
    jQuery(document).ready(function () {
        closedBills.reset();
        closedBills.pageInit();
        closedBills.pageDataInit();
        closedBills.pageBtnInit();
    })
</script>
<div class="panel panel-default" id="currentBisPanel">
    <div class="panel-heading" id="panelHeadDiv">
        <input type="hidden" id="timerTaskId" value="${flushTime}">
        <input type="hidden" id="userCode" value="${userCode}">
        <button class="btn btn-default btn-sm" type="button" id="flushOpenBills">查询</button>
        <%--<c:if test="${fkxz}">--%>
            <button class="btn btn-default btn-sm" type="button" id="printOpenBills" style="display: none">付款修正</button>
        <%--</c:if>--%>
        <%--<c:if test="${bkfp}">--%>
            <button class="btn btn-default btn-sm" type="button" id="repairInvoice" style="display: none">补开发票</button>
        <%--</c:if>--%>
        <%--<c:if test="${returnSettlement}">--%>
            <button class="btn btn-default btn-sm" type="button" id="returnSettlement">返位结算</button>
        <%--</c:if>--%>
       <%-- <button class="btn btn-default btn-sm" type="button" id="print">打印账单-没做</button>
        <button class="btn btn-default btn-sm" type="button" id="export">导出-没做</bu1tton>
        <button class="btn btn-default btn-sm" type="button" id="supplementCoupons">补赠券-没做</button>--%>
        <%--<button class="btn btn-default btn-sm" type="button" id="editNotes">编辑结算备注</button>--%>
        <%--<button class="btn btn-default btn-sm" type="button" id="close">取消</button>--%>
    </div>
    <div class="panel-body" style="overflow:auto;" id="contentDiv">
        <div style="min-width: 1300px;min-height: 800px;margin-top: -15px" id="contentBodyDiv">
            <div class="row" style="background-color: #D6EFDE;">
                <div class="col-md-5" style="border-right:1px solid grey">
                    <label>当日已结账单</label>
                </div>
                <div class="col-md-7">
                    <label>结算流水</label><span style="margin-left: 20px" id="closedWaterNum">无</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5" style="border-right:1px solid grey;min-width: 499px;min-height: 800px" id="lefeDiv">
                    <div class="panel panel-default" id="leftPanelDiv">
                        <div class="panel-body">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label for="openDate" class="col-sm-3 control-label">营业日期：</label>
                                    <div class="col-sm-6">
                                        <input readonly id="openDate" type="text" class="form-control">
                                    </div>
                                    <div class="col-sm-3">
                                        <label class="form-control-static">共<span id="totalWater"></span></label>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <table id="closedBillsTable"></table>
                        <table style="margin: 5px auto; border-width: 60px;">
                            <tr>
                                <td>客位：</td>
                                <td>
                                    <input id="clientSeat" type="text" class="form-control">
                                <%--<select style="width: 200px;" class="form-control" id="clientSeat" name="clientSeat">
										<option selected="selected"></option>
										<c:forEach items="${seatList}" var="seat" varStatus="status">
											<option  value="${seat.id}" >${seat.name}</option>
										</c:forEach>
									</select>--%>
                                </td>
                                <td>市别：</td>
                                <td>
                                	<select style="width: 200px;" class="form-control" id="bis" name="bis">
										<option selected="selected"></option>
										<c:forEach items="${bisList}" var="bis" varStatus="status">
											<option  value="${bis.id}" >${bis.bisName}</option>
										</c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>消费区域：</td>
                                <td>
                                    <select style="width: 200px;" class="form-control" id="expArea" name="expArea">
										<option selected="selected"></option>
										<c:forEach items="${areaList}" var="area" varStatus="status">
											<option  value="${area.id}" >${area.name}</option>
										</c:forEach>
									</select>
                                </td>
                                <td>POS：</td>
                                <td>
                                    <select style="width: 200px;" class="form-control" id="pos" name="pos">
										<option selected="selected"></option>
										<c:forEach items="${posList}" var="pos" varStatus="status">
											<option  value="${pos.id}" >${pos.name}</option>
										</c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <select class="form-control" id="choiceCode" name="choiceCode">
                                        <option value="1">营业流水编号：</option>
                                        <option value="2">结算流水编号：</option>
                                        <%--<option value="3">客位编号：</option>--%>
                                    </select>
                                </td>
                                <td colspan="3">
                                    <input type="text" id="code" name="code" class="form-control">
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="col-md-7" style="min-width: 700px;min-height: 800px" id="rightDiv">
                    <div class="panel panel-default" id="rightPanelDiv">
                        <div class="panel-body">
                            <form class="form-horizontal">
                                <div class="row" id="row1">
                                    <div class="col-md-4" style="border-right: 2px solid #2d6987">
                                        <p class="form-control-static">消费金额：<span id="consumptionAmount" class="clearsp"></span></p>
                                        <p class="form-control-static">抹零金额：<span id="zeroAmount" class="clearsp"></span></p>
                                        <p class="form-control-static">定额金额：<span id="fixedDiscount" class="clearsp"></span></p>
                                        <p class="form-control-static">应收金额：<span id="amountsReceivable" class="clearsp"></span></p>
                                        <p class="form-control-static">实收金额：<span id="paidAmount" class="clearsp"></span></p>
                                        <p class="form-control-static">找零金额：<span id="changeAmount" class="clearsp"></span></p>
                                    </div>
                                    <div class="col-md-8" id="rightDivInfo">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p class="form-control-static">结算时间：<span id="clearingTime" class="clearsp"></span></p>
                                            </div>
                                            <div class="col-md-6">
                                                <p class="form-control-static">结算市别：<span id="bisName" class="clearsp"></span></p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p class="form-control-static">结算操作员：<span id="clearingOperatorName" class="clearsp"></span></p>
                                            </div>
                                            <div class="col-md-6">
                                                <p class="form-control-static">结算POS：<span id="clearingPosName" class="clearsp"></span></p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3">
                                                <p class="form-control-static">打印次数：<span id="printCont" class="clearsp"></span></p>
                                            </div>
                                            <div class="col-md-3">
                                                <p class="form-control-static">开具发票：<span id="invoicing " class="clearsp"></span></p>
                                            </div>
                                            <div class="col-md-3">
                                                <p class="form-control-static">零结算：<span id="zeroSettlement" class="clearsp"></span></p>
                                            </div>
                                            <div class="col-md-3">
                                                <p class="form-control-static">补录单据：<span id="retroDocuments" class="clearsp"></span></p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p class="form-control-static">结账单备注：<span id="statementLabel" class="clearsp"></span></p>
                                            </div>
                                            <div class="col-md-6">
                                                <p class="form-control-static">赠券金额：<span id="couponAmount" class="clearsp"></span></p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <textarea class="form-control" readonly style="resize: none" title="结算备注" id="clearingNotes" class="clearsp"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" id="row2">
                                    <div class="col-md-12">
                                        <table id="openWaterTable"></table>
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
                                            <div role="tabpanel" class="tab-pane active" id="settlementWayDiv">
                                                <table id="settlementWayDivTable"></table>
                                            </div>
                                            <div role="tabpanel" class="tab-pane" id="consumerDetailsDiv">
                                                <table id="consumerDetailsDivTable"></table>
                                            </div>
                                            <div role="tabpanel" class="tab-pane" id="classStatementDiv">
                                                <table id="classStatementDivTable"></table>
                                            </div>
                                            <div role="tabpanel" class="tab-pane" id="invoiceInfoDiv">
                                                <table id="invoiceInfoDivTable"></table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>