<%--
  Created by zhshuo.
  Date: 2016-10-08
  Time: 17:08
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<jsp:include page="../../head.jsp"/>
<script src="app/js/DININGSYS/archive/giftForm/giftForm.js"></script>
<script>
    $(function () {
        arGiftForm.init();
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
                        <input type="text" class="form-control" name="gfcode" />
                    </div>
                </li>
                <li>
                    <span class="title">名称</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" name="gfname" />
                    </div>
                </li>
                <li>
                    <span class="title">赠单原因类型</span>
                    <div class="form-group big-wid">
                        <select name="gfreason" class="form-control">
                            <option value="">默认类型</option>
                            <c:forEach items="${allGiftFormReason}" var="item" >
                                <option value="${item.id}">${item.gfrtype}</option>
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
	        <span id="add"><i class="fa fa-file-o"></i>新增</span>
	        <span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
	        <span id="gotype" class="royalbtn"><i class="fa fa-list-alt"></i>赠单原因类型</span>
	        <span id="delb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
        </c:if>
    </div>
    <div class="jqGrid_wrapper">
        <table id="grid-table"></table>
        <div id="grid-pager"></div>
    </div>
</div>