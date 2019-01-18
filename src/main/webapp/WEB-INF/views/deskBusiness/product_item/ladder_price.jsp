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
<link rel="stylesheet" href="app/css/DININGSYS/bussinessMan/consumerSeat/consumerSeat.css">
<link rel="stylesheet" href="app/css/DININGSYS/bussinessMan/consumerSeat/consumerBffPro.css">

<script src="app/js/DININGSYS/deskBusiness/product_item/ladder_price.js"></script>
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/base.css">
<script src="app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
<script src="app/js/DININGSYS/deskBusiness/product_item/meal_time_add.js"></script>
<title>品项价格按市别</title>
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
				<c:if test="${single == '1' }">
					<span id="save" class="greenbtn"><i class="fa fa-file-o"></i>保存</span>
					<span id="add" class="graybtn"><i class="fa fa-file-o"></i>增加</span>
					<span id="delb" class="redbtn"><i class="fa fa-trash"></i>删除</span>
				</c:if>
				<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
			</div>
            <form class="form-horizontal" role="form" id="userImpDisForm">
				<div class="form-body">
	                    <div  style="height: 40px;margin-top: 10px;">
	 	                     <span style="text-align: left;">阶梯:</span>
	                            <select name="enable"  class="form-control sele-control" id="sele" style="width: 130px;display: inline-block;">
	                                <option value="1">阶梯1价格</option>
	                                <option value="2">阶梯2价格</option>
	                            </select>
	                        <span style="text-align: left;">执行价格=品项标准价格*</span>
	                        <input type="number" name="discount" class="form-control discount"  value="100" min="1" max="100" style="width: 70px;display: inline-block;" dete="discount">
	                        <span style="text-align: left;">%</span>
	                        
	                        <label class="checkbox-inline" style="margin: 0px 10px 8px 15px">
							  	<input type="checkbox" id="isALL"> 只执行价格为空品项
							</label>
	                        <input  type="button" name="set-s"  id="set-s" value="统一设置" class="input-button" style="margin-left: 15px;"/>
	                        <input  type="button" name="set-s"  id="clear-s" value="清空当前市别价格" class="input-button" style="margin-left: 15px;"/>
				        </div>
				        
				        <div class="form-group" style="height: 30px">
	                        <label  class="col-md-10 control-label"><h5>说明:加单时,首个品项默认使用阶梯1价格,不使用标准价格;若想使用标准价格,可以把阶梯1价格设置为标准价格。</h5></label>
	                    </div>
	            </div>
            </form>
             <div class="jqGrid_wrapper">
             	 <form class="table-form" role="form" id="table-form">
			       <table id="grid-table"></table>
			       <div id="grid-pager"></div>
			       </form>
			</div>
	        
	    </div>
</body>
</html>