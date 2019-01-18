<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style type="text/css">
	body {margin:0;padding:0;height:100%;overflow:auto;}
	#myTabContent{height:100%;overflow:auto;}
	table tr {height:30px;}
	table td {font-size:12px;padding-left:20px;}
	.title{font-weight:bold;}
</style>
<html>
	<head>
		<meta charset="UTF-8">
		<title>当前客单</title>
		<jsp:include page="../head.jsp"/>
	</head>
	<body>
		<div id="myTabContent" class="tab-content">
			<div style="width: 100%; background-color: #ff6041;">
				<table class="header">
					<tr>
						<td style="font-size: 18px; font-weight: bold;">${data.seatName }</td>
						<td>消费区域：${data.areaName }</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<c:if test="${not empty data.seatLabel}">
							<td>客位标签:${data.seatLabel }</td>
						</c:if>
						<c:if test="${empty data.seatLabel}">
							<td>客位标签：&lt;无&gt;</td>
						</c:if>
					</tr>
				</table>
			</div>
			<ul id="myTab" class="nav nav-tabs">
				<c:forEach items="${data.resultList}" var="data" varStatus="status">
					<c:if test="${status.index eq 0}">
						<li class="active"><a href="#${data.owNum }" data-toggle="tab">${data.owNum }</a></li>
					</c:if>
					<c:if test="${status.index gt 0}">
						<li><a href="#${data.owNum }" data-toggle="tab">${data.owNum }</a></li>
					</c:if>
				</c:forEach>
			</ul>
			<c:forEach items="${data.resultList}" var="data" varStatus="status">
				<c:if test="${status.index eq 0}">
					<div class="tab-pane fade in active" id="${data.owNum }">
				</c:if>
				<c:if test="${status.index gt 0}">
					<div class="tab-pane fade" id="${data.owNum }">
				</c:if>
					<table style="width: 100%;">
						<tr>
							<td class="title">开单POS:</td><td>${data.posName }</td>
							<td class="title">开单时间:</td><td>${data.firstTime }</td>
							<td class="title">人数:</td><td>${data.peopleCount }</td>
							<td class="title">原始客位:</td><td>${data.seatName}</td>
						</tr>
						<tr>
							<td class="title">服务员:</td><td>${data.empName }</td>
							<td class="title">客户:</td><td> &lt;无&gt;</td>
							<td class="title">单据:</td><td>${data.documents }</td>
							<td class="title">更换客位时间:</td><td>${data.joinTeamTime}</td>
						</tr>
					</table>
					<table style="width: 100%;">
						<tr style="border-top: green 1px solid;border-bottom: green 1px solid;">
							<td class="title">代码</td>
							<td class="title">品项</td>
							<td class="title">规格</td>
							<td class="title">单位</td>
							<td class="title">价格</td>
							<td class="title">折让</td>
							<td class="title">注</td>
							<td class="title">数量</td>
							<!-- <td class="title">只数</td> -->
							<td class="title">小计</td>
							<!-- <td class="title">上菜方式</td>
							<td class="title">上菜情况</td>
							<td class="title">客座</td> -->
						</tr>
						<c:forEach items="${data.itemFileDatas}" var="datas" varStatus="status">
							<tr style="border-bottom: green 1px solid;">
								<td class="title">${datas.num }</td>
								<td>${datas.name }</td>
								<td>${datas.gg }</td>
								<td>${datas.unit }</td>
								<td>${datas.item_final_price }</td>
								<td>${datas.discount }</td>
								<c:if test="${datas.service_type ne 4}">
									<td></td>
								</c:if>
								<c:if test="${datas.service_type eq 4}">
									<td style="color: red;">(退单)</td>
								</c:if>
								<td>${datas.item_file_number }</td>
								<%-- <td>${datas.item_file_zs }</td> --%>
								<td>${datas.subtotal }</td>
								<%-- <td>${datas.servingType }</td>
								<td>${datas.servingCase }</td>
								<td>${datas.guest }</td> --%>
							</tr>
						</c:forEach>
					</table>
					
					<table style="width: 100%;">
						<tr>
							<td class="title">预接单打印次数:</td>
							<td>${data.kyd_print_num}</td>
							<td class="title">客用单打印次数:</td>
							<td>${data.yjd_print_num}</td>
						</tr>
						<tr>
							<td class="title">团队成员:</td>
							<td><div style="width: 160px;">${data.teamMember}</div></td>
							<td style="font-size: 18px; font-weight: bold;">合计:</td>
							<td style="font-size: 18px; font-weight: bold;">${data.total }</td>
						</tr>
					</table>
				</div>
			</c:forEach>
		</div>
	</body>
</html>
