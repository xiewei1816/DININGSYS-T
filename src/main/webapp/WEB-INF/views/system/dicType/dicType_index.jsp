<%--
  Created by mrren.
  Date: 2016-07-26
  Time: 17:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script src="app/js/DININGSYS/system/dicType/sys_dicType.js"></script>
<div class="table-toolbar">
    <div class="clearfix">
        <shiro:hasPermission name="dicType:add">
            <a href="javascript:void(0)" class="btn green dicTypeAdd" data-toggle="modal">新增 <i class="fa fa-plus"></i></a>
        </shiro:hasPermission>
        <shiro:hasPermission name="dicType:edit">
            <a href="javascript:void(0)" class="btn purple dicTypeEdit"><i class="fa fa-times"></i> 修改</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="dicType:del">
            <a href="javascript:void(0)" class="btn red dicTypeDelBtn"><i class="fa fa-times"></i> 删除</a>
        </shiro:hasPermission>
    </div>
</div>
<table class="table table-striped table-bordered table-hover" id="dicType_table">
    <thead>
    <tr>
        <th class="table-checkbox"><input type="checkbox" class="group-checkable" data-set="#dicType_table .checkboxes" /></th>
        <th>分类名称</th>
        <th>分类代码</th>
        <th>创建时间</th>
    </tr>
    </thead>
    <tbody>

    </tbody>
</table>

<div id="dicType_ajax_modal" class="modal fade" tabindex="-1"></div>
<script>

    jQuery(document).ready(function() {
        dicType.init();
    });
</script>

