<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<jsp:include page="../../head.jsp"/>
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">类型</span>
							<div class="form-group biger-wid">
								<select name="payType" id="j-payType" class="form-control edit_payType"
								          editype="val" style="overflow:visible;">
								          <option value="">==请选择支付类型==</option>
								    	  <option value="WX">微信</option>
								    	  <option value="ZFB">支付宝</option>
								</select>
							</div>
						</li>
						<li>
							<span class="title">商户订单号</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="j-outTradeNo" name="outTradeNo" />
							</div>
						</li>
						<li>
							<span class="title">第三方订单号</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="j-threeTradeNo" name="threeTradeNo" />
							</div>
						</li>
						<li>
							<span class="title">店内流水号</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="j-orderNo" name="orderNo" />
							</div>
						</li>
						<li>
							<span class="title">创建时间</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_crStartTime" id="crStartTime" name="crStartTime" editype="val" placeholder="开始时间" readonly
   								onclick="$.jeDate('#crStartTime',{insTrigger:false,isTime:true,festival:true,format:'YYYY-MM-DD'})" dete="em-date">
							</div>
						</li>
						<li>
							<span class="title">至</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_crEndTime" id="crEndTime" name="crEndTime" editype="val" placeholder="结束时间" readonly
   								onclick="$.jeDate('#crEndTime',{insTrigger:false,isTime:true,festival:true,format:'YYYY-MM-DD'})" dete="em-date">
                           	</div>
						</li>
					</ul>
					<div class="search-btns">
						<button class="btn btn-primary">查询</button>
					</div>
				</form> 
			</div>
			<div class="btn-toolbar">
				<span class="btn-check"><i class="fa fa-file-o"></i>单条对账</span>
				<span id="btn-export" class="bluebtn"><i class="fa fa-edit"></i>导出报表</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    <script type="text/javascript" src="${ctx }/app/js/DININGSYS/pay/dgpaywater.js"></script>
	</body>
</html>
