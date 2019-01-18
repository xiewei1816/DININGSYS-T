<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>团队信息</title>
		<jsp:include page="../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
		<link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
		<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	    <script src="app/js/DININGSYS/api/goTeamList.js"></script>
	</head>
	<body style="overflow:auto;" >
		<div class="btn-toolbar" style="height:60px;">
			<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
			<span id="exitteam" class="graybtn"><i class="fa fa-reply"></i>退出团队</span>
			<span id="exitteamHide" style="display: none; color: gray;" class="graybtn"><i class="fa fa-reply"></i>退出团队</span>
			<span id="repeal" class="graybtn"><i class="fa fa-reply"></i>撤销转账</span>
			<span id="repealHide" style="display: none; color: gray;" class="graybtn"><i class="fa fa-reply"></i>撤销转账</span>
        </div>
		<div id="menuTree" class="menuTree" style=" border: 1px solid #C0C0C0; float: left; overflow:auto;">
			<ul id="myTree" class="ztree"></ul>
		</div>
		<div id="tableDiv" style="float:left; width:80%;height:360px;">
			<table id="grid-table" ></table>
	    </div>
	</body>
</html>
