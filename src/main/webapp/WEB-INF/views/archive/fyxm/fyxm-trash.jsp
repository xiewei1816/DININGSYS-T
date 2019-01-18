<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>费用项目管理</title>
		<jsp:include page="../../head.jsp"/>
	    <script src="app/js/DININGSYS/archive/fyxm/fyxm-trash.js"></script>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">编号</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="fyxmNum" name="fyxmNum" />
							</div>
						</li>
						<li>
							<span class="title">名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="fyxmName" name="fyxmName" />
							</div>
						</li>
					</ul>
					<div class="search-btns">
						<button class="btn btn-primary">查询</button>
					</div>
				</form> 
			</div>
			<div class="btn-toolbar">
				<span id="reply" class="greenbtn"><i class="fa fa-reply"></i>还原</span>
				<span id="delete" class="redbtn"><i class="fa fa-times"></i>删除</span>
				<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	</body>
</html>
