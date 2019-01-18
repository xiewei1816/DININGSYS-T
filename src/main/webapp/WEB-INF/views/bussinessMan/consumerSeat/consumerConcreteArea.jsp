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
	    <link rel="stylesheet" href="${ctx}/app/css/DININGSYS/bussinessMan/consumerSeat/consumerConcreteArea.css">
	    <script src="${ctx}/app/js/DININGSYS/bussinessMan/consumerSeat/consumerconreteArea.js"></script>
	    
	    <link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
		<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/base.css">
		<script src="app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
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
	   <input type="hidden" id="area_id" value="${area.id}"/>
	   <p class="title-bar">消费区域管理:${area.name}</p>
	   <div class="area-from">
	   <form class="form-horizontal" role="form" id="editForm" >
	   	   <input type="hidden" name="id" value="${manager.id}"/>
	   	   <input type="hidden" name="areaId" value="${area.id}" id="areaId"/>
		   <div class="head-manager">
		   		<div class="discount-area">
		   		  <fieldset class="discount-fieldset">
			       		<legend class="discount-title">打折选项</legend>
						<ul>
							<li>
							  <div class="checkbox left-5">
							 	  <label>
							      	<input type="checkbox"  name="sjDiscount" <c:if test='${manager.sjDiscount == 1}'> checked</c:if>>时价品项允许打折
							      </label>
							  </div>
							</li>
							<li>
							  <div class="checkbox left-5">
							 	  <label>
							      	<input type="checkbox"  name="cxDiscount" <c:if test='${manager.cxDiscount == 1}'> checked</c:if>>促销品项允许打折
							      </label>
							  </div>
							</li>
							<li>
							  <div class="checkbox left-5">
							 	  <label>
							      	<input type="checkbox"  name="bjDiscount" <c:if test='${manager.bjDiscount == 1}'> checked</c:if>>变价品项允许打折
							      </label>
							  </div>
							</li>
							<li>
							  <div class="checkbox left-5">
							 	  <label>
							      	<input type="checkbox"  name="bffDiscount" <c:if test='${manager.bffDiscount == 1}'> checked</c:if>>包房费允许打折
							      </label>
							  </div>
							</li>
							<li>
							  <div class="checkbox left-5">
							 	  <label>
							      	<input type="checkbox"  name="fwfDiscount" <c:if test='${manager.fwfDiscount == 1}'> checked</c:if>>服务费允许打折
							      </label>
							  </div>
							</li>
							<li>
							  <div class="checkbox left-5">
							 	  <label>
							      	<input type="checkbox"  name="zdxfDiscount" <c:if test='${manager.zdxfDiscount == 1}'> checked</c:if>>最低消费允许打折
							      </label>
							  </div>
							</li>
						</ul>
					</fieldset>
		   		</div>
		   		<div class="limit-area">
		   			 <fieldset class="discount-fieldset">
			       		<legend class="discount-title">消费区域限售品项</legend>
			       			<div class="limit-left">
			       				  <table id="grid-table-2"></table>
			       			</div>
			       			<c:if test="${single == '1' }">
				       			<div class="limit-right">
				       				<input type="button" value="增加" class="r-button-add" id="btn-add">
									<input type="button" value="减少" class="r-button-sub" id="btn-sub">
									<input type="button" value="清空" class="r-button-clear" id="btn-clear">
				       			</div>
			       			</c:if>
		   		 	 </fieldset>	
		   		</div>
		   		<div class="room-area">
		   		 <fieldset class="discount-fieldset">
			       		<legend class="discount-title">
			       		 	<div class="checkbox">
							 	  <label>
							      	<input type="checkbox" id="bffFree" name="bffFree" <c:if test="${manager.bffFree == '1'}"> checked</c:if>>包房费免费时长
							      </label>
							  </div>
			       		</legend>
			       		<div class="form-inline">
						      <span class="text-1 m-left" >开始:</span>
					          <input  type="text" name="bffTimeFree" class="form-control number" id="bffTimeFree" value="${manager.bffTimeFree}" <c:if test="${manager == null || manager.bffFree == '0'}"> disabled="disabled"</c:if> style="width: 80px"/>
					          <span class="text-1" >分钟内免费</span>
						</div>
			       		<div class="form-inline">
						      <span class="text-1 m-left" >剩余:</span>
					          <input  type="text" name="bffSurplusRemind"   class="form-control number" id="bffSurplusRemind" value="${manager.bffSurplusRemind}" <c:if test="${manager == null || manager.bffFree == '0' }"> disabled="disabled"</c:if> style="width: 80px"/>
					          <span class="text-1" >分钟内客位变色提醒</span>
						</div>
		   		 	 </fieldset>	
		   		
		   		</div>
		   </div>
	   </form>
	   </div>
       <p class="title-bar">客位:${count}</p>
       <div class="jqGrid_wrapper jq-margin">
              <table id="grid-table-3"></table>
       </div>
	</body>
</html>
