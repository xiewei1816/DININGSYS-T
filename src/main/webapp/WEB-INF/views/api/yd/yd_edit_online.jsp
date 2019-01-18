<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>预定管理</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
		<link rel="stylesheet" href="${ctx}/app/css/DININGSYS/deskBusiness/product_item/base.css">
		<link rel="stylesheet" href="${ctx}/app/css/DININGSYS/api/yd.css">
		<script src="${ctx }/app/js/api/yd/yd.js"></script>
		<script src="${ctx }/app/js/api/yd/ydEdit.js"></script>
		<script src="${ctx }/app/js/api/yd/ydSeats.js"></script>
		<script src="${ctx }/app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
	</head>
	<script>
	$(function () {
		ydEdit.pagerInit();
	});
	</script>
	<body>
		<input type="hidden"  name="posId" value="${posId}" id="posId"/>
		<form id="editForm">
			<div class="editForm">
				<ul class="edit-contents">
			    	<input type="hidden" name="id" value="${item.id}" id="pId"/>
			    	<input type="hidden" name="ydNum" value="${item.ydNum}" id="ydNum"/>
			    	<input type="hidden" name="state" value="${item.state}" id="state"/>
			    	<input type="hidden"  name="onlineMsg" value="${item.onlineMsg}" id="onlineMsg"/>
			        <li>
			            <span class="title"><span class="required">*</span>姓名</span>
			            <div class="form-group biger-wid">
			                <input placeholder="姓名" type="text" class="form-control" name="name" dete="val" value="${item.name}" <c:if test="${item.name != null}">readonly="readonly"</c:if>/>
			            </div>
			        </li>
			        <li>
			            <span class="title"><span class="required">*</span>人数</span>
			            <div class="form-group biger-wid">
			                <input placeholder="人数" type="text" class="form-control" name="number" dete="val" value="${item.number}"  <c:if test="${item.name != null}">readonly="readonly"</c:if>/>
			            </div>
			        </li>
			        <li>
			            <span class="title"><span class="required">*</span>电话</span>
			            <div class="form-group biger-wid">
			                <input placeholder="电话" type="text" class="form-control" name="phone" dete="val" value="${item.phone}" <c:if test="${item.name != null}">readonly="readonly"</c:if>/>
			            </div>
			        </li>
			        <li>
			            <span class="title"><span class="required">*</span>时间</span>
			            <div class="form-group biger-wid">
			                <input id="ydTime" placeholder="请选择时间" type="text" class="form-control" name="ydTime" dete="val" value="${item.ydTime}" readonly="readonly"/>
			            </div>
			        </li>
			        <li>
			            <span class="title"><span class="required">*</span>性别</span>
			            <div class="form-group biger-wid">
				            <select class="form-control" name="sex" <c:if test="${item.sex != null}">disabled="disabled"</c:if>>
								<option  value="1" <c:if test="${item.sex == 1}">selected</c:if>>男</option>
								<option  value="2" <c:if test="${item.sex == 2}">selected</c:if>>女</option>
						    </select>
					    </div>
			        </li>
			        <li>
			            <span class="title"><span class="required">*</span>客位类型</span>
			            <div class="form-group biger-wid">
				            <select class="form-control" name="crId" id="crId" <c:if test="${item.crId != null}">disabled="disabled"</c:if>>
								<option  value="-1" <c:if test="${item.crId == -1}">selected</c:if>>不限</option>
								<option  value="0" <c:if test="${item.crId == 0}">selected</c:if>>大厅</option>
								<option  value="1" <c:if test="${item.crId == 1}">selected</c:if>>包间</option>
						    </select>
					    </div>
			        </li>
			    </ul>
			</div>
		    <div style="clear:both;margin-left: 93px;">
		        <span class="title" style="display:inline-block;float:left;font-size: 14px;font-weight: 400;padding:7px 10px 0px 0px;">选择客位:</span>
		        <ul class="seats">
		       		 <c:forEach items="${seats}" var="o" varStatus="stat">
							 <li> ${o.seatName}
							 	<input type='hidden' name='seatIds[${stat.index}].seatId' value='${o.seatId}'/>
							 </li>
					  </c:forEach>
		        </ul>
		        <input class="btn btn-primary seatAdd" type="button" value="增加" id="seatAdd">
		     </div>
		     <div style="clear:both;position: absolute;bottom: 50px;left: 50%;  ">
				<button type="button" class="btn btn-success" id="submit">提交预定信息</button>
		     </div>
		</form>
	</body>
</html>