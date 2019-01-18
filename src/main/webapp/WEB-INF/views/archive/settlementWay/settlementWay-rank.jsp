<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>品项(品项小类)排序管理</title>
		<jsp:include page="../../head.jsp"/>
	    <script type="text/javascript" src="app/js/DININGSYS/archive/settlementWay/settlementWay-rank.js" ></script>
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="btn-toolbar">
				<span id="up" class="graybtn" title="上移"><i class="fa fa-arrow-up"></i>上移</span>
				<span id="down" class="royalbtn" title="下移"><i class="fa fa-arrow-down"></i>下移</span>
				<span id="topper" class="graybtn" title="置顶"><i class="fa fa-arrow-up"></i>置顶</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table-rank"></table>
                <!-- <div id="grid-pager"></div> -->
            </div>
	    </div>
	</body>
</html>
