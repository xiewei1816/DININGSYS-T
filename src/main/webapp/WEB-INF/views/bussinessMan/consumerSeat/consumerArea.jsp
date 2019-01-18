<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>品项管理管理</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
		<script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
		<script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.core.js"></script>
		<link rel="stylesheet" href="assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
	    <link rel="stylesheet" href="${ctx}/app/css/DININGSYS/deskBusiness/product_item/style.css">
	    <link rel="stylesheet" href="${ctx}/app/css/DININGSYS/deskBusiness/product_item/base.css">
	    <script src="${ctx}/app/js/DININGSYS/bussinessMan/consumerSeat/consumerArea.js"></script>
	    <style type="text/css">
	    	.title-bar{
	    	font-size: 15;
	    	padding-top: 5px;
	    	padding-bottom: 5px;
	    	font-weight: 600;
	    	}
	    
	    </style>
	</head>
	
	<body>
		<div class="btn-toolbar">
		<!-- <span id="save" class="bluebtn"><i class="fa fa-file-o"></i>保存</span> -->
		<span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span>
		<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
	   </div>
	   <p class="title-bar">消费区域:${count}</p>
	   <div class="jqGrid_wrapper jq-margin">
              <table id="grid-table-1"></table>
       </div>
       <p class="title-bar">客位:${seatCount}</p>
       <div class="jqGrid_wrapper jq-margin">
              <table id="grid-table-2"></table>
       </div>
	</body>
</html>
