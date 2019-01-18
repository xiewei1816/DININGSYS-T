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
		<%
			String single = (String)request.getSession().getAttribute("single");
			request.setAttribute("single", single);
		%>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
		<script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
		<script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.core.js"></script>
		<link rel="stylesheet" href="assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
	    <link rel="stylesheet" href="${ctx}/app/css/DININGSYS/deskBusiness/product_item/style.css">
	    <link rel="stylesheet" href="${ctx}/app/css/DININGSYS/deskBusiness/product_item/base.css">
	    <script src="${ctx}/app/js/DININGSYS/deskBusiness/product_item/allow_discount_plan.js"></script>
		<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan.css">
   		<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
   		<script src="app/js/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.js"></script>
   		<script src="app/js/DININGSYS/deskBusiness/product_item/allow_discount_pan_gate_add.js"></script>
		<script src="app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
		<script src="app/js/DININGSYS/deskBusiness/product_item/pro_disccount_pan_trash.js"></script>
	</head>
	
	<body>
		<div class="btn-toolbar">
		<c:if test="${single == '1' }">
			<span id="save" class="bluebtn"><i class="fa fa-file-o"></i>保存</span>
			<span id="add" class="greenbtn"><i class="fa fa-file-o"></i>新增</span>
			<span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
			<span id="delb" class="redbtn"><i class="fa fa-trash"></i>删除</span>
		</c:if>
		<span id="trash" class="graybtn"><i class="fa fa-trash-o"></i>回收站</span>
		<span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span>
		<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
	   </div>
	   <div class="jqGrid_wrapper jq-margin">
              <table id="grid-table-1"></table>
              <div id="grid-pager-1"></div> 
       </div>
       <div class="container-from">
       		<fieldset>
       			<legend class="container-title">一周内设置不同的品项打折方案</legend>
       			<div class="container-table">
		       		<table class="form-table" cellpadding="0" cellspacing="0">
						<thead class="fixed-thead" id="head">
							<tr>
								<th>星期</th>
								<th>品项打折方案</th>
							</tr>
						</thead>
						<tbody class="scroll-tbody" id="body">
							<c:forEach items="${allWeekDiscount}" var="item">
							 <tr>
								<td>${item.name}</td>
								<td>
									<select  class="select-week" id="${item.pId}" edl="${item.id}">
										<option value ="0" <c:if test="${0 == item.pId}" >selected</c:if> edl="${item.id}"></option>
										<c:forEach items="${allPan}" var="pan">
												<option value ="${pan.id}" <c:if test="${pan.id == item.pId}">selected</c:if> edl="${item.id}">${pan.name}</option>
										</c:forEach>
									</select>
								</td>
							 </tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
       		</fieldset>
       </div> 
	</body>
</html>
