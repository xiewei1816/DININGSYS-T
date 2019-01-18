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
	bf_pro.pagerInit();
});
</script>
<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan-seach">
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
					<span id="add-pro" class="greenbtn"><i class="fa fa-file-o"></i>新增</span>
					<span id="update-pro" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
					<span id="delb-pro" class="redbtn"><i class="fa fa-trash"></i>删除</span>
				</c:if>
				<span id="trash-pro" class="graybtn"><i class="fa fa-trash-o"></i>回收站</span>
				<span id="refresh-pro" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table-5"></table>
            </div>
</div>
