<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>品项出品部门</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
	      <script type="text/javascript" src="${ctx}/assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="${ctx}/assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <link rel="stylesheet" href="${ctx}/assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
	    <link rel="stylesheet" href="${ctx}/app/css/DININGSYS/deskBusiness/product_item/allow_discount.css">
	    <script src="${ctx}/app/js/DININGSYS/deskBusiness/product_item/item_pro_dep.js"></script>
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
				<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
		    </div>
		    <form class="form-horizontal" role="form" id="userImpDisForm">
				<div class="form-body">
	                    <div class="form-group" style="height: 40px;margin-top: 10px;">
	                   		<label  class="col-md-1 control-label">区域:</label>
	                        <div class="col-md-2">
	                            <select name="enable"  class="form-control" id="sele-area">
 									<c:forEach items="${areas}" var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
	                            </select>
	                        </div>
	                   		<label  class="col-md-1 control-label">部门:</label>
	                        <div class="col-md-2">
	                            <select name="enable"  class="form-control" id="sele-dep">
	                               <c:forEach items="${deps}" var="item">
										<option value="${item.id}">${item.depName}</option>
									</c:forEach>
	                            </select>
	                        </div>
	                        <input  type="button" name="set-s"  id="set-s" value="统一设置" class="input-button" style="margin-left: 15px;"/>
				        </div>
	            </div>
            </form>
		    <div class="left-menu">
				<div class="left-content">
					<div id="disTree" class="extra-t" >
	            		<ul id="dtree" class="ztree"></ul>
	       			 </div>
				</div>
				<div class="control" onclick="show_bar()">
		            <span class="mid-bar">
		            </span>
		        </div>
	        </div>
	        <div class="right-content">
			    <div class="jqGrid_wrapper jq-margin">
	               <table id="grid-table"></table>
	            </div>
	          </div>  
          </div>
	</body>
</html>