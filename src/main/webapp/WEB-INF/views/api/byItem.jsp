<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:forEach items="${itemVal}" var="data" varStatus="status">
	<div id="myTabContent" class="tab-content content-${status.index}" 	<c:if test="${status.index != 0}">style="display:none"</c:if> >
		<div class="tab-pane fade in active" id="${data.ow_num }">
				<ul class="c-ul">
					<li class="title">开单POS:</li>
					<li class="contents">${data.posName }</li>
					<li class="title">服务员:</li>
					<li class="contents">${data.waiterName }</li>
				</ul>
				<ul class="c-ul">
					<li class="title">开单时间:</li>
					<li class="contents">${data.first_time }</li>
					<li class="title">人数:</li>
					<li class="contents">${data.people_count }</li>
				</ul>
				<!-- <ul class="c-ul">
					<li class="title">客户:</li>
					<li class="contents">&lt;无&gt;</li>
					<li class="title">单据:</li>
					<li class="contents">${data.documents }</li>
				</ul> -->
				<table style="width: 100%; border-collapse: collapse;">
				<tr
					style="border-top: #ff0000 1px solid; border-bottom: #ff0000 1px solid;">
					<td class="title width20 red">代码</td>
					<td class="title width25 red">品项</td>
					<td class="title width10 center red">单位</td>
					<td class="title width15 center red">价格</td>
					<!--  <td class="title width15 red">注</td>-->
					<td class="title width10 center red">数量</td>
					<td class="title width15 center red">小计</td>
				</tr>
				<c:forEach items="${data.list}" var="datas" varStatus="s">
					<tr style="border-bottom: dotted 1px #ff0000;">
						<td>${datas.itemNum }</td>
						<td>${datas.itemName }</td>
						<td class="center">${datas.unit}</td>
						<td class="center">${datas.item_final_price }</td>
						<!--  <td></td>-->
						<td class="center">${datas.item_file_number }</td>
						<td class="center">${datas.subtotal }</td>
					</tr>
				</c:forEach>
				</table>
			<table class="apiMainTable" style="width: 98%;margin: 5px;">
				<tr>
					<td class="apiMainTablehead">服务费：</td>
					<td>${data.service_charge}</td>
					<td class="apiMainTablehead">包房费：</td>
					<td>${data.private_room_cost}</td>
				</tr>
				<tr>
					<td class="apiMainTablehead" style="width: 120px">客用单打印次数：</td>
					<td>${data.kyd_print_num}</td>
					<td class="apiMainTablehead" style="width: 120px">预接单打印次数：</td>
					<td>${data.yjd_print_num}</td>
				</tr>
				<tr>
					<td class="apiMainTablehead">团队成员：</td>
					<td colspan="3">
						<c:forEach varStatus="index" var="team" items="${data.teamMembers}">
							${team.name}<c:if test="${!(fn:length(data.teamMembers) eq index.index+1)}">，</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td class="apiMainTablehead" colspan="2">品项小计（不含附属费用）：</td>
					<td colspan="2">${data.subtotal}</td>
				</tr>
			</table>
				<%--<ul class="c-ul top10">
					<li class="title ">服务费:</li>
					<li class="contents">${data.service_charge}</li>
					<li class="title">包房费</li>
					<li class="contents">${data.private_room_cost}</li>
				</ul>
				<ul class="c-ul top10">
					<li class="title ">客用单打印次数:</li>
					<li class="contents">${data.kyd_print_num}</li>
					<li class="title">预接单打印次数:</li>
					<li class="contents">${data.yjd_print_num}</li>
				</ul>
				<ul class="c-ul">
					<li class="title">团队成员:</li>
					<li>
						<c:forEach var="team" items="${data.teamMembers}">
							${team.name}/
						</c:forEach>

					</li>
				</ul>
				<ul class="c-ul" style="margin-left: -17px;">
					<li style="font-size: 18px; font-weight: bold;">品项小计（不含附属费用）:</li>
					<li style="font-size: 18px; font-weight: bold;">${data.subtotal }</li>
				</ul>--%>
		</div>
	</div>
</c:forEach>