<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>营业分析</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
	    <script type="text/javascript" src="${ctx}/assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="${ctx}/assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <link rel="stylesheet" href="${ctx}/assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
		<script src="${ctx}/app/js/DININGSYS/report/businessAnalysis/index.js"></script>
		
		<style type="text/css">
			.extra {width: 150px; border: 1px solid #f6a828; height:600px; float:left;overflow: auto;}
			.child {margin-left: 160px}
		</style>
	</head>
	
	<body style="overflow-y:auto;">
		<div class="wrapper animated fadeInUp">
			 <div id="menuTree" class="extra" >
            	<ul id="tree" class="ztree"></ul>
       		 </div>
       		 <div style="margin-left: 160px">
       		     <div class="childIframe">
       		     </div>
       		 </div>
	    </div>
	</body>
</html>