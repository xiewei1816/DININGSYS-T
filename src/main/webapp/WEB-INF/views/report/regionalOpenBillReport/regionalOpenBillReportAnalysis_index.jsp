<%--
  Created by zhshuo.
  Date: 2017-02-09
  Time: 16:06
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>区域开台经营分析</title>
    <jsp:include page="../../head.jsp"/>
    <link rel="stylesheet" href="app/css/DININGSYS/report/businessStatistics/date.css">
    <script type="text/javascript" src="app/js/report.js"></script>
    <script type="text/javascript" src="app/js/DININGSYS/report/regionalOpenBillReport/regionalOpenBillReport.js"></script>
    <script>
        $(document).ready(function () {
        	regionalOpenBillReport.initAnalysisPage();
        });
    </script>
</head>
<body>
<div class="wrapper animated fadeInUp">
    <div class="search-wrapper input-groups">
        <form class="query-pan" id="query-pan">
            <ul>
                <li>
                    <span class="title">时间</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control edit_searchDate" id=searchDate name="searchDate" placeholder="开始时间" readonly>
                    </div>
                </li>
                <li>
                    <span class="title">结算市别</span>
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
    </div>
    <div class="tableAreaOpenv">
        <table id="tableAreaOpen">
       		<thead>
        	<tr>
        		<th rowspan="2">
        			消费区域名称
        		</th>
        		<th rowspan="2">
        			市别
        		</th>
        		<th colspan="2">
        			营业额
        		</th>
        		<th colspan="2">
        			开台数
        		</th>
        		<th colspan="2">
        			客流量
        		</th>
        		<th colspan="2">
        			桌均消费
        		</th>
        		<th colspan="2">
        			人均消费
        		</th>
        		<th colspan="2">
        			菜品数量
        		</th>
        	</tr>
        	<tr>
        		<th>
        			本日
        		</th>
        		<th>
        			当月
        		</th>
        		<th>
        			本日
        		</th>
        		<th>
        			当月
        		</th>
        		<th>
        			本日
        		</th>
        		<th>
        			当月
        		</th>
        		<th>
        			本日
        		</th>
        		<th>
        			当月
        		</th>
        		<th>
        			本日
        		</th>
        		<th>
        			当月
        		</th>
        		<th>
        			本日
        		</th>
        		<th>
        			当月
        		</th>
        	</tr>
        	</thead>
        	<tbody>
        	</tbody>
        </table>
    </div>
</div>
</body>
</html>