<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>转账流水查询</title>
		<jsp:include page="../head.jsp"/>
	    <script src="app/js/DININGSYS/api/goTransferOpenWaterList.js"></script>
	</head>
	<style type="text/css">
		input{width:120px;height:24px;vertical-align:middle;}
		select{width:120px;height:24px;vertical-align:middle;}
		span{font-weight:bold;vertical-align:middle;}
		.search-tab{border-collapse:separate;border-spacing:6px;}
		.show-tab{border-collapse:separate;border-spacing:10px;}
	</style>
	<body style="width:960px;margin:0 auto;font-size:12px;overflow:auto;">
		<!-- <div class="btn-toolbar" style="width: 100%; height: auto;" >
			<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
        </div> -->
		<div class="wrapper animated fadeInUp" style="float:left;width:45%;" >
			<div style="width:98%;height:auto;background:#ff6041; ">
				<span style="font-size:18px;">转账流水</span><span style="font-size:18px;float:right;"></span>
			</div>
			 <div align="center" style="width:98%;height:62px;">
		    	<form id="formid" method="post" action="">
		    		<table class="search-tab">
		    			<tr>
		    				<td>营业流水编号：</td>
		    				<td colspan="3"><input style="width:288px;" onblur="changeClick()" type="text" id="owNum" name="owNum" /></td>
		    			</tr>
		    			<tr>
		    				<td>消费区域：</td>
		    				<td>
		    					<select id="expArea" name="expArea" onblur="changeClick()">
									<option selected="selected"></option>
									<c:forEach items="${areaList}" var="area" varStatus="status">
										<option  value="${area.id}" >${area.name}</option>
									</c:forEach>
								</select>
							</td>
		    				<td>客位：</td>
		    				<td>
		    					<select id="clientSeat" name="clientSeat" onblur="changeClick()">
									<option selected="selected"></option>
									<c:forEach items="${seatList}" var="seat" varStatus="status">
										<option  value="${seat.id}" >${seat.name}</option>
									</c:forEach>
								</select>
		    				</td>
		    			</tr>
		    		</table>
		    	</form>
		    </div>
			<ul id="myTab" class="nav nav-tabs" style="width:98%;">
				<li class="active"><a href="#into" data-toggle="tab">转入</a></li>
				<li><a href="#out" data-toggle="tab">转出</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="into">
		            <table id="grid-table-into"></table>
				</div>
				<div class="tab-pane fade" id="out">
					<table id="grid-table-out" ></table>
				</div>
			</div>
	    </div>
	    <div class="wrapper animated fadeInUp" style="float:left;width:55%;" >
	    	<div style="width:98%;height:auto;background:#ff6041;"><span style="font-size:18px;">营业流水</span><span id="owNumTitle" style="font-size:18px; padding-left: 8px; color: white;"></span></div>
	    	<div style="width:100%;" id="tab-div">
		    	<table class="show-tab" style="width:100%;height:100px;">
	    			<tr>
	    				<td><span>开单POS：</span></td>
	    				<td><p id="openPos"></p></td>
	    				<td><span>开单时间：</span></td>
	    				<td><p id="firstTime"></p></td>
	    				<td><span>人数：</span></td>
	    				<td><p id="peopleCount"></p></td>
	    			</tr>
	    			<tr>
	    				<td><span>服务员：</span></td>
	    				<td><p id="waiter"></p></td>
	    				<td><span>客户：</span></td>
	    				<td><p></p></td>
	    				<td><span>单据：</span></td>
	    				<td><p id="documents"></p></td>
	    			</tr>
	    			<!-- <tr>
	    				<td><span>整单备注：</span></td>
	    				<td colspan="5"><span id="owNotes"></span></td>
	    			</tr> -->
	    			<tr>
	    				<td><span>团队成员：</span></td>
	    				<td colspan="2"><p style="width: 140px;" id="teamMembers" ></p></td>
	    				<td><span>结算流水：</span></td>
	    				<td colspan="2"><a id="goClearingWater" style="color:blue;cursor:pointer;"><p id="cwNum" ></p></a></td>
	    				
	    			</tr>
	    		</table>
    		</div>
			<div class="tab-pane fade in active" id="owNum">
	            <table id="grid-table-item"></table>
			</div>
	    </div>
	</body>
</html>
