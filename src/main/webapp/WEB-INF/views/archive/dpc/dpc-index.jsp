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
		<title>公共代码管理</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
		<link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
		<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	    <script src="app/js/DININGSYS/archive/dpc/dpc.js"></script>
	</head>
	<body>
		<div class="btn-toolbar">
			<c:if test="${single == '1' }">
			<span id="add" class="greenbtn"><i class="fa fa-file-o"></i>新增</span>
			<span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
			<span id="updateHide" style="display: none; color: gray;" class="graybtn"><i class="fa fa-edit"></i>修改</span>
			<span id="delb" class="redbtn"><i class="fa fa-trash"></i>删除</span>
			</c:if>
			<span id="delbHide" style="display: none; color: gray;" class="graybtn"><i class="fa fa-trash"></i>删除</span>
			<span id="delo" class="graybtn"><i class="fa fa-trash-o"></i>回收站</span>
			<span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span>
			<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
        </div>
		<div class="menuTree" style="width: 20%; border: 1px solid #C0C0C0;float: left; overflow:auto;">
			<ul id="myTree" class="ztree"></ul>
		</div>
		<div class="wrapper animated fadeInUp" style="width: 80%;float: left;">
	        <div class="jqGrid_wrapper">
                <table id="grid-table" ></table>
			</div>
	    </div>
	</body>
</html>
