<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<script>
$(function () {
	member_discount_trash.pagerInit();
});
</script>
<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan-trash">
					<ul>
						<li>
							<span class="title">方案名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control"  name="name"/>
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
					<span id="reply" class="greenbtn"><i class="fa fa-reply"></i>还原</span>
					<span id="delete" class="redbtn"><i class="fa fa-times"></i>删除</span>
				</c:if>
				<span id="trash-refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table-2"></table>
                <div id="grid-pager-2"></div>
            </div>
</div>
