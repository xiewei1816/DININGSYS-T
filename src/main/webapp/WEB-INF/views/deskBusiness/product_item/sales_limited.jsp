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

<script src="app/js/DININGSYS/deskBusiness/product_item/sales_limited.js"></script>
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/base.css">
<script src="app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
<script src="app/js/DININGSYS/deskBusiness/product_item/un_disable_add.js"></script>
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
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">菜品编码</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="is_del" name="itemCode"/>
							</div>
						</li>
						<li>
							<span class="title">菜品名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="is_del" name="name"/>
							</div>
						</li>
					</ul>
					<div class="search-btns">
						<button class="btn btn-primary">查询</button>
					</div>
				</form> 
			</div>
			<div class="btn-toolbar">
				<span id="save" class="greenbtn"><i class="fa fa-file-o"></i>保存</span>
				<span id="add" class="graybtn"><i class="fa fa-file-o"></i>增加</span>
				<span id="delb" class="redbtn"><i class="fa fa-trash"></i>删除</span>
				<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
			</div>
            <form class="form-horizontal" role="form" id="userImpDisForm">
				<div class="form-body">
				        <div class="form-group" style="height: 40px;margin-top: 10px">
	                        <label  class="col-md-3 control-label"><h5 class="m-count">共计0个促销品项</h5></label>
	                        <span style="text-align: left;" class="discount_title">今日可销售数量</span>
	                        <input type="number" name="discount" class="form-control discount"  value="500" min="1" max="500" style="width: 70px;display: inline-block;" dete="em-money">
	                        <input  type="button" name="set-s"  id="set-s" value="统一设置" class="input-button" style="margin-left: 15px;"/>
	                    </div>
	            </div>
            </form>
            <div class="bottom-content" >
	             <div class="jqGrid_wrapper">
	                  <form class="table-form" role="form" id="table-form">
				         <table id="grid-table"></table>
				      </form>
				</div>
	        </div>
	        <div class="container">
	        	  <div style="margin-top: 5px;height: 15px;">
	       	      		<h5>今日可销售数量 需要你手工输入;今日预定品项数量 读取自前台咨询客中预定日期为今日的预定单的预定品项数量</h5>
	       	      </div>
	       	      <div class="radio">
					  <label>
					    <input type="radio" name="mType" id="isALL" value="1" <c:if test="${'1' == type}">checked</c:if>>
					    今日前台可销售数量 = 今日可销售数量 - 今日预定品项数量
					  </label>
				 </div>
				<div class="radio">
					  <label>
					    <input type="radio" name="mType" id="isALL" value="2" <c:if test="${'2' == type}">checked</c:if>>
					   今日前台可销售数量 = 今日可销售数量,不考虑今日预定品项数量
					  </label>
				 </div>
	         </div>
	    </div>
</body>
</html>