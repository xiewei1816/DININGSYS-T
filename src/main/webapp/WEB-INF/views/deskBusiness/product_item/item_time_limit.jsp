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

<script src="app/js/DININGSYS/deskBusiness/product_item/item_time_limit.js"></script>
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/base.css">
<script src="app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
<script src="app/js/DININGSYS/deskBusiness/product_item/meal_time_add.js"></script>

<script src="assets/scripts/webuploader/js/jquery.colorbox-min.js"></script>
<script src="assets/scripts/webuploader/js/global.js" type="text/javascript"></script>
<script src="assets/scripts/webuploader/js/webuploader.js" type="text/javascript"></script>
<script src="assets/scripts/webuploader/js/web_pic.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>品项价格按市别</title>
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
				<span id="pic_z" class="bluebtn"><i class="fa fa-file-o"></i>广告图片(纵向显示)</span>
				<span id="pic_h" class="bluebtn"><i class="fa fa-file-o"></i>广告图片(横向显示)</span>
			</div>
            <form class="form-horizontal" role="form" id="userImpDisForm">
				<div class="form-body">
				        <div class="form-group" style="height: 40px;margin-top: 10px">
	                        <label  class="col-md-3 control-label"><h5 class="m-count">共计0个限时抢购品项</h5></label>
	                        <span style="text-align: left;" class="discount_title">按比例设定抢购价格</span>
	                        <input type="number" name="discount" class="form-control discount"  value="100" min="1" max="100" style="width: 70px;display: inline-block;" dete="discount">
	                        <span style="text-align: left;">%</span>
	                        <input  type="button" name="set-s"  id="set-s" value="统一设置" class="input-button" style="margin-left: 15px;"/>
	                    </div>
	            </div>
	            <div class="form-body">                  
						<label  class="col-md-2 control-label">抢购开始时间:</label>
                        <div class="col-md-3">
                            <input type="text" name="startTime" class="form-control" id="startTime"  value="${item.startTime}"  readonly="readonly">
                        </div>
                        <label  class="col-md-2 control-label">抢购结束时间:</label>
                        <div class="col-md-3">
                            <input type="text" name="endTime" class="form-control" id="endTime"  value="${item.endTime}"  readonly="readonly">
                        </div>		
	            </div>
	            <input type="hidden" id="pic-z-names" value="${zPics}">
	            <input type="hidden" id="pic-h-names" value="${hPics}">
            </form>
            <div class="jqGrid_wrapper" style="margin-top: 50px;">
                 <form class="table-form" role="form" id="table-form">
			       <table id="grid-table"></table>
			       <div id="grid-pager"></div>
			       </form>
			</div>
	    </div>
</body>
</html>