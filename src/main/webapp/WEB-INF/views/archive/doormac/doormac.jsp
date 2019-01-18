<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<jsp:include page="../../head.jsp"/>
		<script src="app/js/DININGSYS/archive/doormac/doormac.js"></script>
		<script src="app/js/DININGSYS/deskBusiness/product_item/checkUpdVals.js"></script>
	</head>
	
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				  <form class="query-pan" id="query-pan">
	              <ul>
	                <li>
	                    <span class="title">mac地址:</span>
	                    <div class="form-group big-wid">
	                        <input type="text" class="form-control" name="mac" />
	                    </div>
	                </li>
          		  </ul>
		          <div class="search-btns">
		                <button class="btn btn-primary">查询</button>
		          </div>
				</form> 
			</div>
            <div class="btn-toolbar">
				<span id="add" class="greenbtn"><i class="fa fa-file-o"></i>新增</span>
				<span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
				<span id="del" class="redbtn"><i class="fa fa-trash"></i>删除</span>
				<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    
	</body>
</html>
