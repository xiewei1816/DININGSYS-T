<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/app/css/DININGSYS/api/main.css"> 
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap.min14ed.css?v=3.3.6" />
<script type="text/javascript" src="${ctx}/assets/scripts/jquery-2.1.1.js" ></script>
<script type="text/javascript" src="${ctx}/assets/scripts/bootstrap.min.js" ></script>
<script src="${ctx}/app/js/api/seatWaterDetailByItem.js"></script>
<html>
<head>
<meta charset="UTF-8">
<title>当前客单</title>
<style>
	.apiMainTablehead{
		font-weight: bold;
		font-size: 13px
	}
</style>
<script>
$(function () {
	seatWaterDetail.pagerInit();
});

</script>
</head>
<body>
	<div class="content">
		<div class="header">
			<input type="hidden" id="seatId" value="${seatId}">
			<h1 class="tablename">${seatInfo.number}-${seatInfo.name}</h1>
			<h2 class="qy">消费区域:${seatInfo.consName}</h2>
			<h2 class="qy">客位标签:<c:choose><c:when test="${seatInfo.seatLabel != null && seatInfo.seatLabel != ''}">${seatInfo.seatLabel}</c:when><c:otherwise><无></c:otherwise></c:choose></h2>
			<div class="line"></div>
			<div class="my-select">
				  <label>
				    <input type="radio" name="mType" value="1" checked="checked">
				   		品项
				  </label>
				  <label>
				    <input type="radio" name="mType" value="2" >
				   		品项大类分组
				  </label>
				  <label>
				    <input type="radio" name="mType" value="3" >
				   		服务
				  </label>
				  <label>
				    <input type="radio" name="mType" value="4" >
				   		服务(包含退单)
				  </label>
			</div>
		</div>
		<ul class="nav nav-tabs" role="tablist" >
			<c:forEach items="${itemVal}" var="data" varStatus="status">
				<c:if test="${status.index == 0 }">
				 	<li role="presentation" class="active"  style="position:relative;float: left;width:30%;"><a style="cursor:pointer;text-decoration: none;height: 55px;line-height: 17px;padding-top: 3px;font-size:12px;width:98%;word-wrap:break-word;word-break:break-all;" id="tab-${status.index}">${data.ow_num}</a></li>
				</c:if>
				<c:if test="${status.index != 0 }">
				 	<li role="presentation"  style="position:relative;float: left;width:30%;"><a style="cursor:pointer;text-decoration: none;height: 55px;line-height: 17px;padding-top: 3px;font-size:12px; width:98%;word-wrap:break-word;word-break:break-all;" id="tab-${status.index}">${data.ow_num}</a></li>
				</c:if>
			 </c:forEach>
		</ul>
		<div class="tablecontent" id="tablecontent">
			<div id="byItem">
				<c:import url="byItem.jsp" ></c:import>
			</div>
			<div id="byService" style="display: none;">
				<c:import url="byService.jsp" ></c:import>
			</div>
			<div id="byBigItem" style="display: none;">
				<c:import url="byBigItem.jsp" ></c:import>
			</div>
			<div id="byServiceContain" style="display: none;">
				<c:import url="byServiceContainBack.jsp" ></c:import>
			</div>
		</div>
	</div>
</body>
</html>

<script type="text/javascript">
	var bodyheith = document.body.clientHeight;
	var h = bodyheith - 225;
	var o = document.getElementById('tablecontent');//获得元素
	o.style.height = h + 'px';
</script>
