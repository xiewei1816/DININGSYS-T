<%--
  Created by zhshuo.
  Date: 2016-10-10
  Time: 13:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script src="app/js/DININGSYS/archive/proMethods/proMethods.js"></script>
<script>
    $(function () {
        proMethods.zTreeInit();
        proMethods.pagerInit();
    })

</script>
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
<form class="form-horizontal" role="form">
    <div class="row">
        <div class="col-md-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">制作方法类别</h3>
                    </span>
                </div>
                <div class="panel-body" id="proMethodTypeTree" style="overflow:auto;padding:0px">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
        <div class="col-md-10" id="rightBigPanel">
            <div id="tabelWidth" ></div>
            <div class="btn-toolbar" id="methodsEditDiv" style="display: none">
                <c:if test="${single == '1' }">
	                <span id="methodsEdit" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
	                <span id="methodsDelb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
                </c:if>
            </div>
            <div class="jqGrid_wrapper ">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
            <div class="jqGrid_wrapper ">
                <table id="grid-table1"></table>
                <div id="grid-pager1"></div>
            </div>
        </div>
    </div>
</form>

<div id="rMenu">
    <ul>				
       <c:if test="${single == '1' }">
        <li id="m_addTypes">新增类别</li>
        <li id="m_addMethods">新增方法</li>
        <li id="m_editTypes">修改类别</li>
        <li id="m_del">删除节点</li>
        </c:if>
    </ul>
</div>