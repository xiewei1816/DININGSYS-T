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
		<script src="${ctx}/app/js/DININGSYS/sysSettings/urlset/urlset.js"></script>
		<script src="app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
	            <ul>
	                <li>
	                    <span class="title">编码</span>
	                    <div class="form-group big-wid">
	                        <input type="text" class="form-control" name="code" />
	                    </div>
	                </li>
	                <li>
	                    <span class="title">名称</span>
	                    <div class="form-group big-wid">
	                        <input type="text" class="form-control" name="name" />
	                    </div>
	                </li>
          		  </ul>
	            <div class="search-btns">
	                <button class="btn btn-primary">查询</button>
	            </div>
				</form> 
			</div>
			
			<div class="btn-toolbar">
				<span id="add" class="greenbtn"><i class="fa fa-file-o"></i>新增</span>
				<span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
		    </div>
        
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	</body>
</html>
