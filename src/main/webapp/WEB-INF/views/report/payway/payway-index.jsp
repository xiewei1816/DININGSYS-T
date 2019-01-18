<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>结算方式信息</title>
		<jsp:include page="../../head.jsp"/>
	    <script type="text/javascript" src="app/js/report.js"></script>

	    <script type="text/javascript" src="app/js/DININGSYS/report/payway/payway-index.js"></script>
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
							<span class="title">结算方式</span>
							<div class="form-group big-wid">
								<select class="form-control" id="cwName" name="cwName" >
									<option selected="selected"></option>
									<c:forEach items="${list}" var="payWay">
										<option value="${payWay.number}" >${payWay.name}</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li style="display: none;">
							<span class="title">统计模式</span>
							<div class="form-group big-wid">
								<select class="form-control" id="type" name="type" >
									<option selected="selected" value="1">按汇总</option>
									<option value="2">按明细</option>
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
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="pager_list_1"></div>
            </div>
	    </div>
	</body>
</html>
