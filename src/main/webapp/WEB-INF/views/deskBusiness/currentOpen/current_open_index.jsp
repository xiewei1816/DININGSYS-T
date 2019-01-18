<%--
  Created by zhshuo.
  Date: 2016-11-10
  Time: 16:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<script src="app/js/DININGSYS/deskBusiness/currentOpen/currentOpen.js"></script>
<script src="assets/scripts/rollSlide/rollSlide.js"></script>
<style>
    body,
    ul{
        margin: 0;
        padding: 0;
    }
    ul{
        list-style: none;
    }
    .container{
        margin-right: 5px;
    }
    .roll-wrap:hover .control{
        display: block;
    }

    .roll-wrap{
        position: relative;
        width: 100%;
        height: 50px;
        overflow: hidden;
    }
    .roll_row .roll__list::before, .roll_row .roll__list::after {
        content: "";
        display: table;
        line-height: 0;
    }
    .roll_row .roll__list::after {
        clear: both;
    }
    .roll_row .roll__list{
        width: 9999px;
    }
    .roll_row .roll__list li{
        float: left;
        display: block;
        margin-right: 10px;
        height: 40px;
        line-height: 40px;
        font-weight: bold;
        text-align: center;
        color: rgb(51, 51, 51);
        background-color: #f2f5f7;
        border: 1px solid #f2f5f7;
        cursor: pointer;
    }
    .control{
        position: absolute;
        display: none;
        padding: 10px 5px;
        font-weight: bold;
        text-decoration: none;
        color: #fff;
        background-color: rgba(0, 0, 0, .3);
    }
    .control:hover{
        background-color: rgba(0, 0, 0, .5);
    }
    .control.pre{
        left: 2px;
    }
    .control.next{
        right: 2px;
    }
    .tableBoderLess {
        border-bottom:0px !important;
    }
    .tableBoderLess th, .tableBoderLess td {
        border: 1px !important;
    }
</style>
<script>
    jQuery(document).ready(function () {
        cuOw.reset();
        cuOw.pageInit();
        cuOw.tableTimerInit();
    })
