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
		<title>退菜原因管理</title>
		<jsp:include page="../../head.jsp"/>
	    <script src="app/js/DININGSYS/archive/rfc/rfc.js"></script>
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
								<input type="text" class="form-control" id="rfcCode"  name="rfcCode" />
							</div>
						</li>
						<li>
							<span class="title">名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="rfcName" name="rfcName" />
							</div>
						</li>
					</ul>
					<div class="search-btns">
						<button class="btn btn-primary">查询</button>
					</div>
				</form> 
			</div>
			<div class="btn-toolbar">
			   	<c:if test="${single == '1' }">
					<span id="add" class="greenbtn"><i class="fa fa-file-o"></i>新增</span>
					<span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
					<span id="delb" class="redbtn"><i class="fa fa-trash"></i>删除</span>
					<span id="delo" class="graybtn"><i class="fa fa-trash-o"></i>回收站</span>
				</c:if>
				<span id="gorfct" class="royalbtn"><i class="fa fa-list-alt"></i>退菜原因类型</span>
				<span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span>
				<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    <jsp:include page="rfc-edit.jsp"></jsp:include>
	</body>
</html>
