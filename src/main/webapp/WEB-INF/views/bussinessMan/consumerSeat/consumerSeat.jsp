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
	    <link rel="stylesheet" href="${ctx}/app/css/DININGSYS/bussinessMan/consumerSeat/consumerSeat.css">
	   	<link rel="stylesheet" href="${ctx}/app/css/DININGSYS/bussinessMan/consumerSeat/consumerBffPro.css">
	    <script src="${ctx}/app/js/DININGSYS/bussinessMan/consumerSeat/consumerSeat.js"></script>
	    <script src="app/js/DININGSYS/bussinessMan/consumerSeat/checkUpdVals.js"></script>
	    <script src="app/js/DININGSYS/bussinessMan/consumerSeat/bf_pro.js"></script>
	    <script src="app/js/DININGSYS/bussinessMan/consumerSeat/bf_pro_add.js"></script>
	    <script src="app/js/DININGSYS/bussinessMan/consumerSeat/bf_pro_trash.js"></script>
	    
	    <link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
		<script src="app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
		<script src="app/js/DININGSYS/deskBusiness/product_item/gift_item_plan_host_item_add.js"></script>
		<script src="app/js/DININGSYS/bussinessMan/consumerSeat/dish_add.js"></script>
	</head>
	
	<body>
		<div class="btn-toolbar">
		<c:if test="${single == '1' }">
			<span id="save" class="bluebtn"><i class="fa fa-file-o"></i>保存</span>
		</c:if>
		<span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span>
		<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
	   </div>
	   
	   <p class="title-bar">客位管理:${seat.name}</p>
	   
	   <ul class="nav nav-tabs" role="tablist" style="height: 30px;">
		  <li role="presentation" class="active" ><a style="cursor:pointer;text-decoration: none;height: 30px;padding-top: 3px;" id="setting1_btn">设置项目一</a></li>
		  <li role="presentation" ><a style="cursor:pointer;text-decoration: none;height: 30px;padding-top: 3px;" id="setting2_btn">设置项目二</a></li>
	   </ul>
	   <input type="hidden"  id="seat" value="${seat.id}">
	   <form class="form-horizontal" role="form" id="editForm" >
	   	   <input type="hidden" name="id" id="id" value="${man.id}">
	   	   <input type="hidden" name="seatId" id="seatId" value="${man.seatId}">
	   	   <div class="setting1">
		   <div class="head-manager">
		   		<div class="min-consumption-area">
		   		  <fieldset class="discount-fieldset">
		   		  		<legend class="discount-title">最低消费</legend>
			       		<div class="radio">
						  <label>
						    <input type="radio" name="zdxf" id="zdxf" value="1" <c:if test="${man == null || man.zdxf == '1'}">checked</c:if>>
						 		   不收取最低消费
						    </label>
						</div>
						<div class="radio-line form-inline">
							<label>
						    	<input type="radio" name="zdxf" id="zdxf" value="2" <c:if test="${man.zdxf == '2'}">checked</c:if>>
						    </label>
						    <select  class="form-control sele-control" name="zdxfType" style="width: 130px" isshow>
								<option value="1" <c:if test="${man.zdxfType == '1'}">selected</c:if>>按每客位</option>
								<option value="2" <c:if test="${man.zdxfType == '2'}">selected</c:if>>按每客位人数</option>
	                   		</select>
						</div>
						<div class="form-inline ">
						      <span class="text-1 zdxfMoney_title" >收取:</span>
					          <input  type="text" maxlength="6" class="form-control edit-control decimal" name="zdxfMoney"  value="${man.zdxfMoney}" style="width: 110px" dete="em-money" isshow_extra/>
						</div>
					</fieldset>
		   		</div>
		   		
		   		<div class="service-charge-area">
		   			 <fieldset class="discount-fieldset">
			       		<legend class="discount-title">服务费</legend>
			       		<div class="radio radio-margin">
						  <label>
						    <input type="radio" name="fwf" value="1" <c:if test="${man == null || man.fwf == '1'}">checked</c:if>>
						 		不收取服务费
						  </label>
						</div>
						<div class="radio">
							<label>
							    <input type="radio" name="fwf" value="2" <c:if test="${man.fwf == '2'}">checked</c:if>>
							    	按固定金额收取:
						    </label>
						    <input  type="text" maxlength="6" class="form-control edit-control decimal" name="fwfGd" value="${man.fwfGd}" dete="em-money" isshow/>
						</div>
						<div class="radio">
							<label>
							    <input type="radio" name="fwf" value="3" <c:if test="${man.fwf == '3'}">checked</c:if>>
							    	按消费比例收取:
						    </label>
						    <input  type="text" maxlength="6" class="form-control edit-control discount"  name="fwfXfRatio" value="${man.fwfXfRatio}" dete="discount" isshow/>
						</div>
						<div class="radio">
							<label>
							    <input type="radio" name="fwf" value="4" <c:if test="${man.fwf == '4'}">checked</c:if>>
							    	按客位人数收取:
						    </label>
						    <input  type="text" maxlength="6" class="form-control edit-control decimal"  name="fwfPeople" value="${man.fwfPeople }" dete="em-money" isshow/>
						</div>
						<div class="form-inline">
						      <span class="text-1" >服务费上限(0为不限制):</span>
					          <input  type="text" maxlength="6" class="form-control edit-control decimal" name="fwfSx" value="${man.fwfSx }"  dete="em-money" style="width: 95px" isshow_extra/>
						</div>
						<div class="form-inline">
						      <span class="text-1" >消费满:</span>
					          <input  type="text" maxlength="6" class="form-control edit-control decimal" name="fwfConFree" value="${man.fwfConFree}" dete="em-money" style="width: 110px" isshow_extra/>
					          <span class="text-1" >不收取服务费</span>
						</div>
		   		 	 </fieldset>	
		   		</div>
		   		
		   		<div class="global-settings-area">
		   			 <fieldset class="discount-fieldset">
			       		<legend class="discount-title">全局设置
			       		</legend>
			       		 <button type="button" class="btn btn-default sele-btn" id="bf-fa">包房收费方案</button>
		   		 	 </fieldset>	
		   		</div>
		   		</div>
		   		</div>
		   		
		   		<div class="setting2" style="display: none">
		   		<div class="head-manager">
		   		<div class="bottom-left">
		   			<div class="billing-additem-area">
			   			<fieldset class="discount-fieldset">
				       		<legend class="discount-title">开单自动增加品项
				       		</legend>
				       		    <div class="limit-left">
				       				  <table id="grid-table-2"></table>
				       			</div>
				       			<c:if test="${single == '1' }">
					       			<div class="limit-right">
					       				<input type="button" value="增加" class="btn-add-1" id="btn-add">
										<input type="button" value="减少" class="btn-sub-1" id="btn-sub">
										<input type="button" value="清空" class="btn-clear-1" id="btn-clear">
					       			</div>
				       			</c:if>
			   		 	 </fieldset>	
		   			</div>
		   			<div class="clear-area">
		   				<fieldset class="discount-fieldset">
			       		<legend class="discount-title">清扫状态设置
			       		</legend>
			       		<div class="form-inline">
						      <span class="text-1" >清扫时长:</span>

								<c:if test="${man.qssc == null}">
									<input  type="text" maxlength="6" name="qssc" class="form-control edit-control number" id="qssc" value="1" dete="isnumber" style="width: 110px"/>
								</c:if>
								<c:if test="${man.qssc != null}">
									<input  type="text" maxlength="6" name="qssc" class="form-control edit-control number" id="qssc" value="${man.qssc}" dete="isnumber" style="width: 110px"/>
								</c:if>
					          <span class="text-1" >分钟</span>
						</div>
						<div class="checkbox">
						  <label>
						    <input type="checkbox" name="qsscTx" id="qsscTx" value="${man.qsscTx}" <c:if test="${man.qsscTx == '1'}">checked</c:if> >
						 		  启动清扫时长语音提醒功能
						  </label>
						</div>
						</fieldset>
		   			</div>
		   		</div>
		   		
		   		<div class="bottom-right">
		   			<fieldset class="discount-fieldset">
			       		<legend class="discount-title">包房收费
			       		</legend>
			       		<div class="radio radio-margin">
						  <label>
						    <input type="radio" name="bff" id="bff" value="1" <c:if test="${man == null || man.bff == '1'}">checked</c:if>>
						 		不收取包房费
						  </label>
						</div>
						<div class="radio">
							<label>
							    <input type="radio" name="bff"  value="2" <c:if test="${man.bff == '2'}">checked</c:if>>
							    	按固定金额收取:
						    </label>
						    <input  type="text" maxlength="6" name="bffGd" class="form-control edit-control decimal" id="bffGd" value="${man.bffGd}" dete="em-money" isshow/>
						</div>
						<div class="radio">
							<label>
							    <input type="radio" name="bff"  value="3" <c:if test="${man.bff == '3'}">checked</c:if>>
							    	按客位人数收取:
						    </label>
						    <input  type="text" maxlength="6" name="bffPeople" class="form-control edit-control decimal" id="bffPeople" value="${man.bffPeople}" dete="em-money" isshow/>
						</div>
						<div class="radio">
							<label>
							    <input type="radio" name="bff"  value="4" <c:if test="${man.bff == '4'}">checked</c:if>>
							    	固定包房收费方案:
						    </label>
						    <select  class="form-control edit-control" name="bffGdPro" id="bffGdPro" style="width: 130px" isshow value="${man.bffGdPro}">
								<c:forEach items="${scheme}" var="item">
										<option value="${item.id}" <c:if test="${man.bffGdPro == item.id}">selected</c:if>>${item.name}</option>
								</c:forEach>
	                   		</select>
						</div>
						<div class="radio">
							<label>
							    <input type="radio" name="bff"  value="5" <c:if test="${man.bff == '5'}">checked</c:if>>
							    	一周内设置不同的包房收费方案:
						    </label>
						</div>
						<div class="form-inline" style="margin-left: 10px">
							 <input type="hidden" name="bffWeekProD" id="bffWeekProD" value="${item.bffWeekProD}">
							 <table id="grid-table-3"></table>
					    </div>
			       		<div class="form-inline">
						      <span class="text-1" >包房费品项:</span>
						      <input  type="hidden" name="bffItemId" class="form-control edit-control val" id="bffItemId"  value="${man.bffItemId}"/>
					          <input  type="text" name="bffItemName" class="form-control edit-control val" id="bffItemName" style="width: 110px"  value="${man.bffItemName}" readonly="readonly" dete="val" isshow_extra/>
					          <c:if test="${single == '1' }">
					          	<input type="button" value="..." class="h-btn" id="add-m1">
					          </c:if>
						</div>
						<div class="form-inline">
						      <span class="text-1" >消费满:</span>
					          <input  type="text" maxlength="6" name="bffConFree" class="form-control edit-control decimal" value="${man.bffConFree}" style="width: 110px" dete="em-money" isshow_extra/>
					          <span class="text-1" >不收取包房费</span>
						</div>
						<div class="checkbox">
						  <label>
						    <input type="checkbox" name="bffTiming" id="bffTiming" value="${man.bffTiming}" <c:if test="${man.bffTiming == '1'}">checked</c:if>>
						 		 包房启用时自动启动包房计时
						  </label>
						</div>
						</fieldset>
		   		   </div>
		   		</div>
		     </div>
	   </form>
	</body>
</html>
