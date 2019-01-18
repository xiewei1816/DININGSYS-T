<%--
  Created by zhshuo.
  Date: 2017-03-20
  Time: 9:34
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>日营业报表</title>
    <jsp:include page="../../head.jsp"/>
    <script type="text/javascript" src="app/js/report.js"></script>
    <script type="text/javascript" src="app/js/DININGSYS/report/openDay/openDay.js"></script>
</head>
<body>
<div class="wrapper animated fadeInUp">
    <div class="search-wrapper input-groups">
        <form class="query-pan" id="query-pan">
            <ul>
                <li id="dateFastSel"></li>
                <li>
                    <span class="title">时间：</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control edit_startTime" id="startTime" name="startTime"
                               placeholder="开始时间" readonly>
                    </div>
                </li>
                <li>
                    <span class="title">至</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="endTime" name="endTime"
                               placeholder="结束时间" readonly>
                    </div>
                </li>
                <li style="display: none;">
                    <span class="title">时间类型：</span>
                    <div class="form-group big-wid radio">
                        <label>
                            <input type="radio" name="timeType" value="1" checked>按结算时间
                        </label>
                        <label>
                            <input type="radio" name="timeType" value="2">按开台时间
                        </label>
                    </div>
                </li>
                <li>
                    <span class="title">门店编号：</span>
                    <div class="form-group big-wid">
                        <select class="form-control" id="org" name="org">
                            <c:forEach items="${org}" var="item">
                                <option selected value="${item.orgCode}">${item.orgName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </li>
                <li>
                    <span class="title">结算市别：</span>
                    <div class="form-group big-wid">
                        <select class="form-control" name="bis" id="bis">
                            <option value="">全部</option>
                            <c:forEach items="${bis}" var="item">
                                <option value="${item.id}">${item.bisName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </li>
                <li>
                    <span class="title">POS：</span>
                    <div class="form-group big-wid">
                        <select class="form-control" name="POS" id="pos">
                            <option value="">全部</option>
                            <c:forEach items="${allPosList}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </li>
                <li>
                    <span class="title">消费区域：</span>
                    <div class="form-group big-wid">
                        <select class="form-control" name="area" id="area">
                            <option value="">全部</option>
                            <c:forEach items="${allArea}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </li>
            </ul>
            <div style="position: absolute;right: 20px;bottom: 5px;">
                <input class="btn btn-success" id="flushBtn" type="button" value="刷新">
                <input class="btn btn-success" id="exportBtn" type="button" value="导出">
                <input class="btn btn-success" id="printBtn" type="button" value="打印">
            </div>
        </form>
    </div>
</div>
<div id="openDayReportContainer" class="container-fluid" style="overflow-y:auto;height:auto;">
    <div id="containerDiv" style="width:95%;height:auto;padding:20px;margin:0 auto;"></div>
</div>
</body>
</html>