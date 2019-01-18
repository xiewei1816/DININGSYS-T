<%--
  Created by zhshuo.
  Date: 2017-02-07
  Time: 13:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>结账单查询</title>
    <jsp:include page="../../head.jsp"/>
    <script type="text/javascript" src="app/js/report.js"></script>
    <script type="text/javascript" src="app/js/DININGSYS/report/statementQuery/statementQuery.js"></script>
    <script>
        $(document).ready(function () {
            statementQuery.pageInit();
        })
    </script>
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
                        <input type="text" class="form-control edit_startTime" id="startTime" name="startTime" placeholder="开始时间" readonly>
                    </div>
                </li>
                <li>
                    <span class="title">至</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control edit_endTime" id="endTime" name="endTime" placeholder="结束时间" readonly>
                    </div>
                </li>
                <li>
                    <span class="title">消费区域</span>
                    <div class="form-group big-wid">
                        <select class="form-control" name="consumerArea" id="consumerArea">
                            <option selected value="">全部区域</option>
                            <c:forEach items="${allConsumerArea}" var="area">
                                <option value="${area.id}">${area.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </li>
                <li>
                    <span class="title">市别</span>
                    <div class="form-group big-wid">
                        <select class="form-control" name="bis" id="bis">
                            <option selected value="">全部</option>
                            <c:forEach items="${TbBis}" var="bis">
                                <option value="${bis.id}">${bis.bisName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </li>
                <li>
                    <span class="title">结算POS</span>
                    <div class="form-group big-wid">
                        <select class="form-control" name="pos" id="pos">
                            <option selected value="">全部POS</option>
                            <c:forEach items="${allPosList}" var="pos">
                                <option value="${pos.id}">${pos.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </li>
                <li>
                    <span class="title">结算流水号</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="clearingNum" name="clearingNum" placeholder="结算流水号">
                    </div>
                </li>
            </ul>
            <div style="position: absolute;right: 20px;bottom: 5px;">
				<input class="btn btn-success" id="doSearch" type="button" value="查询">
			</div>
        </form>
    </div>
    <div class="btn-toolbar">
        <span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span>
        <span id="refresh" class="greenbtn"><i class="fa fa-refresh"></i>刷新</span>
        <span id="print" class="graybtn" style="display: none;"><i class="fa fa-print"></i>打印</span>
    </div>
    <div id="tableDetails" style="overflow-x: auto">
        <table id="statementQuery"></table>
        <div id="pager_list_1"></div>
    </div>
</div>
</body>
</html>