<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>到期提醒管理</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
		<script src="${ctx }/app/js/DININGSYS/sysSettings/reminder/reminder.js"></script>
	</head>
	<style>
	   html,body{margin:0px;padding:0px;}
	   .left{width:50%;float:left;}
	   .right{margin-left:50%;margin-top: 10px;}
	   .title{margin: 10px 20px 10px 20px;}
	</style>
	<body>
        	<div class="left">
	        	<h5 class="title">品项打折方案有效期提醒</h5>
	        	<div class="jqGrid_wrapper" style="margin:0px 20px 0px 20px;">
	                <table id="grid-table-item-discount"></table>
	            </div>
	            
	            <h5 class="title">重要活动打折方案有效期提醒</h5>
	        	<div class="jqGrid_wrapper" style="margin:0px 20px 0px 20px;">
	                <table id="grid-table-important-discount"></table>
	            </div>
        	</div>
        	<div class="right">
        		<h5 class="title" style="padding-top: 10px;">品项促销方案方案有效期提醒</h5>
	        	<div class="jqGrid_wrapper" style="margin:0px 20px 0px 20px;">
	                <table id="grid-table-pro-discount"></table>
	            </div>
        	    <h5 class="title">品项会员方案有效期提醒</h5>
	        	<div class="jqGrid_wrapper" style="margin:0px 20px 0px 20px;">
	                <table id="grid-table-member-discount"></table>
	            </div>
        	</div>
	</body>
</html>
