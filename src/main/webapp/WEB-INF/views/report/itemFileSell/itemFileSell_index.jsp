<%--
  Created by zhshuo.
  Date: 2017-02-06
  Time: 9:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>品项销售明细</title>
    <jsp:include page="../../head.jsp"/>
    <script type="text/javascript" src="app/js/report.js"></script>
    <script type="text/javascript" src="app/js/DININGSYS/report/itemFileSell/itemFileSell.js"></script>
    <script>
        $(document).ready(function () {
            itemFileSell.pageInit();
        });
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
                    <span class="title">品项名称</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="itemFileName" name="itemFileName" placeholder="品项名称">
                    </div>
                </li>
                <li>
                    <span class="title">服务方式</span>
                    <div class="form-group big-wid">
                        <%-- 2/加单  3/赠单  4/退单 --%>
                        <select class="form-control" name="serviceType" id="serviceType">
                            <option selected value="">全部</option>
                            <option value="2">加单</option>
                            <option value="3">赠单</option>
                            <option value="4">退单</option>
                        </select>
                    </div>
                </li>
                <li style="display: none">
                    <span class="title">品项类型</span>
                    <div class="form-group big-wid">
                        <select class="form-control" name="itemFileType" id="itemFileType">
                            <option selected value="">全部</option>
                            <option value="0">非套餐</option>
                            <option value="1">套餐</option>
                        </select>
                    </div>
                </li>
                <li style="display: none">
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
            </ul>
            <div style="position: absolute;right: 20px;bottom: 5px;">
				<input class="btn btn-success" id="doSearch" type="button" value="查询">
			</div>
        </form>
    </div>
    <div class="btn-toolbar">
        <span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span>
        <span id="refresh" class="greenbtn"><i class="fa fa-refresh"></i>刷新</span>
        <span id="print" class="graybtn"><i class="fa fa-print"></i>打印</span>
        <label>
            <input type="radio" name="dataType" id="details" value="details" checked>明细
        </label>
        <label>
            <input type="radio" name="dataType" id="summary" value="summary">汇总
        </label>
    </div>
    <%--<div class="radio" style="display: inline">
        <label>
            <input type="radio" name="dataType" id="details" value="details" checked>明细
        </label>
        <label>
            <input type="radio" name="dataType" id="summary" value="summary">汇总
        </label>
    </div>--%>
    <div id="tableDetails">
        <table id="itemFileSellDetails"></table>
        <div id="pager_list_1"></div>
    </div>

    <div id="tableSummary" style="display: none;">
        <table id="itemFileSellSummary"></table>
        <div id="pager_list_2"></div>
    </div>
</div>
</body>
</html>