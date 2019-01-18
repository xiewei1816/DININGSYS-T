<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>消费品项设置管理</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
    	<link rel="stylesheet" href="assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="app/js/DININGSYS/archive/cgds/cgds.js" ></script>
    	<style type="text/css">
			.extra {width: 200px; border: 1px solid #DDD; float: left;}
			.child {margin-left: 210px}
		</style>
	</head>
	<body>
		<ul id="myTab" class="nav nav-tabs">
			<li class="active"><a href="#not_list" data-toggle="tab"><strong>不参与排行的品项</strong></a></li>
			<li><a href="#show_setting" data-toggle="tab"><strong>不参与显示的品项</strong></a></li>
		</ul>	
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" id="not_list">
				<!-- tab1:内容 -->
				<div class="wrapper animated fadeInUp">
					<div class="search-wrapper input-groups">
						<form class="query-pan" id="query-pan">
							<ul>
								 <li>
									<div class="form-group bigest-wid">
										<label class="radiobtn"><input type="radio" value="0" class="edit_isItemFlag" name="isItemFlag" 
										editype="radio" onclick="doSelected(this.value)" checked/><i></i><span></span>按品项</label>
										<label class="radiobtn"><input type="radio" value="1" class="edit_isItemFlag" name="isItemFlag" 
										editype="radio" onclick="doSelected(this.value)" /><i></i><span></span>按品项小类</label>
									</div>
				    			</li>
							</ul>
						</form> 
					</div>
					<div id="tab1">
						<div class="btn-toolbar">
							<c:if test="${single == '1' }">
								<span id="rank-item-add" class="greenbtn" title="添加"><i class="fa fa-plus">添加</i></span>
								<span id="rank-item-reduce" class="bluebtn" title="移除"><i class="fa fa-minus">移除</i></span>
								<span id="rank-item-remove" class="redbtn" title="全部移除"><i class="fa fa-times">全部移除</i></span>
							</c:if>
						</div>
				        <div id="beItem" class="jqGrid_wrapper">
			                <table id="rank-item-table"></table>
			            </div>
			        </div>
			        <div id="tab2" style="display: none;">
						<div class="btn-toolbar">
							<c:if test="${single == '1' }">
								<span id="rank-smallitem-add" class="greenbtn" title="添加"><i class="fa fa-plus">添加</i></span>
								<span id="rank-smallitem-reduce" class="bluebtn" title="移除"><i class="fa fa-minus">移除</i></span>
								<span id="rank-smallitem-remove" class="redbtn" title="全部移除"><i class="fa fa-times">全部移除</i></span>
							</c:if>
						</div>
				        <div id="beItem" class="jqGrid_wrapper">
			                <table id="rank-smallitem-table"></table>
			            </div>
			        </div>
			    </div>
				<!-- tab1:内容end -->
			</div>
			<div class="tab-pane fade" id="show_setting">
				<!-- tab2:内容 -->
				<div class="wrapper animated fadeInUp">
					<div class="search-wrapper input-groups">
						<form class="query-pan" id="query-pan">
							<ul>
								 <li style="display: none;">
									<span class="title">ID</span>
									<div class="form-group big-wid">
										<input type="text" class="form-control" id="id" name="id" value="${dgItemTimeSet.id }"/>
									</div>
								</li>
								 <li>
									<span class="title">刷新频率(秒)</span>
									<div class="form-group big-wid">
										<input type="text" class="form-control" id="refreshHz" name="refreshHz" dete="em-positive" value="${dgItemTimeSet.refreshHz }"/>
									</div>
								</li>
								<li>
									<span class="title">品项统计起始日期</span>
									<div class="form-group big-wid">
			   							<input readonly type="text" id="startDate" class="form-control" name="startDate" 
   											onclick="$.jeDate('#startDate',{insTrigger:false,isTime:true,festival:true,format:'YYYY-MM-DD'})"
   											value="${dgItemTimeSet.startDate }">
				
									</div>
								</li>
								<li>
									<div class="form-group big-wid">
										<input id="save-btn" class="btn btn-primary" type="button" value="保存"> 
									</div>
								</li>
							</ul>
						</form> 
					</div>
					<div class="btn-toolbar">
						<c:if test="${single == '1' }">
							<span id="show-item-add" class="greenbtn" title="添加"><i class="fa fa-plus">添加</i></span>
							<span id="show-item-reduce" class="bluebtn" title="移除"><i class="fa fa-minus">移除</i></span>
							<span id="show-item-remove" class="redbtn" title="全部移除"><i class="fa fa-times">全部移除</i></span>
							<span id="show-item-up" class="graybtn" title="上移"><i class="fa fa-arrow-up"></i>上移</span>
							<span id="show-item-down" class="royalbtn" title="下移"><i class="fa fa-arrow-down"></i>下移</span>
						</c:if>
					</div>
			        <div class="jqGrid_wrapper">
		                <table id="show-item-table"></table>
		            </div>
			    </div>
				<!-- tab2:内容end -->
			</div>
		</div>
	</body>
</html>
