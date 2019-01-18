<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>系统备份数据库管理</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <link rel="stylesheet" href="${ctx }/assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
		<script src="${ctx}/app/js/DININGSYS/sysSettings/backup/backup.js"></script>
		<script src="app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
	            <ul>
	                <li>
	                    <span class="title">备份人</span>
	                    <div class="form-group big-wid">
	                        <input type="text" class="form-control" name="backupName" />
	                    </div>
	                </li>
	                <li>
	                    <span class="title">备份文件名</span>
	                    <div class="form-group big-wid">
	                        <input type="text" class="form-control" name="backupFileName" />
	                    </div>
	                </li>
          		  </ul>
	            <div class="search-btns">
	                <button class="btn btn-primary">查询</button>
	            </div>
				</form> 
			</div>
			
			<div class="btn-toolbar">
		        <span class="add"><i class="fa fa-file-o"></i>备份</span>
		        <span id="del" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
		    </div>
        
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	</body>
</html>
