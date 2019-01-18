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
		<title>核对单据</title>
		<jsp:include page="../head.jsp"/>
	</head>
	<body>
		<div id="myTabContent" class="tab-content">
			<div style="width: 100%; background-color: #ff6041;">
				<table class="header">
					<tr>
						<td>客位：</td>
						<td><input type="text" readonly style="width: 180px;" value="${data.seatName }"/></td>
						<td>营业流水：</td>
						<td><input type="text" readonly style="width: 180px;" value="${data.owNum }"/></td>
					</tr>
				</table>
			</div>
			<ul id="myTab" class="nav nav-tabs">
				<c:forEach items="${data.checkDocList}" var="data" varStatus="status">
					<c:if test="${status.index eq 0}">
						<li class="active"><a href="#${data.serviceNum }" data-toggle="tab">${data.serviceNum }</a></li>
					</c:if>
					<c:if test="${status.index gt 0}">
						<li><a href="#${data.serviceNum }" data-toggle="tab">${data.serviceNum }</a></li>
					</c:if>
				</c:forEach>
			</ul>
			<c:forEach items="${data.checkDocList}" var="data" varStatus="status">
				<c:if test="${status.index eq 0}">
					<div class="tab-pane fade in active" id="${data.serviceNum }">
				</c:if>
				<c:if test="${status.index gt 0}">
					<div class="tab-pane fade" id="${data.serviceNum }">
				</c:if>
					<table style="width: 100%;">
						<tr>
							<td colspan="6" style="text-align: center; padding-top: 10px;">
								<!-- 1/开单客位自增品项 2/加单  3/赠单  4/退单 -->
								<c:if test="${data.serviceType eq 1}">
									<p style="font-size:24px; color:green; font-weight: bold; ">开单</p>
								</c:if>
								<c:if test="${data.serviceType eq 2}">
									<p style="font-size:24px; color:green; font-weight: bold; ">加单</p>
								</c:if>
								<c:if test="${data.serviceType eq 3}">
									<p style="font-size:24px; color:gray; font-weight: bold; ">赠单</p>
								</c:if>
								<c:if test="${data.serviceType eq 4}">
									<p style="font-size:24px; color:red; font-weight: bold; ">退单</p>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="title">服务流水:</td><td>${data.serviceNum }</td>
							<td class="title">市别:</td><td>${data.openBis }</td>
							<td class="title">手工单号:</td><td> &lt;无&gt;</td>
						</tr>
						<tr>
							<td class="title">点菜员:</td><td colspan="2">${data.empName }</td>
							<td class="title">授权人:</td><td colspan="2">${data.empName }</td>
						</tr>
						<tr>
							<td class="title">时间:</td><td colspan="2">${data.serviceTime }</td>
							<td class="title">服务POS:</td><td colspan="2">${data.serPos }</td>
						</tr>
					</table>
					
					<table style="width: 100%;">
						<tr style="border-top: green 1px solid;border-bottom: green 1px solid;">
							<td class="title">品项</td>
							<td class="title">价格</td>
							<td class="title">数量</td>
							<td class="title">只数</td>
							<td class="title">注</td>
							<td class="title">单位</td>
							<td class="title">制作费用</td>
							<td class="title">小计</td>
						</tr>
						<c:forEach items="${data.itemFileDatas}" var="datas" varStatus="status">
							<tr style="border-bottom: green 1px solid;">
								<td>${datas.name }</td>
								<td>${datas.item_final_price }</td>
								<td>${datas.item_file_number }</td>
								<td>${datas.item_file_zs }</td>
								<td>&nbsp;</td>
								<td>${datas.unit }</td>
								<td>${datas.production_costs }</td>
								<td>${datas.subtotal }</td>
							</tr>
						</c:forEach>
						<tr>
							<td style="font-weight: bold;">合计</td>
							<td></td>
							<td style="font-weight: bold;">${data.slTotal }</td>
							<td style="font-weight: bold;">${data.zsTotal }</td>
							<td></td>
							<td></td>
							<td style="font-weight: bold;">${data.zzTotal }</td>
							<td style="font-weight: bold;">${data.total }</td>
						</tr>
					</table>
					<table style="width: 100%;">
						<tr>
							<td class="title">串口厨打次数:</td><td>0</td>
							<td class="title">串口厨打时间:</td><td></td>
						</tr>
						<tr>
							<td class="title">并口厨打次数:</td><td>0</td>
							<td class="title">并口厨打时间:</td><td></td>
						</tr>
						<tr>
							<td class="title">打印服务厨打次数:</td><td>0</td>
							<td class="title">打印服务厨打时间:</td><td></td>
						</tr>
					</table>
				</div>
			</c:forEach>
		</div>
	</body>
</html>