<%--
  Created by zhshuo.
  Date: 2016-10-09
  Time: 10:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../head.jsp"/>
<script src="app/js/DININGSYS/archive/giftForm/giftForm.js"></script>
<script>
    $(function () {
        arGiftForm.giftReasonInit();
        $("#grid-table").hideCol("id");
    })
</script>
<body>
<div class="wrapper animated fadeInUp">
    <div class="btn-toolbar">
        <span id="giftReasonAdd"><i class="fa fa-file-o"></i>新增</span>
        <span id="giftReasonUpdate" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
        <span id="giftReasonDelb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
    </div>
    <div class="jqGrid_wrapper">
        <table id="grid-table"></table>
        <div id="grid-pager"></div>
    </div>
</div>