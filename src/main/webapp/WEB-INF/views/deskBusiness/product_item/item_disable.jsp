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

<script src="app/js/DININGSYS/deskBusiness/product_item/item_disable.js"></script>
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
<script src="app/js/DININGSYS/deskBusiness/product_item/disable_time_add.js"></script>
<title>品项停用</title>
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
								<input type="text" class="form-control itemCode" id="is_del" name="itemCode"/>
							</div>
						</li>
						<li>
							<span class="title">菜品名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control name" id="is_del" name="name"/>
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
            <div class="jqGrid_wrapper">
			       <table id="grid-table"></table>
			</div>
	    </div>
</body>
</html>