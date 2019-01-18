<%--
  Created by zhshuo.
  Date: 2016-10-18
  Time: 9:45
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(function ($) {
        itemFile.initNutritionIndex();
    })
</script>
<div class="wrapper animated fadeInUp">
    <div class="btn-toolbar">
        <span class="nuEdit" id="nuAdd"><i class="fa fa-file-o"></i>新增</span>
        <span class="bluebtn nuEdit" id="nuEdit"><i class="fa fa-edit"></i>修改</span>
        <span id="nuDel" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
    </div>
    <div class="jqGrid_wrapper" style="margin-left: 10px">
        <table id="grid-table-nutrition"></table>
    </div>
</div>
