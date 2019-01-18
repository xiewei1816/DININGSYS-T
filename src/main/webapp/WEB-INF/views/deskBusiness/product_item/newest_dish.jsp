<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="../../head.jsp" />
<script type="text/javascript"
	src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript"
	src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
<link rel="stylesheet"
	href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script src="app/js/DININGSYS/deskBusiness/product_item/newest_dish.js"></script>
	<link rel="stylesheet"
	href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
<script
	src="app/js/DININGSYS/deskBusiness/product_item/un_disable_add.js"></script>
<title>Insert title here</title>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
</head>
<body>
	<div class="wrapper animated fadeInUp">
		<div class="search-wrapper input-groups">
			<form class="query-pan" id="query-pan">
				<ul>
					<li><span class="title">菜品编码</span>
						<div class="form-group big-wid">
							<input type="text" class="form-control" id="is_del" name="code" />
						</div></li>
					<li><span class="title">菜品名称</span>
						<div class="form-group big-wid">
							<input type="text" class="form-control" id="is_del" name="name" />
						</div></li>
				</ul>
				<div class="search-btns">
					<button class="btn btn-primary">查询</button>
				</div>
			</form>
		</div>
		<div class="btn-toolbar">
			<c:if test="${single == '1' }">
				<span id="save" class="royalbtn"><i class="fa fa-file-o"></i>保存</span>
				<span id="add" class="greenbtn"><i class="fa fa-file-o"></i>新增</span>
				<span id="delb" class="redbtn"><i class="fa fa-trash"></i>删除</span> 
			</c:if>
			<span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span> 
			<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
		</div>
		<div class="jqGrid_wrapper">
			<table id="grid-table"></table>
			<div id="grid-pager"></div>
		</div>
	</div>
</body>
</html>