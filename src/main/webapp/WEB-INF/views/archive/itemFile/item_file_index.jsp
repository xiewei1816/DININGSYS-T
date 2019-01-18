<%--
  Created by zhshuo.
  Date: 2016-10-13
  Time: 17:07
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../head.jsp"/>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<script>
	var single = '${single}';
</script>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">

<script src="app/js/DININGSYS/archive/itemFile/itemFile.js"></script>
<script src="app/js/DININGSYS/archive/itemFile/itemFileColorManager.js"></script>
<script src="app/js/DININGSYS/archive/itemFile/item_file_zccf.js"></script>

<script type="text/javascript" src="assets/scripts/ajaxfileupload.js"></script>
<!--  <link rel="stylesheet" href="assets/scripts/jcrop/demo_files/main.css" type="text/css" />
<link rel="stylesheet" href="assets/scripts/jcrop/demo_files/demos.css" type="text/css" />
<link rel="stylesheet" href="assets/scripts/jcrop/css/jquery.Jcrop.css" />-->
<script type="text/javascript" src="assets/scripts/jcrop/js/jquery.Jcrop.min.js"></script>
<script>
    $(function () {
        itemFile.ztreeInit();
        itemFile.pageInit();
    })

</script>
<style type="text/css">
    div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
    div#rMenu ul li{
        margin: 1px 0;
        padding: 0 5px;
        cursor: pointer;
        list-style: none outside none;
        background-color: #DFDFDF;
    }
    .inlineText{
        display: inline;
        width: 300px;
    }
</style>
<form class="form-horizontal" role="form">
    <div class="row">
        <div class="col-md-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">品项分类</h3>
                </div>
                <div class="panel-body" id="itemFileTypeTree" style="overflow:auto;padding:0px">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
        <div class="col-md-10" id="itemFileRightDiv">
            <div class="btn-toolbar" id="itemFileEditDiv">
            	<c:if test="${single == '1' }">
	                <span id="itemFileEdit" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
	                <span id="itemFileDelb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
	                <span id="itemFileTc"><i class="fa fa-edit"></i>套餐</span>
                </c:if>
                <span id="itemFileZccf"><i class="fa fa-edit"></i>组成成分</span>
                <span id="itemFileColor"><i class="fa fa-edit"></i>颜色管理</span>
                <span id="exportItemFiles"><i class="fa fa-leaf"></i>导出</span>
                <span id="rank"><i class="fa fa-fa-sort-numeric-asc"></i>排序</span>
                <input id="searchItemFileText" class="form-control inlineText" type="text" placeholder="菜品名称、助记符、编码">
                <span id="searchItemFile">搜索</span>
                <span id="clearSearchText">清空</span>
            </div>
            <div id="itemFileTable">
                <table id="grid-table"></table>
            </div>
            <div id="itemFileTypeTable" style="margin-top: 20px">
                <table id="grid-table-item-type"></table>
            </div>
        </div>
     </div>
</form>