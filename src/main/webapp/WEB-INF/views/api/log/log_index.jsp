<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>前台操作日志管理</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
		<script src="${ctx }/app/js/api/log/log_index.js"></script>
	</head>
	
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				  <form class="query-pan" id="query-pan">
				  <input type="hidden"  name="posId" value="${posId}" id="posId"/>
	              <ul>
	                <li>
	                    <span class="title">操作人员姓名:</span>
	                    <div class="form-group big-wid">
	                        <input type="text" class="form-control" name="oper" placeholder="请输入操作员姓名"  id="oper"/>
	                    </div>
	                </li>
	                <li>
	                    <span class="title">操作日期:</span>
	                    <div class="form-group big-wid">
	                        <input type="text" class="form-control" name="time" placeholder="操作日期"  id="time" readonly/>
	                    </div>
	                </li>
	                <li>
	                    <span class="title">操作类型:</span>
	                    <div class="form-group big-wid">
	                        <select name="type"  class="form-control" id="type">
	                               <!-- 1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班 -->
									<option value="">全部</option>
									<option value="1">更换客位</option>
									<option value="2">加入团队</option>
									<option value="3">更改客位人数</option>
									<option value="4">修改品项数量</option>
									<option value="5">修改品项价格</option>
									<option value="6">品项赠送</option>
									<option value="7">撤销品项赠送</option>
									<option value="8">单品转台</option>
									<option value="9">转账</option>
									<option value="10">拆帐</option>
									<option value="11">关账</option>
									<option value="12">挂S帐</option>
									<option value="13">撤销S帐</option>
									<option value="14">结班</option>
	                        </select>
	                    </div>
	                </li>
	                <li>
	                    <span class="title">操作客位:</span>
	                    <div class="form-group big-wid">
	                   		 <select name="seatName"  class="form-control" id="seatName">
	                   		 	<option value="">全部</option>
		                     	<c:forEach items="${seats}" var="item">
		                    		<option value="${item.name }">${item.name }</option>
		                    	 </c:forEach>
	                    	</select>
	                    </div>
	                </li>
          		  </ul>
		          <div class="search-btns">
		                <button class="btn btn-primary" id="search">查询</button>
		          </div>
				</form> 
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	</body>
</html>
