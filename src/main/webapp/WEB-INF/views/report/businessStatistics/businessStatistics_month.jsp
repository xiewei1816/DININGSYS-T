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
    <script type="text/javascript">
			var path = "${ctx}";
	</script>
    <link rel="stylesheet" href="app/css/DININGSYS/report/businessStatistics/date.css">
    <script type="text/javascript" src="app/js/DININGSYS/report/businessStatistics/businessStatistics.js"></script>
    <script>
        $(document).ready(function () {
        	businessStatistics.month();
        });
    </script>  
</head>
<body>
<div class="wrapper animated fadeInUp">
    <div class="search-wrapper input-groups">
        <form class="query-pan" id="query-pan">
            <ul>
                <li>
                    <span class="title">年度与月度</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control edit_searchDate" id="searchDate" name="searchDate" placeholder="请选择" readonly>
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
    <div class="tableMonth" id="print-data">
        <table id="tableOne">
        </table>
    </div>
</div>
</body>
</html>