<%--
  Created by zhshuo.
  Date: 16-11-30
  Time: 下午3:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"></jsp:include>
<script src="app/js/DININGSYS/deskBusiness/openBills/openBills.js"></script>
<script>
    jQuery(document).ready(function () {
        openBills.reset();
        openBills.pageInit();
        openBills.pageBtnInit();
    })
</script>
<div class="panel panel-default" id="currentBisPanel">
    <div class="panel-heading" id="panelHeadDiv">
        <%--<input type="hidden" id="timerTaskId" value="${flushTime}">--%>
        <button class="btn btn-default btn-sm" type="button" id="flushOpenBills">查询</button>
        <%--<button class="btn btn-default btn-sm" type="button" id="printOpenBills">打印选中流水</button>--%>
    </div>
    <div class="panel-body" style="padding: 0px">
        <div class="tab-content" id="tabContent">
            <div class="tab-pane active" style="overflow-y: auto;" id="overflowPanel">
                <div style="min-width: 1180px;min-height: 500px;">
                    <div class="row">
                        <div class="col-md-12" style="background-color:#D6EFDE;">
                            <div class="col-md-6" style="border-right:1px solid grey ">
                                <label>本班未结账单</label>
                            </div>
                            <div class="col-md-6">
                                <label class="panel-title">营业流水：</label>
                                <span id="openWaterId" class="resetSpanClass"></span>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div id="openBillsLeftDiv" class="col-md-6" style="min-height: 800px;border-right: 1px solid grey">
                                <div class="row">
                                    <div class="col-md-12" style="padding: 0px">
                                        <table id="openBillsTable"></table>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <table style="margin: 5px auto;">
                                            <tr>
                                                <td>消费区域:</td>
                                                <td>
				                                    <select style="width: 200px;" class="form-control" id="place" name="place">
														<option selected="selected"></option>
														<c:forEach items="${areaList}" var="area" varStatus="status">
															<option  value="${area.id}" >${area.name}</option>
														</c:forEach>
													</select>
                                                </td>
                                                <td>客位:</td>
                                                <td>
                                                    <input type="text" class="form-control" id="seatName">
                                                <%--<select style="width: 200px;" class="form-control" id="seatName" name="seatName">
														<option selected="selected"></option>
														<c:forEach items="${seatList}" var="seat" varStatus="status">
															<option  value="${seat.id}" >${seat.name}</option>
														</c:forEach>
													</select>--%>
				                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 100px">营业流水编号：</td>
                                                <td colspan="3">
                                                    <input type="text" class="form-control" id="owNum">
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div id="openBillsRightDiv" class="col-md-6" style="min-height: 800px;">
                                <div class="row">
                                    <div class="col-md-12">
                                        <table id="rightTopTable">
                                            <tr>
                                                <td><label for="kaiDanPos" class="control-label">开单POS:</label></td>
                                                <td><span id="kaiDanPos" class="resetSpanClass"></span></td>
                                                <td><label for="kaiDanTime" class="control-label">开单时间:</label></td>
                                                <td><span id="kaiDanTime" class="resetSpanClass"></span></td>
                                                <td><label for="kaiDanRs" class="control-label">人数:</label></td>
                                                <td><span id="kaiDanRs" class="resetSpanClass"></span></td>
                                            </tr>
                                            <tr>
                                                <td><label for="kaiDanPos" class="control-label">服务员:</label></td>
                                                <td><span id="servicer" class="resetSpanClass"></span></td>
                                                <td><label for="kaiDanTime" class="control-label">客户:</label></td>
                                                <td><span id="client" class="resetSpanClass"></span></td>
                                                <td><label for="kaiDanRs" class="control-label">单据:</label></td>
                                                <td><span id="documents" class="resetSpanClass"></span></td>
                                            </tr>
                                            <tr>
                                                <td><label for="kaiDanPos" class="control-label">整单备注:</label></td>
                                                <td colspan="5"><span id="owNotes" class="resetSpanClass"></span></td>
                                            </tr>
                                            <tr>
                                                <td><label for="kaiDanTime" class="control-label">团队成员:</label></td>
                                                <td colspan="5"><span id="teamMembers" class="resetSpanClass"></span></td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li role="presentation" class="active"><a href="#openWaterItemFile" id="openWaterIdTwo" aria-controls="home" role="tab" data-toggle="tab">无</a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div role="tabpanel" class="tab-pane active" id="home">
                                                <div id="openWaterItemFile">
                                                    <table id="openWaterItemFileDetails"></table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>