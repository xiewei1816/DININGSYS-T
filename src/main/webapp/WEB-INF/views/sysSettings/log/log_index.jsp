<%--
  Created by heshuai.
  Date: 2016-10-14
  Time: 17:08
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <span class="title">查询字段</span>
                    <div class="form-group big-wid">
                         <select name="gfreason" class="form-control">
                            <option value="" selected="selected">操作员</option>
                            <option value="">操作内容</option>
                        </select>
                    </div>
                </li>
                <li>
                    <span class="title">查询内容</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" name="gfname" />
                    </div>
                </li>
            </ul>
            <div class="search-btns">
                <button class="btn btn-primary">查询</button>
            </div>
        </form>
    </div>
    <div class="jqGrid_wrapper">
        <table id="grid-table"></table>
        <div id="grid-pager"></div>
    </div>
</div>