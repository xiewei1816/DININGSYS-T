<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>已结账单</title>
		<jsp:include page="../head.jsp"/>
	    <script src="app/js/DININGSYS/api/goClearingWaterList.js"></script>
	</head>
	<style type="text/css">
		input{width:120px;height: 24px;vertical-align:middle;}
		select{width:120px;height: 24px;vertical-align:middle;}
		span{font-weight: bold; vertical-align:middle;}
		.show-tab{border-collapse:separate; border-spacing:10px;}
		table{overflow-x:hidden;}
		div{overflow-x:hidden;}
	</style>
	<body style="width: 700px; font-size: 12px; overflow:auto;">
		<!-- <div class="btn-toolbar" style="width: 100%; height: auto;" >
			<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
        </div> -->
	    <div class="wrapper animated fadeInUp" style="width: 100%;" >
	    	<div style="height:auto;background: #ff6041;">
	    		<span style="font-size:18px;">结算流水 |</span>
	    		<span id="cwNum" style="font-size:18px; padding-left:8px; color:white;">${datas.cwNum }</span>
	    	</div>
	    	<div>
		    	<table class="show-tab" style="float:left; width:25%">
	    			<tr>
	    				<td><span>消费总额：</span></td>
	    				<td><span>${datas.consumptionAmount }</span></td>
	    			</tr>
	    			<tr>
	    				<td><span>抹零金额：</span></td>
	    				<td><span>${datas.zeroAmount }</span></td>
	    			</tr>
	    			<tr>
	    				<td><span>定额优惠：</span></td>
	    				<td><span>${datas.fixedDiscount }</span></td>
	    			</tr>
	    			<tr>
	    				<td><span style="color: green;">实收金额：</span></td>
	    				<td><span style="color: green;">${datas.paidAmount }</span></td>
	    			</tr>
	    			<tr>
	    				<td><span>找零金额：</span></td>
	    				<td><span>${datas.changeAmount }</span></td>
	    			</tr>
	    		</table>
	    		<table class="show-tab" style="width:75%; float:left; border-left: 1px solid #dff0d8;" >
	    			<tr>
	    				<td><span>结算时间：</span></td>
	    				<td>${datas.clearingTime }</td>
	    				<td><span>结算市别：</span></td>
	    				<td>${datas.clearingBis }</td>
	    			</tr>
	    			<tr>
	    				<td><span>结算操作员：</span></td>
	    				<td>${datas.clearingOperator }</td>
	    				<td><span>结算POS：</span></td>
	    				<td>${datas.clearingPos }</td>
	    			</tr>
	    			<tr>
	    				<td><span>打印次数：</span>${datas.printCont }</td>
	    				<td><span>开具发票：</span>${datas.invoicing }</td>
	    				<td><span>零结算：</span>${datas.zeroSettlement }</td>
	    				<td><span>补录单据：</span>${datas.retroDocuments }</td>
	    			</tr>
	    			<tr>
	    				<td><span>结账单标注：</span></td>
	    				<td>${datas.statementLabel }</td>
	    				<td><span>赠券金额：</span></td>
	    				<td>${datas.couponAmount }</td>
	    			</tr>
	    		</table>
    		</div>
			<div style=" width:100%;">
            	<table id="grid-table" style="overflow-x: hidden;"></table>
                <ul id="myTab" class="nav nav-tabs">
					<li class="active"><a href="#tab1" data-toggle="tab">结算方式</a></li>
					<li><a href="#tab2" data-toggle="tab">消费明细</a></li>
					<!-- <li><a href="#tab3" data-toggle="tab">优惠信息</a></li> -->
					<li><a href="#tab4" data-toggle="tab">发票信息</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade in active" id="tab1">
			            <table id="grid-table1"></table>
					</div>
					<div class="tab-pane fade" id="tab2">
						<table id="grid-table2" ></table>
					</div>
					<!-- <div class="tab-pane fade" id="tab3">
						<table id="grid-table3" ></table>
					</div> -->
					<div class="tab-pane fade" id="tab4">
						<table id="grid-table4" ></table>
					</div>
                </div>
                
			</div>
	    </div>
	</body>
</html>
