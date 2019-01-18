<%--
  Created by zengchao
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<style type="text/css">
    div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
    div#rMenu ul li{
        margin: 1px 0;
        padding: 0 5px;
        cursor: pointer;
        list-style: none outside none;
        background-color: #DFDFDF;
    }
</style>
<div>
    <div class="leftmenus col-md-2 col-lg-2 col-sm-2">
        <div class="content zTreeDemoBackground">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
    </div>
    <div class="wrapper animated fadeInUp col-md-10 col-lg-10 col-sm-10">
        <div id="tabelWidth"></div>
        <div class="btn-toolbar" id="methodsEditDiv">
            <c:if test="${single == '1' }">
	            <span id="add" class="greenbtn"><i class="fa fa-file-o"></i>新增</span>
	            <span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
	            <span id="delb" class="redbtn"><i class="fa fa-trash"></i>删除</span>
            </c:if>
        </div>
        <div class="jqGrid_wrapper ">
            <table id="grid-table"></table>
            <div id="grid-pager"></div>
        </div>
    </div>
</div>

<div id="rMenu">
    <ul>
        <li id="m_addTypes">新增类别</li>
        <li id="m_addMethods">新增方法</li>
        <li id="m_editTypes">修改类别</li>
        <li id="m_del">删除节点</li>
    </ul>
</div>
<script type="text/javascript" src="app/js/DININGSYS/archive/flavor/flavor.js"></script>
<script>
    $(function () {
        flavor.zTreeInit();
        flavor.pagerInit(); 
        $(".leftmenus .content").height($(window).height() - 100);
    });
</script>
