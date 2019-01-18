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
<link rel="stylesheet" href="${ctx}/app/css/DININGSYS/bussinessMan/consumerSeat/consumerSeat.css">
<link rel="stylesheet" href="${ctx}/app/css/DININGSYS/bussinessMan/consumerSeat/consumerBffPro.css">

<script src="app/js/DININGSYS/deskBusiness/product_item/place_time.js"></script>
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
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
	                   		<span style="text-align: left;">区域:</span>
                            <select name="enable"  class="form-control sele-control" id="sele" style="width: 130px;display: inline-block;">
                              <c:forEach items="${areas}" var="item">
									<option value="${item.id}">${item.name}</option>
								</c:forEach>
                            </select>
	                        <span style="text-align: left;">执行价格=品项标准价格*</span>
	                        <input type="number" name="discount" class="form-control discount"  value="100" min="1" max="100" style="width: 70px;display: inline-block;" >
	                        <span style="text-align: left;">%</span>
	                        
	                        <label class="checkbox-inline" style="margin: 0px 10px 8px 15px">
							  	<input type="checkbox" id="isALL"> 只执行价格为空品项
							</label>
	                        <input  type="button" name="set-s"  id="set-s" value="统一设置" class="input-button" style="margin-left: 15px;"/>
	                        <input  type="button" name="set-s"  id="clear-s" value="清空当前市别价格" class="input-button" style="margin-left: 15px;"/>
				        </div>
				        
				        <div class="form-group" style="height: 30px">
	                        <label  class="col-md-6 control-label"><h5>共计0个品项 按市别设置一菜多价(空价格代表不设置品项价格)</h5></label>
	                    </div>
	            </div>
            </form>
             <div class="jqGrid_wrapper">
			       <table id="grid-table"></table>
			       <div id="grid-pager"></div>
			</div>
	        
	    </div>
</body>
</html>