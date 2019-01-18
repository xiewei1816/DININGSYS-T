<%--
  Created by heshuai.
  Date: 2017-02-09
  Time: 16:06
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>区域开台人均</title>
    <jsp:include page="../../head.jsp"/>
    <link rel="stylesheet" href="app/css/DININGSYS/report/businessStatistics/date.css">
    <script type="text/javascript" src="app/js/report.js"></script>
    <script type="text/javascript" src="app/js/DININGSYS/report/businessAnalysis/businessAnalysis.js"></script>
    <script>
        $(document).ready(function () {
        	businessAnalysis.zb();
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
                        <input type="text" class="form-control edit_searchDate" id="searchDate" name="searchDate" placeholder="开始时间" readonly>
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
    <div class="tableAreaOpenv" id="print-data">
    	<span class="head-mid">营业收入分析报表</span>
    	<span class="title">营业日期:${date}</span> 
    	<span class="title">打印日期:${date}</span>
    	<span class="title">打印时间:${time}</span>
        <table id="tableAreaOpen">
        <thead>
        	<tr>
        		<th>
        			统计项名称
        		</th>
        		<th>
        			人民币
        		</th>
        		<th>
        			应收金额
        		</th>
        		<th>
        			实收金额
        		</th>
        		<th>
        			找零金额
        		</th>
        		<th>
        			定额优惠
        		</th>
        		<th>
        			抹零金额
        		</th>
        		<th>
        			消费金额
        		</th>
        		<th>
        			赠卷金额
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