<%--
  Created by zhshuo.
  Date: 2016-10-28
  Time: 13:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script src="app/js/DININGSYS/permission/sys_per/sys_permission.js"></script>
<script>
    $(function ($) {
        sysPer.treeInit();
        sysPer.pageInit();
        $(".leftmenus .content").height($(window).height() - 100);
    })
</script>
<body>
<div class="wrapper animated fadeInUp" id="sysPerDiv">
    <div class="btn-toolbar">
        <button type="button" id="roleAdd" class="btn btn-success roleBtn">新增</button>
        <button id="editBtn" type="button" class="btn btn-primary">修改</button>
        <button id="delBtn" type="button" class="btn btn-danger">删除</button>
        <button id="flushBtn" type="button" class="btn btn-info" >刷新</button>
        <button id="permissonBtn" type="button" class="btn btn-danger roleBtn" >权限</button>
        <button id="userPwd" type="button" class="btn btn-warning userBtn">密码</button>
    </div>
    <div class="leftmenus col-md-2" style="float: left;">
        <div class="content zTreeDemoBackground">
            <ul id="sysPermisson" class="ztree"></ul>
        </div>
    </div>
    <div class="jqGrid_wrapper col-md-10">
        <div id="tabelWidth" style="width: 100%"></div>
        <table id="sr_per_table"></table>
    </div>
</div>