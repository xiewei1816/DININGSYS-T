<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>品项管理管理</title>
		<jsp:include page="../../head.jsp"/>
		<%
			String single = (String)request.getSession().getAttribute("single");
			request.setAttribute("single", single);
		%>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
	      <script type="text/javascript" src="${ctx}/assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="${ctx}/assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <link rel="stylesheet" href="${ctx}/assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
	    <link rel="stylesheet" href="${ctx}/app/css/DININGSYS/deskBusiness/product_item/allow_discount.css">
	    <script src="${ctx}/app/js/DININGSYS/deskBusiness/product_item/allow_discount.js"></script>
	</head>
	
	<body>
		<div class="btn-toolbar">
			<c:if test="${single == '1' }">
				<span id="add" class="greenbtn"><i class="fa fa-file-o"></i>保存</span>
			</c:if>
			<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
			<span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span>
	   </div>
		<div class="left-menu">
			<div class="left-content">
				<div id="disTree" class="extra-t" >
            		<ul id="dtree" class="ztree"></ul>
       			 </div>
       			 <div id="smallGate" class="smallGate">
       			 	<p>
       			 	  新建品项时,品项允许打折的默认值(按品项小类设置)
       			 	</p>
       			 	<table id="grid-table-gate"></table>
       			 </div>
			</div>
			<div class="control" onclick="show_bar()">
	            <span class="mid-bar">
	            </span>
	        </div>
        </div>
        <div class="right-content">
       		<div class="sele">
       			<span id="discount_count">品项档案:</span>
       			<a class="sele-all">全部选择</a>
       		</div>
		    <div class="jqGrid_wrapper jq-margin">
               <table id="grid-table"></table>
              <!-- <div id="grid-pager"></div> --> 
           </div>
          </div>  
          <div style="display: none;">
          		<form class="query-pan" id="query-pan">
          				<input type="text" name="all_click">
          		</form>
          </div> 
	</body>
</html>
