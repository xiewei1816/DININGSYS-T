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
    <jsp:include page="../../head.jsp"/>
    <script type="text/javascript" src="app/js/DININGSYS/report/monthlypassenger/monthlyPassenger.js"></script>
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
            </ul>
            <div style="position: absolute;right: 20px;bottom: 5px;">
                <input class="btn btn-success" id="flushBtn" type="button" value="查询">
                <input class="btn btn-success" id="exportBtn" type="button" value="导出">
            </div>
        </form>
    </div>
</div>
<div id="openDayReportContainer" class="container-fluid" style="overflow-y: auto;">
    <div id="containerDiv" style="width:95%;padding: 0px;">
</div>
</div>
</div>
</body>