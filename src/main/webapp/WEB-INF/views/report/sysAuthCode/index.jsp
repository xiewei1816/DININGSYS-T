<%--
  Created by zhshuo.
  Date: 2017-06-06
  Time: 11:35
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>授权码日志</title>
    <jsp:include page="../../head.jsp"/>
    <script type="text/javascript" src="app/js/DININGSYS/report/sysAuthCode/sysAuthCode.js" ></script>
    <script>
        $(function () {
            sysAuthCode.pageInit();
        })
    </script>
</head>
<body>
<div class="wrapper animated fadeInUp">
    <div class="search-wrapper input-groups">
        <form class="query-pan" id="query-pan">
            <ul>
                <li>
                    <span class="title">被授权人</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="authOpUser" name="authOpUser" />
                    </div>
                </li>
                <li>
                    <span class="title">授权人</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="authUser" name="authUser" />
                    </div>
                </li>
                <li>
                    <span class="title">开始时间</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="startTime"
                               placeholder="结束时间" readonly>
                    </div>
                </li>
                <li>
                    <span class="title">结束时间</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="endTime"
                               placeholder="开始时间" readonly>
                    </div>
                </li>
            </ul>
            <div class="search-btns">
                <input class="btn btn-success" id="doSearch" type="button" value="查询">
            </div>
        </form>
    </div>
    <div class="btn-toolbar">
        <span id="refresh" class="greenbtn"><i class="fa fa-refresh"></i>刷新</span>
    </div>
    <div class="jqGrid_wrapper">
        <table id="grid-table"></table>
        <div id="grid-pager"></div>
    </div>
</div>
</body>
</html>
