<%--
  Created by zhshuo.
  Date: 2017-02-08
  Time: 13:34
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>就餐人数情况-时段</title>
    <jsp:include page="../../head.jsp"/>
    <script type="text/javascript" src="app/js/report.js"></script>
    <script type="text/javascript" src="app/js/DININGSYS/report/numberOfMeals/numberOfMeals_pnum.js"></script>
</head>
<body>
<div class="wrapper animated fadeInUp">
    <div class="search-wrapper input-groups">
        <form class="query-pan" id="query-pan">
            <ul>
            	<li id="dateFastSel" ></li>
                <li>
                    <span class="title">时间</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="startTime" name="startTime" placeholder="开始时间" readonly>
                    </div>
                </li>
                <li>
                    <span class="title">至</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="endTime" name="endTime" placeholder="结束时间" readonly>
                    </div>
                </li>
            </ul>
            <div style="position: absolute;right: 20px;bottom: 5px;">
				<input class="btn btn-success" id="flushBtn" type="button" value="查询">
			</div>
        </form>
    </div>
</div>
<div id="openDayReportContainer" class="container-fluid" style="overflow-y: auto;">
    <div id="containerDiv" style="width:95%;padding: 0px;"></div>
</div>
</body>
</html>