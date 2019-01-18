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
	    <script type="text/javascript" src="${ctx}/assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="${ctx}/assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <link rel="stylesheet" href="${ctx}/assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
		<script src="${ctx }/app/js/DININGSYS/deskBusiness/deskbusinesssetting/index.js"></script>
		
		<style type="text/css">
			.extra {width: 200px; border: 1px solid #f6a828; float: left;}
			.child {margin-left: 210px}
		</style>
	</head>
	
	<body>
		<div class="wrapper animated fadeInUp">
			 <div id="menuTree" class="extra" >
            	<ul id="tree" class="ztree" style="width: 210px; overflow: auto;"></ul>
       		 </div>
			<!--  <div class="page-content" style="margin-left: 210px">
       		 </div> -->
       		 <div style="margin-left: 210px">
       		     <div class="childIframe"></div>
       		 </div>
	    </div>
	</body>
</html>