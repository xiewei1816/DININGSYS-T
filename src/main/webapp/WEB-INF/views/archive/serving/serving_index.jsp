<%--
  Created by zhshuo.
  Date: 2016-09-29
  Time: 17:07
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../head.jsp"/>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<script src="app/js/DININGSYS/archive/serving/ae_serving.js"></script>
<script>
    $(function () {
        arServing.init();
        $("#grid-table").hideCol("id");
    })
</script>
<body>
<div class="wrapper animated fadeInUp">
    <div class="search-wrapper input-groups">
        <form class="query-pan" id="query-pan">
            <ul>
                <li>
                    <span class="title">编号</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" name="scode" />
                    </div>
                </li>
                <li>
                    <span class="title">名称</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" name="sname" />
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
	        <span class="add"><i class="fa fa-file-o"></i>新增</span>
	        <span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
	        <span id="delb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
        </c:if>
    </div>
    <div class="jqGrid_wrapper">
        <table id="grid-table"></table>
        <div id="grid-pager"></div>
    </div>
</div>