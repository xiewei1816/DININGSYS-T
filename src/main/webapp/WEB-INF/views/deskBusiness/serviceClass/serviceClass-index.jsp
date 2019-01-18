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
		<title></title>
		<jsp:include page="../../head.jsp"/>
		<script src="app/js/DININGSYS/deskBusiness/serviceClass/serviceClass.js"></script>
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">市别</span>
							<div class="form-group big-wid">
								<select class="form-control" name="eatTimeId">
									<option value="0">请选择</option>
									<c:forEach items="${bises }" var="bis">
										<option value="${bis.id }">${bis.bisName }</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li>
							<span class="title">客位</span>
							<div class="form-group big-wid">
								<select class="form-control" name="seatId">
									<option value="0">请选择</option>
									<c:forEach items="${seats }" var="seat">
										<option value="${seat.id }">${seat.name }</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li>
							<span class="title">服务员</span>
							<div class="form-group big-wid">
								<select class="form-control" name="waiterId">
									<option value="0">请选择</option>
									<c:forEach items="${emps }" var="emp">
										<option value="${emp.id }">${emp.empName }</option>
									</c:forEach>
								</select>
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
					<span class="add"><i class="fa fa-file-o"></i>编辑</span>
					<span id="delb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
				</c:if>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	</body>
</html>
