<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script src="app/js/DININGSYS/deskBusiness/product_item/special_item.js"></script>
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
<script src="app/js/DININGSYS/deskBusiness/product_item/gift_item_plan_host_item_add.js"></script>
<script src="app/js/DININGSYS/deskBusiness/product_item/gift_item_plan.js"></script>
<title>品项价格按市别</title>
<style>
.input-button{
	height: 30px;
	font-size:12px; 
	line-height:30px; 
	text-align:center; 
	border:1px; 
	border-color: #37b5f9;
	padding-left:5px;
	padding-right:5px;
	border-radius:3px; 
	
}
.input-button:HOVER{
	border:1px; 
	border-color: #57b5f9;
	background: #c0c0c0;
}
.input-button:ACTIVE{
	border:1px; 
	border-color: #77b5f9;
	background: #a0a0a0;
}
</style>
</head>
<body>
	<div class="wrapper animated fadeInUp">
			<div class="btn-toolbar">
				<span id="save" class="greenbtn"><i class="fa fa-file-o"></i>保存</span>
			</div>
	        <div class="col-md-12 form">
	        	<form class="form-horizontal" role="form">
	                  <div class="form-group">
                        <label  class="col-md-3 control-label">服务费品项:</label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="hidden" name="serviceItemId" id="serviceItemId"  value="${item.serviceItemId}">
                            <input type="hidden" name="serviceItemCode" id="serviceItemCode"  value="${item.serviceItemCode}">
                            <input type="button" name="serviceItemName" class="form-control" id="serviceItemName"  value="${item.serviceItemName}">
                        </div>
                        <div class="col-md-3" style="display: inline-block;">
  				            <input  type="button" name="add-m1" id="add-m1" value="选择" class="input-button" />
					     	<input  type="button" name="sub-m1" id="sub-m1" value="删除" class="input-button" style="margin-left: 20px;"/>	
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">最低消费补齐品项:</label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="hidden" name="minConsumptionItemId" id="minConsumptionItemId"  value="${item.minConsumptionItemId}">
                            <input type="hidden" name="minConsumptionItemCode" id="minConsumptionItemCode"  value="${item.minConsumptionItemCode}">
                            <input type="button" name="minConsumptionItemName" class="form-control" id="minConsumptionItemName"  value="${item.minConsumptionItemName}">
                        </div>
                        <div class="col-md-3" style="display: inline-block;">
  				            <input  type="button" name="add-m2" id="add-m2" value="选择" class="input-button" />
					     	<input  type="button" name="sub-m2" id="sub-m2" value="删除" class="input-button" style="margin-left: 20px;"/>	
                        </div>
                    </div>
                    
                    <div class="form-group">
                         <label  class="col-md-3 control-label">最低消费补齐方式:</label>
                         <div class="radio">
							    <label style="margin-left: 15px;">
							        <input type="radio"  name="minConsumeType" id="minConsumeType1"  value="1" <c:if test="${item.minConsumeType == null || '1' == item.minConsumeType}">checked</c:if> ><span>按折前金额补齐</span>
							    </label>
							    
							    <label style="margin-left: 15px;">
							        <input type="radio"  name="minConsumeType" id="minConsumeType2"  value="2" <c:if test="${'2' == item.minConsumeType}">checked</c:if> ><span>按折后金额补齐</span>
							    </label>
						  </div>
                    </div>
               </form>
               </div>
	    </div>
</body>
</html>