</script>
<div class="panel panel-default" id="currentBisPanel">
    <input type="hidden" value="${fushTime}" id="timeTaskSet">
    <div class="panel-heading" id="panelHeadDiv">
        <button class="btn btn-default btn-sm" type="button" id="toSeatLastClosedCheck">翻台账单</button>
        <button class="btn btn-default btn-sm" type="button" id="currentOpenWaterSetting">设置</button>
        <button class="btn btn-default btn-sm" type="button" id="currentOpenWaterFlush">刷新</button>
    </div>
    <div class="panel-body">
        <div class="tab-content">
            <div class="tab-pane active" style="overflow-x: hidden;: auto;padding: 5px" id="overflowPanel">
                <div style="min-width: 1180px">
                    <div id="bisLeftDiv" style="float:left;min-height: 600px;min-width: 700px">
                        <form class="form-inline" id="searchForm">
                            <div class="form-group">
                                <label for="org">组织机构</label>
                                <select id="org" name="org" class="form-control">
                                    <c:forEach items="${tbOrgs}" var="org">
                                        <option value="${org.id}">${org.orgName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="place">区域</label>
                                <select id="place" name="place" class="form-control">
                                    <option value="">全部区域</option>
                                    <c:forEach items="${dgConsumptionAreas}" var="area">
                                        <option value="${area.id}">${area.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="bis">市别</label>
                                <select id="bis" name="bis" class="form-control">
                                    <option value="">全部市别</option>
                                    <c:forEach items="${tbBises}" var="bis">
                                        <option value="${bis.id}">${bis.bisName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="seatState">客位状态</label>
                                <select id="seatState" name="seatState" class="form-control">
                                    <option value="">全部状态</option>
                                    <option value="2">占用</option>
                                    <option value="1">空闲</option>
                                    <option value="3">清扫</option>
                                    <option value="4">预定</option>
                                    <option value="5">埋单</option>
                                </select>
                            </div>
                        </form>
                        <hr>
                        <div id="bisContent">
                            <h5>当前市别：<span id="currentBis">全部市别</span></h5>
                            <table id="currentBisTable"></table>
                        </div>
                        <div id="bisTabDiv">
                            <hr>
                            <ul class="nav nav-tabs" role="tablist">
                                <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">客位账单信息</a></li>
                                <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">结算方式信息</a></li>
                            </ul>
                            <div class="tab-content">
                                <div role="tabpanel" class="tab-pane active" id="home" style="min-height: 245px">
                                    <div id="leftTabDiv">
                                        <div class="row">
                                            <div class="col-md-12 table-responsive">
                                                <table class="table tableBoderLess">
                                                    <tr>
                                                        <td>客位信息</td>
                                                    </tr>
                                                    <tr>
                                                        <td>总数：</td><td class="clearP" id="seatTotal">0</td>
                                                        <td>清扫：</td><td class="clearP" id="clearingSeat">0</td>
                                                        <td>预定：</td><td class="clearP" id="reservationSeat">0</td>
                                                        <td>开台率：</td><td class="clearPe" id="firstRate">0%</td>
                                                        <td>所选市别预定：</td><td class="clearP" id="todayPBRe">0</td>
                                                        <td>所选市别执行：</td><td class="clearP" id="todayPBImp">0</td>
                                                    </tr>
                                                    <tr>
                                                        <td>占用：</td><td class="clearP" id="busySeat">0</td>
                                                        <td>空闲：</td><td class="clearP" id="freeSeat">0</td>
                                                        <td>埋单：</td><td class="clearP" id="paySeat">0</td>
                                                        <td>上座率：</td><td class="clearPe" id="attendance">0%</td>
                                                        <td>今日预定总数：</td><td class="clearP" id="todayReservationSeat">0</td>
                                                        <td>今日执行总数：</td><td class="clearP" id="todayImpl">0</td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12 table-responsive">
                                                <table class="table tableBoderLess">
                                                    <tr>
                                                        <td>账单信息</td>
                                                    </tr>
                                                    <tr>
                                                        <td>所选市别已结账单：</td><td class="clearP" id="bisClosedClass">0</td>
                                                        <td>今日未结账单：</td><td class="clearP" id="openIncome">0</td>
                                                        <td>全部账单：</td><td class="clearP" id="todayCheck">0</td>
                                                        <td>营业收入：</td><td class="clearP" id="operatingIncome">0</td>
                                                    </tr>
                                                    <tr>
                                                        <td>所选市别已结金额：</td><td class="clearP" id="bisClosedAmount">0</td>
                                                        <td>今日未结金额：</td><td class="clearP" id="openCount">0</td>
                                                        <td>全部金额：</td><td class="clearP" id="allAmount">0</td>
                                                    </tr>
                                                    <tr>
                                                        <td>所选市别已结人数：</td><td class="clearP" id="bisClosedPeople">0</td>
                                                        <td>今日未结人数：</td><td class="clearP" id="openPeopleCount">0</td>
                                                        <td>全部人数：</td><td class="clearP" id="operatingPeopleCount">0</td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div role="tabpanel" class="tab-pane" id="profile" style="min-height: 245px">
                                    <table id="settlementTypeTable"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default" id="disRightDiv" style="min-height: 600px;float: right;min-width:450px;">
                        <div class="panel-heading" id="bisRightPanelHead">
                            <label class="panel-title" id="seatInfoId"></label><br>
                            <div class="row">
                                <div class="col-md-6">
                                    <label class="panel-title">消费区域：</label><span id="areaId"></span>
                                </div>
                                <div class="col-md-6">
                                    <label class="panel-title">客位标签：</label><span id="seatLabel"></span>
                                </div>
                            </div>
                        </div>
                        <div class="panel-body" id="bisRightPanel1">
                            <div class="row container">
                                <div class="roll-wrap roll_row" id="openWatersDiv">
                                    <ul id="openWaterNums" class="roll__list" style="position: absolute; left: 0; top: 0;">
                                    </ul>
                                    <a style="text-decoration:none;color: #ffffff" class="control pre" href="javascript:;">&lt;</a>
                                    <a style="text-decoration:none;color: #ffffff" class="control next" href="javascript:;">&gt;</a>
                                </div>
                            </div>
                            <div style="height: 100px;">
                                <form class="form-horizontal">
                                    <div class="row">
                                        <div class="col-md-7">
                                            <label for="kaiDanTime" class="control-label">开单时间:</label>
                                            <span id="kaiDanTime"></span>
                                        </div>
                                        <div class="col-md-5">
                                            <label for="kaiDanPos" class="control-label">开单POS:</label>
                                            <span id="kaiDanPos"></span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-7">
                                            <label for="members" class="control-label">会员:</label>
                                            <span id="members"></span>
                                        </div>
                                        <div class="col-md-5">
                                            <label for="kaiDanRs" class="control-label">人数:</label>
                                            <span id="kaiDanRs"></span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-7">
                                            <label for="documents" class="control-label">单据:</label>
                                            <span id="documents"></span>
                                        </div>
                                        <div class="col-md-5">
                                            <label for="servicer" class="control-label">服务员:</label>
                                            <span id="servicer"></span>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <table id="bisRightTale"></table>
                            <hr>
                            <div>
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">整单备注：</label>
                                        <div class="col-sm-8">
                                            <p class="form-control-static" id="owNotes"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">合计：</label>
                                        <div class="col-sm-8">
                                            <p class="form-control-static" style="color: red" id="total"></p>
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
</div>