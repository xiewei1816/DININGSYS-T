<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>系统日志管理管理</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <link rel="stylesheet" href="${ctx }/assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
		<script src="${ctx }/app/js/DININGSYS/sysSettings/log/log.js"></script>
	</head>
	
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
	            <ul>
	                <li>
	                    <span class="title">查询字段</span>
	                    <div class="form-group big-wid">
	                         <select name="query_type" class="form-control">
	                            <option value="1" selected="selected">操作员</option>
	                            <option value="2">操作内容</option>
	                        </select>
	                    </div>
	                </li>
	                <li>
	                    <span class="title">查询内容</span>
	                    <div class="form-group big-wid">
	                        <input type="text" class="form-control" name="query_content" />
	                    </div>
	                    <input type="hidden" class="form-control" name="query_time_type" class="query_time_type" id="query_time_type"/>
	                    <input type="hidden" class="form-control" name="query_time" class="query_time" id="query_time"/>
	                    <input type="hidden" class="form-control" name="type" class="type" id="type"/>
	                </li>
	                
          		  </ul>
            <div class="search-btns">
                <button class="btn btn-primary">查询</button>
            </div>
				</form> 
			</div>
			
			<div id="menuTree" style="width: 200px; border: 1px solid #f6a828; float: left;">
            <ul id="tree" class="ztree" style="width: 260px; overflow: auto;"></ul>
       		 </div>
        
	        <div class="jqGrid_wrapper" style="margin-left: 210px;">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    
	</body>
</html>
