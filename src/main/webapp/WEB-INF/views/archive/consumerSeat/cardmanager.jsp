<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>台卡管理</title>
		<jsp:include page="../../head.jsp"/>
	    <script src="app/js/DININGSYS/archive/consumerSeat/cardmanager.js"></script>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
	</head>
	<body>
		<input type="hidden" value="${id}" id="consumerid">
		<div class="wrapper animated fadeInUp">
			<div class="btn-toolbar">
				<span id="add" class="greenbtn"><i class="fa fa-reply"></i>新增台卡</span>
				<span id="delete" class="redbtn"><i class="fa fa-times"></i>删除台卡</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	</body>
</html>
