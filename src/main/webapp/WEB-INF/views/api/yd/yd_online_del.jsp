<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>预定管理</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
		<link rel="stylesheet" href="${ctx}/app/css/DININGSYS/deskBusiness/product_item/base.css">
		<link rel="stylesheet" href="${ctx}/app/css/DININGSYS/api/yd.css">
		<script src="${ctx }/app/js/api/yd/ydOnlineDel.js"></script>
	</head>
	
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				  <form class="query-pan" id="query-pan">
				  <input type="hidden" name="state" value="3"/>
	              <ul>
	                <li>
	                    <span class="title">查询条件:</span>
	                    <div class="form-group big-wid">
	                        <input type="text" class="form-control" name="search" placeholder="请输入姓名/电话/查询线上预定"/>
	                    </div>
	                </li>
          		  </ul>
		          <div class="search-btns">
		                <button class="btn btn-primary">查询</button>
		          </div>
				</form> 
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	</body>
</html>
