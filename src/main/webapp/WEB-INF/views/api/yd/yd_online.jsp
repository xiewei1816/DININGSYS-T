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
			var onlineReserveUrl = "${onlineReserveUrl}";
		</script>
		<link rel="stylesheet" href="${ctx}/app/css/DININGSYS/deskBusiness/product_item/base.css">
		<link rel="stylesheet" href="${ctx}/app/css/DININGSYS/api/yd.css">
		<script src="${ctx }/app/js/api/yd/ydOnline.js"></script>
		<script src="${ctx }/app/js/api/yd/ydOnlineSeats.js"></script>
		<script src="${ctx }/app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
	</head>
	
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				  <form class="query-pan" id="query-pan">
				  <input type="hidden" name="state" value="2"/>
	              <ul>
	                <li>
	                    <span class="title">查询条件:</span>
	                    <div class="form-group big-wid">
	                        <input type="text" class="form-control" name="search" placeholder="请输入姓名/电话/查询线上预定"/>
	                    </div>
	                </li>
          		  </ul>
		          <div class="search-btns">
		                <button class="btn btn-primary">查询</button>
		          </div>
				</form> 
			</div>
            <div class="btn-toolbar">
				<span id="add" class="greenbtn"><i class="fa fa-file-o"></i>绑定客位</span>
				<span id="del" class="redbtn"><i class="fa fa-remove"></i>拒绝订单</span>
				<span id="manager" class="bluebtn"><i class="fa fa-home"></i>预定管理</span>
				<span id="onlineDelGrid" class="redbtn"><i class="fa fa-trash"></i>拒绝订单列表</span>
				<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    
	    <form id="editForm" style="display: none;">
			<div class="editForm">
				    <input type="hidden"  name="posId" value="${posId}" id="posId"/>
			    	<input type="hidden" name="ydNum" id="ydNum"/>
			    	<input type="hidden" name="name" id="name"/>
			    	<input type="hidden" name="phone" id="phone"/>
			    	<input type="hidden" name="number" id="number"/>
			    	<input type="hidden" name="ydTime" id="ydTime"/>
			    	<input type="hidden" name="sex" id="sex"/>
			    	<input type="hidden" name="crId" id="crId"/>
			    	<input type="hidden" name="ydResoure" id="ydResoure"/>
			    	<input type="hidden" name="wOw" id="wOw"/>
			    	<input type="hidden" name="bsd" id="bsd"/>
			    	<input type="hidden" name="time" id="time"/>
			    	<ul id="seatUl">
			    		<!--<li>
						 	  <input type='hidden' name='seatIds[${stat.index}].seatId' value='${o.seatId}'/>
						 </li>-->
			    	</ul>
			</div>
		</form>
	</body>
</html>
